package sg.edu.nus.comp.cs4218.impl.app;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;

public class SampleSortApplicationTest { 
  
    SortApplication ssa;
	String args[];
	String fileName;
	InputStream stdin;
	InputStream numericStdin;
	InputStream emptyStdin;
	String numericFile;
	String testMethodsFile;
	String emptyFile;
	String reorderStr1 = "test.txt";
	String reorderStr2 = "test1.txt";
	
 	String defaultString = "Boisterous he on understood attachment as entreaties ye devonshire." + System.lineSeparator() +
							"In mile an form snug were been sell." + System.lineSeparator() +
							"Hastened admitted joy nor absolute gay its." + System.lineSeparator() +
							"Extremely ham any his departure for contained curiosity defective." + System.lineSeparator() +
							"Way now instrument had eat diminution melancholy expression sentiments stimulated." + System.lineSeparator() +
							"One built fat you out manor books." + System.lineSeparator() +
							"Mrs interested now his affronting inquietude contrasted cultivated." + System.lineSeparator() +
							"Lasting showing expense greater on colonel no." + System.lineSeparator() + System.lineSeparator() +
							
							"Prepared do an dissuade be so whatever steepest." + System.lineSeparator() +
							"Yet her beyond looked either day wished nay." + System.lineSeparator() +
							"By doubtful disposed do juvenile an." + System.lineSeparator() +
							"Now curiosity you explained immediate why behaviour." + System.lineSeparator() +
							"An dispatched impossible of of melancholy favourable." + System.lineSeparator() +
							"Our quiet not heart along scale sense timed." + System.lineSeparator() +
							"Consider may dwelling old him her surprise finished families graceful." + System.lineSeparator() +
							"Gave led past poor met fine was new." + System.lineSeparator() + System.lineSeparator() +
							
							"Is at purse tried jokes china ready decay an." + System.lineSeparator() +
							"Small its shy way had woody downs power." + System.lineSeparator() +
							"To denoting admitted speaking learning my exercise so in." + System.lineSeparator() +
							"Procured shutters mr it feelings." + System.lineSeparator() +
							"To or three offer house begin taken am at." + System.lineSeparator() +
							"As dissuade cheerful overcame so of friendly he indulged unpacked." + System.lineSeparator() +
							"Alteration connection to so as collecting me." + System.lineSeparator() +
							"Difficult in delivered extensive at direction allowance." + System.lineSeparator() +
							"Alteration put use diminution can considered sentiments interested discretion." + System.lineSeparator() +
							"An seeing feebly stairs am branch income me unable." + System.lineSeparator() + System.lineSeparator() +
							
							"No comfort do written conduct at prevent manners on." + System.lineSeparator() +
							"Celebrated contrasted discretion him sympathize her collecting occasional." + System.lineSeparator() +
							"Do answered bachelor occasion in of offended no concerns." + System.lineSeparator() +
							"Supply worthy warmth branch of no ye." + System.lineSeparator() +
							"Voice tried known to as my to." + System.lineSeparator() +
							"Though wished merits or be." + System.lineSeparator() +
							"Alone visit use these smart rooms ham." + System.lineSeparator() +
							"No waiting in on enjoyed placing it inquiry." + System.lineSeparator() + System.lineSeparator() +
							
							"Fat new smallness few supposing suspicion two." + System.lineSeparator() +
							"Course sir people worthy horses add entire suffer." + System.lineSeparator() +
							"How one dull get busy dare far." + System.lineSeparator() +
							"At principle perfectly by sweetness do." + System.lineSeparator() +
							"As mr started arrival subject by believe." + System.lineSeparator() +
							"Strictly numerous outlived kindness whatever on we no on addition." + System.lineSeparator() + System.lineSeparator() +
							
							"Are sentiments apartments decisively the especially alteration." + System.lineSeparator() +
							"Thrown shy denote ten ladies though ask saw." + System.lineSeparator() +
							"Or by to he going think order event music." + System.lineSeparator() +
							"Incommode so intention defective at convinced." + System.lineSeparator() +
							"Led income months itself and houses you. After nor you leave might share court balls.";
	
