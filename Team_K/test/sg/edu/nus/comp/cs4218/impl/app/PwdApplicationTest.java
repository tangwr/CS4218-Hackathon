package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.PwdException;

public class PwdApplicationTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testPwdUsingNullStdout() throws AbstractApplicationException {
		expectedEx.expect(PwdException.class);
		expectedEx.expectMessage("pwd: " + "OutputStream not provided");
		PwdApplication pwdApplication = new PwdApplication();
		pwdApplication.run(null, null, null);
	}

	@Test
	public void testPwd() {
		PwdApplication pwdApplication = new PwdApplication();
		try {
			File out = new File("out.txt");
			OutputStream stdout = new FileOutputStream(out);
			pwdApplication.run(null, null, stdout);
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(out)));
			assertEquals(Environment.getCurrentDirectory(), buffReader.readLine());
			if (out != null) {
				out.delete();
			}
			buffReader.close();
		} catch (IOException e) {
			fail();
		} catch (AbstractApplicationException e) {
			fail();
		}
	}
}
