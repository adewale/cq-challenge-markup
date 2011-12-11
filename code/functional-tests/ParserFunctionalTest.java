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
		expected.addChild(new AST(new Token(Token.PARA, "hello world")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesASTWithOneParaNodeForMultiLineParagraphSpanningSeveralLines() {
		Lexer lexer = new Lexer(new StringReader("hello.\nworld.\ngalaxy.\nuniverse."));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.PARA, "hello. world. galaxy. universe.")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesASTWithMultipleParaNodeForMultiLineParagraphsSpanningSeveralLines() {
		Lexer lexer = new Lexer(new StringReader("hello.\nworld.\n\ngalaxy.\nuniverse."));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.PARA, "hello. world.")));
		expected.addChild(new AST(new Token(Token.PARA, "galaxy. universe.")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesASTWithMultipleParaNodesForMultipleParagraphs() {
		Lexer lexer = new Lexer(new StringReader("hello.\n\nworld.\n\ngalaxy.\n\nuniverse."));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.PARA, "hello.")));
		expected.addChild(new AST(new Token(Token.PARA, "world.")));
		expected.addChild(new AST(new Token(Token.PARA, "galaxy.")));
		expected.addChild(new AST(new Token(Token.PARA, "universe.")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesHeaderNodeForHeaderWord() {
		Lexer lexer = new Lexer(new StringReader("* headerword"));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.HEADER, "headerword")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesHeaderNodeForHeaderLineOfMultipleWords() {
		Lexer lexer = new Lexer(new StringReader("* multiple header words"));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.HEADER, "multiple header words")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesHeaderNodeForHeaderLineOfMultipleWordsAndEmbeddedAsterisk() {
		Lexer lexer = new Lexer(new StringReader("* multiple header * words"));
		Parser parser = new Parser(lexer);
		AST actual = parser.parse();
		
		AST expected = new AST();
		expected.addChild(new AST(new Token(Token.HEADER, "multiple header * words")));
		expected.addChild(new AST(new Token(Token.EOF, "")));
		assertEquals(expected, actual);
	}
	
	@Test
	public void generatesHeaderNodeWithCorrectTypeForLevelNHeaderLineWithMultipleWords() {
		for (int i=1; i<=4; i++) {
			Lexer lexer = new Lexer(new StringReader(makeAsterisks(i) + " multiple header words"));
			Parser parser = new Parser(lexer);
			AST actual = parser.parse();

			AST expected = new AST();
			expected.addChild(new AST(new Token(Token.HEADER, "multiple header words"), "H" + i));
			expected.addChild(new AST(new Token(Token.EOF, "")));
			assertEquals(expected, actual);
		}
	}

	@Test
	public void generatesBlockquoteNodeGivenIndentedLine() {
		for (int i=1; i<=4; i++) {
			Lexer lexer = new Lexer(new StringReader("  Begin the blockquoting."));
			Parser parser = new Parser(lexer);
			AST actual = parser.parse();

			AST expected = new AST();
			
			//The blockquote is empty. All the content is in the para.
			AST blockquote = new AST(new Token(Token.BLOCKQUOTE, ""));
			expected.addChild(blockquote);
			blockquote.addChild(new AST(new Token(Token.PARA, "Begin the blockquoting.")));
			
			expected.addChild(new AST(new Token(Token.EOF, "")));
			assertEquals(expected, actual);
		}
	}

	private String makeAsterisks(int numberOfAsterisks) {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<numberOfAsterisks; i++) {
			builder.append("*");
		}
		return builder.toString();
	}
}