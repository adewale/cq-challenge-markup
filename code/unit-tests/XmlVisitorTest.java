import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.*;
import org.junit.Test;
import org.hamcrest.Matcher;

public class XmlVisitorTest {
	@Test
	public void generatesXmlMatchingLevelOfHeader() throws IOException {
		Writer writer = new StringWriter();
		XmlVisitor visitor = new XmlVisitor(writer);
		
		AST ast = new AST(new Token(Token.HEADER, "headline text"), "H99");
		visitor.visitHeader(ast);
		assertThat(writer.toString(), afterFormattingEqualTo("<h99>headline text</h99>"));
	}

	@Test
	public void generatesXmlForDefaultLevelOfHeader() throws IOException {
		Writer writer = new StringWriter();
		XmlVisitor visitor = new XmlVisitor(writer);
		
		AST ast = new AST(new Token(Token.HEADER, "headline text"));
		visitor.visitHeader(ast);
		assertThat(writer.toString(), afterFormattingEqualTo("<h1>headline text</h1>"));
	}

  @Test
  public void generatesXmlForBlockQuote() throws IOException {
    Writer writer = new StringWriter();
		XmlVisitor visitor = new XmlVisitor(writer);
		
		AST ast = new AST(new Token(Token.BLOCKQUOTE, ""));
		ast.addChild(new AST(new Token(Token.PARA, "some words that have been blockquoted")));
		visitor.visitBlockquote(ast);
		assertThat(writer.toString(), equalTo("<blockquote>\n    <p>some words that have been blockquoted</p>\n</blockquote>\n"));
  }

  private Matcher<String> afterFormattingEqualTo(String line) {
    return equalTo("    " + line + "\n");
  }
}