package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.PwdException;

public class PwdApplication implements Application{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if (stdout == null) {
			throw new PwdException("OutputStream not provided");
		}
		try {
			stdout.write(Environment.getCurrentDirectory().getBytes());
			stdout.write(System.lineSeparator().getBytes());
		} catch (IOException e) {
			//e.printStackTrace();
			throw new PwdException("Cannot write to stdout");
		}
	}

}
