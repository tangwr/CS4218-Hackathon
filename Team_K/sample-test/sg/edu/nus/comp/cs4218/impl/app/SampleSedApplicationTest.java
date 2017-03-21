package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
//import sg.edu.nus.comp.cs4218.util.ReadableOutputStream;
//import sg.edu.nus.comp.cs4218.util.FileInputStream;

/**
 * Unit Test For Sed Application
 *
 */
public class SampleSedApplicationTest {
	private static final String FS = File.separator;
	private static final String NEWLINE = System.lineSeparator();
	private static final String TWO_LINE_FILE_PATH = "sample-test/sg/edu/nus/comp/cs4218/impl/app/sedTestFiles/two-lines.txt";
	private static final String EMPTY_FILE_PATH = "sample-test/sg/edu/nus/comp/cs4218/impl/app/sedTestFiles/empty.txt";
	private static final String NUMBER_FILE_PATH = "sample-test/sg/edu/nus/comp/cs4218/impl/app/sedTestFiles/number.txt";
	private static final String HELLO_WORLD_FILE_PATH = "sample-test/sg/edu/nus/comp/cs4218/impl/app/sedTestFiles/hello world.txt";

	private static SedApplication sed;
	private OutputStream stdout;
	private InputStream stdin;
	private static InputStream twoLineFileInputStream;
	private static InputStream emptyFileInputStream;
	private static InputStream numberFileInputStream;
	private static InputStream hellowWorldFileInputStream;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		Environment.resetCurrentDirectory();
		sed = new SedApplication();

	}

	@AfterClass
	public static void reset() {
	//	Environment.resetCurrentDirectory();
	}

	@Before
	public void setUp() throws Exception {
		stdin = null;
		stdout = new ByteArrayOutputStream();
		twoLineFileInputStream = new FileInputStream(new File(TWO_LINE_FILE_PATH));
		emptyFileInputStream = new FileInputStream(new File(EMPTY_FILE_PATH));
		numberFileInputStream = new FileInputStream(new File(NUMBER_FILE_PATH));
		hellowWorldFileInputStream = new FileInputStream(new File(HELLO_WORLD_FILE_PATH));
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithNullArgument() throws AbstractApplicationException {
		String args[] = null;
		stdin = hellowWorldFileInputStream;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with null args";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithEmptyArgument() throws AbstractApplicationException {
		String args[] = {};
		sed.run(args, null, stdout);
		// "error on sed command - fails to throw exception with empty args";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithSingleArgument() throws AbstractApplicationException {
		String args[] = { "arg1" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with insuffcient
		// args";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithNullStdout() throws AbstractApplicationException {
		String args[] = { "s-c-a-g", EMPTY_FILE_PATH };
		stdout = null;
		sed.run(args, null, stdout);
		// "error on sed command - fails to throw exception with empty stdout";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithNullStdinAndNonFileArg() throws AbstractApplicationException {
		String args[] = { "s|a|b|" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with null stdin and
		// non file arg";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithNullStdinAndNonExistentFile() throws AbstractApplicationException {
		String args[] = { "s|a|b|", "non-existent.txt" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// String msg =
		// "error on sed command - fails to throw exception with null stdin and
		// non-existent file ";
	}

	@Test
	public void testSedWithEmptyFile() throws AbstractApplicationException {
		String args[] = { "s|a|b|", EMPTY_FILE_PATH };
		stdin = null;
		String expected = NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFile() throws AbstractApplicationException {
		String args[] = { "s|a|b|", TWO_LINE_FILE_PATH };
		stdin = null;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is b small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithFileThatNameIncludesSpace() throws AbstractApplicationException {
		String args[] = { "s|1|2|", HELLO_WORLD_FILE_PATH };
		stdin = null;
		String expected = "hello world!" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with file whose name contains space";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithEmptyFileInputStream() throws AbstractApplicationException {
		String args[] = { "s|a|b|" };
		stdin = emptyFileInputStream;
		String expected = NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithNumberFileInputStream() throws AbstractApplicationException {
		String args[] = { "s*3*76*" };
		stdin = numberFileInputStream;
		String expected = "01276456789" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFileInputStream() throws AbstractApplicationException {
		// mock current directory to a fake non-root one
		String args[] = { "s|a|b|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is b small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFileInputStreamAndEmptyFile() throws AbstractApplicationException {
		String args[] = { "s|a|b|", EMPTY_FILE_PATH };
		stdin = twoLineFileInputStream;
		String expected = NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file inputstream and empty file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithExtraArgs1() throws AbstractApplicationException {
		String args[] = { "s|a|b|", EMPTY_FILE_PATH, "-l" };
		stdin = null;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithExtraArgs2() throws AbstractApplicationException {
		String args[] = { "s|0|1|", NUMBER_FILE_PATH, TWO_LINE_FILE_PATH, "-l" };
		stdin = null;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat1() throws AbstractApplicationException {
		String args[] = { "|0|1|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat2() throws AbstractApplicationException {
		// mock current directory to a fake non-root one
		String args[] = { "s|0|1|gg", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat3() throws AbstractApplicationException {
		String args[] = { "s-0|1|g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat4() throws AbstractApplicationException {
		String args[] = { "s-0|1g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat5() throws AbstractApplicationException {
		String args[] = { "s|0| m| |g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalRegex1() throws AbstractApplicationException {
		String args[] = { "s|0| m| |g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal regular expression";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat6() throws AbstractApplicationException {
		String args[] = { "s||0||1||", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat7() throws AbstractApplicationException {
		// mock current directory to a fake non-root one
		String args[] = { "s|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat8() throws AbstractApplicationException {
		String args[] = { "m|1|2|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithInvalidReplacementOnIllegalRegrex() throws AbstractApplicationException {
		String args[] = { "s|[|1|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal regrex";
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithGlobalReplacement() throws AbstractApplicationException {
		String args[] = { "s|l|*|g" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithDifferentSeparator1() throws AbstractApplicationException {
		String args[] = { "ssls*sg" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a sma** fi*e consists of {1+1+0} *ines."
				+ NEWLINE + "/* Hope this he*ps */ # no new *ine here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with different separator";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithDifferentSeparator2() throws AbstractApplicationException {
		String args[] = { "s/l/*/g" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithDifferentSeparator3() throws AbstractApplicationException {
		String args[] = { "s,l,*,g" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a sma** fi*e consists of {1+1+0} *ines."
				+ NEWLINE + "/* Hope this he*ps */ # no new *ine here" + NEWLINE;
		sed.run(args, stdin, stdout);
	}

	@Test
	public void testSedWithEmptyReplacment() throws AbstractApplicationException {
		String args[] = { "s|l||" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty replacement";
	}

	@Test
	public void testSedWithComplexReplacement1() throws AbstractApplicationException {
		String args[] = { "s|no| *&/s|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to k *&/sw <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ #  *&/s new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected=AbstractApplicationException.class)
	public void testSedWithComplexReplacement2() throws AbstractApplicationException {
		String args[] = { "s|o|[^]|" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
		
	}

	@Test
	public void testSedWithComplexReplacement3() throws AbstractApplicationException {
		String args[] = { "s|o   |% #$%^&|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithEmtpyRegexpAndEmptyReplacement() throws AbstractApplicationException {
		String args[] = { "s|||" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSedWithEmtpyRegexp() throws AbstractApplicationException {
		String args[] = { "s||m|g" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
		// String msg =
		// "error on sed command - fail to throw exception with empty regular
		// expression";
	}

	@Test
	public void testSedWithComplexRegexp1() throws AbstractApplicationException {
		String args[] = { "s|^This|r|g" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "r is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp2() throws AbstractApplicationException {
		String args[] = { "s|o{2,3}d*|r|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, gr to know <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp3() throws AbstractApplicationException {
		String args[] = { "s|[^a-zA-Z ]|-|g" };
		stdin = twoLineFileInputStream;
		String expected = "Hey- good to know -you--" + NEWLINE + "This is a small file consists of ------- lines-"
				+ NEWLINE + "-- Hope this helps -- - no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp4() throws AbstractApplicationException {
		String args[] = { "s-good|know-r-" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, r to know <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testReplaceFirstSubStringInFile() throws AbstractApplicationException {
		String cmd = "sed \"s|o||\"  " + TWO_LINE_FILE_PATH;
		String expected = "Hey, god to know <you>!" + NEWLINE + "This is a small file cnsists of {1+1+0} lines."
				+ NEWLINE + "/* Hpe this helps */ # no new line here" + NEWLINE;

		String msg = "error on sed command - incorrect output with method replaceFirstSubStringInFile";
		assertEquals(msg, expected, sed.replaceFirstSubStringInFile(cmd));
	}

	@Test
	public void testReplaceAllSubstringsInFile() throws AbstractApplicationException {
		String expected = "Hey, gd t knw <yu>!" + NEWLINE + "This is a small file cnsists f {1+1+0} lines." + NEWLINE
				+ "/* Hpe this helps */ # n new line here" + NEWLINE;

		String msg = "error on sed command - incorrect output with method replaceAllSubstringsInFile";
		String[] args = new String[]{"s|o||g", TWO_LINE_FILE_PATH};
		sed.run(args, null, stdout);
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testReplaceFirstSubStringFromStdin() throws AbstractApplicationException {
		String args[] = new String[]{"s|o||", TWO_LINE_FILE_PATH};
		String expected = "Hey, god to know <you>!" + NEWLINE + "This is a small file cnsists of {1+1+0} lines."
				+ NEWLINE + "/* Hpe this helps */ # no new line here" + NEWLINE;
		sed.run(args, null, stdout);
		String msg = "error on sed command - incorrect output with method replaceFirstSubStringFromStdin";
		assertEquals(msg, expected, stdout.toString());
	}
}
