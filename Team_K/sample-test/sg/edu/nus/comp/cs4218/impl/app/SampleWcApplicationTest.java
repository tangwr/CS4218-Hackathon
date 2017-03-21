package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.WcException;


public class SampleWcApplicationTest {
	private static final String FILE_SEPARATOR = File.separator;
	private static final String TEST_FILE_EMPTY = "sample-test/sg/edu/nus/comp/cs4218/impl/app/wcTestFiles/empty.txt";
	private static final String TEST_FILE_SINGLE_WORD = "sample-test/sg/edu/nus/comp/cs4218/impl/app/wcTestFiles/singleWord.txt";
	private static final String TEST_FILE_TITLES = "sample-test/sg/edu/nus/comp/cs4218/impl/app/titles.txt";
	private static final File TITLE_FILES = new File(TEST_FILE_TITLES);
	private static final long TITLE_FILES_TOTAL_BYTES = TITLE_FILES.length();
	private static final String TEST_FILE_NAME_HAS_SPACE = String.format("sample-test/sg/edu/nus/comp/cs4218/impl/app/name has space.txt");
	private static final String LINE_SEPARATOR = System.lineSeparator();

	WcApplication app;
	OutputStream stdout;
	InputStream stdin;
	
	private int getByteCount(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
		
			StringBuilder builder = new StringBuilder();
			int currentChar = reader.read();
			while (currentChar != -1) {
				builder.append((char) currentChar);
				currentChar = reader.read();
			}
			reader.close();
			return builder.length();
			} catch (IOException e) {
				fail();
			}
		return 0;
	}
	
	@Before
	public void setUp() throws Exception {
		app = new WcApplication();
		stdin = null;
		stdout = new ByteArrayOutputStream();
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithNullStdout() throws AbstractApplicationException {
		String[] args = null;
		app.run(args, stdin, null);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithNullArgsAndNullStdin() throws AbstractApplicationException {
		String[] args = null;
		app.run(args, null, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithEmptyArgsAndNullStdin() throws AbstractApplicationException {
		String[] args = new String[0];
		app.run(args, null, stdout);
	}

	@Test
	public void testWcWithEmptyFile() throws AbstractApplicationException {
		String filePath = TEST_FILE_EMPTY;
		String[] args = { filePath };
		app.run(args, stdin, stdout);
		assertEquals(String.format("0 0 0 %s%s", filePath, LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithNoFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		int byteCount = getByteCount(filePath);
		
		String[] args = { filePath };
		app.run(args, stdin, stdout);
		assertEquals(String.format(byteCount + " 1 1 %s%s", filePath, LINE_SEPARATOR), stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithInvalidFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-x", filePath };
		app.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithMixValidAndInvalidFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-m", "-x", "-l", filePath };
		app.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithInvalidComplexFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-mxl", filePath };
		app.run(args, stdin, stdout);
	}

	@Test
	public void testWcWithOnlyCharFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		int byteCount = getByteCount(filePath);
		String[] args = { "-m", filePath };
		app.run(args, stdin, stdout);
		assertEquals(String.format(byteCount + " %s%s", filePath, LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithOnlyWordFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", filePath };
		app.run(args, stdin, stdout);
		assertEquals(String.format("1 %s%s", filePath, LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithOnlyNewlineFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-l", filePath };
		app.run(args, stdin, stdout);
		assertEquals(String.format("1 %s%s", filePath, LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithComplexFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		int byteCount = getByteCount(filePath);
		String[] args = { "-m", "-l", "-w", filePath };
		app.run(args, stdin, stdout);
		assertEquals(String.format(byteCount+" 1 1 %s%s", filePath, LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithMultipleSingleFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", "-m", "-l", filePath };
		int byteCount = getByteCount(filePath);
		app.run(args, stdin, stdout);
		assertEquals(String.format("1 "+byteCount+" 1 %s%s", filePath, LINE_SEPARATOR), stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithMixFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", "-ml", "-mlw", filePath };
		app.run(args, stdin, stdout);
	}

	@Test
	public void testWcWithRepeativeSingleFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", "-w", "-w", filePath };
		app.run(args, stdin, stdout);
		assertEquals(String.format("1 1 1 " + filePath+ LINE_SEPARATOR), stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithDuplicateFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-wwwww", filePath };
		app.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithDifferentFlagOrders() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		OutputStream output1 = new ByteArrayOutputStream();
		String[] args1 = { "-wlm", filePath };
		app.run(args1, stdin, output1);
		String[] args2 = { "-lmw", filePath };
		OutputStream output2 = new ByteArrayOutputStream();
		app.run(args2, stdin, output2);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithOutOfOrderFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { filePath, "-w" };
		app.run(args, stdin, stdout);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithInOrderFlagAndOutOfOrderFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", filePath, "-w" };
		app.run(args, stdin, stdout);
	
	}

	@Test
	public void testWcWithMultipleFiles() throws AbstractApplicationException {
		String[] args = { TEST_FILE_EMPTY, TEST_FILE_SINGLE_WORD, TEST_FILE_TITLES };
		app.run(args, stdin, stdout);
		String emptyFileExpected = String.format("0 0 0 %s%s", TEST_FILE_EMPTY, LINE_SEPARATOR);
		int byteCount = getByteCount(TEST_FILE_SINGLE_WORD);
		String singleWordFileExpected = String.format(byteCount+" 1 1 %s%s", TEST_FILE_SINGLE_WORD,
				LINE_SEPARATOR);
		long titlesLength = (new File(TEST_FILE_TITLES)).length();
		String titlesFileExpected = String.format(String.valueOf(titlesLength)+" 717 251 %s%s", TEST_FILE_TITLES,
				LINE_SEPARATOR);
		String expectedResult = String.format("%s%s%s", emptyFileExpected, singleWordFileExpected, titlesFileExpected);
		assertEquals(expectedResult, stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcWithInvalidFiles() throws AbstractApplicationException {
		String[] args = { TEST_FILE_EMPTY, "-w", TEST_FILE_SINGLE_WORD };
		app.run(args, stdin, stdout);
	}

	@Test
	public void testWcReadFromStdin() throws AbstractApplicationException {
		String[] args = {};
		stdin = new ByteArrayInputStream("hello world".getBytes());
		app.run(args, stdin, stdout);
		String expected = String.format("11 2 1%s", LINE_SEPARATOR);
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testWcReadFromStdinWithFlag() throws AbstractApplicationException {
		String[] args = { "-m" };
		stdin = new ByteArrayInputStream("hello world".getBytes());
		app.run(args, stdin, stdout);
		String expected = String.format("11" + System.lineSeparator());
		assertEquals(expected, stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testWcReadFromStdinWithInvalidFlag() throws AbstractApplicationException {
		String[] args = { "-x" };
		stdin = new ByteArrayInputStream("hello world".getBytes());
		app.run(args, stdin, stdout);
	}

	@Test
	public void testWcWithBothStdinAndFile() throws AbstractApplicationException {
		String[] args = { TEST_FILE_SINGLE_WORD };
		stdin = new ByteArrayInputStream("not single word".getBytes());
		int byteCount = getByteCount(TEST_FILE_SINGLE_WORD);
		String singleWordFileExpected = String.format(byteCount +" 1 1 %s%s", TEST_FILE_SINGLE_WORD,
				LINE_SEPARATOR);
		app.run(args, stdin, stdout);
		assertEquals(singleWordFileExpected, stdout.toString());
	}

	@Test
	public void testWcWithFileNameContainSpace() throws AbstractApplicationException {
		String[] args = { TEST_FILE_NAME_HAS_SPACE };
		File testFile = new File(TEST_FILE_NAME_HAS_SPACE);
		long byteCount = testFile.length();
		String singleWordFileExpected = String.format("%d 6 6 %s%s", byteCount, TEST_FILE_NAME_HAS_SPACE,
				LINE_SEPARATOR);
		app.run(args, stdin, stdout);
		assertEquals(singleWordFileExpected, stdout.toString());
	}

	@Test
	public void testPrintCharacterCountInFile() throws AbstractApplicationException {
		String expected = String.format("%d %s%s", TITLE_FILES_TOTAL_BYTES, TEST_FILE_TITLES, LINE_SEPARATOR);
		app.run(new String[]{"-m", TEST_FILE_TITLES}, null, stdout);
		assertEquals(expected, stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void testPrintCharacterCountInInvalidFile() throws WcException {
		String result = app.printCharacterCountInFile("wc -m invalid.txt");
	}

	@Test
	public void testPrintWordCountInFile() throws WcException {
		String expected = String.format("717");
		String result = app.printWordCountInFile(String.format(TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test(expected = AbstractApplicationException.class)
	public void testPrintWordCountInInvalidFile() throws AbstractApplicationException {
		app.run(new String[]{"-w","invalid.txt"}, null, stdout);
	}

	@Test
	public void testPrintNewlineCountInFile() throws WcException {
		String expected = String.format("251");
		String result = app.printNewlineCountInFile(String.format(TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test(expected=AbstractApplicationException.class)
	public void testPrintNewlineCountInInvalidFile() throws WcException {
		app.printNewlineCountInFile("wc -m invalid.txt");
	}

	@Test
	public void printAllCountsInFile() throws AbstractApplicationException {
		String expected = String.format("%d 717 251 %s%s", TITLE_FILES_TOTAL_BYTES, TEST_FILE_TITLES,
				LINE_SEPARATOR);
		app.run(new String[]{TEST_FILE_TITLES}, null, stdout);
		assertEquals(expected, stdout.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void printAllCountsInInvalidFile() throws AbstractApplicationException {
		app.run(new String[]{"-m", "invalid.txt"}, null, stdout);
	}

	@Test
	public void printCharacterCountInStdin() throws FileNotFoundException, AbstractApplicationException {
		String expected = String.format("%d%s", TITLE_FILES_TOTAL_BYTES, LINE_SEPARATOR);
		app.run(new String[]{"-m"}, new FileInputStream(new File(TEST_FILE_TITLES)), stdout);
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void printWordCountInStdin() throws FileNotFoundException, AbstractApplicationException {
		String expected = String.format("717%s", LINE_SEPARATOR);
		app.run(new String[]{"-w"}, new FileInputStream(new File(TEST_FILE_TITLES)), stdout);
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void printNewlineCountInStdin() throws FileNotFoundException, AbstractApplicationException {
		String expected = String.format("251" + System.lineSeparator());
		app.run(new String[]{"-l"}, new FileInputStream(new File(TEST_FILE_TITLES)), stdout);
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void printAllCountsInStdin() throws FileNotFoundException, AbstractApplicationException {
		String expected = String.format("%d 717 251%s", TITLE_FILES_TOTAL_BYTES, LINE_SEPARATOR);
		app.run(new String[0],new FileInputStream(new File(TEST_FILE_TITLES)), stdout);
		assertEquals(expected, stdout.toString());
	}
}
