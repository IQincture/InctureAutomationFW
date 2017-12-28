package randD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataBaseRandD {


	public static void main(String[] args) {
		OracleDB();
	}


	public static void SQLLinuxConnection(){

		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();;  


			//172.16.30.103
			Connection	conn 	=DriverManager.getConnection( "jdbc:mysql://172.16.30.103/sys","root","ConTIntegratioN@321");



		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}


	public static void OracleDB_WB(){
		Connection	conn=null;
		try{

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn =DriverManager.getConnection("jdbc:oracle:thin:@//34.213.118.108:1521/xe","cwpmc","CWPMC");
			Statement st=conn.createStatement();

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {

			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	public static void OracleDB()
	{
		Connection	conn=null;
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//172.16.30.103
		conn =DriverManager.getConnection("jdbc:oracle:thin:@//172.16.30.103:1521/xe","system","system");

		Statement st=conn.createStatement();


		ResultSet rs=st.executeQuery("select * from dept");

		/*while (rs.next()) {
				System.out.println(rs.getString(1));

			}*/


		ResultSetMetaData rsmd=rs.getMetaData();

		int columns = rsmd.getColumnCount();

		for(int i=1;i<=columns;i++)
			System.out.print(rsmd.getColumnName(i)+"		");
		System.out.println();
		System.out.println("----------------------------------------------------");
		while(rs.next()){
			for(int i=1;i<=columns;i++)
				System.out.print(rs.getString(i)+"		");
			System.out.println();
		}



	} catch (Exception e) {
		System.out.println(e.getMessage());
		System.out.println(e);
	}finally {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	}

}
