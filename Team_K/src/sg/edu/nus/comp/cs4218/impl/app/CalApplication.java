package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.helper.CalendarHelper;
import sg.edu.nus.comp.cs4218.Constants;
import sg.edu.nus.comp.cs4218.Utility;

public class CalApplication implements Cal {
	static final String MONDAY_FIRST_FLAG = "-m";
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws CalException {
		if (stdout == null) {
			throw new CalException(Constants.CalMessage.STDOUT_IS_NULL);
		}
		
		if (args == null) {
			throw new CalException(Constants.CalMessage.INVALID_ARGS);
		}
	
		if (args.length > 3) {
			throw new CalException(Constants.CalMessage.INVALID_NUMBER_ARGUMENTS);
		}
		
		String inputArgs = Utility.arrayToString(args);
		String output = null;
		
		if (args.length == 0) {
			output = printCal(inputArgs);
		} else if (args.length == 1 && args[0].equals(MONDAY_FIRST_FLAG)) {
			output = printCalWithMondayFirst(inputArgs);
		} else if (args.length == 1 && !args[0].equals(MONDAY_FIRST_FLAG)) {
			output = printCalForYear(inputArgs);
		} else if (args.length == 2 && args[0].equals(MONDAY_FIRST_FLAG)) {
			output = printCalForYearMondayFirst(inputArgs);
		} else if (args.length == 2 && !args[0].equals(MONDAY_FIRST_FLAG)) {
			output = printCalForMonthYear(inputArgs);
		} else if (args.length == 3 && args[0].equals(MONDAY_FIRST_FLAG)) {
			output = printCalForMonthYearMondayFirst(inputArgs);
		}
		
		if (output == null) {
			throw new CalException(Constants.CalMessage.INVALID_INPUT);
		}
		
		try {
			stdout.write(output.getBytes());
		} catch (Exception e) {
			throw new CalException(e.getMessage());
		}
	}
	
	/**
	 * Print the calendar of the current month
	 * @param args String containing command and arguments to print the calendar of the current month
	 */
	@Override
	public String printCal(String args) {
		Date current = new Date();
		int month = Utility.getMonth(current);
		int year = Utility.getYear(current);
		return CalendarHelper.generateCalendarForMonthYear(month, year, false);
	}

	
	/**
	 * Returns the string to print the calendar of the current month with Monday
	 * as the first day of the week
	 * @param args String containing command and arguments
	 */
	@Override
	public String printCalWithMondayFirst(String args) {
		Date current = new Date();
		int month = Utility.getMonth(current);
		int year = Utility.getYear(current);
		return CalendarHelper.generateCalendarForMonthYear(month, year, true);
	}

	/**
	 * Returns the string to print the calendar for specified month and year
	 * @param args String containing command and arguments
	 */
	@Override
	public String printCalForMonthYear(String args) {
		String[] params = Utility.stringToArray(args);
		try {
			int month = Integer.parseInt(params[0]);
			int year = Integer.parseInt(params[1]);
			if (!Utility.isValidMonth(month) || !Utility.isValidYear(year)) {
				return null;
			}
			
			return CalendarHelper.generateCalendarForMonthYear(month, year, false);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Returns the string to print the calendar for specified month and year
	 * @param args String containing command and arguments
	 */
	@Override
	public String printCalForMonthYearMondayFirst(String args) {
		String[] params = Utility.stringToArray(args);
		try {
			int month = Integer.parseInt(params[1]);
			int year = Integer.parseInt(params[2]);

			if (!Utility.isValidMonth(month) || !Utility.isValidYear(year)) {
				return null;
			}
			
			return CalendarHelper.generateCalendarForMonthYear(month, year, true);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns the string to print the calendar for specified year
	 * @param args String containing command and arguments
	 */
	@Override
	public String printCalForYear(String args) {
		String[] params = Utility.stringToArray(args);
		try {
			int year = Integer.parseInt(params[0]);
			if (!Utility.isValidYear(year)) {
				return null;
			}
			
			return CalendarHelper.generateCalendarForYear(year, false);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns the string to print the calendar for specified year with Monday
	 * as the first day of the week
	 * @param args String containing command and arguments
	 */
	@Override
	public String printCalForYearMondayFirst(String args) {
		String[] params = Utility.stringToArray(args);
		try {
			int year = Integer.parseInt(params[1]);
			if (!Utility.isValidYear(year)) {
				return null;
			}
			
			return CalendarHelper.generateCalendarForYear(year, true);
		} catch (Exception e) {
			return null;
		}
	}

}
