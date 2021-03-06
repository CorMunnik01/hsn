  package nl.iisg.ids06;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.iisg.hsncommon.Common1;
import nl.iisg.hsncommon.ConstRelations2;
import nl.iisg.idscontext.ContextElement;
import nl.iisg.idscontext.Contxt;

@Entity 
@Table(name="b2_st")
public class B2_ST {

	/**
	 * 
	 * This class handles the static attributes of a standardized person (name, date of birth etc.)
	 * Objects of this class are initialized from a Person object
	 *
	 */


	@Id @Column(name = "B1IDBG")  				private int       keyToSourceRegister;
	@Id @Column(name = "B2DIBG")  				private String    entryDateHead;                      
	@Id	@Column(name = "IDNR")    				private int       keyToRP;   
	@Id @Column(name = "B2RNBG")  				private int       keyToPersons;

	@Column(name = "B2FCBG")      				private int       natureOfPerson;

	@Column(name = "B2RDNR")   	  				private String    dateOfRegistration;
	@Column(name = "B2RDFG")   	  				private int       dateOfRegistrationFlag;

	@Column(name = "PERSON_ID")  				private int       personID;
	@Column(name = "PERSON_ID_FG")          	private int       personIDFlag;

	@Column(name = "PERSON_ID_FA")  		    private int       personID_FA;
	@Column(name = "PERSON_ID_FA_FG")          	private int       personID_FA_FG;

	@Column(name = "PERSON_ID_MO")  		    private int       personID_MO;
	@Column(name = "PERSON_ID_MO_FG")          	private int       personID_MO_FG;

	@Column(name = "START_DATE")             	private String    startDate;
	@Column(name = "START_FLAG")             	private int       startFlag;
	@Column(name = "START_EST")             	private int       startEst;

	@Column(name = "END_DATE")              	private String    endDate;
	@Column(name = "END_FLAG")              	private int       endFlag;
	@Column(name = "END_EST")               	private int       endEst;

	@Column(name = "FAMILYNAME")          		private String    familyName;
	@Column(name = "PREFIX_FAMILYNAME")   		private String    prefixLastName;
	@Column(name = "TITLE_NOBLE")         		private String    titleNoble; 
	@Column(name = "TITLE_ELSE")          		private String    titleElse; 
	@Column(name = "FAMILYNAME_FG")     		private int       familyNameInterpreted;

	@Column(name = "FIRSTNAME")  				private String    firstName;
	@Column(name = "FIRSTNAME_FG")	        	private int       firstNameFlag;
	@Column(name = "SEX")        				private String    sex;

	@Column(name = "B2GDNR")      				private String    dateOfBirth;
	@Column(name = "B2GDFG")      				private int       dateOfBirthFlag;
	@Column(name = "BIRTH_LOCALITY_ID")      	private int       placeOfBirthID;
	@Column(name = "BIRTH_LOCALITY_ST")      	private String    placeOfBirthStandardized;
	@Column(name = "BIRTH_LOCALITY_FG")      	private int       placeOfBirthFlag;

	@Column(name = "B2ODNR")     				private String    dateOfDecease;
	@Column(name = "B2ODFG")      				private int       dateOfDeceaseFlag;
	@Column(name = "DEATH_LOCALITY_ID")     	private int       placeOfDeceaseID;
	@Column(name = "DEATH_LOCALITY_ST")      	private String    placeOfDeceaseStandardized;
	@Column(name = "DEATH_LOCALITY_FG")      	private int       placeOfDeceaseFlag;

	@Column(name = "NATIONALITY")               private String    nationality;  

	@Column(name = "LEGAL_LIVPL_ID")     		private int       legalPlaceOfLivingID;
	@Column(name = "LEGAL_LIVPL_ST")      		private String    legalPlaceOfLivingStandardized;
	@Column(name = "LEGAL_LIVPL_FG")      		private int       legalPlaceOfLivingFlag;	
	@Column(name = "LEGAL_LIVPL_CODE")          private String    legalPlaceOfLivingCode;

