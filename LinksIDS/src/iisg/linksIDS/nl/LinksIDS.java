package iisg.linksIDS.nl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.ResultSetMetaData;
//import com.mysql.jdbc.Statement;

public class LinksIDS{

	/**
	 * @param args
	 */

	private static Connection connection = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	//private static LinkedBlockingQueue<String>                   qe = new LinkedBlockingQueue<String>(1024);
	private static LinkedBlockingQueue<ArrayList<Person>>                   qe = new LinkedBlockingQueue<ArrayList<Person>>(1024);
	private static LinkedBlockingQueue<ArrayList<String>>                   qe2 = new LinkedBlockingQueue<ArrayList<String>>(1024);
	private static String insertStatement             = "";
	private static ArrayList<Thread> threads          = new ArrayList<Thread>();
	private static int numberOfThreads                = 12;
	private static Integer personsWritten             = 0;
	public static int phase                           = 0;
	
	//private static int highest_ID_Person = 0;
	//private static int highest_processed_ID_Person = 0;
	//private static int highest_processed_ID_Registration = 0;
	

	
	
	static HashMap<Pair, Integer>                      relations = null;
	static HashMap<Integer, Integer>                   locations = null;
	static HashSet<Integer>[]                          relationsX = null; 
	static HashMap<Integer, Integer>                   locNo2Id_C = new HashMap<Integer, Integer>();
	
	private static String sp01                         = "%s";                
	private static String sp02                         = sp01 + sp01;             
	private static String sp03                         = sp02 + sp02;             
	private static String sp04                         = sp03 + sp03;             
	private static String sp05                         = sp04 + sp04;             
	private static String sp06                         = sp05 + sp05;             
	private static String sp07                         = sp06 + sp06;             
	private static String sp08                         = sp07 + sp07;             
	private static String sp09                         = sp08 + sp08;             
	private static String sp10                         = sp09 + sp09;  
	private static String sp11                         = sp10 + sp10;  
	private static String sp12                         = sp11 + sp11; // 2048  
	private static String sp13                         = sp12 + sp12; // 4096  
	private static String sp14                         = sp13 + sp13; // 8192  
	private static String sp15                         = sp14 + sp14; //16384

	private static ArrayList<String>  iList = new ArrayList<String>(); 
	private static ArrayList<String> iiList = new ArrayList<String>(); 
	private static ArrayList<String>  cList = new ArrayList<String>(); 
	private static ArrayList<String> ccList = new ArrayList<String>(); 
	private static ArrayList<String> icList = new ArrayList<String>(); 


	private static String userid;
	private static String passwd;
	private static String server;
	
	public static void main(String[] args) {

		int c = 0;
		int pn = 0;
		int pageSize = 1000 * 1000;

		int highest_ID_Person = 0;
		int highest_processed_ID_Person = 0;

		try{
			   
			System.out.println("Started");

			if(args.length > 2){
				server = args[0].trim();
				userid = args[1].trim();
				passwd = args[2].trim();
				//connection = Utils.connect("//194.171.4.70" + "links_ids?user=" + userid + "&password=" + passwd); //194.171.4.70 is the 154

				// Connect to Links_IDS
				//connection = Utils.connect(db + Constants.links_ids +"?user=" + userid + "&password=" + passwd); //194.171.4.70 is the 154
				connection = Utils.connect2(server, Constants.links_ids, userid,  passwd); //194.171.4.70 is the 154
				if(connection == null){
					System.out.println("Invalid User/password/server");
					System.exit(-1);
				}

			}


			
			// enable backslash escape
			
			//Utils.executeQ(connection, "SET SESSION sql_mode=''");
			
			

			Utils.createIDSTables(connection);
			populateContext();
			
			//System.exit(0);
			
			Contxt2.initializeContext(connection);
			
			Statement s = (Statement) connection.createStatement ();


			String [] args0 = {server, userid, passwd};
			//PersonNumber.personNumber(args0);		
			//if(1==1) System.exit(8);

			int previousPersonNumber = -1;

			//ArrayList<Person> persons  = new ArrayList<Person>();
			ArrayList<String []> persons = new ArrayList<String []>();
			HashMap<String, Integer> h = null;

			//String q = "SELECT * FROM links_match.personNumbers as N, links_cleaned.person_c as P where N.id_person = P.id_person order by person_number";

			// SELECT * from links_match.personNumbers as N,  links_cleaned.person_c as P, links_cleaned.registration_c as R where N.id_person = P.id_person and P.id_registration =  R.id_registration and (person_number > 0 and   person_number <=  100000) order by person_number


			highest_ID_Person = Utils.getHighestID_Person(connection); 
			highest_processed_ID_Person = Utils.getHighestProcessedID_Person(connection); // from previous run
			System.out.println("Processing Persons with id_person > " + highest_processed_ID_Person);
			outer: for(int a = (highest_processed_ID_Person/pageSize) * pageSize; a <= highest_ID_Person ; a += pageSize){
				
				//if(1==1) break outer;
				//if(1==1)continue;

				//String q = "SELECT * from links_ids.personNumbers as N,  links_cleaned.person_c   as P, links_cleaned.registration_c as R  " +
				//		" where N.id_person = P.id_person and P.id_registration =  R.id_registration " +
				//		" and (person_number > " + a + " and " +	"  person_number <=  " + (a + pageSize)  + ")" + 
				//		" order by person_number";

				String q = "SELECT   " +
						" N.person_number," +
						" P.firstname, " +
						" P.familyname, " +
						" P.prefix, " +
						" P.sex, " +
						
						" P.birth_day, " +
						" P.birth_month, " +
						" P.birth_year, " +
						" P.birth_day_min, " +
						" P.birth_month_min, " +
						" P.birth_year_min, " +
						" P.birth_day_max, " +
						" P.birth_month_max, " +
						" P.birth_year_max, " +
						" P.birth_location, " + 
						
						" P.death_day, " +
						" P.death_month, " +
						" P.death_year, " +
						" P.death_day_min, " +
						" P.death_month_min, " +
						" P.death_year_min, " +
						" P.death_day_max, " +
						" P.death_month_max, " +
						" P.death_year_max, " +
						" P.death_location, " +
						
						" P.role, " + 
						" P.stillbirth, " + 
						" P.occupation, " + 
						" P.religion, " + 
						" P.civil_status, " + 
						" P.mar_location, " + 
						
						" P.mar_day, " +
						" P.mar_month, " +
						" P.mar_year, " +
						" P.mar_day_min, " +
						" P.mar_month_min, " +
						" P.mar_year_min, " +
						" P.mar_day_max, " +
						" P.mar_month_max, " +
						" P.mar_year_max, " +

						
						" P.divorce_location, " + 
						" P.divorce_day, " + 
						" P.divorce_month, " + 
						" P.divorce_year, " + 
						
						" P.living_location, " + 
						" P.id_person_o, " + 
						" P.id_source, " + 
						" R.id_orig_registration, " +
						" R.id_source, " +
						" R.registration_day, " +
						" R.registration_month, " +
						" R.registration_year, " +
						" R.registration_maintype, " +
						" R.registration_type, " +
						" R.registration_seq, " +
						" R.registration_location_no" +
						
						" FROM" +
						" links_ids.personNumbers       as N,  " +
						" links_cleaned.person_c        as P, " +
						" links_cleaned.registration_c  as R " +
						
						" WHERE " +
						" N.id_person = P.id_person and" +
						" P.id_registration =  R.id_registration and " +
						" N.person_number >   " + a + " and " +	
						" N.person_number <=  " + (a + pageSize)  + " and " +  
						" N.person_number >  " + highest_processed_ID_Person  +  
						" ORDER BY N.person_number, R.registration_maintype, P.role";

				System.out.print("Scanning person_number range [" + a + ", " + (a + pageSize) + ")");
				//System.out.println(q);
				s.executeQuery(q);
				resultSet = s.getResultSet ();
				
				if(h == null){
					h = new HashMap<String, Integer>();
				
					ResultSetMetaData meta = (ResultSetMetaData) resultSet.getMetaData();
				
					for(int i = 1; i <= meta.getColumnCount(); i++){
						//System.out.println(meta.getColumnName(i));
						if(meta.getColumnName(i) != null)
							h.put(meta.getColumnName(i), i);
					}
				}
				
				int Id_C = 0;
				while (resultSet.next()){			
					//System.out.println("In loop");
					c++;
					//System.out.println("c = " + c);
					if(resultSet.getInt("person_number") != previousPersonNumber){
						
						pn++;
						
						if(persons.size()  != 0){
							
							//if(c % 10000 == 0)
							//	System.out.println(",  processed " + c + " person appearances");

							//if(c > 1000 * 1000){								
								//System.exit(8);
							//}
								
							writeIndividual(h, persons);	
							saveIndiv(connection, false); // save conditionally

							
						}

						persons.clear();				
						add(resultSet, h, persons);
						previousPersonNumber = resultSet.getInt("person_number");

					}
					else{
						add(resultSet, h, persons);

					}
				}
				//resultSet.close ();

				if(persons.size()  != 0){
					writeIndividual(h, persons);
					saveIndiv(connection, false); // save conditionally
					persons.clear();
				}
				System.out.println(", processed " + String.format("%,d", c)  + " person appearances, " + String.format("%,d", pn) + " person numbers");

			}
			//if(1==1) System.exit(0);
			//s.close ();
			//Utils.closeConnection(connection);	
			
		}


		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			Utils.closeConnection(connection);	
			System.exit(9);
		}		
		
