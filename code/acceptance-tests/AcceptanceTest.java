import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.google.common.io.Files;

@RunWith(Parameterized.class)
public  class AcceptanceTest {
	private final String name;
	private final String textFile;
	private final String xmlFile;
	
	public AcceptanceTest(String name) {
		this.name = name;
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
		
		List<String[]> testData = new ArrayList<String[]>();
		for (String fileName : fileNames) {
			String testName = fileName.substring(0, fileName.length() - 4);
			testData.add(new String[]{testName});
		}
		//TODO(ade) Restrict to first N acceptance tests so that I can see what's going on
		return testData.subList(0, 4);
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
		
		String xmlFileContents = Files.toString(new File(testFolder, xmlFile), Charset.forName("UTF-8"));
		assertEquals(name + " failed:", xmlFileContents, writer.toString());
	}
}