package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class NestedPipeTest {

	@Test
	public void testHeadGrep() throws AbstractApplicationException, 
	ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate(
				"cat test-data/testPipe2.txt | head -n 3 | grep 'apple'",
				outStream);
		String expected = "apple" + System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}
	
	@Test
	public void testTailGrep() throws AbstractApplicationException, 
	ShellException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		ShellImplementation shImpl = new ShellImplementation();
		shImpl.parseAndEvaluate(
				"cat test-data/testPipe2.txt | tail -n 3 | grep '1'",
				outStream);
		String expected = "12"
				+ System.lineSeparator()
				+ "1" + System.lineSeparator();
		assertEquals(expected, outStream.toString());
	}
}
