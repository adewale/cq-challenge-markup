import java.io.*;

public class XmlBackend {
	private final AST ast;
	private final Writer writer;
	private boolean hasContent = false;
	public XmlBackend(AST ast, Writer writer) {
		this.ast = ast;
		this.writer = writer;
	}
	
	public void generate() {
		try {
			//writer.append("<body/>");
			XmlVisitor visitor = new XmlVisitor(writer);
			visitor.visit(ast);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}