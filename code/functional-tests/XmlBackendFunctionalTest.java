import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;

public class XmlBackendFunctionalTest {
	@Test
	public void testGeneratesEmptyBodyElementForEmptyAST() {
		AST ast = new AST();
		Writer writer = new StringWriter();
		XmlBackend backend = new XmlBackend(ast, writer);
		backend.generate();
		
		assertEquals("<body/>", writer.toString());
	}
}