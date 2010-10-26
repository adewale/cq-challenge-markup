public class Token {
	public static final int PARA = 2;
	private static final String[] TOKEN_NAMES = {"n/a", "EOF", "PARA"};
	private final int type;
	private final String text;
	public Token(int type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public String toString() {
		String tokenName = TOKEN_NAMES[this.type];
		return "<" + this.text + ", " + tokenName +">";
	}
	
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
}