package sg.edu.nus.comp.cs4218.impl.app;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplicationTest {
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testDateUsingNullArgs() throws AbstractApplicationException{
		expectedEx.expect(DateException.class);
		expectedEx.expectMessage("date: Null Pointer Exception");
		DateApplication dateApp = new DateApplication();
		dateApp.run(null, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testDateUsingNullStdout() throws AbstractApplicationException{
		expectedEx.expect(DateException.class);
		expectedEx.expectMessage("date: Null Pointer Exception");		
		DateApplication dateApp = new DateApplication();
		dateApp.run(new String[]{}, null, null);
	}
	
	@Test
	public void testDateUsingTooManyArgs() throws AbstractApplicationException{
		expectedEx.expect(DateException.class);
		expectedEx.expectMessage("date: Too many arguments");
		DateApplication dateApp = new DateApplication();
		dateApp.run(new String[]{"1","2"}, null, new ByteArrayOutputStream());
	}
	
	@Test
	public void testDate() {
		//[week day] [month] [day] [hh:mm:ss] [time zone][year].
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				
		DateApplication dateApp = new DateApplication();
		String[] args = new String[0];
		OutputStream stdout = new ByteArrayOutputStream();
		try {
			dateApp.run(args, null, stdout);
		} catch (AbstractApplicationException e) {
			fail();
		}
		Calendar cal = Calendar.getInstance();
		String expectedOutput = dateFormat.format(cal.getTime()) + System.lineSeparator();
		
		assertEquals(expectedOutput, stdout.toString());
	}	
}
