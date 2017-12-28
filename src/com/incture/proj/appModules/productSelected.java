package com.incture.proj.appModules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.incture.proj.pageObjects.Page1;
import com.incture.utility.Constants;
import com.incture.utility.PlugInFunctions;
import com.incture.utility.excel.Excel;



public class productSelected {

	public static WebElement product_selected(WebDriver driver){
		Excel ex=new Excel();
		String flow="";
		try {
			flow=	ex.xlReadCell(Constants.ExcelPath, "Flow", "Flow", "yes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flow.equals("AM002"))
			return Page1.product_A_M002(driver);
		else if(flow.equals("0066"))
			return Page1.product_0066(driver);
		System.out.println("Error while selecting the product from excel sheet");
		return null;
	}

}
