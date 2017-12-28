package com.incture.proj.pageObjects;
import org.openqa.selenium.WebDriver;import org.openqa.selenium.WebElement;import com.incture.utility.PlugInFunctions;import java.io.FileInputStream;import java.io.IOException;
import java.util.List;
public class Page2 {
public static WebElement addRowButton(WebDriver driver) {
WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("addRowButton"));
return element;
}
public static List<WebElement> row_list(WebDriver driver) {
	List<WebElement>  element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty("row_list"));
	return element;
	}	
public static WebElement backButton(WebDriver driver) {
WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("backButton"));
return element;
}


public static List<WebElement> calenderButton(WebDriver driver){
	List<WebElement>   element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty("calenderButton"));
	return element;
}
}
