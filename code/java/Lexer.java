import java.io.Reader;
import java.util.Iterator;

public class Lexer {
	private final Reader reader;
	public Lexer(Reader reader) {
		this.reader = reader;
	}
	
	public Token nextToken() {
		return new Token(1, "EOF");
	}
}