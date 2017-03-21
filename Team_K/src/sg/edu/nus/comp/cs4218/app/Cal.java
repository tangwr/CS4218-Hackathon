package sg.edu.nus.comp.cs4218.app;

import sg.edu.nus.comp.cs4218.Application;

public interface Cal extends Application {

	/**
	 * Print the calendar of the current month
	 * @param args String containing command and arguments to print the calendar of the current month
	 */
	public String printCal(String args);

	/**
	 * Returns the string to print the calendar of the current month with Monday
	 * as the first day of the week
	 * @param args String containing command and arguments
	 */
	public String printCalWithMondayFirst(String args);

	/**
	 * Returns the string to print the calendar for specified month and year
	 * @param args String containing command and arguments
	 */
	public String printCalForMonthYear(String args);

	/**
	 * Returns the string to print the calendar for specified year
	 * @param args String containing command and arguments
	 */
	public String printCalForYear(String args);

	/**
	 * Returns the string to print the calendar for specified month and year
	 * with Monday as the first day of the week
	 * @param args String containing command and arguments
	 */
	public String printCalForMonthYearMondayFirst(String args);

	/**
	 * Returns the string to print the calendar for specified year with Monday
	 * as the first day of the week
	 * @param args String containing command and arguments
	 */
	public String printCalForYearMondayFirst(String args);
}
