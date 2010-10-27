public class Parser {
	private final Lexer input;
	private Token lookahead;
	public Parser(Lexer input) {
		this.input = input;
		
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
			//Do something
		}
	}
}