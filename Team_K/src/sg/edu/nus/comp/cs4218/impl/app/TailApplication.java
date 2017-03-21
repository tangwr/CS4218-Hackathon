package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.TailException;

public class TailApplication implements Application {

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if (args == null || stdout == null) {
			throw new TailException("Null Pointer Exception");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				throw new TailException("Null Pointer Exception");
			}
		}
		int totalReadLine;
		InputStream is;
		switch (args.length) {
		case 0: 
			totalReadLine = 10;
			if (stdin == null) {
				throw new TailException("Null Stdin");
			}
			is = stdin;
			break;
		case 1:
			if (args[0].equals("-n")){
				throw new TailException("Missing argument");
			}
			totalReadLine = 10;
			try{
				is = new BufferedInputStream(new FileInputStream(args[0]));
			} catch (FileNotFoundException e) {
				throw new TailException("File Not Found");
			}
			break;
		case 2:
			if (stdin == null) {
				throw new TailException("Null Stdin");
			}
			is = stdin;
			if (args[0].equals("-n")) {
				try {
					totalReadLine = Integer.parseInt(args[1]);	
				} catch (NumberFormatException nfe) {
					throw new TailException("An integer must follow -n");
				}
				if (totalReadLine < 0) {
					throw new TailException("Invalid number of lines to be read");		
				}
			} else {
				throw new TailException("Invalid arguments");
			}
			break;
		case 3:
			if (args[0].equals("-n")) {
				try {
					totalReadLine = Integer.parseInt(args[1]);	
				} catch (NumberFormatException nfe) {
					throw new TailException("An integer must follow -n");
				}
				if (totalReadLine < 0) {
					throw new TailException("Invalid number of lines to be read");		
				}
			} else {
				throw new TailException("Invalid arguments");
			}
	
			try{
				is = new BufferedInputStream(new FileInputStream(args[2]));
			} catch (FileNotFoundException e) {
				throw new TailException("File Not Found");
			}
			break;
		default:
			throw new TailException("Invalid number of arguments");
		}
		try {
			printTailToStdout(is, totalReadLine, stdout);
		} catch (IOException e) {
			throw new TailException("Error reading input stream");
		}
	}
	
	private void printTailToStdout(InputStream is, int totalLine, OutputStream os) throws IOException{
		Vector<String> lineList = new Vector<String>();
		String str;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		if (is != null) {                            
        	while ((str = reader.readLine()) != null) {    
        		lineList.add(str);
            }                
        }
		int startLine = totalLine >= lineList.size() ? 0 : lineList.size() - totalLine;
		for (int i = startLine; i < lineList.size() ; i++) {
			os.write(lineList.get(i).getBytes());
			os.write(System.lineSeparator().getBytes());
		}
	//	os.write(lineList.get(lineList.size()-1).getBytes());
	}

}
