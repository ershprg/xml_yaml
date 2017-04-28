package test.java;

import static org.junit.Assert.*;
import main.java.xml_yaml_git.XmlToYaml;

import org.junit.Test;

public class BasicTest {

	@Test(expected = org.xml.sax.SAXParseException.class) 
	public void EmptyDocument() throws Exception {
		
		String xml_input = "";		
		
		String yml_output = "";
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
		
	}

	@Test
	public void NothingInside() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+    
	    "</yaml>";		
		
		String yml_output = "";
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
	}
	
	@Test
	public void NoHeader() throws Exception {
		
		String xml_input = "<yaml>"+    
	    "</yaml>";		
		
		String yml_output = "";
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
		
	}
	
	@Test
	public void testShort() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
		"<profile>"+
	    "<profile_list>preview.integration.com</profile_list>"+
	    "<profile_list>preview1.integration.com</profile_list>"+
	    "<profile_list>preview2.integration.com</profile_list>"+
	    "</profile>"+
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		String yml_output = "profile:"+System.lineSeparator()+
				"  - preview2.integration.com"+System.lineSeparator()+
				"  - preview1.integration.com"+System.lineSeparator()+
				"  - preview.integration.com"+System.lineSeparator()+
				"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
		
	}

	@Test(expected = main.java.xml_yaml_git.XMLYMLFormatException.class)
	public void testDualList() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
				"<primary>mapa1</primary>"+
				"<secondary>mapa2</secondary>"+
		"<profile>"+				
	    "<profile_l>preview.integration.com</profile_l>"+
	    "<profile_l>preview1.integration.com</profile_l>"+
	    "<profile_l>preview2.integration.com</profile_l>"+
	    "<profile_m>preview.integration.com</profile_m>"+
	    "<profile_m>preview.integration.com</profile_m>"+
	    "</profile>"+	    
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
	}

	@Test
	public void testReal() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
				"<primary>mapa1</primary>"+
				"<secondary>mapa2</secondary>"+
		"<profile>"+				
	    "<profile_l>preview.integration.com</profile_l>"+
	    "<profile_l>preview1.integration.com</profile_l>"+
	    "<profile_l>preview2.integration.com</profile_l>"+	    
	    "</profile>"+	    
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		String yml_output = "primary: mapa1"+System.lineSeparator()+ 
				"secondary: mapa2"+System.lineSeparator()+
				"profile:"+System.lineSeparator()+
				"  - preview2.integration.com"+System.lineSeparator()+
				"  - preview1.integration.com"+System.lineSeparator()+
				"  - preview.integration.com"+System.lineSeparator()+
				"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
		
	}

	
	
	@Test
	public void testExtended() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
				"<primary>mapa1</primary>"+
				"<secondary>mapa2</secondary>"+
				"<profile>"+				
	    "<profile_elem>preview.integration.com</profile_elem>"+
	    "<profile_elem>preview1.integration.com</profile_elem>"+
	    "<profile_elem>preview2.integration.com</profile_elem>"+
	    "</profile>"+
	    "<manage>true</manage>"+
	    "<internal_structure>"+
	    "<internal::element>elem1</internal::element>"+
	    "<internal::element1>elem2</internal::element1>"+
	    "</internal_structure>"+
	    "<external_structure>"+
	    "<external::element>elem1</external::element>"+
	    "<external::element1>elem2</external::element1>"+
	    "</external_structure>"+
	    
	    "</yaml>";		
		
		String yml_output = "primary: mapa1"+System.lineSeparator()+
							"secondary: mapa2"+System.lineSeparator()+
							"profile:"+System.lineSeparator()+
							"  - preview2.integration.com"+System.lineSeparator()+
							"  - preview1.integration.com"+System.lineSeparator()+
							"  - preview.integration.com"+System.lineSeparator()+
							"manage: true"+System.lineSeparator()+
							"internal_structure:"+System.lineSeparator()+
							"  internal::element1: elem2"+System.lineSeparator()+
							"  internal::element: elem1"+System.lineSeparator()+
							"external_structure:"+System.lineSeparator()+
							"  external::element: elem1"+System.lineSeparator()+
							"  external::element1: elem2"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
	}

	@Test
	public void testSimpleInsideCombo() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
				"<primary>mapa1</primary>"+
				"<secondary>mapa2</secondary>"+
				"<profile>"+				
	    "<profile_elem>preview.integration.com</profile_elem>"+
	    "<profile_elem>preview1.integration.com</profile_elem>"+
	    "<profile_elem>preview2.integration.com</profile_elem>"+
	    "<some_stuff_inside>text</some_stuff_inside>"+
	    "</profile>"+
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		String yml_output = "primary: mapa1"+System.lineSeparator()+
							"secondary: mapa2"+System.lineSeparator()+
							"profile:"+System.lineSeparator()+
							"  - preview2.integration.com"+System.lineSeparator()+
							"  - preview1.integration.com"+System.lineSeparator()+
							"  - preview.integration.com"+System.lineSeparator()+
							"  some_stuff_inside: text"+System.lineSeparator()+
							"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
	}

	@Test(expected = main.java.xml_yaml_git.XMLYMLFormatException.class)
	public void testTextInsideCombo() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
				"<primary>mapa1</primary>"+
				"<secondary>mapa2</secondary>"+
				"<profile>"+				
	    "<profile_elem>preview.integration.com</profile_elem>"+
	    "<profile_elem>preview1.integration.com</profile_elem>"+
	    "<profile_elem>preview2.integration.com</profile_elem>"+
	    "text outside list"+
	    "</profile>"+
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
	}

	@Test
	public void testTagInsideInList() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
				"<primary>mapa1</primary>"+
				"<secondary>mapa2</secondary>"+
				"<profile>"+				
	    "<profile_elem><some_stuff_inside>text</some_stuff_inside></profile_elem>"+
	    "<profile_elem>preview1.integration.com</profile_elem>"+
	    "<profile_elem>preview2.integration.com</profile_elem>"+

	    "</profile>"+
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		String yml_output = "primary: mapa1"+System.lineSeparator()+
							"secondary: mapa2"+System.lineSeparator()+
							"profile:"+System.lineSeparator()+
							"  - preview2.integration.com"+System.lineSeparator()+
							"  - preview1.integration.com"+System.lineSeparator()+
							"  - "+System.lineSeparator()+
							"    some_stuff_inside: text"+System.lineSeparator()+
							"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
	}

	@Test
	public void testTextTagInsideInList() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
				"<primary>mapa1</primary>"+
				"<secondary>mapa2</secondary>"+
				"<profile>"+				
	    "<profile_elem>outertext<some_stuff_inside>innertext</some_stuff_inside></profile_elem>"+
	    "<profile_elem>preview1.integration.com</profile_elem>"+
	    "<profile_elem>preview2.integration.com</profile_elem>"+

	    "</profile>"+
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		String yml_output = "primary: mapa1"+System.lineSeparator()+
							"secondary: mapa2"+System.lineSeparator()+
							"profile:"+System.lineSeparator()+
							"  - preview2.integration.com"+System.lineSeparator()+
							"  - preview1.integration.com"+System.lineSeparator()+
							"  - outertext"+System.lineSeparator()+
							"    some_stuff_inside: innertext"+System.lineSeparator()+
							"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
	}

	@Test
	public void testListInsideList() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
				"<primary>mapa1</primary>"+
				"<secondary>mapa2</secondary>"+
				"<profile>"+				
	    "<profile_elem>outertext"+
		"<some_stuff_inside>"+
		"<list1>list1</list1>"+
		"<list1>list2</list1>"+
		"<list1>list3</list1>"+
		"</some_stuff_inside></profile_elem>"+
	    "<profile_elem>preview1.integration.com</profile_elem>"+
	    "<profile_elem>preview2.integration.com</profile_elem>"+

	    "</profile>"+
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		String yml_output = "primary: mapa1"+System.lineSeparator()+
							"secondary: mapa2"+System.lineSeparator()+
							"profile:"+System.lineSeparator()+
							"  - preview2.integration.com"+System.lineSeparator()+
							"  - preview1.integration.com"+System.lineSeparator()+
							"  - outertext"+System.lineSeparator()+
							"    some_stuff_inside:"+System.lineSeparator()+
							"      - list3"+System.lineSeparator()+
							"      - list2"+System.lineSeparator()+
							"      - list1"+System.lineSeparator()+
							"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
	}
	
	
	@Test
	public void testCRShort() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
		"<profile>"+System.lineSeparator()+
	    "<profile_list>preview.integration.com</profile_list>\n"+System.lineSeparator()+
	    "<profile_list>preview1.integration.com</profile_list>\n"+System.lineSeparator()+
	    "<profile_list>preview2.integration.com</profile_list>"+System.lineSeparator()+
	    "</profile>"+System.lineSeparator()+
	    "<manage>true</manage>"+System.lineSeparator()+
	    "</yaml>";		
		
		String yml_output = "profile:"+System.lineSeparator()+
				"  - preview2.integration.com"+System.lineSeparator()+
				"  - preview1.integration.com"+System.lineSeparator()+
				"  - preview.integration.com"+System.lineSeparator()+
				"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
		
	}

	@Test
	public void testCRSpaceShort() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
		"<profile>    "+System.lineSeparator()+
	    "<profile_list>preview.integration.com</profile_list>  "+System.lineSeparator()+
	    "<profile_list>preview1.integration.com</profile_list> \n"+System.lineSeparator()+
	    "<profile_list>preview2.integration.com</profile_list>    "+
	    "</profile>     \r\n"+
	    "<manage>true</manage>  "+System.lineSeparator()+
	    "</yaml>";		
		
		String yml_output = "profile:"+System.lineSeparator()+
				"  - preview2.integration.com"+System.lineSeparator()+
				"  - preview1.integration.com"+System.lineSeparator()+
				"  - preview.integration.com"+System.lineSeparator()+
				"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
		
	}

	@Test
	public void testCRSpaceInTags() throws Exception {
		
		String xml_input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
				"<yaml>"+
		"<profile>"+
	    "<profile_list>\npreview.integration.com\n</profile_list>"+
	    "<profile_list>  preview1.integration.com   </profile_list>"+
	    "<profile_list>     preview2.integration.com\r\n</profile_list>"+
	    "</profile>"+
	    "<manage>true</manage>"+
	    "</yaml>";		
		
		String yml_output = "profile:"+System.lineSeparator()+
				"  - preview2.integration.com"+System.lineSeparator()+
				"  - preview1.integration.com"+System.lineSeparator()+
				"  - preview.integration.com"+System.lineSeparator()+
				"manage: true"+System.lineSeparator();
		
		XmlToYaml x = new XmlToYaml();
		String real_output = x.ParseXML(xml_input);
		
		assertTrue("Result not as expected: \n"+real_output, real_output.compareTo(yml_output) == 0);
		
	}	

}
