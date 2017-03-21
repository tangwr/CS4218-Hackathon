package sg.edu.nus.comp.cs4218.exception;

public class PwdException extends AbstractApplicationException {
	private static final long serialVersionUID = 6127080722574334190L;

	public PwdException(String message) {
		super("pwd: "+ message);
	}

}
