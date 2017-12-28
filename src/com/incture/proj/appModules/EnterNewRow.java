package com.incture.proj.appModules;

import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.incture.proj.pageObjects.Page2;
import com.incture.utility.CalenderController;


public class EnterNewRow {

	public static void enterNewRow(WebDriver driver, String enterFromDate, String enterToDate) throws InterruptedException, ParseException{
		List<WebElement> elementList = Page2.calenderButton(driver);
		int size = elementList.size();
		WebElement fromDateElement = elementList.get(size-2);
		WebElement toDateElement = elementList.get(size-1);
		CalenderController.selectDate(driver, fromDateElement, enterFromDate);
		CalenderController.selectDate(driver, toDateElement, enterToDate);
	}

}