	@Column(name = "REMARKS")    				private String    remarks; 
	@Column(name = "ADDITION")    				private String    addition;

	@Column(name = "VERSIE")      				private String    versionLastTimeOfDataEntry;
	@Column(name = "ONDRZKO")    				private String    researchCodeOriginal;
	@Column(name = "VERSIEO")     				private String    versionOriginalDataEntry;
	@Column(name = "DATUM")     				private String    date0;

	@Transient                                  private B4_ST     registration;
	
	@Transient                                  private ArrayList<B313_ST> relationsToPKHolder  = new ArrayList<B313_ST>();  
	@Transient                                  private ArrayList<B32_ST> civilStatus           = new ArrayList<B32_ST>();  
	@Transient                                  private ArrayList<B33_ST> religions             = new ArrayList<B33_ST>();  
	@Transient                                  private ArrayList<B34_ST> relations             = new ArrayList<B34_ST>();  
	@Transient                                  private ArrayList<B35_ST> professions           = new ArrayList<B35_ST>();  
	@Transient                                  private ArrayList<B36_ST> origins               = new ArrayList<B36_ST>();  
	@Transient                                  private ArrayList<B37_ST> destinations          = new ArrayList<B37_ST>();  
	@Transient                                  private ArrayList<B6_ST>  addressess            = new ArrayList<B6_ST>();  


	B2_ST(){} // No-arguments Constructor
	
