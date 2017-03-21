package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import sg.edu.nus.comp.cs4218.app.Date;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplication implements Date {
	public static void main(String[] args) throws AbstractApplicationException {
		DateApplication dateApp = new DateApplication();
		dateApp.run(new String[]{}, null, System.out);
	}
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if (args == null || stdout == null) {
			throw new DateException("Null Pointer Exception");
		}
		if (args.length > 0) {
			throw new DateException("Too many arguments");
		}
		
		try {
			stdout.write(printCurrentDate("").getBytes());
		} catch (IOException e) {
			throw new DateException("Error writing to stdout");
		}
	}

	@Override
	public String printCurrentDate(String args) {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		return df.format(cal.getTime()) + System.lineSeparator();
	}

}
