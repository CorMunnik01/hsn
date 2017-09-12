package nl.iisg.ids;

public class CreateIDSTables {
	
	 public static final String INDIVIDUAL = 
			 
			 "CREATE TABLE IF NOT EXISTS individual" +
			 "(" +
			 "Id  INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
			 "Id_D CHAR(50), " +
			 "Id_I INT, " +
			 "Source VARCHAR(50), " +
			 "Type VARCHAR (50), " +
			 "Value VARCHAR(100), " +
			 "Value_Id_C INT, " + 
			 
			 "Date_type VARCHAR(15), " +
			 "Estimation VARCHAR(50), " + 
			 "Day TINYINT,  " + 
			 "Month TINYINT,  " + 
			 "Year SMALLINT,  " +
			 "Hour TINYINT,  " + 
			 "Minute TINYINT,  " + 
			 "Start_day TINYINT,  " + 
			 "Start_month TINYINT,  " + 
			 "Start_year SMALLINT,  " + 
			 "End_day TINYINT,  " + 
			 "End_month TINYINT,  " + 
			 "End_year SMALLINT,  " + 
	         "Missing VARCHAR(50)" +
			 " ); ";

	 public static final String INDIVIDUAL_TRUNCATE = "TRUNCATE TABLE individual;"; 

	 
	 public static final String INDIV_INDIV = 
		 
		 "CREATE TABLE IF NOT EXISTS indiv_indiv" +
		 "(" +
		 "Id  INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
		 "Id_D CHAR(50), " +
		 "Id_I_1 INT, " +
		 "Id_I_2 INT, " +
		 "Source VARCHAR(50), " +
		 "Relation VARCHAR(100), " +
		 
		 "Date_type VARCHAR(15), " +
		 "Estimation VARCHAR(50), " + 
		 "Day TINYINT,  " + 
		 "Month TINYINT,  " + 
		 "Year SMALLINT,  " +
		 "Hour TINYINT,  " + 
		 "Minute TINYINT,  " + 
		 "Start_day TINYINT,  " + 
		 "Start_month TINYINT,  " + 
		 "Start_year SMALLINT,  " + 
		 "End_day TINYINT,  " + 
		 "End_month TINYINT,  " + 
		 "End_year SMALLINT,  " + 
	     "Missing VARCHAR(50)" +
		 " ); ";


	 public static final String INDIV_INDIV_TRUNCATE = "TRUNCATE TABLE indiv_indiv;"; 

	 

	 public static final String INDIV_CONTEXT =  
		 
			"CREATE TABLE IF NOT EXISTS indiv_context" + 
			"(" + 
			"Id  INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +  
			"Id_D CHAR(50), " +  
			"Id_I INT, " + 		
			"Id_C INT, " + 		
			"Source VARCHAR(50), " +
			"Relation VARCHAR(100), " +

			"Date_type VARCHAR(15), " +
			"Estimation VARCHAR(50), " + 
			"Day TINYINT,  " + 
			"Month TINYINT,  " + 
			"Year SMALLINT,  " +
			"Hour TINYINT,  " + 
			"Minute TINYINT,  " + 
			"Start_day TINYINT,  " + 
			"Start_month TINYINT,  " + 
			"Start_year SMALLINT,  " + 
			"End_day TINYINT,  " + 
			"End_month TINYINT,  " + 
			"End_year SMALLINT,  " + 
			"Missing VARCHAR(50)" +
			" );"; 

	 public static final String INDIV_CONTEXT_TRUNCATE =  "TRUNCATE TABLE indiv_context;"; 

	 
	 
	}

