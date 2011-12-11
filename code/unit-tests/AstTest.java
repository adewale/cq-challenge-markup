import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class AstTest {
	@Test
	public void nodesWithDifferentTagNamesAreUnequal() {
		AST node1 = new AST(new Token(Token.HEADER, "headline text"), "H1");
		AST node2 = new AST(new Token(Token.HEADER, "headline text"), "H99");
		
		assertThat(node1, not(equalTo(node2)));
	}

	@Test
	public void toStringIncludesTagName() {
		AST node = new AST(new Token(Token.HEADER, "headline text"), "H99");
		assertThat(node.toString(), equalTo("<<[headline text], HEADER>, H99>"));
	}

	@Test
	public void headerNodesDefaultToH1() {
		AST node = new AST(new Token(Token.HEADER, "headline text"));
		assertThat(node.toString(), equalTo("<<[headline text], HEADER>, H1>"));
	}
	
	@Test
	public void hasChildrenOnlyIfChildrenAdded() {
	  AST node = new AST(new Token(Token.PARA, "Some text"), "");
	  assertFalse(node.hasChildren());
	  
	  //This isn't a legal AST but it enables the easy testing of a specific scenario 
	  node.addChild(new AST(new Token(Token.HEADER, "headline text")));
	  assertTrue(node.hasChildren());
	}
	
	@Test
	public void toStringAlwaysIncludesRootNode() {
		AST node = new AST();
		assertThat(node.toString(), equalTo("<<[], ROOT>, null>"));
	}
	
	@Test
	public void toStringIncludesRootNodeAndHeaderChild() {
		AST node = new AST();
		node.addChild(new AST(new Token(Token.HEADER, "headline text"), "H99"));
		assertThat(node.toString(), equalTo("<<[], ROOT>, null>\n<<[headline text], HEADER>, H99>"));
	}
	
	@Test
	public void toStringIncludesRootNodeAndParaChild() {
		AST node = new AST();
		node.addChild(new AST(new Token(Token.PARA, "some text")));
		assertThat(node.toString(), equalTo("<<[], ROOT>, null>\n<<[some text], PARA>, null>"));
	}
}