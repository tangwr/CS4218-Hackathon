import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class TestBugWcApp {

	static final String FILE_PATH = "BugTestFiles" + File.separator + "wcFiles" + File.separator;
	static final String WC_FILE = FILE_PATH + "wc.txt";
	
	static final String WC_RESULTS = "65 10 12 " + WC_FILE + System.lineSeparator();
	static final String WC_RESULTS_STDIN = "65 10 12";
	WcApplication wcApp;
	InputStream stdin;
	OutputStream stdout;
	ShellImplementation shellImpl;
	
	@Before
	public void setup(){
		wcApp = new WcApplication();
		shellImpl = new ShellImplementation();
	}
	/*
	 * The bug is due to not handling of option in composite order "-lwm". 
	 * Even though the team had indicated in the readme.txt that they don't support composite order, 
	 * the discussion in the forum stated that it was necessary
	 * As discuss on forum "Responses to clarifications about the project" 08-Feb-2017 3:39 PM
	 * In wc can the options be given in any order? Yes, it can. You should also allow both -lmw and -l -m -w format when providing options.
	 * A stated in "Lab7-Hackathon.pdf" page 7 point number 3 "conforms discussion in the IVLE forum"
	 * expected results "65 10 12 wc.txt"
	 * actual results "error thrown"
	 */
	@Test
	public void testCompositeOrder() throws AbstractApplicationException {
		String args[] = {"-lwm", WC_FILE};
		stdout = new ByteArrayOutputStream();
		wcApp.run(args, null, stdout);
		String expectedResults = WC_RESULTS;
		String actualResults = stdout.toString();
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * The bug is due to not printing in the order given in the project description 
	 * - bytes, words, lines - regardless of the order the options were given.
	 * As discuss on forum "Responses to clarifications about the project" 08-Feb-2017 3:39 PM
	 * In what order should the output of wc be printed if options are given? 
	 * They should be printed in the order given in the project description - bytes, words, lines - regardless of the order the options were given.
	 * A stated in "Lab7-Hackathon.pdf" page 7 point number 3 "conforms discussion in the IVLE forum"
	 * expected results "65 10 12 wc.txt"
	 * actual results "12 10 65 wc.txt"
	 */
	@Test
	public void testDifferentOptionOrder() throws AbstractApplicationException {
		String args[] = {"-l", "-w", "-m", WC_FILE};
		stdout = new ByteArrayOutputStream();
		wcApp.run(args, null, stdout);
		String expectedResults = WC_RESULTS;
		String actualResults = stdout.toString();
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * The bug is output wrong results when using "cat wc.txt | wc"
	 * Error with the passing of data from file to input stream
	 * Extra newline count and byte count are shown in the results
	 * WcApplication.java reading of inputStream
	 *  expected results "65 10 12"
	 * actual results "67 10 13"
	 */
	@Test
	public void testCatPipeWc() throws ShellException, AbstractApplicationException {
		String args[] = {"-l", "-w", "-m", WC_FILE};
		String cmdline = "cat " + WC_FILE + " | wc";
		stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults = WC_RESULTS_STDIN;
		String actualResults = stdout.toString();
		assertEquals(expectedResults, actualResults);
	}

	@After
	public void teardown(){
		wcApp = null;
	}
}
