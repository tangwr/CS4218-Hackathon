package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

/*
 * Assumptions: 
 * 1) run function will call the correct functions with the correct inputs in the correct order separated by a space
 * 2) Run function will take inputs directly from shell unordered
 * 3) Args for run: unordered consisting of pattern and files
 * 4) Args for grepFromOneFile: pattern, file
 * 5) Args for grepFromMultipleFiles: pattern, file, file, ...
 * 6) Args for grepFromStdin: pattern (Stdin will be parsed from run)
 */

public class SampleGrepApplicationTest {
	private static final String REGEXMULTIOUT = "Hello Hello"+System.lineSeparator()+
			"ABC Hello"+System.lineSeparator()+"ello milo";
	private static final String NOMATCHFILE = "Pattern Not Found In File!";
	private static final String REGEXPATTERNOUT = "Hello Hello"+System.lineSeparator()+"ABC Hello";
	private static final String REGEXPATTERN = ".*ell";
	private static final String ABCSINGLEFILEOUT = "ABC Hello"+System.lineSeparator()+"ABCDEFGHI";
	private static final String ABCPATTERN = "ABC";
	private static final String HIEPATTERN = "hie";
	private static final String NOMATCHSTDIN = "Pattern Not Found In Stdin!";
	private GrepApplication grepApp;
	private String[] args;
	private FileInputStream stdin;
	private String fileName;
	private String fileName2;
	private String fileName3;
	private String directory;
	private String invalidFile;
	private ByteArrayOutputStream baos;
	PrintStream print;

	@Before
	public void setUp() throws FileNotFoundException {
		grepApp = new GrepApplication();
		stdin = new FileInputStream("sample-test/sg/edu/nus/comp/cs4218/impl/app/greptestdoc.txt");
		fileName = "sample-test/sg/edu/nus/comp/cs4218/impl/app/greptestdoc.txt";
		fileName2 = "sample-test/sg/edu/nus/comp/cs4218/impl/app/greptestdoc2.txt";
		fileName3 = "sample-test/sg/edu/nus/comp/cs4218/impl/app/testdoc.txt";
		invalidFile = "sample-test/sg/edu/nus/comp/cs4218/impl/app/abjkcsnakjc.txt";
		directory = "sample-test/sg/edu/nus/comp/cs4218/impl/app/";
		baos = new ByteArrayOutputStream();
		print = new PrintStream(baos);
		System.setOut(print);
	}

	@Test
	public void grepStdInNoMatchesFromRun() throws AbstractApplicationException {
		args = new String[1];
		args[0] = HIEPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("", baos.toString());
	}

//	@Test
//	public void grepStdInNoMatches() throws AbstractApplicationException {
//		args = new String[1];
//		grepApp.setData(grepApp.readFromInputStream(stdin));
//		args[0] = HIEPATTERN;
//		assertEquals(NOMATCHSTDIN, grepApp.grepFromStdin(args[0]));
//	}

	@Test
	public void grepStdInMatchesFromRun() throws AbstractApplicationException {
		args = new String[1];
		args[0] = ABCPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + System.lineSeparator(), baos.toString());
	}

//	@Test
//	public void grepStdInMatches() throws AbstractApplicationException {
//		args = new String[1];
//		grepApp.setData(grepApp.readFromInputStream(stdin));
//		args[0] = ABCPATTERN;
//		assertEquals(ABCSINGLEFILEOUT, grepApp.grepFromStdin(args[0]));
//	}

	@Test
	public void grepStdInRegexMatchesFromRun() throws AbstractApplicationException {
		args = new String[1];
		args[0] = REGEXPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXPATTERNOUT + System.lineSeparator(), baos.toString());
	}

//	@Test
//	public void grepStdInRegexMatches() throws AbstractApplicationException {
//		args = new String[1];
//		grepApp.setData(grepApp.readFromInputStream(stdin));
//		args[0] = REGEXPATTERN;
//		assertEquals(REGEXPATTERNOUT, grepApp.grepFromStdin(args[0]));
//	}

	@Test
	public void grepSingleFileNoMatchesFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = HIEPATTERN;
		args[1] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("", baos.toString());
	}

	@Test
	public void grepSingleFileMultipleMatchesInALineFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = "h";
		args[1] = fileName3;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("Boisterous he on understood attachment as entreaties ye devonshire."+System.lineSeparator()
				+ "Extremely ham any his departure for contained curiosity defective."+System.lineSeparator()
				+ "Way now instrument had eat diminution melancholy expression sentiments stimulated."+System.lineSeparator()
				+ "Mrs interested now his affronting inquietude contrasted cultivated."+System.lineSeparator()
				+ "Lasting showing expense greater on colonel no."+System.lineSeparator()
				+ "Prepared do an dissuade be so whatever steepest."+System.lineSeparator()
				+ "Yet her beyond looked either day wished nay."+System.lineSeparator()
				+ "Now curiosity you explained immediate why behaviour."+System.lineSeparator()
				+ "An dispatched impossible of of melancholy favourable."+System.lineSeparator()
				+ "Our quiet not heart along scale sense timed."+System.lineSeparator()
				+ "Consider may dwelling old him her surprise finished families graceful."+System.lineSeparator()
				+ "Is at purse tried jokes china ready decay an."+System.lineSeparator() + "Small its shy way had woody downs power."+System.lineSeparator()
				+ "Procured shutters mr it feelings."+System.lineSeparator() + "To or three offer house begin taken am at."+System.lineSeparator()
				+ "As dissuade cheerful overcame so of friendly he indulged unpacked."+System.lineSeparator()
				+ "An seeing feebly stairs am branch income me unable."+System.lineSeparator()
				+ "Celebrated contrasted discretion him sympathize her collecting occasional."+System.lineSeparator()
				+ "Do answered bachelor occasion in of offended no concerns."+System.lineSeparator()
				+ "Supply worthy warmth branch of no ye."+System.lineSeparator() + "Though wished merits or be."+System.lineSeparator()
				+ "Alone visit use these smart rooms ham."+System.lineSeparator() + "Course sir people worthy horses add entire suffer."+System.lineSeparator()
				+ "Strictly numerous outlived kindness whatever on we no on addition."+System.lineSeparator()
				+ "Are sentiments apartments decisively the especially alteration."+System.lineSeparator()
				+ "Thrown shy denote ten ladies though ask saw."+System.lineSeparator() + "Or by to he going think order event music."+System.lineSeparator()
				+ "Led income months itself and houses you. After nor you leave might share court balls."+System.lineSeparator(),
				baos.toString());
	}

