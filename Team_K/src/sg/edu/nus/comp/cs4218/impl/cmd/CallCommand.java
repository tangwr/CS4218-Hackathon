package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Utility;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.app.ApplicationFactory;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken.TokenType;

/**
 * A Call Command is a sub-command consisting of at least one non-keyword and
 * quoted (if any).
 * 
 * <p>
 * <b>Command format:</b> <code>(&lt;non-Keyword&gt; | &lt;quoted&gt;)*</code>
 * </p>
 */

public class CallCommand implements Command {
	public static final String EXP_INVALID_APP = "Invalid app.";
	public static final String EXP_SYNTAX = "Invalid syntax encountered.";
	public static final String EXP_REDIR_PIPE = "File output redirection and pipe "
			+ "operator cannot be used side by side.";
	public static final String EXP_SAME_REDIR = "Input redirection file same as "
			+ "output redirection file.";
	public static final String EXP_STDOUT = "Error writing to stdout.";
	public static final String EXP_NOT_SUPPORTED = " not supported yet";

	private String app;
	private String cmdline;
	private String inputStreamS;
	private String outputStreamS;
	private String processedCommand;
	private List<String> cmdTokens;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	public static void main(String[] args) throws AbstractApplicationException, ShellException, IOException {
		Scanner sc = new Scanner(System.in);
		String cmd = sc.nextLine();
		CallCommand cc = new CallCommand(cmd);
		cc.evaluate(System.in, System.out);
		sc.close();
	}
	
	public CallCommand(String cmdLine) throws ShellException, AbstractApplicationException, IOException {
		this.app = "";
		this.cmdline = cmdLine.trim();
		this.processedCommand = processBackquotes(cmdline);
		this.cmdTokens = splitArguments(processedCommand);
		this.inputStreamS = extractInputRedir(cmdTokens);
		this.outputStreamS = extractOutputRedir(cmdTokens);
	}

	/**
	 * Evaluates sub-command using data provided through stdin stream. Writes
	 * result to stdout stream.
	 * 
	 * @param stdin
	 *            InputStream to get data from.
	 * @param stdout
	 *            OutputStream to write resultant data to.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while evaluating the sub-command.
	 * @throws ShellException
	 *             If an exception happens while evaluating the sub-command.
	 */
	@Override
	public void evaluate(InputStream stdin, OutputStream stdout)
			throws AbstractApplicationException, ShellException {
		if (cmdTokens.isEmpty()) {
			return;
		}
		
		if (inputStreamS != null && inputStreamS.equals(outputStreamS)) {
			throw new ShellException(EXP_SAME_REDIR);
		}

		//expand Glob after processing quote and input/output streams
		cmdTokens = expandGlob();
		inputStream = getInputStream();
		if (inputStream == null) {
			inputStream = stdin;
		}
		
		outputStream = getOutputStream();
		if (outputStream == null) {
			outputStream = stdout;
		}
		
		List<String> argsList = findArguments();
		app = argsList.remove(0);
		
		String[] args = argsList.toArray(new String[argsList.size()]);
		ApplicationFactory.runApp(app, args, inputStream, outputStream);
		terminate();
	}
	
	/**
	 * Extraction of input redirection from cmdLine with two slots at end of
	 * cmdVector reserved for <inputredir and >outredir. For valid inputs,
	 * assumption that input redir and output redir are always at the end of the
	 * command and input stream first the output stream if both are in the args
	 * 
	 * @param str
	 *            String of command to split.
	 * @param cmdVector
	 *            Vector of String to store the found result into.
	 * @param endIdx
	 *            Index of str to start parsing from.
	 * 
	 * @return endIdx Index of string where the parsing of arguments stopped
	 *         (due to no more matches).
	 * 
	 * @throws ShellException
	 *             When more than one input redirection string is found, or when
	 *             invalid syntax is encountered..
	 */
	private String extractInputRedir(List<String> tokens)
			throws ShellException {
		String result = null;
		for (int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			if (token.equals("<")) {
				if (result != null) {
					throw new ShellException("Too many input tokens");
				} 
				
				if (i == tokens.size() - 1) {
					throw new ShellException("Invalid input");
				}
				result = tokens.get(i+1);
				i=i+2;
				if (i < tokens.size()) {
					String next = tokens.get(i);
					List<AbstractToken> subToken = Utility.tokenize(next);
					if (subToken.get(0).getType() == TokenType.NORMAL) {
						throw new ShellException("Too many inputs for IO redirection");
					}
				}
			}
		}
		return result;
	}

