/*
 * Naam:    PersonStandardized
 * Version: 0.1
 *  Author: Cor Munnik
 * Copyright
 */



package nl.iisg.ids03;

import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.iisg.hsncommon.Common1;
import nl.iisg.hsncommon.ConstRelations2;
import nl.iisg.ref.*;


/**
 * 
 * This class handles the static attributes of a standardized person (name, date of birth etc.)
 * Objects of this class are initialized from a Person object
 *
 */

@Entity
@Table(name="b2_st")
public class PersonStandardized {	

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

	@Transient                                  private Person    originalPerson;
	@Transient  								private	ArrayList<PersonDynamicStandardized> dynamicDataOfPersonStandardized = new ArrayList<PersonDynamicStandardized>();
	@Transient  								private	RegistrationStandardized             registrationStandardizedPersonAppearsIn;
	@Transient                                  private String maxStartDate;
	@Transient                                  private String minStartDate;
	@Transient  								private	ArrayList<PersonDynamicStandardized> toAll = new ArrayList<PersonDynamicStandardized>();


	PersonStandardized(){}



	public void transform(Person p){

		setKeyToSourceRegister(p.getKeyToSourceRegister());
		transformHeadOfHouseholdDate(p);
		setKeyToRP(p.getKeyToRP());
		setKeyToPersons(p.getKeyToRegistrationPersons());

		setNatureOfPerson(p.getNatureOfPerson());

		transformRegistrationDate(p);

		transformFamilyname(p);
		transformFirstname(p);
		setSex(p.getSex());		
		transformBirthDate(p);
		transformBirthplace(p);

		transformDeceaseDate(p);

		transformDeceaseplace(p);
		setNationality(p.getNationality());
		transformLegalplaceOfLiving(p);
		setRemarks(p.getRemarks());
		setAddition(p.getRemarks2());
		setVersionLastTimeOfDataEntry(p.getVersionLastTimeOfDataEntry());
		setResearchCodeOriginal(p.getResearchCodeOriginal());
		setVersionOriginalDataEntry(p.getVersionOriginalDataEntry());
		setDate0(p.getDate0());


	}

	private void transformHeadOfHouseholdDate(Person p){

		String temp = String.format("%02d-%02d-%04d", p.getDayEntryHead(), p.getMonthEntryHead(), p.getYearEntryHead());
		setEntryDateHead(temp);		
	}



	/**
	 * 
	 * This routine transforms the family name into the new format
	 * 
	 * The family name is in the format: Familyname, Prefix!title
	 * 
	 * @param p Person
	 */
	private void transformFamilyname(Person p){
		

		String name = p.getFamilyName().trim();
		String prefix = null;
		String titleNoble = null;
		String titleElse = null;
		String title = null;	    
		String remainder = null;

		if(name != null){
			if(name.split("%").length >1){
				name = name.split("%")[0].trim();
				setFamilyNameInterpreted(2);  	
			}
			else
				setFamilyNameInterpreted(1);	

			if(name.split(",").length >1){
				remainder = name.split(",")[1];
				name = name.split(",")[0];
				if(remainder.split("!").length > 1){
					prefix = remainder.split("!")[0];	    		
					title = remainder.split("!")[1];
				}
				else
					prefix = remainder;        
			}
			else{
				if(name.split("!").length >1){
					title = name.split("!")[1];    	
					name = name.split("!")[0];
				}	
			}
		}

		if(name != null){
			Ref_FamilyName f = Ref.getFamilyName(name);

			if(f != null  && f.getCode() != null && (f.getCode().equalsIgnoreCase("y") || f.getCode().equalsIgnoreCase("u")) && f.getName() != null && f.getName().length() > 0){
				name = f.getName();
			}
			else{
				//System.out.println("No usable name found, index = " + index);
				if(f == null){
					Ref_FamilyName f1 = new Ref_FamilyName();
					f1.setOriginal(name);
					f1.setCode("x");
					Ref.addFamilyName(f1);
				}	
			}
		}	    	    

		if(prefix != null){	    
			Ref_Prefix pr = Ref.getPrefix(prefix);    
			if(pr != null && pr.getCode() != null && (pr.getCode().equalsIgnoreCase("y") == true) && pr.getPrefix() != null && pr.getPrefix().length() > 0){
				prefix = pr.getPrefix();  	
			}  
			else{
				if(pr == null){
					Ref_Prefix rp1 = new Ref_Prefix();
					rp1.setOriginal(prefix);
					rp1.setCode("x");
					Ref.addPrefix(rp1);
				}
			}   
		} 

		if(title != null){
			Ref_Prefix pr = Ref.getPrefix(title);
			if(pr != null && pr.getCode() != null && (pr.getCode().equalsIgnoreCase("y")) == true){
				if(pr.getTitleNoble() != null && pr.getTitleNoble().length() > 0)
					titleNoble = pr.getTitleNoble();
				if(pr.getTitleOther() != null && pr.getTitleOther().length() > 0)
					titleElse = pr.getTitleOther(); 
			}
			else{
				if(pr == null){
					Ref_Prefix pr1 = new Ref_Prefix();
					pr1.setOriginal(title);
					pr1.setCode("x");
					Ref.addPrefix(pr1);
				}
			}   
		}

		setFamilyName(name);
		setPrefixLastName(prefix);
		setTitleNoble(titleNoble);
		setTitleElse(titleElse); 
	}

	/**
	 * 
	 * This routine transforms the first name
	 * 
	 * 
	 * @param p
	 */
	private void transformFirstname(Person p){

		String name1 = p.getFirstName();
		String name2 = ""; 

		if(name1 != null && name1.split("%").length >1){
			name1 = name1.split("%")[0].trim();
			setFirstNameFlag(2);  	
		}
		else
			setFirstNameFlag(1);	

		if(name1 != null){

			String names[] = name1.split("[ ,]");
			for(String name: names){
				name.trim(); 
				Ref_FirstName f = Ref.getFirstName(name);

				if(f != null  && f.getCode() != null && (f.getCode().equalsIgnoreCase("y") == true) && f.getName() != null && f.getName().length() > 0)
					name = f.getName();
				else{
					if(f == null){
						Ref_FirstName f1 = new Ref_FirstName();
						f1.setOriginal(name);
						f1.setCode("x");
						Ref.addFirstName(f1);
					}	
				}
				name2 = name2 + " " + name;  
			}
			setFirstName(name2.trim()); 		   
		}
	}

	/**
	 * 
	 * This routine transforms the registration date to the new format
	 * 
	 * @param p
	 */
	private void transformRegistrationDate(Person p){


		int[] result = 	Utils2.transformDateFields(p.getDayOfRegistration(),                    p.getMonthOfRegistration(),                    p.getYearOfRegistration(), 
				p.getDayOfRegistrationAfterInterpretation(), p.getMonthOfRegistrationAfterInterpretation(), p.getYearOfRegistrationAfterInterpretation());

		setDateOfRegistration(String.format("%02d-%02d-%04d", result[0], result[1], result[2]));
		setDateOfRegistrationFlag(result[3]);

	}		
	/**
	 * 
	 * This routine transforms the birth date to the new format
	 * 
	 * @param p
	 */

	private void transformBirthDate(Person p){

		int[] result = 	Utils2.transformDateFields(p.getDayOfBirth(),                    p.getMonthOfBirth(),                    p.getYearOfBirth(),
				p.getDayOfBirthAfterInterpretation(), p.getMonthOfBirthAfterInterpretation(), p.getYearOfBirthAfterInterpretation());


		setDateOfBirth(String.format("%02d-%02d-%04d", result[0], result[1], result[2]));
		setDateOfBirthFlag(result[3]);

	}

	/**
	 * 
	 * This routine transforms the decease date to the new format
	 * 
	 * @param p
	 */


	private void transformDeceaseDate(Person p){


		int[] result = 	Utils2.transformDateFields(p.getDayOfDecease(),                    p.getMonthOfDecease(),                    p.getYearOfDecease(),
				p.getDayOfDeceaseAfterInterpretation(), p.getMonthOfDeceaseAfterInterpretation(), p.getYearOfDeceaseAfterInterpretation());


		setDateOfDecease(String.format("%02d-%02d-%04d", result[0], result[1], result[2]));
		setDateOfDeceaseFlag(result[3]);

	}

	/**
	 * 
	 * This routine transforms the birth place
	 * 
	 * @param p
	 */

	private void transformBirthplace(Person p){


		
		String[] a = Utils.transformPlace(p.getPlaceOfBirth(), Ref.getAINB(getKeyToSourceRegister()), Ref.getRP(getKeyToRP()));

		if(a[0] != null){
			setPlaceOfBirthStandardized(a[0]);
			setPlaceOfBirthID(new Integer(a[1]).intValue()); 
			setPlaceOfBirthFlag(new Integer(a[2]).intValue());
		}
		else{		
			
			setPlaceOfBirthStandardized(p.getPlaceOfBirth());
			setPlaceOfBirthID(0);
			setPlaceOfBirthFlag(0);
			if(a[1] != null)
				message(a[1], p.getPlaceOfBirth());
		}
	}

	/**
	 * 
	 * This routine transforms the decease place
	 * 
	 * @param p
	 */

	private void transformDeceaseplace(Person p){
		

		String[] a = Utils.transformPlace(p.getPlaceOfDecease(), Ref.getAINB(getKeyToSourceRegister()), null);

		if(a[0] != null){
			setPlaceOfDeceaseStandardized(a[0]);
			setPlaceOfDeceaseID(new Integer(a[1]).intValue()); 
			setPlaceOfDeceaseFlag(new Integer(a[2]).intValue());
		}
		else{
			setPlaceOfDeceaseStandardized(p.getPlaceOfDecease());
			setPlaceOfDeceaseID(0); 
			setPlaceOfDeceaseFlag(0);
			
			if(a[1] != null)
				message(a[1], p.getPlaceOfDecease());

		}
	}

	/**
	 * 
	 * This routine transforms the legal place of living (= wetttig domicilie)
	 * 
	 * @param p
	 */

	private void transformLegalplaceOfLiving(Person p){

		if(p.getLegalPlaceOfLiving() == null)
			return;

		
		String[] a = Utils.transformPlace(p.getLegalPlaceOfLiving(), Ref.getAINB(getKeyToSourceRegister()), null);

		if(a[0] != null){
			setLegalPlaceOfLivingStandardized(a[0]);
			setLegalPlaceOfLivingID(new Integer(a[1]).intValue()); 
			setLegalPlaceOfLivingFlag(new Integer(a[2]).intValue());
		}
		else{
			setLegalPlaceOfLivingStandardized(p.getLegalPlaceOfLiving());
			setLegalPlaceOfLivingID(0); 
			setLegalPlaceOfLivingFlag(0);
			
			if(a[1] != null)
				message(a[1], p.getLegalPlaceOfLiving());

		}
	}

	/**
	 * 
	 * This routine prints out essential fields of the object
	 * 
	 */

	public void showFields(){	

		String Header = "";
		String Trailer = "";

		if(getNatureOfPerson() == ConstRelations2.FIRST_APPEARANCE_OF_OP)
			Trailer = "  <== OP";
		else
			if(getNatureOfPerson() == ConstRelations2.FURTHER_APPEARANCE_OF_OP)
				Trailer = "  <==  OP verder";


		String birth   = getDateOfBirth();
		String decease = getDateOfDecease();

		String birthPlace = getPlaceOfBirthStandardized();
		if(birthPlace != null){
			birthPlace = birthPlace.trim();
			if(birthPlace.length() == 0)
				birthPlace = "?? ";
		}
		else     
			birthPlace = "?? ";

		String deceasePlace = getPlaceOfDeceaseStandardized();
		if(deceasePlace != null){
			deceasePlace = deceasePlace.trim();
			if(deceasePlace.length() == 0)
				deceasePlace = "?? ";
		}
		else     
			deceasePlace = "?? ";



		String life = "(" + birthPlace + " " +  birth + " - " + deceasePlace + " " + decease + ")"; 

		String EntryDateHead = getEntryDateHead();      
		String RegistrationDate = getDateOfRegistration();

		String personNumber = String.format("%04d", getPersonID());
		String keyToPersons = String.format("%02d", getKeyToPersons());

		String startDate = getStartDate() != null ? getStartDate() : "00-00-0000";
		String startFlag = String.format("%02d", getStartFlag());
		String startEst  = String.format("%03d", getStartEst());

		String endDate = getEndDate() != null ? getEndDate() : "00-00-0000";
		String endFlag = String.format("%02d", getEndFlag());
		String endEst  = String.format("%03d", getEndEst());

		//System.out.println();
		System.out.println(startDate +
				" " + startFlag +			
				" " + startEst +			
				" " + endDate +
				" " + endFlag +			
				" " + endEst +			
				"   " + keyToPersons +
				"  " + personNumber +
				"  " + RegistrationDate +
				"  " + getFamilyName().trim() + 
				"  " + getFirstName().trim() +
				"  " + life +
				"  " + Trailer);


	}


