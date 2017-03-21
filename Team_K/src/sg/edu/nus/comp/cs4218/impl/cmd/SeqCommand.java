package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Utility;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken.TokenType;

public class SeqCommand implements Command {
	private List<String> commandList;
	private String inputCommand;
	
	public SeqCommand(String command) {
		this.inputCommand = command;
		this.commandList = splitCommand();
	}
	
	private List<String> splitCommand() {
		String curCmd = "";
		List<String> cmds = new ArrayList<String>();
		List<AbstractToken> tokens = Utility.tokenize(inputCommand);
		for (AbstractToken token : tokens) {
			if (token.getType() == TokenType.SEMICOLON) {	//semicolon will split commands
				if (!curCmd.trim().equals("")) {
					cmds.add(curCmd.trim());
					curCmd = "";
				}
			} else {
				curCmd += " " + token.toString();
			}
		}
		if (!curCmd.trim().equals("")) {
			cmds.add(curCmd.trim());
		}
		return cmds;
	}
	
	@Override
	public void evaluate(InputStream stdin, OutputStream stdout) throws AbstractApplicationException, ShellException {
		for (int i = 0; i < commandList.size(); i++) {
			try {
				Command command = CommandFactory.getCommand(commandList.get(i));
				command.evaluate(stdin ,stdout);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
	}

}
