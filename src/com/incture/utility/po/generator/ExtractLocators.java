package com.incture.utility.po.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;

public class ExtractLocators {
	//By.className(className)-->.//*[@class='form-element multi-field name']
	//By.cssSelector(selector)--> pending
	//By.id(id)   -->//*[@id='FirstName']
	//By.name(name) -->.//*[@name='FirstName']
	//By.tagName(name) --> pending
	//By.xpath(xpathExpression) --> target
	//By.linkText(linkText) --> //a[text()='Create an account']
	public static void main(String[] args) throws IOException{
		extractLocatorsFromJavaFile();
	}

	public static void extractLocatorsFromJavaFile() throws IOException{

		try{
			String srcJavaFile="D:/Automation/2017/JavaFiles/test2.java";

			BufferedReader br = new BufferedReader(new FileReader(srcJavaFile));
			String strLine=null;
			while ((strLine = br.readLine()) != null)   {

				if(strLine.contains("By.")){
					String first=strLine.substring(strLine.indexOf("By.")+3, strLine.length());
					String locator=first.substring(0,first.indexOf("\")")).split("\\(\"")[0];
					String value=first.substring(0,first.indexOf("\")")).split("\\(\"")[1];
					//	System.out.println(locator+"="+value);
					String xpath=null;
					switch (locator) {
					case "className":	
						xpath=value+"=.//*[@class='"+value+"']";
						break;
					case "cssSelector":	
						xpath="pending for cssSelector ---> "+value;
						break;
					case "id":
						xpath=value+"=//*[@id='"+value+"']";
						break;
					case "name":	
						xpath=value+"=//*[@name='"+value+"']";
						break;
					case "tagName":	
						xpath="pending for tagName";
						break;
					case "xpath":						
						xpath="\"Please declare Key \"="+value;
						break;
					case "linkText":	
						xpath=value+"=//a[text()='"+value+"']";
						break;

					default:
						xpath=strLine;
						break;
					}
					System.out.println(xpath);

				}		

			}

		}catch(Exception e){
			System.out.println(e.getMessage());
		}


	}
}