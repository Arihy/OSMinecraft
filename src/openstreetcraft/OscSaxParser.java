package openstreetcraft;

import java.io.IOException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;



public class OscSaxParser {

	/**
	 * Initialisation du parseur XML.
	 * @param uri chemin du fichier .osm.
	 * @param filename chemin du fichier créé par Map.
	 * @throws SAXEception si problème de lecture.
	 * @throws IOException si problème d'accès au fichier.
	 */
        public OscSaxParser(String uri, String filename) throws SAXException, IOException {
                        XMLReader saxReader = XMLReaderFactory.createXMLReader();
                        saxReader.setContentHandler(new OscContentHandler(filename));
                        saxReader.parse(uri);
        }

}