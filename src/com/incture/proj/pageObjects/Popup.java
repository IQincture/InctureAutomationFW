package com.incture.proj.pageObjects;
import org.openqa.selenium.WebDriver;import org.openqa.selenium.WebElement;import com.incture.utility.PlugInFunctions;import java.io.FileInputStream;import java.io.IOException;
import java.util.List;
public class Popup {
public static List<WebElement> radioButton_list(WebDriver driver) {
	List<WebElement>  element = PlugInFunctions.getListOfWebElements(driver, PlugInFunctions.loadXPathfile().getProperty("radioButton_list"));
	return element;
	}	
public static WebElement okButton(WebDriver driver) {
WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("okButton"));
return element;
}
}
