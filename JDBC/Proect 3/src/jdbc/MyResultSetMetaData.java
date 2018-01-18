package jdbc;



import java.sql.SQLException;
import java.util.ArrayList;

import dBMS.DBMS;

public class MyResultSetMetaData implements java.sql.ResultSetMetaData {

	private static final Object Integer = null;
	private Object table[][];
	private boolean closeBoolean;
	private String tableName = null;

	public MyResultSetMetaData(Object[][] table, String tableName) {

		this.tableName = tableName;
		this.table = table;
		this.closeBoolean = false;
	}

	public void close() {

		closeBoolean = true;
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnCount() throws SQLException {

			if(!closeBoolean){
		if(table == null){
			return 0;
		}
		return table[0].length;
		}
		throw new SQLException();
	}
	
	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		if(!closeBoolean && table != null && table.length > 1){
			ArrayList <String> array= DBMS.getInstance().getnameofcoluoms(tableName);
			
			return array.get(column);
		}
		throw new SQLException();
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		if(!closeBoolean && table != null && table.length > 1){
			if(!closeBoolean && table != null && table.length > 1){
				ArrayList <String> array= DBMS.getInstance().getnameofcoluoms(tableName);
				
				return array.get(column);
			}
			//return (String)table[0][column-1];
		}
		throw new SQLException();
	}

	@Override
	public int getColumnType(int column) throws SQLException {
	/*	try {
			ArrayList <String> array=DBMS.getInstance().gettypesofcoluoms(tableName);
			 int element = (int) table[1][column-1];
			 return java.sql.Types.INTEGER;
		}catch(Exception e){
			return java.sql.Types.VARCHAR;
		}*/
		ArrayList <String> array=DBMS.getInstance().gettypesofcoluoms(tableName);
		if(array.get(column).equalsIgnoreCase("int")){
			return java.sql.Types.INTEGER;
		}
		else if(array.get(column).equalsIgnoreCase("string")){
			return java.sql.Types.VARCHAR;
		}
		return 0;
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScale(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName(int column) throws SQLException {
		
		return tableName;
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int isNullable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
