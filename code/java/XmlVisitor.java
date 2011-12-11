import java.io.*;
import java.util.*;

public class XmlVisitor {
	private final Writer writer;
	private int indentationLevel = 0;
	public XmlVisitor(Writer writer) {
		this.writer = writer;
	}
	
	public void visit(AST ast) throws IOException {
		switch (ast.tokenType()) {
			case Token.ROOT: visitRoot(ast); break;
			case Token.PARA: visitPara(ast); break;
			case Token.HEADER: visitHeader(ast); break;
			case Token.BLOCKQUOTE: visitBlockquote(ast); break;
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
			visitChildren(ast);
			writer.append("</body>\n");
		}
	}

  private void visitChildren(AST ast) throws IOException {
	indentationLevel++;
    for (AST child : ast.children()) {
		visit(child);
	}
	indentationLevel--;
  }

	public void visitPara(AST ast) throws IOException {
		writer.append(getIndent());
		writer.append("<p>");
		writer.append(ast.tokenText());
		writer.append("</p>\n");
	}
	
	private String getIndent() {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<indentationLevel; i++) {
			builder.append("    ");
		}
		return builder.toString();
	}
	
	public void visitHeader(AST ast) throws IOException {
		String elementName = ast.tagName().toLowerCase();
		writer.append("    <" + elementName + ">");
		writer.append(ast.tokenText());
		writer.append("</" + elementName + ">\n");
	}
	
	public void visitBlockquote(AST ast) throws IOException {
	  writer.append(getIndent());
	  writer.append("<blockquote>\n");
	  visitChildren(ast);
	  writer.append(getIndent());
	  writer.append("</blockquote>\n");
	}

}