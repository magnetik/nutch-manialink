package org.magnetik.nutch.manialink;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.parse.HTMLMetaTags;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.ParseFilter;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.storage.WebPage.Field;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

public class ManialinkParserFilter implements ParseFilter {

	private static Collection<WebPage.Field> fields = new HashSet<WebPage.Field>();
	
	public ManialinkParserFilter() {
		fields.add(Field.TEXT);
	}
	
	public Collection<Field> getFields() {
		return fields;
	}
	
	public Parse filter(String url, WebPage page, Parse parse,
			HTMLMetaTags metaTags, DocumentFragment doc) {
		try {
			XPath xPath = new DOMXPath("//@text");
			List nodeList = xPath.selectNodes(doc);
			for (Object node : nodeList) {
				String value = this.getTextContent(node);
				page.putToMetadata(new Utf8(Field.TEXT.getName()), ByteBuffer.wrap(value.getBytes()));
			}
		} catch (JaxenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parse;
	}
	
	protected String getTextContent(Object node) {
		if (node instanceof Node) {
			return ((Node) node).getTextContent();
		}
		return null;
	}

	public Configuration getConf() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConf(Configuration arg0) {
		// TODO Auto-generated method stub
		
	}

}
