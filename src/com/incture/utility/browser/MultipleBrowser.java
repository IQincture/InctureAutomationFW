package com.incture.utility.browser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.incture.proj.suite.parameters.XmlParameters;
import com.incture.utility.Constants;

public class MultipleBrowser {

	DesiredCapabilities capability = null;
	WebDriver driver = null;

	public WebDriver getBrowserDriver(String browser) {

		if(Constants.isRemote.equalsIgnoreCase("No"))
		{

			switch (browser.toLowerCase()) {

			case "firefox":				

				driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

				break;
			case "ie10":
				System.setProperty("webdriver.ie.driver",Constants.IEDriverServerExe);
				capability = DesiredCapabilities.internetExplorer();
				capability.setCapability("nativeEvents", false);
				driver = new InternetExplorerDriver(capability);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				break;
			case "ie11":
				System.setProperty("webdriver.ie.driver",Constants.IEDriverServerExe);
				capability = DesiredCapabilities.internetExplorer();
				capability.setCapability("nativeEvents", false);
				driver = new InternetExplorerDriver(capability);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);			
				break;
			case "chrome":
				System.setProperty("webdriver.chrome.driver",Constants.chromeDriverExe);
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				break;
			case "android":
				break;
			case "ios":
				break;
			case "ios-ipad":
				break;
			default:
				driver = null;


			}

		}else if(Constants.isRemote.equalsIgnoreCase("Yes")) {
			String url="http://"+XmlParameters.nodeIp+":"+XmlParameters.port+"/wd/hub";

			switch (browser.toLowerCase()) {

			case "firefox":	
				capability=DesiredCapabilities.firefox();
				capability.setBrowserName("firefox");
				capability.setPlatform(Platform.WIN10);	
				try {
					driver = new RemoteWebDriver(new URL(url), capability);
				} catch (MalformedURLException e) {System.out.println(e.getMessage());}
				break;

			case "chrome":
				capability=DesiredCapabilities.chrome();
				capability.setBrowserName("chrome");
				capability.setPlatform(Platform.WIN10);
				//capability.setCapability("screen-resolution", "1280x800");
				try {
					driver = new RemoteWebDriver(new URL(url), capability);
				} catch (MalformedURLException e) {System.out.println(e.getMessage());}
				break;
			case "ie10":
				break;
			case "android":
				break;
			case "ios":
				break;
			case "ios-ipad":
				break;
			default:
				driver=null;

			}
			
		}


		return driver;

	}
}
