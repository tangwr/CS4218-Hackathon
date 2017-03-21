package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CalApplicationTest {
	
	@Test
	public void testPrintCal() {
		// current is March 2017
		String expectedOuput = 
				   "     March 2017     \n" +
				   "Su Mo Tu We Th Fr Sa \n" +
				   "         1  2  3  4  \n" +
				   "5  6  7  8  9  10 11 \n" +
				   "12 13 14 15 16 17 18 \n" +
				   "19 20 21 22 23 24 25 \n" +
				   "26 27 28 29 30 31    \n" +
				   "                     \n";
		CalApplication calApplication = new CalApplication();
		String output = calApplication.printCal("");
		assertEquals(expectedOuput, output);
	}
	
	@Test
	public void testPrintCalMondayFirst() {
		// current is March 2017
		String expectedOuput = 
				   "     March 2017     \n" +
	   			   "Mo Tu We Th Fr Sa Su \n" +
	   			   "      1  2  3  4  5  \n" +
	   			   "6  7  8  9  10 11 12 \n" +
	   			   "13 14 15 16 17 18 19 \n" +
	   			   "20 21 22 23 24 25 26 \n" +
	   			   "27 28 29 30 31       \n" +
	   			   "                     \n";

		CalApplication calApplication = new CalApplication();
		String output = calApplication.printCalWithMondayFirst("-m");
		assertEquals(expectedOuput, output);
	}
	
	@Test
	public void testPrintCalForMonthYear() {
		String expectedOuput = "   February 2017   \n" +
							   "Su Mo Tu We Th Fr Sa \n" +
							   "         1  2  3  4  \n" +
							   "5  6  7  8  9  10 11 \n" +
							   "12 13 14 15 16 17 18 \n" +
							   "19 20 21 22 23 24 25 \n" +
							   "26 27 28             \n" +
							   "                     \n";
		
		CalApplication calApplication = new CalApplication();
		String output = calApplication.printCalForMonthYear("02 2017");
		assertEquals(expectedOuput, output);
	}
	
	@Test
	public void testPrintCalForMonthYearMondayFirst() {
		String expectedOuput = "   February 2017   \n" +
				   			   "Mo Tu We Th Fr Sa Su \n" +
				   			   "      1  2  3  4  5  \n" +
				   			   "6  7  8  9  10 11 12 \n" +
				   			   "13 14 15 16 17 18 19 \n" +
				   			   "20 21 22 23 24 25 26 \n" +
				   			   "27 28                \n" +
				   			   "                     \n";

		CalApplication calApplication = new CalApplication();
		String output = calApplication.printCalForMonthYearMondayFirst("-m 02 2017");
		assertEquals(expectedOuput, output);
	}
	
	@Test
	public void testPrintCalForYear() {
		String expectedOutput =
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
		
		CalApplication calApplication = new CalApplication();
		String output = calApplication.printCalForYear("2017");
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testPrintCalForYearMondayFirst() {
		String expectedOutput =
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

		CalApplication calApplication = new CalApplication();
		String output = calApplication.printCalForYearMondayFirst("-m 2017");
		assertEquals(expectedOutput, output);
	}
}

