package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SedException;

public class SedApplicationTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@Test
	public void testSedUsingNullArgs() throws AbstractApplicationException {
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Null Pointer Exception");
		SedApplication sedApp = new SedApplication();
		sedApp.run(null, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testSedUsingNullStdout() throws AbstractApplicationException {
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Null Pointer Exception");
		SedApplication sedApp = new SedApplication();
		sedApp.run(new String[]{"/s/123/345/"}, null, null);
	}
	
	@Test
	public void testSedUsingNullStdin() throws AbstractApplicationException {
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Null Stdin");
		SedApplication sedApp = new SedApplication();
		sedApp.run(new String[]{"s/123/345/"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testSedUsingWrongArgs() throws AbstractApplicationException {
		SedApplication sedApp = new SedApplication();
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Too few arguments");
		sedApp.run(new String[0], null, new ByteArrayOutputStream());
		
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Too many arguments");
		sedApp.run(new String[3], null, new ByteArrayOutputStream());
	
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Invalid replacement");
		sedApp.run(new String[]{"abc"}, null, new ByteArrayOutputStream());
		
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Replacement must start with \"s\"");
		sedApp.run(new String[]{"d/abc/sd/"}, null, new ByteArrayOutputStream());
		
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Invalid number of divisions");
		sedApp.run(new String[]{"d/abc/sd"}, null, new ByteArrayOutputStream());
		
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Invalid number of divisions");
		sedApp.run(new String[]{"d/abc/sd/ds/"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testSedUsingNonExistentFile() throws AbstractApplicationException {
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: abc.txt is not existed");
		SedApplication sedApp = new SedApplication();
		sedApp.run(new String[]{"s/abc/xzy/", "abc.txt"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testSedUsingInvalidRegexp() throws AbstractApplicationException {
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Invalid Regex To Replace: *");
		SedApplication sedApp = new SedApplication();
		sedApp.run(new String[]{"s/*/xzy/", "abc.txt"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testSedUsingInvalidReplacement() throws AbstractApplicationException {
		expectedEx.expect(SedException.class);
		expectedEx.expectMessage("sed: Invalid Replacement Expression: *");
		SedApplication sedApp = new SedApplication();
		sedApp.run(new String[]{"s/abc/*/", "abc.txt"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testReplaceFirstSubStringInFile() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Apple is love!"+ System.lineSeparator();
		String replacement = "s/Apple/Microsoft/";
		String filePath = "test-data"+File.separator+"testSed.txt";
		String[] args = new String[] { replacement, filePath};
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);
		String output = stdout.toString();
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testReplaceAllSubstringsInFile() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Microsoft is love!" + System.lineSeparator();
		String replacement = "s/Apple/Microsoft/g";
		String filePath = "test-data"+File.separator+"testSed.txt";
		String[] args = new String[] { replacement, filePath};
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);
		String output = stdout.toString();
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdin() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Apple is love!" + System.lineSeparator();
		String replacement = "s/Apple/Microsoft/";
		String input = "Apple beetroot carrot! Apple is love!";
		String[] args = new String[] { replacement };
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);
		String output = stdout.toString();
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testReplaceAllSubStringInStdin() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Microsoft is love!" + System.lineSeparator();
		String replacement = "s/Apple/Microsoft/g";
		String[] args = new String[] { replacement };
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream("Apple beetroot carrot! Apple is love!".getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);
		String output = stdout.toString();
		assertEquals(expectedOutput, output);
	}
	
	@Test(expected = Exception.class)
	public void testReplaceFirstSubStringWithInvalidReplacement() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Apple is love!";
		String replacement = "s/Apple/Mic/rosoft";
		String input = "Apple beetroot carrot! Apple is love!";
		String[] args = new String[] { replacement };
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);
		String output = stdout.toString();
		assertEquals(expectedOutput, output);
	}
	
	@Test(expected = Exception.class)
	public void testReplaceFirstSubStringWithInvalidRegex() throws AbstractApplicationException {
		String expectedOutput = "Microsoft beetroot carrot! Apple is love!";
		String replacement = "s/App/le/Microsoft";
		String input = "Apple beetroot carrot! Apple is love!";
		String[] args = new String[] { replacement };
		
		SedApplication sedApp = new SedApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		
		sedApp.run(args, stdin, stdout);
		String output = stdout.toString();
		assertEquals(expectedOutput, output);
	}
}
