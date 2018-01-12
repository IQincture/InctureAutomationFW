package com.incture.proj.testNg;

public class test {
public static void main(String[] args) {
	String s1 = "<MANDT>100";
	/*s1 = s1.replaceAll("<", "&lt;");
	s1 = s1.replaceAll(">", "&gt;");*/
	try{
	s1 = s1.substring(s1.indexOf(">")+1, s1.indexOf("</"));
	}
	catch(Exception e){
	
		s1 = s1.substring(s1.indexOf(">")+1, s1.length());
	}
	System.out.println(s1);
}
}
