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
		
		assertEquals("<body/>\n", writer.toString());
	}
	
	@Test
	public void generatesParagraphTagPerParagraphToken() {
		AST ast = new AST();
		for (int i = 0; i < 3; i++) {
			ast.addChild(new AST(new Token(Token.PARA, "" + i)));
			ast.addChild(new AST(new Token(Token.LINE_TERMINATOR, "")));
			ast.addChild(new AST(new Token(Token.LINE_TERMINATOR, "")));
		}
		ast.addChild(new AST(new Token(Token.EOF, "")));
		Writer writer = new StringWriter();
		XmlBackend backend = new XmlBackend(ast, writer);
		backend.generate();
		
		assertEquals("<body>\n    <p>0</p>\n    <p>1</p>\n    <p>2</p>\n</body>\n", writer.toString());
	}
	
	@Test
	public void generatesHeaderTagPerHeaderToken() {
		AST ast = new AST();
		for (int i = 0; i < 3; i++) {
			ast.addChild(new AST(new Token(Token.HEADER, "header" + i)));
			ast.addChild(new AST(new Token(Token.LINE_TERMINATOR, "")));
			ast.addChild(new AST(new Token(Token.LINE_TERMINATOR, "")));
		}
		ast.addChild(new AST(new Token(Token.EOF, "")));
		Writer writer = new StringWriter();
		XmlBackend backend = new XmlBackend(ast, writer);
		backend.generate();
		
		assertEquals("<body>\n    <h1>header0</h1>\n    <h1>header1</h1>\n    <h1>header2</h1>\n</body>\n", writer.toString());
	}

	@Test
	public void generatesHeaderTagWithIncreasingLevelPerAsterisk() {
		AST ast = new AST();
		for (int i = 1; i <= 3; i++) {
			ast.addChild(new AST(new Token(Token.HEADER, "header" + i), "H" + i));
			ast.addChild(new AST(new Token(Token.LINE_TERMINATOR, "")));
			ast.addChild(new AST(new Token(Token.LINE_TERMINATOR, "")));
		}
		ast.addChild(new AST(new Token(Token.EOF, "")));
		Writer writer = new StringWriter();
		XmlBackend backend = new XmlBackend(ast, writer);
		backend.generate();
		
		assertEquals("<body>\n    <h1>header1</h1>\n    <h2>header2</h2>\n    <h3>header3</h3>\n</body>\n", writer.toString());
	}
}