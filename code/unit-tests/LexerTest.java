import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.util.Iterator;

public class LexerTest {
	
	@Test
	public void lexingEmptyReaderGeneratesEofToken() {
		Reader reader = new StringReader("");
		Lexer lexer = new Lexer(reader);
		Token token = lexer.nextToken();
		
		assertEquals(new Token(1, "EOF"), token);
	}
}