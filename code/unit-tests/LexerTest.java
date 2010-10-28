import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.util.Iterator;

public class LexerTest {
	
	@Test
	public void lexingEmptyReaderGeneratesEofToken() {
		Reader reader = new StringReader("");
		Lexer lexer = new Lexer(reader);
		
		assertEquals(new Token(Token.EOF, "EOF"), lexer.nextToken());
	}
	
	@Test
	public void repeatedLexingEmptyReaderGeneratesEofToken() {
		Reader reader = new StringReader("");
		Lexer lexer = new Lexer(reader);
		
		assertEquals(new Token(Token.EOF, "EOF"), lexer.nextToken());
		assertEquals(new Token(Token.EOF, "EOF"), lexer.nextToken());
		assertEquals(new Token(Token.EOF, "EOF"), lexer.nextToken());
	}
	
	@Test
	public void lexingParagraphGeneratesParagraphToken() {
		Reader reader = new StringReader("Paragraph\n\n");
		Lexer lexer = new Lexer(reader);
		
		assertEquals(new Token(Token.PARA, "Paragraph"), lexer.nextToken());
	}
	
	@Test
	public void lexingParagraphWithEmbeddedSpaceGeneratesSingleParagraphToken() {
		Reader reader = new StringReader("Paragraph text\n\n");
		Lexer lexer = new Lexer(reader);
		Token token = lexer.nextToken();
		
		assertEquals(new Token(Token.PARA, "Paragraph text"), token);
	}
	
	@Test
	public void lexingMultiLineParagraphGeneratesParagraphTokensWithSpacesInBetween() {
		Reader reader = new StringReader("Paragraph1\nParagraph1 continued\n");
		Lexer lexer = new Lexer(reader);
		
		assertEquals(new Token(Token.PARA, "Paragraph1"), lexer.nextToken());
		assertEquals(new Token(Token.LINE_TERMINATOR, ""), lexer.nextToken());
		assertEquals(new Token(Token.PARA, "Paragraph1 continued"), lexer.nextToken());
	}
	
	@Test
	public void lexingMultipleParagraphsGeneratesMultipleParagraphTokens() {
		Reader reader = new StringReader("Paragraph1\n\nParagraph2\n");
		Lexer lexer = new Lexer(reader);
		
		assertEquals(new Token(Token.PARA, "Paragraph1"), lexer.nextToken());
		assertEquals(new Token(Token.LINE_TERMINATOR, ""), lexer.nextToken());
		assertEquals(new Token(Token.LINE_TERMINATOR, ""), lexer.nextToken());
		assertEquals(new Token(Token.PARA, "Paragraph2"), lexer.nextToken());
	}

}