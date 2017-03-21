package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.TailException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;


public class GrepWithTailCommandTest {
	@Test
	public void testGrepWithTailAlongWithParser()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate(
				"echo `tail -n 2 test-data/SubstituteCommandTestFiles/GrepWithSubCommand.txt` | grep \"usage\"",
				outStream);
		String expected = "This is the second usage of the word."
				+ System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}

	/*
	 * Negative test: Tail applitailion throws exception because a.txt does not
	 * exist
	 */
	@Test(expected = TailException.class)
	public void testGrepWithTailFailing() throws AbstractApplicationException,
			ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("tail a.txt | grep \"usage\"", outStream);
	}
}
