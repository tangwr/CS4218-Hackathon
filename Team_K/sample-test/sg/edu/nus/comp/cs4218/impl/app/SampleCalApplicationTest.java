package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.impl.app.CalApplication;

/* Assumption: 
 * 1) run function will call the correct functions with the correct inputs in the correct order separated by a space
 * 2) Assume year starts from 0 (>= 0)
 * 3) run function will take inputs directly from shell unordered
 * 4) Args for run: unordered consisting of -m, month and year
 * 5) Args for printCall: null
 * 6) Args for printCalWithMondayFirst: -m
 * 7) Args for printCalForYear: year
 * 8) Args for printCalForYearMondayFirst: -m year
 * 9) Args for printCalForMonthYear: month year
 * 10) Args for printCalForMonthYearMondayFirst: -m month year
 */

public class SampleCalApplicationTest {

	private static final String MARCHSUNOUTPUT = "     March 2017     \nSu Mo Tu We Th Fr Sa \n         1  2  3  4  "
			+ "\n5  6  7  8  9  10 11 \n12 13 14 15 16 17 18 \n19 20 21 22 23 24 25 \n26 27 28 29 30 31    \n                     ";
	private static final String SUNFIRSTWEEK = "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa\n";
	private static final String MONFIRSTWEEK = "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su\n";
	private static final String MARCH2017MON = "     March 2017     \nMo Tu We Th Fr Sa Su \n"
			+ "      1  2  3  4  5  \n6  7  8  9  10 11 12 \n13 14 15 16 17 18 19 \n20 21 22 23 24 25 26 \n27 28 29 30 31       \n                     \n";
	private static final String _2017CALMON = 
			"                              2017\n" +
			"      January                      February                       March       \n" +
			"Mo Tu We Th Fr Sa Su         Mo Tu We Th Fr Sa Su         Mo Tu We Th Fr Sa Su \n" +
			"                  1                1  2  3  4  5                1  2  3  4  5  \n" +
			"2  3  4  5  6  7  8          6  7  8  9  10 11 12         6  7  8  9  10 11 12 \n" +
			"9  10 11 12 13 14 15         13 14 15 16 17 18 19         13 14 15 16 17 18 19 \n" +
			"16 17 18 19 20 21 22         20 21 22 23 24 25 26         20 21 22 23 24 25 26 \n" +
			"23 24 25 26 27 28 29         27 28                        27 28 29 30 31       \n" +
	 		"30 31                                                                          \n\n" +
	        "       April                         May                          June        \n" +
	        "Mo Tu We Th Fr Sa Su         Mo Tu We Th Fr Sa Su         Mo Tu We Th Fr Sa Su \n" +
	        "               1  2          1  2  3  4  5  6  7                   1  2  3  4  \n" +
	        "3  4  5  6  7  8  9          8  9  10 11 12 13 14         5  6  7  8  9  10 11 \n" +
	        "10 11 12 13 14 15 16         15 16 17 18 19 20 21         12 13 14 15 16 17 18 \n" +
	        "17 18 19 20 21 22 23         22 23 24 25 26 27 28         19 20 21 22 23 24 25 \n" +
	        "24 25 26 27 28 29 30         29 30 31                     26 27 28 29 30       \n" +
	        "                                                                               \n\n" +
	        "        July                         August                      September     \n" +
	        "Mo Tu We Th Fr Sa Su         Mo Tu We Th Fr Sa Su         Mo Tu We Th Fr Sa Su \n" +
	        "               1  2             1  2  3  4  5  6                      1  2  3  \n" +
	        "3  4  5  6  7  8  9          7  8  9  10 11 12 13         4  5  6  7  8  9  10 \n" +
	        "10 11 12 13 14 15 16         14 15 16 17 18 19 20         11 12 13 14 15 16 17 \n" +
	        "17 18 19 20 21 22 23         21 22 23 24 25 26 27         18 19 20 21 22 23 24 \n" +
	        "24 25 26 27 28 29 30         28 29 30 31                  25 26 27 28 29 30    \n" +
	        "31                                                                             \n\n" +
	        "      October                      November                      December      \n" +
	        "Mo Tu We Th Fr Sa Su         Mo Tu We Th Fr Sa Su         Mo Tu We Th Fr Sa Su \n" +
	        "                  1                1  2  3  4  5                      1  2  3  \n" +
	        "2  3  4  5  6  7  8          6  7  8  9  10 11 12         4  5  6  7  8  9  10 \n" +
	        "9  10 11 12 13 14 15         13 14 15 16 17 18 19         11 12 13 14 15 16 17 \n" +
	        "16 17 18 19 20 21 22         20 21 22 23 24 25 26         18 19 20 21 22 23 24 \n" +
	        "23 24 25 26 27 28 29         27 28 29 30                  25 26 27 28 29 30 31 \n" +
	        "30 31                                                                          \n\n";
	
