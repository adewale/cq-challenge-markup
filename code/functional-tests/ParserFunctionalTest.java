import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;

public class ParserFunctionalTest {
	@Test
	public void generatesASTForEmptyLexer() {
		try {
				Lexer lexer = new Lexer(new StringReader(""));
				Parser parser = new Parser(lexer);
				parser.parse();
		} catch (RuntimeException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void generatesASTWithRootNodeForEmptyLexer() {
		Lexer lexer = new Lexer(new StringReader(""));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesASTWithOneParaNodeForSingleTokenLexer() {
		Lexer lexer = new Lexer(new StringReader("hello world"));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.ROOT, "")));
		expected.addChild(new AST(new Token(Token.PARA, "hello world")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesASTWithOneParaNodeForMultiLineParagraph() {
		Lexer lexer = new Lexer(new StringReader("hello\nworld"));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.ROOT, "")));
		expected.addChild(new AST(new Token(Token.PARA, "hello world")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesASTWithOneParaNodeMultiLineParagraphSpanningSeveralLines() {
		Lexer lexer = new Lexer(new StringReader("hello.\nworld.\ngalaxy.\nuniverse."));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.ROOT, "")));
		expected.addChild(new AST(new Token(Token.PARA, "hello. world. galaxy. universe.")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
}