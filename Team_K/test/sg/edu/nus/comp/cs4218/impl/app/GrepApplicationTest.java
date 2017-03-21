package sg.edu.nus.comp.cs4218.impl.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.GrepException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class GrepApplicationTest {
	private ByteArrayOutputStream stdout;
	private static final String ORIGIN_DIR = Environment.getCurrentDirectory();
	
	private File createAndWriteFile(String fileName, String content) {
		try{
			File input = new File(ORIGIN_DIR + File.separator + fileName + ".txt");
		
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
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		Environment.setCurrentDirectory(ORIGIN_DIR);
		stdout = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() throws Exception {
		Environment.setCurrentDirectory(ORIGIN_DIR);
	}

	@Test
	public void testGrepNoFileAndNullInputStream() throws AbstractApplicationException {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("Null Pointer Exception");
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(new String[] { "bar" }, null, stdout);
	}
	
	@Test
	public void testGrepWithNullOutputStream()
			throws AbstractApplicationException {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("Null Pointer Exception");
		String[] args = { "pattern" };
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(args, null, null);
	}

	@Test
	public void testGrepEmptyArgs() throws AbstractApplicationException {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("Invalid arguments");
		String[] args = new String[] {};
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(args, null, stdout);
	}
	
	@Test
	public void testGrepEmptyArgument() throws AbstractApplicationException {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("Empty argument");
		String[] args = new String[] {""};
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(args, null, stdout);
	}
	
	@Test
	public void testGrepUnexistedFile() throws AbstractApplicationException {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("Unable to read file");
		String[] args = { "a", "unknown.txt" };
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(args, null, stdout);
	}

	@Test
	public void testGrepEmptyPattern() throws AbstractApplicationException {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("Empty argument");
		GrepApplication grepApplication = new GrepApplication();
		String[] args = new String[] { "" };
		grepApplication.run(args, null, stdout);
	}

	@Test
	public void testGrepCharacterPattern() throws AbstractApplicationException {
		String content = "";
		content += "123aa123" + System.lineSeparator();
		content += "abcxyz" + System.lineSeparator();
		content += "ggg" + System.lineSeparator();
		content += " ii a" + System.lineSeparator();
		File file = createAndWriteFile("input",content);
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(new String[]{"a", "input.txt"}, null, stdout);
		String expected = "123aa123" + System.lineSeparator() 
			+"abcxyz" + System.lineSeparator() + " ii a" + System.lineSeparator();
		assertEquals(expected,stdout.toString());
		file.delete();
	}
	
	@Test
	public void testGrepWordsPattern() throws AbstractApplicationException {
		String content = "";
		content += "123aa123 this is a test grep" + System.lineSeparator();
		content += "abcxyz" + System.lineSeparator();
		content += "ggg i need to test grep" + System.lineSeparator();
		content += " ii a" + System.lineSeparator();
		File file = createAndWriteFile("input",content);
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(new String[]{"test grep", "input.txt"}, null, stdout);
		String expected = "123aa123 this is a test grep" + System.lineSeparator() 
			+"ggg i need to test grep" + System.lineSeparator();
		assertEquals(expected,stdout.toString());
		file.delete();
	}
	
	@Test
	public void testGrepPatternWithMultipleFiles() throws AbstractApplicationException {
		String content = "";
		content += "123aa123 this is a test grep" + System.lineSeparator();
		content += "abcxyz" + System.lineSeparator();
		content += "ggg i need to test grep" + System.lineSeparator();
		content += " ii a" + System.lineSeparator();
		File file = createAndWriteFile("input",content);
		String content2 = "";
		content2 += "123aa123 thisgrep" + System.lineSeparator();
		content2 += "abcxyrepz" + System.lineSeparator();
		content2 += "ggg i need to test grep" + System.lineSeparator();
		content2 += " ii a" + System.lineSeparator();
		File file2 = createAndWriteFile("input2",content2);
		
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(new String[]{"rep", "input.txt", "input2.txt"}, null, stdout);
		String expected = "123aa123 this is a test grep" + System.lineSeparator() 
			+"ggg i need to test grep" + System.lineSeparator() 
			+ "123aa123 thisgrep" + System.lineSeparator() + "abcxyrepz" 
			+ System.lineSeparator() + "ggg i need to test grep" + System.lineSeparator();
		assertEquals(expected,stdout.toString());
		file.delete();
		file2.delete();
	}
	
	@Test
	public void testGrepUsingStdin() throws AbstractApplicationException {
		String content = "";
		content += "123aa123" + System.lineSeparator();
		content += "abcxyz" + System.lineSeparator();
		content += "ggg" + System.lineSeparator();
		content += " ii a" + System.lineSeparator();
		File file = createAndWriteFile("input",content);
		try {
			InputStream stdin = new FileInputStream(file);
			GrepApplication grepApplication = new GrepApplication();
			grepApplication.run(new String[]{"a"}, stdin, stdout);
			String expected = "123aa123" + System.lineSeparator() +"abcxyz" + System.lineSeparator() + " ii a" + System.lineSeparator();
			assertEquals(expected,stdout.toString());
			file.delete();
		} catch (FileNotFoundException e) {
			fail();
		}
	}
	
	@Test
	public void testGrepInvalidPatternFromFile() throws AbstractApplicationException {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("Invalid Pattern in file");
		File file = createAndWriteFile("input","abc");
		GrepApplication grepApplication = new GrepApplication();
		grepApplication.run(new String[]{"*", "input.txt"}, null, stdout);
		file.delete();
	}
	
	@Test
	public void testGrepInvalidPatternFromStdin() throws AbstractApplicationException {
		expectedEx.expect(GrepException.class);
		expectedEx.expectMessage("Invalid Pattern in stdin");
		File file = createAndWriteFile("input","abc");
		GrepApplication grepApplication = new GrepApplication();
		try {
			InputStream stdin = new FileInputStream(file);
			grepApplication.run(new String[]{"*"}, stdin, stdout);
			file.delete();
		} catch (FileNotFoundException e) {
			fail();
		}
	}
}
