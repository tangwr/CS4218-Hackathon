package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class SeqCommandTest {
	private ByteArrayOutputStream outputStream;
	private String cmdLine;
	
	@Before
	public void setUp() {
		outputStream = new ByteArrayOutputStream();
		cmdLine = "";
	}
	
	@Test
	public void testSeqCommandSuccess2Cmds() throws AbstractApplicationException, ShellException {
		cmdLine = "echo 123 ; echo 345";
		SeqCommand sc = new SeqCommand(cmdLine);
		sc.evaluate(null, outputStream);
		assertEquals(outputStream.toString(), "123" + System.lineSeparator() 
			+ "345" + System.lineSeparator());
	}
	
	@Test
	public void testSeqCommandSuccessMoreThan2Cmds() {
		File input = new File("input.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(input));
			String content = "ABC SSS" + System.lineSeparator() + "CS4218";
			bw.write(content);
			bw.close();
			cmdLine = "echo 123 ; echo 345 456 ; cat < input.txt";
			SeqCommand sc = new SeqCommand(cmdLine);
			sc.evaluate(null, outputStream);
			assertEquals(outputStream.toString(), "123" + System.lineSeparator() 
			+ "345 456" + System.lineSeparator() + content + System.lineSeparator());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSeqCommandFailure() throws AbstractApplicationException, ShellException {
		cmdLine = "echo ';'";
		SeqCommand sc = new SeqCommand(cmdLine);
		sc.evaluate(null, outputStream);
		assertEquals(outputStream.toString(), ";" + System.lineSeparator());
	}

}
