import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.util.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public  class AcceptanceTest {
	private final String textFile;
	private final String xmlFile;
	
	public AcceptanceTest(String name) {
		this.textFile = name + ".txt";
		this.xmlFile = name + ".xml";
	}
	
	@Parameters
	public static Collection<String[]> data() {
		File testFolder = new File("../tests");
		String[] fileNames = testFolder.list(new FilenameFilter() {
			public boolean accept(File folder, String fileName) {
				return fileName.matches("\\d\\d(.*)\\.txt");
			}
		});
		
		Collection<String[]> testData = new ArrayList<String[]>();
		for (String fileName : fileNames) {
			String testName = fileName.substring(0, fileName.length() - 4);
			testData.add(new String[]{testName});
		}
		return testData;
	}
	
	@Test
	public void astMatchesXml() throws Exception {
		File testFolder = new File("../tests");
		Lexer lexer = new Lexer(new FileReader(new File(testFolder, textFile)));
		Parser parser = new Parser(lexer);
		AST ast = parser.parse();
		Writer writer = new StringWriter();
		XmlBackend backend = new XmlBackend(ast, writer);
		backend.generate();
		
	}
}