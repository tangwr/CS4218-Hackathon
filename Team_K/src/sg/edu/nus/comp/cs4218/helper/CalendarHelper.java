package sg.edu.nus.comp.cs4218.helper;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Utility;

public class CalendarHelper {
	
	public static final int MONTHS_PER_YEAR = 12;
    public static final int CALENDAR_ROW_SIZE = 6;
    public static final int CALENDAR_COL_SIZE = 7;
    public static final int YEAR_ROW_SIZE = 4;
    public static final int YEAR_COL_SIZE = 3;
    public static final String NEW_LINE = "\n";
    public static final String SPACE_SEPARATOR = "\\s+";
    public static final String SPACE_BETWEEN_MONTH = "        ";
	public static final String WHITE_SPACE = " ";
	public static final String ONE_SPACE = " ";
	public static final String TWO_SPACE = "  ";
	public static final String THREE_SPACE = "   ";
	public static final String YEAR_SPACE = "                              ";
    public static final String[] MONTH_TO_TEXT = {
        	"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    };
	
	/**
	 * 
	 * @param month
	 * @param year
	 * @param mondayFirst
	 * @return 2D calendar array
	 */
	public static int[][] getCalendarArrayForMonth(int month, int year, boolean mondayFirst) {
		int[][] calendarArr = Utility.initArray(CALENDAR_ROW_SIZE, CALENDAR_COL_SIZE, -1);
		int maxDay = Utility.getNumberOfDayForMonth(month, year);
		int dayIndex = Utility.getDayOfWeek(1, month, year);
		if (mondayFirst) {
			dayIndex = (dayIndex + 7 - 1) % 7;
		}
		
		for (int i = 1; i <= maxDay; i++) {
			int rowIndex = dayIndex / CALENDAR_COL_SIZE;
			int colIndex = dayIndex % CALENDAR_COL_SIZE;
			calendarArr[rowIndex][colIndex] = i;
			dayIndex++;
		}
		
		return calendarArr;
	}
	
	/**
	 * Create calendar title string
	 * @param month
	 * @param year
	 */
	public static String generateCalTitle(int month, int year, boolean withYear) {
		int calendarWidth = 20;
		StringBuilder output = new StringBuilder();
		
		String calendarTitle = MONTH_TO_TEXT[month - 1];
		if (withYear) {
			calendarTitle += (" " + year);
		}
		
		int space = (calendarWidth - calendarTitle.length()) / 2;
		for (int i = 0; i < space; i++) {
			output.append(ONE_SPACE);
		}
		
		output.append(calendarTitle);
		for (int i = 0; i < space; i++) {
			output.append(ONE_SPACE);
		}
		
		return output.toString();
	}
	
	/**
	 * Generate calendar header string
	 * @param mondayFirst
	 */	
	public static String generateCalHeaders(boolean mondayFirst) {
		if (mondayFirst) {
			return "Mo Tu We Th Fr Sa Su ";
		} else {
			return "Su Mo Tu We Th Fr Sa ";
		}		
	}
	
	public static String generateCalendarForMonthYear(int month, int year, boolean mondayFirst) {
		int[][] monthArr = getCalendarArrayForMonth(month, year, mondayFirst);
		StringBuilder output = new StringBuilder();
		output.append(generateCalTitle(month, year, true));
		output.append(NEW_LINE);
		
		output.append(generateCalHeaders(mondayFirst));
		output.append(NEW_LINE);
		
		for (int i = 0; i < CALENDAR_ROW_SIZE; i++) {
			for (int j = 0; j < CALENDAR_COL_SIZE; j++) {				
				if (monthArr[i][j] == -1) {
					output.append(THREE_SPACE);
				} else if (monthArr[i][j] < 10) {
					output.append(monthArr[i][j] + TWO_SPACE);
				} else {
					output.append(monthArr[i][j] + ONE_SPACE);
				}
			}
			
			output.append(NEW_LINE);
		}
		
		return output.toString();
	}
	
	public static String generateCalendarForYear(int year, boolean isMondayFirst) {
		StringBuilder output = new StringBuilder();
		
		output.append(YEAR_SPACE);
		output.append(year);
		output.append(NEW_LINE);
		
		List<int[][]> yearArray = new ArrayList<int[][]>();
		for (int month = 1; month <= MONTHS_PER_YEAR; month++) {
			int[][] monthArr = getCalendarArrayForMonth(month, year, isMondayFirst);
			yearArray.add(monthArr);
		}

		for (int month = 1; month <= MONTHS_PER_YEAR; month += YEAR_COL_SIZE) {
			output.append(generateMonthCalendarInRow(year, isMondayFirst, month, month + YEAR_COL_SIZE - 1, yearArray));
			output.append(NEW_LINE);
		}
		
		return output.toString();
	}
	
	public static String generateMonthCalendarInRow(int year, boolean mondayFirst, int startMonth, int endMonth, List<int[][]> yearArray) {
		StringBuilder output = new StringBuilder();
		
		for (int month = startMonth; month <= endMonth; month++) {
			output.append(generateCalTitle(month, year, false));
			if (month != endMonth) {
				output.append(SPACE_BETWEEN_MONTH);
				output.append(TWO_SPACE);
			}
		}
		output.append(NEW_LINE);
		
		for (int month = startMonth; month <= endMonth; month++) {
			output.append(generateCalHeaders(mondayFirst));
			if (month != endMonth) {
				output.append(SPACE_BETWEEN_MONTH);
			}
		}
		output.append(NEW_LINE);
		
		for (int i = 0; i < CALENDAR_ROW_SIZE; i++) {
			for (int month = startMonth; month <= endMonth; month++) {
				int[][] monthArr = yearArray.get(month - 1);
				for (int j = 0; j < CALENDAR_COL_SIZE; j++) {
					if (monthArr[i][j] == -1) {
						output.append(THREE_SPACE);
					} else if (monthArr[i][j] < 10) {
						output.append(monthArr[i][j] + TWO_SPACE);
					} else {
						output.append(monthArr[i][j] + ONE_SPACE);
					}
				}
				
				if (month != endMonth) {
					output.append(SPACE_BETWEEN_MONTH);
				}
			}
			output.append(NEW_LINE);
		}
		
		return output.toString();
	}    
}