	/**
	 * Extraction of output redirection from cmdLine with two slots at end of
	 * cmdVector reserved for <inputredir and >outredir. For valid inputs,
	 * assumption that input redir and output redir are always at the end of the
	 * command and input stream first the output stream if both are in the args.
	 * 
	 * @param str
	 *            String of command to split.
	 * @param cmdVector
	 *            Vector of String to store the found result into.
	 * @param endIdx
	 *            Index of str to start parsing from.
	 * 
	 * @return endIdx Index of string where the parsing of arguments stopped
	 *         (due to no more matches).
	 * 
	 * @throws ShellException
	 *             When more than one input redirection string is found, or when
	 *             invalid syntax is encountered..
	 */
	private String extractOutputRedir(List<String> tokens)
			throws ShellException {
		String result = null;
		for (int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			if (token.equals(">")) {
				if (result != null) {
					throw new ShellException("Too many outputs");
				}
				if (i == tokens.size() -1){
					throw new ShellException("Invalid output");
				}
				result = tokens.get(i+1);
				i = i+2;
				if (i < tokens.size()) {
					String next = tokens.get(i);
					List<AbstractToken> subToken = Utility.tokenize(next);
					if (subToken.get(0).getType() == TokenType.NORMAL) {
						throw new ShellException("Too many outputs for IO redirection");
					}
				}
			}
		}
		return result;
	}
	
	public InputStream getInputStream() throws ShellException {
		if (inputStreamS == null) {
			return null;
		} else {
			try {
				return new FileInputStream(new File(inputStreamS));
			} catch (FileNotFoundException e) {
				throw new ShellException(e.toString());
			} 
		}
	}

	public OutputStream getOutputStream() throws ShellException {
		if (outputStreamS == null) {
			return null;
		} else {
			try {
				return new FileOutputStream(new File(outputStreamS));
			} catch (FileNotFoundException e) {
				throw new ShellException(e.toString());
			}	
		}
	}
	

