import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;;

public class TestBugSed {

	SedApplication sedApp;
	
	@Before
	public void setup(){
		sedApp = new SedApplication();
	}

	@Test
	public void replaceDivisionCharacterToLetterHFromInputStream() throws AbstractApplicationException
	{
		InputStream stdin = new ByteArrayInputStream("/ello".getBytes());
		String[] args = {"s///H/"};
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		sedApp.run(args, stdin, output);
		
		String expected = "Hello" + System.lineSeparator();
		String actual = output.toString();
		
		assertEquals(expected, actual);
	}
}
