import java.util.*;

public class AST {
	private final Token token;
	private final List<AST> children;
	private boolean isRoot = false;
	public AST() {
		this(new Token(Token.ROOT, ""));
		this.isRoot = true;
	}
	
	public AST(Token token) {
		this.token = token;
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
	
	public Token token() {
		return token;
	}
	
	public String tokenText() {
		return token.text();
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (isRoot) {
			for (AST child : children) {
				builder.append(child.toString());
			}
		} else {
			builder.append(token);
		}
		return builder.toString();
	}
	
	public boolean equals(Object object) {
		return this.toString().equals(object.toString());
	}
}