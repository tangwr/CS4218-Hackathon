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

public class EchoWithGrepCommandTest {

	Application echoApp;
	Application grepApp;
	String[] echoArgs;
	String[] grepArgs;

	// Command under test: echo Apple Beetroot Carrot | grep 'Apple'

	@Before
	public void setUp() throws Exception {
		echoApp = new EchoApplication();
		grepApp = new GrepApplication();
	}

	/*
	 * This test considers the applications atomically
	 */
	@Test
	public void testEchoWithGrepDirectly() throws AbstractApplicationException {
		echoArgs = new String[] { "Apple Beetroot Carrot" };
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		echoApp = new EchoApplication();
		echoApp.run(echoArgs, null, outStream);

		grepArgs = new String[] { "Apple" };

		byte[] echoOutput = outStream.toByteArray();
		ByteArrayInputStream inStream = new ByteArrayInputStream(echoOutput);

		outStream.reset();
		grepApp = new GrepApplication();
		grepApp.run(grepArgs, inStream, outStream);

		String expected = "Apple Beetroot Carrot" + System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}

	/*
	 * This test integrates the parsing component as well
	 */
	@Test
	public void testEchoWithGrepAlongWithParser()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("echo Apple Beetroot Carrot | grep 'Apple'",
				outStream);
		String expected = "Apple Beetroot Carrot" + System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}

	/*
	 * Negative test: Grep application throws exception because echo returns
	 * empty string
	 */
	@Test(expected = Exception.class)
	public void testEchoWithGrepFailing() throws AbstractApplicationException,
			ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("echo | grep", outStream);
		assertEquals("", outStream.toString());

	}
}
