package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;


import java.io.ByteArrayOutputStream;

import org.junit.Test;


import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class GrepWithHeadCommandTest {
	@Test
	public void testGrepWithHead()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate(
				"echo `head -n 1 test-data/SubstituteCommandTestFiles/GrepWithSubCommand.txt` | grep \"usage\"",
				outStream);
		String expected = "This file meant for the usage of grep with sub commands."
				+ System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}

	@Test(expected = HeadException.class)
	public void testGrepWithHeadFail() throws AbstractApplicationException,
			ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("head a.txt | grep \"usage\"", outStream);
	}
}
