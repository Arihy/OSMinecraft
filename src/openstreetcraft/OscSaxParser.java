package openstreetcraft;

import java.io.IOException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;



public class OscSaxParser {

        public OscSaxParser(String uri) throws SAXException, IOException {
                        XMLReader saxReader = XMLReaderFactory.createXMLReader();
                        saxReader.setContentHandler(new OscContentHandler());
                        saxReader.parse(uri);
        }

}