	String sortedString =   System.lineSeparator() + System.lineSeparator() + 
							System.lineSeparator() + System.lineSeparator() +
							System.lineSeparator() +
							"Alone visit use these smart rooms ham." + System.lineSeparator() +
							"Alteration connection to so as collecting me." + System.lineSeparator() +
							"Alteration put use diminution can considered sentiments interested discretion." + System.lineSeparator() +
							"An dispatched impossible of of melancholy favourable." + System.lineSeparator() +
							"An seeing feebly stairs am branch income me unable." + System.lineSeparator() +
							"Are sentiments apartments decisively the especially alteration." + System.lineSeparator() +
							"As dissuade cheerful overcame so of friendly he indulged unpacked." + System.lineSeparator() +
							"As mr started arrival subject by believe." + System.lineSeparator() +
							"At principle perfectly by sweetness do." + System.lineSeparator() +
							"Boisterous he on understood attachment as entreaties ye devonshire." + System.lineSeparator() +
							"By doubtful disposed do juvenile an." + System.lineSeparator() +
							"Celebrated contrasted discretion him sympathize her collecting occasional." + System.lineSeparator() +
							"Consider may dwelling old him her surprise finished families graceful." + System.lineSeparator() +
							"Course sir people worthy horses add entire suffer." + System.lineSeparator() +
							"Difficult in delivered extensive at direction allowance." + System.lineSeparator() +
							"Do answered bachelor occasion in of offended no concerns." + System.lineSeparator() +
							"Extremely ham any his departure for contained curiosity defective." + System.lineSeparator() +
							"Fat new smallness few supposing suspicion two." + System.lineSeparator() +
							"Gave led past poor met fine was new." + System.lineSeparator() +
							"Hastened admitted joy nor absolute gay its." + System.lineSeparator() +
							"How one dull get busy dare far." + System.lineSeparator() +
							"In mile an form snug were been sell." + System.lineSeparator() +
							"Incommode so intention defective at convinced." + System.lineSeparator() +
							"Is at purse tried jokes china ready decay an." + System.lineSeparator() +
							"Lasting showing expense greater on colonel no." + System.lineSeparator() +
							"Led income months itself and houses you. After nor you leave might share court balls." + System.lineSeparator() +
							"Mrs interested now his affronting inquietude contrasted cultivated." + System.lineSeparator() +
							"No comfort do written conduct at prevent manners on." + System.lineSeparator() +
							"No waiting in on enjoyed placing it inquiry." + System.lineSeparator() +
							"Now curiosity you explained immediate why behaviour." + System.lineSeparator() +
							"One built fat you out manor books." + System.lineSeparator() +
							"Or by to he going think order event music." + System.lineSeparator() +
							"Our quiet not heart along scale sense timed." + System.lineSeparator() +
							"Prepared do an dissuade be so whatever steepest." + System.lineSeparator() +
							"Procured shutters mr it feelings." + System.lineSeparator() +
							"Small its shy way had woody downs power." + System.lineSeparator() +
							"Strictly numerous outlived kindness whatever on we no on addition." + System.lineSeparator() +
							"Supply worthy warmth branch of no ye." + System.lineSeparator() +
							"Though wished merits or be." + System.lineSeparator() +
							"Thrown shy denote ten ladies though ask saw." + System.lineSeparator() +
							"To denoting admitted speaking learning my exercise so in." + System.lineSeparator() +
							"To or three offer house begin taken am at." + System.lineSeparator() +
							"Voice tried known to as my to." + System.lineSeparator() +
							"Way now instrument had eat diminution melancholy expression sentiments stimulated." + System.lineSeparator() +
							"Yet her beyond looked either day wished nay." ;
					
