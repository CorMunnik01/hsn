package iisg.linksIDS.nl;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;


//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class Utils {
	
	
	static ArrayList<INDIVIDUAL>           iL = new ArrayList<INDIVIDUAL>();
	static ArrayList<CONTEXT>             iCL = new ArrayList<CONTEXT>();
	static ArrayList<CONTEXT_CONTEXT>    iCCL = new ArrayList<CONTEXT_CONTEXT>();
	
	


	
	static int Id_C;
	static int Old_id_C;

	
	
	public static void addContext(Connection connection, CONTEXT context){
		
		iCL.add(context);
		if(iCL.size() >= 1000){
			writeCList(connection);
			iCL.clear();
		}
		
	}

	public static void addContextContext(Connection connection, CONTEXT_CONTEXT cc){
		
		iCCL.add(cc);
		if(iCCL.size() >= 1000){
			writeCCList(connection);
			iCCL.clear();
		}
		
	}

	/**
	 * 
	 * This routine gets the context element of a Municipality 
	 * It returns an array (length = 3) with either:
	 *   
	 *   Country - Province - Municipality or
	 *   Country - null     - Municipality 
	 * 
	 * 
	 * 
	 * @param ce
	 * @return
	 */

	public static String [] getLocationHierarchy(ContextElement ce){
		
		 String[] s = new String[3];
		 int j = 0;
		 while(ce != null){
			 for(int i = 0; i < ce.getTypes().size(); i++)
				 if(ce.getTypes().get(i).equals("NAME")){    				 
					 s[2-j++] = ce.getValues().get(i);
					 break;
				 }
			 ce = ce.getParent();
		 }
		 
		 if(s[0] == null){ // this means that there was only 1 level above ce (Country), not 2 (Country and Province), so we correct
			 s[0] = s[1];
			 s[1] = null;
		 }

		 return s;
		
	}

	private static void writeCList(Connection connection){
		
		String insertStatement =
				"insert into links_ids.context (Id_D, Id_C, Source, Type, Value, date_type, estimation, day, month, year) values(\"";
		
		for(CONTEXT c: iCL){
			
			// Front part
			
			insertStatement += c.getId_D();
			insertStatement += "\", \"";
			
			insertStatement += c.getId_C();
			insertStatement += "\", \"";
			
			insertStatement += c.getSource();
			insertStatement += "\", \"";
			
			insertStatement += c.getType();
			insertStatement += "\", \"";
			
			insertStatement += c.getValue();
			insertStatement += "\", \"";			
						
			
			// Timestamp part

			insertStatement += c.getDate_type();
			insertStatement += "\", \"";
			
			insertStatement += c.getEstimation();
			insertStatement += "\", \"";
			
			insertStatement += c.getDay();
			insertStatement += "\", \"";
			
			insertStatement += c.getMonth();
			insertStatement += "\", \"";
			
			insertStatement += c.getYear();
			insertStatement += "\"), (\"";
			
		}
		
		insertStatement = insertStatement.substring(0, insertStatement.length() - 4);
		
		//Utils.executeQ(connection, insertStatement);
		
		try {
			Statement s = (Statement) connection.createStatement ();
			//System.out.println(insertStatement.substring(0,250));
			System.out.println(" Inserted number of lines: " + s.executeUpdate(insertStatement));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Utils.closeConnection(connection);
			e.printStackTrace();
		}
		
	}
	
	private static void writeCCList(Connection connection){
		
		String insertStatement =
				"insert into links_ids.context_context (Id_D, Id_C_1, Id_C_2, Source, relation, date_type, estimation, day, month, year) values(\"";
		
		for(CONTEXT_CONTEXT cc: iCCL){
			
			// Front part
			
			insertStatement += cc.getId_D();
			insertStatement += "\", \"";
			
			insertStatement += cc.getId_C_1();
			insertStatement += "\", \"";
			
			insertStatement += cc.getId_C_2();
			insertStatement += "\", \"";
			
			insertStatement += cc.getSource();
			insertStatement += "\", \"";
			
			insertStatement += cc.getRelation();
			insertStatement += "\", \"";
			
			
			// Timestamp part

			insertStatement += cc.getDate_type();
			insertStatement += "\", \"";
			
			insertStatement += cc.getEstimation();
			insertStatement += "\", \"";
			
			insertStatement += cc.getDay();
			insertStatement += "\", \"";
			
			insertStatement += cc.getMonth();
			insertStatement += "\", \"";
			
			insertStatement += cc.getYear();
			insertStatement += "\"), (\"";
			
		}
		
		insertStatement = insertStatement.substring(0, insertStatement.length() - 4);
		
		try {
			Statement s = (Statement) connection.createStatement ();
			//System.out.println(insertStatement);
			s.execute(insertStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Utils.closeConnection(connection);
			e.printStackTrace();
		}
		
	}
	
	public static void createIDSTables(Connection connection){
		
		//System.out.println("In createIDSTables");
		
		try {
			Statement s = (Statement) connection.createStatement ();
			
			//s.execute("use links_ids");
			
			String createStatement = CreateIDSTables.INDIVIDUAL;
			s.execute(createStatement);
			//createStatement = CreateIDSTables.INDIVIDUAL_TRUNCATE;
			//s.execute(createStatement);
			
            createStatement = CreateIDSTables.INDIV_INDIV;
			s.execute(createStatement);
			//createStatement = CreateIDSTables.INDIV_INDIV_TRUNCATE;
			//s.execute(createStatement);
			
            createStatement = CreateIDSTables.INDIV_CONTEXT;
			s.execute(createStatement);
			//createStatement = CreateIDSTables.INDIV_CONTEXT_TRUNCATE;
			//s.execute(createStatement);
			
            createStatement = CreateIDSTables.CONTEXT;
			s.execute(createStatement);
			//createStatement = CreateIDSTables.CONTEXT_TRUNCATE;
			//s.execute(createStatement);
			
            createStatement = CreateIDSTables.CONTEXT_CONTEXT;
			s.execute(createStatement);
			//createStatement = CreateIDSTables.CONTEXT_CONTEXT_TRUNCATE;
			//s.execute(createStatement);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Utils.closeConnection(connection);
			e.printStackTrace();
		}
		

		
	}

	public static Connection connect2(String server, String dataBase, String userId, String passWd){
		
		//System.out.println("Database = " + dataBase);
		String connectionURL = "jdbc:mysql://localhost:3306/yourDBName";
		connectionURL = connectionURL.replace("localhost" , server);
		connectionURL = connectionURL.replace("yourDBName" , dataBase);
		
		//System.out.println("connectionURL = "+ connectionURL);
		
		Connection c = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = (Connection) DriverManager.getConnection(connectionURL, userId, passWd);
			c.setAutoCommit(false); 

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		
		}
		 catch (SQLException e) {
				e.printStackTrace();
				System.exit(-1);
		}
			
		return c;
	}

	public static Connection connect(String dataBase){
		
		//System.out.println("Database = " + dataBase);
		
		Connection c = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = (Connection) DriverManager.getConnection("jdbc:mysql:" + dataBase);
			c.setAutoCommit(false); 

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		
		}
		 catch (SQLException e) {
				e.printStackTrace();
				System.exit(-1);
		}
			
		return c;
	}
	
	public static void closeConnection(Connection connection){
		
		try {
			connection.close();
		}
		 catch (SQLException e) {
				e.printStackTrace();
				System.exit(-1);
		}
	}
	
	public static void commitConnection(Connection connection){
		
		try {
			connection.commit();
		}
		 catch (SQLException e) {
				closeConnection(connection);
				e.printStackTrace();
				System.exit(-1);
		}
	}	
	
	public static void executeQN(Connection connection, String s){	
		
		//System.out.println("executeQN: " + s);
		
		try {
			java.sql.Statement statement = connection.createStatement();
			connection.commit();

			statement.execute(s);
			//connection.commit();
		}  catch (SQLException e) {
			System.out.println(s);
			System.out.println("-->" + e.getErrorCode());
				e.printStackTrace();
				Utils.closeConnection(connection);
				System.exit(-1);
		}
	}

	public static void executeQ(Connection connection, String s){	

		//System.out.println("executeQ: " + s);

		try {
			java.sql.Statement statement = connection.createStatement();
			connection.commit();
			
			//System.out.println(s);

			statement.execute(s);
			//connection.commit();
		}  catch (SQLException e) {
			System.out.println(s);
			System.out.println("-->" + e.getErrorCode());
				e.printStackTrace();
				Utils.closeConnection(connection);
				System.exit(-1);
		}
		
	}

	public static void executeQI(Connection connection, String s){	

		//System.out.println("executeQI: " + s);

		try {
			java.sql.Statement statement = connection.createStatement();
			//System.out.println("+++-->" + s);

			statement.execute(s);
			//connection.commit();
		}  catch (SQLException e) {
			;
		}
	}


	
	public static int getOld_id_C() {
		return Old_id_C;
	}

	public static void setOld_id_C(int old_id_C) {
		Old_id_C = old_id_C;
	}
	
	public static int getId_C() {
		
		//int x = 1/0;
	
		return Id_C++;
	}

	public static void setId_C(int id_C) {
		Id_C = id_C;
	}
    static int getHighestID_Person(Connection connection){
		
		//System.out.println("Identifying highest id_person");
		ResultSet r = null;
		try {
			r = connection.createStatement().executeQuery("select max(id_person) as m FROM links_ids.personNumbers");
			while (r.next()) {
				System.out.println("Highest id_person = " + r.getInt("m"));
				return(r.getInt("m"));
			}
			r.close();
			//connection.createStatement().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		return 0;
		
	}

	static int getHighestProcessedID_Person(Connection connection){
		
		//System.out.println("Identifying highest id_person");
		ResultSet r = null;
		try {
			r = connection.createStatement().executeQuery("select max(id_i) as m FROM links_ids.individual");
			while (r.next()) {
				System.out.println("Highest Processed id_person = " + r.getInt("m"));
				return(r.getInt("m"));
			}
			r.close();
			//connection.createStatement().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		return 0;
		
	}

    static int getHighestID_Registration(Connection connection){
		
		//System.out.println("Identifying highest id_person");
		ResultSet r = null;
		try {
			r = connection.createStatement().executeQuery("select max(id_registration) as m FROM links_cleaned.registration_c");
			while (r.next()) {
				System.out.println("Highest id_registration = " + r.getInt("m"));
				return(r.getInt("m"));
			}
			r.close();
			//connection.createStatement().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		return 0;
		
	}


	
    static int getHighestProcessedID_Registration(Connection connection){
		
  		//System.out.println("Identifying highest id_person");
  		ResultSet r = null;
  		try {
  			r = connection.createStatement().executeQuery("select max(id_c) as m FROM links_ids.context");
  			while (r.next()) {
  				if(r.getInt("m") < 1 * 1000 * 1000){
  					System.out.println("Highest Processed id_registration = 0");
  					return(0);
  				}
  				else{
  					System.out.println("Highest Processed id_registration = " + (r.getInt("m") - (1 * 1000 * 1000)));
  					return(r.getInt("m") - (1 * 1000 * 1000));
  				}
  			}
  			r.close();
  			//connection.createStatement().close();
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			System.exit(0);
  		}
  		
  		return 0;
  		
  	}

    static int getHighestId_C(Connection connection){
		
  		//System.out.println("Identifying highest id_person");
  		ResultSet r = null;
  		try {
  			r = connection.createStatement().executeQuery("select max(id_c) as m FROM links_ids.context");
  			while (r.next()) {
  					System.out.println("Highest Processed id_registration = " + (r.getInt("m")));
  					return(r.getInt("m"));
  			}
  			r.close();
  			//connection.createStatement().close();
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			System.exit(0);
  		}
  		
  		return 0;
  		
  	}

}

