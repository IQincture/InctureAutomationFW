package com.incture.proj.appModules;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.incture.proj.pageObjects.Page2;
import com.incture.proj.pageObjects.Popup;
import com.incture.utility.reports.Report;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class Create_NewRow {
	static int rowCountAfterAdd;
	public static void test1(WebDriver driver, String fromDate, String toDate) throws InterruptedException, ParseException{
		//public static void test1(WebDriver driver, String fromDate, String toDate, ExtentReports report, ExtentTest logger) throws InterruptedException, ParseException{
		
		String fileName = System.getProperty("user.home")+"\\Desktop\\Date\\"+fromDate.replaceAll("/", "")+" - "+toDate.replaceAll("/", "");
		System.out.println(fileName);
		new File(fileName).mkdir();
		TestCase(driver,"OverWrite", fromDate, toDate, fileName);//TestCase(driver,"OverWrite", fromDate, toDate, fileName, logger);
		//logger.log(LogStatus.INFO, "Test Case Completed");
		Report report=new Report(driver);
		
		report.info("Test Case Completed", "Test Case Completed", false);
		
	}
	
	
	public static void TestCase(WebDriver driver, String action, String fromDate, String toDate, String fileName) throws InterruptedException, ParseException{//public static void TestCase(WebDriver driver, String action, String fromDate, String toDate, String fileName, ExtentTest logger) throws InterruptedException, ParseException{
		productSelected.product_selected(driver).click();		
		Thread.sleep(5000);
		int rowCountBefore = Page2.row_list(driver).size();
	//	logger.log(LogStatus.INFO, "Initially : "+logger.addScreenCapture(PlugInFunctions.takeScreenShotWithDynamicPath(driver, "Initially",fileName)));
		Report report=new Report(driver);
		report.info("Initially : ", "Initially : ", true);
		
		Page2.addRowButton(driver).click();
		rowCountAfterAdd = Page2.row_list(driver).size();
		if(rowCountAfterAdd == rowCountBefore+1){
			EnterNewRow.enterNewRow(driver, fromDate, toDate);
			Thread.sleep(5000);
			List<WebElement> radioButtons = Popup.radioButton_list(driver);
			System.out.println(radioButtons.size());
			Popup.okButton(driver).click();
			//logger.log(LogStatus.INFO, action+" : "+logger.addScreenCapture(PlugInFunctions.takeScreenShotWithDynamicPath(driver, action,fileName)));
			report.info("Initially : ", action, true);
			
			Thread.sleep(4000);
			ToGoBack.clickToFirstPage(driver);
			
		}
		else
			System.out.println("Addition didn't happen");
	}	
}

