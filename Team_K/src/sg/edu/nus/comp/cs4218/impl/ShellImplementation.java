package sg.edu.nus.comp.cs4218.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.CommandFactory;

/**
 * A Shell is a command interpreter and forms the backbone of the entire
 * program. Its responsibility is to interpret commands that the user type and
 * to run programs that the user specify in her command lines.
 * 
 * <p>
 * <b>Command format:</b>
 * <code>&lt;Pipe&gt; | &lt;Sequence&gt; | &lt;Call&gt;</code>
 * </p>
 */

public class ShellImplementation implements Shell {

	/**
	 * Main method for the Shell Interpreter program.
	 * 
	 * @param args
	 *            List of strings arguments, unused.
	 * @throws ShellException 
	 * @throws AbstractApplicationException 
	 * @throws IOException 
	 */

	public void run() {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				System.in));
		String readLine = null;
		String currentDir;

		while (true) {
			try {
				currentDir = Environment.getCurrentDirectory();
				System.out.print(currentDir + ">");
				
				readLine = bReader.readLine();
				
				if (readLine == null) {
					break;
				}
				
				if (("").equals(readLine)) {
					continue;
				}
				
				parseAndEvaluate(readLine, System.out);
				//Command command = CommandFactory.getCommand(readLine);
				//command.evaluate(null, System.out);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}		
	}

	/**
	 * Main method for the Shell Interpreter program.
	 * 
	 * @param args
	 *            List of strings arguments, unused.
	 * @throws ShellException 
	 * @throws AbstractApplicationException 
	 */

	public static void main(String... args) throws AbstractApplicationException, ShellException {
		ShellImplementation shell = new ShellImplementation();
		shell.run();
	}

	@Override
	public void parseAndEvaluate(String cmdline, OutputStream stdout) throws ShellException, AbstractApplicationException {	
		try {
			Command command = CommandFactory.getCommand(cmdline);
			command.evaluate(null, stdout);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String pipeTwoCommands(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pipeMultipleCommands(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pipeWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globNoPaths(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globOneFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globFilesDirectories(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String globWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectInput(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectOutput(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectInputWithNoFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectOutputWithNoFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectInputWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirectOutputWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String performCommandSubstitution(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String performCommandSubstitutionWithException(String args) {
		// TODO Auto-generated method stub
		return null;
	}
}
