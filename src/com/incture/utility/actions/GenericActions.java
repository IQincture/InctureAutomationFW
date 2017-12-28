package com.incture.utility.actions;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class GenericActions {

	public static void sleep(long timeMs){
		try{
			Thread.sleep(timeMs);

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * txt_enter() is used to enter text in the UI Field by passing below params 
	 * @param element  --> WebElement
	 * @param inputText --> Text that we are entering in the UI.
	 * @return
	 */
	public static boolean txt_enter(WebElement element,String inputText){
		element.sendKeys(inputText);
		element.sendKeys(Keys.TAB);
		sleep(1000);
		return !txt_getText(element).equals("");

	}
	/**
	 * txt_clear() is used to clear the input text UI field
	 * @param element
	 * @return
	 */
	public static boolean txt_clear(WebElement element)
	{
		element.clear();
		sleep(1000);

		return txt_getText(element).equals("");
	}
	/**
	 * 
	 * @param element
	 * @return
	 */
	public static String txt_getText(WebElement element){

		if(!element.getText().equals(""))
			return element.getText();
		else{
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
			String str="";
			element.click();
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
			element.click();
			try{
				str=(String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			}catch (Exception e) {System.out.println(e.getMessage());}

			return str;			
		}

		//return element.getText();
	}
	/**
	 * 
	 * @param element
	 * @param attr
	 * @return
	 */
	public static String txt_getTextFromAttribute(WebElement element,String attr){
		return element.getAttribute(attr);
	}

	public static void btn_Click(WebElement element){

		element.click();	
		sleep(1000);

	}
	public static void btn_DoubbleClick(WebDriver driver,WebElement element) {
		Actions action=new Actions(driver);
		action.doubleClick(element);
		action.build();
		action.perform();		

	}
	public static boolean btn_isEnable(WebElement element){

		boolean flag=element.isEnabled();

		return flag;
	}

	/**
	 * drpdn_ul_SelectByVisibleText is used to select the dropdown values in UI & LI format
	 * @param driver
	 * @param downArrowButton eg:- By.className("sapMSltArrow")
	 * @param liList          eg:- By.xpath("//ul[@class='sapMSelectList']/li")
	 * @param textLov
	 * @return
	 */
	public static boolean drpdn_ul_SelectByVisibleText(WebDriver driver, By downArrowButton,By liList,String textLov){

		driver.findElement(downArrowButton).click();

		List<WebElement> options = driver.findElements(liList);

		for (WebElement opt : options) {
			System.out.println(opt.getText());
			if (opt.getText().equals(textLov)) {
				opt.click();
				return true;
			}
		}

		return false;
	}


	/**
	 * drpdn_ul_SelectByIndex is used to select the dropdown index which is in UI & LI format
	 * @param driver
	 * @param downArrowButton eg:- By.className("sapMSltArrow")
	 * @param liList          eg:- By.xpath("//ul[@class='sapMSelectList']/li")
	 * @param textLov
	 * @return
	 */
	public static boolean drpdn_ul_SelectByIndex(WebDriver driver, By downArrowButton,By liList,int i){

		driver.findElement(downArrowButton).click();
		sleep(2000);
		List<WebElement> options = driver.findElements(liList);

		if(!(options.get(i-1)==null)){
			options.get(i-1).click();
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param driver
	 * @param element
	 * @param textLov
	 */
	public static boolean drpdn_SelectByVisibleText(WebElement element,String textLov){
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(textLov);

		return drpdn_getSelectedOptions(element).equals(textLov);
	}


	public static void drpdn_SelectByIndex(WebElement element,int index){
		Select dropdown = new Select(element);
		dropdown.selectByIndex(index);

	}

	public static boolean drpdn_MultipleSelectByVisibleText(WebElement element,List<String>  textLovList)
	{
		Select dropdown = new Select(element);
		int i=0;
		for(String textLov:textLovList){
			dropdown.selectByVisibleText(textLov);
			i++;
		}
		if(i==textLovList.size())
			return true;

		return false;
	}

	public static String drpdn_getSelectedOptions(WebElement element){
		/*Select dropdown = new Select(element);
		return dropdown.getFirstSelectedOption().getText();*/
		return element.getText();

	}


	public static boolean chbox_SelectCheckBox(WebElement element){
		if(!element.isSelected())
			element.click();
		return element.isSelected();
	}
	public static boolean chbox_UnSelectCheckBox(WebElement element){
		if(element.isSelected())
			element.click();
		return !element.isSelected();
	}

	public static boolean radio_SelectRadioButton(WebElement element){
		element.click();		
		return element.isSelected();
	}


	public static boolean switchTo_Window(WebDriver driver,String windowTitle){

		Set<String> windows=driver.getWindowHandles();
		if(!(windows.size()>1))
			return false;

		for(String window:windows)
		{
			driver.switchTo().window(window);
			if(driver.getTitle().equals(windowTitle))
				return true;
		}
		driver.switchTo().defaultContent();
		return false;
	}

	public static void switchTo_DefaultWindow(WebDriver driver){
		driver.switchTo().defaultContent();
	}


	public static boolean switchTo_AlertAccept(WebDriver driver){
		try{
			driver.switchTo().alert().accept();
			return true;
		}catch(Exception e){
			System.out.println("No alert exception"+e.getMessage());
			return false;
		}
	}

	public static boolean  switchTo_AlertDismiss(WebDriver driver){
		try{
			driver.switchTo().alert().dismiss();
			return true;
		}catch(Exception e){
			System.out.println("No alert exception"+e.getMessage());
			return false;
		}
	}
	public static String switchTo_AlertGetText(WebDriver driver){
		try{
			return driver.switchTo().alert().getText();
		}catch(Exception e){
			System.out.println("No alert exception"+e.getMessage());
			return null;
		}
	}

	public static boolean  switchTo_Frame(WebDriver driver,int frameindex){

		try{
			driver.switchTo().frame(frameindex);

			return true;
		}catch (Exception e) {

			System.out.println("NoSuchFrameException "+e.getMessage());
			return false;
		}
	}

	public static boolean  switchTo_Frame(WebDriver driver,String frameName){

		try{
			driver.switchTo().frame(frameName);

			return true;
		}catch (Exception e) {

			System.out.println("NoSuchFrameException "+e.getMessage());
			return false;
		}
	}
	public static boolean  switchTo_Frame(WebDriver driver,WebElement element){

		try{
			driver.switchTo().frame(element);
			return true;
		}catch (Exception e) {
			System.out.println("NoSuchFrameException "+e.getMessage());
			return false;
		}
	}
	public static boolean  switchTo_FrameParent(WebDriver driver){

		try{
			driver.switchTo().parentFrame();
			return true;
		}catch (Exception e) {
			System.out.println("NoSuchFrameException "+e.getMessage());
			return false;
		}
	}



	public static void launchUrl(WebDriver driver,String url){

		driver.get(url);
		sleep(2000);
	}











}
