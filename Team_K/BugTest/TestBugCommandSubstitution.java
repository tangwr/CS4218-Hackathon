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
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImplementation;

public class TestBugCommandSubstitution {


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
	 * Command: sort `cat files.txt | head -n 1`
	 * Refer: Lab 5 Integration command
	 * It does not sort the data after concatenation
	 */
	@Test
	public void testSortingCatFile() {
		ShellImplementation test = new ShellImplementation();
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
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
			test.parseAndEvaluate("sort `cat input.txt | head -n 1`", stdout);
			assertEquals(content+extra,stdout.toString());
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			assertEquals(content+extra,e.getMessage());
		}
	}

}
