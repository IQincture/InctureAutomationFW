package com.incture.utility.po.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.incture.utility.Constants;

import java.util.Properties;


public class PageElementsGeneratorFromProp {

	public static void main(String[] args) throws IOException {
		//generateElementesFromPropFile("./src/com/incture/utility/po/generator/prop.properties","./src/com/incture/utility/po/generator/element.java");
		generateElementesIntoJavaPageFile(Constants.xPathfile, "com.incture.proj.pageObjects");//./src/com/incture/proj/pageObjects/
	}

	/**
	 * This
	 * @param propFileLocation eg:- ./src/com/incture/utility/po/generator/prop.properties
	 * @param srcPackage       eg:- ./src/com/incture/utility/po/generator/
	 */
	public static void generateElementesIntoJavaPageFile(String propFileLocation,String srcPackage){
		try{


			System.out.println( System.getProperty("user.dir"));
			BufferedReader br = new BufferedReader(new FileReader(propFileLocation));
			BufferedWriter writer = null;
			String strLine;

			while ((strLine = br.readLine()) != null)   {
				//System.out.println (strLine);
				//new BufferedWriter(new FileWriter("./src/com/incture/utility/po/generator/element.txt", false));
				if((strLine.charAt(0)=='#') && !(strLine.charAt(1)=='#')){//strLine.subSequence(strLine.length()-5, strLine.length()).equals(".java")

					if(!(writer==null)){
						writer.write("}");writer.newLine();
						writer.flush();writer.close();
						writer=null;
					}

					if(!srcPackage.contains("."))
					{
						writer=new BufferedWriter(new FileWriter(System.getProperty("user.dir")+srcPackage+strLine.replace("#", "")+".java", false));
						writer.write("package "+srcPackage.replace("/", ".")+";");writer.newLine();

					}else{
						//String projDir=System.getProperty("user.dir");
						String srcFolder=srcPackage.replace(".", "/");
						writer=new BufferedWriter(new FileWriter("./src/"+srcFolder+"/"+strLine.replace("#", "")+".java", false));
						writer.write("package "+srcPackage+";");writer.newLine();

					}
					writer.write("import org.openqa.selenium.WebDriver;import org.openqa.selenium.WebElement;import com.incture.utility.PlugInFunctions;import java.io.FileInputStream;import java.io.IOException;");writer.newLine();
					writer.write("import java.util.List;");writer.newLine();
					writer.write("public class "+strLine.replace("#", "").replace(".java", "")+" {");writer.newLine();

					System.out.println(strLine);
				}else if((strLine.charAt(0)=='#')){
					System.out.println(strLine);
				}else{
					if(!strLine.split("=")[0].contains("_list"))
					{writer.write("public static WebElement "+strLine.split("=")[0]+"(WebDriver driver) {");
					writer.newLine();
					writer.write("WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty(\""+strLine.split("=")[0]+"\"));");
					writer.newLine();
					writer.write("return element;");
					writer.newLine();
					writer.write("}");
					writer.newLine();
					writer.flush();}
					else{
						writer.write("public static List<WebElement> "+strLine.split("=")[0]+"(WebElement driver) {");
						writer.newLine();
						writer.write("	List<WebElement>  element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty(\""+strLine.split("=")[0]+"\"));");
						writer.newLine();
						writer.write("	return element;");
						writer.newLine();
						writer.write("	}	");
						writer.newLine();
						writer.flush();
					}
				}

			}
			writer.write("}");writer.newLine();
			writer.flush();writer.close();
			br.close();

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("!Done");
	}
	/**
	 * This method will convert the .property file from "propFileLocation" in to PageObjcet WebElements to the scr.txt "srcTxtFileLocation". 
	 * @param propFileLocation eg:- ./src/com/incture/utility/po/generator/prop.properties
	 * @param srcTxtFileLocation eg:- ./src/com/incture/utility/po/generator/element.txt
	 * @throws IOException
	 */
	public static void generateElementesFromPropFile(String propFileLocation,String srcTxtFileLocation) throws IOException{


		//System.out.println( System.getProperty("user.dir"));
		Properties propGet = new Properties();
		propGet.load(new FileInputStream(propFileLocation));

		BufferedWriter writer = new BufferedWriter(new FileWriter(srcTxtFileLocation,false));


		for (Entry<Object, Object> entry : propGet.entrySet()) {

			String Key=(String) entry.getKey();
			String value=(String) entry.getValue();

			/*System.out.println("public static WebElement "+Key+"(WebDriver driver) {");
			System.out.println("WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty(\""+Key+"\"));");
			System.out.println("return element;");
			System.out.println("}");*/

			writer.write("public static WebElement "+Key+"(WebDriver driver) {");
			writer.newLine();
			writer.write("WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty(\""+Key+"\"));");
			writer.newLine();
			writer.write("return element;");
			writer.newLine();
			writer.write("}");
			writer.newLine();
			System.out.println("Writing ... ");
		}
		writer.close();


		System.out.println("!Done");
	}

}
