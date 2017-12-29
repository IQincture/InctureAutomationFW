import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DB {

	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static String DB_URL_db = new String("jdbc:mysql://localhost");

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "root";
	   
	   static Connection conn = null;
	   static Statement stmt = null;

	
	   public static void main(String[] args) throws Exception {
	
		   System.out.println("Total Num of files is "+FolderList.fileList().size());
		   for(int j=0;j<FolderList.fileList().size();j++)
			{
				System.out.println(FolderList.fileList().get(j).replace(".xlsx", ""));
				DB b=new DB(DB_URL_db, FolderList.fileList().get(j).replace(".xlsx", ""));
			}
		   
		 //  DB b=new DB(DB_URL_db, "TestData1");
		  
		   
	}
	   
	   /** constructor
	    * connection to the database
	    * calling createDB(String excelName) method at the end
	    * 
	    * @param DB_URL
	    * @param excelname 
	    */
	   
	protected DB(String DB_URL,String excelname) {
		
		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      System.out.println("Connection successful");
		   }catch(SQLException se){
		      //Handle errors for JDBC
			   System.out.println("Connection failed");
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
			   System.out.println("Connection failed");
		      e.printStackTrace();
		   }
		  
		  createDB(excelname);
		  
		   
		   
		   
		}
		
	
	/**constructor
	 * connection to the particular schema(database)
	 * 
	 * @param DB_URL_table
	 */
	
	protected DB(String DB_URL_table) {
		
		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL_table, USER, PASS);
		      System.out.println("Connection successful");
		   }catch(SQLException se){
		      //Handle errors for JDBC
			   System.out.println("Connection failed");
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
			   System.out.println("Connection failed");
		      e.printStackTrace();
		   }
		   
		  
		   
		}
	/**method
	 * creating database(schema) based on the .xlsx file name
	 * DB(String DB_URL_table) call to constructor
	 * calling createTable(String excelName) method
	 * @param excelName
	 */
	
		protected static void createDB(String excelName) {
			
			
			
			 //STEP 4: Execute a query
		      System.out.println("Creating database...");
		    
				try {
					stmt = conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		      
		      String sql = "CREATE DATABASE "+excelName;
		      try {
				stmt.executeUpdate(sql);
				 
				 
				 
				 System.out.println("database creation successful...");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("database creation failed...");
				e.printStackTrace();
			}
		     
		      DB_URL_db = "jdbc:mysql://localhost/"+excelName;
				 DB db=new DB(DB_URL_db);
			createTable(excelName);
			
		}
		
		
		/**method
		 * calling LinkedHashSet<String> sheetname(String fileName) method and iterating through sheet names
		 * calling table(String excelName,String sheetName) method for table creation
		 * 
		 * @param excelName
		 */
		protected static void createTable(String excelName)  {
			
			 System.out.println("Creating database table...");
			
			Iterator iterator;
			try {
				iterator = ExcelUtility.sheetname(excelName).iterator();
			
			  while (iterator.hasNext()){
				   String sheet=(String) iterator.next();
				   table(excelName, sheet);
				   
				   }
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			}
		
		/**method
		 * get the last no of column (present in 0th row) and creating columns with VARCHAR(30) type
		 * calling insertData(String excelName,String sheetName) method for data insert
		 * @param excelName
		 * @param sheetName
		 */
		
		protected static void table(String excelName,String sheetName)
		{
			ExcelUtility.setExcel(System.getProperty("user.dir")+"/src/main/java/testdata/"+excelName+".xlsx", sheetName);
			 String sql1 = "CREATE TABLE "+sheetName+" (";
				try {
					for(int i=0;i<=ExcelUtility.ExcelCol(excelName, sheetName)-1;i++){
						
						String sql=ExcelUtility.getCellValue(0, i)+" VARCHAR(30)";
						
						if(i>=1==true){
						sql1=sql1+","+sql;	
						}
						else{
							sql1=sql1+sql;	
							
						}
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      System.out.println(sql1+");");
						
						
						try {
							stmt = conn.createStatement();
							stmt.executeUpdate(sql1+");");
							System.out.println("table creation successful..");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							System.out.println("table creation failed..");
							e.printStackTrace();
						}
						insertData(excelName, sheetName);
						
		}
		
		/**method
		 * 
		 * takes the last row no and insert the values
		 * 
		 * @param excelName
		 * @param sheetName
		 */
	
		protected static void insertData(String excelName,String sheetName) {
			
			//insert into CUSTOMER values (001,admin,admin,5555);
			
			ExcelUtility.setExcel(System.getProperty("user.dir")+"/src/main/java/testdata/"+excelName+".xlsx", sheetName);
			try {
				for(int i=1;i<=ExcelUtility.lastrow(0, 0);i++)
				{
					String sql1 = "INSERT INTO "+sheetName+" values (";
					for(int j=0;j<=ExcelUtility.ExcelCol(excelName, sheetName)-1;j++){
					String sql="'"+ExcelUtility.getCellValue(i, j)+"'";
					
					if(j>=1==true){
					sql1=sql1+","+sql;	
					}
					else{
						sql1=sql1+sql;	
						
					}
				}
					System.out.println(sql1+");");
				stmt = conn.createStatement();
		        stmt.executeUpdate(sql1+");");
				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	
	
	
	
}

