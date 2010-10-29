public class Parser {
	private final Lexer input;
	private final AST ast;
	private Token lookahead;
	public Parser(Lexer input) {
		this.input = input;
		this.ast = new AST(new Token(Token.ROOT, ""));
		
		//lookahead
		consume();
	}
	
	public void match(int candidateType) {
		if (lookahead.type() == candidateType) {
			consume();
		} else {
			throw new RuntimeException("Expecting " + Token.getTokenName(candidateType) + " but found " + lookahead);
		}
	}
	
	public void consume() {
		lookahead = input.nextToken();
	}
	
	public void paragraph() {
		while (lookahead.type() == Token.PARA || lookahead.type() == Token.LINE_TERMINATOR) {
			ast.addChild(new AST(lookahead));
			consume();
		}
	}
	
	public AST parse() {
		paragraph();
		ast.addChild(new AST(new Token(Token.EOF, "")));
		return ast;
	}
}