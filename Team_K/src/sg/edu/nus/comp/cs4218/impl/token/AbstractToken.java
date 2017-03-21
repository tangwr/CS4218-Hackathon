package sg.edu.nus.comp.cs4218.impl.token;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public abstract class AbstractToken {
	
	public enum TokenType {
		SPACES, NORMAL, SEMICOLON, PIPE, INPUT, OUTPUT, SINGLE_QUOTES, DOUBLE_QUOTES, BACK_QUOTES
	}
	
	String parent;
	int begin;
	int end;
	
	public AbstractToken(String parent, int begin) {
		this.parent = parent;
		this.begin = begin;
		this.end = begin;
	}
	
	public AbstractToken(String parent, int begin, int end) {
		this.parent = parent;
		this.begin = begin;
		this.end = end;
	}
	
	public abstract boolean appendNext();
	
	public abstract TokenType getType();
	
	public abstract String value() throws ShellException, AbstractApplicationException;
	
	public abstract void checkValid() throws ShellException;
	
	public static TokenType getType(Character firstChar) {
		switch (firstChar) {
		case ' ':
			return TokenType.SPACES;
		case '<':
			return TokenType.INPUT;
		case '>':
			return TokenType.OUTPUT;
		case ';':
			return TokenType.SEMICOLON;
		case '"':
			return TokenType.DOUBLE_QUOTES;
		case '`':
			return TokenType.BACK_QUOTES;
		case '\'':
			return TokenType.SINGLE_QUOTES;
			
		case '|':
			return TokenType.PIPE;
			
		default:
			return TokenType.NORMAL;
		}
	}	
	
	@Override
	public String toString() {
		return parent.substring(begin, end + 1);
	}	

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
}
