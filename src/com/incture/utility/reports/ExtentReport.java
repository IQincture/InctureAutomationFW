package com.incture.utility.reports;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.incture.utility.Constants;
import com.incture.utility.PlugInFunctions;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class ExtentReport {

	public static Map extentTestMap = new HashMap();
	public static ExtentReports	report=null;


	
	
	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized void endTest() {
		getReporter().endTest((ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId())));
		getReporter().flush();
		if(Constants.isEachTestNGReport.equalsIgnoreCase("Yes"))
		report=null;
	}

	public static synchronized ExtentTest startTest(String testName) {
		return startTest(testName, "");
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		//	extent = ExtentManager.getReporter();
		ExtentTest test = getReporter().startTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

		return (ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId()));
		//return test;
	}
	public static ExtentReports getReporter(){
		new File(Constants.extentReportPath).mkdirs();
		//System.out.println(PlugInFunctions.getConstantProperty("reportName").concat(String.valueOf(LocalDateTime.now()).replaceAll(":", ".")));
		if(report==null)
		report=new ExtentReports(Constants.extentReportPath+Constants.reportName,true);
		return report;
	}
	
	public static String getReportStatus(){
		String status=null;		
		status=ExtentReport.getTest().getRunStatus().toString();
		if(status.equals("unknown"))
			status="pass";
		return status;
		
		
	}

}
