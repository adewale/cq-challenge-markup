public class Token {
	public static final int ROOT = 0;
	public static final int EOF = 1;
	public static final int PARA = 2;
	public static final int LINE_TERMINATOR = 3;
	public static final int HEADER = 4;
	public static final int BLOCKQUOTE = 5;
	private static final String[] TOKEN_NAMES 
	  = {"ROOT", "EOF", "PARA", "LINE_TERMINATOR", "HEADER", "BLOCKQUOTE"};
	private final int type;
	private final String text;
	public Token(int type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public String toString() {
		String tokenName = TOKEN_NAMES[this.type];
		return "<[" + this.text + "], " + tokenName +">";
	}
	
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
	
	public static String getTokenName(int type) {
		return TOKEN_NAMES[type];
	}
	
	public int type() {
		return type;
	}
	
	public String text() {
		return text;
	}
}