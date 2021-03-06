package sg.edu.nus.comp.cs4218.impl.token;

import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.Utility;

public class NormalToken extends AbstractToken {

	public NormalToken(String parent, int begin) {
		super(parent, begin);
	}

	@Override
	public boolean appendNext() {
		if (end >= parent.length() - 1) {
			return false;
		}

		Character nextChar = parent.charAt(end + 1);
		if (Utility.isNormalCharacter(nextChar)) {
			end++;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TokenType getType() {
		return TokenType.NORMAL;
	}

	@Override
	public String value() {
		return parent.substring(begin, end + 1);
	}

	@Override
	public void checkValid() throws ShellException {
		// Do not need to check anything
	}	
	
}
