package com.incture.proj.testNg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.incture.utility.openBrowser;
import com.incture.utility.actions.GenericActions;
import com.incture.utility.browser.MultipleBrowser;
import com.incture.utility.listener.CustomListeneresImplementation;
import com.incture.utility.reports.ExtentReport;
import com.incture.utility.reports.Report;

import randD.SettersGetters;
@Listeners(CustomListeneresImplementation.class)
public class DemoTher {

	WebDriver driver=null;



	@BeforeMethod
	public void beforeTest() throws InterruptedException{
		
		
		driver=new MultipleBrowser().getBrowserDriver("chrome");
		GenericActions.launchUrl(driver, "https://www.facebook.com/login/");			
	}

	@Test
	public void test1(){

		Report report =new Report(driver);
		report.pass("step1", "Validate uwername ", true);
		report.fail("step1", "Validate uwername ", true);
	}

	@Test
	public void test2(){
		Report report =new Report(driver);
		report.pass("step1", "Validate uwername ", true);
		report.fail("step1", "Validate uwername ", true);
	}




	@Test(enabled = false)
	public void test3(){
		System.out.println("Skipping test3 ....");

	}


	@AfterMethod
	public  void afterMethod(){
		System.out.println("afterMethod()");
		driver.close();
		driver.quit();
	}
	@AfterTest
	public void afterTest() throws InterruptedException{
		
		System.out.println("afterTest()");
		Thread.sleep(6000);
	}



	public void dummyMethod(){
		try{

			System.out.println(driver.findElement(By.id("email")).getAttribute("placeholder")==null);

			System.out.println();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
