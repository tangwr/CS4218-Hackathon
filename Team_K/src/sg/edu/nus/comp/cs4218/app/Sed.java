package sg.edu.nus.comp.cs4218.app;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.SedException;

public interface Sed extends Application {

	/**
	 * Returns string containing lines with the first matched substring replaced
	 * in file
	 * @param args String containing command and arguments
	 */
	public String replaceFirstSubStringInFile(String args) throws SedException;

	/**
	 * Returns string containing lines with all matched substring replaced in
	 * file
	 * @param args String containing command and arguments
	 */
	public String replaceAllSubstringsInFile(String args) throws SedException;

	/**
	 * Returns string containing lines with first matched substring replaced in
	 * Stdin
	 * @param args String containing command and arguments
	 */
	public String replaceFirstSubStringFromStdin(String args) throws SedException;

	/**
	 * Returns string containing lines with all matched substring replaced in
	 * Stdin
	 * @param args String containing command and arguments
	 */
	public String replaceAllSubstringsInStdin(String args) throws SedException;

	/**
	 * Returns string containing lines when invalid replacement string is
	 * provided
	 * @param args String containing command and arguments
	 */
	public String replaceSubstringWithInvalidReplacement(String args) throws SedException;

	/**
	 * Returns string containing lines when invalid regex is provided
	 * @param args String containing command and arguments
	 */
	public String replaceSubstringWithInvalidRegex(String args) throws SedException;

}
