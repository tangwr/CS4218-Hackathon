package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.TailException;

public class TailApplicationTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		Environment.setCurrentDirectory(System.getProperty(Environment.DIR));
	}

	@After
	public void tearDown() throws Exception {
		Environment.setCurrentDirectory(System.getProperty(Environment.DIR));
	}
	
	private File createAndWriteFile(String fileName, String content) {
		try{
			File input = new File(Environment.getCurrentDirectory() 
					+ File.separator + fileName);
		
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(input)));
			writer.write(content);
			writer.close();
			
			return input;
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		return null;
	}
	
	@Test
	public void testTailUsingNullArgs() throws AbstractApplicationException {
		expectedEx.expect(TailException.class);
		expectedEx.expectMessage("tail: Null Pointer Exception");
		TailApplication tailApplication = new TailApplication();
		tailApplication.run(null, null, System.out);
	}

	@Test
	public void testTailUsingNullStdout() throws AbstractApplicationException {
		expectedEx.expect(TailException.class);
		expectedEx.expectMessage("tail: Null Pointer Exception");
		TailApplication tailApplication = new TailApplication();
		tailApplication.run(new String[2], null, null);
	}

	@Test
	public void testTailUsingNullString() throws AbstractApplicationException {
		expectedEx.expect(TailException.class);
		expectedEx.expectMessage("tail: Null Pointer Exception");
		TailApplication tailApplication = new TailApplication();
		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		tailApplication.run(new String[1], null, testOutputStream);
	}

	@Test
	public void testTailUsingInvalidArguments() throws AbstractApplicationException{
		expectedEx.expect(TailException.class);
		expectedEx.expectMessage("tail: Invalid arguments");
		TailApplication tailApplication = new TailApplication();
		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		String[] args;
		args = new String[2];
		args[0] = "";
		args[1] = "";
		tailApplication.run(args, new ByteArrayInputStream("".getBytes()), testOutputStream);

		expectedEx.expect(TailException.class);
		expectedEx.expectMessage("tail: Invalid arguments");
		args = new String[3];
		args[0] = "";
		args[1] = "";
		args[2] = "";
		tailApplication.run(args, new ByteArrayInputStream("".getBytes()), testOutputStream);
	}

	@Test
	public void testTailUsingNegativeNumLines() throws AbstractApplicationException {
		expectedEx.expect(TailException.class);
		expectedEx.expectMessage("tail: Invalid number of lines to be read");
		TailApplication tailApplication = new TailApplication();
		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		tailApplication.run(new String[]{"-n","-1"}, new ByteArrayInputStream("".getBytes()), testOutputStream);
	}

	@Test
	public void testTailUsingMoreThanThreeArguments() throws AbstractApplicationException {
		expectedEx.expect(TailException.class);
		expectedEx.expectMessage("tail: Invalid number of arguments");
		TailApplication tailApplication = new TailApplication();
		String[] args = new String[4];
		args[0] = "abc";
		args[1] = "bcd";
		args[2] = "cde";
		args[3] = "def";
		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		tailApplication.run(args, null, testOutputStream);
	}
	
	@Test
	public void testTailUsingFileDefaultLineNumber() {
		TailApplication tailApplication = new TailApplication();

		String content = "";
		for (int i = 0; i < 10; i++) {
			content += "n" + i + System.lineSeparator();
		}
		String extraContent = "Hello world"+System.lineSeparator();
		File inputFile = createAndWriteFile("input.txt",extraContent+content);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApplication.run(new String[]{"input.txt"}, null, stdout);
			assertEquals(content, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
	
	@Test
	public void testTailUsingFileLessThan10LinesWithDefaultLineNumber() {
		TailApplication tailApplication = new TailApplication();

		String content = "";
		for (int i = 0; i < 5; i++) {
			content += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApplication.run(new String[]{"input.txt"}, null, stdout);
			assertEquals(content, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
	
	@Test
	public void testTailUsingFileLineNumberOption() {
		TailApplication tailApplication = new TailApplication();

		String content = "";
		for (int i = 0; i < 4; i++) {
			content += "n" + i + System.lineSeparator();
		}
		String extra = "";
		for (int i = 4; i < 10; i++) {
			extra += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content+extra);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApplication.run(new String[]{"-n","6","input.txt"}, null, stdout);
			assertEquals(extra, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}

	@Test
	public void testTailUsingFileWithLessThanLineNumberOption() {
		TailApplication tailApplication = new TailApplication();

		String content = "";
		for (int i = 0; i < 2; i++) {
			content += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApplication.run(new String[]{"-n","4","input.txt"}, null, stdout);
			assertEquals(content, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
	
	@Test
	public void testTailUsingFileWithLineNumberOption0() {
		TailApplication tailApplication = new TailApplication();

		String content = "";
		for (int i = 0; i < 2; i++) {
			content += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApplication.run(new String[]{"-n","0","input.txt"}, null, stdout);
			assertEquals("", stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
	
	@Test
	public void testTailUsingInputStream() {
		TailApplication tailApplication = new TailApplication();

		String content = "";
		for (int i = 0; i < 10; i++) {
			content += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content);
		try {
			FileInputStream stdin = new FileInputStream(inputFile);
		
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApplication.run(new String[0], stdin, stdout);
			assertEquals(content, stdout.toString());
			stdin.close();
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
			
	@Test
	public void testTailUsingNonExistentFile() throws AbstractApplicationException {
		expectedEx.expect(TailException.class);
		expectedEx.expectMessage("tail: File Not Found");
		TailApplication tailApplication = new TailApplication();

		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		tailApplication.run(new String[]{"abc.txt"}, null, testOutputStream);
	}
}
