import java.io.*;
import java.util.*;

public class XmlVisitor {
	private final Writer writer;
	public XmlVisitor(Writer writer) {
		this.writer = writer;
	}
	
	public void visit(AST ast) throws IOException {
		switch (ast.tokenType()) {
			case Token.ROOT: visitRoot(ast); break;
			case Token.PARA: visitPara(ast); break;
			case Token.LINE_TERMINATOR: break;
			case Token.EOF: break;
			default: throw new RuntimeException("AST with uknown token : " + ast.token());
		}
	}
	
	public void visitRoot(AST ast) throws IOException {
		List<AST> children = ast.children();
		if (children.size() == 1 && children.get(0).tokenType() == Token.EOF) {
			writer.append("<body/>\n");
		} else {
			writer.append("<body>\n");
			for (AST child : children) {
				visit(child);
			}
			writer.append("</body>\n");
		}
	}

	public void visitPara(AST ast) throws IOException {
		writer.append("    <p>");
		writer.append(ast.tokenText());
		writer.append("</p>\n");
	}	
}