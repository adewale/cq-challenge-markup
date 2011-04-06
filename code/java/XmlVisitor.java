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
			case Token.HEADER: visitHeader(ast); break;
			case Token.LINE_TERMINATOR: break;
			case Token.EOF: break;
			default: throw new RuntimeException("AST with uknown token. AST is: " + ast);
		}
	}
	
	public void visitRoot(AST ast) throws IOException {
		if (ast.onlyChildIsEndOfFile()) {
			writer.append("<body/>\n");
		} else {
			writer.append("<body>\n");
			for (AST child : ast.children()) {
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
	
	public void visitHeader(AST ast) throws IOException {
		String tagName = ast.tagName().toLowerCase();
		writer.append("    <" + tagName + ">");
		writer.append(ast.tokenText());
		writer.append("</" + tagName + ">\n");
	}
}