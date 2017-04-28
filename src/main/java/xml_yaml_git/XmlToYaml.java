package main.java.xml_yaml_git;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class XmlToYaml {

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	
	public XmlToYaml() throws Exception {
		builder = factory.newDocumentBuilder();
	}
	
    private boolean isNotEmptyTextNode(Node n) {
        if (n.getNodeType() == Node.TEXT_NODE) {
            String val = n.getNodeValue();
            return val.trim().length() != 0;
        } else {
            return false;
        }
    }
    
    private boolean isWhitespaceNode(Node n) {
        if (n.getNodeType() == Node.TEXT_NODE) {
            String val = n.getNodeValue();
            return val.trim().length() == 0;
        } else {
            return false;
        }
    }    
    
	private String processDocument(Document doc) throws Exception {
		String result = "";
		
		//Create and Fill the XML Elements Stack with 1st level Children (root element is ignored)
		//=======================================================
		LinkedList<IndentNode> l = new LinkedList<IndentNode>();

		NodeList nlx = doc.getDocumentElement().getChildNodes();
		for(int i=0;i<nlx.getLength();i++) {
			if (!isWhitespaceNode(nlx.item(i))) {
				l.add(new IndentNode(0, nlx.item(i)));
			}
		}
		//=======================================================

		//Process all XML Elements until end
		while (l.isEmpty() == false) {

			IndentNode dn = l.pop();//Current Node
			Node n = dn.node;
			int current_delim = dn.indent;
			
			HashMap<String,LinkedList<Node>> m = new HashMap<String,LinkedList<Node>>(); //Used to detect List(s)
			
			// Evaluating and processing all children
			NodeList nl = n.getChildNodes();
			
			//Cleaning the whitespace children - have to do it like that :(
			LinkedList<Node> targetElements = new LinkedList<Node>();
			for (int i = 0; i < nl.getLength(); i++) {
			  Node e = (Node)nl.item(i);
			  if (isWhitespaceNode(e)) {
			    targetElements.add(e);
			  }
			}
			for (Node e: targetElements) {
			  e.getParentNode().removeChild(e);
			}

			// listelem->#text converts to "- text"
			// listelem->X converts to "- ", X follows with indent
			// listelem->#text,X converts to - text, X follows with indent
			if (n.getNodeName().compareTo("listelem") == 0) {
				result = result + dn.getDelimeter() + "- ";
				for(int i=0;i<nl.getLength();i++) {
					if (nl.item(i).getNodeType() == Node.TEXT_NODE) {
						result = result + nl.item(i).getNodeValue().trim();
					} else {
						l.addFirst(new IndentNode(dn.indent+2,nl.item(i)));
					}
				}	
				result = result + System.lineSeparator();//There always will be one TEXT_NODE or embedded object or nothing ("- ")
				continue; //Go on with while
			}
			//X->text converts to X: text 
			if ((nl.getLength() == 1)&&(nl.item(0).getNodeType() == Node.TEXT_NODE)) {
				result = result + dn.getDelimeter() + n.getNodeName() + ": "+nl.item(0).getNodeValue().trim()+System.lineSeparator();
				continue; //Go on with while
			}
			
			//Now we treat longer elements
			//Check for subElement+Text combination 
			//=====================================
			boolean hasText = false;
			boolean hasOther = false;
			for(int i=0;i<nl.getLength();i++) {
				if (nl.item(i).getNodeType() == Node.TEXT_NODE)
					hasText = true;
				else
					hasOther = true;
			}
			
			if (hasText && hasOther) {
				throw new XMLYMLFormatException("An Element "+n.getNodeName()+" cannot contain Text and SubElements!");//Or ignore the parsing???
			}
			//=====================================
			
			//Build a hash of Children to find if there is a List element
			for(int i=0;i<nl.getLength();i++) {
				Node cn = nl.item(i);
	
				//Complex element: can be a List header so we check for a List inside
				//All content is added for further processing
				if (m.containsKey(cn.getNodeName())) {
					LinkedList<Node> ll = m.get(cn.getNodeName());
					ll.add(cn);
				} else {
					//Add node to empty part of Hash
					LinkedList<Node> ll = new LinkedList<Node>();
					ll.add(cn);
					m.put(cn.getNodeName(), ll);
				}
			}

			result = result + dn.getDelimeter() + n.getNodeName() + ":"+System.lineSeparator();
			
			//Iterate over the HashMap to process everything inside list(s)/complex node(s)
			int listsInsideCounter = 0;
			for (String s : m.keySet()) {
				LinkedList<Node> nd = m.get(s);
				
				//Count for multiple lists inside single tag
				if (nd.size() > 1)
					listsInsideCounter++;
				
				for (Node dnod: nd) {
					if (nd.size() > 1) { //Change the name of tag to one for the List
						doc.renameNode(dnod, dnod.getNamespaceURI(), "listelem");
					}
					l.addFirst(new IndentNode(current_delim+2,dnod));//Increase indent, process first
				}
			}
			
			//Check for multiple lists inside single tag
			if (listsInsideCounter > 1) {
				throw new XMLYMLFormatException("An Element "+n.getNodeName()+" cannot contain more than one List inside!");//Or ignore the parsing???
			}
			
		}
		return result;
	}
	
	
	public String ParseXML(String input_xml) throws Exception {
		// Init DOM Parser and get a root document
		//=======================================================		
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append(input_xml);
		ByteArrayInputStream input =  new ByteArrayInputStream(
		   xmlStringBuilder.toString().getBytes("UTF-8"));
		Document doc = builder.parse(input);
		//=======================================================
		
		return processDocument(doc);
	}
}
