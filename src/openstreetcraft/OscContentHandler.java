package openstreetcraft;


import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

public class OscContentHandler implements ContentHandler {
	

    public OscContentHandler() {
            super();
            locator = new LocatorImpl();
    }

    public void setDocumentLocator(Locator value) {
            locator =  value;
    }

    public void startDocument() throws SAXException {
            System.out.println("Debut de l'analyse des données");
    }

    public void endDocument() throws SAXException {
            System.out.println("Fin de l'analyse des données" );
            OscMapGenerator.saveMap();
    }

    public void startPrefixMapping(String prefix, String URI) throws SAXException {
            System.out.println("Traitement de l'espace de nommage : " + URI + ", prefixe choisi : " + prefix);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
            System.out.println("Fin de traitement de l'espace de nommage : " + prefix);
    }

    public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
    	
            switch(localName){
            	case "bounds":
            		double minLon = Double.parseDouble(attributs.getValue("minlon"));
            		double maxLon = Double.parseDouble(attributs.getValue("maxlon"));
            		double minLat = Double.parseDouble(attributs.getValue("minlat"));
            		double maxLat = Double.parseDouble(attributs.getValue("maxlat"));
            		OscMapGenerator.createMap(minLat, minLon, maxLat, maxLon);
            		break;
            	case "node":
            		String key = attributs.getValue("id");
            		double lon= Double.parseDouble(attributs.getValue("lon"));
            		double lat= Double.parseDouble(attributs.getValue("lat"));
            		Location l=OscMapGenerator.getLocation(lon, lat);
            		OscMapGenerator.addNode(key, l);
            		break;
            	case "way":
            		OscMapGenerator.createNewWay();
            		break;
            	case "nd":
            		Location loc = OscMapGenerator.getNodeLocation(attributs.getValue("ref"));
            		OscMapGenerator.way.addLocation(loc);
            		break;
            	case "tag":
            		if(OscMapGenerator.way!=null){
            			OscMapGenerator.way.addTag(attributs.getValue("k"), attributs.getValue("v"));
            		}
            		break;
            	default :
            		System.out.println("Balise non traitée :"+localName);
            		System.out.println("  Attributs de la balise : ");
            		
            		for (int index = 0; index < attributs.getLength(); index++) {
                    	System.out.println("     - " +  attributs.getLocalName(index) + " = " + attributs.getValue(index));
            		}
            		break;
            }
    }

    public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
    	switch(localName){
    	case "way":
    		OscMapGenerator.buildWay();
    		break;
    	default:
    		break;
    	}
    }

    public void characters(char[] ch, int start, int end) throws SAXException {

    }

    public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
            System.out.println("espaces inutiles rencontres : ..." + new String(ch, start, end) +  "...");
    }

    public void processingInstruction(String target, String data) throws SAXException {
            System.out.println("Instruction de fonctionnement : " + target);
            System.out.println("  dont les arguments sont : " + data);
    }

    public void skippedEntity(String arg0) throws SAXException {        
    }

    @SuppressWarnings("unused")
	private Locator locator;
    
    
}
