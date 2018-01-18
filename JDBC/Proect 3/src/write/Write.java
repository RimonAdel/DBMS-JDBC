package write;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

public class Write {
	private  BufferedWriter output = null;
	private File file= null;
	public String filename= null;
	public static  Write write=null;
	public Write(String tableName){
           
		filename="Directory\\"+tableName+".xml"; 
			file = new File(filename);
	}
	public void startNode(String string){
		try {
			FileWriter writer = new FileWriter(file,true);
			 output = new BufferedWriter(writer);
			 PrintWriter out = new PrintWriter(output);
			 out.print("<"+string+">");
			 out.println();
			 if ( output != null ) {
				 output.close();
					writer.close();
		      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	public void endNode(String string){
		try {
			FileWriter writer = new FileWriter(file,true);
			 output = new BufferedWriter(writer);
			 PrintWriter out = new PrintWriter(output);
			 out.print("</"+string+">");
			 out.println();
			 if ( output != null ) {
				 output.close();
					writer.close();
		      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeCharachter(String string){
		try {
			FileWriter writer = new FileWriter(file,true);
			 output = new BufferedWriter(writer);
			 PrintWriter out = new PrintWriter(output);
			 out.print(string);
			 out.println();
			 if ( output != null ) {
				 output.close();
					writer.close();
		      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public void deleteLastLine(){
	
	RandomAccessFile randomAccessFile;
	try {
		randomAccessFile = new RandomAccessFile(filename, "rw");
		byte b;
	long length = randomAccessFile.length() ;
	if (length != 0) {
	    do {
	        length -= 1;
	        randomAccessFile.seek(length);
	        b = randomAccessFile.readByte();
	    } while (b != 10 && length > 0);
	    randomAccessFile.setLength(length);
	    randomAccessFile.close();
	} }catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
public void writeFirstLine(ArrayList<String> colname,ArrayList<String> coltype){
	try{
		//System.out.println("1");
	FileWriter writer = new FileWriter(file,true);
	 output = new BufferedWriter(writer);
	 PrintWriter out = new PrintWriter(output);
	 out.print("<?ignore ");
	 for (int i=0 ;i<coltype.size()-1;i++){
		 if(coltype.get(i).equalsIgnoreCase("varchar")){
			 out.print(colname.get(i)+","+"string"+",");
		 }
		 else 
			 {
			  out.print(colname.get(i)+","+coltype.get(i)+",");
		 }
		
	 }
		for(int i=0;i<coltype.size();i++){
			System.out.println(colname.get(i));
			System.out.println(coltype.get(i));
		}
	if(coltype.get(colname.size()-1).equalsIgnoreCase("varchar")){
		 out.println(colname.get(colname.size()-1)+","+"string"+" ?>");
	}
	else{
		out.println(colname.get(colname.size()-1)+","+"string"+" ?>");
	}
	 
	 if ( output != null ) {
			output.close();
			writer.close();
     }
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
public void createDirectory(String s){
	File file= new File(s);
	if(!file.exists()){
		file.mkdirs();
	}
}
public void Createttablenode(String tableName){
	try {
		FileWriter writer = new FileWriter(file,true);
		 output = new BufferedWriter(writer);
		 PrintWriter out = new PrintWriter(output);
		 out.print("<"+"table");
		 out.println();
		 out.println("xmlns="+'"'+"https://www.w3schools.com"+'"');
		 out.println("xmlns:xsi="+'"'+"http://www.w3.org/2001/XMLSchema-instance"+'"');
		 out.println("xsi:schemaLocation="+'"'+"https://www.w3schools.com "+tableName+".xsd"+'"'+">");
		 if ( output != null ) {
			 output.close();
				writer.close();
	      }
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public void Createtable(String tableName,String tableNameop,ArrayList<String> colname,ArrayList<String> coltype){
	writeFirstLine(colname,coltype);
	Createttablenode(tableNameop);
	endNode("table");	
}
}
