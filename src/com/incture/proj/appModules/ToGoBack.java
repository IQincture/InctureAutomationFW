package com.incture.proj.appModules;

import org.openqa.selenium.WebDriver;

import com.incture.proj.pageObjects.Page2;

public class ToGoBack {
	public static void clickToFirstPage(WebDriver driver){
		Page2.backButton(driver).click();
	}
}
