import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;

public class ParserFunctionalTest {
	@Test
	public void testGeneratesASTForEmptyLexer() {
		try {
				Lexer lexer = new Lexer(new StringReader(""));
				Parser parser = new Parser(lexer);
				parser.paragraph();
		} catch (RuntimeException e) {
			fail(e.toString());
		}
	}
}