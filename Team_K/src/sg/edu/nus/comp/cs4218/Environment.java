package sg.edu.nus.comp.cs4218;

import java.io.File;

import sg.edu.nus.comp.cs4218.exception.ShellException;

public final class Environment {
	
	/**
	 * Java VM does not support changing the current working directory. 
	 * For this reason, we use Environment.currentDirectory instead.
	 */
	public static final String DIR = "user.dir";
	private static volatile String currentDirectory = System.getProperty(DIR);
	
	private Environment() {
	};
	
	public static String getCurrentDirectory() {
		return currentDirectory;
	}
	
	public static void setCurrentDirectory(String dir) throws ShellException {
		File f = new File(dir);

		if (!f.exists()) {
			throw new ShellException("Directory does not exist");
		}

		if (!f.isDirectory()) {
			throw new ShellException("Not a directory");
		}
		currentDirectory = dir;
		System.setProperty(DIR, dir);
	}
}
