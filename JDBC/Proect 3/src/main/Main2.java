package main;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import dBMS.DBMS;
import jdbc.MyConnection;
import jdbc.MyDriver;
import jdbc.MyResultSetMetaData;
import jdbc.MyStatement;
import jdbc.Result_set;

public class Main2 {
	
	public static void main(String [] args) throws SQLException{
		MyDriver driver =new MyDriver();
		String s="any";
		Properties prop= new Properties();
		prop.setProperty("path","Directory");
		Connection connect= driver.connect(s, prop);
		Statement statment=connect.createStatement();
		ResultSet result=statment.executeQuery("select name,surname,id,gpa from data where gpa >3.8;");
		int i=result.findColumn("gpa");
		System.out.println(i);
		System.out.println(" \n");
		System.out.println(result.first());
		System.out.println(" \n");
		//System.out.println(result.getInt(1));
		/*ResultSetMetaData my= result.getMetaData();
		System.out.println(my.getColumnCount());
		System.out.println(my.getColumnLabel(0));
		System.out.println(my.getColumnName(3));
		System.out.println(my.getColumnType(3));
		System.out.println(my.getTableName(3));
	*/
		}

}
