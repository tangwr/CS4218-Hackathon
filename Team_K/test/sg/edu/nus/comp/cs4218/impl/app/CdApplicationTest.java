package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class CdApplicationTest {

	File testDir;
	static String  originDir = "";
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@BeforeClass
	public static void setUpOnce(){
		originDir = System.getProperty(Environment.DIR);
	}

	@Before
	public void setUp() throws Exception {
		testDir = new File(originDir + File.separator
				+ "test");
		testDir.mkdir();
	}

	@After
	public void tearDown() throws Exception {
		testDir.delete();
		Environment.setCurrentDirectory(originDir);
	}
	
	@Test
	public void testCdUsingNullArgrs() throws AbstractApplicationException {
		expectedEx.expect(CdException.class);
		expectedEx.expectMessage("cd: Null Pointer Exception");
		CdApplication cdApplication = new CdApplication();
		cdApplication.run(null, null, System.out);
	}

	@Test
	public void testCdUsingEmptyArg() throws AbstractApplicationException {
		expectedEx.expect(CdException.class);
		expectedEx.expectMessage("cd: Application requires 1 argument");
		CdApplication cdApplication = new CdApplication();
		cdApplication.run(new String[0], null, System.out);
	}
	
	@Test
	public void testCdUsingMoreThanOneArgs() throws AbstractApplicationException {
		expectedEx.expect(CdException.class);
		expectedEx.expectMessage("cd: Application requires 1 argument");
		CdApplication cdApplication = new CdApplication();
		String[] args = new String[2];
		args[0] = "folder1";
		args[1] = "folder2";
		cdApplication.run(args, null, System.out);
	}
	
	@Test
	public void testCdUsingNonexistentDir() throws AbstractApplicationException {
		expectedEx.expect(CdException.class);
		expectedEx.expectMessage("cd: This is not a directory");
		CdApplication cdApplication = new CdApplication();
		cdApplication.run(new String[]{"abc"}, null, System.out);
	}

	@Test
	public void testCdUsingExistentDir() throws AbstractApplicationException, IOException {
		CdApplication cdApplication = new CdApplication();
	
		cdApplication.run(new String[]{"test"}, null, System.out);
		String newDir = Environment.getCurrentDirectory();
		assertEquals(originDir + File.separator + "test", newDir);
	}
	
	@Test
	public void testCdUsingCanonicalPath() throws AbstractApplicationException, IOException {
		CdApplication cdApplication = new CdApplication();
		try {
			Environment.setCurrentDirectory(originDir + File.separator + "test");
		} catch (ShellException e) {
			fail();
		}
		cdApplication.run(new String[]{".."}, null, System.out);
		String newDir = Environment.getCurrentDirectory();
		assertEquals(originDir , newDir);
	}

}
