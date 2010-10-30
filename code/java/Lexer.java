import java.io.Reader;
import java.util.Iterator;
import java.io.*;

public class Lexer {
	private static final int EOF = -1;
	private final Reader reader;
	private int c;
	public Lexer(Reader reader) {
		this.reader = reader;
		try {
			this.c = this.reader.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Token nextToken() {
		while (!isEOF()) {
			switch (c) {
				case '\n': return LINE_TERMINATOR();
				case '*': return HEADER();
				default: 
					if (isText()) {
						return PARA();
					}
			}
		}
		return new Token(1, "EOF");
	}
	
	private boolean isText() {
		return !isEOF() && !isLineTerminator();
	}

	private boolean isEOF() {
		return c == EOF;
	}

	private boolean isLineTerminator() {
		return c == '\n';
	}
	
	private Token PARA() {
		StringBuilder builder = new StringBuilder();
		do {
			builder.append((char)c);
			consume();
		} while (isText());
		return new Token(Token.PARA, builder.toString());
	}

	private Token LINE_TERMINATOR() {
		consume();
		
		return new Token(Token.LINE_TERMINATOR, "");
	}
	
	private Token HEADER() {
		StringBuilder builder = new StringBuilder();
		do {
			builder.append((char)c);
			consume();
		} while (isText());
		return new Token(Token.HEADER, builder.toString());
	}
	
	private void consume() {
		try {
			this.c = this.reader.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}