	public void print(){

		showFields();
		System.out.println();
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){
			pds.print();
		}
		System.out.println();

	}
	/**
	 * 
	 * This procedure gives a first start date for the person
	 * 
	 * 
	 */
	public ArrayList<PersonStandardized>	giveStartDate1(){
		
		setStartDate("00-00-0000");

		// Give start date = headDate
		
		if(Utils.dateIsValid(getEntryDateHead()) == 0) {
			
			setStartDate(getEntryDateHead());
			setStartFlag(1);
			setStartEst(100);
		}

		// If Person is OP, use OP date (if later)
		
		if(getNatureOfPerson() == 1 || getNatureOfPerson() == 5){
			if(Utils.dateIsValid(getRegistrationStandardizedPersonAppearsIn().getEntryDateRP()) == 0  && Utils.dateIsValid(getStartDate()) == 0 &&
			 Utils.dayCount(getRegistrationStandardizedPersonAppearsIn().getEntryDateRP()) > Utils.dayCount(getStartDate())){				
				setStartDate(getRegistrationStandardizedPersonAppearsIn().getEntryDateRP());
				setStartFlag(2);
				setStartEst(120);				
			}			
		}

		// Try date of birth if first record in registration

		getDateOfBirthEstimate();

		// Try arrival date if later than headDate (and later or equal to current)

		getArrivalDate();

		// Try date of Person entry itself

		getEntryDate();

		// Try explicit Head Date

		getExplicitHeadDate();

		// Try marriage Date

		getMarriageDate();

		// Handle arrivals and Departures

		return(getArrivalDeparturesRecords());
	}

	/**
	 * 
	 * This routine tries to give a start date based on the end date of the same person in
	 * a previous registration. Only when the start flag is still 1.
	 * 
	 * 
	 * 
	 */
	public void	giveStartDate2(){
		
		if(getStartFlag() != 1)
			return;
		
		
		String endDate = null;
		int    endEst  = 0;
		
		int persNr = getPersonID();
		
		for(RegistrationStandardized rs: getRegistrationStandardizedPersonAppearsIn().getOp().getRegistrationsStandardizedOfOP()){
			
			if(rs == getRegistrationStandardizedPersonAppearsIn())
				break;
			
			for(PersonStandardized ps: rs.getPersonsStandardizedInRegistration()){
				if(ps.getPersonID() == persNr){					
					endDate = ps.getEndDate();
					endEst  = ps.getEndEst();					
				}
			}
		}
		
		if(endDate != null){
			setStartDate(endDate);
			setStartFlag(10);
			setStartEst(endEst);
		}
	}
	
	/**
	 * 
	 * This procedure gives a first end date for Person
	 * 
	 */

	public void	giveEndDate1(){
		
		// Try date of death
		
		if(getDateOfDeathEstimate()) return;
		
		// try date of departure or PK

		if(getDateOfDeparture()) return;
		
		// try entry date RP in next register
		
		if(getOPDateNextRegister()) return;
		
		// try entry date Head in next register
		
		if(getHeadDateNextRegister()) return;
		
		// try if group end can be used
		
		if(getGroupEnd(1)) return;
		
		// try start date same person in next Register
		
		if(getStartDateNextRegister()) return;

		// try marriage date
		
		if(getMarriageDateForEnd()) return;
		
		// try Census (=Volkstelling) date
		
		if(getCensusDate()) return;
		
		// try again if group end can be used
		
		if(getGroupEnd(2)) return;
		

	}
	
	/**
	 * 
	 * This routine looks if the date of death can be used as end date
	 * 
	 * @return
	 */	
	
	private boolean getDateOfDeathEstimate(){
		
		
		int day   = getOriginalPerson().getDayOfDecease();
		int month = getOriginalPerson().getMonthOfDecease();
		int year  = getOriginalPerson().getYearOfDecease();
		int estimate = 100; // presume exact

		if(year < 1750)
			return false;
		if(day < 1){			
			day = 1;
			estimate = 131;			
		}
		if(month < 1){			
			month = 1;
			estimate = 141;		
		}
		
		int dayCount = Utils.dayCount(day, month, year);
		
		// Check if there is an undated or later departure

		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){
			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK)	{	
				if(pds.getDateOfMutation2() != null) {
					if(Utils.dayCount(pds.getDateOfMutation2()) < 0)
						message("4362");
					else
						if(Utils.dayCount(pds.getDateOfMutation2()) > dayCount)
							message("4363"); 
				}
			}
		}
		
		// Check if later records exist 
		
		boolean later = false;
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){
			if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0 && Utils.dayCount(pds.getDateOfMutation2()) > dayCount){
				later = true;
				break;
			}
		}

		if(later == true) return false;

		String s = String.format("%02d-%02d-%04d", day, month, year);
			
		setEndDate(s);
		setEndEst(estimate);
		setEndFlag(51);
		
		return true;
		
		
	}
	/**
	 * 
	 * This routine looks if the date of departure can be used as end date
	 * For an undated departure, it looks if the phrase "&PK" can be found in DESTINATION_ST
	 * If so, the appropriate end data is set 
	 * 
	 */

	private boolean getDateOfDeparture(){
		
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){
			
			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK){
				
				if(Utils.dateIsValid(pds.getDateOfMutation2()) == 0 && Utils.dayCount(pds.getDateOfMutation2()) > 0){
					
					int day   = new Integer(pds.getDateOfMutation2().substring(0,2)).intValue();
					int month = new Integer(pds.getDateOfMutation2().substring(3,5)).intValue();
					int year  = new Integer(pds.getDateOfMutation2().substring(6,10)).intValue();
					int estimate = 100; // presume exact

					if(year < 1750)
						return false;
					if(day < 1){			
						day = 1;
						estimate = 131;			
					}
					if(month < 1){			
						month = 1;
						estimate = 141;		
					}
					String s = String.format("%02d-%02d-%04d", day, month, year);
					setEndDate(s);
					setEndEst(estimate);
					setEndFlag(52);
					return true;
				}
				else{					
					if(((PDS_PlaceOfDestination)pds).getDestinationStandardized().trim().toUpperCase().indexOf("&PK") > 0){
						
						setEndDate("31-12-1939");
						setEndEst(101);
						setEndFlag(53);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * This routine checks if this person is the OP and it is the last entry for the OP in this registration
	 * It will then check if there is a next registration 
	 * If so, it uses the OP date in that registration as the end date 
	 * 
	 * @return
	 */
	
	
	private boolean getOPDateNextRegister(){
		
		if(getNatureOfPerson() == ConstRelations2.FIRST_APPEARANCE_OF_OP || getNatureOfPerson() == ConstRelations2.FURTHER_APPEARANCE_OF_OP){
			
			int persNumber = getPersonID();
			
			// check if this is last entry for OP
			
			boolean start = false;
			for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
				
				if(ps == this){
					start = true;
					continue;
				}
				
				if(start == true)					
					if(ps.getPersonID() == persNumber)
						return false;
			}
			
			// find next registration
			
			RegistrationStandardized currentRegistration = getRegistrationStandardizedPersonAppearsIn();
			start = false;
			for(RegistrationStandardized rs: getRegistrationStandardizedPersonAppearsIn().getOp().getRegistrationsStandardizedOfOP()){
				
				//if(getKeyToRP() == 159225 && getKeyToSourceRegister() == 163308)
					//System.out.println("AAAA rs.getEntryDateRP() = " + rs.getEntryDateRP());
				
				if(rs == currentRegistration){
					start = true;
					continue;
				}
				
				if(start == true){ // if we get here, there is a next registration
					
					// We have to test if the next RP date is later than the start date of the RP in the current register
					// Because the RP could have left and return at a later date
					// This happens sometimes

					if(Utils.dateIsValid(getStartDate()) == 0 && Utils.dateIsValid(rs.getEntryDateRP()) == 0) {
						if(Utils.dayCount(rs.getEntryDateRP()) > Utils.dayCount(getStartDate())){
							setEndDate(rs.getEntryDateRP());
							setEndFlag(54);
							setEndEst(120);
							return true;
						}
						else return false;
					}
					else return false;
				}
				
			}
		}
		
		return false;
		
	}
	/**
	 * 
	 * This routine checks if this person is the Head and it is the last entry for the Head in this registration
	 * It will then check if there is a next registration 
	 * If so, it uses the headdate in that registration as the end date 
	 * 
	 * @return
	 */
	
	
	private boolean getHeadDateNextRegister(){
		
		if(getOriginalPerson().getIsHead() == true){
			
			int persNumber = getPersonID();
			
			// check if this is last entry for Head
			
			boolean start = false;
			for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
				
				if(ps == this){
					start = true;
					continue;
				}
				
				if(start == true)					
					if(ps.getPersonID() == persNumber)
						return false;
			}
			
			// find next registration
			
			RegistrationStandardized currentRegistration = getRegistrationStandardizedPersonAppearsIn();
			start = false;
			for(RegistrationStandardized rs: getRegistrationStandardizedPersonAppearsIn().getOp().getRegistrationsStandardizedOfOP()){
				
				if(rs == currentRegistration){
					start = true;
					continue;
				}
				
				if(start == true){ // if we get here, there is a next registration 
					
					// Check that Head is Head in new Registration
					
					for(PersonStandardized ps: rs.getPersonsStandardizedInRegistration()){
						if(ps.getOriginalPerson().getIsHead() == true && ps.getPersonID() != getPersonID())
							return false;
					}
					
					// We have to test if the next Head date is later than the start date of the Head in the current register
					// Because the RP could have left and return at a later date
					// This happens sometimes

					if(Utils.dateIsValid(getStartDate()) == 0 && Utils.dateIsValid(rs.getEntryDateHead()) == 0) {
						if(Utils.dayCount(rs.getEntryDateHead()) > Utils.dayCount(getStartDate())){
							setEndDate(rs.getEntryDateHead());
							setEndFlag(55);
							setEndEst(110);
							return true;
						}
						else return false;
					}
					else return false;
				}
						
			}
		}
		
		return false;
		
	}
	
	
	/**
	 * 
	 * This routine check if the group has ended
	 * The fase parameter tells which codes are ok
	 * 
	 * @return
	 */
	private boolean getGroupEnd(int fase){

		
		
		int group = 0;
		String endDate = null;
		int endEst = 0;
		int endFlag = 0;
		
		
		// get group of this PersonStandardized
		
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized())
			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK)
				group = ((PDS_PlaceOfDestination)pds).getDestinationGroup();
		
		boolean first = true;
		
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized())
				if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK)
					if(((PDS_PlaceOfDestination)pds).getDestinationGroup() == group){
						if(first == true)
							if(ps == this)
								return false;
							else{
								first = false;
								endDate = ps.getEndDate();
								endEst = ps.getEndEst();
								endFlag = ps.getEndFlag();
							}
						else{							
							if(ps == this){
								if(fase == 1 && (endFlag == 52 || endFlag == 53 || endFlag == 54)){									
									ps.setEndDate(endDate);
									ps.setEndFlag(56);
									ps.setEndEst(endEst);
									return true;
								}
								else
									if(fase == 2 && (endFlag == 57 || endFlag == 58 || endFlag == 59)){									
										ps.setEndDate(endDate);
										ps.setEndFlag(60);
										ps.setEndEst(endEst);
										return true;
									}

							}
						}
					}
		}
		
		return false;
	}
	
	
	
	/**
	 * 
	 * This routine looks if the current person (if last occurrence) can be found in the next Register
	 * The (first occurrence of) this Person is used. His startdate is the enddate of the current person
	 * 
	 * @return
	 */
	private boolean getStartDateNextRegister(){
		
		int persNumber = getPersonID();
		
		// check if this is last entry for Person
		
		boolean start = false;
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			
			if(ps == this){
				start = true;
				continue;
			}
			
			if(start == true)					
				if(ps.getPersonID() == persNumber)
					return false;
		}
		
		// find next registration
		
		RegistrationStandardized currentRegistration = getRegistrationStandardizedPersonAppearsIn();
		start = false;
		for(RegistrationStandardized rs: getRegistrationStandardizedPersonAppearsIn().getOp().getRegistrationsStandardizedOfOP()){
			
			if(rs == currentRegistration){
				start = true;
				continue;
			}
			
			if(start == true){ // if we get here, there is a next registration 
				
				// find person in this registration
				
				for(PersonStandardized ps: rs.getPersonsStandardizedInRegistration()){
					if(ps.getPersonID() == persNumber){		
						
						
						// We have to test if the start date next register is later than the start date of
						// this person
						// Because the person could have left and return at a later date
						// This happens sometimes

						if(Utils.dateIsValid(getStartDate()) == 0 && Utils.dateIsValid(ps.getStartDate()) == 0) {
							if(Utils.dayCount(ps.getStartDate()) > Utils.dayCount(getStartDate())){
								setEndDate(ps.getStartDate());
								setEndFlag(57);
								setEndEst(ps.getStartEst());
								return true;
							}
							else return false;
						}
						else return false;
					}
				}
				return false;
			}
		}
		
		return false;
	}
	
	
	/**
	 * 
	 * This routine looks if the marriage date can be used as an end date
	 * 
	 * @return
	 */
	public boolean getMarriageDateForEnd(){
		
		
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){

			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){

				if(((PDS_CivilStatus)pds).getCivilStatusFlag() == ConstRelations2.GEHUWD){					


					// Check that marriage in range register

					Ref_AINB ainb = Ref.getAINB(getKeyToSourceRegister());

					int marriageYear = new Integer(pds.getDateOfMutation2().substring(6,10)).intValue();

					if(marriageYear >= ainb.getStartYearRegister() && marriageYear <= ainb.getEndYearRegister()){

						if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0) {

							int marriageDay   = new Integer(pds.getDateOfMutation2().substring(0,2)).intValue();
							int marriageMonth = new Integer(pds.getDateOfMutation2().substring(3,5)).intValue();
							int estimate = 100; // presume exact

							if(marriageYear < 1750)
								return false;
							if(marriageDay < 1){			
								marriageDay = 1;
								estimate = 131;			
							}
							if(marriageMonth < 1){			
								marriageMonth = 1;
								estimate = 141;		
							}
							if(Common1.dateIsValid(marriageDay, marriageMonth, marriageYear) == 0 && Common1.dateIsValid(getStartDate()) == 0)
								if(Utils.dayCount(marriageDay, marriageMonth, marriageYear) > Utils.dayCount(getStartDate())){
									setEndDate(String.format("%02d-%02d-%04d", marriageDay, marriageMonth, marriageYear));
									setEndFlag(58);
									setEndEst(estimate);
									return true;
								}
						}
					}
				}
			}
		}


		return false;
	}
	
	
	/**
	 * 
	 * This routine tries to see if there is Census data for this person that can be used to establish an end date
	 * 
	 * @return
	 */
    private boolean getCensusDate(){
    	
    	
    	String[] censusYear = {"", "", "", "1849", "1859", "1869", "1879", "1889", "1899", "1909", "1920", "1930"}; 
    	
    	for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){
			
			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK){
				
				String dest = ((PDS_PlaceOfDestination)pds).getDestinationStandardized();
				if(dest == null) return false;
				
				String census = "";
				int    censusNr = 0;
				int    index = dest.indexOf("&VT");
				
				if(index >= 0){
					
					dest = dest.substring(index + 3);
					
					for(int i = 0; i < dest.length(); i++){
						if(dest.charAt(i) == ' ')
							continue;
						if("0123456789".indexOf(dest.charAt(i)) >= 0){
							census += dest.charAt(i);
							break;
						}
						else{
							census = "";
							break;
						}
					}
				}
				else
					return false;
				
				
				
				if(census.length() != 0){
				    censusNr = (new Integer(census)).intValue();
				    if(censusNr < 3 || censusNr > 11)
				    	return false;
				}
				else{
				    censusNr = 0;
					for(int i = 3; i <= censusYear.length; i++){
						if(Common1.dateIsValid("01-01-" + censusYear[i]) == 0 && Common1.dateIsValid(getStartDate()) == 0)
							if(Utils.dayCount("01-01-" + censusYear[i]) > Utils.dayCount(getStartDate()))
								break;
						censusNr++;
					}
				}
                if(censusNr > 0 && censusNr < censusYear.length){
                	setEndDate("30-12-" + censusYear[censusNr]);
                	setEndFlag(59);
					setEndEst(151);
					return true;
                }
			}
    	}

    	return false;
    }
	
    /**
     * 
     * This routine gives end date = range of source (einde register) for those records that
     * have end date < start date. Only for end_flag in 58-62
     * And only if range of source is later than the current value of end date  
     * 
     */
	public void	giveEndDate2(){

		
		if(Utils.dateIsValid(getStartDate()) == 0 && Utils.dateIsValid(getEndDate()) == 0 &&
				Utils.dayCount(getStartDate()) > Utils.dayCount(getEndDate()))
			if(58 <= getEndFlag() && getEndFlag() <= 62){
		    	Ref_AINB ainb = Ref.getAINB(getKeyToSourceRegister());
		    	if(Utils.dayCount("31-12-" + ainb.getEndYearRegister()) > Utils.dayCount(getEndDate())){
		    		setEndDate("31-12-" + ainb.getEndYearRegister());
		    		setEndFlag(63);
		    		setEndEst(100);
		    	}
			}

	}
	
	
	/**
	 * 
	 * This routine compares the (estimated) date of birth with the current start date (first occurrence of person only)
	 * If the date of birth is later, it is used	  
	 * 
	 */
	private void getDateOfBirthEstimate(){

		boolean first = true;
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			if(ps == this)
				break;
			if(ps.getPersonID() == getPersonID() && ps.getKeyToPersons() != getKeyToPersons()){
				first = false;
				break;
			}
		}

		if(first == true){
			
			int day   = getOriginalPerson().getDayOfBirth();
			int month = getOriginalPerson().getMonthOfBirth();
			int year  = getOriginalPerson().getYearOfBirth();
			int estimate = 100; // presume exact

			if(year < 1750)
				return;
			if(day < 1){			
				day = 1;
				estimate = 131;			
			}
			if(month < 1){			
				month = 1;
				estimate = 141;		
			}
			
			//System.out.println("XXX " + getStartDate());
			
			if(Common1.dateIsValid(day, month, year) == 0 	&& getStartDate() != null && Common1.dateIsValid(getStartDate()) == 1 
					&& Utils.dayCount(day, month, year) > Utils.dayCount(getStartDate())){
				String s = String.format("%02d-%02d-%04d", day, month, year);
				setStartDate(s);
				setStartFlag(3);
				setStartEst(estimate);				
			}
		}
	}

	/**
	 * 
	 * This routine looks for the earliest arrival date of the person that is:
	 * 
	 *      -- later than current startDate
	 *      -- later or equal head
	 *      -- not later than first departure
	 */

	private void getArrivalDate(){

		int arrDay   = 100;
		int arrMonth = 0;
		int arrYear  = 0;

		int depDay   = 100;
		int depMonth = 0;
		int depYear  = 0;

		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){

			if(Common1.dateIsValid(pds.getDateOfMutation2()) != 0)  continue;
			
			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){

				if(arrDay == 100){					
					arrDay    = new Integer(pds.getDateOfMutation2().substring(0,2)).intValue();
					arrMonth  = new Integer(pds.getDateOfMutation2().substring(3,5)).intValue();
					arrYear   = new Integer(pds.getDateOfMutation2().substring(6,10)).intValue();
				}

			}
			else{
				if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK){
					if(depDay == 100){					
						depDay    = new Integer(pds.getDateOfMutation2().substring(0,2)).intValue();
						depMonth  = new Integer(pds.getDateOfMutation2().substring(3,5)).intValue();;
						depYear   = new Integer(pds.getDateOfMutation2().substring(6,10)).intValue();
					}

				}
			}

		}

		int    estimate = 100; // presume exact

		if(arrYear < 1750)
			return;
		if(arrDay < 1){			
			arrDay = 1;
			estimate = 131;			
		}
		if(arrMonth < 1){			
			arrMonth = 1;
			estimate = 141;		
		}
		
		
		if(Common1.dateIsValid(arrDay, arrMonth, arrYear) == 0 && Common1.dateIsValid(depDay, depMonth, depYear) == 0){
			if(arrDay != 100 && (depDay == 100 || Utils.dayCount(arrDay, arrMonth, arrYear) <= Utils.dayCount(depDay, depMonth, depYear))){
				String s = String.format("%02d-%02d-%04d", arrDay, arrMonth, arrYear);
				if(Utils.dateIsValid(s) == 0 && Utils.dateIsValid(getStartDate()) == 0){
					if(Utils.dayCount(arrDay, arrMonth, arrYear) > Utils.dayCount(getStartDate())){				
						setStartDate(s);
						setStartFlag(4);
						setStartEst(estimate);

					}
					else
						if(getEntryDateHead().equals(getStartDate()) && getEntryDateHead().equals(s)){
							setStartFlag(4);
						}
				}
			}
		}
	}

	/**
	 * 
	 * This routine looks at the entry date of the person to see if it can be used
	 * 
	 */	

	private void getEntryDate(){

		int entDay   = getOriginalPerson().getDayOfRegistration();
		int entMonth = getOriginalPerson().getMonthOfRegistration();
		int entYear  = getOriginalPerson().getYearOfRegistration();

		int estimate = 100; // presume exact

		if(entYear < 1750)
			return;
		if(entDay < 1){
			if(entYear == getOriginalPerson().getYearEntryHead() && entMonth == getOriginalPerson().getMonthEntryHead()){				
				entDay = getOriginalPerson().getDayEntryHead();
				estimate = 134;
			}
			else{
				entDay = 1;
				estimate = 131;
			}
		}
		if(entMonth < 1){	
			if(entYear == getOriginalPerson().getYearEntryHead()){
				entDay = getOriginalPerson().getDayEntryHead();
				entMonth = getOriginalPerson().getMonthEntryHead();
				estimate = 144;

			}
			else{
				entMonth = 1;
				estimate = 141;
			}
		}

		// find start date of person before this person

		String previousPersonStartDate = "00-00-0000";
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			if(ps == this)
				break;
			previousPersonStartDate = ps.getStartDate();			
		}

		// Use entry date if it is:
		//    -- later than the date established thus far
		//    -- later than the date of the previous person
		//    -- previous person date is later than date established so far

        if(previousPersonStartDate != null && previousPersonStartDate.equals("00-00-0000"))
        		return;

        if(Common1.dateIsValid(entDay, entMonth, entYear) == 0 && Utils.dateIsValid(getStartDate()) == 0) {
        	if(Utils.dayCount(entDay, entMonth, entYear) > Utils.dayCount(getStartDate())){
        		if(Utils.dayCount(entDay, entMonth, entYear) > Utils.dayCount(previousPersonStartDate)){
        			if(Utils.dayCount(previousPersonStartDate) > Utils.dayCount(getStartDate())){
        				String s = String.format("%02d-%02d-%04d", entDay, entMonth, entYear);
        				setStartDate(s);
        				setStartFlag(5);
        				setStartEst(estimate);
        			}
        		}
        	}
        }
	}

	/**
	 * This routine looks at the explicit Head Date to see if it can be used
	 * 
	 */

	private void getExplicitHeadDate(){
		
		

		if(getStartDate() != null && Common1.dateIsValid(getStartDate()) == 0 &&
				getEntryDateHead() != null && Common1.dateIsValid(getEntryDateHead()) == 0 &&
				!getStartDate().equals(getEntryDateHead()))
			return;


		// find start date of person before this person

		String previousPersonStartDate = "00-00-0000";
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			if(ps == this)
				break;
			previousPersonStartDate = ps.getStartDate();			
		}

		if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(previousPersonStartDate) == 0)
			if(Utils.dayCount(getStartDate()) >= Utils.dayCount(previousPersonStartDate))
				return;

		// find if secondary head and get date

		String expHeadDate = "00-00-0000";
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){			
			if(pds.getKeyToDistinguishDynamicDataType() == 11){  // relation to Head

				String dd = ((PDS_RelationToHead)pds).getDynamicData2();
				if(dd != null){
					int i = dd.indexOf("###$");
					if(i > 0){
						expHeadDate = dd.substring(i + 4).trim().replaceAll("/", "-");
						break;

					}			
				}
			}
		}

		if(expHeadDate.equals("00-00-0000"))
			return;

		if(Common1.dateIsValid(expHeadDate) == 0 && Common1.dateIsValid(previousPersonStartDate) == 0)
			if(Utils.dayCount(expHeadDate) <= Utils.dayCount(previousPersonStartDate))
				return;
		
		// look at later birth or arrival records

		boolean start = false;
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			if(ps == this){
				start = true;
				continue;
			}
			if(start == true){	
				if(Common1.dateIsValid(ps.getDateOfBirth()) == 0 && Common1.dateIsValid(expHeadDate) == 0)
					if(Utils.dayCount(ps.getDateOfBirth()) < Utils.dayCount(expHeadDate))
						return;
				for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){	
					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST)
						if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0 && Common1.dateIsValid(expHeadDate) == 0)
							if(Utils.dayCount(pds.getDateOfMutation2()) < Utils.dayCount(expHeadDate))
								return;
				
				}	
			}
		}

		setStartDate(expHeadDate);
		setStartFlag(6);
		setStartEst(100);

	}

	/**
	 * This routine looks at the marriage Date to see if it can be used
	 * 
	 */

	private void getMarriageDate(){

		if(getStartDate() != null && !getStartDate().equals(getEntryDateHead()))
			return;


		// find start date of person before this person

		String previousPersonStartDate = "00-00-0000";
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			if(ps == this)
				break;
			previousPersonStartDate = ps.getStartDate();			
		}

		if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(previousPersonStartDate) == 0)
			if(Utils.dayCount(getStartDate()) >= Utils.dayCount(previousPersonStartDate))
				return;

		// See if person not related to Head as son or daughter

		boolean sonOrDaughter = false;
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){			
			if(pds.getKeyToDistinguishDynamicDataType() == 11){  // relation to Head
				if(((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.ZOON ||
						((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.DOCHTER){
					sonOrDaughter = true;
					break;
				}
			}
		}		
		if(sonOrDaughter)
			return;

		// See if person marries to someone in Household (first marriage)

		String marriageDate = "00-00-0000";
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){			
			if(pds.getKeyToDistinguishDynamicDataType() == 2){  // civil status
				if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD &&
						pds.getValueOfRelatedPerson() > 0){
					marriageDate = pds.getDateOfMutation2();
					break;
				}
			}
		}
		if(marriageDate != null && marriageDate.equals("00-00-0000"))
			return;

		// See if marriage date later than foregoing date

		if(Common1.dateIsValid(marriageDate) == 0 && Common1.dateIsValid(previousPersonStartDate) == 0)
			if(Utils.dayCount(marriageDate) <= Utils.dayCount(previousPersonStartDate))
				return;


		// look at later birth or arrival records

		boolean start = false;
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			if(ps == this){
				start = true;
				continue;
			}
			if(start == true){	
				if(Common1.dateIsValid(ps.getDateOfBirth()) == 0 && Common1.dateIsValid(marriageDate) == 0)
					if(Utils.dayCount(ps.getDateOfBirth()) < Utils.dayCount(marriageDate))
						return;
				for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){	
					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST)
						if(Common1.dateIsValid(marriageDate) == 0 && Common1.dateIsValid(pds.getDateOfMutation2()) == 0)
							if(Utils.dayCount(pds.getDateOfMutation2()) < Utils.dayCount(marriageDate))
								return;
				
				}	
			}
		}

		// Look at last departure date of person
		
		int depDays = 0;
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){	
			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK)
				if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0)
					depDays = Utils.dayCount(pds.getDateOfMutation2());
		}	

		if(depDays != 0)
			if(Common1.dateIsValid(marriageDate) == 0)
				if(depDays < Utils.dayCount(marriageDate) + 365)
					return;
		

		setStartDate(marriageDate);
		setStartFlag(7);
		setStartEst(100);

	}

	/**
	 * 
	 * This routine looks at the arrival and departure records
	 * It splits them into multiple records if necessary, and it estimates the start
	 * (and sometimes end)-dates
	 * 
	 * @return
	 */
	public ArrayList<PersonStandardized> getArrivalDeparturesRecords(){

		int arrivalI = 0;
		int departureI = 0;

		int[] arrDays     = new int[2];
		int[] depDays   = new int[2];
		
		int [] arrEst = new int[2];
		int [] depEst = new int[2];

		PersonDynamicStandardized [] arrival   = new PersonDynamicStandardized[2];
		PersonDynamicStandardized [] departure = new PersonDynamicStandardized[2];


		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){			
			
			
			int day   = 0;
			int month = 0;
			int year  = 0;
			
			if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0) {
				day   = (new Integer(pds.getDateOfMutation2().substring(0,2))).intValue();
				month = (new Integer(pds.getDateOfMutation2().substring(3,5))).intValue();
				year  = (new Integer(pds.getDateOfMutation2().substring(6,10))).intValue();
			}
			else
				continue;
			
			int dayHead   = 0;
			int monthHead = 0;
			int yearHead  = 0;
			
			if(Common1.dateIsValid(pds.getEntryDateHead()) == 0) {
				dayHead   = (new Integer(pds.getEntryDateHead().substring(0,2))).intValue();
				monthHead = (new Integer(pds.getEntryDateHead().substring(3,5))).intValue();
				yearHead  = (new Integer(pds.getEntryDateHead().substring(6,10))).intValue();
			}
			else
				continue;
			
			int dayCount = 0;
			int estimate = 0;
			
			int [] a = Utils.normalize(day, month, year, dayHead, monthHead, yearHead); 
			if(Common1.dateIsValid(a[0], a[1], a[2]) == 0) {
				dayCount = Utils.dayCount(a[0], a[1], a[2]);
				estimate = a[3];
			}
			
			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){				
				if(arrivalI < 2){				
					arrDays[arrivalI] = dayCount;
					arrival[arrivalI] = pds;
					arrEst[arrivalI]  = estimate;
				}
				arrivalI++;
			}
			else{
				if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK){				
					if(departureI < 2){					
						depDays[departureI] = dayCount;
						departure[departureI] = pds;
						depEst[departureI] = estimate;
					}
					departureI++;
				}
			}
		}

		if(arrDays[0] == 0 && depDays[0] == 0)  // No arrivals, no departures
			return null;

		if(arrDays[1] == 0 && depDays[1] == 0){

			// 1    Maximal 1 arrival and maximal 1 departure
			//
			// 1A   1st arrival          -                     OK
			// 1B       -              1st departure           OK
			// 1C   1st arrival   <    1st departure           OK
			// 1D   1st arrival   >    1st departure           OK, extra line for first arrival 
			// 1E   1st arrival = -1   1st departure           OK 
			// 1F   1st arrival        1st departure = -1      Error 1328: Date first departure invalid/missing, already handled    

			if(arrDays[0] > 0 && depDays[0] > 0 && arrDays[0] >  depDays[0]) return(handle1D(arrival, departure, arrDays, depDays, arrEst, depEst));


		}

		if(arrDays[1] == 0 && depDays[1] != 0){

			// 2   Maximal 1 arrival and 2 departures
			//
			// 2A  1st arrival        <     1st departure
			//        -               <     2nd departure      Error 1327: Second arrival date invalid/missing
			// 2B  1st arrival = -1         1st departure
			//        -                     2nd departure      Error 1327: Second arrival date invalid/missing
			// 2C  1st arrival        <     1st departure
			//        -               >     2nd departure      Error 1324: Date first departure later/equal second departure + Error 1327
			// 2D  1st arrival        >     1st departure
			//        -               <     2nd departure      Error 1327
			// 2E  1st arrival        >     1st departure
			//        -               >     2nd departure      Error 1329: Date first arrival later/equal date second departure + Error 1327
			// 2F     -                     1st departure
			//        -                     2nd departure      Error 1327

			return null;
		}

		if(arrDays[1] != 0 && depDays[1] == 0){

			// 3   Maximal 1 departure and 2 arrivals
			//
			// 3A  1st arrival        <     1st departure        Extra line for second arrival, first departure -> second departure
			//     2nd arrival        <          -               estimate date first departure
			// 3B  1st arrival              1st departure = -1
			//     2nd arrival                   -               Error 1322: 2 arrivals, no valid departure + Error 1328: Date first departure invalid/missing
			// 3C  1st arrival        >     1st departure
			//     2nd arrival        <                          Error 1323: Date first departure later/equal second arrival + Error 1327 Second arrival date invalid/missing
			// 3D  1st arrival        <     1st departure
			//     2nd arrival        >                          Extra line for second arrival         
			// 3E  1st arrival        >     1st departure        
			//     2nd arrival        >                          Extra lines for 1st and 2nd arrival + estimate 2nd departure date
			// 3F  1st arrival                   -       
			//     2nd arrival                   -               Error 1322: 2 arrivals, no valid departure
			// 3G  1st arrival              1st departure
			//     2nd arrival = -1                              Error 1327: Second arrival date invalid/missing 



			if(depDays[0] > 0 && arrDays[1] != -1){
				if(arrDays[0] < depDays[0] && arrDays[1] < depDays[0]) return(handle3A(arrival, departure, arrDays, depDays, arrEst, depEst));
				if(arrDays[0] < depDays[0] && arrDays[1] > depDays[0]) return(handle3D(arrival, departure, arrDays, depDays, arrEst, depEst));				
				if(arrDays[0] > depDays[0] && arrDays[1] > depDays[0]) return(handle3E(arrival, departure, arrDays, depDays, arrEst, depEst));
			}
		}	

		if(arrDays[1] != 0 && depDays[1] != 0){

			if(arrDays[0] != -1 && depDays[0] != -1  && arrDays[1] != -1 && depDays[1] != -1){ // all dates specified

				// 4  2 arrivals and 2 departures, all dated
				//
				// 4A  1st arrival        <     1st departure
				//     2nd arrival        <     2nd departure     Extra line for 2nd arrival - 2nd departure
				//		
				// 4B  1st arrival        <     1st departure
				//     2nd arrival        >     2nd departure     Extra lines for 2nd (implicit) arrival and  (2nd ->) 3rd arrival + estimate second implicit arrival
				//		
				// 4C  1st arrival        >     1st departure     Extra lines for (1st ->) 2nd and (2nd ->) 3d arrival + estimate 2nd (implicit) departure + 
				//     2nd arrival        <     2nd departure     2nd departure -> 3d departure 
				//		
				// 4D  1st arrival        >     1st departure     Extra lines for 2nd and 3rd arrival
				//     2nd arrival        >     2nd departure     

				if(arrDays[0] < depDays[0] && arrDays[1] < depDays[1]) return(handle4A(arrival, departure, arrDays, depDays, arrEst, depEst));
				if(arrDays[0] < depDays[0] && arrDays[1] > depDays[1]) return(handle4B(arrival, departure, arrDays, depDays, arrEst, depEst));
				if(arrDays[0] > depDays[0] && arrDays[1] < depDays[1]) return(handle4C(arrival, departure, arrDays, depDays, arrEst, depEst));
				if(arrDays[0] > depDays[0] && arrDays[1] > depDays[1]) return(handle4D(arrival, departure, arrDays, depDays, arrEst, depEst));

			}
			
			if((arrDays[0] == -1 || depDays[0] == -1)  && arrDays[1] != -1 && depDays[1] != -1){ 	// 1 or 2 times -1 in first arrival/departure				

				// 5  2 departures and 2 arrivals, first one can be -1
				//
				// 5A  1st arrival              1st departure = -1
				//     2nd arrival        >     2nd departure        Error 1322: 2 arrivals, no valid departure + Error 1328: Date first departure invalid/missing
				// 5B  1st arrival              1st departure = -1
				//     2nd arrival        <     2nd departure        Error 1322: 2 arrivals, no valid departure + Error 1328: Date first departure invalid/missing
				// 5C  1st arrival = -1         1st departure
				//     2nd arrival        <     2nd departure        Estimate 1st arrival by start date, then run 4A
				// 5D  1st arrival = -1         1st departure
				//     2nd arrival        >     2nd departure        Estimate 1st arrival by start date, then run 4B
				// 5E  1st arrival = -1         1st departure = -1   
				//     2nd arrival        <     2nd departure        Error 1322: 2 arrivals, no valid departure + Error 1328: Date first departure invalid/missing
				// 5F  1st arrival = -1         1st departure = -1
				//     2nd arrival        >     2nd departure        Error 1322: 2 arrivals, no valid departure + Error 1328: Date first departure invalid/missing	
			
				if(arrDays[0] == -1 && arrDays[1] < depDays[1]) return(handle4A(arrival, departure, arrDays, depDays, arrEst, depEst)); // 5C
				if(arrDays[0] == -1 && arrDays[1] > depDays[1]) return(handle4B(arrival, departure, arrDays, depDays, arrEst, depEst)); // 5D
				
			}
			
			if((arrDays[1] == -1 || depDays[1] == -1)  && arrDays[0] != -1 && depDays[0] != -1){  // 1 or 2 times -1 in second arrival/departure	

				// 6  2 departures and 2 arrivals, second one can be -1
				//
				// 6A  1st arrival        >     1st departure    
				//     2nd arrival              2nd departure = -1   Estimate second departure, then run 4D 
				// 6B  1st arrival        <     1st departure 
				//     2nd arrival              2nd departure = -1   OK
				// 6C  1st arrival        >     1st departure
				//     2nd arrival = -1         2nd departure        Error 1327: Second arrival date invalid/missing
				// 6D  1st arrival        <     1st departure
				//     2nd arrival = -1         2nd departure        Error 1327: Second arrival date invalid/missing
				// 6E  1st arrival        >     1st departure        
				//     2nd arrival = -1         2nd departure = -1   Error 1327: Second arrival date invalid/missing
				// 6F  1st arrival        <     1st departure     
				//     2nd arrival = -1         2nd departure = -1   Error 1327: Second arrival date invalid/missing

				if(arrDays[0] > depDays[0] && arrDays[1] > 0 && depDays[1] == -1){ // 6A
					depDays[1] = (arrDays[0] + arrDays[1])/2;
					ArrayList<PersonStandardized>  a = handle4D(arrival, departure, arrDays, depDays, arrEst, depEst);
					PersonStandardized ps = a.get(0);
					ps.setEndEst(202); //  
					return(a);
				}
				if(arrDays[0] < depDays[0] && arrDays[1] > 0 && depDays[1] == -1){ // 6B
					return(handle4A(arrival, departure, arrDays, depDays, arrEst, depEst));
				}

			}
			
		}

		return null;
	}

	/**
	 * 
	 * This routine handles situation 1D
	 * 
	 */

	private ArrayList<PersonStandardized> handle1D(PersonDynamicStandardized [] arr, PersonDynamicStandardized [] dep, int[] arrDays, int[] depDays, int arrEst[], int[] depEst){

		ArrayList<PersonStandardized> a = insert1Person();// Extra line
		PersonStandardized ps = a.get(0);				

		move(arr[0], ps); // move the arrival element 

		// set start data on new PersonStandardized element

		if(arrDays[0] > 0) {
			ps.setStartDate(Utils.dateFromDayCount(arrDays[0]));
			ps.setStartEst(arrEst[0]); 
			ps.setStartFlag(8);
		}

		// set end date on current PersonStandardized

		if(depDays[0] > 0) {
			setEndDate(Utils.dateFromDayCount(depDays[0]));
			setEndEst(depEst[0]);
			setEndFlag(8);
		}


		return(a); 


	}

	/**
	 * 
	 * This routine handles situation 3A
	 * 
	 */

	private ArrayList<PersonStandardized> handle3A(PersonDynamicStandardized [] arr, PersonDynamicStandardized [] dep, int[] arrDays, int[] depDays, int[] arrEst, int[] depEst){

		ArrayList<PersonStandardized> a = insert1Person();// Extra line
		PersonStandardized ps = a.get(0);

		move(arr[1], ps);   // move 2nd arrival element
		move(dep[0], ps); // move 1st departure element

		// create new departure record like the existing one

		PersonDynamicStandardized pdsd = dep[0].copyPersonDynamicStandardized();

		// set destination data to null

		((PDS_PlaceOfDestination)pdsd).setDestinationStandardized("");
		((PDS_PlaceOfDestination)pdsd).setDestinationID(0);
		((PDS_PlaceOfDestination)pdsd).setDestinationFlag(0);

		pdsd.setPersonStandardizedToWhomDynamicDataRefers(this);
		
		// set start date new Person
		
		if(arrDays[0] > 0) {
			ps.setStartDate(Utils.dateFromDayCount(arrDays[1]));
			ps.setStartEst(arrEst[1]);
			ps.setStartFlag(8);
		}

		// estimate the departure date

		String estDate = "";
		if((arrDays[0] + arrDays[1])/2 > 0){
			estDate = Utils.dateFromDayCount((arrDays[0] + arrDays[1])/2);
			pdsd.setDateOfMutation(estDate);
		
		// set end date on this PersonStandardized
		
			setEndDate(estDate);
			setEndEst(202);
			setEndFlag(8);
		}

		// add new departure record to this PersonStandardized

		getDynamicDataOfPersonStandardized().add(pdsd); 	


		return(a); 
	}

	/**
	 * 
	 * This routine handles situation 3D
	 * 
	 */

	private ArrayList<PersonStandardized> handle3D(PersonDynamicStandardized [] arr, PersonDynamicStandardized [] dep, int[] arrDays, int[] depDays, int[] arrEst, int[] depEst){

		ArrayList<PersonStandardized> a = insert1Person();// Extra line
		PersonStandardized ps = a.get(0);

		move(arr[1], ps); // move 2nd arrival element

		if(arrDays[1] > 0) {
			ps.setStartDate(Utils.dateFromDayCount(arrDays[1]));					
			ps.setStartEst(arrEst[1]); 
			ps.setStartFlag(8);	
		}

		// set end date on this PersonStandardized
		
		if(depDays[0] > 0) {
			setEndDate(Utils.dateFromDayCount(depDays[0]));
			setEndEst(depEst[0]);
			setEndFlag(8);
		}
		
		return(a); 

	}

	/**
	 * 
	 * This routine handles situation 3E
	 * 
	 */

	private ArrayList<PersonStandardized> handle3E(PersonDynamicStandardized [] arr, PersonDynamicStandardized [] dep, int[] arrDays, int[] depDays, int[] arrEst, int[] depEst){

		ArrayList<PersonStandardized> a = insert2Persons();// Extra line
		PersonStandardized ps1 = a.get(0);
		PersonStandardized ps2 = a.get(1);

		move(arr[0], ps1); // move 1st arrival element to 1st new PersonStandardized

		// set start data on person 1

		if(arrDays[0] > 0) {
			ps1.setStartDate(Utils.dateFromDayCount(arrDays[0]));
			ps1.setStartEst(arrEst[0]);
			ps1.setStartFlag(8);
		}
		
		// create new departure record like the existing one

		PersonDynamicStandardized pdsd = dep[0].copyPersonDynamicStandardized();
		
		// set destination data to null

		((PDS_PlaceOfDestination)pdsd).setDestinationStandardized("");
		((PDS_PlaceOfDestination)pdsd).setDestinationID(0);
		((PDS_PlaceOfDestination)pdsd).setDestinationFlag(0);


		// estimate the departure date
		
		String estDate = "";
		if((arrDays[0] + arrDays[1])/2 > 0) {
			estDate = Utils.dateFromDayCount((arrDays[0] + arrDays[1])/2);
			pdsd.setDateOfMutation(estDate);	  
		}

		pdsd.setPersonStandardizedToWhomDynamicDataRefers(ps1); // point to 1st new PersonStandardized
		ps1.getDynamicDataOfPersonStandardized().add(pdsd);
		
		// set end date person 1
		
		ps1.setEndDate(estDate);
		ps1.setEndEst(202);
		ps1.setEndFlag(8);
		
		move(arr[1], ps2); // move 2nd arrival element to 2nd new PersonStandardized

		// set start data on person 2

		if(arrDays[1] > 0) {
			ps2.setStartDate(Utils.dateFromDayCount(arrDays[1]));
			ps2.setStartEst(arrEst[1]);
			ps2.setStartFlag(8);	
		}

		// set end date on this person
		
		if(depDays[0] > 0) {
			setEndDate(Utils.dateFromDayCount(depDays[0]));
			setEndEst(depEst[0]);
			setEndFlag(8);
		}
		
		return(a); 


	}


	/**
	 * 
	 * This routine handles situation 4A
	 * 
	 */

	private ArrayList<PersonStandardized> handle4A(PersonDynamicStandardized [] arr, PersonDynamicStandardized [] dep, int[] arrDays, int[] depDays, int[] arrEst, int[] depEst){


		ArrayList<PersonStandardized> a = insert1Person();// Extra line
		PersonStandardized ps = a.get(0);

		move(arr[1], ps);   // move 2nd arrival element to new PersonStandardized
		move(dep[1], ps); // move 2nd departure element to new PersonStandardized

		// set start data on new person 

		if(arrDays[1] > 0) {
			ps.setStartDate(Utils.dateFromDayCount(arrDays[1]));
			ps.setStartEst(arrEst[1]);
			ps.setStartFlag(8);	
		}

		// set end date on this person
		
		if(depDays[0] > 0) {
			setEndDate(Utils.dateFromDayCount(depDays[0]));
			setEndEst(depEst[0]);
			setEndFlag(8);	
		}
		
		return a;




	}

	/**
	 * 
	 * This routine handles situation 4B
	 * 
	 */

	private ArrayList<PersonStandardized> handle4B(PersonDynamicStandardized [] arr, PersonDynamicStandardized [] dep, int[] arrDays, int[] depDays, int[] arrEst, int[] depEst){

		ArrayList<PersonStandardized> a = insert2Persons();// Extra lines
		PersonStandardized ps1 = a.get(0);
		PersonStandardized ps2 = a.get(1);

		// create and estimate arrival record for new person 1

		PersonDynamicStandardized pdsa = arr[0].copyPersonDynamicStandardized();

		// set destination data to null

		((PDS_PlaceOfOrigin)pdsa).setOriginStandardized("");
		((PDS_PlaceOfOrigin)pdsa).setOriginID(0);
		((PDS_PlaceOfOrigin)pdsa).setOriginFlag(0);

		// estimate the arrival date

		String estDate = "";
		if((depDays[0] + depDays[1])/2 > 0) {
			estDate = Utils.dateFromDayCount((depDays[0] + depDays[1])/2);
			pdsa.setDateOfMutation(estDate);	
		}

		// link the elements

		pdsa.setPersonStandardizedToWhomDynamicDataRefers(ps1);
		ps1.getDynamicDataOfPersonStandardized().add(pdsa);
		
		move(dep[1], ps1); // move 2nd departure to new person 1

		// set start date 1st new person
		
		ps1.setStartDate(pdsa.getDateOfMutation2());
		ps1.setStartFlag(8);
		ps1.setStartEst(201);
		
		// set end date on 1st new person
		
		if(depDays[1] > 0) {
			ps1.setEndDate(Utils.dateFromDayCount(depDays[1]));
			ps1.setEndFlag(8);
			ps1.setEndEst(depEst[1]);
		}

		move(arr[1], ps2);   // move 2nd arrival to new person 2

		// set start data on new person 2

		if(arrDays[1] > 0) {
			ps2.setStartDate(Utils.dateFromDayCount(arrDays[1]));
			ps2.setStartEst(arrEst[1]);
			ps2.setStartFlag(8);
		}
		
		// set end date on this person
		
		if(depDays[0] > 0) {
			setEndDate(Utils.dateFromDayCount(depDays[0]));
			setEndEst(depEst[0]);
			setEndFlag(8);	
		}

		return a;


	}

	/**
	 * 
	 * This routine handles situation 4C
	 * 
	 */

	private ArrayList<PersonStandardized> handle4C(PersonDynamicStandardized [] arr, PersonDynamicStandardized [] dep, int[] arrDays, int[] depDays ,int[] arrEst, int[] depEst){

		ArrayList<PersonStandardized> a = insert2Persons();// Extra lines
		PersonStandardized ps1 = a.get(0);
		PersonStandardized ps2 = a.get(1);

		move(arr[0], ps1); // move 1st arrival to new person 1

		// create new departure record like the existing one

		PersonDynamicStandardized pdsd = dep[0].copyPersonDynamicStandardized();

		// set destination data to null		

		((PDS_PlaceOfDestination)pdsd).setDestinationStandardized("");
		((PDS_PlaceOfDestination)pdsd).setDestinationID(0);
		((PDS_PlaceOfDestination)pdsd).setDestinationFlag(0);

		// estimate the departure date

		String estDate = "";
		if((arrDays[0] + arrDays[1])/2 > 0) {
			estDate = Utils.dateFromDayCount((arrDays[0] + arrDays[1])/2);
			pdsd.setDateOfMutation(estDate);
		}

		// add the arrival record to person 1

		pdsd.setPersonStandardizedToWhomDynamicDataRefers(ps1); // point to 1st new PersonStandardized
		ps1.getDynamicDataOfPersonStandardized().add(pdsd);

		
		// set start date on person 1
		
		ps1.setStartDate(arr[0].getDateOfMutation2());
		ps1.setStartEst(100);
		ps1.setStartFlag(8);			
		
		// set end date on person 1
		
		ps1.setEndDate(estDate);
		ps1.setEndEst(202);
		ps1.setEndFlag(8);	
		

		move(arr[1], ps2); // move second arrival to second new person
		move(dep[1], ps2); // move 2nd departure to new person 2

		// set start date on person 2
		
		if(arrDays[1] > 0) {
			ps2.setStartDate(Utils.dateFromDayCount(arrDays[1]));
			ps2.setStartEst(arrEst[1]);
			ps2.setStartFlag(8);
		}

		// set end date on person 2
		
		if(depDays[1] > 0) {
			ps2.setStartDate(Utils.dateFromDayCount(depDays[1]));
			ps2.setStartEst(depEst[1]);
			ps2.setStartFlag(8);
		}
		
		// set end date on this person
		
		if(depDays[0] > 0) {
			setEndDate(Utils.dateFromDayCount(depDays[0]));
			setEndEst(depEst[0]);
			setEndFlag(8);	
		}

		return a;

	}

	/**
	 * 
	 * This routine handles situation 4D
	 * 
	 */

	private ArrayList<PersonStandardized> handle4D(PersonDynamicStandardized [] arr, PersonDynamicStandardized [] dep, int[] arrDays, int[] depDays, int[] arrEst, int[] depEst){

		if(arrDays[0] < depDays[1]){

			ArrayList<PersonStandardized> a = insert2Persons();// Extra lines
			PersonStandardized ps1 = a.get(0);
			PersonStandardized ps2 = a.get(1);

			move(arr[0], ps1); // move first arrival to first new person

			move(dep[1], ps1);// move 2nd departure to new person 1

			// set start and end date on ps1
			if(arrDays[0] > 0) {
				ps1.setStartDate(Utils.dateFromDayCount(arrDays[0]));
				ps1.setStartEst(arrEst[0]);			
				ps1.setStartFlag(8);	
			}

			if(depDays[1] > 0) {
				ps1.setEndDate(Utils.dateFromDayCount(depDays[1]));
				ps1.setEndEst(100);			
				ps1.setEndFlag(8);		
			}

			move(arr[1], ps2); // move second arrival to second new person

			// set start date on ps2
			
			if(arrDays[1] > 0) {
				ps2.setStartDate(Utils.dateFromDayCount(arrDays[1]));
				ps2.setStartEst(arrEst[1]);
				ps2.setStartFlag(8);
			}
			
			// set end date on current record

			if(depDays[0] > 0) {
				setEndDate(Utils.dateFromDayCount(depDays[0]));
				setEndEst(depEst[0]);
				setEndFlag(8);
			}

			return a;

		}
		else; // still to be done

		return null;
	}


	/**
	 * 
	 * This routine inserts a new PersonStandardized Object in the Registration
	 * 
	 * 
	 */
	private ArrayList<PersonStandardized> insert1Person(){

		PersonStandardized ps = copyPersonStandardized();

		// find last occurrence of PersonDynamicStandardized for each type

		boolean [] remove = new boolean[12+1]; // elements get default value which is: false
		PersonDynamicStandardized [] pdsa = new PersonDynamicStandardized[12+1];

		for(PersonDynamicStandardized pds:  getDynamicDataOfPersonStandardized()) {

			if(pdsa[pds.getKeyToDistinguishDynamicDataType()] == null){
				pdsa[pds.getKeyToDistinguishDynamicDataType()] = pds;				
			}
			else{
				pdsa[pds.getKeyToDistinguishDynamicDataType()] = pds;				
				remove[pds.getKeyToDistinguishDynamicDataType()] = true;               				
			}
		}

		// Now move objects to new PersonStandardized object	

		for (Iterator<PersonDynamicStandardized> iter = getDynamicDataOfPersonStandardized().iterator(); iter.hasNext();) {
			PersonDynamicStandardized pds = (PersonDynamicStandardized)	iter.next();

			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK || pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST)
				continue;  // do not move arrival/departure records here


			boolean found = false;
			for(int i = 1; i <= 12; i++){
				if(pdsa[i] == pds)
					found = true;
			}

			if(found == true){
				if(remove[pds.getKeyToDistinguishDynamicDataType()] == true){					
					iter.remove();			// remove entry from original PersonStandardized
					pds.setPersonStandardizedToWhomDynamicDataRefers(ps); // point to new PersonStandardized
					ps.getDynamicDataOfPersonStandardized().add(pds); // add entry to new PersonStandardized
				}
				else{

					PersonDynamicStandardized pds1 = pds.copyPersonDynamicStandardized(); // copy entry
					pds1.setPersonStandardizedToWhomDynamicDataRefers(ps); // point to new PersonStandardized
					ps.getDynamicDataOfPersonStandardized().add(pds1); // add entry to new PersonStandardized
				}
			}
		}

		ps.setRegistrationStandardizedPersonAppearsIn(getRegistrationStandardizedPersonAppearsIn()); // point to Register		
		ArrayList<PersonStandardized> a = new ArrayList<PersonStandardized>();
		a.add(ps);
		return a;

	}
	/**
	 * 
	 * This routine inserts 2 new PersonStandardized Objects in the Registration
	 * 
	 * 
	 */
	private ArrayList<PersonStandardized> insert2Persons(){

		PersonStandardized ps1 = copyPersonStandardized();
		PersonStandardized ps2 = copyPersonStandardized();

		ArrayList<PersonDynamicStandardized> elements = new ArrayList<PersonDynamicStandardized>(); 


		for(int i = 1; i <= 12; i++){

			if(i == ConstRelations2.AANKOMST || i == ConstRelations2.VERTREK)
				continue;

			elements.clear();

			for(PersonDynamicStandardized pds:  getDynamicDataOfPersonStandardized()) {
				if(pds.getKeyToDistinguishDynamicDataType() == i)
					elements.add(pds);

			}

			switch(elements.size()){

			default:
			case 3:

				// move dd-elements 2 and 3 to the new PersonStandardized objects

				int index = getDynamicDataOfPersonStandardized().indexOf(elements.get(1));
				getDynamicDataOfPersonStandardized().remove(index);
				elements.get(1).setPersonStandardizedToWhomDynamicDataRefers(ps1); // point to new PersonStandardized
				ps1.getDynamicDataOfPersonStandardized().add(elements.get(1));

				index = getDynamicDataOfPersonStandardized().indexOf(elements.get(2));
				getDynamicDataOfPersonStandardized().remove(index);
				elements.get(2).setPersonStandardizedToWhomDynamicDataRefers(ps2); // point to new PersonStandardized
				ps2.getDynamicDataOfPersonStandardized().add(elements.get(2));

				break;


			case 2:

				// move dd-element 2 to new PersonStandardized 1, move copy of dd-element 2 to new PersonStandardized 2

				index = getDynamicDataOfPersonStandardized().indexOf(elements.get(1));
				getDynamicDataOfPersonStandardized().remove(index);
				elements.get(1).setPersonStandardizedToWhomDynamicDataRefers(ps1); // point to new PersonStandardized
				ps1.getDynamicDataOfPersonStandardized().add(elements.get(1));

				PersonDynamicStandardized pds = elements.get(1).copyPersonDynamicStandardized(); // copy entry
				pds.setPersonStandardizedToWhomDynamicDataRefers(ps2); // point to new PersonStandardized
				ps2.getDynamicDataOfPersonStandardized().add(pds);

				break;

			case 1:

				// move copy of dd-element 1 to new PersonStandardized 1, move copy of dd-element 1 to new PersonStandardized 1

				pds = elements.get(0).copyPersonDynamicStandardized(); // copy entry
				pds.setPersonStandardizedToWhomDynamicDataRefers(ps1); // point to new PersonStandardized
				ps1.getDynamicDataOfPersonStandardized().add(pds);

				pds = elements.get(0).copyPersonDynamicStandardized(); // copy entry
				pds.setPersonStandardizedToWhomDynamicDataRefers(ps2); // point to new PersonStandardized
				ps2.getDynamicDataOfPersonStandardized().add(pds);

				break;

			case 0:

				break;


			}

		}

		ps1.setRegistrationStandardizedPersonAppearsIn(getRegistrationStandardizedPersonAppearsIn()); // point to Register
		ps2.setRegistrationStandardizedPersonAppearsIn(getRegistrationStandardizedPersonAppearsIn()); // point to Register

		ArrayList<PersonStandardized> a = new ArrayList<PersonStandardized>();
		a.add(ps1);
		a.add(ps2);
		return a;

	}

	public PersonDynamicStandardized createPersonDynamicStandardized(int type){
		
		PersonDynamicStandardized pds =  PersonDynamicStandardized.PDS_Factory(type);
		
		pds.setKeyToSourceRegister(getKeyToSourceRegister());
		pds.setEntryDateHead(getEntryDateHead());
		pds.setKeyToRP(getKeyToRP());
		pds.setKeyToRegistrationPersons(getKeyToPersons());
		pds.setKeyToDistinguishDynamicDataType(type);
		pds.setDynamicDataSequenceNumber(0); 
		
		pds.setValueOfRelatedPerson(0); 
		pds.setNatureOfPerson(getNatureOfPerson());

		pds.setDateOfMutation(null);
		pds.setDateOfMutationFlag(0);
		
		pds.setVersionLastTimeOfDataEntry(getVersionLastTimeOfDataEntry());
		pds.setResearchCodeOriginal(getResearchCodeOriginal());
		pds.setVersionOriginalDataEntry(getVersionOriginalDataEntry());
		pds.setDate0(getDate0());
		
		pds.setOriginalPersonDynamic(null);
		pds.setPersonStandardizedToWhomDynamicDataRefers(this);
		
		return pds;
		
	}

	private PersonStandardized copyPersonStandardized(){

		PersonStandardized ps = new PersonStandardized();

		ps.setKeyToSourceRegister(getKeyToSourceRegister());
		ps.setEntryDateHead(getEntryDateHead());
		ps.setKeyToRP(getKeyToRP());
		ps.setKeyToPersons(getKeyToPersons());

		ps.setNatureOfPerson(getNatureOfPerson());

		ps.setDateOfRegistration(getDateOfRegistration());
		ps.setDateOfRegistrationFlag(getDateOfRegistrationFlag());

		ps.setPersonID(getPersonID());
		ps.setPersonIDFlag(getPersonIDFlag());

		ps.setStartDate(getStartDate());
		ps.setStartFlag(getStartFlag());
		ps.setStartEst(getStartEst());

		ps.setEndDate(getEndDate());
		ps.setEndFlag(getEndFlag());
		ps.setEndEst(getEndEst());

		ps.setFamilyName(getFamilyName());
		ps.setPrefixLastName(getPrefixLastName());
		ps.setTitleNoble(getTitleNoble());
		ps.setTitleElse(getTitleElse());
		ps.setFamilyNameInterpreted(getFamilyNameInterpreted());


		ps.setFirstName(getFirstName());
		ps.setFirstNameFlag(getFirstNameFlag());


		ps.setSex(getSex());		
		ps.setDateOfBirth(getDateOfBirth());
		ps.setDateOfBirthFlag(getDateOfBirthFlag());




		ps.setPlaceOfBirthID(getPlaceOfBirthID());
		ps.setPlaceOfBirthStandardized(getPlaceOfBirthStandardized());
		ps.setPlaceOfBirthFlag(getPlaceOfBirthFlag());

		ps.setDateOfDecease(getDateOfDecease());
		ps.setDateOfDeceaseFlag(getDateOfDeceaseFlag());

		ps.setPlaceOfDeceaseID(getPlaceOfDeceaseID());
		ps.setPlaceOfDeceaseStandardized(getPlaceOfDeceaseStandardized());
		ps.setPlaceOfDeceaseFlag(getPlaceOfDeceaseFlag());



		ps.setNationality(getNationality());


		ps.setLegalPlaceOfLivingID(getLegalPlaceOfLivingID());
		ps.setLegalPlaceOfLivingStandardized(getLegalPlaceOfLivingStandardized());
		ps.setLegalPlaceOfLivingFlag(getLegalPlaceOfLivingFlag());
		ps.setLegalPlaceOfLivingCode(getLegalPlaceOfLivingCode());



		ps.setRemarks(getRemarks());
		ps.setAddition(getAddition());

		ps.setVersionLastTimeOfDataEntry(getVersionLastTimeOfDataEntry());
		ps.setResearchCodeOriginal(getResearchCodeOriginal());
		ps.setVersionOriginalDataEntry(getVersionOriginalDataEntry());
		ps.setDate0(getDate0());

		ps.setOriginalPerson(getOriginalPerson());

		return(ps);


	}

	/**
	 * 
	 * This routine moves a PersonDynamicStandardized (psd) element from the current PersonStandardized to the PersonStandardized ps 
	 * 
	 * 
	 * @param psd
	 * @param ps
	 */
	
	private void move(PersonDynamicStandardized psd, PersonStandardized ps){
		
		getDynamicDataOfPersonStandardized().remove(psd);
		psd.setPersonStandardizedToWhomDynamicDataRefers(ps); // point to new PersonStandardized
		ps.getDynamicDataOfPersonStandardized().add(psd);	
		
		
	}
	
	
	/**
	 * 
	 * This routine gives a start and end date to all the dynamic data elements of a person
	 * 
	 */
	
	public void dateDynamicElements(){
		
		
		ArrayList<PersonDynamicStandardized> relationToHead  = new ArrayList<PersonDynamicStandardized>();
		ArrayList<PersonDynamicStandardized> parentsChildren = new ArrayList<PersonDynamicStandardized>();
		ArrayList<PersonDynamicStandardized> civilStatus     = new ArrayList<PersonDynamicStandardized>();
		ArrayList<PersonDynamicStandardized> religion        = new ArrayList<PersonDynamicStandardized>();
		ArrayList<PersonDynamicStandardized> occupation      = new ArrayList<PersonDynamicStandardized>();
		ArrayList<PersonDynamicStandardized> origin          = new ArrayList<PersonDynamicStandardized>();
		ArrayList<PersonDynamicStandardized> destination     = new ArrayList<PersonDynamicStandardized>();
		
		for(PersonDynamicStandardized pds: getDynamicDataOfPersonStandardized()){
			
			switch(pds.getKeyToDistinguishDynamicDataType()){
			case 11: relationToHead.add(pds);            break;
			case 12: parentsChildren.add(pds);           break;
			case  2: civilStatus.add(pds);               break;
			case  3: religion.add(pds);        		     break;
			case  5: occupation.add(pds);         		 break;
			case  6: origin.add(pds);         	    	 break;
			case  7: destination.add(pds);         		 break;
			default:			
			}
			
		}
		
		dateRelationToHead(relationToHead, civilStatus); 
		dateCivilStatus(civilStatus); 
		dateReligion(religion, civilStatus); 
		dateOccupation(occupation, civilStatus);  
		dateArrivalDepartures(origin, destination);
		
		setDynamicDataOfPersonStandardized(new ArrayList<PersonDynamicStandardized>());
		
		for(PersonDynamicStandardized pds: relationToHead)
			getDynamicDataOfPersonStandardized().add(pds);
		
		for(PersonDynamicStandardized pds: parentsChildren)
			getDynamicDataOfPersonStandardized().add(pds);
		
		for(PersonDynamicStandardized pds: civilStatus)
			getDynamicDataOfPersonStandardized().add(pds);
		
		for(PersonDynamicStandardized pds: religion)
			getDynamicDataOfPersonStandardized().add(pds);
		
		for(PersonDynamicStandardized pds: occupation)
			getDynamicDataOfPersonStandardized().add(pds);
		
		for(PersonDynamicStandardized pds: origin)
			getDynamicDataOfPersonStandardized().add(pds);
		
		for(PersonDynamicStandardized pds: destination)
			getDynamicDataOfPersonStandardized().add(pds);
		
				
	}
	
	/**
	 * 
	 * This routine gives a start date/flag/estimate and an end date/flag/estimate to all b311_st records (relation to head)for this person.
	 * It handles the situation where an explicit head is defined and creates new relationToHead records for all persons to 
	 * reflect their relation to the new head.  	  
	 * 
	 * 
	 * @param relationToHead
	 */	

	private void dateRelationToHead(ArrayList<PersonDynamicStandardized> relationToHead, ArrayList<PersonDynamicStandardized> civilStatus){

		int flag = 0; 
		boolean explicitHead = false;
		String explicitHeadDate = "00-00-0000";
		int explicitHeadRelationToOldHead = 0;
		
		PersonStandardized explicit_Head = null;
		
		// see if there is a explicit head
		
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
				if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){
					if(((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.HOOFD){
						if(((PDS_RelationToHead)pds).getDynamicData2() != null && ((PDS_RelationToHead)pds).getDynamicData2().indexOf("###$") >= 0){
							explicitHead = true;
							explicit_Head = ps;
							explicitHeadDate = ((PDS_RelationToHead)pds).getDynamicData2().substring(4).replaceAll("/", "-");
							flag = 33; // date from explicit head
							break;							
						}
					}
				}
			}	
		}
		
		if(explicitHead){
			
			if(explicitHeadDate.equals("00-00-0000")){

				// try to find marriage record of explicit head

				for(PersonDynamicStandardized pds: civilStatus){							
					if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){								
						flag = 32;
						explicitHeadDate = pds.getDateOfMutation2();
						break;		
					}
				}

			}			


			// get relation to old head

			for(PersonDynamicStandardized pds: explicit_Head.getDynamicDataOfPersonStandardized()){
				if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){
					/* Next test to prevent us from using the explicit head element that we found already above */
					if(((PDS_RelationToHead)pds).getDynamicData2() == null){
						explicitHeadRelationToOldHead = ((PDS_RelationToHead)pds).getContentOfDynamicData();
						break;
					}
				}
			}			
		}

		
		if(explicitHead){
			//System.out.println("  " + explicitHeadDate + "  " + explicitHeadRelationToOldHead);
			if(relationToHead.size() == 2){  // Explicit head, 2 records

				PersonDynamicStandardized pds1 = relationToHead.get(0);
				PersonDynamicStandardized pds2 = relationToHead.get(1);

				copyDatingInfo(this, pds1);
				if(Common1.dateIsValid(explicitHeadDate) == 0) {
					pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(explicitHeadDate) - 1));	
					pds1.setEndFlag(flag);
				}

				copyDatingInfo(this, pds2);
				if(Common1.dateIsValid(explicitHeadDate) == 0) {
					pds2.setStartDate(explicitHeadDate);
					pds2.setStartFlag(flag);	
				}
			}

			else{ 
				if(relationToHead.size() == 1){  // Explicit head, 1 record

					PersonDynamicStandardized pds1 = relationToHead.get(0);
					if(Common1.dateIsValid(getDateOfRegistration()) == 0 && Common1.dateIsValid(explicitHeadDate) == 0 &&
							Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0){
						if(Utils.dayCount(getDateOfRegistration()) > Utils.dayCount(explicitHeadDate) ||
								Utils.dayCount(getStartDate()) > Utils.dayCount(explicitHeadDate) ||
								Utils.dayCount(getEndDate())   < Utils.dayCount(explicitHeadDate)){
							copyDatingInfo(this, pds1);
						}
					}
					else{
						int oldCode = ((PDS_RelationToHead)pds1).getContentOfDynamicData();

						if(oldCode < 0){  // both the old head and the explicit head have oldCode = 1, they both don't need a second record							
							//pds1.print();
							return;
						}
						
						copyDatingInfo(this, pds1);
						if(Common1.dateIsValid(explicitHeadDate) == 0) {
							pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(explicitHeadDate) - 1));	// ,,
							pds1.setEndFlag(flag);
						}

						PersonDynamicStandardized pds2 = pds1.copyPersonDynamicStandardized();
						copyDatingInfo(this, pds2);
						if(Common1.dateIsValid(explicitHeadDate) == 0) {
							pds2.setStartDate(explicitHeadDate);
							pds2.setStartFlag(flag);
							relationToHead.add(pds2);
						}

						//((PDS_RelationToHead)pds2).setDynamicData2("-----");

						// find relation to explicit head

						int newCode = 0;
						
						if(oldCode == 80 || oldCode == -1 || oldCode == -2 ||  oldCode == -3 ||
							(oldCode >= 90 && oldCode <= 100) ||
							(oldCode >= 41 && oldCode <= 49))
							newCode = oldCode;
						else{

							switch(explicitHeadRelationToOldHead){

							case 0: newCode = ConstRelations2.newHusband[oldCode]; break;

							case ConstRelations2.ZOON:      							
								newCode = ConstRelations2.marriedSon[oldCode]; break;

							case ConstRelations2.BROER:
								newCode = ConstRelations2.brother[oldCode]; break;

							case ConstRelations2.ECHTGENOTE_HOOFD:
								newCode = ConstRelations2.widow[oldCode]; break;							

							case ConstRelations2.SCHOONZOON:
								newCode = ConstRelations2.sonInLaw[oldCode]; break;

							case ConstRelations2.DOCHTER:
								newCode = ConstRelations2.marriedSon[oldCode]; break;  // daughter and (married) son have identical table

							case ConstRelations2.SCHOONBROER_ZWAGER:
								newCode = ConstRelations2.brotherInLaw[oldCode]; break;

							case ConstRelations2.ZUSTER:
								newCode = ConstRelations2.sister[oldCode]; break;

							}

							// Expand the newCode

							if(newCode > 1000)
								newCode = newCode % 1000; // get lower part for now
							if(newCode < 0){							
								newCode = -newCode;
								if(getSex().equalsIgnoreCase("V"))
									newCode += 10; // get female equivalent;		

							}
						}
                    
						((PDS_RelationToHead)pds2).setContentOfDynamicData(newCode);

						//System.out.println("Relation to old head " + explicitHeadRelationToOldHead + " Old code = " + oldCode + " New code = " + newCode);

					}

				}
				else{
					if(relationToHead.size() == 3){ // Explicit head, 3 records
						
						PersonDynamicStandardized pds1 = relationToHead.get(0);
						PersonDynamicStandardized pds2 = relationToHead.get(1);
						PersonDynamicStandardized pds3 = relationToHead.get(2);
						
						String splitDate1 = pds2.getDateOfMutation2();
						int flag3 = 31;
						
						if(splitDate1.equals("00-00-0000")){
							
							// try to find marriage record
							
							for(PersonDynamicStandardized pds: civilStatus){							
								if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){								
									flag3 = 32;
									splitDate1 = pds.getDateOfMutation2();
									break;		
								}
							}
							
						}
						
						if(splitDate1 == null || splitDate1.equals("00-00-0000")) {
							if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0) {
							    splitDate1 = Utils.dateFromDayCount((Utils.dayCount(getEndDate()) + Utils.dayCount(getStartDate()))/2);
						
							    if(Common1.dateIsValid(splitDate1) == 0 && Common1.dateIsValid(explicitHeadDate) == 0) {	    
							    	if(Utils.dayCount(splitDate1) < Utils.dayCount(explicitHeadDate)){

							    		copyDatingInfo(this, pds1);
							    		pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(splitDate1) - 1));	
							    		pds1.setEndFlag(flag3);

							    		copyDatingInfo(this, pds2);
							    		pds2.setStartDate(splitDate1);
							    		pds2.setStartFlag(flag3);							
							    		pds2.setEndDate(Utils.dateFromDayCount(Utils.dayCount(explicitHeadDate) - 1));	
							    		pds2.setEndFlag(flag);

							    		copyDatingInfo(this, pds3);
							    		pds2.setStartDate(explicitHeadDate);
							    		pds2.setStartFlag(flag);	

							    	}
							    	else{

							    		copyDatingInfo(this, pds1);
							    		pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(explicitHeadDate) - 1));	
							    		pds1.setEndFlag(flag);

							    		copyDatingInfo(this, pds2);
							    		pds2.setStartDate(explicitHeadDate);
							    		pds2.setStartFlag(flag);							
							    		pds2.setEndDate(Utils.dateFromDayCount(Utils.dayCount(splitDate1) - 1));	
							    		pds2.setEndFlag(flag3);

							    		copyDatingInfo(this, pds3);
							    		pds2.setStartDate(splitDate1);
							    		pds2.setStartFlag(flag3);	

							    	}
							    }
							}
						}
					}
				}
			}
		}
		else{
			
			if(relationToHead.size() == 1){  // No explicit head, 1 record
				
				PersonDynamicStandardized pds = relationToHead.get(0);
				copyDatingInfo(this, pds);
				
				
			}
			else
				if(relationToHead.size() == 2){  // No explicit head, 2 records
					
					int flag1 = 0;
					String date = null;
					
					PersonDynamicStandardized pds1 = relationToHead.get(0);
					PersonDynamicStandardized pds2 = relationToHead.get(1);
					
					if(!pds2.getDateOfMutation2().equals("00-00-0000")){						
						flag1 = 31; 		
						date = pds2.getDateOfMutation2();
					}
					else{
						
						// try to find marriage record
						
						for(PersonDynamicStandardized pds: civilStatus){							
							if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){								
								flag1 = 32;
								date = pds.getDateOfMutation2();
								break;		
							}
						}
					}
					
					if(date == null || date.equals("00-00-0000")){
						if(Common1.dateIsValid(getEndDate()) == 0 && Common1.dateIsValid(getStartDate()) == 0) {
							date = Utils.dateFromDayCount((Utils.dayCount(getEndDate()) + Utils.dayCount(getStartDate()))/2);
						}
					}
					
					
					
					copyDatingInfo(this, pds1);
					if(Common1.dateIsValid(date) == 0) {
						pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(date) - 1));	
						pds1.setEndFlag(flag1);
					}
					
					copyDatingInfo(this, pds2);
					if(Common1.dateIsValid(date) == 0) {
						pds2.setStartDate(date);
						pds2.setStartFlag(flag1);
					}
					
				}
		}
		
	}
	
	/**
	 * 
	 * This routine gives a start date/flag/estimate and an end date/flag/estimate to all b32_st records (civil status)for this person.
	 * 
	 * @param civilStatus
	 */	
	
	private void dateCivilStatus(ArrayList<PersonDynamicStandardized> civilStatus){
		
		
		PersonDynamicStandardized pds1 = civilStatus.size() >= 1 ?  civilStatus.get(0) : null;
		PersonDynamicStandardized pds2 = civilStatus.size() >= 2 ?  civilStatus.get(1) : null;
		PersonDynamicStandardized pds3 = civilStatus.size() >= 3 ?  civilStatus.get(2) : null;
		
		int css = 0;
		if(pds3 != null) css = 3;
		else
			if(pds2 != null) css = 2;
			else
				if(pds1 != null) css = 1;
		
		//System.out.println("BBBNN " + getKeyToRP() + "-" + getEntryDateHead() + "-" + getKeyToSourceRegister() + "-" +  getKeyToPersons() +": "+ civilStatus.size() +  "  " + css);
		
		// for mutation dates with flag 41 or 42 replace month and/or day with Start month and/or day if Start year matches mutation year
		if(pds1 != null && Common1.dateIsValid(pds1.getDateOfMutation2()) == 0 && Common1.dateIsValid(getStartDate()) == 0) {
			if(pds1.getDateOfMutation2().substring(6,10).equals(getStartDate().substring(6,10))){
				if(pds1.getDateOfMutationFlag() == 41)
					pds1.setDateOfMutation(getStartDate());
				if(pds1.getDateOfMutationFlag() == 42)
					if(pds1.getDateOfMutation2().substring(3,4).equals(getStartDate().substring(3,4)))
						pds1.setDateOfMutation(getStartDate());
			}
		}
		
		if(pds2 != null && Common1.dateIsValid(pds2.getDateOfMutation2()) == 0 && Common1.dateIsValid(getStartDate()) == 0) {
			if(pds2.getDateOfMutation2().substring(6,10).equals(getStartDate().substring(6,10))){
				if(pds2.getDateOfMutationFlag() == 41)
					pds2.setDateOfMutation(getStartDate());
				if(pds2.getDateOfMutationFlag() == 42)
					if(pds2.getDateOfMutation2().substring(3,4).equals(getStartDate().substring(3,4)))
						pds2.setDateOfMutation(getStartDate());
			}
		}

		if(pds3 != null && Common1.dateIsValid(pds3.getDateOfMutation2()) == 0 && Common1.dateIsValid(getStartDate()) == 0) {
			if(pds3 != null &&  pds3.getDateOfMutation2().substring(6,10).equals(getStartDate().substring(6,10))){
				if(pds3.getDateOfMutationFlag() == 41)
					pds3.setDateOfMutation(getStartDate());
				if(pds3.getDateOfMutationFlag() == 42)
					if(pds3.getDateOfMutation2().substring(3,4).equals(getStartDate().substring(3,4)))
						pds3.setDateOfMutation(getStartDate());
			}
		}
		
		// replace "unknown" with data from previous registration if possible
		
		if(pds1 != null && ((PDS_CivilStatus)pds1).getContentOfDynamicData() == ConstRelations2.ONBEKEND){	
			if(Common1.dateIsValid(pds1.getDateOfMutation2()) == 0 && Common1.dateIsValid(getDateOfBirth()) == 0) {
				if(Utils.dayCount(pds1.getDateOfMutation2()) - Utils.dayCount(getDateOfBirth()) < 15 * 365){
					((PDS_CivilStatus)pds1).setContentOfDynamicData(ConstRelations2.ONGEHUWD);
				}
			}
			else{					
				int civilStatus1 = 0;
				for(RegistrationStandardized rs: getRegistrationStandardizedPersonAppearsIn().getOp().getRegistrationsStandardizedOfOP()){
					if(rs == getRegistrationStandardizedPersonAppearsIn())
						break;
					for(PersonStandardized ps: rs.getPersonsStandardizedInRegistration()){
						if(ps.getPersonID() == getPersonID()){
							for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
								if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
									civilStatus1 = ((PDS_CivilStatus)pds).getContentOfDynamicData();	
								}								
							}
						}
					}
				}
				if(civilStatus1 != 0)
					((PDS_CivilStatus)pds1).setContentOfDynamicData(civilStatus1); 						
			}
		}

		
		
		switch(css){		
		case 1:
			if(pds1 != null && Utils.dateIsValid(pds1.getDateOfMutation2()) != 0){				// Not dated
				copyDatingInfo(this, pds1);                                                     // Use b2_st dates
				((PDS_CivilStatus)pds1).setCivilStatusFlag(52); 
			}
			else{                                               
				if(pds1 != null && Common1.dateIsValid(pds1.getDateOfMutation2()) == 0  && Common1.dateIsValid(getStartDate()) == 0) {
					if(Utils.dayCount(pds1.getDateOfMutation2()) > Utils.dayCount(getStartDate())){
						PersonDynamicStandardized pdsnew = pds1.copyPersonDynamicStandardized();
						pdsnew.setDateOfMutation("00-00-0000");
						copyDatingInfo(this, pdsnew);
						pdsnew.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds1.getDateOfMutation2()) - 1));
						pdsnew.setEndFlag(32);
						pdsnew.setEndEst(100); 
						((PDS_CivilStatus)pdsnew).setCivilStatusFlag(52);
						civilStatus.add(0, pdsnew); // add before

						switch(((PDS_CivilStatus)pds1).getContentOfDynamicData()){
						case ConstRelations2.WEDUWNAAR_WEDUWE:
							((PDS_CivilStatus)pdsnew).setContentOfDynamicData(ConstRelations2.GEHUWD);
							break;
						case ConstRelations2.GESCHEIDEN:
							((PDS_CivilStatus)pdsnew).setContentOfDynamicData(ConstRelations2.GEHUWD);
							break;
						case ConstRelations2.GEHUWD:
							((PDS_CivilStatus)pdsnew).setContentOfDynamicData(ConstRelations2.ONBEKEND);

							if(pdsnew != null && Common1.dateIsValid(pdsnew.getStartDate()) == 0 && Common1.dateIsValid(getDateOfBirth()) == 0 &&
							   Utils.dayCount(pdsnew.getStartDate()) - Utils.dayCount(getDateOfBirth()) < 15 * 365){
								((PDS_CivilStatus)pdsnew).setContentOfDynamicData(ConstRelations2.ONGEHUWD);
							}
							else{					
								int civilStatus1 = 0;
								for(RegistrationStandardized rs: getRegistrationStandardizedPersonAppearsIn().getOp().getRegistrationsStandardizedOfOP()){
									if(rs == getRegistrationStandardizedPersonAppearsIn())
										break;
									for(PersonStandardized ps: rs.getPersonsStandardizedInRegistration()){
										if(ps.getPersonID() == getPersonID()){
											for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
												if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
													civilStatus1 = ((PDS_CivilStatus)pds).getContentOfDynamicData();	
												}								
											}
										}
									}
								}
								if(civilStatus1 != 0)
									((PDS_CivilStatus)pdsnew).setContentOfDynamicData(civilStatus1); 						
							}

						}

					}
				}
				
				
				pds1.setStartDate(pds1.getDateOfMutation2());
				pds1.setStartFlag(32);
				pds1.setStartEst(100); 
				((PDS_CivilStatus)pds1).setCivilStatusFlag(51);
				pds1.setEndDate(getEndDate());
				pds1.setEndFlag(getEndFlag());
				pds1.setEndEst(getEndEst());
				
				
				
			}
			
			// We check if this person's partner died during this registration
			// If so, a new pds record is made with status WEDUWE/WEDUWNAAR
			
			if(((PDS_CivilStatus)pds1).getContentOfDynamicData() == ConstRelations2.GEHUWD){
				if(((PDS_CivilStatus)pds1).getValueOfRelatedPerson() != 0) {
					for(PersonStandardized p: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()) {
						if(p.getKeyToPersons() == ((PDS_CivilStatus)pds1).getValueOfRelatedPerson()) {
							if(!p.getDateOfDecease().equals("00-00-0000")) {
								
								//System.out.println("XCVVV adding new pds for idnr = " + getKeyToRP());
								pds1.setEndDate(p.getDateOfDecease()); // status 'GEHUWD' ends when partner dies
								
								PersonDynamicStandardized pdsnew = pds1.copyPersonDynamicStandardized();
								((PDS_CivilStatus)pdsnew).setContentOfDynamicData(ConstRelations2.WEDUWNAAR_WEDUWE);
								copyDatingInfo(this, pdsnew); // This sets correct end date
								pdsnew.setDateOfMutation(Utils.dateFromDayCount(Utils.dayCount(p.getDateOfDecease()) + 1));
								pdsnew.setStartDate(pdsnew.getDateOfMutation());
								pdsnew.setStartFlag(32);
								pdsnew.setStartEst(100);
								
								((PDS_CivilStatus)pdsnew).setCivilStatusFlag(52);
								civilStatus.add(1, pdsnew); // add behind
							}							
						}
						break;
					}									
				}
			}
			
			break;
			
		case 2:
			if(Utils.dateIsValid(pds1.getDateOfMutation2()) != 0){	// Not dated
				copyDatingInfo(this, pds1);
				((PDS_CivilStatus)pds1).setCivilStatusFlag(52);  // declared 
				if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
					//System.out.println("-->" + pds2.getDateOfMutation2());
					pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
					pds1.setEndFlag(32); // date from b32_st
					pds1.setEndEst(100);				
				}
				else; // done
			}
			else{ // date1 valid
				if(pds1 != null && pds1.getDateOfMutation2() != null && getStartDate() != null && pds1.getDateOfMutation2().equals(getStartDate())){
					copyDatingInfo(this, pds1);
					((PDS_CivilStatus)pds1).setCivilStatusFlag(51);  // event
					pds1.setStartFlag(32); // date from b32_st  
					pds1.setEndEst(100);
					if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
						pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
						pds1.setEndFlag(32); // date from b32_st
						pds1.setEndEst(100);				
					}
					else; // done

				}
				else{
					if(pds1 != null && Common1.dateIsValid(pds1.getDateOfMutation2()) == 0 && Common1.dateIsValid(getStartDate()) == 0) {
						if(Utils.dayCount(pds1.getDateOfMutation2()) > Utils.dayCount(getStartDate())){ // extra record before record 1
							PersonDynamicStandardized pdsnew = pds1.copyPersonDynamicStandardized();
							pdsnew.setDateOfMutation("00-00-0000");
							copyDatingInfo(this, pdsnew);
							pdsnew.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds1.getDateOfMutation2()) - 1));
							pdsnew.setEndFlag(32);
							pdsnew.setEndEst(100); 
							((PDS_CivilStatus)pdsnew).setCivilStatusFlag(52);
							civilStatus.add(0, pdsnew); // add before

							copyDatingInfo(this, pds1);

							pds1.setStartDate(pds1.getDateOfMutation2());
							pds1.setStartFlag(32);
							pds1.setStartEst(100);

							if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
								pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
								pds1.setEndFlag(32); // date from b32_st
								pds1.setEndEst(100);				
							}
							else; // done
						}
					}
					copyDatingInfo(this, pds1);
					pds1.setStartDate(pds1.getDateOfMutation2());
					pds1.setStartFlag(32);
					pds1.setStartEst(100);

					if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
						pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
						pds1.setEndFlag(32); // date from b32_st
						pds1.setEndEst(100);				
					}
					else; // done
				}
			}
			
			// If the person has status 'GEHUWD', we check if his partner died during this registration
			// If so, the decease date is used as the endDate of the 'GEHUWD' periode
			// If the next record is 'WEDUWE/WEDUWNAAR', the startDate of this status is set to the decease date
			// If the next record is not 'WEDUWE/WEDUWNAAR, such a record is created and added to the civil status collection of this person
			
			PersonDynamicStandardized pdsnew1 = null;
			
			//System.out.println("AAAABB 2");
			
			if(((PDS_CivilStatus)pds1).getContentOfDynamicData() == ConstRelations2.GEHUWD) {
				if(((PDS_CivilStatus)pds2).getContentOfDynamicData() != ConstRelations2.WEDUWNAAR_WEDUWE){
					//System.out.println("AAAABB 3");
					if(((PDS_CivilStatus)pds1).getValueOfRelatedPerson() != 0) {
						//System.out.println("AAAABB 4");
						for(PersonStandardized p: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()) {
							//System.out.println("AAAABB 5");
							//System.out.println(p.getKeyToPersons() + " "  + ((PDS_CivilStatus)pds1).getValueOfRelatedPerson() );
							if(p.getKeyToPersons() == ((PDS_CivilStatus)pds1).getValueOfRelatedPerson()) {
								if(!p.getDateOfDecease().equals("00-00-0000")) {

									//System.out.println("AAAABB 1");
									pds1.setEndDate(p.getDateOfDecease()); // status 'GEHUWD' ends when partner dies

									pdsnew1 = pds1.copyPersonDynamicStandardized();
									((PDS_CivilStatus)pdsnew1).setContentOfDynamicData(ConstRelations2.WEDUWNAAR_WEDUWE);
									copyDatingInfo(this, pdsnew1); 

									if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
										pdsnew1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
										pdsnew1.setEndFlag(32); // date from b32_st
										pdsnew1.setEndEst(100);				
									}

									pdsnew1.setDateOfMutation(Utils.dateFromDayCount(Utils.dayCount(p.getDateOfDecease()) + 1));
									pdsnew1.setStartDate(pdsnew1.getDateOfMutation());
									pdsnew1.setStartFlag(32);
									pdsnew1.setStartEst(100);

									((PDS_CivilStatus)pdsnew1).setCivilStatusFlag(52);
									civilStatus.add(1, pdsnew1); // add behind 1
									//System.out.println("XCVVV2 adding new pds for idnr = " + getKeyToRP());
									//System.out.println(pdsnew1.getKeyToRP());
									//System.out.println(pdsnew1.getKeyToSourceRegister());
									//System.out.println(pdsnew1.getEntryDateHead());
									//System.out.println(pdsnew1.getKeyToRegistrationPersons());
									//System.out.println(pdsnew1.getKeyToDistinguishDynamicDataType());
								}	
								break;
							}
						}									
					}
				}
				else {
					if(((PDS_CivilStatus)pds1).getValueOfRelatedPerson() != 0) {
						//System.out.println("AAAABB 4");
						for(PersonStandardized p: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()) {
							//System.out.println("AAAABB 5");
							//System.out.println(p.getKeyToPersons() + " "  + ((PDS_CivilStatus)pds1).getValueOfRelatedPerson() );
							if(p.getKeyToPersons() == ((PDS_CivilStatus)pds1).getValueOfRelatedPerson()) {
								if(!p.getDateOfDecease().equals("00-00-0000")) {

									//System.out.println("AAAABB 1");
									pds1.setEndDate(p.getDateOfDecease()); // status 'GEHUWD' ends when partner dies
									pds2.setStartDate(p.getDateOfDecease());
									

								}	
								break;
							}
					
						}
					}
					
				}
			}
			
			// Second record
			
			if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
				copyDatingInfo(this, pds2);
				pds2.setStartDate(pds2.getDateOfMutation2());
				pds2.setStartFlag(51); // event
				pds2.setStartEst(100);
				if(pds1.getEndDate() == null || pds1.getEndDate().equals("0000-0000"))
					pds1.setEndDate(pds2.getStartDate());
			}
			else{
				if(pds2.getStartDate() == null || pds2.getStartDate().equals("00-00-0000")) {
					copyDatingInfo(this, pds2);
					pds2.setStartDate(pdsnew1 != null ? pdsnew1.getEndDate() : pds1.getEndDate());
					pds2.setStartFlag(pdsnew1 != null ? pdsnew1.getEndFlag() : pds1.getEndFlag());
					pds2.setStartEst (pdsnew1 != null ? pdsnew1.getEndEst()  : pds1.getEndEst());
				}

				if(((PDS_CivilStatus)pds2).getContentOfDynamicData() == ConstRelations2.GEHUWD){
					
					
					boolean married = false;
					String arrivalDate = null; 
					for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
						
						if(!ps.getSex().equals(getSex())){
							for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
							
								if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
									if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
										married = true;
									}
								}
								if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
									if(pds != null && Utils.dateIsValid(pds.getDateOfMutation2()) == 0 &&
											pds1 != null && Utils.dateIsValid(pds1.getStartDate()) == 0){
										if(Utils.dayCount(pds.getDateOfMutation2()) > Utils.dayCount(pds1.getStartDate()))
											arrivalDate = pds.getDateOfMutation2();
									}
									
								}
							}
						}
					}
					if(married == true && arrivalDate != null){
						
						if(arrivalDate != null &&  Common1.dateIsValid(arrivalDate) == 0) {
							pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(arrivalDate) - 1));
							pds1.setEndFlag(51);
							pds1.setEndEst(100);
						}
						
						pds2.setStartDate(arrivalDate);
						pds2.setStartEst(100);
						pds2.setStartFlag(51); // event
						
					}
				}
			}
			
			break;
			
		case 3:
			if(pds1 != null && Utils.dateIsValid(pds1.getDateOfMutation2()) != 0){	
				copyDatingInfo(this, pds1);
				((PDS_CivilStatus)pds1).setCivilStatusFlag(52);  // declared 
				if(pds2 != null && Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
					//System.out.println("-->" + pds2.getDateOfMutation2());
					pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
					pds1.setEndFlag(32); // date from b32_st
					pds1.setEndEst(100);				
				}
				else; // done
			}
			else{ // date1 valid
				if(pds1 != null && pds1.getDateOfMutation2() != null && getStartDate() != null && pds1.getDateOfMutation2().equals(getStartDate())){
					copyDatingInfo(this, pds1);
					((PDS_CivilStatus)pds1).setCivilStatusFlag(51);  // event
					pds1.setStartFlag(32); // date from b32_st  
					pds1.setEndEst(100);
					if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
						pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
						pds1.setEndFlag(32); // date from b32_st
						pds1.setEndEst(100);				
					}
					else; // done

				}
				else{
					if(pds1 != null && Utils.dateIsValid(pds1.getDateOfMutation2()) == 0 && Utils.dateIsValid(getStartDate()) == 0 &&
							Utils.dayCount(pds1.getDateOfMutation2()) > Utils.dayCount(getStartDate())){ // extra record before record 1
						PersonDynamicStandardized pdsnew = pds1.copyPersonDynamicStandardized();
						copyDatingInfo(this, pdsnew);
						pdsnew.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds1.getDateOfMutation2()) - 1));
						pdsnew.setEndFlag(32);
						pdsnew.setEndEst(100); 
						((PDS_CivilStatus)pdsnew).setCivilStatusFlag(52);
						civilStatus.add(0, pdsnew); // add before
						
						copyDatingInfo(this, pds1);
						
						pds1.setStartDate(pds1.getDateOfMutation2());
						pds1.setStartFlag(32);
						pds1.setStartEst(100);
						
						if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
							pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
							pds1.setEndFlag(32); // date from b32_st
							pds1.setEndEst(100);				
						}
						else; // done
					}
					copyDatingInfo(this, pds1);
					pds1.setStartDate(pds1.getDateOfMutation2());
					pds1.setStartFlag(32);
					pds1.setStartEst(100);

					if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
						pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
						pds1.setEndFlag(32); // date from b32_st
						pds1.setEndEst(100);				
					}
					else; // done
				}
			}
			
			
			// If the person has status 'GEHUWD', we check if his partner died during this registration
			// If so, the decease date is used as the endDate of the 'GEHUWD' periode
			// If the next record is 'WEDUWE/WEDUWNAAR', the startDate of this status is set to the decease date
			// If the next record is not 'WEDUWE/WEDUWNAAR, such a record is created and added to the civil status collection of this person
			
			//PersonDynamicStandardized pdsnew1 = null;
			
			//System.out.println("AAAABB 2");
			
			if(((PDS_CivilStatus)pds1).getContentOfDynamicData() == ConstRelations2.GEHUWD) {
				if(((PDS_CivilStatus)pds2).getContentOfDynamicData() != ConstRelations2.WEDUWNAAR_WEDUWE){
					//System.out.println("AAAABB 3");
					if(((PDS_CivilStatus)pds1).getValueOfRelatedPerson() != 0) {
						//System.out.println("AAAABB 4");
						for(PersonStandardized p: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()) {
							//System.out.println("AAAABB 5");
							//System.out.println(p.getKeyToPersons() + " "  + ((PDS_CivilStatus)pds1).getValueOfRelatedPerson() );
							if(p.getKeyToPersons() == ((PDS_CivilStatus)pds1).getValueOfRelatedPerson()) {
								if(!p.getDateOfDecease().equals("00-00-0000")) {

									//System.out.println("AAAABB 1");
									pds1.setEndDate(p.getDateOfDecease()); // status 'GEHUWD' ends when partner dies

									pdsnew1 = pds1.copyPersonDynamicStandardized();
									((PDS_CivilStatus)pdsnew1).setContentOfDynamicData(ConstRelations2.WEDUWNAAR_WEDUWE);
									copyDatingInfo(this, pdsnew1); 

									if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
										pdsnew1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 1));
										pdsnew1.setEndFlag(32); // date from b32_st
										pdsnew1.setEndEst(100);				
									}

									pdsnew1.setDateOfMutation(Utils.dateFromDayCount(Utils.dayCount(p.getDateOfDecease()) + 1));
									pdsnew1.setStartDate(pdsnew1.getDateOfMutation());
									pdsnew1.setStartFlag(32);
									pdsnew1.setStartEst(100);

									((PDS_CivilStatus)pdsnew1).setCivilStatusFlag(52);
									civilStatus.add(1, pdsnew1); // add behind 1
									//System.out.println("XCVVV2 adding new pds for idnr = " + getKeyToRP());
									//System.out.println(pdsnew1.getKeyToRP());
									//System.out.println(pdsnew1.getKeyToSourceRegister());
									//System.out.println(pdsnew1.getEntryDateHead());
									//System.out.println(pdsnew1.getKeyToRegistrationPersons());
									//System.out.println(pdsnew1.getKeyToDistinguishDynamicDataType());
								}	
								break;
							}
						}									
					}
				}
				else {
					if(((PDS_CivilStatus)pds1).getValueOfRelatedPerson() != 0) {
						//System.out.println("AAAABB 4");
						for(PersonStandardized p: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()) {
							//System.out.println("AAAABB 5");
							//System.out.println(p.getKeyToPersons() + " "  + ((PDS_CivilStatus)pds1).getValueOfRelatedPerson() );
							if(p.getKeyToPersons() == ((PDS_CivilStatus)pds1).getValueOfRelatedPerson()) {
								if(!p.getDateOfDecease().equals("00-00-0000")) {

									//System.out.println("AAAABB 1");
									pds1.setEndDate(p.getDateOfDecease()); // status 'GEHUWD' ends when partner dies
									pds2.setStartDate(p.getDateOfDecease());

								}	
								break;
							}
					
						}
					}
					
				}
			}

			
			
			// record 2
			
			if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
				copyDatingInfo(this, pds2);
				pds2.setStartDate(pds2.getDateOfMutation2());
				pds2.setStartFlag(51); // event
				pds2.setStartEst(100);
				if(Utils.dateIsValid(pds3.getDateOfMutation2()) == 0){
					pds2.setEndDate(Utils.dateFromDayCount(Utils.dayCount(pds3.getDateOfMutation2()) -1));
					pds2.setEndFlag(51);
					pds2.setEndEst(100);
				}
			}
			else{
				if(pds2.getStartDate() == null || pds2.getStartDate().equals("00-00-0000")) {
					copyDatingInfo(this, pds2);
					pds2.setStartDate(pds2.getEndDate());
					pds2.setStartFlag(pds2.getEndFlag());
					pds2.setStartEst(pds2.getEndEst());
				}

				if(((PDS_CivilStatus)pds2).getContentOfDynamicData() == ConstRelations2.GEHUWD){
					
					
					boolean married = false;
					String arrivalDate = null; 
					for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
						
						if(!ps.getSex().equals(getSex())){
							for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
							
								if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
									if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
										married = true;
									}
								}
								if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
									if(Utils.dateIsValid(pds.getDateOfMutation2()) == 0)
										arrivalDate = pds.getDateOfMutation2();
									
								}
							}
						}
					}
					if(married == true && arrivalDate != null){
						
						pds2.setStartDate(arrivalDate);
						pds2.setStartEst(100);
						pds2.setStartFlag(51); // event
						
					}
				}
			}
			
			// record 3
			
			if(Utils.dateIsValid(pds3.getDateOfMutation2()) == 0){
				copyDatingInfo(this, pds3);
				pds3.setStartDate(pds3.getDateOfMutation2());
				pds3.setStartFlag(51); // event
				pds3.setStartEst(100);
				if(pds2.getEndDate() == null || pds2.getEndDate().equals("00-00-0000"))
					pds2.setEndDate(pds3.getStartDate());
			}
			else{
				copyDatingInfo(this, pds3);
				pds3.setStartDate(pds2.getEndDate());
				pds3.setStartFlag(pds2.getEndFlag());
				pds3.setStartEst(pds2.getEndEst());

				if(((PDS_CivilStatus)pds3).getContentOfDynamicData() == ConstRelations2.GEHUWD){
					
					
					boolean married = false;
					String arrivalDate = null; 
					for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
						
						if(!ps.getSex().equals(getSex())){
							for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
							
								if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
									if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
										married = true;
									}
								}
								if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
									if(Utils.dateIsValid(pds.getDateOfMutation2()) == 0)
										arrivalDate = pds.getDateOfMutation2();
									
								}
							}
						}
					}
					if(married == true && arrivalDate != null){
						
						pds3.setStartDate(arrivalDate);
						pds3.setStartEst(100);
						pds3.setStartFlag(51); // event
						
					}
				}
			}
			
			break;
		}
		
	}
	/**
	 * This routine dates the religion records of a person
	 * The situation with "IDEM" or "DITTO" for religion is resolved here 
	 * 
	 * 
	 * @param religion
	 */
	private void dateReligion(ArrayList<PersonDynamicStandardized> religion, ArrayList<PersonDynamicStandardized> civilStatus){
		
		PersonDynamicStandardized pds1 = religion.size() >= 1 ?  religion.get(0) : null;
		PersonDynamicStandardized pds2 = religion.size() >= 2 ?  religion.get(1) : null;
		PersonDynamicStandardized pds3 = religion.size() >= 3 ?  religion.get(2) : null;
		
		final String idem = "Idem";
		
		// find foregoing person with a least one other value than "Idem"
		
		PersonStandardized psForegoing = null;
		for(PersonStandardized ps: getRegistrationStandardizedPersonAppearsIn().getPersonsStandardizedInRegistration()){
			if(ps == this)
				break;
			for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
				if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.GODSDIENST){
					if(((PDS_Religion) pds).getReligionStandardized() != null && !((PDS_Religion)pds).getReligionStandardized().equalsIgnoreCase(idem)){
						psForegoing = ps;
					}
				}
			}
		}
		
		
		if(getOriginalPerson().getIsHead()){
			switch(religion.size()){
			
			case 1:
				copyDatingInfo(this, pds1);
				break;
				
			case 2:
				
				// split on marriage date
				
				String date = null;
				for(PersonDynamicStandardized pds: civilStatus){
					
					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
							if(Utils.dateIsValid(pds.getDateOfMutation2()) == 0)
								date = pds.getDateOfMutation2();
							
						}
					}
				}
				//System.out.println("date = " + date);
				if(date == null || Utils.dateIsValid(date) != 0)
					if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0)
						date = Utils.dateFromDayCount((Utils.dayCount(getStartDate()) + Utils.dayCount(getEndDate())) / 2);

				//System.out.println("date = " + date);

				//System.out.println();

				copyDatingInfo(this, pds1);
				copyDatingInfo(this, pds2);

				if(Utils.dateIsValid(date) == 0) {
					pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(date) - 1));
					pds1.setEndFlag(77); // marriage end date
					pds1.setEndEst(100);

					pds2.setStartDate(date);
					pds2.setStartFlag(77); // marriage start date
					pds2.setStartEst(100);
				}
				
				break;
				
			case 3:
				
				// split on marriage date
				
				String date1 = null;
				String date2 = null;
				for(PersonDynamicStandardized pds: civilStatus){
					
					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
							if(date1 == null)
								date1 = pds.getDateOfMutation2();
							else
								if(date2 == null)
									date2 = pds.getDateOfMutation2();
								else
									break;
							
						}
					}
				}
				
				if(date1 == null || Utils.dateIsValid(date1) != 0){
					if(date2 == null || Utils.dateIsValid(date2) != 0){
						if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0) {
							date1 = Utils.dateFromDayCount((Utils.dayCount(getStartDate()) + Utils.dayCount(getEndDate())) / 3);
							date2 = Utils.dateFromDayCount(( 2 * Utils.dayCount(getStartDate()) + Utils.dayCount(getEndDate())) / 3);	
						}
					}
					else
						if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0)
							date1 = Utils.dateFromDayCount((Utils.dayCount(getStartDate()) + Utils.dayCount(date2)) / 2);
				}
				else{
					if(date2 == null || Utils.dateIsValid(date2) != 0){
						date2 = Utils.dateFromDayCount((Utils.dayCount(getEndDate()) + Utils.dayCount(date1)) / 2);
						
					}
					else; // done
				}
				
				copyDatingInfo(this, pds1);
				copyDatingInfo(this, pds2);
				copyDatingInfo(this, pds3);

				if(Utils.dateIsValid(date1) == 0) {

					pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(date1) - 1));
					pds1.setEndFlag(77); // marriage end date
					pds1.setEndEst(100);

					pds2.setStartDate(date1);
					pds2.setStartFlag(77); // marriage start date
					pds2.setStartEst(100);
				}

				if(Utils.dateIsValid(date2) != 0) {


					pds2.setEndDate(Utils.dateFromDayCount(Utils.dayCount(date2) - 1));
					pds2.setEndFlag(77); // marriage end date
					pds2.setEndEst(100);

					pds3.setStartDate(date2);
					pds3.setStartFlag(77); // marriage start date
					pds3.setStartEst(100);
				}


			
			}
			
		}
		else{ // not head
			switch(religion.size()){

			case 1:
				if((((PDS_Religion)pds1).getReligionStandardized()) != null  &&    !((PDS_Religion)pds1).getReligionStandardized().equalsIgnoreCase(idem)){
					copyDatingInfo(this, pds1);
				}
				else{
					if(psForegoing != null){

						copyDatingInfo(this, pds1);
						ArrayList<PersonDynamicStandardized> a = splitDitto(psForegoing, pds1, null);
						//System.out.println("--" + a.size());
						if(a.size() > 0)
							religion.remove(pds1);
						int i = 0;
						for(PersonDynamicStandardized pds: a){
							//pds.print();
							religion.add(i++, pds);
						}
					}
				}

				break;
				
				
			case 2:
				// split on marriage date
				
				String date = null;
				for(PersonDynamicStandardized pds: civilStatus){
					
					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
							date = pds.getDateOfMutation2();
							
						}
					}
				}
				if(date == null || Utils.dateIsValid(date) != 0)
					if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0)
						date = Utils.dateFromDayCount(Utils.dayCount(getStartDate()) + Utils.dayCount(getEndDate()) / 2);

				
				copyDatingInfo(this, pds1);
				copyDatingInfo(this, pds2);

				if(Utils.dateIsValid(date) == 0) {
					pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(date) - 1));
					pds1.setEndFlag(77); // marriage end date
					pds1.setEndEst(100);

					pds2.setStartDate(date);
					pds2.setStartFlag(77); // marriage start date
					pds2.setStartEst(100);
				}

				
				if(((PDS_Religion)pds1).getReligionStandardized() != null && ((PDS_Religion)pds1).getReligionStandardized().equalsIgnoreCase(idem)){
					if(psForegoing != null){
						ArrayList<PersonDynamicStandardized> a = splitDitto(psForegoing, pds1, null);
						religion.remove(pds1);
						int i = 0;
						for(PersonDynamicStandardized pds: a){
							religion.add(i++, pds);
						}
					}
				}				
					
				break;
					
				
			case 3:
				// split on marriage date
				
				String date1 = null;
				String date2 = null;
				for(PersonDynamicStandardized pds: civilStatus){
					
					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
							if(date1 == null)
								date1 = pds.getDateOfMutation2();
							else
								if(date2 == null)
									date2 = pds.getDateOfMutation2();
								else
									break;
							
						}
					}
				}
				
				if(date1 == null || Utils.dateIsValid(date1) != 0){
					if(date2 == null || Utils.dateIsValid(date2) != 0){
						if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0) {

							date1 = Utils.dateFromDayCount(Utils.dayCount(getStartDate()) + Utils.dayCount(getEndDate()) / 3);
							date2 = Utils.dateFromDayCount(( 2 * Utils.dayCount(getStartDate()) + Utils.dayCount(getEndDate())) / 3);
						}
					}
					else
						if(Common1.dateIsValid(getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0)

							date1 = Utils.dateFromDayCount(Utils.dayCount(getStartDate()) + Utils.dayCount(date2) / 2);
				}
				else{
					if(date2 == null || Utils.dateIsValid(date2) != 0){
						if(Common1.dateIsValid(getEndDate()) == 0)

							date2 = Utils.dateFromDayCount(Utils.dayCount(getEndDate()) + Utils.dayCount(date1) / 2);

					}
					else; // done
				}
				
				copyDatingInfo(this, pds1);
				copyDatingInfo(this, pds2);
				copyDatingInfo(this, pds3);
				
				if(Utils.dateIsValid(date1) == 0) {
					pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(date1) - 1));
					pds1.setEndFlag(77); // marriage end date
					pds1.setEndEst(100);

					pds2.setStartDate(date1);
					pds2.setStartFlag(77); // marriage start date
					pds2.setStartEst(100);
				}


				if(Utils.dateIsValid(date2) == 0) {
					pds2.setEndDate(Utils.dateFromDayCount(Utils.dayCount(date2) - 1));
					pds2.setEndFlag(77); // marriage end date
					pds2.setEndEst(100);

					pds3.setStartDate(date2);
					pds3.setStartFlag(77); // marriage start date
					pds3.setStartEst(100);
				}
				
				int i = 0;

				if(((PDS_Religion)pds1).getReligionStandardized() != null && ((PDS_Religion)pds1).getReligionStandardized().equalsIgnoreCase(idem)){
					if(psForegoing != null){
						ArrayList<PersonDynamicStandardized> a = splitDitto(psForegoing, pds1, pds2);
						religion.remove(pds1);
						for(PersonDynamicStandardized pds: a){
							religion.add(i++, pds);
						}
					}
				}				

				if(((PDS_Religion)pds2).getReligionStandardized() != null && ((PDS_Religion)pds2).getReligionStandardized().equalsIgnoreCase(idem)){
					if(psForegoing != null){

						ArrayList<PersonDynamicStandardized> a = splitDitto(psForegoing, pds2, pds3);
						religion.remove(pds2);
						for(PersonDynamicStandardized pds: a){
							religion.add(i++, pds);
						}
					}
				}				

				if(((PDS_Religion)pds3).getReligionStandardized() != null && ((PDS_Religion)pds3).getReligionStandardized().equalsIgnoreCase(idem)){
					if(psForegoing != null){

						ArrayList<PersonDynamicStandardized> a = splitDitto(psForegoing, pds3, null);
						religion.remove(pds3);
						for(PersonDynamicStandardized pds: a){
							religion.add(i++, pds);
						}
					}
				}				
			}
			
		}
	}
	/**
	 * 
	 * This routine takes a pds as input (pdsDitto) and produces one or more output pds's that are copied from the Person psForegoing
	 * These new pds's cover the time range of the "ditto" record
	 * 
	 * psForegoing       AAAAABBBBBBCCCCCCCC
	 * pdsDitto              DDDD
	 * 
	 * Output                A
	 *                        BBB
	 * 
	 * @param psForegoing
	 * @param pdsDitto
	 * @param pdsNext
	 * @return
	 */
	
	private ArrayList<PersonDynamicStandardized> splitDitto(PersonStandardized psForegoing, PersonDynamicStandardized pdsDitto, PersonDynamicStandardized pdsNext){
		
		
		ArrayList<PersonDynamicStandardized> apds = new ArrayList<PersonDynamicStandardized>();
		
		String startDate = pdsDitto.getStartDate();
		String endDate   = pdsNext != null ? pdsNext.getStartDate() : getEndDate();
		
		//System.out.println("=================================================");
		//if(psForegoing != null) psForegoing.print(); 
		//else  System.out.printf("psForgoing = null");
		//
		//System.out.println("=================================================");
		//
		//if(pdsDitto != null) pdsDitto.print(); 
		//else  System.out.println("pdsDitto = null");
		//
		//if(pdsNext != null) pdsNext.print(); 
		//else  System.out.println("pdsNext  = null");
        //
		//System.out.println("=================================================");

		
		//System.out.println(startDate + "  " + endDate);
		
		//int n = 1;
		
		//System.out.println(psForegoing);
		for(PersonDynamicStandardized pds: psForegoing.getDynamicDataOfPersonStandardized()){
			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.GODSDIENST){
				
				String startDate1 = pds.getStartDate();
				String endDate1   = pds.getEndDate();
				//System.out.println("  " + startDate1 + "  " + endDate1);

				if(Utils.dateIsValid(endDate1) == 0 && Utils.dateIsValid(startDate) == 0 &&
						Utils.dateIsValid(startDate1) == 0 && Utils.dateIsValid(endDate)  == 0 && 
						Utils.dayCount(endDate1) > Utils.dayCount(startDate) && Utils.dayCount(startDate1) < Utils.dayCount(endDate)){
					
					//System.out.println("In loop");
					
					PersonDynamicStandardized pdsNew = pdsDitto.copyPersonDynamicStandardized();
					((PDS_Religion)pdsNew).setReligionStandardized(((PDS_Religion)pds).getReligionStandardized());
					//((PDS_Religion)pdsNew).setReligionStandardized("Scientology " + n++);
					((PDS_Religion)pdsNew).setReligionID(((PDS_Religion)pds).getReligionID());
					((PDS_Religion)pdsNew).setReligionFlag(((PDS_Religion)pds).getReligionFlag());
					apds.add(pdsNew);
					
					if(Common1.dateIsValid(startDate1) == 0 && Common1.dateIsValid(startDate) == 0 &&
							Utils.dayCount(startDate1) < Utils.dayCount(startDate)){
						pdsNew.setStartDate(pdsDitto.getStartDate());
						pdsNew.setStartFlag(pdsDitto.getStartFlag());
						pdsNew.setStartEst(pdsDitto.getStartEst());						
					}
					else{
						pdsNew.setStartDate(pds.getStartDate());
						pdsNew.setStartFlag(pds.getStartFlag());
						pdsNew.setStartEst(pds.getStartEst());						
					}
					if(Common1.dateIsValid(endDate1) == 0 && Common1.dateIsValid(endDate) == 0 &&
							Utils.dayCount(endDate1) > Utils.dayCount(endDate)){
						pdsNew.setEndDate(pdsDitto.getEndDate());
						pdsNew.setEndFlag(pdsDitto.getEndFlag());
						pdsNew.setEndEst(pdsDitto.getEndEst());						
					
					}
					else{
						pdsNew.setEndDate(pds.getEndDate());
						pdsNew.setEndFlag(pds.getEndFlag());
						pdsNew.setEndEst(pds.getEndEst());						
						
					}
				}
			}
		}
		
		return apds;
	}
	/**
	 * This routine dates the occupational title records of a person
	 * 
	 * 
	 * @param occupation
	 * @param civilStatus
	 */
	private void dateOccupation(ArrayList<PersonDynamicStandardized> occupation, ArrayList<PersonDynamicStandardized> civilStatus){

		PersonDynamicStandardized pds1 = occupation.size() >= 1 ?  occupation.get(0) : null;
		PersonDynamicStandardized pds2 = occupation.size() >= 2 ?  occupation.get(1) : null;
		PersonDynamicStandardized pds3 = occupation.size() >= 3 ?  occupation.get(2) : null;
		PersonDynamicStandardized pds4 = occupation.size() >= 4 ?  occupation.get(3) : null;	


		String profession = "Geen beroep";
		int    professionID = 0;

		Ref_Profession ref_profession = Ref.getProfession(profession);
		if(ref_profession != null){
			profession   = ref_profession.getProfession();
			professionID = ref_profession.getProfessionID();	    	
		}


		switch(occupation.size()){

		case 0:

			if(Common1.dateIsValid(getDateOfBirth()) == 0) {
				if(Utils.dayCount(getDateOfBirth()) + 15 * 365 >= Utils.dayCount(getStartDate()) && Utils.dayCount(getDateOfBirth()) + 15 * 365 <= Utils.dayCount(getEndDate())){
					PersonDynamicStandardized pds = createPersonDynamicStandardized(ConstRelations2.BEROEPSTITEL);
					((PDS_OccupationalTitle)pds).setOccupationStandardized(profession);
					((PDS_OccupationalTitle)pds).setOccupationID(professionID);
					if(Utils.dateIsValid(getDateOfBirth()) != 0) {
						pds.setStartDate(Utils.dateFromDayCount(Utils.dayCount(getDateOfBirth()) + 15 * 365));
						pds.setStartFlag(51);
						pds.setStartEst(100);
					}
					pds.setEndDate(getEndDate());
					pds.setEndFlag(getEndFlag());
					pds.setEndEst(getEndEst());

					civilStatus.add(pds);

				}
			}
			break;

		case 1:

			if(Common1.dateIsValid(getDateOfBirth()) == 0) {
				int flag = 0;
				String startDate0 = null;
				if(Utils.dateIsValid(pds1.getDateOfMutation2()) == 0){
					//System.out.println("ABBC 6 " + getDateOfBirth() + "  " + getEndDate());
					if(Utils.dateIsValid(getEndDate()) == 0 && Utils.dayCount(getDateOfBirth()) + 15 * 365 >= Utils.dayCount(getEndDate()))
						startDate0 = Utils.dateFromDayCount(Utils.dayCount(getEndDate()) - 1);
					else{
						if(Utils.dateIsValid(getStartDate()) == 0 && Utils.dayCount(getDateOfBirth()) + 15 * 365 >= Utils.dayCount(getStartDate()))
							startDate0 = Utils.dateFromDayCount(Utils.dayCount(getDateOfBirth()) + 15 * 365);
						else 
							startDate0 = getStartDate();
						flag = 51;
					}
				}
				else{
					startDate0 = pds1.getDateOfMutation2();
					
					if(Utils.dateIsValid(pds1.getDateOfMutation2())  != 0 &&
							Utils.dayCount(getDateOfBirth()) + 12 * 365 > Utils.dayCount(pds1.getDateOfMutation2()))
						startDate0 = Utils.dateFromDayCount(Utils.dayCount(getDateOfBirth()) + 12 * 365);
					flag = 52;
				}


				pds1.setStartDate(startDate0);
				pds1.setStartFlag(flag);
				pds1.setStartEst(100);
				pds1.setEndDate(getEndDate());
				pds1.setEndFlag(getEndFlag());
				pds1.setEndEst(getEndEst());

			}

			break;


		case 2:

			String startDate0 = null;
			String startDate1 = null;
			int flag = 0;
			
			if(Common1.dateIsValid(getDateOfBirth()) == 0) {
				if(Utils.dateIsValid(pds1.getDateOfMutation2()) != 0){
					if(Common1.dateIsValid(getEndDate()) == 0 &&							
							Utils.dayCount(getDateOfBirth()) + 15 * 365 >= Utils.dayCount(getEndDate()))
						startDate0 = Utils.dateFromDayCount(Utils.dayCount(getEndDate()) - 1);
					else{
						if(Common1.dateIsValid(getStartDate()) == 0) {
							if(Utils.dayCount(getDateOfBirth()) + 15 * 365 >= Utils.dayCount(getStartDate()))
								startDate0 = Utils.dateFromDayCount(Utils.dayCount(getDateOfBirth()) + 15 * 365);
							else 
								startDate0 = getStartDate();
						}
					}
					flag = 51;
				}
				else{
					startDate0 = pds1.getDateOfMutation2();
					if(Utils.dayCount(getDateOfBirth()) + 12 * 365 > Utils.dayCount(pds1.getDateOfMutation2()))
						startDate0 = Utils.dateFromDayCount(Utils.dayCount(getDateOfBirth()) + 12 * 365);
					flag = 52;
				}

				if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
					if(Utils.dayCount(startDate0) > Utils.dayCount(pds2.getDateOfMutation2()))
						startDate0 = Utils.dateFromDayCount(Utils.dayCount(pds2.getDateOfMutation2()) - 365);

				}

				pds1.setStartDate(startDate0);
				pds1.setStartFlag(flag);
				pds1.setStartEst(100);

				if(Utils.dateIsValid(pds2.getDateOfMutation2()) == 0){
					startDate1 = pds2.getDateOfMutation2();
					flag = pds2.getDateOfMutationFlag();
				}
				else{
					for(PersonDynamicStandardized pds: civilStatus){
						if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
							if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
								if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0 && Common1.dateIsValid(pds1.getStartDate()) == 0 &&
										Utils.dayCount(pds.getDateOfMutation2()) > Utils.dayCount(pds1.getStartDate())){
									startDate1 = pds.getDateOfMutation2();
									flag = 53;
									break;
								}
							}
						}
					}
					if(startDate1 == null){
						if(Common1.dateIsValid(pds1.getStartDate()) == 0 && Common1.dateIsValid(getEndDate()) == 0) {
							startDate1 = Utils.dateFromDayCount((Utils.dayCount(pds1.getStartDate()) + Utils.dayCount(getEndDate())) / 2);
							flag = 54;
						}
					}
				}

				//System.out.println(startDate1);

				if(Common1.dateIsValid(startDate1) == 0) {
					pds1.setEndDate(Utils.dateFromDayCount(Utils.dayCount(startDate1) - 1));
					pds1.setEndFlag(flag);
					pds1.setEndEst(100);

					pds2.setStartDate(startDate1);
					pds2.setStartFlag(flag);
					pds2.setStartEst(100);
				}

				
				pds2.setEndDate(getEndDate());
				pds2.setEndFlag(getEndFlag());
				pds2.setEndEst(getEndEst());
			}

			break;

		}

	}	

	/**
	 * 
	 * This routine dates the arrival and departure records of a person
	 * 
	 * @param origin
	 * @param destination
	 */
	
	private void dateArrivalDepartures(ArrayList<PersonDynamicStandardized> origin, ArrayList<PersonDynamicStandardized> destination){
		
		for(PersonDynamicStandardized pds: origin){
			if(Utils.dateIsValid(pds.getDateOfMutation2()) == 0){
				pds.setStartDate(pds.getDateOfMutation2());
				pds.setStartEst(100);
				pds.setStartFlag(pds.getDateOfMutationFlag());			
			}
			else{
				pds.setStartDate(getStartDate());
				pds.setStartFlag(getStartFlag());
				pds.setStartEst(getStartEst());
			}
		}
		
		for(PersonDynamicStandardized pds: destination){
			if(Utils.dateIsValid(pds.getDateOfMutation2()) == 0){
				pds.setStartDate(pds.getDateOfMutation2());
				pds.setStartEst(100);
				pds.setStartFlag(pds.getDateOfMutationFlag());			
			}
			else{
				pds.setStartDate(getStartDate());
				pds.setStartFlag(getStartFlag());
				pds.setStartEst(getStartEst());
			}
		}
	}

	private void copyDatingInfo(PersonStandardized ps, PersonDynamicStandardized pds){

		pds.setStartDate(ps.getStartDate());
		pds.setStartFlag(ps.getStartFlag());
		pds.setStartEst(ps.getStartEst());
		
		pds.setEndDate(ps.getEndDate());
		pds.setEndFlag(ps.getEndFlag());
		pds.setEndEst(ps.getEndEst());

		
	}
	
	/**
	 * 
	 * This routine truncates fields that are too long for the corresponding database columns
	 * 
	 */
	
	
	public void truncate(){	
		
		String field = getFamilyName();
		int allowedSize = ConstTables.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "FAMILYNAME", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setFamilyName(field);
		}
			
		field = getPrefixLastName();
		allowedSize = ConstTables.Smallstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "PREFIX_FAMILYNAME", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setPrefixLastName(field);
		}
			
		field = getTitleNoble();
		allowedSize = ConstTables.Smallstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "TITLE_NOBLE", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setTitleNoble(field);
		}
			
		field = getTitleElse();
		allowedSize = ConstTables.Smallstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "TITLE_ELSE", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setTitleElse(field);
		}
			
		field = getFirstName();
		allowedSize = ConstTables.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "FIRSTNAME", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setFirstName(field);
		}
			
		field = getPlaceOfBirthStandardized();
		allowedSize = ConstTables.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "BIRTH_LOCALITY_ST", "" +allowedSize);
			field = field.substring(0, allowedSize);
			setPlaceOfBirthStandardized(field);
		}
			
		field = getPlaceOfDeceaseStandardized();
		allowedSize = ConstTables.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "DEATH_LOCALITY_ST", "" +allowedSize);
			field = field.substring(0, allowedSize);
			setPlaceOfDeceaseStandardized(field);
		}
			
		field = getNationality();
		allowedSize = ConstTables.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "NATIONALITY", "" +allowedSize);
			field = field.substring(0, allowedSize);
			setNationality(field);
		}
			
		field = getLegalPlaceOfLivingStandardized();
		allowedSize = ConstTables.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "LEGAL_LIVPL_ST", "" +allowedSize);
			field = field.substring(0, allowedSize);
			setLegalPlaceOfLivingStandardized(field);
		}
			
		field = getRemarks();
		allowedSize = ConstTables.XBigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "REMARKS", "" +allowedSize);
			field = field.substring(0, allowedSize);
			setRemarks(field);
		}
			
		field = getAddition();
		allowedSize = ConstTables.XBigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B2_ST", "ADDITION", "" +allowedSize);
			field = field.substring(0, allowedSize);
			setAddition(field);
		}
			
		
		for(PersonDynamicStandardized pd: getDynamicDataOfPersonStandardized()){
			pd.truncate();
		}

	}
	
	/**
	 * 
	 * This routine writes the standardized person object to the database
	 * Also, for all standardized dynamic elements are saved in the database by calling pd.write()
	 * 
	 * 
	 */
	
	public void write(){
		
		Utils.persist(this);
		
		for(PersonDynamicStandardized pd: getDynamicDataOfPersonStandardized()){
			pd.write();
		}
		for(PersonDynamicStandardized pd: getToAll()){
			pd.write();
		}

		
		
	}

	
	private void message(String number, String... fills){
		
		Message m = new Message(number);
		
		m.setKeyToRP(getKeyToRP());
		
		m.setKeyToSourceRegister(getKeyToSourceRegister());
		
		m.setDayEntryHead((new Integer(getRegistrationStandardizedPersonAppearsIn().getEntryDateHead().substring(0,2)).intValue()));
		m.setMonthEntryHead((new Integer(getRegistrationStandardizedPersonAppearsIn().getEntryDateHead().substring(3,5)).intValue()));
		m.setYearEntryHead((new Integer(getRegistrationStandardizedPersonAppearsIn().getEntryDateHead().substring(6,10)).intValue()));
		
		
		m.setDayEntryRP((new Integer(getRegistrationStandardizedPersonAppearsIn().getEntryDateRP().substring(0,2)).intValue()));
		m.setMonthEntryRP((new Integer(getRegistrationStandardizedPersonAppearsIn().getEntryDateRP().substring(3,5)).intValue()));
		m.setYearEntryRP((new Integer(getRegistrationStandardizedPersonAppearsIn().getEntryDateRP().substring(6,10)).intValue()));
		
		m.setKeyToRegistrationPersons(getKeyToPersons());
		m.setNatureOfPerson(getNatureOfPerson());
		
		m.save(fills); 
		
		
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

	public void setEntryDateHead(String dateEntryHeadOfHousehold) {
		this.entryDateHead = dateEntryHeadOfHousehold;
	}

	public int getKeyToRP() {
		return keyToRP;
	}

	public void setKeyToRP(int keyToHSNResearchPerson) {
		this.keyToRP = keyToHSNResearchPerson;
	}

	public int getKeyToPersons() {
		return keyToPersons;
	}

	public void setKeyToPersons(
			int keyToDistinguishPersonsWithinOneRegistration) {
		this.keyToPersons = keyToDistinguishPersonsWithinOneRegistration;
	}

	public int getNatureOfPerson() {
		return natureOfPerson;
	}

	public void setNatureOfPerson(int natureOfPerson) {
		this.natureOfPerson = natureOfPerson;
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

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String lastName) {
		this.familyName = lastName;
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

	public void setFamilyNameInterpreted(int lastNameInterpreted) {
		this.familyNameInterpreted = lastNameInterpreted;
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

	public void setLegalPlaceOfLivingCode(String legalPlaceOfLivingInCodes) {
		this.legalPlaceOfLivingCode = legalPlaceOfLivingInCodes;
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

	public void setDate0(String date) {
		this.date0 = date;
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



	public Person getOriginalPerson() {
		return originalPerson;
	}



	public void setOriginalPerson(Person originalPerson) {
		this.originalPerson = originalPerson;
	}



	public ArrayList<PersonDynamicStandardized> getDynamicDataOfPersonStandardized() {
		return dynamicDataOfPersonStandardized;
	}



	public void setDynamicDataOfPersonStandardized(
			ArrayList<PersonDynamicStandardized> dynamicDataOfPersonStandardized) {
		this.dynamicDataOfPersonStandardized = dynamicDataOfPersonStandardized;
	}



	public RegistrationStandardized getRegistrationStandardizedPersonAppearsIn() {
		return registrationStandardizedPersonAppearsIn;
	}



	public void setRegistrationStandardizedPersonAppearsIn(
			RegistrationStandardized registrationStandardizedPersonAppearsIn) {
		this.registrationStandardizedPersonAppearsIn = registrationStandardizedPersonAppearsIn;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		//System.out.println("ABBC startDate =  " + startDate + ", caller = " + Thread.currentThread().getStackTrace()[2].getMethodName() + 
		//		",  lineNumber  = " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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



	public String getMaxStartDate() {
		return maxStartDate;
	}



	public void setMaxStartDate(String maxStartDate) {
		this.maxStartDate = maxStartDate;
	}



	public String getMinStartDate() {
		return minStartDate;
	}



	public void setMinStartDate(String minStartDate) {
		this.minStartDate = minStartDate;
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



	public ArrayList<PersonDynamicStandardized> getToAll() {
		return toAll;
	}



	public void setToAll(ArrayList<PersonDynamicStandardized> toAll) {
		this.toAll = toAll;
	}



	
}
