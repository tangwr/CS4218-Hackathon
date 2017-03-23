import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class TestBugPipe {
	static ShellImplementation shellImpl;
	static ByteArrayOutputStream output;
	private static final String NEW_LINE = System.lineSeparator();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shellImpl = new ShellImplementation();
		sg.edu.nus.comp.cs4218.Environment.setCurrentDirectory(System.getProperty("user.dir")); 
	}
	
	/*
	 * The bug is due to not supporting the command without space at the pipe operator
	 * For example, echo test|echo test1 and echo test| echo test1 will print "test| echo test1"
	 * but not "test1"
	 */
	@Test
	public void testBugPipeTwoCommandsWithoutSpace() throws AbstractApplicationException, ShellException {
		String input = "echo test| echo test1";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "test1"+NEW_LINE, actual;
		
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		actual = output.toString();
		assertEquals(expected, actual);		
	}
	
	
}