		saveIndiv(connection, true); // save unconditionally

		//Contxt.save(connection);
		
		
		
		try {
			connection.commit();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Utils.closeConnection(connection);		
		
		//System.out.println("Program ended");
		//if(1==1) System.exit(0);
		
		relations = new HashMap<Pair, Integer>();		
		relationsX = new HashSet[highest_ID_Person + 1]; 
		
		String regType = null;
		String [] certType = {"", "Birth Cert", "Marriage Cert", "Death Cert"};

		

		ArrayList<Integer> personNumbers = new ArrayList<Integer>();
		ArrayList<Integer> roles         = new ArrayList<Integer>();
		ArrayList<String>  stillborns    = new ArrayList<String>();
		
		c = 0;
		int highest_processed_ID_Registration = Utils.getHighestProcessedID_Registration(connection);
		int highestID_Registration = Utils.getHighestID_Registration(connection);
		System.out.println("Processing Registrations");

		
		System.out.println("--highest_processed_ID_Registration = " + highest_processed_ID_Registration);
		System.out.println("--(highest_processed_ID_Registration / pageSize) = " + (highest_processed_ID_Registration / pageSize));
		System.out.println("--((highest_processed_ID_Registration / pageSize) * pageSize) = " + ((highest_processed_ID_Registration / pageSize) * pageSize));
		
		for(int a = ((highest_processed_ID_Registration / pageSize) * pageSize); a <= highestID_Registration ; a += pageSize){
			

			try{
				String q = "SELECT " +
						" R.*," +
						" N.person_number," +
						" P.role, " +
						" P.stillbirth, " +
						" P.living_location " +
						" FROM " +
						" links_ids.personNumbers        as N," +
						" links_cleaned.person_c         as P, " +
						" links_cleaned.registration_c   as R  " +
						" WHERE" +
						" R.id_source !=  10  and " +    // 10 = HSNRPxxx, the 'anchor'
						" R.id_registration       = P.id_registration and " +
						" P.id_person             = N.id_person and " +
						" R.id_registration >  " +  a + " and " +	
						" R.id_registration <= " + (a + pageSize)  + " and " + 
						" R.id_registration > " + highest_processed_ID_Registration + 
						" order by R.id_registration";

				//System.out.println(q);
				System.out.print("Scanning id_registration range (" + a + ", " + (a + pageSize) + "]");
				Statement s = (Statement) connection.createStatement ();
				s.executeQuery(q);
				resultSet = s.getResultSet ();

				// 1 Child 
				// 2 Mother
				// 3 Father
				// 4 Bride
				// 5 Mother Bride
				// 6 Father Bride
				// 7 Groom
				// 8 Mother Groom
				// 9 Father Groom 
				//10 Deceased
				//11 Partner
				//12 Witness
				
				String [] rls = {"", 
						"Child", 
						"Mother",
						"Father",
						"Bride",
						"Mother Bride",
						"Father Bride",
						"Groom",
						"Mother Groom", 						
						"Father Groom",  
						"Deceased",
						"Partner",
						"Witness"
				};

	
				
				
				int previousRegistrationNumber = -1;

				// Columns of registration_c

				int id_registration             = 0;
				int id_source 					= 0;
				int id_persist_source 			= 0;
				String id_persist_registration 	= null;
				int id_orig_registration 		= 0;
				int registration_maintype 		= 0;
				String registration_type 		= null;
				String extract 					= null;
				int registration_location_no 	= 0;
				int registration_spec 			= 0;
				String registration_church 		= null;
				int registration_day 			= 0;
				int registration_month 			= 0;
				int registration_year 			= 0;
				String registration_seq 		= null;
				String remarks 					= null;
				int person_number 				= 0;
				int role        				= 0;
				String stillborn   				= null;

				int Id_C = 0;

				while (resultSet.next()){
					
					if(resultSet.getInt("id_registration") != previousRegistrationNumber){
						
						
						//System.out.println("resultSet.getInt(id_registration =" + resultSet.getInt("id_registration"));

						saveRegistration(connection, false);

						if(personNumbers.size() > 0)
							writeIndivIndiv(personNumbers, roles, stillborns, registration_day, registration_month, registration_year, regType + " " + previousRegistrationNumber);

						// Add context for this source

						registration_maintype = resultSet.getInt("registration_maintype");
						regType = "" + registration_maintype;
						if(registration_maintype < certType.length)
							regType = certType[registration_maintype];

						//System.out.println("registration_location_no = " + resultSet.getInt("registration_location_no"));
						
						if(resultSet.getString("registration_location_no") != null){

							//System.out.println("locNo2Id_C = " +  locNo2Id_C.get(resultSet.getInt("registration_location_no")));

							if(locNo2Id_C.get(resultSet.getInt("registration_location_no")) != 0){

								Id_C = Contxt2.addCertificate(connection, cList, ccList, 
										regType,
										locNo2Id_C.get(resultSet.getInt("registration_location_no")), 
										resultSet.getInt("registration_year"), 
										resultSet.getInt("registration_month"), 
										resultSet.getInt("registration_day"), 
										resultSet.getString("registration_seq"),
										resultSet.getInt("id_registration") + (1 * 1000 * 1000));  // move them above the location context elements
							}
						}
						else
							Id_C = 0;

						// Add context elements for
						
						previousRegistrationNumber = resultSet.getInt("id_registration");

						// Save some registration_c columns

						registration_maintype 		=  resultSet.getInt("registration_maintype");
						registration_day 			=  resultSet.getInt("registration_day");
						registration_month 			=  resultSet.getInt("registration_month");
						registration_year 			=  resultSet.getInt("registration_year");
						registration_seq 			=  resultSet.getString("registration_seq");
						person_number 	     		=  resultSet.getInt("person_number");
						role        	     		=  resultSet.getInt("role");
						stillborn      	     		=  resultSet.getString("stillbirth");


						
						personNumbers.clear();
						roles.clear();
						stillborns.clear();


					}

					if (Id_C != 0){
						
						
						int roleIndex = resultSet.getInt("role");
						String rol = "" + roleIndex;
						if(roleIndex < rls.length)
							rol = rls[roleIndex];
						
						
						
						addIndivContext(connection, resultSet.getInt("person_number" ), Id_C,  regType, rol, "Event", "Exact", registration_day, registration_month, registration_year);
					}
					c++;
					//if(c % 10000 == 0)
						//System.out.println("---> processed " + c + " person appearances (2)");

					personNumbers.add(resultSet.getInt("person_number"));
					roles.add(resultSet.getInt("role"));
					stillborns.add(resultSet.getString("stillbirth"));
					
					
					
				}

				if(personNumbers.size() > 0){
					writeIndivIndiv(personNumbers, roles, stillborns, registration_day, registration_month, registration_year, regType  + "-" + previousRegistrationNumber);
					saveRegistration(connection, false);
					personNumbers.clear();
					roles.clear();
					stillborns.clear();
				}
				
								
				//  String.format("%,d", c)
				 
				System.out.println(", processed " + String.format("%,d", c) + " person appearances");

			}
			catch (Exception e) {
				System.out.println("In catch");
				System.out.println(e.getMessage());
				e.printStackTrace();
				Utils.closeConnection(connection);			
				System.exit(9);

			}		
		}

		saveRegistration(connection, true);

		//saveIndivContext(connection);
		//saveIndivIndiv(connection);
		//System.out.println("Saving Context...");
		//Contxt2.saveContext(connection, cList, ccList);
		System.out.println("Program has ended");

	}

