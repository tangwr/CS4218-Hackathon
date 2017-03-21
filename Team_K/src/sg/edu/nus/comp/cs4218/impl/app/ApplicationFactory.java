package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class ApplicationFactory {
	public static final String EXP_INVALID_APP = "Invalid app.";
	
	public ApplicationFactory() {
		
	}
	
	public static void runApp(String app, String[] argsArray,
			InputStream inputStream, OutputStream outputStream)
			throws AbstractApplicationException, ShellException {
		Application absApp = null;
		if (("cat").equals(app)) {// cat [FILE]...	(done?)
			absApp = new CatApplication();
		} else if (("cd").equals(app)) {	//done simple case
			absApp = new CdApplication();
		} else if (("pwd").equals(app)){	//done?
			absApp = new PwdApplication();
		} else if (("echo").equals(app)) {// echo [args]... (done?)
			absApp = new EchoApplication();
		} else if (("head").equals(app)) {// head [OPTIONS] [FILE] (done?)
			absApp = new HeadApplication();
		} else if (("tail").equals(app)) {// tail [OPTIONS] [FILE] (done?)
			absApp = new TailApplication();
		} else if (("grep").equals(app)){
			absApp = new GrepApplication();
		} else if (("cal").equals(app)){
			absApp = new CalApplication();
		} else if (("sort").equals(app)) {
			absApp = new SortApplication();
		} else if (("date").equals(app)) {
			absApp = new DateApplication();
		} else if (("wc").equals(app)) {
			absApp = new WcApplication();
		} else if (("sed").equals(app)) {
			absApp = new SedApplication();
		} else{ // invalid command
			throw new ShellException(app + ": " + EXP_INVALID_APP);
		}
		
		absApp.run(argsArray, inputStream, outputStream);
	}
	
}
