package sg.edu.nus.comp.cs4218.app;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.GrepException;

public interface Grep extends Application {

	/**
	 * Returns string containing lines which match the specified pattern in
	 * Stdin
	 * @param args String containing command and arguments
	 */
	public String grepFromStdin(String args) throws GrepException;;

	/**
	 * Returns string containing lines which match the specified pattern in the
	 * given file
	 * @param args  String containing command and arguments
	 * @throws GrepException 
	 */
	public String grepFromOneFile(String args) throws GrepException;

	/**
	 * Returns string containing lines which match the specified pattern in the
	 * given files
	 * @param args  String containing command and arguments
	 * @throws GrepException 
	 */
	public String grepFromMultipleFiles(String args) throws GrepException;

	/**
	 * Returns string when invalid pattern is specified in grep from Stdin
	 * @param args String containing command and arguments
	 * @throws GrepException 
	 */
	public String grepInvalidPatternInStdin(String args) throws GrepException;

	
	/**
	 * Returns string when invalid pattern is specified in grep from file
	 * @param args String containing command and arguments
	 * @throws GrepException 
	 */
	public String grepInvalidPatternInFile(String args) throws GrepException;
}
