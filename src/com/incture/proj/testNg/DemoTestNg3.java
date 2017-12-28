package com.incture.proj.testNg;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.incture.utility.openBrowser;
import com.incture.utility.reports.ExtentReport;
import com.incture.utility.reports.Report;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DemoTestNg3 {
WebDriver driver=null;
	
	@BeforeTest
	public void beforeTest() throws InterruptedException{
		openBrowser op=new openBrowser();
		System.out.println("beforeTest()");
		driver=op.LaunchLocalBrowser("chrome", "https://www.google.com");
	}
	
	@BeforeMethod
	public  void beforeMethod(){
		System.out.println("beforeMethod()");
		
	}

	@Test
	public void test1(){
		//Assert.assertEquals(true, true);
		System.out.println("test1()");
		//Report.pass("Step1-- login", "Users is try to login", true);	
		//Report.info("Step2-- Perform", "Users is try to do  Perform", true);
		ExtentTest child=ExtentReport.getReporter().startTest("child", "child desc");
		
		child.log(LogStatus.INFO, "test1 child ", "test1 child ");
		child.log(LogStatus.INFO, "test1 child ", "test1 child ");
		ExtentReport.getTest().appendChild(child);
		//ExtentReport.getReporter().endTest(child);
		
	ExtentTest child2=ExtentReport.getReporter().startTest("child", "child desc");
		
		child2.log(LogStatus.INFO, "test1 child2 ", "test1 child2 ");
		child2.log(LogStatus.INFO, "test1 child2 ", "test1 child2");
		ExtentReport.getTest().appendChild(child2);
		//ExtentReport.getReporter().endTest(child2);
		//Report.pass("Step1-- login", "Users is try to login", true);	
		//Report.info("Step2-- Perform", "Users is try to do  Perform", true);	
	}

	@Test
	public void test2(){/*
		//Assert.assertEquals(true, true);
		System.out.println("test2()");
		Report.pass("Step1-- login", "Users is try to login", true);	
		Report.fail("Step2-- Perform", "Users is try to Perform", true);	
	*/

		//Assert.assertEquals(true, true);
		System.out.println("test1()");
		//Report.pass("Step1-- login", "Users is try to login", true);	
		//Report.info("Step2-- Perform", "Users is try to do  Perform", true);
		ExtentTest child=ExtentReport.getReporter().startTest("child", "child desc");
		
		child.log(LogStatus.INFO, "test2 child ", "test2 child ");
		child.log(LogStatus.INFO, "test2 child ", "test2 child ");
		ExtentReport.getTest().appendChild(child);
		//ExtentReport.getReporter().endTest(child);
		
	ExtentTest child2=ExtentReport.getReporter().startTest("child", "child desc");
		
		child2.log(LogStatus.INFO, "test2 child2 ", "test2 child2 ");
		child2.log(LogStatus.INFO, "test2 child2 ", "test2 child2");
		ExtentReport.getTest().appendChild(child2);
		//ExtentReport.getReporter().endTest(child2);
		//Report.pass("Step1-- login", "Users is try to login", true);	
		//Report.info("Step2-- Perform", "Users is try to do  Perform", true);	
		
	}
	
	@AfterMethod
	public  void afterMethod(){
		System.out.println("afterMethod()");
	}
	@AfterTest
	public void afterTest(){
		driver.close();
		driver.quit();
		System.out.println("afterTest()");
	}
	
	
	
}
