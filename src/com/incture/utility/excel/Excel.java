package com.incture.utility.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Excel {



	@SuppressWarnings("resource")
	public String[][] xlReadSheet(String sPath, String sheetName) throws Exception {
		File myXL = new File(sPath);
		FileInputStream myStream = new FileInputStream(myXL);
		XSSFWorkbook myWB = new XSSFWorkbook(myStream);
		XSSFSheet mySheet1 = myWB.getSheet(sheetName);
		int xRows0 = mySheet1.getLastRowNum() + 1;
		int xCols0 = mySheet1.getRow(0).getLastCellNum();
		String xData0[][] = new String[xRows0][xCols0];
		for (int i = 0; i < xRows0; i++) {
			XSSFRow row0 = mySheet1.getRow(i);
			for (short k = 0; k < xCols0; k++) {
				XSSFCell cell0 = row0.getCell(k);
				if (cell0 == null) {
					cell0 = row0.createCell(k);
				}
				cell0.setCellType(Cell.CELL_TYPE_STRING);
				String value =cell0.toString();// cellToString(cell0);
				xData0[i][k] = value;
			}
		}
		return xData0;
	}


	public static String cellToString(XSSFCell cell) {
		int type = cell.getCellType();
		Object result;
		switch (type) {		
		
		case XSSFCell.CELL_TYPE_NUMERIC: // 0
			result = cell.getNumericCellValue();
			break;
		case XSSFCell.CELL_TYPE_STRING: // 1
			result = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_FORMULA: // 2
			throw new RuntimeException("We can't evaluate formulas in Java");
		case XSSFCell.CELL_TYPE_BLANK: // 3
			result = "";
			break;
		case XSSFCell.CELL_TYPE_ERROR: // 5
			throw new RuntimeException("This cell has an error");
		default:
			throw new RuntimeException("We don't support this cell type: " + type);
		}
		return result.toString();
	}
	/**
	 * It will create sheet if not exists
	 * @param sPath
	 * @throws Exception
	 */
	public void removeSheet(String sPath,String sheetname) throws Exception{
		try{


			File myXL = new File(sPath);
			System.out.println(myXL.exists());

			if(!myXL.exists())
			{

				FileOutputStream fos = new FileOutputStream(sPath);
				XSSFWorkbook  workbook = new XSSFWorkbook();            

				XSSFSheet sheet = workbook.createSheet(); 
				workbook.write(fos);
				fos.flush();
				fos.close();	
			}

			FileInputStream myStream;
			XSSFWorkbook myWB=null;

			do{
				try{
					myStream = new FileInputStream(myXL);
					myWB = new XSSFWorkbook(myStream);
				}
				catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);


			XSSFSheet mySheet1 = myWB.getSheet(sheetname);
			if(mySheet1!=null)
			{
				myWB.removeSheetAt(myWB.getSheetIndex(sheetname));
				FileOutputStream fos = new FileOutputStream(sPath);
				myWB.write(fos);
				fos.flush();
				fos.close();
			}

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * It will create sheet if not exists
	 * @param sPath
	 * @throws Exception
	 */
	public void createSheet(String sPath,String sheetname) throws Exception{
		try{


			File myXL = new File(sPath);
			System.out.println(myXL.exists());

			if(!myXL.exists())
			{

				FileOutputStream fos = new FileOutputStream(sPath);
				XSSFWorkbook  workbook = new XSSFWorkbook();            

				XSSFSheet sheet = workbook.createSheet(); 
				workbook.write(fos);
				fos.flush();
				fos.close();	
			}


			FileInputStream myStream;
			XSSFWorkbook myWB=null;

			do{
				try{
					myStream = new FileInputStream(myXL);
					myWB = new XSSFWorkbook(myStream);
				}
				catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);


			XSSFSheet mySheet1 = myWB.getSheet(sheetname);
			if(mySheet1==null)
			{
				myWB.createSheet(sheetname);
				FileOutputStream fos = new FileOutputStream(sPath);
				myWB.write(fos);
				fos.flush();
				fos.close();
			}

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	/**
	 * Read the Perticular column based on columnName 
	 * @param sPath
	 * @param sheetName
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public  String[] xlReadColumn (String sPath, String sheetName,String columnName) throws Exception
	{
		boolean columnFound=false;
		int columnIndex=0;

		String fileName = sPath;
		File file = new File(fileName);
		File sameFileName = new File(fileName);
		do{
			Thread.sleep(1);
			if(!file.renameTo(sameFileName))
				System.out.println("Please colose the "+fileName);

		}while(!file.renameTo(sameFileName));


		int xRows1,xCols1;
		String[] xData = {} ;
		try{
			File myXL = new File(sPath);
			//FileInputStream myStream = new FileInputStream(myXL);
			//XSSFWorkbook myWB = new XSSFWorkbook(myStream);



			FileInputStream myStream;
			XSSFWorkbook myWB=null;

			do{
				try{
					myStream = new FileInputStream(myXL);
					myWB = new XSSFWorkbook(myStream);
				}
				catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);


			XSSFSheet mySheet1 = myWB.getSheet(sheetName);
			xRows1 = mySheet1.getLastRowNum()+1;

			xCols1 = mySheet1.getRow(0).getLastCellNum();


			for (short k = 0; k < xCols1; k++)
			{
				XSSFCell cell1 = mySheet1.getRow(0).getCell(k);
				if(cell1==null)
					cell1= mySheet1.getRow(0).createCell(k);
				//String value = cellToString(cell1);
				String value1 = cell1.toString();
				if(value1.equalsIgnoreCase(columnName))
				{
					xData = new String[xRows1-1];
					columnFound=true;
					columnIndex=k;
					break;
				}
			}
			if(!columnFound)
				System.out.println("Given column not found--"+columnName);

			if(columnFound)
				for (int i = 1; i < xRows1; i++)
				{
					XSSFRow row1 = mySheet1.getRow(i);
					if(row1==null)
						row1=mySheet1.createRow(i);
					XSSFCell cell1 = row1.getCell(columnIndex);
					if(cell1==null)
						cell1=row1.createCell(columnIndex);
					String value =cell1.toString();
					xData[i-1] = value;
				}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}


		return xData;
	}
	/**
	 * reading perticular row based on the row num
	 * 
	 * @param sPath
	 * @param sheetName
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public  String[] xlReadRow (String sPath, String sheetName,int row) throws Exception
	{


		String fileName = sPath;
		File file = new File(fileName);
		File sameFileName = new File(fileName);
		do{
			Thread.sleep(3);
			if(!file.renameTo(sameFileName))
				System.out.println("Please colose the "+fileName);

		}while(!file.renameTo(sameFileName));


		int xRows1,xCols1;
		String[] xData = {};
		try{
			File myXL = new File(sPath);


			//FileInputStream myStream = new FileInputStream(myXL);
			//XSSFWorkbook myWB = new XSSFWorkbook(myStream);

			FileInputStream myStream;
			XSSFWorkbook myWB=null;

			do{
				try{
					myStream = new FileInputStream(myXL);
					myWB = new XSSFWorkbook(myStream);
				}
				catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);

			XSSFSheet mySheet1 = myWB.getSheet(sheetName);



			xRows1 = mySheet1.getLastRowNum()+1;

			if(xRows1>=row)
			{
				XSSFRow row1 = mySheet1.getRow(row);
				if(row1==null)
					row1=mySheet1.createRow(row);

				xCols1 = mySheet1.getRow(0).getLastCellNum();
				xData = new String[xCols1];
				for(short k = 0; k < xCols1; k++)
				{

					XSSFCell cell1 = row1.getCell(k);
					if(cell1==null)
						cell1=row1.createCell(k);
					String value = cell1.toString();

					xData[k] = value;

				}

			}
			else{
				System.out.println("Please pass the currect row num , the "+sheetName+" countains only "+xRows1+" no of rows");
			}



		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return xData;
	}
	/**
	 * Reading perticular cell data based on column name and row num
	 * @param sPath
	 * @param sheetName
	 * @param columnName
	 * @param rowCount
	 * @return
	 * @throws Exception
	 */
	public  String xlReadCell (String sPath, String sheetName,String columnName,int rowCount) throws Exception
	{

		boolean columnFound=false;

		String fileName = sPath;
		File file = new File(fileName);
		File sameFileName = new File(fileName);
		String expecteData="";
		do{
			Thread.sleep(3);
			if(!file.renameTo(sameFileName))
				System.out.println("Please colose the "+fileName);

		}while(!file.renameTo(sameFileName));

		try{
			File myXL = new File(sPath);

			//FileInputStream myStream = new FileInputStream(myXL);
			//XSSFWorkbook myWB = new XSSFWorkbook(myStream);

			FileInputStream myStream;
			XSSFWorkbook myWB=null;

			do{
				try{
					myStream = new FileInputStream(myXL);
					myWB = new XSSFWorkbook(myStream);
				}
				catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);


			XSSFSheet mySheet1 = myWB.getSheet(sheetName);

			int xRows1,xCols1;
			xRows1 = mySheet1.getLastRowNum()+1;
			xCols1 = mySheet1.getRow(0).getLastCellNum();


			for (short k = 0; k < xCols1; k++)
			{

				XSSFCell cell1 = mySheet1.getRow(0).getCell(k);
				if(cell1==null)
					cell1= mySheet1.getRow(0).createCell(k);
				//String value = cellToString(cell1);
				String value1 = cell1.toString();
				if(value1.equalsIgnoreCase(columnName))
				{

					columnFound=true;
					if(xRows1>=rowCount)
					{
						XSSFRow row = mySheet1.getRow(rowCount);
						if(row==null)
							row=mySheet1.createRow(rowCount);
						XSSFCell cell=row.getCell(k);

						if(cell==null)
							cell=row.createCell(k);

						expecteData=cell.toString();

					}else{
						System.out.println("Please check the row count");
					}

					break;
				}


			}
			if(!columnFound)
				System.out.println("Given column not found--"+columnName);




		}catch(Exception e){
			System.out.println(e.getMessage());
		}



		return expecteData;
	}
	/**
	 * Reading perticular cell data based on column name and row name
	 * @param sPath
	 * @param sheetName
	 * @param columnName
	 * @param rowCount
	 * @return
	 * @throws Exception
	 */
	public  String xlReadCell (String sPath, String sheetName,String columnName,String rowName) throws Exception
	{

		boolean columnFound=false;

		String fileName = sPath;
		File file = new File(fileName);
		File sameFileName = new File(fileName);
		String expecteData=null;
		do{
			Thread.sleep(3);
			if(!file.renameTo(sameFileName))
				System.out.println("Please colose the "+fileName);

		}while(!file.renameTo(sameFileName));

		try{
			File myXL = new File(sPath);

			//FileInputStream myStream = new FileInputStream(myXL);
			//XSSFWorkbook myWB = new XSSFWorkbook(myStream);

			FileInputStream myStream;
			XSSFWorkbook myWB=null;

			do{
				try{
					myStream = new FileInputStream(myXL);
					myWB = new XSSFWorkbook(myStream);
				}
				catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);


			XSSFSheet mySheet1 = myWB.getSheet(sheetName);

			int xRows1,xCols1;
			xRows1 = mySheet1.getLastRowNum()+1;
			xCols1 = mySheet1.getRow(0).getLastCellNum();


			for (short k = 0; k < xRows1; k++)
			{

				XSSFCell cell1 = mySheet1.getRow(k).getCell(0);
				if(cell1==null)
					cell1= mySheet1.getRow(k).createCell(0);
				//String value = cellToString(cell1);
				String value1 = cell1.toString();
				if(value1.equalsIgnoreCase(rowName))
				{
					columnFound=true;
					int colCell=xlRead_GetColumnCount(sPath, sheetName, columnName);
					
						XSSFCell cell = mySheet1.getRow(k).getCell(colCell);
						

						expecteData=cell.toString();

						break;
				}

			}
			if(!columnFound)
				System.out.println("Given column not found--"+columnName);




		}catch(Exception e){
			System.out.println(e.getMessage());
		}



		return expecteData;
	}
	/**
	 * writing single data basedon column name and rownum , 
	 * @param xlPath
	 * @param sheetName
	 * @param columnName
	 * @param rowNum
	 * @param xlData
	 * @throws Exception
	 */
	public static File file=null;
	 public  void xlWriteData(String xlPath, String sheetName,String columnName, int rowNum, String cellData) throws Exception 
	{

		
			int colNum = 0;			
			boolean columnFound=false;
			String fileName = xlPath;
		
			do{
			if(file!=null)
			{
				Thread.sleep(200);
				System.out.println("Thread is waiting for file to be null");
				
			}
			}while(file!=null);
				
			 file = new File(fileName);
			File sameFileName = file;
			do{
				Thread.sleep(10);
				if(!file.renameTo(sameFileName))
					System.out.println("Please colose the "+fileName);

			}while(!file.renameTo(sameFileName));


			//FileInputStream myStream1 = new FileInputStream(outFile);
			//XSSFWorkbook myWBook = new XSSFWorkbook (myStream1);

			FileInputStream myStream;
			XSSFWorkbook myWB=null;
			FileOutputStream fOut = null;
			do{
				try{
					myStream = new FileInputStream(file);
				
					myWB = new XSSFWorkbook(myStream);
					fOut = new FileOutputStream(file);			
				
				}
				catch (Exception e) {
					System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);

			XSSFSheet oSheet = myWB.getSheet(sheetName);

			int xCols1 = oSheet.getRow(0).getLastCellNum();
			for (short k = 0; k < xCols1; k++)
			{
				XSSFCell cell1 = oSheet.getRow(0).getCell(k);
				if(cell1==null)
					cell1= oSheet.getRow(0).createCell(k);
				String value1 = cell1.toString();
				if(value1.equalsIgnoreCase(columnName))
				{
					columnFound=true;
					colNum=k;
					break;
				}
			}

			if(!(rowNum==0))
				if(columnFound){
					XSSFRow row = oSheet.getRow(rowNum);
					if(row==null)
						row=oSheet.createRow(rowNum);
					XSSFCell cell = row.getCell(colNum);
					if(cell==null)
						cell=row.createCell(colNum);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(cellData);

				}else{
					System.out.println("Given column not found--"+columnName);
				}
			else{
				System.out.println("Hedders cont cheange");
			}



			try{

			
			 myWB.write(fOut);
			fOut.flush();
			fOut.close();
			
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			//methoToWrite(myWB, xlPath);
			file=null; 

	}	


	
	public  void xlWriteData(String xlPath, String sheetName,int colNum, int rowNum, String cellData) throws Exception 
	{
		//int colNum = 0;			
		boolean columnFound=false;
		String fileName = xlPath;
		File file = new File(fileName);
		File sameFileName = new File(fileName);
		do{
			Thread.sleep(10);
			if(!file.renameTo(sameFileName))
				System.out.println("Please colose the "+fileName);

		}while(!file.renameTo(sameFileName));

		File outFile = new File(xlPath);

		//FileInputStream myStream1 = new FileInputStream(outFile);
		//XSSFWorkbook myWBook = new XSSFWorkbook (myStream1);



		FileInputStream myStream;
		XSSFWorkbook myWB=null;

		do{
			try{
				myStream = new FileInputStream(file);
				myWB = new XSSFWorkbook(myStream);
			}
			catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
		}
		while(myWB==null);


		XSSFSheet oSheet = myWB.getSheet(sheetName);






		XSSFRow row = oSheet.getRow(rowNum);
		if(row==null)
			row=oSheet.createRow(rowNum);
		XSSFCell cell = row.getCell(colNum);
		if(cell==null)
			cell=row.createCell(colNum);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(cellData);



		FileOutputStream fOut = new FileOutputStream(outFile);
		myWB.write(fOut);
		fOut.flush();
		fOut.close();	

	}	

	/**
	 * Getting column count
	 * @param xlPath
	 * @param sheetName
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public int xlRead_GetColumnCount(String xlPath, String sheetName,String columnName) throws Exception
	{
		int colNum = 0;
		try{

			boolean columnFound=false;

			File outFile = new File(xlPath);

			//FileInputStream myStream1 = new FileInputStream(outFile);
			//XSSFWorkbook myWBook = new XSSFWorkbook (myStream1);

			FileInputStream myStream;
			XSSFWorkbook myWB=null;

			do{
				try{
					myStream = new FileInputStream(outFile);
					myWB = new XSSFWorkbook(myStream);
				}
				catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);

			XSSFSheet oSheet = myWB.getSheet(sheetName);



			int xCols1 = oSheet.getRow(0).getLastCellNum();
			for (short k = 0; k < xCols1; k++)
			{
				XSSFCell cell1 = oSheet.getRow(0).getCell(k);
				if(cell1==null)
					cell1= oSheet.getRow(0).createCell(k);
				String value1 = cell1.toString();
				if(value1.equalsIgnoreCase(columnName))
				{
					columnFound=true;
					colNum=k;
					break;
				}
			}

			if(!columnFound)
				System.out.println("Given column not found--"+columnName);

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return colNum;
	}
	public int xlRead_GetRowCount(String xlPath, String sheetName,String columnName,String expectedData) throws Exception
	{
		int rowNum = 0;
		try{
			String[] colData=xlReadColumn(xlPath, sheetName, columnName);

			for(int i=0;i<colData.length;i++)
			{
				if(colData[i].equalsIgnoreCase(expectedData))
				{
					rowNum=i+1;
					break;
				}
			}			


		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return rowNum;
	}
	/**
	 * writing arry of data into column
	 * @param xlPath
	 * @param sheetName
	 * @param columnName
	 * @param columnData
	 * @throws Exception
	 */
	public  void xlWriteColumn(String xlPath, String sheetName,String columnName, String columnData[]) throws Exception 
	{
		int colNum = 0;			
		boolean columnFound=false;
		String fileName = xlPath;
		File file = new File(fileName);
		File sameFileName = new File(fileName);
		do{
			Thread.sleep(10);
			if(!file.renameTo(sameFileName))
				System.out.println("Please colose the "+fileName);

		}while(!file.renameTo(sameFileName));

		File outFile = new File(xlPath);

		//FileInputStream myStream1 = new FileInputStream(outFile);
		//	XSSFWorkbook myWBook = new XSSFWorkbook (myStream1);
		FileInputStream myStream;
		XSSFWorkbook myWB=null;

		do{
			try{
				myStream = new FileInputStream(outFile);
				myWB = new XSSFWorkbook(myStream);
			}
			catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
		}
		while(myWB==null);

		XSSFSheet oSheet = myWB.getSheet(sheetName);



		int xCols1 = oSheet.getRow(0).getLastCellNum();
		for (short k = 0; k < xCols1; k++)
		{
			XSSFCell cell1 = oSheet.getRow(0).getCell(k);
			if(cell1==null)
				cell1= oSheet.getRow(0).createCell(k);
			String value1 = cell1.toString();
			if(value1.equalsIgnoreCase(columnName))
			{
				columnFound=true;
				colNum=k;
				break;
			}
		}


		for (int i=1;i<= columnData.length;i++)
		{
			if(columnFound){
				XSSFRow row = oSheet.getRow(i);
				if(row==null)
					row=oSheet.createRow(i);
				XSSFCell cell = row.getCell(colNum);
				if(cell==null)
					cell=row.createCell(colNum);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(columnData[i-1]);
			}
			else{
				System.out.println("Given column not found--"+columnName);
			}
		}







		FileOutputStream fOut = new FileOutputStream(outFile);
		myWB.write(fOut);
		fOut.flush();
		fOut.close();	

	}	


	/**
	 * writing arry of data into row
	 * @param xlPath
	 * @param sheetName
	 * @param rowNumber
	 * @param rowData
	 * @throws Exception
	 */
	public  void xlWriteRow(String xlPath, String sheetName, int rowNumber,String rowData[]) throws Exception 
	{

		String fileName = xlPath;
		File file = new File(fileName);
		File sameFileName = new File(fileName);
		do{
			Thread.sleep(10);
			if(!file.renameTo(sameFileName))
				System.out.println("Please colose the "+fileName);

		}while(!file.renameTo(sameFileName));

		File outFile = new File(xlPath);

		//FileInputStream myStream1 = new FileInputStream(outFile);
		//XSSFWorkbook myWBook = new XSSFWorkbook (myStream1);

		FileInputStream myStream;
		XSSFWorkbook myWB=null;

		do{
			try{
				myStream = new FileInputStream(outFile);
				myWB = new XSSFWorkbook(myStream);
			}
			catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
		}
		while(myWB==null);



		XSSFSheet oSheet = myWB.getSheet(sheetName);
		if(rowNumber!=0){
			XSSFRow row = oSheet.getRow(rowNumber);
			if(row==null)
				row=oSheet.createRow(rowNumber);


			for (int i=0;i< rowData.length;i++)
			{

				XSSFCell cell = row.getCell(i);
				if(cell==null)
					cell=row.createCell(i);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(rowData[i]);

			}
		}else{
			System.out.println("Headders con't modifie pls check the row count");
		}
		FileOutputStream fOut = new FileOutputStream(outFile);
		myWB.write(fOut);
		fOut.flush();
		fOut.close();	

	}	
	/**
	 * writing only for headers
	 * @param xlPath
	 * @param sheetName
	 * @param rowHeaders
	 * @throws Exception
	 */
	public  void xlWriteHeader(String xlPath, String sheetName,String rowHeaders[]) throws Exception 
	{

		String fileName = xlPath;
		File file = new File(fileName);
		File sameFileName = new File(fileName);
		do{
			Thread.sleep(10);
			if(!file.renameTo(sameFileName))
				System.out.println("Please colose the "+fileName);

		}while(!file.renameTo(sameFileName));

		File outFile = new File(xlPath);

		//FileInputStream myStream1 = new FileInputStream(outFile);
		//XSSFWorkbook myWBook = new XSSFWorkbook (myStream1);

		FileInputStream myStream;
		XSSFWorkbook myWB=null;

		do{
			try{
				myStream = new FileInputStream(outFile);
				myWB = new XSSFWorkbook(myStream);
			}
			catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
		}
		while(myWB==null);



		XSSFSheet oSheet = myWB.getSheet(sheetName);

		XSSFRow row = oSheet.getRow(0);
		if(row==null)
			row=oSheet.createRow(0);


		for (int i=0;i< rowHeaders.length;i++)
		{

			XSSFCell cell = row.getCell(i);
			if(cell==null)
				cell=row.createCell(i);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(rowHeaders[i]);

		}

		FileOutputStream fOut = new FileOutputStream(outFile);
		myWB.write(fOut);
		fOut.flush();
		fOut.close();	

	}	





	/**
	 * Writing data based on column & column data mapping , and row count
	 * @param xlPath
	 * @param sheetName
	 * @param rowNum
	 * @param Header_value
	 * @throws Exception
	 */
	public  void xlWrite_MultipleData(String xlPath, String sheetName, int rowNum, HashMap<String, String> Header_value) throws Exception //
	{
		try{

			String fileName = xlPath;
			File file = new File(fileName);
			File sameFileName = new File(fileName);
			do{
				Thread.sleep(1);
				if(!file.renameTo(sameFileName))
					System.out.println("Please colose the "+fileName);

			}while(!file.renameTo(sameFileName));


			File outFile = new File(xlPath);

			//FileInputStream myStream1 = new FileInputStream(outFile);
			//XSSFWorkbook myWBook = new XSSFWorkbook (myStream1);

			FileInputStream myStream;
			XSSFWorkbook myWB=null;

			do{
				try{
					myStream = new FileInputStream(outFile);
					myWB = new XSSFWorkbook(myStream);
				}
				catch (Exception e) {System.out.println("ignore this is in xlRead -->"+e.getMessage());Thread.sleep(100);}
			}
			while(myWB==null);



			XSSFSheet oSheet = myWB.getSheet(sheetName);
			oSheet.setDisplayGridlines(true);
			oSheet.setDefaultColumnWidth(15);
			oSheet.setAutobreaks(true);
			XSSFCellStyle style = myWB.createCellStyle();
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			if(rowNum!=0){
				XSSFRow row = oSheet.getRow(rowNum);
				if(row==null)
					row=oSheet.createRow(rowNum);

				Set set = Header_value.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext()) {
					Map.Entry mentry = (Map.Entry)iterator.next();


					int colN=xlRead_GetColumnCount(xlPath, sheetName,  mentry.getKey().toString());
					XSSFCell cell = row.getCell(colN);
					if(cell==null)
						cell=row.createCell(colN);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(mentry.getValue().toString());
					cell.setCellStyle(style);


				}

			}else{
				System.out.println("Headders con't modifie pls check the row count");
			}
			FileOutputStream fOut = new FileOutputStream(outFile);
			myWB.write(fOut);
			fOut.flush();
			fOut.close();	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}








}

