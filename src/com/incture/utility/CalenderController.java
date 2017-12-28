package com.incture.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.incture.proj.pageObjects.CalenderWidget;


public class CalenderController {
	public static void selectDate(WebDriver driver, WebElement dateWidget, String expectedDate) throws InterruptedException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.dateFormat);
		Date parse = sdf.parse(expectedDate);
		Calendar c = Calendar.getInstance();
		c.setTime(parse);
		String month = Month.of(c.get(Calendar.MONTH)+1).name();
		String date = String.valueOf(c.get(Calendar.DATE));
		String year = String.valueOf(c.get(Calendar.YEAR));
		
		dateWidget.click();
		Thread.sleep(3000);
		CalenderController sl = new CalenderController();
		sl.getMonth(dateWidget, month).click();
		sl.getYear(dateWidget, year).click();
		sl.getDay(dateWidget, date).click();
	}
	
	public WebElement getDay(WebElement dateWidget, String day){
		List<WebElement> columns=CalenderWidget.day_list(dateWidget);
		int dayInInt = Integer.parseInt(day);
		List<WebElement> el = new ArrayList<WebElement>();
		for (WebElement cell : columns)
        {
			
			if (cell.getText().equals(day))
           {
              el.add(cell);
           }
        }
		if(dayInInt>0 && dayInInt<25){
			return el.get(0);
		}
		return el.get(el.size()-1);
	}
	
	public WebElement getMonth(WebElement dateWidget, String month){
		CalenderWidget.monthShowed(dateWidget).click();
		List<WebElement> columns= CalenderWidget.months_list(dateWidget);
		for(WebElement cell : columns)
		{
			 if(cell.getText().equalsIgnoreCase(month))
	         {
	        	 return cell;
	         }
		}
		return null;
	}
	
	public WebElement getYear(WebElement dateWidget, String year){
		try{
		CalenderWidget.yearShowed(dateWidget).click();
		int maxYearShowed = getMaxYearShowed(dateWidget);
		int minYearShowed = getMinYearShowed(dateWidget);
		if(Integer.parseInt(year)>maxYearShowed){
			do{
				clickNextIcon(dateWidget);
				maxYearShowed = getMaxYearShowed(dateWidget);
			}while(Integer.parseInt(year)>maxYearShowed);
		}
		else if(Integer.parseInt(year)<minYearShowed){
			do{
				clickPreviousIcon(dateWidget);
				minYearShowed = getMinYearShowed(dateWidget);
			}while(Integer.parseInt(year)<minYearShowed);
		}
		List<WebElement> columns= CalenderWidget.years_list(dateWidget);
		for(WebElement cell : columns)
		{
			 if(cell.getText().equals(year))
	         {
	        	 return cell;
	         }
		}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public static void clickNextIcon(WebElement dateWidget){
		CalenderWidget.goRight(dateWidget).click();
	}
	
	public static void clickPreviousIcon(WebElement dateWidget){
		CalenderWidget.goLeft(dateWidget).click();
	}
	
	public static int getMaxYearShowed(WebElement dateWidget){
		List<WebElement> columns= CalenderWidget.maxYearShowed_list(dateWidget);
		int colSize = columns.size();
		return Integer.parseInt(columns.get(colSize-1).getText());
	}
	
	public static int getMinYearShowed(WebElement dateWidget){
		List<WebElement> columns= CalenderWidget.minYearShowed_list(dateWidget);
		return Integer.parseInt(columns.get(0).getText());
	}
}