	public void convert(EntityManager em){
		
		//System.out.println("Cr Enter B2, family name = " + getFamilyName() + ", firstname = " +  getFirstName() + ",  key to persons = " + getKeyToPersons());

		int day   = 0;
		int month = 0;
		int year  = 0;

		if(getDateOfBirth() != null){
			
		    day   = (new Integer(getDateOfBirth().substring(0,2))).intValue();
		    month = (new Integer(getDateOfBirth().substring(3,5))).intValue();
		    year  = (new Integer(getDateOfBirth().substring(6,10))).intValue();
		}
		
		
		
		if(getFamilyName() != null && getFamilyName().trim().length() > 0 && !getFamilyName().trim().equalsIgnoreCase("N"))
			Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "LAST_NAME", getFamilyName().trim(), "Reported", "Exact", 0, 0, 0);
		else
			return;
		if(getPrefixLastName() != null && getPrefixLastName().trim().length() > 0)
			Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "PREFIX_LAST_NAME", getPrefixLastName().trim(), "Reported", "Exact", 0, 0, 0);
		if(getFirstName() != null && getFirstName().trim().length() > 0)
			Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "FIRST_NAME", getFirstName().trim(), "Reported", "Exact", 0, 0, 0);
		if(getSex() != null && getSex().trim().length() > 0)
			Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "SEX", Utils.sex(getSex()), "Reported", "Exact", 0, 0, 0);
		else
			Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "SEX", "Unknown", "Reported", "Exact", 0, 0, 0);

		if(getKeyToRP() != 0)
			Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "HSN_IDENTIFIER", "" + getKeyToRP(), "Assigned", "Exact", 0, 0, 0);
		
		//System.out.println("Number of b313 " + getRelationsToPKHolder().size());
		
		
		// In the main registration (of the Wife) we set the relation to RP
		
		if(getEntryDateHead().equals("01-01-1940")){
			for(B313_ST b313: getRelationsToPKHolder()){

				//System.out.println("Relation to RP = " + b313.getContentOfDynamicData());

				switch(b313.getContentOfDynamicData()){

				case  1:  // RP

					Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "HSN_RESEARCH_PERSON", "HSN RP", "Assigned", "Exact", 0, 0, 0);
					break;


				case 11:  // Father

					Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "HSN_RESEARCH_PERSON", "Father RP", "Assigned", "Exact", 0, 0, 0);
					break;

				case 21:  // Mother

					Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "HSN_RESEARCH_PERSON", "Mother RP", "Assigned", "Exact", 0, 0, 0);
					break;

				default:  


					String other = null;
					//if(b313.getContentOfDynamicData() < ConstRelations2.b3kode1.length && ConstRelations2.b3kode1[b313.getContentOfDynamicData()] != null)
					//	other = ConstRelations2.b3kode1[b313.getContentOfDynamicData()] + " RP";
					if(b313.getContentOfDynamicData() != 0){

						String code = b313.getContentOfDynamicData() + ""; 
						other = Common1.standardizeRelation(code) ;
					}

					if(other != null){
						String person = other + " RP";
						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "HSN_RESEARCH_PERSON", person, "Assigned", "Exact",  0, 0, 0);
					}

				}

			}
		}

		if(year > 0)
			Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "BIRTH_DATE", null, "Reported", "Exact", day, month, year);	
		else{
			
			// Estimate birthdate based on birth date PK Holder
			
			B2_ST b2PK = getRegistration().getPersons().get(0);
			if(b2PK == this) return;

			if(b2PK.getDateOfBirth() != null && b2PK.getDateOfBirth().length() == 10){
				
				int birthyearPK = new Integer(b2PK.getDateOfBirth().substring(6, 10)).intValue();

				for(B313_ST b313: getRelationsToPKHolder()){

					switch(b313.getContentOfDynamicData()){

					case 2:  // Spouse
					case 145:  // Echtgenoot Man
					case 161:  // Partner

						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "BIRTH_DATE", null, "Reported", "Estimated [-50/50]", 1 , 1, birthyearPK -50, 1, 1, birthyearPK + 50, "Unavailable");
						break;

					case 3:  // Son
					case 4:  // Daughter
					case 8:  // Step Son
					case 9:  // Step Daughter
					case 133:  // Kind
					case 134:  // Step Daughter

						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "BIRTH_DATE", null, "Reported", "Estimated [15/100]", 1 , 1, birthyearPK +15, 1, 1, birthyearPK + 100, "Unavailable");
						break;

					case 11:  // Father
					case 61:  // Father-in-Law

						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "BIRTH_DATE", null, "Reported", "Estimated [-100/-15]", 1 , 1, birthyearPK -100, 1, 1, birthyearPK - 15, "Unavailable");
						break;
						
					case 71:  // Mother-in-Law

						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "BIRTH_DATE", null, "Reported", "Estimated [-50/-15]", 1 , 1, birthyearPK -50, 1, 1, birthyearPK - 15, "Unavailable");
						break;

						
					case 21:  // Mother

						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "BIRTH_DATE", null, "Reported", "Estimated [-50/-15]", 1 , 1, birthyearPK -50, 1, 1, birthyearPK - 15, "Unavailable");
						break;

					default:   

					}

				}
			}
		}
		
		if(getPlaceOfBirthID() > 0){
			ContextElement ce = Contxt.get2(getPlaceOfBirthStandardized());		
			if(ce != null){
				Utils.addIndivAndContext(null, null, null, null, ce, em, getKeyToRP(), getPersonID(), "B2_ST",  "BIRTH_LOCATION", "Reported", "Exact", day, month, year);
			}
		}
		
		//System.out.println("B2 1");
		
		if(getDateOfDecease() != null && !getDateOfDecease().equals("00-00-0000")){

			//System.out.println("B2 2");

			
			day   = (new Integer(getDateOfDecease().substring(0,2))).intValue();
			month = (new Integer(getDateOfDecease().substring(3,5))).intValue();
			year  = (new Integer(getDateOfDecease().substring(6,10))).intValue();
			
			if(Utils.dateIsValid(day, month, year) == 0)
				Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "DEATH_DATE", null, "Reported", "Exact", day, month, year);			

			//System.out.println("B2 3");

			
			if(getPlaceOfDeceaseID() > 0){
				ContextElement ce = Contxt.get2(getPlaceOfDeceaseStandardized());		
				if(ce != null){
					Utils.addIndivAndContext(null, null, null, null, ce, em, getKeyToRP(), getPersonID(), "B2_ST", "DEATH_LOCATION", "Reported", "Exact", day, month, year);
				}
			}
		}
		
		// Nationality, needs start and end date
		
		int startDay   = 0;
		int startMonth = 0;
		int startYear  = 0;
		
		int endDay   = 0;
		int endMonth = 0;
		int endYear  = 0;		

		if(getStartDate() != null){

			startDay   = (new Integer(getStartDate().substring(0,2))).intValue();
			startMonth = (new Integer(getStartDate().substring(3,5))).intValue();
			startYear  = (new Integer(getStartDate().substring(6,10))).intValue();
		}

		if(getEndDate() != null){

			endDay   = (new Integer(getEndDate().substring(0,2))).intValue();
			endMonth = (new Integer(getEndDate().substring(3,5))).intValue();
			endYear  = (new Integer(getEndDate().substring(6,10))).intValue();
		}



		if(getNationality() != null && getNationality().trim().length() > 0)
			Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "NATIONALITY", getNationality().trim(), "Declared", "Exact", startDay, startMonth, startYear, endDay, endMonth, endYear);

		
		// Start Observation for PK Holder, Spouses /* and (step) children if in scope */
		
		String startObservation = null;
		for(B313_ST b313: getRelationsToPKHolder()){
			
			switch(b313.getContentOfDynamicData()){
			case 1:  // Holder
				
				startObservation = "Start source";
				break;
				
			case 2: // Partner
			case 145: 
			case 161: 
				
				if(getStartFlag() == 21) startObservation = "Arrival";
				else                     startObservation = "Start source";
				break;
				
			case 3: // Children
			case 4:
			case 133:
			case 8: // Step Children
			case 9: 
				
				if(startYear == 0) break; // not under observation
				
				if(getStartFlag() == 21) startObservation = "Arrival";
				else
					if(getStartFlag() == 22) startObservation = "Birth";
				else                     startObservation = "Start source";	// 		
				
				break;
			
			default:
				
			}
		}
		
		if(startObservation != null && getStartDate() != null){


			startDay = new Integer(getStartDate().substring(0,2));
			startMonth = new Integer(getStartDate().substring(3,5));
			startYear = new Integer(getStartDate().substring(6,10));

			//System.out.println("START_OBSERVATION for IDNR = " +  getKeyToRP() + " id = " +  getPersonID());
			//Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "START_OBSERVATION", startObservation, "Assigned", "Exact", startDay, startMonth, startYear);


			// End Observation for PK Holder, Spouses and (step) children

			String endObservation = null;
			for(B313_ST b313: getRelationsToPKHolder()){

				switch(b313.getContentOfDynamicData()){
				case 1:  // Holder
					if(endYear == 0) break;  // not dead yet, but this is not possible because we get the PK only after the holder (=subject) dies.
					if(endFlag == 10) {

						endDay = new Integer(getEndDate().substring(0,2));
						endMonth = new Integer(getEndDate().substring(3,5));
						endYear = new Integer(getEndDate().substring(6,10));

						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "START_OBSERVATION", startObservation, "Assigned", "Exact", startDay, startMonth, startYear);

						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "END_OBSERVATION", "Death", "Assigned", "Exact", endDay, endMonth, endYear);
						
						Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "OBSERVATION", null, "Assigned", "Exact", startDay, startMonth, startYear, endDay, endMonth, endYear);

					}
					break;

				case 2: // Partner
				case 145: 
				case 161:

				case 3: // (Step) Children
				case 4: 
				case 8: 
				case 9: 
				case 133:
				case 134:

					if(endYear == 0) break;  // not under observation

					if(getEndFlag() == 40) endObservation = "Death";
					else if(endFlag == 41 || endFlag == 42) endObservation = "Departure";		

					if(endObservation != null){

						if(getEndDate() != null){

							endDay = new Integer(getEndDate().substring(0,2));
							endMonth = new Integer(getEndDate().substring(3,5));
							endYear = new Integer(getEndDate().substring(6,10));

							Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "START_OBSERVATION", startObservation, "Assigned", "Exact", startDay, startMonth, startYear);

							Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "END_OBSERVATION", endObservation, "Assigned", "Exact", endDay, endMonth, endYear);
							
							Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "OBSERVATION", null, "Assigned", "Exact", startDay, startMonth, startYear, endDay, endMonth, endYear);

						}
					}

					else{
						if(getRegistration().getEndDate() != null){

							endDay   = new Integer(getRegistration().getEndDate().substring(0, 2));
							endMonth = new Integer(getRegistration().getEndDate().substring(3, 5));
							endYear  = new Integer(getRegistration().getEndDate().substring(6, 10));

							Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "START_OBSERVATION", startObservation, "Assigned", "Exact", startDay, startMonth, startYear);
							
							Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "END_OBSERVATION", "End source", "Assigned", "Exact", endDay, endMonth, endYear);
							
							Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "OBSERVATION", null, "Assigned", "Exact", startDay, startMonth, startYear, endDay, endMonth, endYear);



						}
					}
					break;

				default:	
				}
			}
		}

		/*
		for(B313_ST b313: getRelationsToPKHolder()){
			if((b313.getContentOfDynamicData() == 1 || b313.getContentOfDynamicData() == 2 || b313.getContentOfDynamicData() == 145 || b313.getContentOfDynamicData() == 161)
					&& startYear > 0 && endYear > 0){
				Utils.addIndiv(em, getKeyToRP(), getPersonID(), "B2_ST", "OBSERVATION", null, "Assigned", "Exact", startDay, startMonth, startYear, endDay, endMonth, endYear);
				break;
			}
		}
		 */

		
		
		// See which addresses this person can be bound to
		// Check relation to RP, only spouses and children live with RP
		// For now, only RP
		// 28-02-2019 Also for others
		
		//int r = getRelationsToPKHolder().get(0).getContentOfDynamicData();
		//if(r == 1 || r == ConstRelations2.ECHTGENOTE_HOOFD || r == ConstRelations2.ECHTGENOOT_MAN_GEEN_HOOFD || r == ConstRelations2.ECHTGENOOT_MAN_GEEN_HOOFD)
		
		
		if(getStartDate() != null && getEndDate() != null){

			for(B6_ST b6: getRegistration().getAddresses()){ // via B4_ST to B6_ST

				if(b6.getStartDate() != null && b6.getEndDate() != null){

					String [] intersect = Common1.getIntersection(getStartDate(), getEndDate(), b6.getStartDate(), b6.getEndDate());
					
					//System.out.println("[" + getStartDate() + "," + getEndDate() + "] and [" + b6.getStartDate() + "," + b6.getEndDate() + "]");
					//if(intersect != null)
					//	System.out.println("[" + intersect[0]   + "," + intersect[1] + "]");
					//else
					//	System.out.println( "No intersection"); 
					
					
					int rel = getRelationsToPKHolder().get(0).getContentOfDynamicData();
					
					//System.out.println("rel = "+ rel);

					
					if((rel == 1  || 
					    rel ==  2 || rel == 145 ||  rel == 161 || 
				        rel ==  3 || rel == 4   ||  rel == 8   || rel == 9 || rel == 133 || rel == 134) && intersect != null){

						int startDay1   = (new Integer(intersect[0].substring(0,2))).intValue();
						int startMonth1 = (new Integer(intersect[0].substring(3,5))).intValue();
						int startYear1  = (new Integer(intersect[0].substring(6,10))).intValue();

						int endDay1   = (new Integer(intersect[1].substring(0,2))).intValue();
						int endMonth1 = (new Integer(intersect[1].substring(3,5))).intValue();
						int endYear1  = (new Integer(intersect[1].substring(6,10))).intValue();
                      

						ContextElement ce = null;
						if(b6.getMunicipality() != null && b6.getMunicipality().trim().length() > 0 ){
							ce = Contxt.get2(b6.getMunicipality().trim());
							if(ce != null){
								Utils.addIndivContextAndContext(b6.getBoat(), b6.getQuarter(), b6.getStreet(), b6.getNumber(), b6.getAddition(),
										ce, em, getKeyToRP(), getPersonID(), "B6_ST ",  "?LIVING_LOCATION?", "Reported", "Exact",  startDay1, startMonth1, startYear1, endDay1, endMonth1, endYear1);
							}
						}
					}
				}
			}
		}

		
		// down the tree
		
		

		for(B313_ST b313: getRelationsToPKHolder()){
			b313.convert(em);
		}

		for(B32_ST b32: getCivilStatus()){
			b32.convert(em);
		}

		for(B33_ST b33: getReligions())
			b33.convert(em);

		for(B34_ST b34: getRelations())
			b34.convert(em);

		for(B35_ST b35: getProfessions())
      		b35.convert(em);

		for(B37_ST b37: getDestinations())
      		b37.convert(em);

		//System.out.println("Exit B2");

		

	}
	
	public boolean contains(B3_ST pd){

		if(getKeyToSourceRegister()  == pd.getKeyToSourceRegister() &&
				getEntryDateHead().equals(pd.getEntryDateHead()) &&
				getKeyToRP()     == pd.getKeyToRP() &&
				getKeyToPersons() == pd.getKeyToRegistrationPersons())

			return true;
		else
			return false;


	}




	public int getKeyToSourceRegister() {
		return keyToSourceRegister;
	}


	public void setKeyToSourceRegister(int keyToSourceRegister) {
		this.keyToSourceRegister = keyToSourceRegister;
	}


	public String getEntryDateHead() {
		return entryDateHead;
	}


	public void setEntryDateHead(String entryDateHead) {
		this.entryDateHead = entryDateHead;
	}


	public int getKeyToRP() {
		return keyToRP;
	}


	public void setKeyToRP(int keyToRP) {
		this.keyToRP = keyToRP;
	}


	public int getKeyToPersons() {
		return keyToPersons;
	}


	public void setKeyToPersons(int keyToPersons) {
		this.keyToPersons = keyToPersons;
	}


	public String getDateOfRegistration() {
		return dateOfRegistration;
	}


	public void setDateOfRegistration(String dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}


	public int getDateOfRegistrationFlag() {
		return dateOfRegistrationFlag;
	}


	public void setDateOfRegistrationFlag(int dateOfRegistrationFlag) {
		this.dateOfRegistrationFlag = dateOfRegistrationFlag;
	}


	public int getPersonID() {
		return personID;
	}


	public void setPersonID(int personID) {
		this.personID = personID;
	}


	public int getPersonIDFlag() {
		return personIDFlag;
	}


	public void setPersonIDFlag(int personIDFlag) {
		this.personIDFlag = personIDFlag;
	}


	public int getPersonID_FA() {
		return personID_FA;
	}


	public void setPersonID_FA(int personID_FA) {
		this.personID_FA = personID_FA;
	}


	public int getPersonID_FA_FG() {
		return personID_FA_FG;
	}


	public void setPersonID_FA_FG(int personID_FA_FG) {
		this.personID_FA_FG = personID_FA_FG;
	}


	public int getPersonID_MO() {
		return personID_MO;
	}


	public void setPersonID_MO(int personID_MO) {
		this.personID_MO = personID_MO;
	}


	public int getPersonID_MO_FG() {
		return personID_MO_FG;
	}


	public void setPersonID_MO_FG(int personID_MO_FG) {
		this.personID_MO_FG = personID_MO_FG;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public int getStartFlag() {
		return startFlag;
	}


	public void setStartFlag(int startFlag) {
		this.startFlag = startFlag;
	}


	public int getStartEst() {
		return startEst;
	}


	public void setStartEst(int startEst) {
		this.startEst = startEst;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public int getEndFlag() {
		return endFlag;
	}


	public void setEndFlag(int endFlag) {
		this.endFlag = endFlag;
	}


	public int getEndEst() {
		return endEst;
	}


	public void setEndEst(int endEst) {
		this.endEst = endEst;
	}


	public String getFamilyName() {
		return familyName;
	}


	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}


	public String getPrefixLastName() {
		return prefixLastName;
	}


	public void setPrefixLastName(String prefixLastName) {
		this.prefixLastName = prefixLastName;
	}


	public String getTitleNoble() {
		return titleNoble;
	}


	public void setTitleNoble(String titleNoble) {
		this.titleNoble = titleNoble;
	}


	public String getTitleElse() {
		return titleElse;
	}


	public void setTitleElse(String titleElse) {
		this.titleElse = titleElse;
	}


	public int getFamilyNameInterpreted() {
		return familyNameInterpreted;
	}


	public void setFamilyNameInterpreted(int familyNameInterpreted) {
		this.familyNameInterpreted = familyNameInterpreted;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public int getFirstNameFlag() {
		return firstNameFlag;
	}


	public void setFirstNameFlag(int firstNameFlag) {
		this.firstNameFlag = firstNameFlag;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public int getDateOfBirthFlag() {
		return dateOfBirthFlag;
	}


	public void setDateOfBirthFlag(int dateOfBirthFlag) {
		this.dateOfBirthFlag = dateOfBirthFlag;
	}


	public int getPlaceOfBirthID() {
		return placeOfBirthID;
	}


	public void setPlaceOfBirthID(int placeOfBirthID) {
		this.placeOfBirthID = placeOfBirthID;
	}


	public String getPlaceOfBirthStandardized() {
		return placeOfBirthStandardized;
	}


	public void setPlaceOfBirthStandardized(String placeOfBirthStandardized) {
		this.placeOfBirthStandardized = placeOfBirthStandardized;
	}


	public int getPlaceOfBirthFlag() {
		return placeOfBirthFlag;
	}


	public void setPlaceOfBirthFlag(int placeOfBirthFlag) {
		this.placeOfBirthFlag = placeOfBirthFlag;
	}


	public String getDateOfDecease() {
		return dateOfDecease;
	}


	public void setDateOfDecease(String dateOfDecease) {
		this.dateOfDecease = dateOfDecease;
	}


	public int getDateOfDeceaseFlag() {
		return dateOfDeceaseFlag;
	}


	public void setDateOfDeceaseFlag(int dateOfDeceaseFlag) {
		this.dateOfDeceaseFlag = dateOfDeceaseFlag;
	}


	public int getPlaceOfDeceaseID() {
		return placeOfDeceaseID;
	}


	public void setPlaceOfDeceaseID(int placeOfDeceaseID) {
		this.placeOfDeceaseID = placeOfDeceaseID;
	}


	public String getPlaceOfDeceaseStandardized() {
		return placeOfDeceaseStandardized;
	}


	public void setPlaceOfDeceaseStandardized(String placeOfDeceaseStandardized) {
		this.placeOfDeceaseStandardized = placeOfDeceaseStandardized;
	}


	public int getPlaceOfDeceaseFlag() {
		return placeOfDeceaseFlag;
	}


	public void setPlaceOfDeceaseFlag(int placeOfDeceaseFlag) {
		this.placeOfDeceaseFlag = placeOfDeceaseFlag;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public int getLegalPlaceOfLivingID() {
		return legalPlaceOfLivingID;
	}


	public void setLegalPlaceOfLivingID(int legalPlaceOfLivingID) {
		this.legalPlaceOfLivingID = legalPlaceOfLivingID;
	}


	public String getLegalPlaceOfLivingStandardized() {
		return legalPlaceOfLivingStandardized;
	}


	public void setLegalPlaceOfLivingStandardized(
			String legalPlaceOfLivingStandardized) {
		this.legalPlaceOfLivingStandardized = legalPlaceOfLivingStandardized;
	}


	public int getLegalPlaceOfLivingFlag() {
		return legalPlaceOfLivingFlag;
	}


	public void setLegalPlaceOfLivingFlag(int legalPlaceOfLivingFlag) {
		this.legalPlaceOfLivingFlag = legalPlaceOfLivingFlag;
	}


	public String getLegalPlaceOfLivingCode() {
		return legalPlaceOfLivingCode;
	}


	public void setLegalPlaceOfLivingCode(String legalPlaceOfLivingCode) {
		this.legalPlaceOfLivingCode = legalPlaceOfLivingCode;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getAddition() {
		return addition;
	}


	public void setAddition(String addition) {
		this.addition = addition;
	}


	public String getVersionLastTimeOfDataEntry() {
		return versionLastTimeOfDataEntry;
	}


	public void setVersionLastTimeOfDataEntry(String versionLastTimeOfDataEntry) {
		this.versionLastTimeOfDataEntry = versionLastTimeOfDataEntry;
	}


	public String getResearchCodeOriginal() {
		return researchCodeOriginal;
	}


	public void setResearchCodeOriginal(String researchCodeOriginal) {
		this.researchCodeOriginal = researchCodeOriginal;
	}


	public String getVersionOriginalDataEntry() {
		return versionOriginalDataEntry;
	}


	public void setVersionOriginalDataEntry(String versionOriginalDataEntry) {
		this.versionOriginalDataEntry = versionOriginalDataEntry;
	}


	public String getDate0() {
		return date0;
	}


	public void setDate0(String date0) {
		this.date0 = date0;
	}


	public B4_ST getRegistration() {
		return registration;
	}


	public void setRegistration(B4_ST registration) {
		this.registration = registration;
	}


	public ArrayList<B313_ST> getRelationsToPKHolder() {
		return relationsToPKHolder;
	}


	public void setRelationsToPKHolder(ArrayList<B313_ST> relationsToPKHolder) {
		this.relationsToPKHolder = relationsToPKHolder;
	}


	public ArrayList<B32_ST> getCivilStatus() {
		return civilStatus;
	}


	public void setCivilStatus(ArrayList<B32_ST> civilStatus) {
		this.civilStatus = civilStatus;
	}


	public ArrayList<B33_ST> getReligions() {
		return religions;
	}


	public void setReligions(ArrayList<B33_ST> religions) {
		this.religions = religions;
	}


	public ArrayList<B35_ST> getProfessions() {
		return professions;
	}


	public void setProfessions(ArrayList<B35_ST> professions) {
		this.professions = professions;
	}


	public ArrayList<B36_ST> getOrigins() {
		return origins;
	}


	public void setOrigins(ArrayList<B36_ST> origins) {
		this.origins = origins;
	}


	public ArrayList<B37_ST> getDestinations() {
		return destinations;
	}


	public void setDestinations(ArrayList<B37_ST> destinations) {
		this.destinations = destinations;
	}


	public ArrayList<B6_ST> getAddressess() {
		return addressess;
	}


	public void setAddressess(ArrayList<B6_ST> addressess) {
		this.addressess = addressess;
	}

	public ArrayList<B34_ST> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<B34_ST> relations) {
		this.relations = relations;
	}

	public int getNatureOfPerson() {
		return natureOfPerson;
	}

	public void setNatureOfPerson(int natureOfPerson) {
		this.natureOfPerson = natureOfPerson;
	}


	
}
