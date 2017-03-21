package sg.edu.nus.comp.cs4218.exception;

public class SedException extends AbstractApplicationException {

	private static final long serialVersionUID = 9105496071275898837L;

	public SedException(String message) {
		super("sed: "+message);
	}

}
