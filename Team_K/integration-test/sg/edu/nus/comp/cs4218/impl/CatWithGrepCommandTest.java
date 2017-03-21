package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;
import sg.edu.nus.comp.cs4218.impl.app.*;


public class CatWithGrepCommandTest {

	Application grepApp;
	Application catApp;
	String[] catArgs;
	String[] grepArgs;

	// Command under test: cat PipeCommandTestFiles/GrepWithPipeCommand.txt |
	// grep 'usage'

	@Before
	public void setUp() throws Exception {
		grepApp = new GrepApplication();
		catApp = new CatApplication();
	}

	
	/*
	 * This test considers the applications atomically
	 */
	@Test
	public void testGrepWithCatDirectly() throws AbstractApplicationException {
		// Build arguments for cat
		catArgs = new String[] { "test-data/testPipe1.txt" };
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// Run
		catApp = new CatApplication();
		catApp.run(catArgs, null, outStream);

		// Convert cat output to byte array
		byte[] catOutput = outStream.toByteArray();
		outStream.reset();

		// Run Grep
		ByteArrayInputStream inStream = new ByteArrayInputStream(catOutput);
		grepArgs = new String[] { "usage" };
		grepApp = new GrepApplication();
		grepApp.run(grepArgs, inStream, outStream);
		String expected = " This file meant for the usage of grep with sub commands."
				+ System.lineSeparator()
				+ "This is the second usage of the word."
				+ System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}

	/*
	 * This test integrates the parsing component as well
	 */
	@Test
	public void testGrepWithCatAlongWithParser()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate(
				"cat test-data/testPipe1.txt | grep 'usage'",
				outStream);
		String expected = " This file meant for the usage of grep with sub commands."
				+ System.lineSeparator()
				+ "This is the second usage of the word."
				+ System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}

	/*
	 * Negative test: Cat application throws exception when file does not exist
	 */
	@Test(expected = Exception.class)
	public void testGrepWithCatFailing() throws AbstractApplicationException,
			ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("cat a.txt | grep 'usage'", outStream);
	}
	
	@Test
	public void testPipeTwoCommands() throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		String expectedOutput = "Apple beetroot carrot!" + System.lineSeparator() 
		+ "Apple is life!" + System.lineSeparator();
		ShellImplementation shell = new ShellImplementation();
		shell.parseAndEvaluate("cat test-data/testPipe.txt | grep 'Apple'", outStream);

		assertEquals(expectedOutput, outStream.toString());
	}

	@Test
	public void testPipeMultipleCommands() throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		String expectedOutput = "Apple beetroot carrot!" + System.lineSeparator();
		ShellImplementation shell = new ShellImplementation();
		shell.parseAndEvaluate("cat test-data/testPipe.txt | grep 'Apple' | grep carrot", outStream);

		assertEquals(expectedOutput, outStream.toString());
	}

	@Test(expected = Exception.class)
	public void testPipeWithException() throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();		
		ShellImplementation shell = new ShellImplementation();
		shell.parseAndEvaluate("cat test-data/testPipeNotExist.txt | grep 'Apple' | grep carrot", outStream);
	}
}
