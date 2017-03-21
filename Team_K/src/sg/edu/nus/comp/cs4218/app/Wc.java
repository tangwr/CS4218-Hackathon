package sg.edu.nus.comp.cs4218.app;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.WcException;

public interface Wc extends Application {

	/**
	 * Returns string containing the character count in file
	 * @param args String containing command and arguments
	 */
	public String printCharacterCountInFile(String args) throws WcException;

	/**
	 * Returns string containing the word count in file
	 * @param args String containing command and arguments
	 */
	public String printWordCountInFile(String args) throws WcException;

	/**
	 * Returns string containing the newline count in file
	 * @param args String containing command and arguments
	 */
	public String printNewlineCountInFile(String args) throws WcException;

	/**
	 * Returns string containing all counts in file
	 * @param args String containing command and arguments
	 */
	public String printAllCountsInFile(String args) throws WcException;

	/**
	 * Returns string containing the character count in Stdin
	 * @param args String containing command and arguments
	 */
	public String printCharacterCountInStdin(String args) throws WcException;

	/**
	 * Returns string containing the word count in Stdin
	 * @param args String containing command and arguments
	 */
	public String printWordCountInStdin(String args) throws WcException;

	/**
	 * Returns string containing the newline count in Stdin
	 * @param args String containing command and arguments
	 */
	public String printNewlineCountInStdin(String args) throws WcException;

	/**
	 * Returns string containing all counts in Stdin
	 * @param args String containing command and arguments
	 */
	public String printAllCountsInStdin(String args) throws WcException;

}