	private static void writeIndividual(HashMap<String, Integer> h, ArrayList<String []> persons){

		//System.out.println("In write Individual");
		
		int birth_day       = 0;
		int birth_month     = 0;
		int birth_year      = 0;
		
		int birth_day_min   = 0;
		int birth_month_min = 0;
		int birth_year_min  = 0;
		int birth_day_max   = 0;
		int birth_month_max = 0;
		int birth_year_max  = 0;
		
		boolean birthDay = false;

		int death_day       = 0;
		int death_month     = 0;
		int death_year      = 0;
		
		int death_day_min   = 0;
		int death_month_min = 0;
		int death_year_min  = 0;
		int death_day_max   = 0;
		int death_month_max = 0;
		int death_year_max  = 0;
		
		boolean deathDay = false;

		int mar_day 		= 0;
		int mar_month 		= 0;
		int mar_year 		= 0;
		
		int mar_day_min		= 0;
		int mar_month_min 	= 0;
		int mar_year_min	= 0;
		int mar_day_max		= 0;
		int mar_month_max 	= 0;
		int mar_year_max 	= 0;
		
		int divorce_day = 0;
		int divorce_month = 0;
		int divorce_year = 0;
		
		boolean fn = false;
		boolean ln = false;
		boolean pf = false;
		boolean sx = false;
		boolean bl = false;
		boolean dl = false;
		
		int person_number = 0;
		
		int registration_maintype = 0;
		int registration_day = 0;
		int registration_month = 0;
		int registration_year = 0;
		String registration_seq = null;
		
		String regType = "Unknown";
		String [] certType = {"", "Birth Cert", "Marriage Cert", "Death Cert", "Divorce Cert"};


		String stillborn = null;
		
		int id_source = 0;
		int id_person_o = 0;

		for(int i = 0; i < persons.size(); i++){

			String columnName = "person_number";  // Remains the same, done for consistency
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) person_number = new Integer (persons.get(i)[h.get(columnName)]);

			columnName = "registration_maintype";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) registration_maintype = new Integer (persons.get(i)[h.get(columnName)]);
			
