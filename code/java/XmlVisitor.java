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
			default: throw new RuntimeException("AST with uknown token : " + ast.token());
		}
	}
	
	public void visitRoot(AST ast) throws IOException {
		List<AST> children = ast.children();
		if (children.size() == 1 && children.get(0).tokenType() == Token.EOF) {
			writer.append("<body/>");
		} else {
			writer.append("<body>");
			for (AST child : children) {
				visit(child);
			}
			writer.append("</body>");
		}
	}

	public void visitPara(AST ast) throws IOException {
		writer.append("<p>");
		writer.append(ast.tokenText());
		writer.append("</p>");
	}	
}