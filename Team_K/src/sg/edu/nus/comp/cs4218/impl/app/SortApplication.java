package sg.edu.nus.comp.cs4218.impl.app;

import java.util.*;
import java.io.*;


import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.Constants;
import sg.edu.nus.comp.cs4218.Utility;


public class SortApplication implements Sort {
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		String toSort = "";
		String result = "";
		BufferedReader br;
		//Check case invalid args
		if (args == null || args.length == 0) {
			if (stdin != null) {
				br = new BufferedReader(new InputStreamReader(stdin));
				toSort = readStdin(br);
				result = sortAll(toSort);
			}
			else {
				throw new SortException(Constants.Common.NULL_ARGS);
			}
		}
		else if (args.length>2) {
			throw new SortException(Constants.Common.INVALID_NUMBER_ARGUMENTS);
		}

		//Normal sort
		else if (args.length == 1) {
			//Sort from file
			if (!args[0].equals("-n")) {
				toSort = readFile(args[0]);
				result = sortAll(toSort);	
			}
			//Sort from stdin with st words treated as numbers
			else {
				if (stdin != null) {
					br = new BufferedReader(new InputStreamReader(stdin));
					ArrayList<String> toSortList = readStdinNum(br);
					result = sortFirstAsNum(toSort, toSortList);
				}
				else {
					throw new SortException(Constants.Common.NULL_ARGS);

				}
			}

		}
		//Sort from file with condition of 1st words treated as numbers
		else if (args.length == 2) {
			if (!args[0].equals("-n")) {
				throw new SortException(Constants.SortMessage.INVALID_ARGS);
			}
			ArrayList<String> toSortList = readFileNum(args[1]);
			result = sortFirstAsNum(toSort, toSortList);
		}
		try {
			stdout.write((result + System.lineSeparator()).getBytes());
		} catch (Exception e) {
			throw new SortException(Constants.SortMessage.INVALID_OUT);
		}
	}

	private String sortFirstAsNum(String toSort, ArrayList<String> toSortList) throws AbstractApplicationException {
		String result = "";
		Hashtable<String, String> numTable = new Hashtable<String, String>();
		for (int i =0; i<toSortList.size(); i++) {
			String curLine = toSortList.get(i);
			int index = curLine.indexOf(" ");
			String firstWord = "";
			String restLine = "";
			if (index !=-1) {
				firstWord = curLine.substring(0, index);
				restLine = curLine.substring(index);
			}
			else {
				firstWord = curLine;
			}
			numTable.put(firstWord, restLine);
			toSort += firstWord;
			if (i<toSortList.size()-1) {
				toSort += System.lineSeparator();
			}
		}

		String[] sortedList = sortNumbers(toSort).split(System.lineSeparator());
		for (int i = 0; i<sortedList.length; i++) {
			String firstWord = sortedList[i];
			result = result + firstWord + numTable.get(firstWord);
			if (i<sortedList.length-1) {
				result += System.lineSeparator();
			}
		}
		return result;
	}

	private String readFile(String filePath) throws SortException {
		String toSort = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = br.readLine();
			while(line!=null){
				toSort += line;
				line = br.readLine();
				if(line!=null){
					toSort += System.lineSeparator();
				}
			}
			br.close();
		} catch (Exception e) {
			throw new SortException(Constants.Common.INVALID_PATH);
		}
		return toSort;
	}

	private ArrayList<String> readFileNum(String filePath) throws SortException {
		ArrayList<String> toSort = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = br.readLine();
			while(line != null){
				toSort.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			throw new SortException(Constants.Common.INVALID_PATH);
		}
		return toSort;
	}

	private String readStdin(BufferedReader br) throws SortException {
		String toSort = "";
		try {
			String line = br.readLine();
			while (line!=null) {
				toSort += line;
				line = br.readLine();
				if(line!=null) {
					toSort += System.lineSeparator();
				}
			}
		} catch (Exception e) {
			throw new SortException(Constants.SortMessage.INVALID_IN);
		}
		return toSort;
	}

	private ArrayList<String> readStdinNum(BufferedReader br) throws SortException {
		ArrayList<String> toSort = new ArrayList<String>();
		try {
			String line = br.readLine();
			while(line!=null){
				toSort.add(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			throw new SortException(Constants.SortMessage.INVALID_IN);
		}
		return toSort;
	}


	@Override
	public String sortStringsSimple(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortStringsCapital(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortNumbers(String toSort) throws AbstractApplicationException {
		String result = "";
		String[] stringList = toSort.split(System.lineSeparator());
		Double[] numList = new Double[stringList.length];
		boolean allNumber = true;
		boolean isInt = true;
		for (int i = 0; i < stringList.length; i++) {
			try {
				Double tempNum = Double.parseDouble(stringList[i]);
				numList[i] = tempNum;
				if (tempNum != Math.floor(tempNum) || Double.isInfinite(tempNum)) {
					isInt = false;
				}
			}
			catch (Exception e) {
				allNumber = false;
				throw new SortException(Constants.SortMessage.INVALID_NUM);
			}
		}
		if (allNumber) {
			Utility.sort(numList);
			for (int i = 0; i<numList.length; i++) {
				if(isInt) {
					result += String.valueOf((numList[i].intValue()));
				}
				else {
					result += String.valueOf(numList[i]);
				}
				if (i < numList.length-1) {
					result += System.lineSeparator();
				}
			}
		}
		return result;
	}

	@Override
	public String sortSpecialChars(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortSimpleCapital(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortSimpleNumbers(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortSimpleSpecialChars(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortCapitalNumbers(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortCapitalSpecialChars(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortNumbersSpecialChars(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortSimpleCapitalNumber(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortSimpleCapitalSpecialChars(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortSimpleNumbersSpecialChars(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortCapitalNumbersSpecialChars(String toSort) {
		return sortAll(toSort);
	}

	@Override
	public String sortAll(String toSort) {
		String result = "";
		String[] toSortArr = toSort.split(System.lineSeparator());
		Utility.sort(toSortArr);
		for (int i = 0; i<toSortArr.length; i++) {
			result += toSortArr[i];
			if (i < toSortArr.length-1) {
				result += System.lineSeparator();
			}
		}
		return result;
	}

}
