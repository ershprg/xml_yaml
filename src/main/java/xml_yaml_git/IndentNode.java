package main.java.xml_yaml_git;

import org.w3c.dom.Node;

public class IndentNode {
	int indent = 0; //Amount of spaces
	Node node;
	
	public IndentNode(int del, Node n) {
		indent = del;
		node = n;
	}
	
	public String getDelimeter() {
		String s = "";
		for (int i=0;i<indent;i++)
			s+=" ";
		return s;
	}
	
}
