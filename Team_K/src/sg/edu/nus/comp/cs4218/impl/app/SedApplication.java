package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sg.edu.nus.comp.cs4218.app.Sed;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SedException;

public class SedApplication implements Sed {
	private String file;
	private String regexp;
	private String replacement;
	private boolean doReplaceAll;
	private InputStream inputStream;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if (args == null || stdout == null) {
			throw new SedException("Null Pointer Exception");
		}
		if (args.length < 1) {
			throw new SedException("Too few arguments");
		}
		String replacementArg = args[0];
		parseReplacementString(replacementArg);
		if (!isValidRegex(regexp) || regexp.length() == 0) {
			replaceSubstringWithInvalidRegex(regexp);
		}
		if (!isValidRegex(replacement)) {
			replaceSubstringWithInvalidReplacement(replacement);
		}
		String output = "";
		if (args.length == 1) {
			if (stdin == null) {
				throw new SedException("Null Stdin");
			}
			inputStream = stdin;
			if (!doReplaceAll) {
				output = replaceFirstSubStringFromStdin("");
			} else {
				output = replaceAllSubstringsInStdin("");
			}
		} else if (args.length == 2) {
			file = getFullPath(args[1]);	
			if (!doReplaceAll) {
				output = replaceFirstSubStringInFile("");
			} else {
				output = replaceAllSubstringsInFile("");
			}
		} else {
			throw new SedException("Too many arguments");
		}
		try {
			stdout.write(output.getBytes());	
		} catch (IOException e) {
			throw new SedException("Cannot write to stdout");
		}
	}
	
	@Override
	public String replaceFirstSubStringInFile(String args) throws SedException {
		if (file == null || regexp == null || replacement == null) {
			throw new SedException("Null Pointer Exception");
		}
		String output = "";
		boolean isFileEmpty = true;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String input;
			Pattern pattern = Pattern.compile(regexp);
			while ((input = br.readLine()) != null) {
				isFileEmpty = false;
				Matcher matcher = pattern.matcher(input);
				output += matcher.replaceFirst(replacement) + System.lineSeparator();
			}
			br.close();
		} catch (Exception e) {
			throw new SedException(e.toString());
		} 
		if (isFileEmpty) {
			return System.lineSeparator();	
		} else {
			return output;
		}
	}

	@Override
	public String replaceAllSubstringsInFile(String args) throws SedException {
		if (file == null || regexp == null || replacement == null) {
			throw new SedException("Null Pointer Exception");
		}
		String output = "";
		boolean isFileEmpty = true;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String input;
			Pattern pattern = Pattern.compile(regexp);
			while ((input = br.readLine()) != null) {
				isFileEmpty = false;
				Matcher matcher = pattern.matcher(input);
				output += matcher.replaceAll(replacement) + System.lineSeparator();
			}
			br.close();
		} catch (Exception e) {
			throw new SedException(e.toString());
		} 
		if (isFileEmpty) {
			return System.lineSeparator();	
		} else {
			return output;
		}
	}

	@Override
	public String replaceFirstSubStringFromStdin(String args) throws SedException {
		if (inputStream == null || regexp == null || replacement == null) {
			throw new SedException("Null Pointer Exception");
		}
		String output = "";
		boolean isStreamEmpty = true;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String input;
			Pattern pattern = Pattern.compile(regexp);
			while ((input = br.readLine()) != null) {
				isStreamEmpty = false;
				Matcher matcher = pattern.matcher(input);
				output += matcher.replaceFirst(replacement) + System.lineSeparator();
			}
			br.close();
		} catch (Exception e) {
			throw new SedException(e.toString());
		} 
		if (isStreamEmpty) {
			return System.lineSeparator();	
		} else {
			return output;
		}
	}

	@Override
	public String replaceAllSubstringsInStdin(String args) throws SedException {
		if (inputStream == null || regexp == null || replacement == null) {
			throw new SedException("Null Pointer Exception");
		}
		String output = "";
		boolean isStreamEmpty = true;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String input;
			Pattern pattern = Pattern.compile(regexp);
			while ((input = br.readLine()) != null) {
				isStreamEmpty = false;
				Matcher matcher = pattern.matcher(input);
				output += matcher.replaceAll(replacement) + System.lineSeparator();
			}
			br.close();
		} catch (Exception e) {
			throw new SedException(e.toString());
		} 
		if (isStreamEmpty) {
			return System.lineSeparator();	
		} else {
			return output;
		}
	}

	@Override
	public String replaceSubstringWithInvalidReplacement(String args) throws SedException {
		throw new SedException("Invalid Replacement Expression: " + args);
	}

	@Override
	public String replaceSubstringWithInvalidRegex(String args) throws SedException {
		throw new SedException("Invalid Regex To Replace: " + args);
	}
	
	private void parseReplacementString(String input) throws SedException {
		if (input.length() < 4) {	//require s///
			throw new SedException("Invalid replacement");
		}
		if (!input.substring(0, 1).equals("s")) {
			throw new SedException("Replacement must start with \"s\"");
		}
		String division = input.substring(1,2);
		List<Integer> indicesOfDivision = new ArrayList<Integer>();
		for (int i = 1; i < input.length(); i++) {
			if (input.charAt(i) == division.charAt(0)) {
				indicesOfDivision.add(i);
			}
		}
		if (indicesOfDivision.size() != 3) {
			throw new SedException("Invalid number of divisions");
		}
		regexp = input.substring(indicesOfDivision.get(0) + 1, indicesOfDivision.get(1));
		replacement = input.substring(indicesOfDivision.get(1) + 1, indicesOfDivision.get(2));
		if (regexp.contains(division) || replacement.contains(division)) {
			throw new SedException("Invalid division");
		}
		if (indicesOfDivision.get(2) != input.length() - 1) {
			if (input.substring(indicesOfDivision.get(2) + 1, input.length()).equals("g")) {
				doReplaceAll = true;
			} else {
				throw new SedException("Invalid termination of replacement");
			}
		} else {
			doReplaceAll = false;
		}
	}
	
	private String getFullPath(String fileName) throws SedException {
		File file = new File(fileName);
		if (!file.isAbsolute()) {
			try {
				file = file.getCanonicalFile();
			} catch (IOException e) {
				throw new SedException("Cannot get canonical path of file "+fileName);
			}
		}
		if (file.isFile()) {
			return file.getAbsolutePath();
		} else {
			throw new SedException(fileName + " is not existed");
	
		}
	}
	
	private boolean isValidRegex(String pattern) {
		if (pattern == null) {
			return false;
		}
		try {
			Pattern.compile(pattern);
		} catch (PatternSyntaxException e) {
			return false;
		}
		return true;
	}
}
