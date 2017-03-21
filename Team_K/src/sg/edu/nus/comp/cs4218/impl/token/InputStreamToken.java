package sg.edu.nus.comp.cs4218.impl.token;

import sg.edu.nus.comp.cs4218.exception.ShellException;

public class InputStreamToken extends AbstractToken {
	
	public InputStreamToken(String parent, int begin) {
		super(parent, begin);
	}

	@Override
	public boolean appendNext() {
		return false;
	}

	@Override
	public TokenType getType() {
		return TokenType.INPUT;
	}

	@Override
	public String value() {
		return String.valueOf(parent.charAt(begin));
	}

	@Override
	public void checkValid() throws ShellException {
		// Don't need to check anything
	}
}
