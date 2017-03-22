import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class TestBugSortApp {

	static final String FILE_PATH = "BugTestFiles" + File.separator + "sortFiles" + File.separator;
	static final String UNSORTED_FILE = FILE_PATH + "unsorted.txt";
	
	static final String UNSORTED_TEXT = 
			"100" + System.lineSeparator() +
			"A" + System.lineSeparator() +
			"@" + System.lineSeparator() +
			System.lineSeparator() +
			"a" + System.lineSeparator() +
			"-1";
	
	static final String SORTED_TEXT = 
			System.lineSeparator() +
			"-1" +  System.lineSeparator() +
			"@" + System.lineSeparator() +
			"100" +	System.lineSeparator() +	
			"A" + System.lineSeparator() +
			"a";
	
	static final String SORTED_TEXT_N = 
			System.lineSeparator() +
			"@" + System.lineSeparator() +
			"-1" + System.lineSeparator() +
			"100" +	System.lineSeparator() +	 
			"A" + System.lineSeparator() +
			"a";
			
	static final String SORTED_TEXT_MULTIPLE = 
			System.lineSeparator() +
			System.lineSeparator() +
			"-1" +  System.lineSeparator() +
			"-1" +  System.lineSeparator() +
			"@" + System.lineSeparator() +
			"@" + System.lineSeparator() +
			"100" +	System.lineSeparator() +
			"100" +	System.lineSeparator() +	
			"A" + System.lineSeparator() +
			"A" + System.lineSeparator() +
			"a" +
			"a";
	
	static final String UNSORTED_TEXT_NUM = 
			"100" + System.lineSeparator() +
			"1" + System.lineSeparator() +
			"-1" +	System.lineSeparator() +	 
			"2" + System.lineSeparator() +
			"0" + System.lineSeparator() +
			"+0";
	
	static final String SORTED_TEXT_NUM = 
			"+0" + System.lineSeparator() +
			"-1" + System.lineSeparator() +
			"0" +	System.lineSeparator() +	 
			"1" + System.lineSeparator() +
			"2" + System.lineSeparator() +
			"100";
	
	SortApplication sortApp;
	InputStream stdin;
	OutputStream stdout;
	
	@Before
	public void setup(){
		sortApp = new SortApplication();
	}
	
	/*
	 * This bug is due to not sorting in the order of special character, numbers, capital letter, simple letter
	 * pg 12 Seciton 7.2.9 sort of CS4218 Project Description
	 * Line 189 in Utility.java "merge" method, values or character should not be compare directly, 
	 * as some special chars have greate ASCII values as compare to simple letters
	 */
	@Test
	public void testSortAll() throws AbstractApplicationException {
		String expectedResults = SORTED_TEXT;
		String actualResults = sortApp.sortAll(UNSORTED_TEXT);
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * This bug is due to "sortNumber(String toSort)" treating a "special char + number as a number"
	 * e.g. "+0" was treated as a number "0" instead of a special char "+" and number "0"
	 * pg 12 Seciton 7.2.9 sort of CS4218 Project Description
	 * SortApplication.java, line 182 "SortNumbers(String toSort)" methods
	 */
	@Test
	public void testSortNumber() throws AbstractApplicationException {
		String expectedResults = SORTED_TEXT_NUM;
		String actualResults = sortApp.sortNumbers(UNSORTED_TEXT_NUM);
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * This bug is due to "sort -n" not being able to sort a text file which consist of a mixture of words such as,
	 *  special character, numbers, capital letter, simple letter
	 * pg 12 Seciton 7.2.9 sort of CS4218 Project Description
	 *line 190 in SortApplication.java. an error msg invalid number type is throw
	 */
	@Test
	public void testSortWithDashN() throws AbstractApplicationException {
		String args[] = {"-n", UNSORTED_FILE};
		stdout = new ByteArrayOutputStream();
		sortApp.run(args, null, stdout);
		String expectedResults = SORTED_TEXT_N;
		String actualResults = stdout.toString();
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * This bug is due to "sort [FILE] [FILE]" not being able to sort 2 or or more File(s)
	 * pg 12 Seciton 7.2.9 sort of CS4218 Project Description
	 * Definition of FILE - The name of the file(s)
	 * SortApplication.java, line 59 throwing invalid argument
	 */
	@Test
	public void testMultipleFiles() throws AbstractApplicationException {
		String args[] = {UNSORTED_FILE, UNSORTED_FILE};
		stdout = new ByteArrayOutputStream();
		sortApp.run(args, null, stdout);
		String expectedResults = SORTED_TEXT_MULTIPLE;
		String actualResults = stdout.toString();
		assertEquals(expectedResults, actualResults);
	}
	
	@After
	public void teardown(){
		sortApp = null;
	}

}
