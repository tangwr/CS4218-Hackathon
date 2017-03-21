package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class CallCommandTest {
	private ByteArrayOutputStream outputStream;
	private String cmdLine;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp(){
		outputStream = new ByteArrayOutputStream();
		cmdLine = "";
	}
	
	@Test
	public void testCallCommandOnlyApp() throws ShellException, AbstractApplicationException, IOException {
		cmdLine = "echo 123";
		try {
			CallCommand cc = new CallCommand(cmdLine);
			cc.evaluate(null, outputStream);
			assertEquals(outputStream.toString(), "123" + System.lineSeparator());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testCallCommandWithInputStream() throws IOException {
		File input = new File("input.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(input));
			String content = "ABC SSS" + System.lineSeparator() + "CS4218";
			bw.write(content);
			bw.close();
			InputStream is = new FileInputStream(input);
			cmdLine = "cat < input.txt";
			CallCommand cc = new CallCommand(cmdLine);
			cc.evaluate(null, outputStream);
			assertEquals(outputStream.toString(), content + System.lineSeparator());
			is.close();
		} catch (Exception e) {
			fail();
		} finally {
			input.delete();
		}
	}
	
	@Test
	public void testCallCommandWithOutputStream() {
		File output = new File("output.txt");
		try {
			cmdLine = "echo 123 > output.txt";
			CallCommand cc = new CallCommand(cmdLine);
			cc.evaluate(null, outputStream);
			BufferedReader br = new BufferedReader(new FileReader(output));
			assertEquals(br.readLine(), "123");
			br.close();
		} catch (Exception e) {
			fail();
		} finally {
			output.delete();
		}
	}
	
	@Test
	public void testCallCommandWithStreamsSuccess() {
		File input = new File("input.txt");
		File output = new File("output.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(input));
			String content = "ABC SSS" + System.lineSeparator() + "CS4218";
			bw.write(content);
			bw.close();
			
			cmdLine = "cat < input.txt > output.txt";
			CallCommand cc = new CallCommand(cmdLine);
			cc.evaluate(null, outputStream);
			BufferedReader br = new BufferedReader(new FileReader(output));
			assertEquals(br.readLine(), "ABC SSS");
			assertEquals(br.readLine(), "CS4218");
			br.close();
		} catch (Exception e) {
			fail();
		} finally {
			input.delete();
			output.delete();
		}
	}
	
	@Test
	public void testCallCommandWithStreamsFail() throws AbstractApplicationException, ShellException, IOException {
		expectedEx.expect(ShellException.class);
		expectedEx.expectMessage("shell: Too many inputs for IO redirection");
		cmdLine = "echo < 1.txt 2.txt";
		CallCommand cc = new CallCommand(cmdLine);
		cc.evaluate(null, outputStream);
		
		expectedEx.expect(ShellException.class);
		expectedEx.expectMessage("shell: Too many outputs");
		cmdLine = "echo > 1.txt > 2.txt";
		CallCommand cc2 = new CallCommand(cmdLine);
		cc2.evaluate(null, outputStream);
		
		expectedEx.expect(ShellException.class);
		expectedEx.expectMessage("shell: Invalid input");
		cmdLine = "echo <";
		CallCommand cc3 = new CallCommand(cmdLine);
		cc3.evaluate(null, outputStream);
		
		expectedEx.expect(ShellException.class);
		expectedEx.expectMessage("shell: Input redirection file same as "
			+ "output redirection file.");
		cmdLine = "echo < 1.txt > 1.txt";
		CallCommand cc4 = new CallCommand(cmdLine);
		cc4.evaluate(null, outputStream);
	}
	
	@Test
	public void testCallCommandWithGlobbing() {
		File input = new File("input.txt");
		File input2 = new File("input2.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(input));
			String content = "ABC SSS" + System.lineSeparator() + "CS4218";
			bw.write(content);
			bw.close();
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(input2));
			String content2 = "8124SC" + System.lineSeparator();
			bw2.write(content2);
			bw2.close();
			
			
			cmdLine = "cat input*";
			CallCommand cc = new CallCommand(cmdLine);
			cc.evaluate(null, outputStream);
			assertEquals(outputStream.toString(), content + System.lineSeparator() 
				+ content2 + System.lineSeparator());
		} catch (Exception e) {
			fail();
		} finally {
			input.delete();
			input2.delete();
		}
	}
	
	@Test
	public void testCallCommandWithGlobbing2() {
		File input2 = new File("testFolder/input2.txt");
		File input = new File("testFolder/input.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(input));
			String content = "ABC SSS" + System.lineSeparator() + "CS4218";
			bw.write(content);
			bw.close();
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(input2));
			String content2 = "8124SC" + System.lineSeparator();
			bw2.write(content2);
			bw2.close();
			
			
			cmdLine = "cat testFolder/*";
			CallCommand cc = new CallCommand(cmdLine);
			cc.evaluate(null, outputStream);
			assertEquals(outputStream.toString(), content + System.lineSeparator() 
				+ content2 + System.lineSeparator());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			input.delete();
			input2.delete();
		}
	}
	
	@Test
	public void testCallCommandWithGlobbing3() {
		File input = new File("testFolder/input.txt");
		File input2 = new File("testFolder/input2.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(input));
			String content = "ABC SSS" + System.lineSeparator() + "CS4218";
			bw.write(content);
			bw.close();
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(input2));
			String content2 = "8124SC" + System.lineSeparator();
			bw2.write(content2);
			bw2.close();
			
			
			cmdLine = "cat testFolder/*.txt";
			CallCommand cc = new CallCommand(cmdLine);
			cc.evaluate(null, outputStream);
			assertEquals(outputStream.toString(), content + System.lineSeparator() 
				+ content2 + System.lineSeparator());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			input.delete();
			input2.delete();
		}
	}
	
	@Test
	public void testCallCommandWithGlobbing4() {
		File input = new File("testFolder/input.txt");
		File input2 = new File("testFolder/input2.txt");
		File input3 = new File("testFolder/output.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(input));
			String content = "ABC SSS" + System.lineSeparator() + "CS4218";
			bw.write(content);
			bw.close();
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(input2));
			String content2 = "8124SC" + System.lineSeparator();
			bw2.write(content2);
			bw2.close();
			
			
			cmdLine = "cat testFolder/i*.txt";
			CallCommand cc = new CallCommand(cmdLine);
			cc.evaluate(null, outputStream);
			assertEquals(outputStream.toString(), content + System.lineSeparator() 
				+ content2 + System.lineSeparator());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			input.delete();
			input2.delete();
			input3.delete();
		}
	}
}
