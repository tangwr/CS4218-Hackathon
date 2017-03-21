package sg.edu.nus.comp.cs4218.exception;

public class CalException extends AbstractApplicationException{
	private static final long serialVersionUID = 9185605254209285472L;

	public CalException(String message) {
		super("cal: " + message);
	}
	
}
