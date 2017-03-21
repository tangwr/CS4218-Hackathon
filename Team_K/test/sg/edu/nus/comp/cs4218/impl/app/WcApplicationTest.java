package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.WcException;

public class WcApplicationTest {
	
	private int getByteCount(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
		
			StringBuilder builder = new StringBuilder();
			int currentChar = reader.read();
			while (currentChar != -1) {
				builder.append((char) currentChar);
				currentChar = reader.read();
			}
			reader.close();
			return builder.length();
			} catch (IOException e) {
				fail();
			}
		return 0;
	}
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testWcUsingNullArgs() throws AbstractApplicationException {
		expectedEx.expect(WcException.class);
		expectedEx.expectMessage("wc: Null Pointer Exception");
		WcApplication wcApp = new WcApplication();
		wcApp.run(null, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testWcUsingNullOutputStream() throws AbstractApplicationException {
		expectedEx.expect(WcException.class);
		expectedEx.expectMessage("wc: Null Pointer Exception");
		WcApplication wcApp = new WcApplication();
		wcApp.run(new String[0], null, null);
	}
	
	@Test
	public void testWcUsingNullInputStreamAndEmptyArgs() throws AbstractApplicationException {
		expectedEx.expect(WcException.class);
		expectedEx.expectMessage("wc: Null Stdin");
		WcApplication wcApp = new WcApplication();
		wcApp.run(new String[]{"-l"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testWcUsingNonExistentFile() throws AbstractApplicationException{
		expectedEx.expect(WcException.class);
		expectedEx.expectMessage("wc: abc.txt is not existed");
		WcApplication wcApp = new WcApplication();
		wcApp.run(new String[]{"abc.txt"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testWcUsingWrongOption() throws AbstractApplicationException{
		expectedEx.expect(WcException.class);
		expectedEx.expectMessage("wc: -z is not existed");
		WcApplication wcApp = new WcApplication();
		wcApp.run(new String[]{"-z", "abc.txt"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testPrintCharacterCountInFile() throws AbstractApplicationException {
		
		String option = "-m";
		String filePath = "test-data"+File.separator+"testWc.txt";
		int byteCount = getByteCount(filePath);
		String expectedOutput = byteCount+" " + filePath + System.lineSeparator();
		String[] args = new String[] { option, filePath };

		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}

	@Test
	public void testPrintWordCountInFile() throws AbstractApplicationException {
		String option = "-w";
		String filePath = "test-data"+File.separator+"testWc.txt";
		String expectedOutput = "6 " + filePath + System.lineSeparator();
		
		String[] args = new String[] { option, filePath };

		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}

	@Test
	public void testPrintNewlineCountInFile() throws AbstractApplicationException {
		String option = "-l";
		String filePath = "test-data"+File.separator+"testWc.txt";
		String expectedOutput = "1 " + filePath + System.lineSeparator();
		String[] args = new String[] { option, filePath };

		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testPrintAllCountsInFile() throws AbstractApplicationException {		
		String filePath = "test-data"+File.separator+"testWc.txt";
		String[] args = new String[] { filePath };
		int byteCount = getByteCount(filePath);
		String expectedOutput = byteCount + " 6 1 " + filePath + System.lineSeparator();
		
		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}

	@Test
	public void testPrintCharacterCountInStdin() throws AbstractApplicationException {
		String expectedOutput = "37" + System.lineSeparator();
		String input = "Apple beetroot carrot!"
				+'\n' +"Apple is love!";
		String option = "-m";
		String[] args = new String[] { option };

		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}

	@Test
	public void testPrintWordCountInStdin() throws AbstractApplicationException {
		String expectedOutput = "6" + System.lineSeparator();
		String input = "Apple beetroot carrot!"
				+System.lineSeparator()+"Apple is love!";
		String option = "-w";
		String[] args = new String[] { option };

		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testPrintNewlineCountInStdin() throws AbstractApplicationException {
		String expectedOutput = "2" + System.lineSeparator();
		String input = "Apple beetroot carrot!"
				+System.lineSeparator()+"Apple is love!";
		String option = "-l";
		String[] args = new String[] { option };

		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test
	public void testPrintAllCountsInStdin() throws AbstractApplicationException {
		String expectedOutput = "37 6 2" + System.lineSeparator();
		String input = "Apple beetroot carrot!"
				+'\n'+"Apple is love!";
		String[] args = new String[0];

		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test (expected = Exception.class)
	public void testFileException() throws AbstractApplicationException {
		String expectedOutput = "1";
		String option = "-l";
		String filePath = "test-data"+File.separator+"testWc1.txt";
		String[] args = new String[] { option, filePath };

		WcApplication wcApp = new WcApplication();
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		wcApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
}
