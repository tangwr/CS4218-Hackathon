package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class HeadWithGrepWithSedCommandTest {

	@Test
	public void testHeadWithGrepAndSedSucess() throws AbstractApplicationException,
			ShellException {
		Shell shell = new ShellImplementation();
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		shell.parseAndEvaluate(
				"head -n 1 test-data/sample.txt | grep \"9000\" | sed s/over/lower/", bao);
		String expected = "The power is lower 9000 !" + System.lineSeparator();
		assertEquals(expected, bao.toString());
	}

	@Test(expected = HeadException.class)
	public void testHeadWithGrepAndSedFail() throws AbstractApplicationException,
			ShellException {
		Shell shell = new ShellImplementation();
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		shell.parseAndEvaluate(
				"head -n 1 haha.txt | grep \"9000\" | sed s/over/lower/", bao);
	}

	@Test
	public void testHeadAndGrepAndSedSuccess2() throws AbstractApplicationException,
			ShellException {
		Shell shell = new ShellImplementation();
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		shell.parseAndEvaluate(
				"head -n 1 test-data/sample.txt | grep \"nonsense\" | sed s/over/lower/",
				bao);
		assertEquals(System.lineSeparator(), bao.toString());
	}
}
