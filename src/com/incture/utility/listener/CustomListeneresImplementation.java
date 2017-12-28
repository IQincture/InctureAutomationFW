package com.incture.utility.listener;

import java.time.LocalDateTime;
import java.util.Map;

import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlSuite;

import com.incture.proj.suite.parameters.XmlParameters;
import com.incture.utility.Constants;
import com.incture.utility.PlugInFunctions;
import com.incture.utility.reports.ExtentReport;
import com.incture.utility.reports.Report;

public class CustomListeneresImplementation implements CustomListeners{

	// ISuiteListener -- starts

	@Override
	public void onStart(ISuite suite) {
		System.out.println(suite.getName()+" - has started the execution ");

		// below code is to use for Remote browser (Grid).
		Map<String, String> suiteParameters=suite.getXmlSuite().getAllParameters();
		XmlParameters.nodeIp=suiteParameters.get("nodeIp");
		XmlParameters.port=suiteParameters.get("port");
		XmlParameters.userName=suiteParameters.get("userName");



	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println(suite.getName()+" - has Finished the execution ");
		XmlParameters.nodeIp=null;
		XmlParameters.port=null;
		XmlParameters.userName=null;

	}

	// ISuiteListener -- ends


	//ITestListener -- starts
	public void onTestStart(ITestResult result) {
		if(Constants.isEachTestNGReport.equalsIgnoreCase("Yes"))
		Constants.reportName=getTestMethodName(result).concat(String.valueOf("_"+LocalDateTime.now()).replaceAll(":", "."))+".html";
		System.out.println("--> lis onTestStart()" +  getTestMethodName(result) + " start");
		ExtentReport.startTest(getTestMethodName(result)).assignCategory(PlugInFunctions.getDateNow("dd-MM-yyyy"));

	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("--> lis onTestSuccess() " + getTestMethodName(result) + " success");
		Report report=new Report();
		report.conditionUpdate(ExtentReport.getReportStatus().equals("pass"), getTestMethodName(result)+" has executed sucessfully",getTestMethodName(result)+" has failed ", "TestCase Status", false);

		ExtentReport.endTest();
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("--> lis  onTestFailure()" + getTestMethodName(result) + " failure");
		Report report=new Report();
		report.conditionUpdate(ExtentReport.getReportStatus().equals("pass"), getTestMethodName(result)+" has executed sucessfully",getTestMethodName(result)+" has failed ", "TestCase Status", false);
		ExtentReport.endTest();
	}

	public void onTestSkipped(ITestResult result) {
		Constants.reportName=getTestMethodName(result)+PlugInFunctions.getDateNow("dd-MM-yyyy")+".html";
		ExtentReport.startTest(getTestMethodName(result)).assignCategory(PlugInFunctions.getDateNow("dd-MM-yyyy"));

		Report report=new Report();
		report.fail(getTestMethodName(result)+" got skipped ","Skipped testcase" , false);
		ExtentReport.endTest();
		System.out.println("--> lis  onTestSkipped()" + getTestMethodName(result) + " skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("--> lis onTestFailedButWithinSuccessPercentage() " + getTestMethodName(result));
	}


	public void onStart(ITestContext context) {
		System.out.println("--> lis  onStart()" + context.getName());
	}

	public void onFinish(ITestContext context) {
		System.out.println("--> lis  onFinish()" + context.getName());
	}

	private static String getTestMethodName(ITestResult result) {
		
		return result.getMethod().getConstructorOrMethod().getName();
	}
	//ITestListener -- ends




}
