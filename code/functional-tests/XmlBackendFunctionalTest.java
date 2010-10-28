import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;

public class XmlBackendFunctionalTest {
	@Test
	public void generatesEmptyBodyElementForASTRepresentingEmptyDocument() {
		AST ast = new AST();
		ast.addChild(new AST(new Token(Token.EOF, "")));
		Writer writer = new StringWriter();
		XmlBackend backend = new XmlBackend(ast, writer);
		backend.generate();
		
		assertEquals("<body/>", writer.toString());
	}
	
	@Test
	public void generatesParagraphTagPerParagraphToken() {
		AST ast = new AST();
		for (int i = 0; i < 3; i++) {
			ast.addChild(new AST(new Token(Token.PARA, "" + i)));
		}
		Writer writer = new StringWriter();
		XmlBackend backend = new XmlBackend(ast, writer);
		backend.generate();
		
		assertEquals("<body><p>0</p><p>1</p><p>2</p></body>", writer.toString());
	}
}