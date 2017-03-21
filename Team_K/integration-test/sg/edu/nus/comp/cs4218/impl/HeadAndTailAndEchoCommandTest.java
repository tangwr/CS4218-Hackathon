package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class HeadAndTailAndEchoCommandTest {

	@Test
	public void testHeadAndTailAndEchoAlongWithParser()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate(
				"echo `head -n 1 test-data/SubCommandTestFiles/SubCommand.txt` `tail -n 1 test-data/SubCommandTestFiles/SubCommand2.txt`",
				outStream);
		String expected = "This file meant for the usage of grep with sub commands. "
				+ "Its tests the usage of various commands."
				+ System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}

	@Test(expected = HeadException.class)
	public void testHeadAndTailAndEchoFailing()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("echo `head` `tail -n 1 test-data/SubCommandTestFiles/SubCommand2.txt`",
				outStream);
	}

}
