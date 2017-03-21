package sg.edu.nus.comp.cs4218;

import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public interface Shell {
	
	/**
	 * Parses and evaluates user's command line.
	 */	
	public void parseAndEvaluate(String cmdline, OutputStream stdout) throws AbstractApplicationException, ShellException;
	
	/**
	 * Evaluate pipe call with two commands
	 * @param args String containing the command, input arguments and the pipe operator
	 */
	public String pipeTwoCommands(String args);

	/**
	 * Evaluate pipe call with more than two commands
	 * @param args String containing the commands, input arguments and the pipe operators
	 */
	public String pipeMultipleCommands(String args);

	/**
	 * Evaluate pipe call with one part generating an exception
	 * @param args String containing the commands, input arguments and the pipe operator/s
	 */
	public String pipeWithException(String args);

	/**
	 * Evaluate globbing with no files or directories
	 * @param args String containing the commands, input arguments and the asterisk (globbing operator)
	 */
	public String globNoPaths(String args);

	/**
	 * Evaluate globbing with one file (one path)
	 * @param args String containing the commands, input arguments and the asterisk (globbing operator)
	 */
	public String globOneFile(String args);

	/**
	 * Evaluate globbing with multiple files and directories (multiple paths)
	 * @param args String containing the commands, input arguments and the asterisk (globbing operator)
	 */
	public String globFilesDirectories(String args);

	/**
	 * Evaluate globbing with exception
	 * @param args String containing the commands, input arguments and the asterisk (globbing operator)
	 */
	public String globWithException(String args);

	/**
	 * Evaluate opening InputStream from file for input redirection
	 * @param args String containing the commands, input arguments and the "<" symbol (input redirection operator)
	 */
	public String redirectInput(String args);

	/**
	 * Evaluate opening OutputStream to file for output redirection
	 * @param args String containing the commands, input arguments and the ">" symbol (output redirection operator)
	 */
	public String redirectOutput(String args);

	/**
	 * Evaluate input redirection with no files
	 * @param args String containing the commands, input arguments and the "<" symbol (input redirection operator)
	 */
	public String redirectInputWithNoFile(String args);

	/**
	 * Evaluate output redirection with no files
	 * @param args String containing the commands, input arguments and the ">" symbol (output redirection operator)
	 */
	public String redirectOutputWithNoFile(String args);

	/**
	 * Evaluate input redirection with exception
	 * @param args String containing the commands, input arguments and the "<" symbol (input redirection operator)
	 */
	public String redirectInputWithException(String args);

	/**
	 * Evaluate output redirection with exception
	 * @param args String containing the commands, input arguments and the ">" symbol (output redirection operator)
	 */
	public String redirectOutputWithException(String args);

	/**
	 * Evaluate command substitution
	 * @param args String containing the commands, input arguments surrounded by backquotes
	 */
	public String performCommandSubstitution(String args);

	/**
	 * Evaluate command substitution with exception
	 * @param args String containing the commands, input arguments surrounded by backquotes
	 */
	public String performCommandSubstitutionWithException(String args);
}
