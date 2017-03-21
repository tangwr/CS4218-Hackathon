package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplication implements Application{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		int totalReadLine;
		InputStream is;
		if (args == null || stdout == null) {
			throw new HeadException("Null Pointer Exception");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				throw new HeadException("Null Pointer Exception");
			}
		}
		switch (args.length) {
		case 0: 
			totalReadLine = 10;
			if (stdin == null) {
				throw new HeadException("Null Stdin");
			}
			is = stdin;
			break;
		case 1:
			if (args[0].equals("-n")){
				throw new HeadException("Missing argument");
			}
			totalReadLine = 10;
			try{
				is = new BufferedInputStream(new FileInputStream(args[0]));
			} catch (FileNotFoundException e) {
				throw new HeadException("File Not Found");
			}
			break;
		case 2:
			if (stdin == null) {
				throw new HeadException("Null Stdin");
			}
			is = stdin;
			if (args[0].equals("-n")) {
				try {
					totalReadLine = Integer.parseInt(args[1]);	
				} catch (NumberFormatException nfe) {
					throw new HeadException("An integer must follow -n");
				}
				if (totalReadLine < 0) {
					throw new HeadException("Invalid number of lines to be read");		
				}
			} else {
				throw new HeadException("Invalid arguments");
			}
			break;
		case 3:
			if (args[0].equals("-n")) {
				try {
					totalReadLine = Integer.parseInt(args[1]);	
				} catch (NumberFormatException nfe) {
					throw new HeadException("An integer must follow -n");
				}
				if (totalReadLine < 0) {
					throw new HeadException("Invalid number of lines to be read");		
				}
			} else {
				throw new HeadException("Invalid arguments");
			}
	
			try{
				is = new BufferedInputStream(new FileInputStream(args[2]));
			} catch (FileNotFoundException e) {
				throw new HeadException("File Not Found");
			}
			break;
		default:
			throw new HeadException("Invalid number of arguments");
		}
		try {
			printHeadToStdout(is, totalReadLine, stdout);
		} catch (IOException e) {
			throw new HeadException("Error reading input stream");
		}
	}
	
	private void printHeadToStdout(InputStream is, int totalLine, OutputStream os) throws IOException{
		while (totalLine > 0) {
			int nextByte = is.read();
			if (nextByte == -1) {
				break;
			}

			if (nextByte == (byte)'\n') {
				totalLine--;
			}
			os.write(nextByte);;
		}
	}
}
