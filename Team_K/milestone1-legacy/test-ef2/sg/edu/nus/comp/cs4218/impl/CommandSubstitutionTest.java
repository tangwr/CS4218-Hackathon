package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.CommandSubstitution;

public class CommandSubstitutionTest {
	
	@Test
	public void testPerformCommandSubstitution() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" + System.lineSeparator() 
		+ "Apple is life!" + System.lineSeparator();
		String args = "grep \"Apple\" `cat test-data/testPipe.txt`";
		ShellImplementation shell = new ShellImplementation();
		String output = shell.performCommandSubstitution(args);

		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testEvaluateCommandSubstitution() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" + System.lineSeparator() 
		+ "Apple is life!" + System.lineSeparator();
		String input = "grep \"Apple `cat test-data/testPipe.txt`";
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		CommandSubstitution cs = new CommandSubstitution();
		cs.evaluate(stdin, stdout);
		String output = stdout.toString();

		assertEquals(expectedOutput, output);
	}
	
	@Test(expected = Exception.class)
	public void testPerformCommandSubstitutionWithException() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Exception!!!" ;
		String args = "cat test-data/testPipe1.txt | grep �Apple� | grep carrot";
		ShellImplementation shell = new ShellImplementation();
		String output = shell.performCommandSubstitutionWithException(args);

		assertEquals(expectedOutput, output);
	}
}
