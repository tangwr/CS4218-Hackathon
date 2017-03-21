package sg.edu.nus.comp.cs4218.exception;

public class HeadException extends AbstractApplicationException {

	private static final long serialVersionUID = 268859084984324554L;
	public HeadException(String message) {
		super("head: "+message);
	}
}
