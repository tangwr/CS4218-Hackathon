package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;

public class CatApplicationTest {
	static String  originDir = "";
	private final String CONTENT1 = "Hello World!";
	private final String CONTENT2 = "Anhongxeo";
	
	private File createAndWriteFile(String fileName, String content) {
		try{
			File input = new File(originDir + File.separator + fileName + ".txt");
		
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
	
	@BeforeClass
	public static void setUpOnce(){
		originDir = System.getProperty(Environment.DIR);
	}

	@Before
	public void setUp() throws Exception {
		createAndWriteFile("input", CONTENT1);
		createAndWriteFile("input2", CONTENT2);
	}

	@After
	public void tearDown() throws Exception {
		File input = new File(originDir + File.separator + "input.txt");
		input.delete();
		File input2 = new File(originDir + File.separator + "input2.txt");
		input2.delete();
		Environment.setCurrentDirectory(originDir);
	}

	@Test
	public void testCatUsingNullArgs()
			throws AbstractApplicationException {
		expectedEx.expect(CatException.class);
		expectedEx.expectMessage("cat: Null Pointer Exception");
		CatApplication catApplication = new CatApplication();
		catApplication.run(null, null, System.out);
	}

	@Test
	public void testCatUsingNullStdout()
			throws AbstractApplicationException {
		expectedEx.expect(CatException.class);
		expectedEx.expectMessage("cat: stdout is null");

		CatApplication catApplication = new CatApplication();
		String[] args = new String[]{"testFolder"};
		catApplication.run(args, null, null);
	}

	@Test
	public void testCatUsingNullStdin()
			throws AbstractApplicationException {
		expectedEx.expect(CatException.class);
		expectedEx.expectMessage("cat: Null Pointer Exception");

		CatApplication catApplication = new CatApplication();
		String[] args = new String[]{};
		catApplication.run(args, null, System.out);
	}
	
	@Test
	public void testCatUsingDir() throws AbstractApplicationException {
		expectedEx.expect(CatException.class);
		expectedEx.expectMessage("cat: This is a directory");
		CatApplication catApplication = new CatApplication();

		String folderName = "testFolder";
		File dir = new File(folderName);
		dir.mkdir();

		String[] args = { folderName};
		catApplication.run(args, null, System.out);
	}
	
	@Test
	public void testCatUsingStdin() {

		CatApplication catApplication = new CatApplication();
		String[] args = new String[]{};
		String input = "input.txt";
		try {
			InputStream stdin = new FileInputStream(new File(input));
			File outputfile = File.createTempFile("output", ".txt");
			OutputStream stdout = new FileOutputStream(outputfile);
			catApplication.run(args, stdin, stdout);
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(outputfile)));
			assertEquals(CONTENT1, buffReader.readLine());
			
			buffReader.close();
		} catch (IOException e) {
			fail();
		} catch (CatException e) {
			fail();
		}
	}

	@Test
	public void testCatUsingFileNames() {
		CatApplication catApplication = new CatApplication();
		String[] args = new String[2];
		args[0] = "input.txt";
		args[1] = "input2.txt";

		try {
			File outputfile = File.createTempFile("output", ".txt");
			OutputStream stdout = new FileOutputStream(outputfile);
			catApplication.run(args, null, stdout);
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(outputfile)));
			assertEquals(CONTENT1, buffReader.readLine());
			assertEquals(CONTENT2, buffReader.readLine());

			buffReader.close();
		} catch (IOException e) {
			fail();
		} catch (CatException e) {
			fail();
		}	
	}

	@Test
	public void testCatWithUnfoundedFile()
			throws AbstractApplicationException {
		expectedEx.expect(CatException.class);
		expectedEx.expectMessage("cat: Could not read file");
		CatApplication catApplication = new CatApplication();
		String[] args = { "abc.txt" };
		catApplication.run(args, null, System.out);
	}
}
