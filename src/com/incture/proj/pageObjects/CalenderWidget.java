package com.incture.proj.pageObjects;
import org.openqa.selenium.WebDriver;import org.openqa.selenium.WebElement;import com.incture.utility.PlugInFunctions;import java.io.FileInputStream;import java.io.IOException;
import java.util.List;
public class CalenderWidget {
	public static List<WebElement> day_list(WebElement driver) {
		List<WebElement>  element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty("day_list"));
		return element;
	}	


	public static WebElement monthShowed(WebElement driver) {
		WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("monthShowed_list"));
		return element;
	}
	public static List<WebElement> months_list(WebElement driver) {
		List<WebElement>  element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty("months_list"));
		return element;
	}	
	public static WebElement yearShowed(WebElement driver) {
		WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("yearShowed"));
		return element;
	}
	public static List<WebElement> years_list(WebElement driver) {
		List<WebElement>  element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty("years_list"));
		return element;
	}	
	public static WebElement goRight(WebElement driver) {
		WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("goRight"));
		return element;
	}
	public static WebElement goLeft(WebElement driver) {
		WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("goLeft"));
		return element;
	}
	public static List<WebElement> maxYearShowed_list(WebElement driver) {
		List<WebElement>  element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty("maxYearShowed_list"));
		return element;
	}	
	public static List<WebElement> minYearShowed_list(WebElement driver) {
		List<WebElement>  element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty("minYearShowed_list"));
		return element;
	}	
}
