package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;


public class PwdWithGrepCommandTest {

	@Test
	public void testPwdWithGrepSucess()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		String currentDir = System.getProperty("user.dir");
		String testString = currentDir.substring(0,1);
		shImpl.parseAndEvaluate("pwd | grep " + testString, outStream);
		String expected = System.getProperty("user.dir") + System.lineSeparator();
		
		assertEquals(expected, outStream.toString());
	}

	@Test(expected = GrepException.class)
	public void testPwdWithGrepFailing() throws AbstractApplicationException,
			ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("grep | pwd", outStream);
	}
}
