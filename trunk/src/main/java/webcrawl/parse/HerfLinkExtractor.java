package webcrawl.parse;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * extract only the link in the <a> tag from the html page content
 * 
 * @author jingjing.zhijj
 * 
 */
public class HerfLinkExtractor implements Extractor<Set<String>> {

	public Set<String> extract(String pageContent) {

		if (pageContent == null || "".equals(pageContent)) {
			return Collections.emptySet();
		}

		org.cyberneko.html.parsers.SAXParser parser = new org.cyberneko.html.parsers.SAXParser();

		AHerfHandler handler = new AHerfHandler();
		parser.setContentHandler(handler);

		try {
			parser.parse(new InputSource(new StringReader(pageContent)));
		} catch (Exception e) {
			e.printStackTrace();

		}

		Set<String> ret = new HashSet<String>();
		ret.addAll(handler.links);

		return ret;
	}

	public Set<String> extract(InputStream is) {

		org.cyberneko.html.parsers.SAXParser parser = new org.cyberneko.html.parsers.SAXParser();
		AHerfHandler handler = new AHerfHandler();
		parser.setContentHandler(handler);

		try {
			parser.parse(new InputSource(is));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return handler.links;
	}

	class AHerfHandler implements ContentHandler {

		Set<String> links = new LinkedHashSet<String>();

		public void characters(char[] arg0, int arg1, int arg2)
				throws SAXException {
			// TODO Auto-generated method stub

		}

		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub

		}

		public void endElement(String arg0, String arg1, String arg2)
				throws SAXException {
			// TODO Auto-generated method stub

		}

		public void endPrefixMapping(String arg0) throws SAXException {
			// TODO Auto-generated method stub

		}

		public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
				throws SAXException {
			// TODO Auto-generated method stub

		}

		public void processingInstruction(String arg0, String arg1)
				throws SAXException {
			// TODO Auto-generated method stub

		}

		public void setDocumentLocator(Locator arg0) {
			// TODO Auto-generated method stub

		}

		public void skippedEntity(String arg0) throws SAXException {
			// TODO Auto-generated method stub

		}

		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub

		}

		public void startElement(String arg0, String arg1, String arg2,
				Attributes arg3) throws SAXException {

			if ("A".equalsIgnoreCase(arg1)) {
				links.add(arg3.getValue("href"));
			}

		}

		public void startPrefixMapping(String arg0, String arg1)
				throws SAXException {
			// TODO Auto-generated method stub

		}

	}

}
