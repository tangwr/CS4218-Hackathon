package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;



public class EchoWithWcWithPwdCommandTest {
	@Test
	public void testEchoAndWcAndPwdAlongWithParser()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("echo `wc test-data/SubCommandTestFiles/SubCommand.txt` `pwd`", outStream);
		String file = "test-data/SubCommandTestFiles/SubCommand.txt";
		long byteCount = new File(file).length();
		String expected = byteCount+" 19 3 test-data/SubCommandTestFiles/SubCommand.txt "
				+ System.getProperty("user.dir") + System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}


	@Test(expected = WcException.class)
	public void testEchoAndWcAndPwdFailing()
			throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate("echo `wc` `pwd`", outStream);
	}

}
