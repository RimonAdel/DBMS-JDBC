package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import dBMS.DBMS;
import write.Write;

public class Main {
	public static void main(String[] args){
		DBMS dbms =DBMS.getInstance();
		dbms.setpath("Directory");
	/*ArrayList<String> names= new ArrayList<String>();
		names.add("name");
		names.add("surname");
		names.add("gpa");
		names.add("id");
		ArrayList<String> types= new ArrayList<String>();
		types.add("varchar");
		types.add("varchar");
		types.add("varchar");
		types.add("int");
		dbms.createTable("data",names,types);
				*//*HashMap<String,String> newElement= new HashMap<String,String>();
		newElement.put("name", "any");
		newElement.put("surname", "abo any");
		newElement.put("gpa", "1.7");
		newElement.put("id", "6354");
		if(dbms.validateBeforeinsert("data3", newElement)){
			dbms.appendXML("data3", newElement);
		}*/
		//dbms.query("data3", "gpa", "4", '=');
		//System.out.println(dbms.delete("data3","gpa", "4", '<'));
		//dbms.validationByXSD("data2");
	//	dbms.dropTable("data3");
		
		/**/System.out.println("enter the expression: ");
		Scanner S = new Scanner (System.in);
		String input = S.nextLine(); 
		try {
			dbms.callMethods(input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
