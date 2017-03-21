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
import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplicationTest {
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
	public void testHeadUsingNullArgs() throws AbstractApplicationException {
		expectedEx.expect(HeadException.class);
		expectedEx.expectMessage("head: Null Pointer Exception");
		HeadApplication headApplication = new HeadApplication();
		headApplication.run(null, null, System.out);
	}

	@Test
	public void testHeadUsingNullStdout() throws AbstractApplicationException {
		expectedEx.expect(HeadException.class);
		expectedEx.expectMessage("head: Null Pointer Exception");
		HeadApplication headApplication = new HeadApplication();
		headApplication.run(new String[2], null, null);
	}

	@Test
	public void testHeadUsingNullString() throws AbstractApplicationException {
		expectedEx.expect(HeadException.class);
		expectedEx.expectMessage("head: Null Pointer Exception");
		HeadApplication headApplication = new HeadApplication();
		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		headApplication.run(new String[1], null, testOutputStream);
	}

	@Test
	public void testHeadUsingInvalidArguments() throws AbstractApplicationException{
		expectedEx.expect(HeadException.class);
		expectedEx.expectMessage("head: Invalid arguments");
		HeadApplication headApplication = new HeadApplication();
		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		String[] args;
		args = new String[2];
		args[0] = "";
		args[1] = "";
		headApplication.run(args, new ByteArrayInputStream("".getBytes()), testOutputStream);

		expectedEx.expect(HeadException.class);
		expectedEx.expectMessage("head: Invalid arguments");
		args = new String[3];
		args[0] = "";
		args[1] = "";
		args[2] = "";
		headApplication.run(args, new ByteArrayInputStream("".getBytes()), testOutputStream);
	}

	@Test
	public void testHeadUsingNegativeNumLines() throws AbstractApplicationException {
		expectedEx.expect(HeadException.class);
		expectedEx.expectMessage("head: Invalid number of lines to be read");
		HeadApplication headApplication = new HeadApplication();
		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		headApplication.run(new String[]{"-n","-1"}, new ByteArrayInputStream("".getBytes()), testOutputStream);
	}

	@Test
	public void testHeadUsingMoreThanThreeArguments() throws AbstractApplicationException {
		expectedEx.expect(HeadException.class);
		expectedEx.expectMessage("head: Invalid number of arguments");
		HeadApplication headApplication = new HeadApplication();
		String[] args = new String[4];
		args[0] = "abc";
		args[1] = "bcd";
		args[2] = "cde";
		args[3] = "def";
		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		headApplication.run(args, null, testOutputStream);
	}
	
	@Test
	public void testHeadUsingFileDefaultLineNumber() {
		HeadApplication headApplication = new HeadApplication();

		String content = "";
		for (int i = 0; i < 10; i++) {
			content += "n" + i + System.lineSeparator();
		}
		String extraContent = "Hello world";
		File inputFile = createAndWriteFile("input.txt",content+extraContent);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			headApplication.run(new String[]{"input.txt"}, null, stdout);
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
	public void testHeadUsingFileLessThan10LinesWithDefaultLineNumber() {
		HeadApplication headApplication = new HeadApplication();

		String content = "";
		for (int i = 0; i < 5; i++) {
			content += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			headApplication.run(new String[]{"input.txt"}, null, stdout);
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
	public void testHeadUsingFileLineNumberOption() {
		HeadApplication headApplication = new HeadApplication();

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
			headApplication.run(new String[]{"-n","4","input.txt"}, null, stdout);
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
	public void testHeadUsingFileWithLessThanLineNumberOption() {
		HeadApplication headApplication = new HeadApplication();

		String content = "";
		for (int i = 0; i < 2; i++) {
			content += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			headApplication.run(new String[]{"-n","4","input.txt"}, null, stdout);
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
	public void testHeadUsingFileWithLineNumberOption0() {
		HeadApplication headApplication = new HeadApplication();

		String content = "";
		for (int i = 0; i < 2; i++) {
			content += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			headApplication.run(new String[]{"-n","0","input.txt"}, null, stdout);
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
	public void testHeadUsingInputStream() {
		HeadApplication headApplication = new HeadApplication();

		String content = "";
		for (int i = 0; i < 10; i++) {
			content += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content);
		try {
			FileInputStream stdin = new FileInputStream(inputFile);
		
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			headApplication.run(new String[0], stdin, stdout);
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
	public void testHeadUsingNonExistentFile() throws AbstractApplicationException {
		expectedEx.expect(HeadException.class);
		expectedEx.expectMessage("head: File Not Found");
		HeadApplication headApplication = new HeadApplication();

		ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
		headApplication.run(new String[]{"abc.txt"}, null, testOutputStream);
	}
}
