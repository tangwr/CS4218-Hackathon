package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortApplicationTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void testSortStringsSimple() {
		String expectedOutput = "apple"+System.lineSeparator()
		+"beetroot"+System.lineSeparator()
		+"carrot";
		String toSort = "carrot"+System.lineSeparator()
		+"apple"+System.lineSeparator()
		+"beetroot";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortStringsSimple(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortStringsCapital() {
		String expectedOutput = "Apple"+System.lineSeparator()
		+"Beetroot"+System.lineSeparator()
		+"Carrot";
		String toSort = "Carrot"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"Beetroot";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortStringsCapital(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortNumbers() throws AbstractApplicationException {
		String expectedOutput = "1"+System.lineSeparator()
		+"2"+System.lineSeparator()
		+"12";
		String toSort = "2"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"12";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortNumbers(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortSpecialChars() {
		String expectedOutput = "+"+System.lineSeparator()
		+"-"+System.lineSeparator()
		+"/";
		String toSort = "-"+System.lineSeparator()
		+"/"+System.lineSeparator()
		+"+";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortSpecialChars(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortSimpleCapital() {
		String expectedOutput = "Beetroot"+System.lineSeparator()
		+"apple"+System.lineSeparator()
		+"carrot";
		String toSort = "apple"+System.lineSeparator()
		+"Beetroot"+System.lineSeparator()
		+"carrot";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortSimpleCapital(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortSimpleNumbers() {
		String expectedOutput = "1"+System.lineSeparator()
		+"apple"+System.lineSeparator()
		+"beetroot";
		String toSort = "beetroot"+System.lineSeparator()
		+"apple"+System.lineSeparator()
		+"1";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortSimpleNumbers(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortSimpleSpecialChars() {
		String expectedOutput = "+"+System.lineSeparator()
		+"apple"+System.lineSeparator()
		+"beetroot";
		String toSort = "beetroot"+System.lineSeparator()
		+"apple"+System.lineSeparator()
		+"+";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortSimpleSpecialChars(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortCapitalNumbers() {
		String expectedOutput = "1"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"Beetroot";
		String toSort = "Beetroot"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"Apple";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortCapitalNumbers(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortCapitalSpecialChars() {
		String expectedOutput = "+"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"Beetroot";
		String toSort = "Apple"+System.lineSeparator()
		+"Beetroot"+System.lineSeparator()
		+"+";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortCapitalSpecialChars(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortNumbersSpecialChars() {
		String expectedOutput = "+"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"2";
		String toSort = "2"+System.lineSeparator()
		+"+"+System.lineSeparator()
		+"1";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortNumbersSpecialChars(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortSimpleCapitalNumbers() {
		String expectedOutput = "+"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"Apple";
		String toSort = "Apple"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"+";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortSimpleCapitalNumber(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortSimpleCapitalSpecialChars() {
		String expectedOutput = "+"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"beetroot";
		String toSort = "beetroot"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"+";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortSimpleCapitalSpecialChars(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortSimpleNumbersSpecialChars() {
		String expectedOutput = "+"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"apple";
		String toSort = "apple"+System.lineSeparator()
		+"+"+System.lineSeparator()
		+"1";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortSimpleNumbersSpecialChars(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortCapitalNumbersSpecialChars() {
		String expectedOutput = "+"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"Apple";
		String toSort = "Apple"+System.lineSeparator()
		+"+"+System.lineSeparator()
		+"1";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortCapitalNumbersSpecialChars(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortAll() {
		String expectedOutput = "+"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"beetroot";
		String toSort = "beetroot"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"+";
		SortApplication sortApp = new SortApplication();
		String output = sortApp.sortAll(toSort);
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testSortAllFile() throws AbstractApplicationException {
		String expectedOutput = "1"+System.lineSeparator()
		+"12"+System.lineSeparator()
		+"2";
		String filePath = "test-data"+File.separator+"testSort.txt";
		String[] args = new String[] { filePath};
		
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		SortApplication sortApp = new SortApplication();

		sortApp.run(args, stdin, stdout);
		assertEquals(expectedOutput + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testSortNumbersFile() throws AbstractApplicationException {
		String expectedOutput = "1"+System.lineSeparator()
		+"2"+System.lineSeparator()
		+"12";
		String filePath = "test-data"+File.separator+"testSort.txt";
		String[] args = new String[] { "-n", filePath};
		
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		SortApplication sortApp = new SortApplication();

		sortApp.run(args, stdin, stdout);
		assertEquals(expectedOutput + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testSortAllStdin() throws AbstractApplicationException {
		String expectedOutput = "+"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"beetroot";
		String toSort = "beetroot"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"+";
		String[] args = new String[] { };

		SortApplication sortApp = new SortApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(toSort.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sortApp.run(args, stdin, stdout);
		assertEquals(expectedOutput + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testSortNumbersStdin() throws AbstractApplicationException {
		String expectedOutput = "1"+System.lineSeparator()
		+"2"+System.lineSeparator()
		+"12";
		String toSort = "2"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"12";
		String[] args = new String[] { "-n" };

		SortApplication sortApp = new SortApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(toSort.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sortApp.run(args, stdin, stdout);
		assertEquals(expectedOutput + System.lineSeparator(), stdout.toString());
	}
	
	@Test(expected = SortException.class)
	public void testFileException() throws AbstractApplicationException {
		String expectedOutput = "1"+System.lineSeparator()
		+"12"+System.lineSeparator()
		+"2";
		String filePath = "test-data"+File.separator+"testSort1.txt";
		String[] args = new String[] { filePath};
		
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		SortApplication sortApp = new SortApplication();

		sortApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test(expected = SortException.class)
	public void testNumberException() throws AbstractApplicationException {
		String expectedOutput = "1"+System.lineSeparator()
		+"12"+System.lineSeparator()
		+"2";
		String option = "-n";
		String filePath = "test-data"+File.separator+"testSed.txt";
		String[] args = new String[] { option, filePath};
		
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		SortApplication sortApp = new SortApplication();

		sortApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
	
	@Test(expected = SortException.class)
	public void testNumberException2() throws AbstractApplicationException {
		String expectedOutput = "+"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"beetroot";
		String toSort = "beetroot"+System.lineSeparator()
		+"Apple"+System.lineSeparator()
		+"1"+System.lineSeparator()
		+"+";
		String option = "-n";
		String[] args = new String[] { option };

		SortApplication sortApp = new SortApplication();
		ByteArrayInputStream stdin = new ByteArrayInputStream(toSort.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sortApp.run(args, stdin, stdout);
		assertEquals(expectedOutput, stdout.toString());
	}
}
