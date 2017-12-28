package com.incture.proj.testNg;


import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.incture.proj.appModules.Create_NewRow;
import com.incture.utility.Constants;
import com.incture.utility.openBrowser;
import com.incture.utility.excel.Excel;
import com.incture.utility.reports.ExtentReport;
import com.incture.utility.reports.Report;




public class TestNG {
	/*ExtentReports report;
	ExtentTest logger;*/
	WebDriver driver;
	
	@DataProvider(name = "datas1")
	public static Object[][] createData1() throws Exception {
		Excel ex = new Excel();
		String[][] Records = ex.xlReadSheet(Constants.ExcelPath, "Single Record");
		
		return Records;
	}
	@BeforeTest
	@Parameters({"nodeIp","port","executionType"})
	public void createReport(String nodeIp, String port, String executionType) throws InterruptedException, MalformedURLException{
		//report = PlugInFunctions.reportGeneration();
		openBrowser op=new openBrowser();
		if(executionType.equalsIgnoreCase("local")){
			driver = op.LaunchLocalBrowser(Constants.browserName, Constants.URL);
		}
		else if(executionType.equalsIgnoreCase("remote")){
			driver = op.LaunchRemoteBrowser(Constants.browserName, Constants.URL, nodeIp, port);
		}
		else{
			System.out.println("Executiontype should be either 'remote' or 'local'");
			//Report.fail("Launching Browser ", "Executiontype should be either 'remote' or 'local'", true);
		}
	}
	
	@Test(dataProvider = "datas1")
	public void main(String fromDate, String toDate) throws InterruptedException, ParseException{
		Thread.sleep(10000);
		
		System.out.println("From: "+fromDate);
		System.out.println("ToDate: "+toDate);
		//logger = report.startTest("appModules Date: "+fromDate+" to "+toDate);
		ExtentReport.startTest("appModules Date: "+fromDate+" to "+toDate);
		//Create_NewRow.test1(driver, fromDate, toDate, report, logger);
		Create_NewRow.test1(driver, fromDate, toDate);
	}
	
	@AfterTest
	public void endReport() throws InterruptedException{
		//report.endTest(logger);
		//report.flush();
		ExtentReport.endTest();
		Thread.sleep(3000);
		driver.close();
	}
}
