package com.incture.utility;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class openBrowser {

	
	//public static WebDriver driver;
	public  WebDriver LaunchLocalBrowser(String browserName, String url) throws InterruptedException {
		WebDriver driver=null;
		if(browserName.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", Constants.chromeDriverExe);
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(url);			
		}
		
		return driver;
	}
	
	public  WebDriver LaunchRemoteBrowser(String browserName, String url, String nodeIp, String port) throws MalformedURLException{
		WebDriver driver=null;
		if(browserName.equalsIgnoreCase("chrome")){
			String 	nodeurl="http://"+nodeIp+":"+port+"/wd/hub";
			new DesiredCapabilities();
			DesiredCapabilities cap=DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.WIN10);
			driver = new RemoteWebDriver(new URL(nodeurl), cap);
			driver.get(url);
		}
		return driver;
	}

}