	private static final String _2017CAL =
			"                              2017\n" +
			"      January                      February                       March       \n" +
			"Su Mo Tu We Th Fr Sa         Su Mo Tu We Th Fr Sa         Su Mo Tu We Th Fr Sa \n" +
			"1  2  3  4  5  6  7                   1  2  3  4                   1  2  3  4  \n" +
			"8  9  10 11 12 13 14         5  6  7  8  9  10 11         5  6  7  8  9  10 11 \n" +
			"15 16 17 18 19 20 21         12 13 14 15 16 17 18         12 13 14 15 16 17 18 \n" +
			"22 23 24 25 26 27 28         19 20 21 22 23 24 25         19 20 21 22 23 24 25 \n" +
			"29 30 31                     26 27 28                     26 27 28 29 30 31    \n" +
	 		"                                                                               \n\n" +
	        "       April                         May                          June        \n" +
	        "Su Mo Tu We Th Fr Sa         Su Mo Tu We Th Fr Sa         Su Mo Tu We Th Fr Sa \n" +
	        "                  1             1  2  3  4  5  6                      1  2  3  \n" +
	        "2  3  4  5  6  7  8          7  8  9  10 11 12 13         4  5  6  7  8  9  10 \n" +
	        "9  10 11 12 13 14 15         14 15 16 17 18 19 20         11 12 13 14 15 16 17 \n" +
	        "16 17 18 19 20 21 22         21 22 23 24 25 26 27         18 19 20 21 22 23 24 \n" +
	        "23 24 25 26 27 28 29         28 29 30 31                  25 26 27 28 29 30    \n" +
	        "30                                                                             \n\n" +
	        "        July                         August                      September     \n" +
	        "Su Mo Tu We Th Fr Sa         Su Mo Tu We Th Fr Sa         Su Mo Tu We Th Fr Sa \n" +
	        "                  1                1  2  3  4  5                         1  2  \n" +
	        "2  3  4  5  6  7  8          6  7  8  9  10 11 12         3  4  5  6  7  8  9  \n" +
	        "9  10 11 12 13 14 15         13 14 15 16 17 18 19         10 11 12 13 14 15 16 \n" +
	        "16 17 18 19 20 21 22         20 21 22 23 24 25 26         17 18 19 20 21 22 23 \n" +
	        "23 24 25 26 27 28 29         27 28 29 30 31               24 25 26 27 28 29 30 \n" +
	        "30 31                                                                          \n\n" +
	        "      October                      November                      December      \n" +
	        "Su Mo Tu We Th Fr Sa         Su Mo Tu We Th Fr Sa         Su Mo Tu We Th Fr Sa \n" +
	        "1  2  3  4  5  6  7                   1  2  3  4                         1  2  \n" +
	        "8  9  10 11 12 13 14         5  6  7  8  9  10 11         3  4  5  6  7  8  9  \n" +
	        "15 16 17 18 19 20 21         12 13 14 15 16 17 18         10 11 12 13 14 15 16 \n" +
	        "22 23 24 25 26 27 28         19 20 21 22 23 24 25         17 18 19 20 21 22 23 \n" +
	        "29 30 31                     26 27 28 29 30               24 25 26 27 28 29 30 \n" +
	        "                                                          31                   \n\n";

	private static final String _2017 = "2017";
	private CalApplication calendarApp;
	private String[] args;
	ByteArrayOutputStream baos;
	PrintStream print;

	@Before
	public void setUp() {
		calendarApp = new CalApplication();
		baos = new ByteArrayOutputStream();
		print = new PrintStream(baos);
		System.setOut(print);
	}

	@Test
	public void printCalendarWithoutInputsFromRun() throws CalException {
		args = new String[0];
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(
				"     March 2017     \nSu Mo Tu We Th Fr Sa \n"
						+ "         1  2  3  4  \n5  6  7  8  9  10 11 \n12 13 14 15 16 17 18 \n19 20 21 22 23 24 25 \n26 27 28 29 30 31    \n                     \n",
				baos.toString());
	}

	// Needs to change every month as it prints the current month values
	@Test
	public void printCalendarWithoutInputs() {
		assertEquals(
				"     March 2017     \nSu Mo Tu We Th Fr Sa \n"
						+ "         1  2  3  4  \n5  6  7  8  9  10 11 \n12 13 14 15 16 17 18 \n19 20 21 22 23 24 25 \n26 27 28 29 30 31    \n                     \n",
				calendarApp.printCal(null));
	}

