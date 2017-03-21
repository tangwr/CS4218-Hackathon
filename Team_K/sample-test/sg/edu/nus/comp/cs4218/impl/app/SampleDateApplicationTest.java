package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DateException;
//import sg.edu.nus.comp.cs4218.util.ReadableOutputStream;
//import sg.edu.nus.comp.cs4218.util.WritableInputStream;

public class SampleDateApplicationTest {

	private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	private static DateApplication app;
	private OutputStream stdout;
	private InputStream stdin;

	@BeforeClass
	public static void init() throws Exception {
		app = new DateApplication();
	}

	@Before
	public void setUp() throws Exception {
	//	stdin = new WritableInputStream("");
	//	stdout = new ReadableOutputStream();
		stdin = null;
		stdout = new ByteArrayOutputStream();
	}

	@Test
	public void testDateWithNullStdin() throws AbstractApplicationException {
		String message = "date - test with null stdin";
		app.run(new String[0], null, stdout);
		Calendar cal = Calendar.getInstance();
		assertEquals(message, DEFAULT_DATE_FORMAT.format(cal.getTime())+ System.lineSeparator(), stdout.toString());
	}

	@Test(expected = DateException.class)
	public void testDateWithNullStdout() throws AbstractApplicationException {
		app.run(new String[0], stdin, null);
	}

	@Test(expected = DateException.class)
	public void testDateWithNullStdinAndStdout() throws AbstractApplicationException {
		app.run(null, null, null);
	}

	@Test
	public void testDateWithCurrentTimeDateWithNullStdin() throws AbstractApplicationException {
		String message = "date - test with current time and date with null stdin";
		app.run(new String[0], null, stdout);
		Calendar cal = Calendar.getInstance();
		assertEquals(message, DEFAULT_DATE_FORMAT.format(cal.getTime())+ System.lineSeparator(), stdout.toString());
	}

	@Test
	public void testDateWithCurrentTimeDate() throws AbstractApplicationException {
		String message = "date - test with current time and date";
		app.run(new String[0], stdin, stdout);
		Calendar cal = Calendar.getInstance();
		assertEquals(message, DEFAULT_DATE_FORMAT.format(cal.getTime()) + System.lineSeparator(), stdout.toString());
	}

	@Test
	public void testDateWithWaitOneSecond() throws InterruptedException, AbstractApplicationException {
		String message = "date - test with current time and wait seconds";
		app.run(new String[0], stdin, stdout);
		Thread.sleep(1000);
		Calendar cal = Calendar.getInstance();
		assertNotSame(message, DEFAULT_DATE_FORMAT.format(cal.getTime())+ System.lineSeparator(), stdout.toString());
	}

}
