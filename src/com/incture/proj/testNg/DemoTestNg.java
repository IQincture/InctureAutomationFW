package com.incture.proj.testNg;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.incture.utility.openBrowser;
import com.incture.utility.reports.Report;

public class DemoTestNg {
WebDriver driver=null;
	
	@BeforeTest
	public void beforeTest() throws InterruptedException{
		System.out.println("beforeTest()");
		openBrowser op =new openBrowser();
		driver=op.LaunchLocalBrowser("chrome", "https://www.google.com");
	}
	
	@BeforeMethod
	public  void beforeMethod(){
		System.out.println("beforeMethod()");
		
	}

	@Test
	public void test1(){
		//Assert.assertEquals(true, true);
		Report report=new Report(driver);
		System.out.println("test1()");
		
		
		report.pass("Step1-- login", "Users is try to login", true);	
		report.info("Step2-- Perform", "Users is try to do  Perform", true);	
	}

	@Test
	public void test2(){
		//Assert.assertEquals(true, true);
		System.out.println("test2()");
		Report report=new Report(driver);
		report.pass("Step1-- login", "Users is try to login", true);	
		report.fail("Step2-- Perform", "Users is try to Perform", true);	
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
