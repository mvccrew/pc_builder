import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
	
	static Connection conn = null;
	static ResultSet result = null;
	static MyModel model = null;
	
	public static Connection getConnection() {
		
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static MyModel getAllModel(String tableName) {
		String sql = "select * from " + tableName;
		conn = getConnection();
		try {
			PreparedStatement state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}//end method
	
	public static MyModel getCompModel() {
		String sql = "SELECT C.ID, C.NAME, C.PRICE, C.TYPE, P.NAME, P1.NAME, P2.NAME, P3.NAME, P4.NAME\n" + 
				"FROM COMPUTERS C JOIN PARTS P\n" + 
				"ON C.CPUID = P.ID JOIN PARTS P1 ON C.GPUID = P1.ID \n" + 
				"JOIN PARTS P2 ON C.RAMID = P2.ID JOIN PARTS P3 ON C.HDDID = P3.ID\n" + 
				"JOIN PARTS P4 ON C.COOLINGID = P4.ID";
		conn = getConnection();
		try {
			PreparedStatement state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}//end method

	public static MyModel getEmpModel() {
		String sql = "SELECT E.ID, E.NAME, E.DEPARTMENT, C.NAME\n" +
				"FROM EMPLOYEES E JOIN COMPUTERS C\n" +
				"ON E.COMPUTER_ID = C.ID";
		conn = getConnection();
		try {
			PreparedStatement state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}//end method
	
	public static MyModel searchByDepModel(String dep, double price) {
		String sql = "SELECT E.NAME, C.PRICE\n" +
				"FROM EMPLOYEES E JOIN COMPUTERS C\n" + 
				"ON E.COMPUTER_ID = C.ID\n" + 
				"WHERE E.DEPARTMENT = '"+ dep + "' AND C.PRICE > " + price + ";";
		conn = getConnection();
		try {
			PreparedStatement state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}//end method

	public static MyModel searchByTypeModel(String partType, String compType) {
		String sql = "SELECT P.NAME, C.NAME\n" +
				"FROM PARTS P JOIN COMPUTERS C\n" +
				"ON P.ID = C."+partType+"ID\n" +
				"WHERE C.TYPE = '"+ compType + "'";
		conn = getConnection();
		try {
			PreparedStatement state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}//end method

	public static MyModel getFilteredByName(String name, String tableName) {
		String tempSQL = "%" + name + "%";
		String sql = "";
		switch (tableName){
			case "parts":
				sql = "SELECT NAME, PRICE, TYPE FROM PARTS\n" +
						"WHERE NAME LIKE ?;";
				break;
			case "computers":
				sql = "SELECT C.NAME, C.PRICE, C.TYPE, P.NAME, P1.NAME, P2.NAME, P3.NAME, P4.NAME\n" +
						"FROM COMPUTERS C JOIN PARTS P\n" +
						"ON C.CPUID = P.ID JOIN PARTS P1 ON C.GPUID = P1.ID\n" +
						"JOIN PARTS P2 ON C.RAMID = P2.ID JOIN PARTS P3 ON C.HDDID = P3.ID\n" +
						"JOIN PARTS P4 ON C.COOLINGID = P4.ID\n" +
						"WHERE C.NAME LIKE ?;";
				break;
			case "employees":
				sql = "SELECT E.NAME, E.DEPARTMENT, C.NAME\n" +
						"FROM EMPLOYEES E JOIN COMPUTERS C\n" +
						"ON E.COMPUTER_ID = C.ID\n" +
						"WHERE E.NAME LIKE ?;";
				break;

		}
		conn = getConnection();
		try {
			PreparedStatement state = conn.prepareStatement(sql);
			if (!name.equalsIgnoreCase("")) {
				state.setString(1, tempSQL);
			}
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}


}
