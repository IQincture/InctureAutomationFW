package com.incture.proj.testNg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.incture.utility.browser.MultipleBrowser;
import com.incture.utility.listener.CustomListeneresImplementation;
@Listeners(CustomListeneresImplementation.class)
public class MakingReport {
	
	WebDriver driver=null;
	
	@BeforeMethod
	public void beforeMethod(){
		
		
		driver=new MultipleBrowser().getBrowserDriver("chrome");
		driver.get("https://fioridev.murphyoilcorp.com/sap/bc/ui5_ui5/ui2/ushell/shells/abap/FioriLaunchpad.html?sap-client=100&sap-language=EN#ZSLOCSem-display");
		
	}
	
	
	@Test
	public void test(){
		m1();
		
	}
	
	public void m1(){
		
		try{
			
			
			System.out.println(driver.getPageSource());
		
			
			
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