			if(registration_maintype < certType.length)
				regType = certType[registration_maintype];

			
			columnName = "registration_day";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) registration_day = new Integer (persons.get(i)[h.get(columnName)]);

			columnName = "registration_month";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) registration_month = new Integer (persons.get(i)[h.get(columnName)]);

			columnName = "registration_year";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) registration_year = new Integer (persons.get(i)[h.get(columnName)]);
			
			columnName = "registration_seq";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) registration_seq = new String (persons.get(i)[h.get(columnName)]);
			
			if(birthDay == false){

				columnName = "birth_day";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_day = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "birth_month";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_month = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "birth_year";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_year = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "birth_day_min";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_day_min = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "birth_month_min";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_month_min = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "birth_year_min";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_year_min = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "birth_day_max";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_day_max = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "birth_month_max";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_month_max = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "birth_year_max";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) birth_year_max = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "stillbirth";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) stillborn = new String (persons.get(i)[h.get(columnName)]);

				String birthdate = "BIRTH_DATE";
				if(stillborn != null && stillborn.trim().equals("1")) birthdate = "STILLBIRTH_DATE";

				if(birth_year != 0){					
					if(birth_year_max != 0){
						
						birth_day_min   = 0;
						birth_month_min = 0;
						birth_year_min  = 0;
						birth_day_max   = 0;
						birth_month_max = 0;
						birth_year_max  = 0;						
					}
					addIndiv(connection, person_number, regType, birthdate, "",  0, "Declared", "Exact", birth_day, birth_month, birth_year,
							birth_day_min, birth_month_min, birth_year_min,  birth_day_max, birth_month_max, birth_year_max);
														
					
					birthDay = true;
				}

				
			}
			
			if(bl == false){
				columnName = "birth_location";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null){
					Integer bll = new Integer(persons.get(i)[h.get(columnName)]);
					Integer Id_C = locNo2Id_C.get(bll);
					//System.out.println("birth_location = " + persons.get(i)[h.get(columnName)] + " Id_C = " + Id_C);
					if(Id_C != null && Id_C != 0){
						addIndiv(connection, person_number, "" + regType, "BIRTH_LOCATION",   "", Id_C, "Declared", "Exact", 
								birth_day, birth_month, birth_year,
								birth_day_min, birth_month_min, birth_year_min,  birth_day_max, birth_month_max, birth_year_max);

						bl = true;
					}

				}
			}

			if(fn == false){
				columnName = "firstname";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null && persons.get(i)[h.get(columnName)].trim().length() > 0){ 
					addIndiv(connection, person_number, regType, "FIRST_NAME",   persons.get(i)[h.get(columnName)].trim(), 0, "Declared", "Exact", birth_day, birth_month, birth_year,
							birth_day_min, birth_month_min, birth_year_min,  birth_day_max, birth_month_max, birth_year_max);
					fn = true;
				}
			}

			if(ln == false){
				columnName = "familyname";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null && persons.get(i)[h.get(columnName)].trim().length() > 0){ 
					addIndiv(connection, person_number, regType, "LAST_NAME",   persons.get(i)[h.get(columnName)].trim(), 0, "Declared", "Exact", birth_day, birth_month, birth_year,
							birth_day_min, birth_month_min, birth_year_min,  birth_day_max, birth_month_max, birth_year_max);
					ln = true;
				}
			}

			if(pf == false){
				columnName = "prefix";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null && persons.get(i)[h.get(columnName)].trim().length() > 0) {
					addIndiv(connection, person_number, regType, "PREFIX_LAST_NAME",   persons.get(i)[h.get(columnName)].trim(), 0, "Declared", "Exact", birth_day, birth_month, birth_year,
							birth_day_min, birth_month_min, birth_year_min,  birth_day_max, birth_month_max, birth_year_max);
					pf = true;

				}
			}
			
			if(sx == false){
				columnName = "sex";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null && persons.get(i)[h.get(columnName)].trim().length() > 0){ 
					addIndiv(connection, person_number, regType, "SEX",   persons.get(i)[h.get(columnName)].trim(), 0, "Declared", "Exact", birth_day, birth_month, birth_year,
							birth_day_min, birth_month_min, birth_year_min,  birth_day_max, birth_month_max, birth_year_max);
					sx = true;
				}
			}

			if(registration_maintype == 3){

				if(deathDay == false){

					columnName = "death_day";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_day = new Integer (persons.get(i)[h.get(columnName)]);

					columnName = "death_month";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_month = new Integer (persons.get(i)[h.get(columnName)]);

					columnName = "death_year";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_year = new Integer (persons.get(i)[h.get(columnName)]);

					columnName = "death_day_min";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_day_min = new Integer (persons.get(i)[h.get(columnName)]);

					columnName = "death_month_min";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_month_min = new Integer (persons.get(i)[h.get(columnName)]);

					columnName = "death_year_min";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_year_min = new Integer (persons.get(i)[h.get(columnName)]);

					columnName = "death_day_max";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_day_max = new Integer (persons.get(i)[h.get(columnName)]);

					columnName = "death_month_max";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_month_max = new Integer (persons.get(i)[h.get(columnName)]);

					columnName = "death_year_max";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) death_year_max = new Integer (persons.get(i)[h.get(columnName)]);

					if(death_year != 0){
						if(death_year_max != 0){						
							death_day_min   = 0;
							death_month_min = 0;
							death_year_min  = 0;
							death_day_max   = 0;
							death_month_max = 0;
							death_year_max  = 0;
						}

						addIndiv(connection, person_number, regType, "DEATH_DATE",   "", 0, "Declared", "Exact", death_day, death_month, death_year,
								death_day_min, death_month_min, death_year_min, death_day_max, death_month_max, death_year_max);

						deathDay = true;
					}


				}

				if(dl == false){
					columnName = "death_location";
					if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null){
						Integer pn = new Integer(persons.get(i)[h.get(columnName)]);
						Integer Id_C = locNo2Id_C.get(pn);
						if(Id_C != null && Id_C != 0){
							addIndiv(connection, person_number, regType, "DEATH_LOCATION",   "", Id_C, "Declared", "Exact", 
									death_day, death_month, death_year,
									death_day_min, death_month_min, death_year_min, death_day_max, death_month_max, death_year_max);

							dl = true;
						}

					}
				}

			}
			
			if(registration_maintype == 4){

				columnName = "divorce_day";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_day = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "divorce_month";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_month = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "divorce_year";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_year = new Integer (persons.get(i)[h.get(columnName)]);

				if(mar_year != 0)
					addIndiv(connection, person_number, regType, "DIVORCE_DATE",   "", 0, "Declared", "Exact", divorce_day, divorce_month, divorce_year,	0, 0, 0, 0, 0, 0);


				columnName = "divorce_location";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null){
					Integer pn = new Integer(persons.get(i)[h.get(columnName)]);
					Integer Id_C = locNo2Id_C.get(pn);
					if(Id_C != null && Id_C != 0)
						addIndiv(connection, person_number, regType, "DIVORCE_LOCATION",   "", Id_C, "Declared", "Exact", divorce_day, divorce_month, divorce_year,	0, 0, 0, 0, 0, 0);




				}
			}

			if(registration_maintype == 2){

				columnName = "mar_day";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_day = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "mar_month";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_month = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "mar_year";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_year = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "mar_day_min";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_day_min = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "mar_month_min";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_month_min = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "mar_year_min";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_year_min = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "mar_day_max";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_day_max = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "mar_month_max";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_month_max = new Integer (persons.get(i)[h.get(columnName)]);

				columnName = "mar_year_max";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) mar_year_max = new Integer (persons.get(i)[h.get(columnName)]);


				if(mar_year != 0){

					if(mar_year_max != 0){						
						mar_day_min   = 0;
						mar_month_min = 0;
						mar_year_min  = 0;
						mar_day_max   = 0;
						mar_month_max = 0;
						mar_year_max  = 0;
					}

					addIndiv(connection, person_number, regType, "MARRIAGE_DATE",   "", 0, "Declared", "Exact", mar_day, mar_month, mar_year,
							mar_day_min, mar_month_min, mar_year_min, mar_day_max, mar_month_max, mar_year_max);

				}

				columnName = "mar_location";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null){
					Integer pn = new Integer(persons.get(i)[h.get(columnName)]);
					Integer Id_C = locNo2Id_C.get(pn);
					if(Id_C != null && Id_C != 0)
						addIndiv(connection, person_number, regType, "MARRIAGE_LOCATION",   "", Id_C, "Declared", "Exact", mar_day, mar_month, mar_year,	
								mar_day_min, mar_month_min, mar_year_min, mar_day_max, mar_month_max, mar_year_max);




				}
			}

			columnName = "occupation";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null && persons.get(i)[h.get(columnName)].trim().length() > 0)
				addIndiv(connection, person_number, regType, "OCCUPATION_STANDARD",   persons.get(i)[h.get(columnName)].trim(), 0, "Declared", "Exact", registration_day, registration_month, registration_year,0,0,0,0,0,0);

			columnName = "religion";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null && persons.get(i)[h.get(columnName)].trim().length() > 0)
				addIndiv(connection, person_number, regType, "RELIGION_STANDARD",   persons.get(i)[h.get(columnName)].trim(), 0, "Declared", "Exact", registration_day, registration_month, registration_year,0,0,0,0,0,0);

			columnName = "civil_status";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null && persons.get(i)[h.get(columnName)].trim().length() > 0)
				addIndiv(connection, person_number, regType, "CIVIL_STATUS",   persons.get(i)[h.get(columnName)].trim(), 0, "Declared", "Exact", registration_day, registration_month, registration_year,0,0,0,0,0,0);


			columnName = "living_location";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null && persons.get(i)[h.get(columnName)].trim().length() > 0){
				Integer livingLocation = new Integer(persons.get(i)[h.get(columnName)]); 
				Integer Id_C_l = locNo2Id_C.get(livingLocation);	
				System.out.println("livingLocation = " + livingLocation + "Id_C_l = " +  Id_C_l);
				if(Id_C_l != null && Id_C_l.intValue() != 0)
					addIndivContext(connection, person_number, Id_C_l,  regType, "LIVING_LOCATION", "Event", "Exact", registration_day, registration_month, registration_year);
			}

			// Special processing for HSN Start

			columnName = " P.id_source";
			if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) id_source = new Integer (persons.get(i)[h.get(columnName)]);
			
			if(id_source == 10){
				
				columnName = " P.id_person_o";
				if(h.get(columnName) != null && persons.get(i)[h.get(columnName)] != null) id_person_o = new Integer (persons.get(i)[h.get(columnName)]);
				
				if(id_person_o > 0)
					addIndiv(connection, person_number, regType, "HSN_IDENTIFIER",   "" + id_person_o, 0, "Declared", "Exact", registration_day, registration_month, registration_year,0,0,0,0,0,0);
				
				
			}
			
			// Special processing for HSN End
			
			
		}
		
	}
	 
	 
	private static void add(ResultSet r, HashMap<String, Integer> h, ArrayList<String []> persons){

		
		String [] row = new String[h.size() + 1];
		for(int i = 1; i <= h.size(); i++)
			try {
				if(r.getObject(i) != null){
					row[i] = r.getObject(i).toString(); 
					if(row[i].length() > 0 && row[i].substring(row[i].length() -1).equalsIgnoreCase("\\"))  // Hack: remove ending \, gives sql error
						row[i] = row[i].substring(0, row[i].length() -1);
					row[i] = row[i].replaceAll("\"", "\\\\\"");
					//row[i] = row[i].replaceAll("\\", "\\\\");
					//System.out.println(row[i]);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(9);

			}
		persons.add(row);
	}
	
	public static void addIndiv(Connection connection, int Id_I, String source, String type, String value, int Id_C,
			String dateType, String estimation, int day, int month, int year, int min_day, int min_month, int min_year,int max_day, int max_month, int max_year){
		
		source = "LINKS " + source;
		
		String t = String.format("(\"%d\",\"%s\",\"%s\",\"%s\",\"%s\", \"%d\", \"%s\",\"%s\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\"),",  
				                    Id_I, "LINKS", source, type, value, Id_C, dateType, estimation, day, month, year, min_day, min_month, min_year, max_day, max_month,  max_year);
		
		
		iList.add(t);
		
		 //if(iList.size() > 5000)
			// saveIndiv(connection);

		
	}

	
	
	public static void addIndivIndiv(Connection connection, int Id_I_1, int Id_I_2, String source, String relation, 
			String dateType, String estimation, int day, int month, int year){
	
		String missing = "";
		if(year == 0)
			missing = "Time_invariant";

		source = "LINKS " + source;
		
		String t = String.format("(\"%d\",\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\"),",  
				                    Id_I_1,  Id_I_2, "LINKS", "" + source, relation, dateType, estimation, day, month, year, missing);
		
		
		iiList.add(t);
		 //if(iiList.size() > 5000)
			// saveIndivIndiv(connection);

		
	}
	
	public static void addIndivContext(Connection connection, int Id_I, int Id_C, String source, String relation, 
			String dateType, String estimation, int day, int month, int year){

		source = "LINKS " + source;

		
		String t = String.format("(\"%d\",\"%s\",\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%d\",\"%d\",\"%d\"),",  
				                    Id_I, "LINKS", Id_C, source, relation, dateType, estimation, day, month, year);
		
		
		icList.add(t);
		
		//System.out.println("addIndivContext, icList.size() = " + icList.size());
		
		 //if(icList.size() > 5000)
			// saveIndivContext(connection);
		
	}
	
	public static void addContext(Connection connection, int Id_C, String type, String value){
		
		//System.out.println("Add context, cList.size(); = "+ cList.size());
		
		String t = String.format("(\"%d\",\"%s\",\"%s\", \"%s\", \"%s\"),",  
				                    Id_C, "REF_LOCATION", type, value, "Time_invariant");
		
		//System.out.println(t);
		cList.add(t);
		
		 //if(cList.size() > 5000)
			// saveContext(connection);

		
	}
	
	public static void addContextContext(Connection connection, int Id_C_1, int Id_C_2, String relation){
		
		
		String t = String.format("(\"%d\",\"%d\",\"%s\",\"%s\", \"%s\"),",  
				                    Id_C_1, Id_C_2, "REF_LOCATION", relation, "Time_invariant");
		
		//System.out.println(t);
		ccList.add(t);
		
		 //if(ccList.size() > 5000)
			// saveContextContext(connection);

		
	}
	
	
	
	public static void saveIndiv(Connection connection, boolean regardless){
		
		
		if(iList.size() == 0 || (iList.size() < 5000 && regardless == false))
			return;
		
		
		//System.out.println("saveIndiv " + iList.size());
		//System.out.println("saveIndiv " + iList.get(0).substring(2,10) + " - " +  iList.get(iList.size() - 1).substring(2,10));
		
		
		String s = String.format(sp15.substring(0, 2 * iList.size()), iList.toArray());
		

		iList.clear();
		s = s.substring(0, s.length() -1);

		String u = "insert into links_ids.individual (Id_I, Id_D, Source, Type, Value, Id_C, date_type, estimation, day, month, year, Start_day, Start_month, Start_year, End_day, End_month, End_year) values" + s;
		//System.out.println(u.substring(0, 420));

		//String u = "insert into links_ids.context (Id_C, Id_D, Source, Type, Value, date_type, estimation, day, month, year) values" + s;
			
   		Utils.executeQ(connection, u);
   		

	}

	public static void saveRegistration(Connection connection, boolean regardless) {
		
		//System.out.println("saveRegistration, cList.size() = " + cList.size());
		
		if(cList.size() < 3000 && ccList.size() < 3000 && icList.size() < 3000 && iiList.size() < 3000 && regardless == false)
			return;
		
		try {
			connection.setSavepoint();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		Contxt2.saveContextContext(ccList, connection);
		Contxt2.saveContext(cList, connection);
		
		saveIndivContext(connection);
		saveIndivIndiv(connection);
		
		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void saveCont(Connection connection, boolean regardless) {
		
		//System.out.println("saveRegistration, cList.size() = " + cList.size());
		
		if(cList.size() == 0 || (cList.size() < 5000 && regardless == false))
			return;
		
		try {
			connection.setSavepoint();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		saveContextContext(connection);
		saveContext(connection);
		
		
		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void saveIndivIndiv(Connection connection){
		
		if(iiList.size() == 0)
			return;
		
		String s = String.format(sp15.substring(0, 2 * iiList.size()), iiList.toArray());
		iiList.clear();
		s = s.substring(0, s.length() -1);
		String u = "insert into links_ids.indiv_indiv (Id_I_1, Id_I_2, Id_D, Source, relation, date_type, estimation, day, month, year, Missing) values" + s;
			
   		Utils.executeQ(connection, u);
	}
	
	
	public static void saveIndivContext(Connection connection){
		
		if(icList.size() == 0)
			return;
		
		String s = String.format(sp15.substring(0, 2 * icList.size()), icList.toArray());
		icList.clear();
		s = s.substring(0, s.length() -1);
		String u = "insert into links_ids.indiv_context (Id_I, Id_D, Id_C, Source, relation, date_type, estimation, day, month, year) values" + s;
			
		//System.out.println(u.substring(0, 120));
   		Utils.executeQ(connection, u);
	}
	
	public static void saveContext(Connection connection){
		
		
		//System.out.println("cList.size() = " + cList.size());
		
		if(cList.size() == 0)
			return;
		
		String s = String.format(sp15.substring(0, 2 * cList.size()), cList.toArray());
		//System.out.println(s);
		cList.clear();
		s = s.substring(0, s.length() -1);
		String u = "insert into links_ids.context (Id_C, Id_D, type, value, Missing) values" + s;
			
		//System.out.println(u.substring(0, 30));
   		Utils.executeQ(connection, u);
	}
	
	public static void saveContextContext(Connection connection){
		
		if(ccList.size() == 0)
			return;
		
		String s = String.format(sp15.substring(0, 2 * ccList.size()), ccList.toArray());
		//System.out.println(s);
		ccList.clear();
		s = s.substring(0, s.length() -1);
		String u = "insert into links_ids.context_context (Id_C_1, Id_C_2, Id_D, relation, Missing) values" + s;
			
		//System.out.println(u.substring(0, 30));
   		Utils.executeQ(connection, u);
	}
	

		
	static void writeIndivIndiv(ArrayList<Integer> personNumbers, ArrayList<Integer> roles, ArrayList<String> stillborns,  
			int registration_day, int registration_month, int registration_year, String source){
		
		// 1 Child 
		// 2 Mother
		// 3 Father
		// 4 Bride
		// 5 Mother Bride
		// 6 Father Bride
		// 7 Groom
		// 8 Mother Groom
		// 9 Father Groom
		//10 Deceased
		//11 Partner
		//12 Witness
		
		// Add INDIV_INDIV
		
		//System.out.println("Number of kept relations: " + relations.size());
		
		int [] x = new int[13];
		
		boolean stillborn = false;
		
		int pn = 0;
		for(int i = 0; i < roles.size(); i++){
			
			switch(roles.get(i)){
			
			case 1:  
				x[1] = personNumbers.get(i);
				if(stillborns.get(i) != null && stillborns.get(i).trim().equals("1")) stillborn = true;
				break;
				
			case 2:  
				x[2] = personNumbers.get(i);
				break;
				
			case 3:  
				x[3] = personNumbers.get(i);
				break;
				
			case 4:  
				x[4] = personNumbers.get(i);
				break;
				
			case 5:  
				x[5] = personNumbers.get(i);
				break;
				
			case 6:  
				x[6] = personNumbers.get(i);
				break;
				
			case 7:  
				x[7] = personNumbers.get(i);
				break;
				
			case 8:  
				x[8] = personNumbers.get(i);
				break;
				
			case 9:  
				x[9] = personNumbers.get(i);
				break;
				
			case 10: 
				x[10] = personNumbers.get(i);
				break;
				
			case 11:  
				x[11] = personNumbers.get(i);
				break;
				
			case 12:  
				x[12] = personNumbers.get(i);
				break;
			
			}
		}

		
		if(x[1] != 0){
			//addIi(x[1], x[2], "Child and Mother", source);
			//addIi(x[1], x[3], "Child and Father", source);
			
			String rel = "Child";
			if(stillborn) rel = "Stillbirth-child";
			
			addIi(x[1], x[2], rel, source, 0, 0, 0);
			addIi(x[1], x[3], rel, source, 0, 0, 0);
		}

		if(x[2] != 0){
			//addIi(x[2], x[1], "Mother and Child", source);
			//addIi(x[2], x[10], "Mother and Child", source);
			addIi(x[2], x[1], "Mother", source, 0, 0, 0);
			addIi(x[2], x[10], "Mother", source, 0, 0, 0);
			
		}

		if(x[3] != 0){
			//addIi(x[3], x[1], "Father and Child", source);
			//addIi(x[3], x[10], "Father and Child", source);
			addIi(x[3], x[1], "Father", source, 0, 0, 0);
			addIi(x[3], x[10], "Father", source, 0, 0, 0);
			
		}

		if(x[4] != 0){
			//addIi(x[4], x[5], "Child and Mother", source);
			//addIi(x[4], x[6], "Child and Father", source);
			//addIi(x[4], x[7], "Bride and Groom", source);
			//addIi(x[4], x[8], "Daughter-in-law and Mother-in-law", source);
			//addIi(x[4], x[9], "Daughter-in-law and Father-in-law", source);
			addIi(x[4], x[5], "Child", source, 0, 0, 0);
			addIi(x[4], x[6], "Child", source, 0, 0, 0);
			addIi(x[4], x[7], "Bride", source, registration_year, registration_month, registration_day);
			addIi(x[4], x[8], "Daughter-in-law", source, registration_year, registration_month, registration_day);
			addIi(x[4], x[9], "Daughter-in-law", source, registration_year, registration_month, registration_day);
		}

		if(x[5] != 0){
			//addIi(x[5], x[4], "Mother and Child", source);
			//addIi(x[5], x[7], "Mother-in-law and Son-in-law", source);
			addIi(x[5], x[4], "Mother", source, 0, 0, 0);
			addIi(x[5], x[7], "Mother-in-law", source, registration_year, registration_month, registration_day);
		}

		if(x[6] != 0){
			//addIi(x[6], x[4], "Father and Child", source);
			//addIi(x[6], x[7], "Father-in-law and Son-in-law", source);
			addIi(x[6], x[4], "Father", source, 0, 0, 0);
			addIi(x[6], x[7], "Father-in-law", source, registration_year, registration_month, registration_day);
		}

		if(x[7] != 0){
			//addIi(x[7], x[8], "Child and Mother", source);
			//addIi(x[7], x[9], "Child and Father", source);
			//addIi(x[7], x[4], "Groom and Bride", source);
			//addIi(x[7], x[5], "Son-in-law and Mother-in-law", source);
			//addIi(x[7], x[6], "Son-in-law and Father-in-law", source);
			addIi(x[7], x[8], "Child", source, 0, 0, 0);
			addIi(x[7], x[9], "Child", source, 0, 0, 0);
			addIi(x[7], x[4], "Groom", source, registration_year, registration_month, registration_day);
			addIi(x[7], x[5], "Son-in-law", source, registration_year, registration_month, registration_day);
			addIi(x[7], x[6], "Son-in-law", source, registration_year, registration_month, registration_day);
		}

		if(x[8] != 0){
			//addIi(x[8], x[7], "Mother and Child", source);
			//addIi(x[8], x[4], "Mother-in-law and Daughter-in-law", source);
			addIi(x[8], x[7], "Mother", source, 0, 0, 0);
			addIi(x[8], x[4], "Mother-in-law", source, registration_year, registration_month, registration_day);
		}

		if(x[9] != 0){
			//addIi(x[9], x[7], "Father and Child", source);
			//addIi(x[9], x[4], "Father-in-law and Daughter-in-law", source);
			addIi(x[9], x[7], "Father", source, 0, 0, 0);
			addIi(x[9], x[4], "Father-in-law", source, registration_year, registration_month, registration_day);
		}

		if(x[10] != 0){
			//addIi(x[10], x[2], "Child and Mother", source);
			//addIi(x[10], x[3], "Child and Father", source);
			//addIi(x[10], x[11], "Spouse and spouse", source);
			addIi(x[10], x[2], "Child", source, 0, 0, 0);
			addIi(x[10], x[3], "Child", source, 0, 0, 0);
			addIi(x[10], x[11], "Spouse", source, registration_year, registration_month, registration_day);
		}

		if(x[11] != 0){
			//addIi(x[11], x[10], "Spouse and spouse", source);
			addIi(x[11], x[10], "Spouse", source, registration_year, registration_month, registration_day);
		}

		
		
	}
	
	static void addIi(int personNumber1, int personNumber2, String relation, String source, int year, int month, int day){
		
		
		
		
		
		if(personNumber1 == 0 || personNumber2 == 0) return;
		
		
		String[] s = {"Father-in-law", "Mother-in-law", "Son-in-law", "Daughter-in-law", "Bride", "Groom"};
		
		boolean nodub = true;
		for(String r: s)
			if(r.equalsIgnoreCase(relation)){
				addIndivIndiv(connection, personNumber1, personNumber2, source, relation, "Event", "Exact", day, month, year);
				return;
			}
		
		
		
		
		
		if(relationsX[personNumber1] == null)
			relationsX[personNumber1] = new HashSet<Integer>();
		
		if(!relationsX[personNumber1].contains(personNumber2)){
			addIndivIndiv(connection, personNumber1, personNumber2, source, relation, "Event", "Exact", day, month, year);
			relationsX[personNumber1].add(personNumber2);
		}

	}
	
	static void populateContext(){
		
		//System.out.println("Populate Context 1");
	
		// insert into links_ids.context (Id_C, Id_D, type, value) values("1","LINKS","NAME", "Afrika")
		
		
		try{
			//connection = Utils.connect2(server, Constants.links_ids, userid,  passwd);
			Statement s = (Statement) connection.createStatement ();
			s.executeQuery("select * from context");
			//s.executeQuery("select * from links_ids.personNumbers");
			resultSet = s.getResultSet ();	
			//System.out.println("c = " + resultSet.getInt("c"));
			
			//System.out.println("resultSet.next() = " + resultSet.next());
			
			
			
			//System.exit(8);
			//if(1==1) return;
			//int c = 0;
			boolean mustWrite = true;
			while(resultSet.next() == true){
				//System.out.println("c = " + resultSet.getInt("c"));

				//System.out.println("AAAA");
				//c = resultSet.getInt("Id_C");
				System.out.println("Table context is already populated");
				mustWrite = false;
				break;
				// We must still fill locNo2Id_C
				//return;  // there are elements in context table, so we do not populate
			}
			
			//if(c > 0) return;  // There are already entries in context, so it is not the first time, so we don't populate
			
			//System.out.println("Here");
			//Utils.executeQ(connection, "truncate links_ids.context"); // clear context_context
			//Utils.executeQ(connection, "truncate links_ids.context_context"); // clear context_context
			
			//connection_ref = Utils.connect("//194.171.4.30" + "links_ids?user=" + userid + "&password=" + passwd); //194.171.4.30 is the 030);  // this is on the reference server
			//connection_ref = Utils.connect2(Constants.reference_server, Constants.links_general, userid,  passwd); //194.171.4.30 is the 030);  // this is on the reference server
			Connection connection_ref = Utils.connect2(Constants.reference_server, Constants.links_general, "cmu", "cmucmu"); //194.171.4.30 is the 030);  // this is on the reference server

			Statement t = (Statement) connection_ref.createStatement ();
			
			t.executeQuery("select * from links_general.ref_location group by location_no order by " +
					"country, region, province,  municipality, location");
			resultSet = t.getResultSet ();		
			
			/*
			while (resultSet.next()){
				if(1==1) continue;
				
				System.out.println(resultSet.getString("original") + "   " + 
									resultSet.getInt("location_no") + "   " +
									resultSet.getString("country") + "   " +
								   resultSet.getString("region")  + "   " +
								   resultSet.getString("province")  + "   " +
								   resultSet.getString("municipality")  + "   " +
								   resultSet.getString("location"));
				
				System.out.println(String.format("%6d           %20s       %20s      %20s      %20s     %20s", 
						resultSet.getInt("location_no"),
						resultSet.getString("country"),
						resultSet.getString("region"),
						resultSet.getString("province"),
						resultSet.getString("municipality"), 
						resultSet.getString("location")
						));
			}
			*/
			
			
			String country      = "";
			String region       = "";
			String province     = "";
			String municipality = "";
			String locality     = "";
			
			int Id_C_CurrentCountry      = -1;
			int Id_C_CurrentRegion       = -1;
			int Id_C_CurrentProvince     = -1;
			int Id_C_CurrentMunicipality = -1;
			int Id_C_CurrentLocality     = -1;
			
			int Id_C = 0;
			String x = null;
			
			while (resultSet.next()){
				
				if(resultSet.getString("country") != null && !resultSet.getString("country").equals(country) &&
						!resultSet.getString("country").equals("Onbekend")){
					country = resultSet.getString("country");
					++Id_C;
					if(mustWrite){
						addContext(connection,   Id_C, "NAME", country);
						addContext(connection,   Id_C, "LEVEL", "Country");
					}
					Id_C_CurrentCountry = Id_C;
					Id_C_CurrentRegion       = -1;
					Id_C_CurrentProvince     = -1;
					Id_C_CurrentMunicipality = -1;
					Id_C_CurrentLocality     = -1;					
				}
				else
					if(resultSet.getString("country") == null || resultSet.getString("country") == "Onbekend") continue;
				
					
				if(resultSet.getString("region") != null && !resultSet.getString("region").equals(region)
						&& !resultSet.getString("region").equals("Onbekend")){
					region = resultSet.getString("region");
					++Id_C;
					if(mustWrite){
						addContext(connection,   Id_C, "NAME", region);
						addContext(connection,   Id_C, "LEVEL", "Region");
					}
					Id_C_CurrentRegion = Id_C;
					Id_C_CurrentProvince     = -1;
					Id_C_CurrentMunicipality = -1;
					Id_C_CurrentLocality     = -1;
					
					if(mustWrite)
						addContextContext(connection,   Id_C, Id_C_CurrentCountry, "Region and Country");
				}
				else
					if(resultSet.getString("region") == null || resultSet.getString("region") == "Onbekend"){
						region = "";
						Id_C_CurrentRegion = -1;
					}

				if(resultSet.getString("province") != null && !resultSet.getString("province").equals(province) && 
						!resultSet.getString("province").equals("Onbekend")){
					province = resultSet.getString("province");
					++Id_C;
					if(mustWrite){
						addContext(connection,   Id_C, "NAME", province);
						addContext(connection,   Id_C, "LEVEL", "Province");
					}
					
					Id_C_CurrentProvince = Id_C;
					Id_C_CurrentMunicipality = -1;
					Id_C_CurrentLocality     = -1;					
					
					int Id_C_Temp = Id_C_CurrentRegion;
					x = "Region";
					if(Id_C_Temp == -1){
						Id_C_Temp = Id_C_CurrentCountry;
						x = "Country";
					}
					
					if(mustWrite)
						addContextContext(connection,   Id_C, Id_C_Temp, "Province and " + x);
				}
				else
					if(resultSet.getString("province") == null || resultSet.getString("province") == "Onbekend"){
						province = "";
						Id_C_CurrentProvince = -1;
					}

				
				if(resultSet.getString("municipality") != null && !resultSet.getString("municipality").equals(municipality)
						&& !resultSet.getString("municipality").equals("Onbekend")){
					municipality = resultSet.getString("municipality");
					++Id_C;
					if(mustWrite){
						addContext(connection,   Id_C, "NAME", municipality);
						addContext(connection,   Id_C, "LEVEL", "Municipality");
					}
					
					Id_C_CurrentMunicipality = Id_C;
					Id_C_CurrentLocality     = -1;					
					
					int Id_C_Temp = Id_C_CurrentProvince;
					x = "Province";
					if(Id_C_Temp == -1){
						Id_C_Temp = Id_C_CurrentRegion;
						x = "Region";
					}
					if(Id_C_Temp == -1){
						Id_C_Temp = Id_C_CurrentCountry;
						x = "Country";
					}
					
					if(mustWrite)
						addContextContext(connection,   Id_C, Id_C_Temp, "Municipality and " + x);
				}
				else
					if(resultSet.getString("municipality") == null || resultSet.getString("municipality") == "Onbekend" ){
						municipality = "";
						Id_C_CurrentMunicipality = -1;
					}

				if(resultSet.getString("location") != null && !resultSet.getString("location").equals(locality)
						&& !resultSet.getString("location").equals("Onbekend")){
					locality = resultSet.getString("location");
					++Id_C;
					if(mustWrite){
						addContext(connection,   Id_C, "NAME", locality);
						addContext(connection,   Id_C, "LEVEL", "Locality");
					}
					
					Id_C_CurrentLocality = Id_C;
					
					int Id_C_Temp = Id_C_CurrentMunicipality;
					x = "Municipality";
					if(Id_C_Temp == -1){
					    Id_C_Temp = Id_C_CurrentProvince;
					    x = "Province";
					}
					if(Id_C_Temp == -1){
						Id_C_Temp = Id_C_CurrentRegion;
						x = "Region";
					}
					if(Id_C_Temp == -1){
						Id_C_Temp = Id_C_CurrentCountry;
						x = "Country";
					}
					if(mustWrite)
						addContextContext(connection,   Id_C, Id_C_Temp, "Locality and " + x);
				}
				else
					if(resultSet.getString("location") == null || resultSet.getString("location") == "Onbekend"){
						locality = "";
						Id_C_CurrentLocality = -1;
					}
				
				
				//int locNo = resultSet.getInt("location_no");
				//System.out.println("locNo2Id_C.put((resultSet.getInt(location_no)) = " + locNo + " Id C = " + Id_C); // To find it back later
				locNo2Id_C.put((resultSet.getInt("location_no")), Id_C); // To find it back later
				//System.out.println("Location " + resultSet.getInt("location_no") + " has Id_C " + Id_C);
			
				
				
				saveCont(connection, false);
			}
			saveCont(connection, true);

			
			if(connection_ref != null)
				Utils.closeConnection(connection_ref);
		}
		catch (Exception e) {
			System.out.println("In catch");
			System.out.println(e.getMessage());
			e.printStackTrace();
			Utils.closeConnection(connection);
			System.exit(9);

		}	
		
		/*
		try {
			connection.commit();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//System.out.println("Populate Context 2");

	}
	
	
}


   