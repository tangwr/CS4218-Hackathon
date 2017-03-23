import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class TestBugGlobbing {

	static ShellImplementation shellImpl;
	static ByteArrayOutputStream output;
	private static final String NEW_LINE = System.lineSeparator();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shellImpl = new ShellImplementation();
		sg.edu.nus.comp.cs4218.Environment.setCurrentDirectory(System.getProperty("user.dir")); 
	}
	
	
//	  The bug is due to not supporting the globbing in the folder name. The program only support
//	  globbing in file name but not folder name.
//	  For example, "cat BugTestFiles/globFiles/testingFolder/te*.txt" is working but 
//	  "cat BugTestFiles/globFiles/testing*/test.txt" is not supported.
	 
	@Test
	public void testBugGlobbingFolderName() throws AbstractApplicationException, ShellException {
		String input = "cat BugTestFiles/globFiles/testing*/test.txt";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "test 2 test 2 test 2"+NEW_LINE, actual;
		
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
