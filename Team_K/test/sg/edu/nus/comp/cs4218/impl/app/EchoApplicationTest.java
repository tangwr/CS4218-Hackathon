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

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.EchoException;

public class EchoApplicationTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testEchoUsingNullArgs() throws AbstractApplicationException{
		expectedEx.expect(EchoException.class);
		expectedEx.expectMessage("echo: Null arguments");
		EchoApplication echoApplication = new EchoApplication();
		echoApplication.run(null, null, System.out);
	}

	@Test
	public void testEchoUsingNullStdout() throws AbstractApplicationException {
		expectedEx.expect(EchoException.class);
		expectedEx.expectMessage("echo: OutputStream not provided");
		EchoApplication echoApplication = new EchoApplication();
		echoApplication.run(new String[]{"test"}, null, null);
	}
	
	@Test
	public void testEchoUsingNoArg() {
		EchoApplication echoApplication = new EchoApplication();
		try {
			File out = new File("out.txt");
			OutputStream stdout = new FileOutputStream(out);
			echoApplication.run(new String[]{}, null, stdout);
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(out)));
			assertEquals(" ", buffReader.readLine());
			if (out != null) {
				out.delete();
			}
			buffReader.close();
		} catch (AbstractApplicationException e) {
			fail();
		} catch (IOException e) {
			fail();
		} 
	}
	
	@Test
	public void testEchoUsingOneArg() {
		EchoApplication echoApplication = new EchoApplication();
		try {
			File out = new File("out.txt");
			OutputStream stdout = new FileOutputStream(out);
			echoApplication.run(new String[]{"test"}, null, stdout);
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(out)));
			assertEquals("test", buffReader.readLine());
			if (out != null) {
				out.delete();
			}
			buffReader.close();
		} catch (AbstractApplicationException e) {
			fail();
		} catch (IOException e) {
			fail();
		} 
	}
	
	@Test
	public void testEchoUsingMoreThanOneArg() {
		EchoApplication echoApplication = new EchoApplication();
		try {
			File out = new File("out.txt");
			OutputStream stdout = new FileOutputStream(out);
			echoApplication.run(new String[]{"test hehe", "xd"}, null, stdout);
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(out)));
			assertEquals("test hehe xd", buffReader.readLine());
			if (out != null) {
				out.delete();
			}
			buffReader.close();
		} catch (AbstractApplicationException e) {
			fail();
		} catch (IOException e) {
			fail();
		} 
	}	
}
