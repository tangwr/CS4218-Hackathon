package sg.edu.nus.comp.cs4218;

import java.util.Arrays;
import java.util.List;

public class Constants {
	
    public static class Common {
    	public static final String NULL_ARGS = "Agrs is null";
    	public static final String INVALID_NUMBER_ARGUMENTS = "Invalid number of arguments";
    	public static final String INVALID_INPUT = "Invalid input";
    	public static final String INVALID_PATH = "Invalid path";

    	public static final Character WHITE_SPACE = ' ';
    	public static final Character DOUBLE_QUOTE = '"';
    	public static final Character SINGLE_QUOTE = '\'';
    	public static final Character BACK_QUOTE = '`';
    	public static final Character SEMICOLON = ';';
    	public static final Character IN_STREAM = '<';
    	public static final Character OUT_STREAM = '>';
    	public static final Character PIPE = '|';
    	public static final List<Character> SPECIALS = Arrays.asList(SEMICOLON, IN_STREAM, OUT_STREAM);
    }
    
    public class CalMessage {
    	public static final String STDOUT_IS_NULL = "Stdout is null";
    	public static final String INVALID_ARGS = "Agrs is null";
    	public static final String INVALID_NUMBER_ARGUMENTS = "Invalid number of arguments";
    	public static final String INVALID_INPUT = "Invalid input";
    }
    
    public class SortMessage {
    	public static final String INVALID_ARGS = "Invalid argument";
    	public static final String INVALID_NUM = "Invalid number type";
    	public static final String INVALID_OUT = "Invalid output";
    	public static final String INVALID_IN = "Invalid input";
    }
    
}
