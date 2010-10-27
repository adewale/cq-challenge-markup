import java.util.*;

public class AST {
	private Token token;
	private List<AST> children;
	public AST() {
		
	}
	
	public AST(Token token) {
		this.token = token;
	}
	
	public void addChild(AST child) {
		if (children == null) {
			children = new ArrayList<AST>();
		}
		children.add(child);
	}
}