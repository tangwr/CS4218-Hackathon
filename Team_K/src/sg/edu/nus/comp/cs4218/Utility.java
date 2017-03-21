package sg.edu.nus.comp.cs4218;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.impl.token.*;
import sg.edu.nus.comp.cs4218.Constants.Common;

public class Utility {	
	public static final String SPACE_SEPARATOR = "\\s+";
	public static final String ONE_SPACE = " ";

	/**
	 * Return 2D array of default value
	 * @param rowSize
	 * @param colSize
	 * @param defaultValue
	 * @return result
	 */
	public static int[][] initArray(int rowSize, int colSize, int defaultValue) {
		int[][] result = new int[rowSize][colSize];
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				result[i][j] = defaultValue;
			}
		}

		return result;
	}

	/**
	 * 
	 * @param date
	 * @return month
	 */
	public static int getMonth(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		return Integer.parseInt(dateFormat.format(date));
	}

	/**
	 * 
	 * @param date
	 * @return year
	 */
	public static int getYear(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return Integer.parseInt(dateFormat.format(date));		
	}

	/**
	 * 
	 * @param year
	 * @return boolean to know if this is leap year
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 !=0) || (year % 400 == 0);
	}

	public static boolean isValidMonth(int month) {
		return month >= 1 && month <= 12;
	}

	public static boolean isValidYear(int year) {
		return year >= 0;
	}

	/**
	 * 
	 * @param month
	 * @param year
	 * @return number of days in that month of year
	 */
	public static int getNumberOfDayForMonth(int month, int year) {
		switch (month) {
		case 1: case 3: case 5: case 7: case 8: case 10: case 12:
			return 31;
		case 4: case 6: case 9: case 11:
			return 30;
		case 2:
			if (isLeapYear(year)) {
				return 29;
			} else {			
				return 28;
			}
		default:
			return -1; // invalid
		}
	}

	/**
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return dayCode of week (from 0 to 6) as Sun to Sat
	 */
	public static int getDayOfWeek(int day, int month, int year) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = "" + year + "/";
		if (month < 10) {
			dateString += "0";
		}
		dateString += (month + "/");

		if (day < 10) {
			dateString += "0";
		}
		dateString += (day + " 00:00:00");

		try {
			Date dateObject = dateFormat.parse(dateString);
			dateFormat.applyPattern("EE");
			String dayText = dateFormat.format(dateObject);

			switch (dayText) {
			case "Sun":
				return 0;
			case "Mon":
				return 1;
			case "Tue":
				return 2;
			case "Wed":
				return 3;
			case "Thu":
				return 4;
			case "Fri":
				return 5;
			case "Sat":
				return 6;
			default:
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	public static String arrayToString(String[] array) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			output.append(array[i]);
			if (i != array.length - 1) {
				output.append(ONE_SPACE);
			}
		}

		return output.toString();
	}

	public static String[] stringToArray(String string) {
		return string.split(SPACE_SEPARATOR);
	}

	//Implement merge sort
	public static <T extends Comparable<T>> void sort (T[] values) {
		mergeSort(values, 0, values.length-1);
	}

	private static <T extends Comparable<T>> void mergeSort (T[] values, int left, int right) {
		if (left==right) {
			return;
		}
		int middle = (left+right)/2;

		//Recursively dividing
		mergeSort(values, left, middle);
		mergeSort(values, middle+1, right);
		//Merge partitions
		merge(values, left, middle, right);
	}

	private static <T extends Comparable<T>> void merge (T[] values, int left, int middle, int right) {
		int stepCount = right-left+1;

		int leftStep = left;
		T leftVal = null;

		int rightStep = middle+1;
		T rightVal = null;
		
		//Initialize tempArr with T objects of values' size
		//ArrayList<T> tempList = new ArrayList<T>(Collections.nCopies(stepCount, values[0]));
		ArrayList<T> tempList = new ArrayList<T>();
		//Merge the 2 lists' items iteratively
		for (int i = 0; i<stepCount; i++){
			if (leftStep <= middle &&rightStep <= right) {
				leftVal = values[leftStep];
				rightVal = values[rightStep];
			}
			//If 1 of the 2 lists is already exhausted, add the remaining sorted items to list
			else {
				if (leftStep == middle+1) {
					for (int j = rightStep; j<=right; j++) {
						rightVal = values[j];
						tempList.add(rightVal);
					}
				}
				else {
					for (int j = leftStep; j<=middle; j++) {
						leftVal = values[j];
						tempList.add(leftVal);
					}
				}
				break;
			}

			//Add the smaller value to tempList
			if(compare(leftVal, rightVal)<=0) {
				tempList.add(leftVal);
				leftStep++;
			}
			else {
				tempList.add(rightVal);
				rightStep++;
			}
		}
		for (int i = 0; i<stepCount; i++) {
			values[left + i] = tempList.get(i);
		}
	}

	//Compare 2 T objects as define in object comparator
	public static <T extends Comparable<T>> int compare(T first, T second) {
		return first.compareTo(second);
	}
	
	public static List<AbstractToken> tokenize(String input) {
		List<AbstractToken> tokens = new ArrayList<AbstractToken>();
		AbstractToken currentToken = null;
		
		for (int i = 0; i < input.length(); i++) {
			if (currentToken == null) {
				currentToken = generateToken(input, i);
			} else if (!currentToken.appendNext()) {
				tokens.add(currentToken);
				currentToken = generateToken(input, i);
			}
		}
		
		if (currentToken != null) {
			tokens.add(currentToken);
		}
		
		return tokens;
	}	
	
	public static AbstractToken generateToken(String parent, int begin) {
		Character firstChar = parent.charAt(begin);
		AbstractToken.TokenType type = AbstractToken.getType(firstChar);
		
		switch (type) {
		case SPACES:
			return new SpaceToken(parent, begin);
		case SEMICOLON:
			return new SemicolonToken(parent, begin);
		case INPUT:
			return new InputStreamToken(parent, begin);
		case OUTPUT:
			return new OutputStreamToken(parent, begin);
		case SINGLE_QUOTES:
			return new SingleQuoteToken(parent, begin);
		case DOUBLE_QUOTES:
			return new DoubleQuoteToken(parent, begin);
		case BACK_QUOTES:
			return new BackQuoteToken(parent, begin);
		case PIPE:
			return new PipeToken(parent, begin);
		case NORMAL:
			return new NormalToken(parent, begin);
		default:
			return null;
		}		
	}

	public static boolean isInStream(Character character) {
		return character == Common.IN_STREAM;
	}
	
	public static boolean isOutStream(Character character) {
		return character == Common.OUT_STREAM;
	}
	
	public static boolean isQuote(Character character) {
		return character.equals(Common.SINGLE_QUOTE) || character.equals(Common.DOUBLE_QUOTE) || character.equals(Common.BACK_QUOTE);
 	}
	
	public static boolean isNormalCharacter(Character character) {
		return !isQuote(character) && !Common.SPECIALS.contains(character) && !character.equals(Common.WHITE_SPACE);
	}

}
