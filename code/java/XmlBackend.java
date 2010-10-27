import java.io.*;

public class XmlBackend {
	private final AST ast;
	private final Writer writer;
	public XmlBackend(AST ast, Writer writer) {
		this.ast = ast;
		this.writer = writer;
	}
	
	public void generate() {
		try {
			writer.append("<body/>");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}