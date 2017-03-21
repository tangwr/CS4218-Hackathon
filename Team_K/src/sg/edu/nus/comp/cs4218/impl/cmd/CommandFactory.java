package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.IOException;
import java.util.List;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Utility;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken.TokenType;

public class CommandFactory {
  
	public CommandFactory() {
		// constructor
	}
	
	public static Command getCommand(String cmdLine) throws ShellException, AbstractApplicationException, IOException {
		String trimmed = cmdLine.trim();
		List<AbstractToken> tokens = Utility.tokenize(trimmed);
		
		for (AbstractToken token : tokens) {
			if (token.getType() == TokenType.SEMICOLON) {
				return new SeqCommand(trimmed);
			}
		}
		
		for (AbstractToken token: tokens) {
			if (token.getType() == TokenType.PIPE) {
				return new PipeCommand(trimmed);
			}
		}
		
		return new CallCommand(trimmed);
	}
	
}
