

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private static XSSFWorkbook xBook;
	private static XSSFSheet xSheet;
	private static XSSFCell xCell;
	private static XSSFRow xRow;
	
	public static void setExcel(String Path, String Sheet){
		
		try {
			FileInputStream xfile = new FileInputStream(Path);
			xBook = new XSSFWorkbook (xfile);
			xSheet = xBook.getSheet(Sheet);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	public static String getCellValue(int iRow, int iCell){
		    
		DataFormatter formatter = new DataFormatter();
		xCell = xSheet.getRow(iRow).getCell(iCell);
		String sValue = formatter.formatCellValue(xCell);
		return sValue;
	
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void setCellValue(String sResult, int iRow, int iCell){
		
		try {
			xRow = xSheet.getRow(iRow);
			xCell = xRow.getCell(iCell, xRow.RETURN_BLANK_AS_NULL);
			if (xCell == null)
				{
				  xCell = xRow.createCell(iCell);
				  xCell.setCellValue(sResult);
				}
			else
				xCell.setCellValue(sResult);
		
			FileOutputStream xFileOut = new FileOutputStream("");
			xBook.write(xFileOut);
			xFileOut.flush();
			xFileOut.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static int ExcelCol(String fileName, String Sheet) throws Exception {
		
		FileInputStream xfile = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/testdata/"+fileName+".xlsx");
		xBook = new XSSFWorkbook (xfile);
		xSheet = xBook.getSheet(Sheet);
		
		int columnCount = xSheet.getRow(0).getLastCellNum();
		return columnCount;
		
		}
	
public static int lastrow(int iRow, int iCell) throws Exception{
		
		System.out.println();
		DataFormatter formatter = new DataFormatter();
		xRow = xSheet.getRow(iRow);
		int count = 0;
		 int  lastrow=0;
		for (Row row : xSheet) {
		    if (row.getCell(iCell) != null) {
		        count += 1; 
		    }
		    
		  lastrow=count-1;  
		}
		 System.out.println("no of rows(data) present: "+lastrow);
		return lastrow;		
}
	
	
public static LinkedHashSet<String> sheetname(String fileName) throws Exception {
	
	FileInputStream xfile = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/testdata/"+fileName+".xlsx");
	xBook = new XSSFWorkbook (xfile);
	int sheets = xBook.getNumberOfSheets();
	System.out.println("no of sheets present in "+fileName+": "+sheets);
	LinkedHashSet<String> sheetNames=new LinkedHashSet<String>();
	
	for(int i=0;i<=sheets-1;i++)
	{
		sheetNames.add(xBook.getSheetName(i));
	}
	  /*Iterator iterator = sheetNames.iterator(); 
	  while (iterator.hasNext()){
		   System.out.println("Value: "+iterator.next() + " ");  
		   }
	*/
	
	return sheetNames;
	
	}	
	
	public static void main(String[] args) throws Exception {
		sheetname("TestData");
	}
	
	
	
}
