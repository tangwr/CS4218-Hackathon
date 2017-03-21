package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class TailWithGrepAndSedCommandTest {
	@Test
	public void testTailAndGrepAndSedSuccess() throws AbstractApplicationException,
			ShellException {
		Shell shell = new ShellImplementation();
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		shell.parseAndEvaluate(
				"tail -n 1 test-data/sample.txt | grep \"brown\" | sed s/brown/black/",
				bao);
		String expected = "The quick black fox jumps over the lazy dog"
				+ System.lineSeparator();
		assertEquals(expected, bao.toString());
	}

	

	@Test(expected = ShellException.class)
	public void testTailAndGrepAndSedUsingWrongIOFile() throws AbstractApplicationException,
			ShellException {
		Shell shell = new ShellImplementation();
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		shell.parseAndEvaluate(
				"tail -n 1 < haha.txt | grep \"brown\" | sed s/over/lower", bao);
	}
}
