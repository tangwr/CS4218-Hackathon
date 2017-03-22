import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;


public class TestBugTailApp {
	private final static String NEWLINE = System.lineSeparator();
	
	
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
		
		/*
		 * Command: tail input.txt -n 4
		 * The arguments of command may appear in any order
		 * Refer: https://ivle.nus.edu.sg/v1/Forum/board_read.aspx?forumid=c6521898-470a-4e05-9765-5443e65bd182&postid=ef59c680-fefe-4268-a59c-832634dff9e2&lpreferrer=&headingid=7c157686-8c5b-4dab-bab9-78c0a0d19129&isNested=1
		 */
	@Test
	public void testWithPathAndLineNumber() {
		TailApplication tailApp = new TailApplication();

		String content = "";
		for (int i = 0; i < 5; i++) {
			content += "n" + i + System.lineSeparator();
		}
		String extra = "";
		for (int i = 6; i < 10; i++) {
			extra += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content+extra);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApp.run(new String[]{"-n","4","input.txt"}, null, stdout);
			assertEquals(extra, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApp.run(new String[]{"input.txt","-n","4"}, null, stdout);
			assertEquals(content, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
	
	/*
	 * Command: tail -n 4 -n 2 input.txt
	 * No assumption made on whether to override the line numbers or throw exception
	 */
	@Test
	public void testWithPathAndMultipleLineNumber() {
		TailApplication tailApp = new TailApplication();

		String content = "";
		for (int i = 0; i < 5; i++) {
			content += "n" + i + System.lineSeparator();
		}
		String extra = "";
		for (int i = 6; i < 10; i++) {
			extra += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content+extra);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApp.run(new String[]{"-n","4","input.txt"}, null, stdout);
			assertEquals(extra, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApp.run(new String[]{"-n","4","-n","2","input.txt"}, null, stdout);
			assertEquals(content, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
	
	/*
	 * No assumption on whether throwing exceptions or printing the files for reading multiple files
	 * Refer: https://ivle.nus.edu.sg/v1/forum/board_read.aspx?forumid=c6521898-470a-4e05-9765-5443e65bd182&postid=a9fe9e83-6373-46f4-80b2-c8ee7d4969c2&lpreferrer=&headingid=7c157686-8c5b-4dab-bab9-78c0a0d19129&isNested=1
	 */
	@Test
	public void testWithPathAndMultiplePath() {
		TailApplication tailApp = new TailApplication();

		String content = "";
		for (int i = 0; i < 5; i++) {
			content += "n" + i + System.lineSeparator();
		}
		String extra = "";
		for (int i = 6; i < 10; i++) {
			extra += "n" + i + System.lineSeparator();
		}
		File inputFile = createAndWriteFile("input.txt",content+extra);
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApp.run(new String[]{"-n","4","input.txt"}, null, stdout);
			assertEquals(extra, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			tailApp.run(new String[]{"-n","4","input.txt","input.txt"}, null, stdout);
			assertEquals(extra, stdout.toString());
			stdout.close();
			inputFile.delete();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
}
