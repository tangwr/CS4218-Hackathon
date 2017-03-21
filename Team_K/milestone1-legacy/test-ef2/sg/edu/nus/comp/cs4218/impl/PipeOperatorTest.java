package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.PipeOperator;

public class PipeOperatorTest {

	@Test
	public void testPipeTwoCommands() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" + System.lineSeparator() 
		+ "Apple is life!" + System.lineSeparator();
		String args = "cat test-data/testPipe.txt | grep “Apple”";
		ShellImplementation shell = new ShellImplementation();
		String output = shell.pipeTwoCommands(args);

		assertEquals(expectedOutput, output);
	}

	@Test
	public void testPipeMultipleCommands() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" + System.lineSeparator();
		String args = "cat test-data/testPipe.txt | grep “Apple” | grep carrot";
		ShellImplementation shell = new ShellImplementation();
		String output = shell.pipeMultipleCommands(args);

		assertEquals(expectedOutput, output);
	}

	@Test
	public void testEvaluateTwoCommands() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" + System.lineSeparator() 
		+ "Apple is life!" + System.lineSeparator();
		String input = "cat test-data/testPipe.txt | grep “Apple”";
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		PipeOperator po = new PipeOperator();
		po.evaluate(stdin, stdout);
		String output = stdout.toString();

		assertEquals(expectedOutput, output);
	}

	@Test
	public void testEvaluateMultipleCommands() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Apple beetroot carrot!" + System.lineSeparator();
		String input = "cat test-data/testPipe.txt | grep “Apple” | grep carrot";
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		PipeOperator po = new PipeOperator();
		po.evaluate(stdin, stdout);
		String output = stdout.toString();

		assertEquals(expectedOutput, output);
	}

	@Test(expected = Exception.class)
	public void testPipeWithException() throws AbstractApplicationException, ShellException {
		String expectedOutput = "Exception!!!" ;
		String args = "cat test-data/testPipe1.txt | grep “Apple” | grep carrot";
		ShellImplementation shell = new ShellImplementation();
		String output = shell.pipeWithException(args);

		assertEquals(expectedOutput, output);
	}

}