//	@Test
//	public void grepSingleFileNoMatches() throws AbstractApplicationException {
//		args = new String[2];
//		grepApp.setData(grepApp.readFromFile(fileName));
//		args[0] = HIEPATTERN;
//		args[1] = fileName;
//		assertEquals(NOMATCHFILE, grepApp.grepFromOneFile(args[0] + " " + args[1]));
//	}

	@Test
	public void grepSingleFileMatchesFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = ABCPATTERN;
		args[1] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + System.lineSeparator(), baos.toString());
	}

//	@Test
//	public void grepSingleFileMatches() throws AbstractApplicationException {
//		args = new String[2];
//		grepApp.setData(grepApp.readFromFile(fileName));
//		args[0] = ABCPATTERN;
//		args[1] = fileName;
//		assertEquals(ABCSINGLEFILEOUT, grepApp.grepFromOneFile(args[0] + " " + args[1]));
//	}

	@Test
	public void grepSingleFileRegexMatchesFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = REGEXPATTERN;
		args[1] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXPATTERNOUT + System.lineSeparator(), baos.toString());
	}

//	@Test
//	public void grepSingleFileRegexMatches() throws AbstractApplicationException {
//		args = new String[2];
//		grepApp.setData(grepApp.readFromFile(fileName));
//		args[0] = REGEXPATTERN;
//		args[1] = fileName;
//		assertEquals(REGEXPATTERNOUT, grepApp.grepFromOneFile(args[0] + " " + args[1]));
//	}

	@Test
	public void grepMultipleFileNoMatchesFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = HIEPATTERN;
		args[1] = fileName;
		args[2] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("", baos.toString());
	}


	@Test
	public void grepMultipleFileMatchesFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = "DEF";
		args[1] = fileName;
		args[2] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("ABCDEFGHI"+System.lineSeparator()+"DEF" + System.lineSeparator(), baos.toString());
	}


	@Test
	public void grepMultipleFileRegexMatchesFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = REGEXPATTERN;
		args[1] = fileName;
		args[2] = fileName2;
		grepApp.run(args, stdin, baos);
		assertEquals(REGEXMULTIOUT + System.lineSeparator(), baos.toString());
	}


	@Test
	public void grepUnorderedInput1FromRun() throws AbstractApplicationException {
		args = new String[3];
		args[1] = fileName;
		args[0] = REGEXPATTERN;
		args[2] = fileName2;
		grepApp.run(args, stdin, baos);
		assertEquals(REGEXMULTIOUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepUnorderedInput2FromRun() throws AbstractApplicationException {
		args = new String[3];
		args[1] = fileName;
		args[2] = fileName2;
		args[0] = REGEXPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXMULTIOUT + System.lineSeparator(), baos.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void grepMultiFileDirectoryFromRun() throws AbstractApplicationException {
		args = new String[4];
		args[0] = fileName;
		args[1] = fileName2;
		args[2] = REGEXPATTERN;
		args[3] = directory;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected =AbstractApplicationException.class)
	public void grepDirectoryWithFileFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = REGEXPATTERN;
		args[1] = fileName;
		args[2] = directory;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXPATTERNOUT + "\n", baos.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void grepNoPatternFileFromRun() throws AbstractApplicationException {
		args = new String[1];
		args[0] = fileName;
		grepApp.run(args, null, baos);
		System.out.flush();
	}

	@Test
	public void grepNoPatternMultipleFileFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = fileName;
		args[1] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("", baos.toString());
	}

	@Test(expected = AbstractApplicationException.class)
	public void grepNoPatternStdInFromRun() throws AbstractApplicationException {
		args = new String[0];
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = AbstractApplicationException.class)
	public void grepDirectoryFromRun() throws AbstractApplicationException {
		args = new String[2];
		args[0] = REGEXPATTERN;
		args[1] = directory;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = AbstractApplicationException.class)
	public void grepMultiplePatternFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = REGEXPATTERN;
		args[1] = ABCPATTERN;
		args[2] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = AbstractApplicationException.class)
	public void invalidRegexFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = "[";
		args[1] = fileName2;
		args[2] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = AbstractApplicationException.class)
	public void invalidRegexFileFromRun() throws AbstractApplicationException {
		args = new String[3];
		args[0] = "[";
		args[1] = fileName2;
		args[2] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

}
