package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Utility;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellThread;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken;
import sg.edu.nus.comp.cs4218.impl.token.AbstractToken.TokenType;

public class PipeCommand implements Command {
	private List<CallCommand> commands;
	private List<ShellThread> threads;
	
	public PipeCommand(String commandLine) throws ShellException, AbstractApplicationException {
		this.commands = splitCommand(commandLine);
	}
	
	public List<CallCommand> splitCommand(String commandLine) throws ShellException, AbstractApplicationException {
		List<CallCommand> commands = new ArrayList<CallCommand>();
		String currentCommand = "";
		
		List<AbstractToken> tokens = Utility.tokenize(commandLine);
		for (AbstractToken token: tokens) {
			if (token.getType() == TokenType.PIPE) {
				try {
					Command command = CommandFactory.getCommand(currentCommand);
					commands.add((CallCommand) command);
					currentCommand = "";
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				currentCommand += token.toString();
			}
		}
		
		if (!currentCommand.trim().equals("")) {
			try {
				CallCommand command = (CallCommand) CommandFactory.getCommand(currentCommand);
				commands.add(command);
			} catch (Exception e) {
				e.printStackTrace();
			}
;		}
		
		return commands;
	}
	
	@Override
	public void evaluate(InputStream stdin, OutputStream stdout) throws ShellException, AbstractApplicationException {
		try {
			threads = new ArrayList<ShellThread>();
			
			// setup streams
			InputStream currentInput = stdin; // default
			OutputStream currentOutput = stdout;
			
			for (int i = 0; i < commands.size(); i++) {
				CallCommand command = commands.get(i);
				if (i != commands.size() - 1) {
					currentOutput = new PipedOutputStream();
				} else {
					currentOutput = stdout;
				}
				
				ShellThread thread = new ShellThread(command, currentInput, currentOutput);
				threads.add(thread);
				
				if (i < commands.size() - 1) {
					currentInput = new PipedInputStream((PipedOutputStream) currentOutput);
				}
			}
			
			// start all threads
			for (Thread thread: threads) {
				thread.start();
			}
			
			// wait for all threads to complete
			waitForAllThreads();
		} catch (IOException e) {
			throw new ShellException(e.getMessage());
		} finally {
			terminate();
		}
	}
	
	private void waitForAllThreads() throws ShellException, AbstractApplicationException {
		boolean running = true;
		
		while (running) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				throw new ShellException(e.getMessage());
			}
			
			running = false;
			for (ShellThread thread: threads) {
				if (thread.isAlive()) {
					running = true;
					break;
				} else if (thread.getShellException() != null) {
					throw thread.getShellException();
				} else if (thread.getAppException() != null) {
					throw thread.getAppException();
				}
			}
		}
	}
	
	@Override
	public void terminate() {
		if (threads != null) {
			for (ShellThread thread: threads) {
				thread.interrupt();
			}
		}
	}
}