	private List<String> expandGlob() {		//assume * is not in any quotation
		List<String> expandedArgs = new ArrayList<String>();
		for (int i = 0; i < cmdTokens.size(); i++) {
			String arg = cmdTokens.get(i).trim();
			if (arg.indexOf("*") != -1) {
				try {
					List<String> expandedPath = expandPath(arg);
					Collections.sort(expandedPath);
					expandedArgs.addAll(expandedPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				expandedArgs.add(arg);
			}
		}
		return expandedArgs;
	}
	
	/**
	 * function to expand a path to all of its existed files and directories
	 * @param curPath
	 * @return
	 * @throws IOException 
	 */
	//rule: Collect all the paths to existing files and directories such that these paths can be
	// obtained by replacing all the unquoted asterisk symbols in ARG by some (possibly
	//	empty) sequences of non-slash characters
	//If no such path -> return List with only curPath
	//else replace curPath with new list of expanded paths
	
	//we can assume first asterisk is after last slash (ie only expand current directory)
	private List<String> expandPath(String curPath) throws IOException {
		List<String> expanded = new ArrayList<String>();
		expanded.add(curPath);
		int lastFileSepIndex = curPath.lastIndexOf("/");
		int firstAsteriskIndex = curPath.indexOf("*");
		
		if (firstAsteriskIndex < lastFileSepIndex && lastFileSepIndex != -1) {
			return expanded;		//non applicable to glob
		}
		String directory = "";
		String glob = "";
		if (lastFileSepIndex == -1) {
			glob = curPath;
			directory = Environment.getCurrentDirectory();
		} else {
			directory = curPath.substring(0, lastFileSepIndex);

			File f = new File(directory);
			if (!f.isAbsolute()) {
				directory = Environment.getCurrentDirectory() + File.separator +  directory;
				File newPath = new File(directory);
				if (newPath.exists()) {
					directory = newPath.getCanonicalPath();
				} else {
					return expanded; //directory does not exist
				}
			}
			glob = curPath.substring(lastFileSepIndex+1);
		}
		File dir = new File(directory);
		String [] files = dir.list();
		String regex = ("\\Q" + glob + "\\E").replace("*", "\\E.*\\Q");
		for (int i = 0; i < files.length; i++) {
			if (files[i].matches(regex)) {
				expanded.add(directory + File.separator + files[i]);
			}
		}
		if (expanded.size() > 1) {
			expanded.remove(0);	//remove original curPath
		}

		return expanded;
	}
	
	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null && 
					outputStream.getClass() == PipedOutputStream.class) {
				outputStream.close();
			}
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * Substitute each back quote token with the corresponding result from
	 * command substitution if necessary.
	 * 
	 * @param cmdLine
	 *            original command line
	 * @return command line after command substitution.
	 */
	public static String processBackquotes(String cmdLine)
			throws ShellException, AbstractApplicationException {
		List<AbstractToken> tokens = Utility.tokenize(cmdLine);
		for (AbstractToken token : tokens) {
			token.checkValid();
		}
		String result = "";
		for (AbstractToken token : tokens) {
			TokenType type = token.getType();
			String val = token.value();
			if (type == TokenType.BACK_QUOTES) {
				// Split the strings inside into multiple args
				List<String> strList = splitBySpaces(val);
				if (!strList.isEmpty()) {
					result += strList.get(0);
					for (int i = 1; i < strList.size(); i++) {
						result += " " + strList.get(i);
					}
				}
			} else {
				result += val;
			}
		}

		return result;
	}

	/**
	 * Split the command line into arguments/tokens. Also remove quotes from
	 * quote tokens.
	 * 
	 * @param input
	 *            the command line after command substitution
	 * @return A list of substituted tokens.
	 */
	public static List<String> splitArguments(String input)
			throws AbstractApplicationException, ShellException, IOException {
		List<AbstractToken> tokens = Utility.tokenize(input);
		for (AbstractToken token : tokens) {
			token.checkValid();
		}

		String current = null;
		List<String> list = new ArrayList<String>();
		for (AbstractToken token : tokens) {
			TokenType type = token.getType();
			String val = token.value();
			if (type == TokenType.SPACES) {
				addNonNull(list, current);
				current = null;
			} else if (type == TokenType.INPUT || type == TokenType.OUTPUT) {
				addNonNull(list, current);
				current = null;
				list.add(val);
			} else {
				if (type == TokenType.SINGLE_QUOTES
						|| type == TokenType.DOUBLE_QUOTES) {
					val = val.substring(1, val.length() - 1);
				}
				current = current == null ? val : current + val;
			}
		}
		addNonNull(list, current);
		return list;
	}
	
	/**
	 * Takes a string input and trims the string
	 * 
	 * @param String
	 *            to trim
	 * @return List of split strings
	 */
	private static List<String> splitBySpaces(String input) {
		String str = input.replaceAll("\\s+", " ").trim();
		return Arrays.asList(str.split("\\s"));
		//return Arrays.asList(input.split(" "));
	}

	/**
	 * Adds non null string to list
	 * 
	 * @param list
	 *            of strings
	 * @param non
	 *            null str
	 */
	private static void addNonNull(List<String> list, String str) {
		if (str != null) {
			list.add(str);
		}
	}
	
	/**
	 * Find the arguments from the command which does not include IO
	 * redirections.
	 * 
	 * @return A list of string that represents arguments.
	 */
	public List<String> findArguments() {
		ArrayList<String> result = new ArrayList<String>();
		int currentIndex = 0;
		while (currentIndex < cmdTokens.size()) {
			String token = cmdTokens.get(currentIndex++);
			if (token.equals("<") || token.equals(">")) {
				// Ignore the next token (possibly file name)
				currentIndex++;
			} else {
				result.add(token);
			}
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "App: " + app + System.lineSeparator();
		s += "CmdLine: " + cmdline + System.lineSeparator();
		s += "Input: " + inputStreamS+ System.lineSeparator();
		s += "Output: " + outputStreamS + System.lineSeparator();
		s += "Args: ";
		for (int i = 0; i < cmdTokens.size(); i++) {
			s+= cmdTokens.get(i) + " ";
		}
		s += System.lineSeparator();
		return s;
	}

}