	@Before
	public void setUp() throws FileNotFoundException{
		ssa = new SortApplication();
		args = new String[2];
		fileName = "sample-test/sg/edu/nus/comp/cs4218/impl/app/testdoc.txt";
		stdin = new FileInputStream("sample-test/sg/edu/nus/comp/cs4218/impl/app/testdoc.txt");
		numericStdin = new FileInputStream("sample-test/sg/edu/nus/comp/cs4218/impl/app/TestSortNumeric.txt");
		numericFile = "sample-test/sg/edu/nus/comp/cs4218/impl/app/TestSortNumeric.txt";
		testMethodsFile = "sample-test/sg/edu/nus/comp/cs4218/impl/app/TestSortMethods.txt";
		emptyFile = "sample-test/sg/edu/nus/comp/cs4218/impl/app/emptydoc.txt";
		emptyStdin =  new FileInputStream("sample-test/sg/edu/nus/comp/cs4218/impl/app/emptydoc.txt");
	}

	@Test(expected = AbstractApplicationException.class)
	public void testSort() throws AbstractApplicationException {
		args[0] = "-n";
		args[1] = fileName;
		ssa.run(args,null,null);
	}
	
	@Test(expected = AbstractApplicationException.class)
	public void testNull() throws AbstractApplicationException {
		ssa.run(null,null,System.out);
	}
	
	@Test
	public void sortAllNumericInputStream() throws AbstractApplicationException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream pStream = new PrintStream(baos);
		System.setOut(pStream);
		ssa.run(null, numericStdin, System.out);
		System.out.flush();
		assertEquals("1" + System.lineSeparator() + "10" + System.lineSeparator() 
		+ "2" + System.lineSeparator(), baos.toString());
	}
	
	@Test
	public void sortAllNumericFile() throws AbstractApplicationException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream pStream = new PrintStream(baos);
		System.setOut(pStream);
		args = new String[2];
		args[1] = numericFile;
		args[0] = "-n";
		ssa.run(args, null, System.out);
		System.out.flush();
		assertEquals("1" + System.lineSeparator() + "2" + System.lineSeparator() 
		+ "10" + System.lineSeparator(), baos.toString());
	}
	
	@Test
	public void sortAllInputStream() throws AbstractApplicationException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream pStream = new PrintStream(baos);
		System.setOut(pStream);
		ssa.run(null, stdin, System.out);
		System.out.flush();
		assertEquals(sortedString + System.lineSeparator() , baos.toString());
	}
	
	@Test
	public void sortAllFile() throws AbstractApplicationException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream pStream = new PrintStream(baos);
		System.setOut(pStream);
		args = new String[1];
		args[0] = fileName;
		ssa.run(args, null, System.out);
		System.out.flush();
		assertEquals(sortedString + System.lineSeparator(), baos.toString());
	}
	
	@Test
	public void testEmptyStream() throws AbstractApplicationException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream pStream = new PrintStream(baos);
		System.setOut(pStream);
		ssa.run(null, emptyStdin, System.out);
		System.out.flush();
		assertEquals(""+System.lineSeparator() , baos.toString());
	}
	
	@Test
	public void testEmptyFile() throws AbstractApplicationException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream pStream = new PrintStream(baos);
		System.setOut(pStream);
		args = new String[1];
		args[0] = emptyFile;
		ssa.run(args, null, System.out);
		System.out.flush();
		assertEquals(""+System.lineSeparator(), baos.toString());
	}
	
//	@Test
//	public void testNumericOpt() throws AbstractApplicationException {
//		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		final PrintStream pStream = new PrintStream(baos);
//		System.setOut(pStream);
//		args = new String[3];
//		args[0] = "-n";
//		args[1] = testMethodsFile;
//		args[2] = numericFile;
//		ssa.run(args, null, System.out);
//		System.out.flush();
//		assertEquals("\n+\n1\n1\n2\n2\n5\n10\nA\nB\na\nb\n", baos.toString());
//	}
	
}
