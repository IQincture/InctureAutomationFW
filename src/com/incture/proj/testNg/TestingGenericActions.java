package com.incture.proj.testNg;

import java.awt.HeadlessException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.internal.Invoker;

import com.beust.jcommander.Parameter;
import com.incture.utility.Constants;
import com.incture.utility.openBrowser;
import com.incture.utility.actions.GenericActions;
import com.incture.utility.listener.CustomListeneresImplementation;
import com.incture.utility.reports.Report;

@Listeners(CustomListeneresImplementation.class)
public class TestingGenericActions {

	WebDriver driver =null;

	@BeforeMethod
	public void beforeMethod(ITestResult tr1){
		
		
		System.out.println("before method");

	}


	@Test
	@Parameters({"Str1","Str2"})
	public void testingGenericMethods(@Optional String Str1,@Optional String Str2) throws InterruptedException, HeadlessException, UnsupportedFlavorException, IOException{
		openBrowser op=new openBrowser();
		driver=op.LaunchLocalBrowser("chrome", Constants.URL);
		m1(driver);

	}

	public void m1(WebDriver driver) throws HeadlessException, UnsupportedFlavorException, IOException{
		GenericActions.drpdn_ul_SelectByIndex(driver,By.className("sapMSltArrow"),By.xpath("//ul[@class='sapMSelectList']/li"), 3);
		
		Report report=new Report(driver);

		String materialXpath=".//*[contains(text(),'Material No./Descri')]//parent::div//following::input[1]";

		boolean flag=GenericActions.txt_enter(driver.findElement(By.xpath(materialXpath)), "BEAT-5286");

		report.pass("Material Name ", "material name detialys BEAT-5286 ", true);


		GenericActions.txt_clear(driver.findElement(By.xpath(materialXpath)));

		String lov=".//*[@id='iStatusId-select']";//".//*[contains(text(),'Status:')]//parent::div//following::select[1]";

		GenericActions.drpdn_ul_SelectByVisibleText(driver,By.className("sapMSltArrow"),By.xpath("//ul[@class='sapMSelectList']/li"), "Update Pending");
		GenericActions.drpdn_ul_SelectByIndex(driver,By.className("sapMSltArrow"),By.xpath("//ul[@class='sapMSelectList']/li"), 3);
		
		report.pass("sapMSltArrow", "sapMSltArrow Update Pending ", true);
		report.fail("<a href='https://www.google.com' >Visit W3Schools.com!</a>", "<a href='https://www.google.com' >Visit W3Schools.com!</a>", true);

		GenericActions.btn_Click(driver.findElement(By.xpath(".//*[@class='inctureDialogBtn sapMBarChild sapMBtn sapMBtnBase sapUiSizeCompact sapUiSmallMarginBegin']/div/.//*[contains(text(),'Search')]")));
		report.info("sapMSltArrow", "sapMSltArrow Update Pending ", true);
		GenericActions.sleep(4000);

	}


}
