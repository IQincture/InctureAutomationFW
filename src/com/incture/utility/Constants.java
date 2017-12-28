package com.incture.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Constants {
	public static final String constantProp = "./src/com/incture/proj/objectRepository/Constant.properties";
	

	////public static final String xPathfile = "src/objectRepository/xpathLibrary.properties";
	
	public static String reportName = PlugInFunctions.getConstantProperty("reportName").concat(String.valueOf(LocalDateTime.now()).replaceAll(":", "."))+".html";
	public static final String URL = PlugInFunctions.getConstantProperty("URL");
	public static final String ExcelPath = PlugInFunctions.getConstantProperty("ExcelPath");
	public static final String extentReportPath = PlugInFunctions.getConstantProperty("extentReportPath").concat(LocalDate.now().toString()+"/");//+Constants.reportName+ ".html";
	public static final String extentReportImgFolderName = PlugInFunctions.getConstantProperty("extentReportImgFolderName");
	public static final String extentReportImgNameFormate= PlugInFunctions.getConstantProperty("extentReportImgNameFormate");
	public static final String dateFormat = PlugInFunctions.getConstantProperty("dateFormat");
	public static final String sheetName = PlugInFunctions.getConstantProperty("sheetName");
	public static final String browserName =  PlugInFunctions.getConstantProperty("browserName");
	public static final String chromeDriverExe =  PlugInFunctions.getConstantProperty("chromeDriverExe");
	public static final String IEDriverServerExe=PlugInFunctions.getConstantProperty("IEDriverServerExe");
	public static final String xPathfile = PlugInFunctions.getConstantProperty("xPathfile");
	public static final String isRemote=PlugInFunctions.getConstantProperty("isRemote");
	
}
