import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class TestBugEcho {

	EchoApplication echoApp;
	ShellImplementation si;
	@Before
	public void setup(){
		echoApp = new EchoApplication();
		si = new ShellImplementation();
	}

	/*
	 * This bug is due to not Globbing taking effect. For echo, globbing should not be implemented
	 * and hence '*' should be printed as a literal.
	 */
	
	@Test
	public void echoGlobbing() throws AbstractApplicationException, ShellException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		si.parseAndEvaluate("echo r*", output);
		
		String expected = "r*" + System.lineSeparator();
		String actual = output.toString();
		
		assertEquals(expected, actual);
	}
}