	@Test
	public void printCalendarMondayWithoutInputsFromRun() throws CalException {
		args = new String[1];
		args[0] = "-m";
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(
				"     March 2017     \nMo Tu We Th Fr Sa Su \n      1  2  3  4  5  \n"
						+ "6  7  8  9  10 11 12 \n13 14 15 16 17 18 19 \n20 21 22 23 24 25 26 \n27 28 29 30 31       \n                     \n",
				baos.toString());
	}

	// Needs to change every month as it prints the current month values
	@Test
	public void printCalendarMondayWithoutInputs() {
		args = new String[1];
		args[0] = "-m";
		assertEquals(
				"     March 2017     \nMo Tu We Th Fr Sa Su \n      1  2  3  4  5  \n"
						+ "6  7  8  9  10 11 12 \n13 14 15 16 17 18 19 \n20 21 22 23 24 25 26 \n27 28 29 30 31       \n                     \n",
				calendarApp.printCalWithMondayFirst(args[0]));
	}

	@Test
	public void printCalendarMonthYearFromRun() throws CalException {
		args = new String[2];
		args[0] = "03";
		args[1] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCHSUNOUTPUT + "\n", baos.toString());
	}

	@Test
	public void printCalendarMonthYearFromRunSingleDigit() throws CalException {
		args = new String[2];
		args[0] = "3";
		args[1] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCHSUNOUTPUT + "\n", baos.toString());
	}

	// run method will add 0 in front of the month
	@Test
	public void printCalendarMonthYear() {
		args = new String[2];
		args[0] = "03";
		args[1] = _2017;
		assertEquals(MARCHSUNOUTPUT + "\n", calendarApp.printCalForMonthYear(args[0] + " " + args[1]));
	}

	@Test
	public void printCalendarMonthYearMondayFromRun() throws CalException {
		args = new String[3];
		args[0] = "-m";
		args[1] = "03";
		args[2] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCH2017MON, baos.toString());
	}

	@Test
	public void printCalendarMonthYearMondayFromRunSingleDigit() throws CalException {
		args = new String[3];
		args[0] = "-m";
		args[1] = "3";
		args[2] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCH2017MON, baos.toString());
	}

	@Test
	public void printCalendarMonthYearMonday() {
		args = new String[3];
		args[0] = "-m";
		args[1] = "03";
		args[2] = _2017;
		assertEquals(MARCH2017MON,
				calendarApp.printCalForMonthYearMondayFirst(args[0] + " " + args[1] + " " + args[2]));
	}

	@Test
	public void printCalendarYearFromRun() throws CalException {
		args = new String[1];
		args[0] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(_2017CAL, baos.toString());
	}

	@Test
	public void printCalendarYear() {
		args = new String[1];
		args[0] = _2017;
		assertEquals(_2017CAL, calendarApp.printCalForYear(args[0]));
	}

	@Test
	public void printCalendarYearMondayFromRun() throws CalException {
		args = new String[2];
		args[0] = "-m";
		args[1] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(_2017CALMON, baos.toString());
	}

	@Test
	public void printCalendarYearMonday() {
		args = new String[2];
		args[0] = "-m";
		args[1] = _2017;
		assertEquals(_2017CALMON, calendarApp.printCalForYearMondayFirst(args[0] + " " + args[1]));
	}

	@Test(expected = CalException.class)
	public void multipleMFromRun() throws CalException {
		args = new String[2];
		args[0] = "-m";
		args[1] = "-m";
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(
				"   February 2017\nSu Mo Tu We Th Fr Sa\n"
						+ "         1  2  3  4 \n5  6  7  8  9  10 11\n12 13 14 15 16 17 18\n19 20 21 22 23 24 25\n26 27 28 ",
				baos.toString());
	}

	@Test(expected = CalException.class)
	public void multipleYearFromRun() throws CalException {
		args = new String[2];
		args[0] = "1991";
		args[1] = "2007";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void multipleMonthFromRun() throws CalException {
		args = new String[3];
		args[0] = "11";
		args[1] = "12";
		args[2] = "2017";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void multipleMonthStringFromRun() throws CalException {
		args = new String[2];
		args[0] = "may";
		args[1] = "april";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void invalidMonthFromRun() throws CalException {
		args = new String[2];
		args[0] = "0";
		args[1] = "2017";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void invalidYearFromRun() throws CalException {
		args = new String[1];
		args[0] = "-10000";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void invalidYear1FromRun() throws CalException {
		args = new String[1];
		args[0] = "-300";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void unorderedFromRun() throws CalException {
		args = new String[3];
		args[0] = _2017;
		args[1] = "-m";
		args[2] = "03";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}
	
	@Test(expected = CalException.class)
	public void printCalendarMonthNameYearFromRun() throws CalException {
		args = new String[2];
		args[0] = "march";
		args[1] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCHSUNOUTPUT + "\n", baos.toString());
	}

}
