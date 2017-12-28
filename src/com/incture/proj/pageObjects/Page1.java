package com.incture.proj.pageObjects;
import org.openqa.selenium.WebDriver;import org.openqa.selenium.WebElement;import com.incture.utility.PlugInFunctions;import java.io.FileInputStream;import java.io.IOException;
import java.util.List;
public class Page1 {
public static WebElement product_A_M002(WebDriver driver) {
WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("product_A-M002"));
return element;
}
public static WebElement product_0066(WebDriver driver) {
WebElement element = PlugInFunctions.expWait(driver, PlugInFunctions.loadXPathfile().getProperty("product_0066"));
return element;
}
}
