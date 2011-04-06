import java.util.*;

public class AST {
	private final Token token;
	private final String tagName;
	private final List<AST> children;
	public AST() {
		this(new Token(Token.ROOT, ""), null);
	}
	
	public AST(Token token) {
		this(token, null);
	}
	
	public AST(Token token, String tagName) {
		this.token = token;
		if (tagName == null && token.type() == Token.HEADER) {
		  	this.tagName = "H1";
		} else {
			this.tagName = tagName;
		}
		children = new ArrayList<AST>();
	}
	
	public void addChild(AST child) {
		children.add(child);
	}
	
	public List<AST> children() {
		return children;
	}
	
	public boolean hasChildren() {
		return children.isEmpty();
	}
	
	public int tokenType() {
		return token.type();
	}
	
	public String tokenText() {
		return token.text();
	}
	
	public String tagName() {
		return tagName;
	}
	
	public boolean onlyChildIsEndOfFile() {
		return children.size() == 1 && children.get(0).tokenType() == Token.EOF;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (Token.ROOT == token.type()) {
			for (AST child : children) {
				builder.append(child.toString());
			}
		} else {
			builder.append("<");
			builder.append(token);
			builder.append(", ");
			builder.append(tagName);
			builder.append(">");
		}
		return builder.toString();
	}
	
	public boolean equals(Object object) {
		return this.toString().equals(object.toString());
	}
}