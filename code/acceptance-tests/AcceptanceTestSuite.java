import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.*;
import org.junit.runners.*;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.*;

@RunWith(AllTests.class)
public class AcceptanceTestSuite {
	//open each pair of files in the ../tests folder and dynamically generate
	//a Test for them. Make sure you don't use ASCII-ordering. Instead numeric
	//ordering so that 9.txt is before 19.txt
	
	public static junit.framework.Test suite() {
		TestSuite suite = new TestSuite();
		File testFolder = new File("../tests");
		String[] fileNames = testFolder.list(new FilenameFilter() {
			public boolean accept(File folder, String fileName) {
				return fileName.matches("\\d\\d(.*)\\.txt");
			}
		});
		for (String fileName : fileNames) {
			String testName = fileName.substring(0, fileName.length() - 4);
			suite.addTest(new AcceptanceTest(testName));
		}
		
		return suite;
	}
	
	public static class AcceptanceTest extends TestCase {
		private final String textFile;
		private final String xmlFile;
		public AcceptanceTest(String name) {
			super("dynamicTest");
			this.textFile = name + ".txt";
			this.xmlFile = name + ".xml";
		}
		
		@Test
		public void dynamicTest() throws Exception {
			File testFolder = new File("../tests");
			Lexer lexer = new Lexer(new FileReader(new File(testFolder, textFile)));
			Parser parser = new Parser(lexer);
			AST ast = parser.parse();
			Writer writer = new StringWriter();
			XmlBackend backend = new XmlBackend(ast, writer);
			backend.generate();
			
		}
	}
}