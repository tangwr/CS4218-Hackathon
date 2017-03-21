package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.app.Wc;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.WcException;

public class WcApplication implements Wc {
	//The wc command prints the number of bytes, words, and lines in given files (followed by a newline).
	/*wc [OPTIONS] [FILE]...
	OPTIONS
	-m : Print only the character counts
	-w : Print only the word counts
	-l : Print only the newline counts
	FILE – the file(s), when no file is present, use stdin.*/
	private static final String CHARACTER_OPTION = "-m";
	private static final String WORD_OPTION = "-w";
	private static final String NEWLINE_OPTION = "-l";
	private static List<String> files;
	private static List<String> fileNames;
	private static List<String> options;
	private static InputStream inputStream;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if (args == null || stdout == null) {
			throw new WcException("Null Pointer Exception");
		}
		String output = "";
		options = new ArrayList<String>();
		files = new ArrayList<String>();
		fileNames = new ArrayList<String>();
	
		extractArguments(args);
		if (files.size() == 0) {		//use stdin
			if (stdin == null) {
				throw new WcException("Null Stdin");
			}
			inputStream = stdin;
			if (options.size() == 0) {
				output = printAllCountsInStdin("");
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				String streamContents = null;
				try {
					streamContents = outputString(br);
				} catch (IOException e) {
					throw new WcException("Cannot read input stream");
				}
				
				for (int i = 0; i < options.size(); i++) {
					switch (options.get(i)) {
					case CHARACTER_OPTION:
						output += printCharacterCountInStdin(streamContents);
						break;
					case WORD_OPTION:
						output += printWordCountInStdin(streamContents);
						break;
					case NEWLINE_OPTION:
						output += printNewlineCountInStdin(streamContents);
						break;
					default:
						throw new WcException("Unexpected error occured");
					}
					if (i != options.size() - 1) {
						output += " ";
					}
				}
				output += System.lineSeparator();
			}
		} else {		//use files
			if (options.size() == 0) {
				output = printAllCountsInFile("");
			} else {
				for (int i = 0; i < files.size(); i++) {
					String filePath = files.get(i);
					for (int j = 0; j < options.size(); j++) {
						switch (options.get(j)) {
						case CHARACTER_OPTION:
							output += printCharacterCountInFile(filePath) + " ";
							break;
						case WORD_OPTION:
							output += printWordCountInFile(filePath) + " ";
							break;
						case NEWLINE_OPTION:
							output += printNewlineCountInFile(filePath) + " ";
							break;
						default:
							throw new WcException("Unexpected error occured");
						}
					}
					output += fileNames.get(i) + System.lineSeparator();
				}
			}			
		}
		try {
			stdout.write(output.getBytes());
		} catch (IOException e) {
			throw new WcException("Error writing to stdout");
		}
	}
	
	private String getFullPath(String fileName) throws WcException {
		File file = new File(fileName);
		if (!file.isAbsolute()) {
			try {
				file = file.getCanonicalFile();
			} catch (IOException e) {
				throw new WcException("Cannot get canonical path of file "+fileName);
			}
		}
		if (file.isFile()) {
			return file.getAbsolutePath();
		} else {
			throw new WcException(fileName + " is not existed");
	
		}
	}
	
	private void extractArguments(String[] args) throws WcException {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(CHARACTER_OPTION) || args[i].equals(WORD_OPTION)
					|| args[i].equals(NEWLINE_OPTION)) {
				options.add(args[i]);
			} else {
				for (int j = i; j < args.length; j++) {
					String filePath = getFullPath(args[j]);
					files.add(filePath);
					fileNames.add(args[j]);
				}
				break;
			}
		}
	}

	@Override
	public String printCharacterCountInFile(String args) throws WcException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(args));
			String fileContents = outputString(br);
			//Uncomment this to convert Windows to Linux counts
			//fileContents = fileContents.replace(System.lineSeparator(), "\n");
			Integer bytesLength = getByteCount(fileContents);
			return bytesLength.toString();
		} catch (Exception e) {
			throw new WcException(e.toString());
		}
	}

	@Override
	public String printWordCountInFile(String args) throws WcException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(args));
			String fileContents = outputString(br);
			Integer wordsLength = getWordCount(fileContents);
			return wordsLength.toString();
		} catch (Exception e) {
			throw new WcException(e.toString());
		}
	}

	@Override
	public String printNewlineCountInFile(String args) throws WcException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(args));
			String fileContents = outputString(br);
			Integer lineLength = getLineCount(fileContents);
			return lineLength.toString();
		} catch (Exception e) {
			throw new WcException(e.toString());
		}
	}

	@Override
	public String printAllCountsInFile(String args) throws WcException {
		if (files == null || fileNames == null) {
			throw new WcException("Null Pointer Exception");
		}
		String output = "";
		for (int i = 0; i < files.size(); i++) {
			String filePath = files.get(i);
			output += printCharacterCountInFile(filePath) + " ";
			output += printWordCountInFile(filePath) + " ";
			output += printNewlineCountInFile(filePath) + " ";
			output += fileNames.get(i) + System.lineSeparator();
		}
		return output;
	}

	@Override
	public String printCharacterCountInStdin(String args) throws WcException {
		try{
			Integer bytesLength = getByteCount(args);
			return bytesLength.toString();
		} catch (Exception e) {
			throw new WcException(e.toString());
		}
	}

	@Override
	public String printWordCountInStdin(String args) throws WcException {
		try{
			Integer bytesLength = getWordCount(args);
			return bytesLength.toString();
		} catch (Exception e) {
			throw new WcException(e.toString());
		}
	}
	

	@Override
	public String printNewlineCountInStdin(String args) throws WcException {
		try{
			Integer bytesLength = getLineCount(args);
			return bytesLength.toString();
		} catch (Exception e) {
			throw new WcException(e.toString());
		}
	}

	@Override
	public String printAllCountsInStdin(String args) throws WcException {
		if (inputStream == null) {
			throw new WcException("Null Pointer Exception");
		}
		String output = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		try {
			String streamContents = outputString(br);
			output += printCharacterCountInStdin(streamContents) + " ";
			output += printWordCountInStdin(streamContents) + " ";
			output += printNewlineCountInStdin(streamContents) + System.lineSeparator();
		} catch (Exception e) {
			throw new WcException(e.toString());
		}
		return output;
	}

	private static String outputString(BufferedReader reader)
			throws IOException {
		StringBuilder builder = new StringBuilder();
		int currentChar = reader.read();
		while (currentChar != -1) {
			builder.append((char) currentChar);
			currentChar = reader.read();
		}
		reader.close();
		return builder.toString();
	}

	private static int getByteCount(String fileContents) throws WcException {
		if (fileContents == null) {
			throw new WcException("Null Pointer Exception");
		}

		return fileContents.length();
	}
	
	private static int getWordCount(String fileContents) throws WcException {
		int count = 0;
		if (fileContents == null) {
			throw new WcException("Null Pointer Exception");
		}

		if (fileContents.length() == 0)
			return 0;

		String[] tokenList = fileContents.trim().split("\\s+");
		for (String token : tokenList) {
			if (token.length() > 0) {
				count++;
			}
		}
		return count;
	}

	private static int getLineCount(String fileContents) throws WcException {
		if (fileContents == null) {
			throw new WcException("Null Pointer Exception");
		}
		if (fileContents.length() == 0) {
			return 0;
		}
		int count = 1;
		for (int i = 0; i < fileContents.length(); i++) {
			if (fileContents.charAt(i) == '\n') {
				count++;
			}
		}
		return count;
	}

}
