package sg.edu.nus.comp.cs4218.impl.token;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.CommandFactory;

public class BackQuoteToken extends AbstractToken {
	
	public BackQuoteToken(String parent, int begin) {
		super(parent, begin);
	}
	
	public BackQuoteToken(String parent, int begin, int end) {
		super(parent, begin, end);
	}
	

	@Override
	public boolean appendNext() {
		if (end >= parent.length() - 1) {
			return false;
		}

		if (end > begin && parent.charAt(begin) == '`'
				&& parent.charAt(end) == '`') {
			return false;
		} else {
			end++;
			return true;
		}
	}

	@Override
	public TokenType getType() {
		return TokenType.BACK_QUOTES;
	}

	@Override
	public String value() throws ShellException, AbstractApplicationException {
		checkValid();
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		
		// run backquote command here
		// command substitution 
		String cmdLine = parent.substring(begin + 1, end);
		try {
			Command command = CommandFactory.getCommand(cmdLine);
			command.evaluate(null, byteOutStream);
		} catch (IOException e) {
			throw new ShellException("Error in executing backquote command");
		}
		
		return byteOutStream.toString();
	}

	@Override
	public void checkValid() throws ShellException {
		if (end > begin && parent.charAt(begin) == '`'
				&& parent.charAt(end) == '`') {
			return;
		}
		
		throw new ShellException("Quote does not match");
	}
}
