public class Parser {
	private final Lexer input;
	private final AST ast;
	private Token[] lookahead;
	private int position;
	private final int lookaheadDepth;
	public Parser(Lexer input) {
		this.input = input;
		this.ast = new AST(new Token(Token.ROOT, ""));
		
		lookaheadDepth = 3;
		lookahead = new Token[lookaheadDepth];
		
		position = 0;
		//lookahead
		for (int i = 0; i< lookaheadDepth; i++) {
			consume();
		}
	}
	
	public Token lookaheadToken(int depth) {
		return lookahead[(position + depth - 1) % lookaheadDepth];
	}
	
	public int lookaheadTokenType(int depth) {
		return lookaheadToken(depth).type();
	}
	
	public void match(int candidateType) {
		if (lookaheadTokenType(1) == candidateType) {
			consume();
		} else {
			throw new RuntimeException("Expecting " + Token.getTokenName(candidateType) + " but found " + lookaheadToken(1));
		}
	}
	
	public void consume() {
		lookahead[position] = input.nextToken();
		
		position = (position + 1) % lookaheadDepth; 
	}
	
	public void paragraph() {
		//System.err.println(lookaheadToken(1) + " : " + lookaheadToken(2) + " : " + lookaheadToken(3));
		//Process all multi-line paragraphs
		if (lookaheadTokenType(1) == Token.PARA && lookaheadTokenType(2) == Token.LINE_TERMINATOR && lookaheadTokenType(3) == Token.PARA) {
			StringBuilder builder = new StringBuilder();
			builder.append(lookahead[position].text());
			consume();//para
			do {
				builder.append(" ");
				match(Token.LINE_TERMINATOR);
				
				builder.append(lookahead[position].text());
				match(Token.PARA);
			} while (lookaheadTokenType(1) == Token.LINE_TERMINATOR && lookaheadTokenType(2) == Token.PARA);
			
			Token multiLinePara = new Token(Token.PARA, builder.toString());
			ast.addChild(new AST(multiLinePara));
		}
		
		//Process single line paragraphs
		while (lookaheadTokenType(1) == Token.PARA || lookaheadTokenType(1) == Token.LINE_TERMINATOR) {
			if (lookaheadTokenType(1) == Token.PARA) {
				ast.addChild(new AST(lookaheadToken(1)));
			}
			consume();
		}
	}
	
	public AST parse() {
		paragraph();
		ast.addChild(new AST(new Token(Token.EOF, "")));
		return ast;
	}
}