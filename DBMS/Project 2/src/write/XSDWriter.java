package write;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class XSDWriter {
	
	public boolean createXSDFile(String tablename,ArrayList<String> colname,ArrayList<String> coltype){
		File file=new File("Directory"+"\\"+tablename+".xsd");
		if(!file.exists()){
			FileWriter writer;
			try {
				writer = new FileWriter(file);
				BufferedWriter output = new BufferedWriter(writer);
				 PrintWriter out = new PrintWriter(output);
				 out.println("<?xml version="+'"'+"1.0"+'"'+"?>");
				 out.println("<xs:schema xmlns:xs="+'"'+"http://www.w3.org/2001/XMLSchema"+'"');
				 out.println("targetNamespace="+'"'+"https://www.w3schools.com"+'"');
				 out.println("xmlns="+'"'+"https://www.w3schools.com"+'"');
				 out.println("elementFormDefault="+'"'+"qualified"+'"'+">");
				 out.println("<xs:element name="+'"'+"table"+'"'+">");
				 out.println("<xs:complexType>");
				 out.println("<xs:sequence>");
				 out.println("<xs:element name="+'"'+"Element"+'"'+" "+ "maxOccurs="+'"'+"unbounded"+'"'+">");
				 out.println("<xs:complexType>");
				 out.println("<xs:sequence>");
				 for(int i=0;i<colname.size();i++){
					 System.out.print(coltype.get(i));
					 if(coltype.get(i).equalsIgnoreCase("varchar")){
						 out.println("<xs:element name="+'"'+colname.get(i)+'"'+" "+"type="+'"'+"xs:"+"string"+'"'+"/>");
					 }
					 else if(coltype.get(i).equalsIgnoreCase("int")){
						 out.println("<xs:element name="+'"'+colname.get(i)+'"'+" "+"type="+'"'+"xs:"+"int"+'"'+"/>");
					 }
					 else {
						 System.out.print("error");
					 }
				 }
				 out.println("</xs:sequence>");
				 out.println("</xs:complexType>");
				 out.println("</xs:element>");
				 out.println("</xs:sequence>");
				 out.println("</xs:complexType>");
				 out.println("</xs:element>");
				 out.println("</xs:schema>"); 
				 output.close();
				 out.close();
			return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
