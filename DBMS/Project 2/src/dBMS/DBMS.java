package dBMS;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.crypto.dsig.Transform;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.Database;
import write.Write;
import write.XSDWriter;

public class DBMS implements Database {
	private static DBMS dbms=null;
	private static String newpath;
	private  String tableName1;
	String REGEX_CT = "(\\s*)(CREATE)(\\s+)(TABLE)(\\s+)([A-Za-z0-9._%]+)(\\s*)(\\()((\\s*)([A-Za-z0-9._%]+)(\\s+)(varchar|int)(\\s*)(\\,|))+(\\s*)(\\))(\\s*)(;)(\\s*)";
	  String REGEX_DT = "(\\s*)(DROP)(\\s+)(TABLE)(\\s+)([A-Za-z0-9._%]+)(\\s*)(;)(\\s*)"; 
	  String REGEX_DFT = "(\\s*)(DELETE)(\\s+)(FROM)(\\s+)([A-Za-z0-9_.%]+)(\\s+)(WHERE)(\\s+)([A-Za-z0-9_.%]+(\\s*)(<|>|=)(\\s*)([A-Za-z0-9_.%]+))(\\s*)(;)(\\s*)";
	  String REGEX_II = "(\\s*)(INSERT)(\\s+)(INTO)(\\s+)([a-zA-z0-9._]+)(\\s*)(\\()((\\s*)([a-zA-z0-9._]+)(\\s*)(,(\\s*)([a-zA-z0-9._]+)(\\s*))*)(\\))(\\s+)(VALUES)(\\s*)(\\()((\\s*)([a-zA-z0-9._]+)(\\s*)(,(\\s*)([a-zA-z0-9._]+)(\\s*))*)(\\))(\\s+|)(;)";
	  String REGEX_SF ="(\\s*)(SELECT)(\\s+)(([a-zA-z0-9._]+)(\\s*)(,(\\s*)(\\w+))*)(\\s+)(FROM)(\\s+)([a-zA-z0-9._]+)(\\s+)(WHERE)(\\s+)(([a-zA-z0-9._]+)(\\s*)(>|<|=)(\\s*)([a-zA-z0-9._]+))(\\s*)(;)";
	  
private DBMS(){
	
}
public static DBMS getInstance(){
	if(dbms==null){
		DBMS dbms=new DBMS();
		
		return dbms;
		
	}else{
		return dbms;
	}
	
	//return dbms;
}
public void setpath(String path){
	newpath=path;
}
	public Object[][] query(String tablename, String field, String value, char condition) {
		StringBuffer rawXML = null;
		tableName1=tablename;
		String filename = newpath+"\\"+ tablename + ".xml";
		int flag = 0, flag2 = 0,counter=0;
		XMLEvent xMLEvent;
		List<List<String>> listoflist = new ArrayList<List<String>>();
		int conditionvalue = 0;
		switch (condition) {
		case '=':
			conditionvalue = 0;
			break;
		case '>':
			conditionvalue = 1;
			break;
		case '<':
			conditionvalue = -1;
			break;
		}
		System.out.println(conditionvalue);
		try {
			File xMLFile = new File(filename);
			XMLEventReader xMLEventReader = XMLInputFactory.newInstance()
					.createXMLEventReader(new FileInputStream(xMLFile));
			HashMap<String, String> map = new HashMap<String, String>();
			map = getColunmsinMap(tablename);
			ArrayList<String> colunms = getnameofcoluoms(tablename);
			int i = 0;
			while (xMLEventReader.hasNext()) {
				xMLEvent = xMLEventReader.nextEvent();
				switch (xMLEvent.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					if (((((StartElement) xMLEvent).getName()).getLocalPart()) == "Element") {
						flag = 1;

					}
					break;
				case XMLStreamConstants.CHARACTERS:
					Characters characters = (Characters) xMLEvent;
					if (!(characters.isWhiteSpace() || characters.isIgnorableWhiteSpace())) {
						if (flag == 1) {
							String data = characters.getData().replaceAll("\n", "");
							map.put(colunms.get(i), data);
							// System.out.println(colunms[i]);
						}
						i++;
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (((EndElement) xMLEvent).getName().getLocalPart() == "Element") {
						flag = 0;
						i = 0;
						if (map.get(field).compareToIgnoreCase(value) == 0) {
							flag2 = 1;
						}
						if (map.get(field).compareToIgnoreCase(value) == conditionvalue) {
							HashMap<String, String> map2 = new HashMap<String, String>();
							ArrayList<String> list = new ArrayList<String>();
							for (int j = 0; j < colunms.size(); j++) {
								map2.put(colunms.get(j), map.get(colunms.get(j)));
							}
							for (int j = 0; j < colunms.size(); j++) {
								list.add(j, map2.get(colunms.get(j)));
							}
							listoflist.add(list);
							counter++;
						} else {
							if (map.get(field).compareToIgnoreCase(value) != 0) {
								if (map.get(field).compareToIgnoreCase(value)
										/ Math.abs(map.get(field).compareToIgnoreCase(value)) == conditionvalue) {
									ArrayList<String> list = new ArrayList<String>();
									HashMap<String, String> map2 = new HashMap<String, String>();
									for (int j = 0; j < colunms.size(); j++) {
										map2.put(colunms.get(j), map.get(colunms.get(j)));
									}
									for (int j = 0; j < colunms.size(); j++) {
										list.add(j, map2.get(colunms.get(j)));
									}
									listoflist.add(list);
								}
								counter++;
							}

						}
					}

					break;

				}
			}
			String[][] result = new String[listoflist.size()][colunms.size()];
			for (int j = 0; j < listoflist.size(); j++) {
				for (int f = 0; f < colunms.size(); f++) {
					result[j][f] = listoflist.get(j).get(f);
				}
			}
			for (int j = 0; j < listoflist.size(); j++) {
				for (int f = 0; f < colunms.size(); f++) {
					System.out.print(result[j][f]+"  ");
				}
				System.out.println();
			}
			return result;
		} catch (FileNotFoundException | XMLStreamException | FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public int delete(String tablename, String field, String value, char condition) {
		tableName1=tablename;
		int counter = 0;
		StringBuffer rawXML = null;
		int flag3 = 1;
		String filename = newpath + tablename + ".xml";
		int flag = 0, flag2 = 0;
		XMLEvent xMLEvent;
		int conditionvalue = 0;
		switch (condition) {
		case '=':
			conditionvalue = 0;
			break;
		case '>':
			conditionvalue = 1;
			break;
		case '<':
			conditionvalue = -1;
			break;
		}
		System.out.println(conditionvalue);
		try {
			
			File xMLFile = new File(filename);
			String newfile = newpath + "temp";
			File file= new File(newfile+".xml");
			file.createNewFile();
			Write Writer = new Write("temp");
			XMLEventReader xMLEventReader = XMLInputFactory.newInstance()
					.createXMLEventReader(new FileInputStream(xMLFile));
			HashMap<String, String> map = new HashMap<String, String>();
			map = getColunmsinMap(tablename);
			ArrayList<String> colunms = getnameofcoluoms(tablename);
			ArrayList<String> types = gettypesofcoluoms(tablename);
			for (int j = 0; j < colunms.size(); j++) {
				System.out.println(colunms.get(j) + "," + types.get(j));
			}
			Writer.Createtable("temp",tablename,colunms, types);
			int i = 0;
			while (xMLEventReader.hasNext()) {
				xMLEvent = xMLEventReader.nextEvent();
				switch (xMLEvent.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					if (((((StartElement) xMLEvent).getName()).getLocalPart()) == "Element") {
						flag = 1;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					Characters characters = (Characters) xMLEvent;
					if (!(characters.isWhiteSpace() || characters.isIgnorableWhiteSpace())) {
						if (flag == 1) {
							String data = characters.getData().replaceAll("\n", "");
							map.put(colunms.get(i), data);

						}
						i++;
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (((EndElement) xMLEvent).getName().getLocalPart() == "Element") {
						int temp = 0,temp2=0;
						flag = 0;
						i = 0;
						System.out.println(map.get(field)+" "+map.get(field).compareToIgnoreCase(value));
						if (map.get(field).compareToIgnoreCase(value) == 0 && conditionvalue != 0) {
							temp2=1;
							appendXML("temp", map);
							flag2 = 1;
						}
						if (map.get(field).compareToIgnoreCase(value) == 0) {
							flag3 = 0;
						//	System.out.println("here2"+map.get(field));
						}
						if (map.get(field).compareToIgnoreCase(value) ==0
								&& conditionvalue == 0) {
							counter++;
							//System.out.println(map.get(field));
							temp=1;
						}
						if (map.get(field).compareToIgnoreCase(value) != 0) {
							if (map.get(field).compareToIgnoreCase(value)
									/ Math.abs(map.get(field).compareToIgnoreCase(value)) == conditionvalue) {
								if (temp==0){
								counter++;	
								}
								
								//System.out.println(map.get(field));
							}
							} if(map.get(field).compareToIgnoreCase(value) != 0){
								if (map.get(field).compareToIgnoreCase(value)
										/ Math.abs(map.get(field).compareToIgnoreCase(value)) != conditionvalue) {
									appendXML("temp", map);
									//System.out.println("here2"+map.get(field));
							
						}} 

						flag3 = 1;
					}
					break;
				}
			}

			xMLEventReader.close();
			xMLFile.delete();
			//File file = new File("Directory" + "\\" + "temp" + ".xml");
			file.renameTo(new File(filename));
			return counter;
		} catch (FileNotFoundException | XMLStreamException | FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return counter;
	}

	public void appendXML(String tablename, HashMap<String, String> newElement) {
		tableName1=tablename;
		String filename = newpath+"\\" + tablename;
Write Writer = new Write(tablename);
		ArrayList<String> keys = getnameofcoluoms(tablename);
		Writer.deleteLastLine();
		Writer.deleteLastLine();
		Writer.startNode("Element");
		for (int i = 0; i < keys.size(); i++) {
			Writer.startNode(keys.get(i));
			Writer.writeCharachter(newElement.get(keys.get(i)));
			Writer.endNode(keys.get(i));
		}
		Writer.endNode("Element");
		Writer.endNode("table");	
		}
	

	public HashMap<String, String> getColunmsinMap(String tablename) {

		ArrayList<String> keys = getnameofcoluoms(tablename);
		HashMap<String, String> colunms = new HashMap<String, String>();
		for (int i = 0; i < keys.size(); i++) {
			colunms.put(keys.get(i), null);
		}
		return colunms;

	}

	public boolean createTable(String tablename, ArrayList<String> names, ArrayList<String> types) {
		tableName1=tablename;
		Write Writer = new Write(tablename);
		ArrayList<String> filesArray;
		filesArray = new ArrayList<>();
		String s=newpath;
		File[] files = new File(s).listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".xml")) {
				filesArray.add(file.getName().substring(0, file.getName().length() - 4));
			}
		}
		if (filesArray.contains(tablename)) {
			return false;
		} else {
			try {
				String fullName = newpath+"\\"+ tablename;
				File file = new File(fullName + ".xml");
				file.createNewFile();

				Writer.Createtable(tablename,tablename,names, types);
				XSDWriter writer = new XSDWriter();
				writer.createXSDFile(tablename, names, types);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	}

	public boolean dropTable(String tablename) {

		try {
			tableName1=tablename;
			File file = new File(newpath +"\\"+ tablename + ".xml");
			File xsdfile = new File(newpath +"\\"+ tablename + ".xsd");
			file.delete();
			xsdfile.delete();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public ArrayList<String> getnameofcoluoms(String tablename) {
		
	String	s1=newpath+"\\" + tablename + ".xml";
		File file = new File(s1);
		System.out.println(newpath);
		FileReader namereader;
		System.out.println(s1);
		try {
			namereader = new FileReader(s1);
			BufferedReader in = new BufferedReader(namereader);
			String s = in.readLine();
			String[] names = s.split(" ");
			names = names[1].split(",");
			ArrayList<String> result = new ArrayList<String>();
			int j = 0;
			for (int i = 0; i < names.length; i += 2) {
				result.add(j, names[i]);
				System.out.println(names[i]);
				j++;
			}
			in.close();
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> gettypesofcoluoms(String tablename) {
		String s1=newpath+"\\" + tablename + ".xml";
		File file = new File(s1);
		FileReader namereader;
		try {
			namereader = new FileReader(s1);
			BufferedReader in = new BufferedReader(namereader);
			String s = in.readLine();
			String[] names = s.split(" ");
			names = names[1].split(",");
			ArrayList<String> result = new ArrayList<String>();
			int j = 0;
			for (int i = 1; i < names.length; i += 2) {
				result.add(j, names[i]);
				j++;
			}
			in.close();
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean validationByXSD(String xmlfilename,String xsdfilename) {
		try {
			File xsdfile = new File(newpath+"\\" + xsdfilename + ".xsd");
			File xmlfile = new File(newpath +"\\"+ xmlfilename + ".xml");
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(xsdfile);
			Validator validator = schema.newValidator();
			StreamSource source = new StreamSource(xmlfile);
			validator.validate(source);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean validateBeforeinsert(String tableName, HashMap<String, String> newElement){
		Write Writer = new Write("temp");
		File file = new File(newpath+"\\"+ "temp" + ".xml");
		System.out.println();
		try {
			file.createNewFile();
			String filename = newpath + "temp";
		ArrayList<String> names=getnameofcoluoms(tableName);
		ArrayList<String> types=gettypesofcoluoms(tableName);
		for(int i=0;i<names.size();i++){
			System.out.println(names.get(i));
			System.out.println(types.get(i));
		}
		 Writer.Createtable("temp",tableName,names, types);
		appendXML("temp", newElement);
		if(validationByXSD("temp",tableName)){
			file.delete();
			return true;
		}
		
		} catch (Exception e) {
				e.printStackTrace();
		return false;	
		}
		return false;
		
		
		
	
	}
	public String getpass(){
		String s= null;
		s=newpath ;
		return s;
	}
	public void callMethods (String query) throws SQLException{
		String [] call = query.split("\\s");
		if (call[0].equalsIgnoreCase("CREATE")||call[0].equalsIgnoreCase("DROP")){
			
			executeStructureQuery(query);
		}
		else if (call[0].equalsIgnoreCase("SELECT")){
			executeRetrievalQuery(query);
		}
		else if (call[0].equalsIgnoreCase("DELETE")||call[0].equalsIgnoreCase("INSERT")){
			executeUpdateQuery(query);
		}
		else {}
	}

	
	  
	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		String[] words=query.split("\\s");
		String tableName = null;
	
		if (words[0].equalsIgnoreCase("CREATE")){//create table
				DBMS dbms=new DBMS();
			Pattern pattern_regex = Pattern.compile(REGEX_CT,Pattern.CASE_INSENSITIVE);
			Matcher match_regex = pattern_regex.matcher(query);
			System.out.println(match_regex.matches());
			if (match_regex.matches())
			{
				 tableName = match_regex.group(6);
				
			}
			 tableName1=tableName;
				 pattern_regex = Pattern.compile("(\\s*)([A-Za-z0-9._%]+)(\\s+)(varchar|int)(\\s*)(\\,|)",Pattern.CASE_INSENSITIVE);
				 match_regex = pattern_regex.matcher(query);

				ArrayList<String> colname = new ArrayList<String>();
				
				ArrayList<String> coltype = new ArrayList<String>();
				int i = 0;
		        while (match_regex.find()) {
		            colname.add(match_regex.group(2));
		            coltype.add(match_regex.group(4));
		            i++;
		        }
		        System.out.println(tableName);
		       if (dbms.createTable(tableName,colname,coltype))
		       {return true;}
		       else if (!dbms.createTable(tableName,colname,coltype))
		       {return false;}
		        return false;
			 }
			
		else if (words[0].equalsIgnoreCase("DROP")){
			DBMS dbms= new DBMS();
				 Pattern pattern_regex = Pattern.compile(REGEX_DT,Pattern.CASE_INSENSITIVE);
					Matcher match_regex = pattern_regex.matcher(query);
					if (match_regex.matches()){
						 tableName = match_regex.group(6);
						
					}tableName1=tableName;
					if (dbms.dropTable(tableName)){return true;}
				       else if (!dbms.dropTable(tableName)){return false;}
					return false;
			 }
		return false;
	}

	@Override
	public Object[][] executeRetrievalQuery(String query) throws SQLException {
		DBMS dbms= new DBMS();
		Pattern pattern_regex = Pattern.compile(REGEX_SF,Pattern.CASE_INSENSITIVE);
		Matcher match_regex = pattern_regex.matcher(query);
		String tableName = null;
		if (match_regex.matches())
		{
			 tableName = match_regex.group(13);
			System.out.println("the table name is: " + tableName);
		}
		tableName1=tableName;
		
		String colexp = match_regex.group(4);
		 
            
     
            String [] colnames = colexp.split("(,)");
            
            ArrayList<String> colnameslist = new ArrayList<String>(); //columns in array list
           
            for (int i = 0; i < colnames.length; i++) {
                colnameslist.add(colnames[i]);
                System.out.println(colnames[i]);
            }
            
            pattern_regex = Pattern.compile("(\\s*)([A-Za-z0-9_.%]+)(\\s*)(<|>|=)(\\s*)([A-Za-z0-9_.%]+)(\\s*)",Pattern.CASE_INSENSITIVE);
			 match_regex = pattern_regex.matcher(query);//nafs tare2et el insert bzzbt bs msh han7ot values
			 char c;
			 Object[][] array = null;
			 if (match_regex.find()){ // save the condition 
				 c=match_regex.group(4).charAt(0); 
			array= dbms.query(tableName,match_regex.group(2),match_regex.group(6), c);
			 }
		return array;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		String[] words=query.split("\\s");
		if (words[0].equalsIgnoreCase("INSERT")){//insert to the table 
			DBMS dbms= new DBMS();
			
			String tableName = null ;
			String s=newpath+"\\"+ "temp" + ".xml";
			System.out.println(s);
			File file =new File(s);
			if(file.exists()){
			file.delete();	
			}
			 Pattern pattern_regex = Pattern.compile(REGEX_II,Pattern.CASE_INSENSITIVE);
			 Matcher match_regex = pattern_regex.matcher(query);
			 if (match_regex.matches())
				{
					 tableName = match_regex.group(6);
				}
			 tableName1=tableName;
			 String colexp = match_regex.group(9);
			 System.out.println(colexp);
               String valueexp = match_regex.group(22);
        
               String [] colnames = colexp.split("(,)");
               String [] values = valueexp.split("(,)");
               ArrayList<String> colnameslist = new ArrayList<String>(); //columns in array list
               ArrayList<String> valueslist = new ArrayList<String>();  //values in array list
               HashMap<String,String> map=new HashMap<String,String>();
               for (int i = 0; i < colnames.length; i++) {
                   colnameslist.add(colnames[i]);
                   valueslist.add(values[i]);
                   map.put(colnames[i],values[i]);
               }
             if( dbms.validateBeforeinsert(tableName, map)){
            	 System.out.println("valid");
            	 dbms.appendXML(tableName, map);
             }   
             else {
            	 System.out.println("invalid");
            	 
             }
             
			  }
		
		 else if (words[0].equalsIgnoreCase("DELETE")){//delete from table 
				DBMS dbms= new DBMS();
	 			Pattern pattern_regex = Pattern.compile(REGEX_DFT,Pattern.CASE_INSENSITIVE);
				Matcher match_regex = pattern_regex.matcher(query);
				String tableName = null;
				if (match_regex.matches())
				{
					 tableName = match_regex.group(6);
			
				}
				tableName1=tableName;
				 pattern_regex = Pattern.compile("(\\s*)([A-Za-z0-9_.%]+)(\\s*)(<|>|=)(\\s*)([A-Za-z0-9_.%]+)(\\s*)",Pattern.CASE_INSENSITIVE);
				 match_regex = pattern_regex.matcher(query);
				 
				 if (match_regex.find()){
				 ArrayList<String> condition = new ArrayList<String>();
				 condition.add(match_regex.group(2));
				 condition.add(match_regex.group(4));
				 condition.add(match_regex.group(6));
				 char c=condition.get(1).charAt(0);
				 System.out.println(c);
				 int count=0;
				 count=dbms.delete(tableName, condition.get(0), condition.get(2),condition.get(1).charAt(0));
				 System.out.println("items deleted ="+count);
				 }
	       }
		
		return 0;
	}
public String gettablename(){
	return tableName1;
}
}
