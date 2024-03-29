package nl.iisg.convertPK;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.iisg.hsncommon.Common1;

@Entity
@Table(name="pkknd") 
public class PkKnd {
	
 @Id @Column(name="IDNR")      private int     idnr; 		  // ID Number
    @Column(name="IDNRP")      private int     idnrp;  		  // IDNR partner
    
    @Column(name="GAKTNRP")    private String  gaktnrp;       // Number or code of the birth certificate on which the Personal Card has been based 
    @Column(name="PKTYPE")     private int     pktype;        // Type of PK
    
    @Column(name="EINDAGPK")   private int     eindagpk;      // Day of end of the PK, when the observation of the PK-holder does not end with his or her death
    @Column(name="EINMNDPK")   private int     einmndpk;      // Month
    @Column(name="EINJARPK")   private int     einjarpk;      // Year
    
    @Column(name="CTRDGP")     private int     ctrdgp;        // Day of the check of the personal card with the information on the PK and check sign of the civil servant.
    @Column(name="CTRMDP")     private int     ctrmdp;        // Month of the check of the personal card with the information on the PK and check sign of the civil servant.
    @Column(name="CTRJRP")     private int     ctrjrp;        // Year of the check of the personal card with the information on the PK and check sign of the civil servant.

    @Column(name="CTRPARP")    private String  ctrparp;       // check sign of the civil servant (j/n)
    @Column(name="GZNVRMP")    private String  gznvrmp;       // Structure of the family, indicated by who is the head of the family 

    // Information about the PK-Holder
    
    @Column(name="ANMPERP")    private String  anmperp;       // Last name PK-Holder 
    @Column(name="TUSPERP")    private String  tusperp;       // Prefix Last Name PK-Holder 
    @Column(name="VNM1PERP")   private String  vnm1perp;      // First first name of PK-Holder 
    @Column(name="VNM2PERP")   private String  vnm2perp;      // Second first name of PK-Holder 
    @Column(name="VNM3PERP")   private String  vnm3perp;      // Third (and fourth, fifth etc.) first name of PK-Holder 
    
    @Column(name="GDGPERP")    private int 	   gdgperp;       // Birth day of PK-Holder
    @Column(name="GMDPERP")    private int     gmdperp;       // Birth month of PK-Holder
    @Column(name="GJRPERP")    private int     gjrperp;       // Birth year of PK-Holder
    
    @Column(name="GDGPERPCR")  private int     gdgperpcr;     // Birth day of PK-Holder after correction
    @Column(name="GMDPERPCR")  private int     gmdperpcr;     // Birth month of PK-Holder after correction
    @Column(name="GJRPERPCR")  private int     gjrperpcr;     // Birth year of PK-Holder after correction
    
    @Column(name="GPLPERP")    private String  gplperp;       // Birth place of PK-Holder
    @Column(name="NATPERP")    private String  natperp;       // Nationality of PK-Holder
    @Column(name="GDSPERP")    private String  gdsperp;       // Religion of PK-Holder
    @Column(name="GSLPERP")    private String  gslperp;       // Gender of PK-Holder
    
    // Information about the Father of the PK-Holder

    @Column(name="ANMVDRP")    private String  anmvdrp;       // Last name Father PK-Holder 
    @Column(name="TUSVDRP")    private String  tusvdrp;       // Prefix Last name Father PK-Holder 
    @Column(name="VNM1VDRP")   private String  vnm1vdrp;      // First first name Father PK-Holder 
    @Column(name="VNM2VDRP")   private String  vnm2vdrp;      // Second firstname Father PK-Holder 
    @Column(name="VNM3VDRP")   private String  vnm3vdrp;      // Third (and fourth, fifth etc.) first name Father of PK-Holder 
    
    @Column(name="GDGVDRP")    private int	   gdgvdrp;       // Birth day of Father PK-Holder
    @Column(name="GMDVDRP")    private int     gmdvdrp;       // Birth month of Father PK-Holder
    @Column(name="GJRVDRP")    private int     gjrvdrp;       // Birth year of Father PK-Holder
    
    @Column(name="GDGVDRPCR")  private int     gdgvdrpcr;     // Birth day of Father PK-Holder after correction
    @Column(name="GMDVDRPCR")  private int     gmdvdrpcr;     // Birth month of Father PK-Holder after correction
    @Column(name="GJRVDRPCR")  private int     gjrvdrpcr;     // Birth year of Father PK-Holder after correction
    
    @Column(name="GPLVDRP")    private String  gplvdrp;       // Birth place of Father PK-Holder
 
    // Information about the Mother of the PK-Holder

    @Column(name="ANMMDRP")    private String  anmmdrp;       // Last name Mother PK-Holder 
    @Column(name="TUSMDRP")    private String  tusmdrp;       // Prefix Last name Mother PK-Holder 
    @Column(name="VNM1MDRP")   private String  vnm1mdrp;      // First first name Mother PK-Holder 
    @Column(name="VNM2MDRP")   private String  vnm2mdrp;      // Second firstname Mother PK-Holder 
    @Column(name="VNM3MDRP")   private String  vnm3mdrp;      // Third (and fourth, fifth etc.) first name Mother of PK-Holder 
    
    @Column(name="GDGMDRP")    private int 	   gdgmdrp;       // Birth day of Mother PK-Holder
    @Column(name="GMDMDRP")    private int     gmdmdrp;       // Birth month of Mother PK-Holder
    @Column(name="GJRMDRP")    private int     gjrmdrp;       // Birth year of Mother PK-Holder
    
    @Column(name="GDGMDRPCR")  private int     gdgmdrpcr;     // Birth day of Mother PK-Holder after correction
    @Column(name="GMDMDRPCR")  private int     gmdmdrpcr;     // Birth month of Mother PK-Holder after correction
    @Column(name="GJRMDRPCR")  private int     gjrmdrpcr;     // Birth year of Mother PK-Holder after correction
    
    @Column(name="GPLMDRP")    private String  gplmdrp;       // Birth place of Mother PK-Holder
    
    // Information about the PK-Holder
    
    @Column(name="ODGPERP")    private int 	   odgperp;       // Decease day of PK-Holder
    @Column(name="OMDPERP")    private int     omdperp;       // Decease month of PK-Holder
    @Column(name="OJRPERP")    private int     ojrperp;       // Decease year of PK-Holder

    @Column(name="OPLPERP")    private String  oplperp;       // Decease place of PK-Holder    
    @Column(name="OAKPERP")    private String  oakperp;       // Code of death certificate of PK-holder
    @Column(name="ODOPERP")    private String  odoperp;       // Cause of death of the PK-holder
    
    @Column(name="GEGPERP")    private String  gegperp;       // Fields to record distinctions between data of the original birth certificate and the Personal Card; not usable and not necessary in the further process
    @Column(name="GEGVDRP")    private String  gegvdrp;       // Fields to record distinctions between data of the original birth certificate and the Personal Card; not usable and not necessary in the further process
    @Column(name="GEGMDRP")    private String  gegmdrp;       // Fields to record distinctions between data of the original birth certificate and the Personal Card; not usable and not necessary in the further process
    
    @Column(name="PROBLMP")    private String  problmp;       // Indicator if there are records in the file PKBYZ or not; not usable anymore
    
    @Column(name="PSBDGP")     private int     psbdgp;        // Day    of the "Persoonsbewijs" (Ausweis), date should be between 01-01-1941 and 5-05-1945.
    @Column(name="PSBMDP")     private int     psbmdp;        // Month  of the "Persoonsbewijs" (Ausweis), date should be between 01-01-1941 and 5-05-1945.
    @Column(name="PSBJRP")     private int     psbjrp;        // Year   of the "Persoonsbewijs" (Ausweis), date should be between 01-01-1941 and 5-05-1945.
    @Column(name="PSBNRP")     private String  psbnrp;        // Number of the "Persoonsbewijs" (Ausweis), date should be between 01-01-1941 and 5-05-1945.

    
	 @Column(name="OPDRNR")    private String  opdrnr;  	  // order number
	 @Column(name="DATUM")     private String  datum;         // date
	 @Column(name="INIT")      private String  init;          // initials 
	 @Column(name="VERSIE")    private String  versie;        // version
	 @Column(name="ONDRZKO")   private String  onderzko;      // original research
	 @Column(name="OPDRNRO")   private String  opdrnro;       // original order number  
	 @Column(name="DATUMO")    private String  datumo;        // original date   
	 @Column(name="INITO")     private String  inito;         // original initials    
	 @Column(name="VERSIEO")   private String  versieo;       // original version
	 @Column(name="OPDRNRI")   private String  orderNumberI;  // order number 2
    
    // Next come object fields that are not saved. 
    // They are marked "Transient"
    
    @Transient                 private ArrayList<PkBrp> 	professions = new ArrayList<PkBrp>(); 	 // Professions of PK-Holder
    @Transient                 private ArrayList<PkAdres> 	addresses   = new ArrayList<PkAdres>();  // Addresses   of PK-Holder
    @Transient                 private ArrayList<PkEigknd>	children    = new ArrayList<PkEigknd>(); // Children    of PK-Holder
    @Transient                 private ArrayList<PkHuw>   	marriages   = new ArrayList<PkHuw>();    // Marriages   of PK-Holder
    @Transient                 private ArrayList<PkByz>   	remarks     = new ArrayList<PkByz>();    // Remarks     of PK-Holder
    @Transient                 private P7                 	p7;                                      // P7          of PK-Holder 
    @Transient                 private ArrayList<P8>      	p8          = new ArrayList<P8>();       // P8          of PK-Holder

    
    @Transient                 private B4_ST b4;  // B4_ST Object for PKKND
    	
    // No-arguments constructor is necessary
    
    public PkKnd(){
    	
    }
    	
    
    public void convert(String B2dibg){
    	
    	//System.out.println("Pkknd: idnr = " +  getIdnr());
    	
    	// New Family
    	
    	B4_ST b4 = new B4_ST();  
    	setB4(b4);
    	b4.setKeyToRP(getIdnr());
    	b4.setEntryDateHead(B2dibg);
    	b4.setKeyToSourceRegister(getPktype());
    	
    	// dating
    	
    	String startDate = "01-07-1938";
    	int    startFlag = 1;
    	
    	if(Common1.dateIsValid(getGdgperp(), getGmdperp(), getGjrperp()) == 0){
    		if(Common1.dayCount(getGdgperp(), getGmdperp(), getGjrperp()) > Common1.dayCount(startDate)){
        		startDate = String.format("%02d-%02d-%04d", getGdgperp(), getGmdperp(), getGjrperp());
        		startFlag = 2;
    		}
    		else{
    			if(Common1.dateIsValid(getOdgperp(), getOmdperp(), getOjrperp()) == 0){
    	    		if(Common1.dayCount(getOdgperp(), getOmdperp(), getOjrperp()) < Common1.dayCount(startDate)){
    	    			startDate = Common1.dateFromDayCount(Common1.dayCount(getOdgperp(), getOmdperp(), getOjrperp()) - (3 * 365));
    	    			startFlag = 3;
    	    		}
    			}
    		}
    	}
    	
    	b4.setStartDate(startDate);
    	b4.setStartFlag(startFlag);
    	
    	String eindDate = null;
    	if(Utils.dateIsValid(getEindagpk(), getEinmndpk(), getEinjarpk()) == 0){
    		eindDate = String.format("%02d-%02d-%04d",  getEindagpk(), getEinmndpk(), getEinjarpk());
    		b4.setEndDate(eindDate);
    		b4.setEndFlag(31);
    	}
    	else{
    		if(Utils.dateIsValid(getOdgperp(), getOmdperp(), getOjrperp()) == 0){
    			eindDate = String.format("%02d-%02d-%04d",  getOdgperp(), getOmdperp(), getOjrperp());
        		b4.setEndDate(eindDate);
        		b4.setEndFlag(10);
    		}
    		else{ // use latest address date
    			for(PkAdres address: getAddresses()){
    				if(Utils.dateIsValid(address.getDgadrp(), address.getMdadrp(), address.getJradrp()) == 0){
    					eindDate = String.format("%02d-%02d-%04d",  address.getDgadrp(), address.getMdadrp(), address.getJradrp());
    	        		b4.setEndDate(eindDate);
    	        		b4.setEndFlag(32);
    				}
    			}
    		}
    	}
    	
    	// Administrative Fields
    	
    	b4.setVersionLastTimeOfDataEntry(getVersie());
    	b4.setResearchCodeOriginal(getOnderzko());
    	b4.setVersionOriginalDataEntry(getVersieo());
    	b4.setDate0(getDatum());
    	
    	
    	// New Person PK-Holder
    	
    	Utils.checkKeyFields(getIdnr() , "PkKnd.DBF", getVnm1perp(), getAnmperp(), "" + getGjrperp());
    	
    	int seqNoPersons = 1; 
    	
    	B2_ST b2 = new B2_ST();
    	b4.getPersons().add(b2); // Link B4_ST -> B2_ST
    	b2.setRegistration(b4);  // Link B2_ST -> B4_ST

    	b2.setKeyToPersons(seqNoPersons);
    	
    	initialiseB2_ST(b2);
    	setStartAndEndDate(b2);
    	
    	// Registration data - not needed

    	//String registrationDate = String.format("%02d-%02d-%04d", getCtrdgp(), getCtrmdp(), getCtrjrp());
    	//b2.setDateOfRegistration(registrationDate);
    	
    	// RP
    	
    	if(getIdnr() < 500000)
    		b2.setNatureOfPerson(1);
    	
    	
    	// Last name
    	
    	String lastName = getAnmperp().trim();
    	
    	//System.out.println("++++> lastName = " + lastName);
    	
    	if(lastName != null){
			if(lastName.split("%").length > 1){
				lastName = lastName.split("%")[0].trim();
				b2.setFamilyNameInterpreted(2);  	
			}
			else
				b2.setFamilyNameInterpreted(1);	
    	}
    	
    	
    	// Last name may still contain prefix, remove it
    	
    	
    	String prefix = null;
    	int i = lastName.indexOf(",");
    	if(i >= 0){    		
       		prefix = lastName.substring(i+1).trim();
       		lastName = lastName.substring(0,i).trim();    		
    		
    	}
    	b2.setFamilyName(Utils.standardizeFamilyName(lastName));
    	
    	
    	// First name
    	/*
    	b2.setFamilyNameInterpreted(1);
    	String firstName ="";
    	
    	String firstName1 = getVnm1perp();
    	
    	if(firstName1 != null && firstName1.trim().length() > 0){
			if(firstName1.split("%").length > 1){
				firstName1 = firstName1.split("%")[0].trim();
				b2.setFamilyNameInterpreted(2);  
			}
			firstName += Utils.standardizeFirstName(firstName1);
			firstName += " ";
    	
    	}    	

    	String firstName2 = getVnm2perp();

    	if(firstName2 != null && firstName2.trim().length() > 0){
			if(firstName2.split("%").length > 1){
				firstName2 = firstName2.split("%")[0].trim();
				b2.setFamilyNameInterpreted(2);  	
			}
			firstName += Utils.standardizeFirstName(firstName2);
			firstName += " ";
    	}    	

    	String firstName3 = getVnm3perp();
    	
    	if(firstName3 != null && firstName3.trim().length() > 0){
			if(firstName3.split("%").length > 1){
				firstName3 = firstName3.split("%")[0].trim();
				b2.setFamilyNameInterpreted(2);  	
			}
			firstName += Utils.standardizeFirstName(firstName3);
    	}    	
    	
    	b2.setFirstName(firstName.trim());
	    */
    	
    	Utils.handleFirstNames(b2, getVnm1perp(), getVnm2perp(), getVnm3perp());
    	
    	// Prefix
    	//System.out.println("++++> prefix = " + prefix + " standardized: "+ Utils.standardizePrefix(prefix));
    	//System.out.println("++++> getTusperp() = " + getTusperp() + " length: " +  getTusperp().length());

    	
    	if(getTusperp() == null || getTusperp().trim().length() == 0){
    		if(prefix != null)    	
    	    	b2.setPrefixLastName(Utils.standardizePrefix(prefix));
    		else;
    	}
    	else
        	b2.setPrefixLastName(Utils.standardizePrefix(getTusperp()));
    	
    	
    	//System.out.println("XXXXX  " + b2.getFirstName() + "  " + b2.getPrefixLastName() + "  " + b2.getFamilyName());
    	
    	if(getGslperp() == null || getGslperp().trim().length() == 0){
    		if(getPktype() != 8 && getPktype() != 9)  // Person List do not have to have gender
    			message(b2.getKeyToRP(), "7106");
    	}
    	else{    		
    		if(getIdnr() > 500000 && getGslperp().equalsIgnoreCase("V"))
        		message(getIdnr(), "7109");
    		b2.setSex(getGslperp().toLowerCase());

    	}
    	
    	// Birth date
    	
		int[] result = 	Utils.transformDateFields(getGdgperp(), getGmdperp(), getGjrperp(), getGdgperpcr(), getGmdperpcr(), getGjrperpcr()); 

		if(Common1.dateIsValid(result[0], result[1], result[2]) == 0){
		
			b2.setDateOfBirth(String.format("%02d-%02d-%04d", result[0], result[1], result[2]));
			b2.setDateOfBirthFlag(result[3]);
		}
		else
			message(b2.getKeyToRP(), "4129", "PkKnd.dbf");

    	
    	
    	// Now we can set PK_Holder in B4
    	
    	String pkHolder = b2.getFamilyName() + ", ";
    	if(b2.getPrefixLastName() != null)
    		pkHolder += b2.getPrefixLastName() + " ";
    	pkHolder += b2.getFirstName() + " (";
    	pkHolder += b2.getDateOfBirth();
    	pkHolder += ")";
    	b4.setPkHolder(pkHolder);
    	
    	// Birth Place
    	
    	String birthPlace = getGplperp();
    	
    	if(birthPlace != null){
			if(birthPlace.split("%").length > 1){
				birthPlace = birthPlace.split("%")[0].trim();
				b2.setPlaceOfBirthFlag(2);  	
			}
			else{
				birthPlace = birthPlace.split("%")[0].trim();
				b2.setPlaceOfBirthFlag(1);
			}
    	}
    	
    	ArrayList a = Utils.standardizeLocation(birthPlace);
    	b2.setPlaceOfBirthStandardized((String)a.get(0));
    	b2.setPlaceOfBirthID((Integer)a.get(1));
    	
    	// Decease date
    	
    	String deceaseDate = null;

    	
    	if(Common1.dateIsValid(getOdgperp(), getOmdperp(), getOjrperp()) == 0)    	
    		deceaseDate = String.format("%02d-%02d-%04d", getOdgperp(), getOmdperp(), getOjrperp());
    	else
    		deceaseDate = null;
    	
    	b2.setDateOfDecease(deceaseDate);    	
    	b2.setDateOfDeceaseFlag(1);
    	
    	
    	if(b2.getDateOfDecease() != null && b2.getDateOfBirth() != null &&
    			Common1.dayCount(b2.getDateOfBirth()) > Common1.dayCount(b2.getDateOfDecease()))
    			message(b2.getKeyToRP(), "4124", "" + b2.getFirstName() + " " + b2.getFamilyName());
    		
    	if(b2.getStartDate() != null && b2.getEndDate() != null && 
    			Common1.dayCount(b2.getStartDate()) > Common1.dayCount(b2.getEndDate()))
				message(b2.getKeyToRP(), "7136", "knd " + b2.getFirstName() + " " + b2.getFamilyName());
    		
    	
    	// Decease place 
    	
    	String deceasePlace = getOplperp();
    	String countrySuffix = "";

    	if(deceasePlace != null){
			if(deceasePlace.split("%").length > 1){
				deceasePlace = deceasePlace.split("%")[0].trim();
				b2.setPlaceOfDeceaseFlag(2);  	
			}
			else{
				deceasePlace = deceasePlace.split("%")[0].trim();
				b2.setPlaceOfDeceaseFlag(1);  
			}
    	}
    	else{  // Decease Place via P7 (for PL)
    		if(getP7() != null && getP7().getP7oppg() != null){
    			deceasePlace = getP7().getP7oppg();
    			if(getP7().getP7opol() != null)
       	    	 	countrySuffix = " $ " + getP7().getP7opol();
    		}
    		
    	}
    	
    	a = Utils.standardizeLocation(deceasePlace + countrySuffix);
    	b2.setPlaceOfDeceaseStandardized((String)a.get(0));
    	b2.setPlaceOfDeceaseID((Integer)a.get(1));
    	
    	// Religion
    	
    	if(getGdsperp() != null && getGdsperp().trim().length() != 0){
    		
    		String [] s = null;
    		String religion = getGdsperp();
    		if(religion.indexOf("*") >= 0)
    			s = religion.split("[*]");

    		else{
    			
    			s = new String[1];
    			s[0] = religion;
    		}
    		
    		
    		int date = Common1.dayCount(b2.getStartDate());
    		String endDate = b2.getEndDate() != null ? b2.getEndDate() : "01-01-2015";
    		int increment = (Common1.dayCount(endDate) - Common1.dayCount(b2.getStartDate())) / s.length;
    		
    		int seqRel = 1;
    		for(String rel: s){
    		
    			B33_ST b33 = new B33_ST();
    			b2.getReligions().add(b33);    // Link B33_ST -> B2_ST
    			b33.setPerson(b2);             // Link B2_ST -> B33_ST

    			b33.setKeyToRegistrationPersons(b2.getKeyToPersons());
    			initialiseB3_ST(b33);
    			b33.setDynamicDataType(3);
    			b33.setStartDate(Common1.dateFromDayCount(date));
    			b33.setEndDate(Common1.dateFromDayCount(date + increment - 1));
    			
    			date += increment;
    			

    			b33.setReligionStandardized(Utils.standardizeReligion(rel));
    			//b33.setReligionID((Integer)b.get(1));

    			b33.setDynamicDataSequenceNumber(seqRel++);

    		}
    	}
    		
    	b2.setNationality(getNatperp());
    	
    	// Relation to PK-Holder
    	
		B313_ST b313 = new B313_ST();
		b2.getRelationsToPKHolder().add(b313);    // Link B313_ST -> B2_ST
		b313.setPerson(b2);            			  // Link B2_ST -> B313_ST

		initialiseB3_ST(b313);
		b313.setDynamicDataType(13);
		b313.setContentOfDynamicData(1);         // PK-Holder him/her self
		b313.setDynamicDataSequenceNumber(1);
		
		b313.setStartDate(b2.getDateOfBirth());
		b313.setStartFlag(0);

		b313.setKeyToRegistrationPersons(b2.getKeyToPersons());

    	
    	B2_ST b2pk = b2; // remember the PK-Holder!
    	
    	
    	// New person Father PK-Holder
    	
    	Utils.checkKeyFields(getIdnr() , "PkKnd.DBF", getVnm1vdrp(), getAnmvdrp(), "" + getGjrvdrp());

    	
    	seqNoPersons++;
    	
    	b2 = new B2_ST();
    	
    	b2.setRegistration(b4);  // Link B2_ST -> B4_ST

    	initialiseB2_ST(b2);
    	b2.setKeyToPersons(seqNoPersons);
    	
    	// No period of observation
    	
    	b2.setStartDate(null);
    	b2.setStartFlag(11);  // 11 = unavailable
    	
    	b2.setEndDate(null);
    	b2.setEndFlag(11);  // 11 = unavailable
    	
    	// Last name     	
		
    	lastName = getAnmvdrp();
    	
    	if(lastName != null && lastName.trim().length() > 0){
			if(lastName.split("%").length > 1){
				lastName = lastName.split("%")[0].trim();
				b2.setFamilyNameInterpreted(2);  	
			}
			else{
				lastName = lastName.split("%")[0].trim();
				b2.setFamilyNameInterpreted(1);
			}
    	}
		
    	prefix = null;
    	if(lastName != null){
    		i = lastName.indexOf(",");
    		if(i >= 0){    		
    			prefix = lastName.substring(i+1).trim();
    			lastName = lastName.substring(0,i).trim();    		

    		}
    		b2.setFamilyName(Utils.standardizeFamilyName(lastName));
    	}
    	
    	// First Name
    	
    	Utils.handleFirstNames(b2, getVnm1vdrp(), getVnm2vdrp(), getVnm3vdrp());

    	// prefix
    	
    	if(getTusvdrp() == null || getTusvdrp().trim().length() == 0){
    		if(prefix != null)    	
    	    	b2.setPrefixLastName(Utils.standardizePrefix(prefix));
    		else;
    	}
    	else
        	b2.setPrefixLastName(Utils.standardizePrefix(getTusvdrp()));
    	
    	// Sex
    	
    	b2.setSex("m");
    	
    	// Birth date
    	
    	if((getGjrvdrpcr() > 0 &&  Common1.dateIsValid(getGdgvdrpcr(), getGmdvdrpcr(), getGjrmdrpcr()) != 0) || Common1.dateIsValid(getGdgvdrp(), getGmdvdrp(), getGjrvdrp()) != 0)
			message(b2.getKeyToRP(), "4129", "PkKnd.dbf");

    	else{				
    		result = Utils.transformDateFields(getGdgvdrp(), getGmdvdrp(), getGjrvdrp(), getGdgvdrpcr(), getGmdvdrpcr(), getGjrvdrpcr()); 

    		b2.setDateOfBirth(String.format("%02d-%02d-%04d", result[0], result[1], result[2]));
    		b2.setDateOfBirthFlag(result[3]);

    	}
		// Birth place
		
    	birthPlace = getGplvdrp();
    	
    	if(birthPlace != null){
			if(birthPlace.split("%").length > 1){
				birthPlace = birthPlace.split("%")[0].trim();
				b2.setPlaceOfBirthFlag(2);  	
			}
			else
				b2.setPlaceOfBirthFlag(1);  	
    	}
    	
    	a = Utils.standardizeLocation(birthPlace);
    	b2.setPlaceOfBirthStandardized((String)a.get(0));
    	b2.setPlaceOfBirthID((Integer)a.get(1));

    	// Relation to PK-Holder
    	
		b313 = new B313_ST();
		b2.getRelationsToPKHolder().add(b313);    // Link B313_ST -> B2_ST
		b313.setPerson(b2);            			  // Link B2_ST -> B313_ST

		initialiseB3_ST(b313);
		b313.setDynamicDataType(13);
		b313.setContentOfDynamicData(11);         // Father of PK-Holder
		b313.setStartDate(b2pk.getDateOfBirth()); // Start date = PK-Holder birth date
		b313.setStartFlag(0); 
		b313.setDynamicDataSequenceNumber(1);

		b313.setKeyToRegistrationPersons(b2.getKeyToPersons());

		// Only add this b2 if key fields are specified
    	
		if(b2.getFamilyName() != null && b2.getFirstName() != null)
			b4.getPersons().add(b2); // Link B4_ST -> B2_ST
		else
			seqNoPersons--;
    	
    	// New person Mother PK-Holder
		
    	Utils.checkKeyFields(getIdnr() , "PkKnd.DBF", getVnm1mdrp(), getAnmmdrp(), "" + getGjrmdrp());

		seqNoPersons++;
		
    	b2 = new B2_ST();
    	b2.setRegistration(b4);  // Link B2_ST -> B4_ST

    	initialiseB2_ST(b2);
    	b2.setKeyToPersons(seqNoPersons);
    	
    	// No period of observation
    	
    	b2.setStartDate(null);
    	b2.setStartFlag(11);  // 11 = unavailable
    	
    	b2.setEndDate(null);
    	b2.setEndFlag(11);  // 11 = unavailable
    	

    	
    	// Last name 

    	lastName = getAnmmdrp();
    	
    	if(lastName != null){
			if(lastName.split("%").length > 1){
				lastName = lastName.split("%")[0].trim();
				b2.setFamilyNameInterpreted(2);  	
			}
			else{
				lastName = lastName.split("%")[0].trim();
				b2.setFamilyNameInterpreted(1);
			}
    	}

    	prefix = null;
    	if(lastName != null){
    		i = lastName.indexOf(",");
    		if(i >= 0){    		
    			prefix = lastName.substring(i+1).trim();
    			lastName = lastName.substring(0,i).trim();    		

    		}
    		b2.setFamilyName(Utils.standardizeFamilyName(lastName));
    	}

    	// first name
    	
    	Utils.handleFirstNames(b2, getVnm1mdrp(), getVnm2mdrp(), getVnm3mdrp());

    	
    	// Prefix
    	
    	if(getTusmdrp() == null || getTusmdrp().trim().length() == 0){
    		if(prefix != null)    	
    	    	b2.setPrefixLastName(Utils.standardizePrefix(prefix));
    		else;
    	}
    	else
        	b2.setPrefixLastName(Utils.standardizePrefix(getTusmdrp()));
    	
    	b2.setSex("v");
    	
    	// Birth date
    	
    	if((getGjrmdrpcr() > 0 &&  Common1.dateIsValid(getGdgmdrpcr(), getGmdmdrpcr(), getGjrmdrpcr()) != 0) || Common1.dateIsValid(getGdgmdrp(), getGmdmdrp(), getGjrmdrp()) != 0)
			message(b2.getKeyToRP(), "4129", "PkKnd.dbf");

    	else{				
    		result = Utils.transformDateFields(getGdgmdrp(), getGmdmdrp(), getGjrmdrp(), getGdgmdrpcr(), getGmdmdrpcr(), getGjrmdrpcr()); 

    		b2.setDateOfBirth(String.format("%02d-%02d-%04d", result[0], result[1], result[2]));
    		b2.setDateOfBirthFlag(result[3]);

    	}
    	
		// Birth Place

		
		birthPlace = getGplmdrp();
		
    	if(birthPlace != null){
			if(birthPlace.split("%").length > 1){
				birthPlace = birthPlace.split("%")[0].trim();
				b2.setPlaceOfBirthFlag(2);  	
			}
			else
				b2.setPlaceOfBirthFlag(1);  	
    	}
		
    	a = Utils.standardizeLocation(birthPlace);
    	b2.setPlaceOfBirthStandardized((String)a.get(0));
    	b2.setPlaceOfBirthID((Integer)a.get(1));
    	
    	// Relation to PK-Holder
    	
		b313 = new B313_ST();
		b2.getRelationsToPKHolder().add(b313);    // Link B313_ST -> B2_ST
		b313.setPerson(b2);            			  // Link B2_ST -> B313_ST

		initialiseB3_ST(b313);
		b313.setDynamicDataType(13);
		b313.setContentOfDynamicData(21);         // Mother of PK-Holder 
		b313.setStartDate(b2pk.getDateOfBirth()); // Start date = PK-Holder birth date
		b313.setStartFlag(0);
		b313.setDynamicDataSequenceNumber(1);
		b313.setKeyToRegistrationPersons(b2.getKeyToPersons());


		// Only add this b2 if key fields are specified
    	
		if(b2.getFamilyName() != null && b2.getFirstName() != null)
			b4.getPersons().add(b2); // Link B4_ST -> B2_ST
		else
			seqNoPersons--;

    	
    	// Marriages of PK-Holder
		
		int seqNoCivil = 1;
		
		// If the first marriage is after the start date of the card: before the first marriage add an unmarried b32 	
		
   		
    			
		PkHuw pkhw = getMarriages().size() > 0 ? getMarriages().get(0) : null;
		
		
		if(pkhw != null && pkhw.getHjrhuwp() > 0 && Common1.dayCount(pkhw.getHdghuwp(), pkhw.getHmdhuwp(), pkhw.getHjrhuwp()) > Common1.dayCount(b2pk.getStartDate())){

			if(pkhw.getAnmhuwp() == null || pkhw.getAnmhuwp().trim().length() == 0)
				//System.out.println("PKHUW: IDNR = " + pkhw.getIdnr() + " Sequence Number " + pkhw.getVnrhuwp() + " heeft geen achternaam, skipped");
			    //message(b2.getKeyToRP(), "7146");
				;
			
			else{

				B32_ST b32 = new B32_ST();
				b2pk.getCivilStatus().add(b32); // Link B32_ST -> B2_ST
				b32.setPerson(b2pk);          // Link B2_ST -> B32_ST

				initialiseB3_ST(b32);

				b32.setKeyToRegistrationPersons(b2pk.getKeyToPersons());        		
				b32.setDynamicDataType(2);
				b32.setContentOfDynamicData(1);    // 1 = unmarried

				b32.setStartDate(b2pk.getStartDate());
				b32.setEndDate(Common1.dateFromDayCount(Common1.dayCount(pkhw.getHdghuwp(), pkhw.getHmdhuwp(), pkhw.getHjrhuwp()) - 1));

				b32.setDynamicDataSequenceNumber(seqNoCivil++);
			}
		}




		
    	
    	
    	for(PkHuw pkhuw: getMarriages()){

    		if(pkhuw.getAnmhuwp() == null || pkhuw.getAnmhuwp().trim().length() == 0){
    		    message(b2.getKeyToRP(), "7146");
    			//System.out.println("Partner without last name skipped for idnr = " + pkhw.getIdnr());
    			continue;
    		}
    		
    		
        	// First check if there already are civil status records
        	// If so, the end date of the last one must be update to 1 day before the start date of this one
    		
        	B32_ST b32Prev = b2.getCivilStatus().size() > 0 ? b2.getCivilStatus().get(b2.getCivilStatus().size() - 1) : null;
        	
        	if(pkhuw.getHjrhuwp() > 0 && Common1.dateIsValid(pkhuw.getHdghuwp(), pkhuw.getHmdhuwp(), pkhuw.getHjrhuwp()) == 0  && b32Prev != null){
        		
				String endDate  = String.format("%02d-%02d-%04d", pkhuw.getHdghuwp(), pkhuw.getHmdhuwp(), pkhuw.getHjrhuwp());
				String endDateM = Common1.dateFromDayCount(Common1.dayCount(endDate) - 1);

        		b32Prev.setEndDate(endDateM);
        		
        	}

    		
    		seqNoPersons++;
    		
    		b2 = new B2_ST();
        	b4.getPersons().add(b2); // Link B4_ST -> B2_ST
        	b2.setRegistration(b4);  // Link B2_ST -> B4_ST

    		initialiseB2_ST(b2);
    		b2.setKeyToPersons(seqNoPersons);
    		//setStartAndEndDate(b2);
    		pkhuw.convert(b2);

    		
    		//System.out.println("key = " + b2.getKeyToPersons());

    		
        	// add Civil Status Marriage row for PK-Holder
        	

        	
    		B32_ST b32 = new B32_ST();
        	b2pk.getCivilStatus().add(b32); // Link B32_ST -> B2_ST
        	b32.setPerson(b2pk);          // Link B2_ST -> B32_ST

    		initialiseB3_ST(b32);
    		
			b32.setKeyToRegistrationPersons(b2pk.getKeyToPersons());

    		
    		b32.setDynamicDataType(2);
    		b32.setContentOfDynamicData(5);    // 5 = married

        	ArrayList b = Utils.standardizeLocation(pkhuw.getHplhuwp());
        	b32.setCivilLocalityStandardized((String)b.get(0));
        	b32.setCivilLocalityID((Integer)b.get(1));
        	
        	if(Utils.dateIsValid(pkhuw.getHdghuwp(), pkhuw.getHmdhuwp(), pkhuw.getHjrhuwp()) == 0){  
        		
            	String marriageDate = String.format("%02d-%02d-%04d", pkhuw.getHdghuwp(), pkhuw.getHmdhuwp(), pkhuw.getHjrhuwp());
            	b32.setDateOfMutation(marriageDate);
            	b32.setStartDate(marriageDate);
            	
            	
        		
        	}
        	
        	b32.setValueOfRelatedPerson(seqNoPersons);  // set row number spouse 

        	b32.setDynamicDataSequenceNumber(seqNoCivil++);
        	
        	B32_ST b32Last = b32; // keep marriage 
        	
        	// Check if spouse left, this gives a new Civil status record for the PK-Holder (and for the spouse in PkHuw)
        	
        	if(pkhuw.getAjrhuwp() > 0){
        		
        		// Update Marriage enddate
        		
            	// First check if there already are civil status records
            	// If so, the end date of the last one must be update to 1 day before the start date of this one
        		
           		b32Last.setEndDate(Common1.dateFromDayCount(Common1.dayCount(pkhuw.getAdghuwp(), pkhuw.getAmdhuwp(), pkhuw.getAjrhuwp()) - 1));
            		
        		// new civil status record for PK-Holder with code = 11
        		
        		b32 = new B32_ST();
            	b2pk.getCivilStatus().add(b32); // Link B32_ST -> B2_ST
            	b32.setPerson(b2pk);          // Link B2_ST -> B32_ST

        		initialiseB3_ST(b32);
        		b32.setDynamicDataType(2);
        		b32.setContentOfDynamicData(11);  
    			b32.setKeyToRegistrationPersons(b2pk.getKeyToPersons());

        		b32.setDynamicDataSequenceNumber(seqNoCivil++);
        		
            	
                String departureDate = String.format("%02d-%02d-%04d", pkhuw.getAdghuwp(), pkhuw.getAmdhuwp(), pkhuw.getAjrhuwp());
                b32.setDateOfMutation(departureDate);
                b32.setStartDate(departureDate);
                //b32.setEndDate(b32Marriage.getEndDate());
                
                // Update previous status end date
            		
                //b32Marriage.setEndDate(Common1.dateFromDayCount(Common1.dayCount(departureDate) - 1));     
                
                b32Last = b32; // Now this is last one

        	}

        	// Check if marriage terminated
        	
        	
        	if(pkhuw.getOrdhuwp() >= 1 || pkhuw.getOjrhuwp() > 0){ // termination including death spouse
        		
        		      		
        		
        		// Update Marriage end date
            	
        		if(pkhuw.getOjrhuwp() > 0){
            		
        			String endDate  = String.format("%02d-%02d-%04d", pkhuw.getOdghuwp(), pkhuw.getOmdhuwp(), pkhuw.getOjrhuwp());

        			b32Last.setEndDate(endDate);
            		
        		}


        	       		
        		// new civil status record for PK Holder, but only if the end date of the marriage is known (on his card). Otherwise, he died, no further status possible 
        		
        		if(pkhuw.getOjrhuwp() > 0 && Common1.dateIsValid(pkhuw.getOdghuwp(), pkhuw.getOmdhuwp(), pkhuw.getOjrhuwp()) == 0){  // because sometimes like "00-00-1932"

        			b32 = new B32_ST();
        			b2.getCivilStatus().add(b32); // Link B32_ST -> B2_ST
        			b32.setPerson(b2pk);          // Link B2_ST -> B32_ST

        			initialiseB3_ST(b32);
        			b32.setDynamicDataType(2);
        			b32.setKeyToRegistrationPersons(b2pk.getKeyToPersons());
 
        			// set the start date of the new record

        			if(pkhuw.getOjrhuwp() > 0){

        				String startDate1  = String.format("%02d-%02d-%04d", pkhuw.getOdghuwp(), pkhuw.getOmdhuwp(), pkhuw.getOjrhuwp());
        				//System.out.println("startDate1 = " + startDate1);
        				String startDateP = Common1.dateFromDayCount(Common1.dayCount(startDate1) + 1);

        				b32.setStartDate(startDateP);
        			}

        			// New civil status 

        			int ordhuwp = pkhuw.getOrdhuwp() >= 1 ? pkhuw.getOrdhuwp() : 1;  // 1 = death spouse

        			switch(ordhuwp){

        			case 1: b32.setContentOfDynamicData(2);  // death

        			break;
        			case 2: b32.setContentOfDynamicData(3);  // divorce
        			break;
        			case 3: b32.setContentOfDynamicData(12); // other
        			break;
        			case 4: b32.setContentOfDynamicData(3);  // bigamy
        			break;
        			case 9: b32.setContentOfDynamicData(12); // no reason
        			break;
        			}

        			b32.setDynamicDataSequenceNumber(seqNoCivil++);
        		}
        	}
    	}
    	
    	// Children of PK-Holder
    	
    	
    	for(PkEigknd pkeigknd: getChildren()){
    		
    		seqNoPersons++;
    		
    		b2 = new B2_ST();
        	b4.getPersons().add(b2); // Link B4_ST -> B2_ST
        	b2.setRegistration(b4);  // Link B2_ST -> B4_ST

    		initialiseB2_ST(b2);
    		//if(getPktype() != 8 && getPktype() != 9) // not for Person List children -> Fix in PkEigknd
   			setStartAndEndDate(b2);
        	b2.setKeyToPersons(seqNoPersons);
    		pkeigknd.convert(b2);
        	

    		
    	}
    	
    	// Professions of PK-Holder
    	
    	
    	if(getProfessions().size() > 0){

    		int date = Common1.dayCount(b2pk.getStartDate());
    		
    		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    		//Date date1 = new Date();
    		//System.out.println(localDate);
    		//String endDate1 =  dateFormat.format(localDate); // e.g. "01-01-2020";

    		LocalDate localDate = LocalDate.now();

    		String endDate1 = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate);
    		
    		//System.out.println("--->" + endDate1);
    		
    		String endDate = b2pk.getEndDate() != null ? b2pk.getEndDate() : endDate1;
    		
    		
    		// Check if last profession = 'zonder' and more than  profession an alive at 65
    		// Then start 'zonder beroep' at 65 and spread the other professions equidistantly
    		
    		B35_ST b35_last   = null; 
    		PkBrp pkbrp_last  = null;
    		//System.out.println("Last profession = " + getProfessions().get(getProfessions().size() -1).getBeroepp());
    		if(getProfessions().size() > 1 && getProfessions().get(getProfessions().size() -1).getBeroepp().equalsIgnoreCase("zonder")){
    			
    			String birthdate = b2pk.getDateOfBirth();
    			Integer birthyear = new Integer(birthdate.substring(6));
    			String pensionDate = birthdate.substring(0, 6) + (birthyear + 65);
    			
    			//System.out.println("Geboorte datum = " + birthdate);
    			//System.out.println("Pensioen datum = " + pensionDate);
    			
    			if(Common1.dayCount(pensionDate) < Common1.dayCount(b2pk.getEndDate())){
    				
    				// create b35 for the last 'zonder beroep' from age 65 until death
    				
    				B35_ST b35 = new B35_ST();
    				b35.setPerson(b2pk);            // Link B2_ST -> B35_ST

    				initialiseB3_ST(b35);
    				b35.setStartFlag(0);
    				b35.setEndFlag(0);
    				b35.setKeyToRegistrationPersons(b2pk.getKeyToPersons());
    				//System.out.println("key = " + b2pk.getKeyToPersons());

    				//b35.setDynamicDataSequenceNumber(seqNoPr++);
    				
    				String profession = getProfessions().get(getProfessions().size() -1).getBeroepp();
    				String position   = getProfessions().get(getProfessions().size() -1).getBrpposp();
    				
    				if(position != null){
    					if(!position.equalsIgnoreCase("n")){

    						if(!profession.endsWith("(o)") && !profession.endsWith("(h)")){

    							profession = profession + " (" + position + ")";
    						}
    					}
    				}
    				
    				ArrayList b = Utils.standardizeProfession(getProfessions().get(getProfessions().size() -1).getBeroepp());
    				b35.setOccupationStandardized((String)b.get(0));
    				b35.setOccupationID((Integer)b.get(1));
    				
					b35.setStartFlag(53); // Special code to indicate that we do this
					b35.setEndFlag(54);   // Special code to indicate that we do this
					b35.setDynamicDataType(5);
    				
    				b35.setStartDate(pensionDate);
    				b35.setEndDate(b2pk.getEndDate());
    				endDate = pensionDate;

    				int ii = getProfessions().size() -1;
    				pkbrp_last = getProfessions().get(ii); // remember
    				getProfessions().remove(ii);
    				b35_last = b35; // remember this b35
    			}
    			
    		}
    		
    			
    		int increment = (Common1.dayCount(endDate) - Common1.dayCount(b2pk.getStartDate())) / getProfessions().size();

    		if(increment > 0){ // because sometimes endDate is invalid

    			int seqNoPr = 1;
    			for(PkBrp pkbrp: getProfessions()){

    				B35_ST b35 = new B35_ST();
    				b35.setPerson(b2pk);            // Link B2_ST -> B35_ST

    				initialiseB3_ST(b35);
    				b35.setStartFlag(0);
    				b35.setEndFlag(0);

    				b35.setKeyToRegistrationPersons(b2pk.getKeyToPersons());
    				//System.out.println("key = " + b2pk.getKeyToPersons());

    				b35.setDynamicDataSequenceNumber(seqNoPr++);
    				
    				String profession = pkbrp.getBeroepp();
    				String position   = pkbrp.getBrpposp();
    				
    				if(position != null){
    					if(!position.equalsIgnoreCase("n")){

    						if(!profession.endsWith("(o)") && !profession.endsWith("(h)")){

    							profession = profession + " (" + position + ")";
    						}
    					}
    				}
    				
    				ArrayList b = Utils.standardizeProfession(pkbrp.getBeroepp());
    				b35.setOccupationStandardized((String)b.get(0));
    				b35.setOccupationID((Integer)b.get(1));

    				b35.setStartDate(Common1.dateFromDayCount(date));
    				if(seqNoPr <= getProfessions().size())
    					b35.setEndDate(Common1.dateFromDayCount(date + increment - 1));
    				else
    					if(b35_last != null){
    						
    						b35.setEndDate(Common1.dateFromDayCount(Common1.dayCount(b35_last.getStartDate()) - 1));
    						
    					}
    					else
    						b35.setEndDate(b2pk.getEndDate());  // to make the last date equal the death date
    				
    				if(getProfessions().size() > 1 || b35_last != null){
    					
    					b35.setStartFlag(51);
    					b35.setEndFlag(52);
    				}

    				date += increment;

    				b35.setDynamicDataType(5);
    				if(pkbrp.convert(b35)){
    					b2pk.getProfessions().add(b35); // Link B35_ST -> B2_ST
    				}
    			}
    			
    			// Now check if there is a b35_last
    			
    			if(b35_last != null){
    				
    				b35_last.setDynamicDataSequenceNumber(seqNoPr);
    				
    				if(pkbrp_last.convert(b35_last)){
    					b2pk.getProfessions().add(b35_last); // Link B35_ST -> B2_ST
    				}
    				
    				
    			}
    			
    			
    		}
    	}
    	// Remarks of PK-Holder -> B4 (Remarks)
    	
    	String remarks = "";
    	
    	for(PkByz pkbyz: getRemarks()){
    		
    		if(pkbyz != null){
    			
    			String r = pkbyz.getByz();
    			
    			if(r != null){    		
    				remarks += r;
    				remarks += " " ;
    			}
    			
    		}
    		
    	}
    	if(remarks != null)
    		remarks = remarks.trim();
    	
    	if(remarks.length() != 0)
    		b4.setRemarks(remarks);

    	
    	// Addresses of the PK-Holder

    	if(getAddresses().size() > 0){

    		int nrOfAddresses = getAddresses().size();
    		int [] addressDates = new int[nrOfAddresses]; 
    		int [] addressDateFlags = new int[nrOfAddresses]; 
    		
    		
    		// Find oldest date
    		
    		int oldestDate = Common1.dayCount("01-01-2100");
    		for(int k = 0; k < nrOfAddresses; k++){
    			if(Utils.dateIsValid(getAddresses().get(k).getDgadrp(), getAddresses().get(k).getMdadrp(), getAddresses().get(k).getJradrp()) == 0)
    				if(Common1.dayCount(getAddresses().get(k).getDgadrp(), getAddresses().get(k).getMdadrp(), getAddresses().get(k).getJradrp()) < oldestDate)
    					oldestDate = Common1.dayCount(getAddresses().get(k).getDgadrp(), getAddresses().get(k).getMdadrp(), getAddresses().get(k).getJradrp());
    			
    			
    		}
    		
    		if(oldestDate == Common1.dayCount("01-01-2100"))
    			oldestDate = Common1.dayCount(b2pk.getStartDate());
    		
    		//System.out.println("Oldest date= "+ Common1.dateFromDayCount(oldestDate));
    		
    		
    		// Allow 2 invalid dates at the head of the addresses
    		
    		int offset = 0;
    		if(Utils.dateIsValid(getAddresses().get(0).getDgadrp(), getAddresses().get(0).getMdadrp(), getAddresses().get(0).getJradrp()) != 0){
    	    		if(nrOfAddresses > 1 && Utils.dateIsValid(getAddresses().get(1).getDgadrp(), getAddresses().get(1).getMdadrp(), getAddresses().get(1).getJradrp()) != 0){
    	    			addressDates[0] = oldestDate - (8 * 365) - 2; // 8 years before start PK
    	    			addressDateFlags[0] = 4;
    	    			addressDates[1] = oldestDate - (4 * 365) - 1; // 8 years before start PK
    	    			addressDateFlags[1] = 3;
    	    			offset = 2;
    	    		}
    	    		else{
    	    			addressDates[0] = oldestDate - (4 * 365) - 1; // 8 years before start PK
    	    			addressDateFlags[0] = 3;
    	    			offset = 1;
    	    		}
    		}

    		//System.out.println("1 1st element date= "+ Common1.dateFromDayCount(addressDates[0]));

    		
    		// copy address dates to array
    		
    		for(int j = offset; j < nrOfAddresses; j++){
    			if(Utils.dateIsValid(getAddresses().get(j).getDgadrp(), getAddresses().get(j).getMdadrp(), getAddresses().get(j).getJradrp()) == 0){
    				addressDates[j] =  Common1.dayCount(getAddresses().get(j).getDgadrp(), getAddresses().get(j).getMdadrp(), getAddresses().get(j).getJradrp());
    				addressDateFlags[j] = 1;

    			}
    			else{
    				if(j >= 2){ // because the first 2 addresses have been made valid above already
    					addressDates[j] =  0;
    					addressDateFlags[j] =  5;  // will be estimated
    				}

    			}
    		}

    		//System.out.println("2 1st element date= "+ Common1.dateFromDayCount(addressDates[0]));

    		
    		// estimate address dates which are 0

    		int cnt = 0;
    		for(int j = 1; j < nrOfAddresses; j++){
    			if(addressDates[j] ==  0){
    				cnt = 0;
    				int dateBefore = addressDates[j - 1];
    				int dateAfter = 0;
    				for(int k = j; k < nrOfAddresses; k++){
    					cnt++;
    					if(addressDates[k] != 0){
    						dateAfter = addressDates[k];
    						break;
    					}
    					if(dateAfter == 0){
    						if(b2pk.getEndDate() != null)
    							dateAfter = Common1.dayCount(b2pk.getEndDate());
    						else
    							dateAfter = Common1.dayCount("01-01-2018");

    					}
    				}

    				int div = (dateAfter - dateBefore) / cnt + 1;				
    				int c = 1; 

    				for(int l = j; l < j + cnt; l++){

    					if(addressDates[l] == 0)  // this is necessary
    						addressDates[l] = dateBefore + c * div;
    					c++;

    				}
    			}
    		}

    		
    		//System.out.println("Adressen Idnr = " + getIdnr());
    		//for(int l = 0; l < addressDates.length; l++){
    		//	System.out.println("add_date = " + addressDates[l]);
    		//	
    		//}
    		
    		int seqNoAdr = 1;
    		String street = null;

    		//System.out.println("3 1st element date= "+ Common1.dateFromDayCount(addressDates[0]));

    		
    		for(PkAdres pkadr: getAddresses()){

    			if(pkadr.getLndadrp() == null || pkadr.getLndadrp().trim().equalsIgnoreCase("NL")){

    				B6_ST b6 = new B6_ST();
    				b6.setPerson(b2pk);            // Link B2_ST -> B35_ST
    				b2pk.getAddressess().add(b6);  // Link B6_ST -> B2_ST
    				initialiseB6_ST(b6);
    				b6.setSequenceNumberToAddresses(seqNoAdr);
    				b6.setStartDate(Common1.dateFromDayCount(addressDates[seqNoAdr - 1]));
    				b6.setStartFlag(addressDateFlags[seqNoAdr - 1]);
    				if(seqNoAdr < nrOfAddresses){
    					b6.setEndDate(Common1.dateFromDayCount(addressDates[seqNoAdr] - 1)); // xxx
    					b6.setEndFlag(2);
    				}
    				else{
    					b6.setEndDate(b2pk.getEndDate());
    					b6.setEndFlag(1);
    				}

    				pkadr.convert(b6);
    			}

    			seqNoAdr++;  
    		}
    	}
    	
    	
    	// addresses of the PK-Holder in P8 (person lists)

    	int seqNoAdr = 1;
    	for(P8 p8: getP8()){

    		if(p8.getP8opwf() == null || p8.getP8opwf().equalsIgnoreCase("BRIEFADRES"))
    			continue;

    		if(p8.getP8opil() != null && (p8.getP8opil().trim().equalsIgnoreCase("NL") || p8.getP8opil().trim().length() == 0)){

    			B6_ST b6 = new B6_ST();

    			b2pk.getAddressess().add(b6);  // Link B6_ST -> B2_ST
    			b6.setPerson(b2pk);            // Link B2_ST -> B6

    			initialiseB6_ST(b6);
    			b6.setSequenceNumberToAddresses(seqNoAdr);

    			p8.convert(b6);
    		}

    		seqNoAdr++;  

    	}


    	// check addresses for origin and destination from/to other countries for PkAdres

    	PkAdres [] adres = new PkAdres[getAddresses().size()];
    	getAddresses().toArray(adres);   //get array


    	for(int j = 0; j < adres.length; j++){

    		if(j == 0 && !(adres[j].getLndadrp() == null || adres[j].getLndadrp().trim().length() == 0 || adres[j].getLndadrp().trim().equalsIgnoreCase("NL")))
    			adres[j].origin(b2pk);
    		else{
    			if(!(adres[j].getLndadrp() == null || adres[j].getLndadrp().trim().length() == 0 || adres[j].getLndadrp().trim().equalsIgnoreCase("NL"))){
    				if(j < adres.length - 1){
    					if(!(adres[j + 1].getLndadrp() == null || adres[j + 1].getLndadrp().trim().length() == 0 || adres[j + 1].getLndadrp().trim().equalsIgnoreCase("NL"))){
        					//System.out.println("PkAddress 1 destination country = " + adres[j].getLndadrp() + " locality = " + adres[j].getPladrp());
    						adres[j].destination(b2pk);
    					}
    					else{
    						adres[j].origin(b2pk);
    					}
    				}
    				else{
    					//System.out.println("PkAddress 2 destination country = " + adres[j].getLndadrp() + " locality = " + adres[j].getPladrp());
    					adres[j].destination(b2pk);
    				}
    			}
    		}
    	}

    	// check addresses for origin and destination from/to other countries for p8 

    	
    	P8 [] p8 = new P8[getP8().size()];
    	getP8().toArray(p8);   //get array
    	
    	
    	for(int k = 0; k < p8.length; k++){

    		if(k == 0 &&  !(p8[k].getP8opil() == null || p8[k].getP8opil().trim().length() == 0 || p8[k].getP8opil().trim().equalsIgnoreCase("NL")))
    			p8[k].origin(b2pk);
    		

    		else{
        		if(!(p8[k].getP8opil() == null || p8[k].getP8opil().trim().length() == 0 || p8[k].getP8opil().trim().equalsIgnoreCase("NL"))){
    				if(k < p8.length - 1){
    		    		if(!(p8[k].getP8opil() == null || p8[k].getP8opil().trim().length() == 0 || p8[k].getP8opil().trim().equalsIgnoreCase("NL"))){
        					//System.out.println("P8        1 destination country = " + p8[k].getP8opil() + " locality = " + p8[k].getPladrp());
    						p8[k].destination(b2pk);
    		    		}
    					else
    						p8[k].origin(b2pk);
    				}
    				else{
    					//System.out.println("P8        2 destination country = " + p8[k].getP8opil() + " locality = " + p8[k].getPladrp());
    					p8[k].destination(b2pk);
    					
    				}
        		}
    		}
    	}
    	
    	// If no addresses specified, create a dummy address
    	
    	
    	if(getAddresses().size() == 0){
    		
			B6_ST b6 = new B6_ST();
			b2pk.getAddressess().add(b6);  // Link B6_ST -> B2_ST
			b6.setPerson(b2pk);            // Link B2_ST -> B35_ST

			initialiseB6_ST(b6);
			b6.setSequenceNumberToAddresses(1);
			b6.setAddressFlag(3);          // means "no address"
			b6.setStreet("$Geen adres$");
    		
    		
    	}
    
    	

    }
    	
    
    private void setStartAndEndDate(B2_ST b2){

    	b2.setStartDate(b2.getRegistration().getStartDate());
    	b2.setStartFlag(b2.getRegistration().getStartFlag());
    	b2.setEndDate(b2.getRegistration().getEndDate());
    	b2.setEndFlag(b2.getRegistration().getEndFlag());
    	
    }
    private void initialiseB2_ST(B2_ST b2){
    	
    	b2.setKeyToRP(getIdnr());
    	b2.setEntryDateHead(getB4().getEntryDateHead());
    	b2.setKeyToSourceRegister(getPktype());
    	
    	b2.setVersionLastTimeOfDataEntry(getVersie());
    	b2.setResearchCodeOriginal(getOnderzko());
    	b2.setVersionOriginalDataEntry(getVersieo());
    	b2.setDate0(getDatum());
    	
    }
    
    private void initialiseB3_ST(B3_ST b3){
    	
    	b3.setKeyToRP(getIdnr());
    	b3.setEntryDateHead(getB4().getEntryDateHead());
    	b3.setKeyToSourceRegister(getPktype());

    	b3.setStartDate(b3.getPerson().getStartDate());
    	b3.setStartFlag(b3.getPerson().getStartFlag());
    	b3.setEndDate(b3.getPerson().getEndDate());
    	b3.setEndFlag(b3.getPerson().getEndFlag());

    	
    	b3.setVersionLastTimeOfDataEntry(getVersie());
    	b3.setResearchCodeOriginal(getOnderzko());
    	b3.setVersionOriginalDataEntry(getVersieo());
    	b3.setDate0(getDatum());
    	
    }
    
    private void initialiseB6_ST(B6_ST b6){
    	
    	b6.setKeyToRP(getIdnr());
    	b6.setDateEntryHeadOfHousehold(getB4().getEntryDateHead());
    	b6.setKeyToSourceRegister(getPktype());

    	//b6.setStartDate(b6.getPerson().getStartDate());
    	//b6.setStartFlag(b6.getPerson().getStartFlag());
    	//b6.setEndDate(b6.getPerson().getEndDate());
    	//b6.setEndFlag(b6.getPerson().getEndFlag());

    	
    	b6.setVersionLastTimeOfDataEntry(getVersie());
    	b6.setResearchCodeOriginal(getOnderzko());
    	b6.setVersionOriginalDataEntry(getVersieo());
    	b6.setDate0(getDatum());
    	
    }
    
    
   	public void print(){
   		
   		if(getIdnr() != 71047) return;
    		
  		System.out.println("In PkKnd, idnr = " + getIdnr() + ", Family Name = " + getAnmperp() + ", datum = " + getDatum());
  		
  		for(PkHuw pkhuw: getMarriages())
  			pkhuw.print();
  		
  		for(PkEigknd pkeigknd: getChildren())
  			pkeigknd.print();
   	}
   	
    private static void message(int idnr, String number, String... fills) {

        //print("Messagenr: " + number);

        Message m = new Message(number);

        m.setKeyToRP(idnr);
        m.save(fills);
    }



	public int getIdnr() {
		return idnr;
	}


	public void setIdnr(int idnr) {
		this.idnr = idnr;
	}


	public int getIdnrp() {
		return idnrp;
	}


	public void setIdnrp(int idnrp) {
		this.idnrp = idnrp;
	}


	public String getGaktnrp() {
		return gaktnrp;
	}


	public void setGaktnrp(String gaktnrp) {
		this.gaktnrp = gaktnrp;
	}


	public int getPktype() {
		return pktype;
	}


	public void setPktype(int pktype) {
		this.pktype = pktype;
	}


	public int getEindagpk() {
		return eindagpk;
	}


	public void setEindagpk(int eindagpk) {
		this.eindagpk = eindagpk;
	}


	public int getEinmndpk() {
		return einmndpk;
	}


	public void setEinmndpk(int einmndpk) {
		this.einmndpk = einmndpk;
	}


	public int getEinjarpk() {
		return einjarpk;
	}


	public void setEinjarpk(int einjarpk) {
		this.einjarpk = einjarpk;
	}


	public int getCtrdgp() {
		return ctrdgp;
	}


	public void setCtrdgp(int ctrdgp) {
		this.ctrdgp = ctrdgp;
	}


	public int getCtrmdp() {
		return ctrmdp;
	}


	public void setCtrmdp(int ctrmdp) {
		this.ctrmdp = ctrmdp;
	}


	public int getCtrjrp() {
		return ctrjrp;
	}


	public void setCtrjrp(int ctrjrp) {
		this.ctrjrp = ctrjrp;
	}


	public String getCtrparp() {
		return ctrparp;
	}


	public void setCtrparp(String ctrparp) {
		this.ctrparp = ctrparp;
	}


	public String getGznvrmp() {
		return gznvrmp;
	}


	public void setGznvrmp(String gznvrmp) {
		this.gznvrmp = gznvrmp;
	}


	public String getAnmperp() {
		return anmperp;
	}


	public void setAnmperp(String anmperp) {
		this.anmperp = anmperp;
	}


	public String getTusperp() {
		return tusperp;
	}


	public void setTusperp(String tusperp) {
		this.tusperp = tusperp;
	}


	public String getVnm1perp() {
		return vnm1perp;
	}


	public void setVnm1perp(String vnm1perp) {
		this.vnm1perp = vnm1perp;
	}


	public String getVnm2perp() {
		return vnm2perp;
	}


	public void setVnm2perp(String vnm2perp) {
		this.vnm2perp = vnm2perp;
	}


	public String getVnm3perp() {
		return vnm3perp;
	}


	public void setVnm3perp(String vnm3perp) {
		this.vnm3perp = vnm3perp;
	}


	public int getGdgperp() {
		return gdgperp;
	}


	public void setGdgperp(int gdgperp) {
		this.gdgperp = gdgperp;
	}


	public int getGmdperp() {
		return gmdperp;
	}


	public void setGmdperp(int gmdperp) {
		this.gmdperp = gmdperp;
	}


	public int getGjrperp() {
		return gjrperp;
	}


	public void setGjrperp(int gjrperp) {
		this.gjrperp = gjrperp;
	}


	public int getGdgperpcr() {
		return gdgperpcr;
	}


	public void setGdgperpcr(int gdgperpcr) {
		this.gdgperpcr = gdgperpcr;
	}


	public int getGmdperpcr() {
		return gmdperpcr;
	}


	public void setGmdperpcr(int gmdperpcr) {
		this.gmdperpcr = gmdperpcr;
	}


	public int getGjrperpcr() {
		return gjrperpcr;
	}


	public void setGjrperpcr(int gjrperpcr) {
		this.gjrperpcr = gjrperpcr;
	}


	public String getGplperp() {
		return gplperp;
	}


	public void setGplperp(String gplperp) {
		this.gplperp = gplperp;
	}


	public String getNatperp() {
		return natperp;
	}


	public void setNatperp(String natperp) {
		this.natperp = natperp;
	}


	public String getGdsperp() {
		return gdsperp;
	}


	public void setGdsperp(String gdsperp) {
		this.gdsperp = gdsperp;
	}


	public String getGslperp() {
		return gslperp;
	}


	public void setGslperp(String gslperp) {
		this.gslperp = gslperp;
	}


	public String getAnmvdrp() {
		return anmvdrp;
	}


	public void setAnmvdrp(String anmvdrp) {
		this.anmvdrp = anmvdrp;
	}


	public String getTusvdrp() {
		return tusvdrp;
	}


	public void setTusvdrp(String tusvdrp) {
		this.tusvdrp = tusvdrp;
	}


	public String getVnm1vdrp() {
		return vnm1vdrp;
	}


	public void setVnm1vdrp(String vnm1vdrp) {
		this.vnm1vdrp = vnm1vdrp;
	}


	public String getVnm2vdrp() {
		return vnm2vdrp;
	}


	public void setVnm2vdrp(String vnm2vdrp) {
		this.vnm2vdrp = vnm2vdrp;
	}


	public String getVnm3vdrp() {
		return vnm3vdrp;
	}


	public void setVnm3vdrp(String vnm3vdrp) {
		this.vnm3vdrp = vnm3vdrp;
	}


	public int getGdgvdrp() {
		return gdgvdrp;
	}


	public void setGdgvdrp(int gdgvdrp) {
		this.gdgvdrp = gdgvdrp;
	}


	public int getGmdvdrp() {
		return gmdvdrp;
	}


	public void setGmdvdrp(int gmdvdrp) {
		this.gmdvdrp = gmdvdrp;
	}


	public int getGjrvdrp() {
		return gjrvdrp;
	}


	public void setGjrvdrp(int gjrvdrp) {
		this.gjrvdrp = gjrvdrp;
	}


	public int getGdgvdrpcr() {
		return gdgvdrpcr;
	}


	public void setGdgvdrpcr(int gdgvdrpcr) {
		this.gdgvdrpcr = gdgvdrpcr;
	}


	public int getGmdvdrpcr() {
		return gmdvdrpcr;
	}


	public void setGmdvdrpcr(int gmdvdrpcr) {
		this.gmdvdrpcr = gmdvdrpcr;
	}


	public int getGjrvdrpcr() {
		return gjrvdrpcr;
	}


	public void setGjrvdrpcr(int gjrvdrpcr) {
		this.gjrvdrpcr = gjrvdrpcr;
	}


	public String getGplvdrp() {
		return gplvdrp;
	}


	public void setGplvdrp(String gplvdrp) {
		this.gplvdrp = gplvdrp;
	}


	public String getAnmmdrp() {
		return anmmdrp;
	}


	public void setAnmmdrp(String anmmdrp) {
		this.anmmdrp = anmmdrp;
	}


	public String getTusmdrp() {
		return tusmdrp;
	}


	public void setTusmdrp(String tusmdrp) {
		this.tusmdrp = tusmdrp;
	}


	public String getVnm1mdrp() {
		return vnm1mdrp;
	}


	public void setVnm1mdrp(String vnm1mdrp) {
		this.vnm1mdrp = vnm1mdrp;
	}


	public String getVnm2mdrp() {
		return vnm2mdrp;
	}


	public void setVnm2mdrp(String vnm2mdrp) {
		this.vnm2mdrp = vnm2mdrp;
	}


	public String getVnm3mdrp() {
		return vnm3mdrp;
	}


	public void setVnm3mdrp(String vnm3mdrp) {
		this.vnm3mdrp = vnm3mdrp;
	}


	public int getGdgmdrp() {
		return gdgmdrp;
	}


	public void setGdgmdrp(int gdgmdrp) {
		this.gdgmdrp = gdgmdrp;
	}


	public int getGmdmdrp() {
		return gmdmdrp;
	}


	public void setGmdmdrp(int gmdmdrp) {
		this.gmdmdrp = gmdmdrp;
	}


	public int getGjrmdrp() {
		return gjrmdrp;
	}


	public void setGjrmdrp(int gjrmdrp) {
		this.gjrmdrp = gjrmdrp;
	}


	public int getGdgmdrpcr() {
		return gdgmdrpcr;
	}


	public void setGdgmdrpcr(int gdgmdrpcr) {
		this.gdgmdrpcr = gdgmdrpcr;
	}


	public int getGmdmdrpcr() {
		return gmdmdrpcr;
	}


	public void setGmdmdrpcr(int gmdmdrpcr) {
		this.gmdmdrpcr = gmdmdrpcr;
	}


	public int getGjrmdrpcr() {
		return gjrmdrpcr;
	}


	public void setGjrmdrpcr(int gjrmdrpcr) {
		this.gjrmdrpcr = gjrmdrpcr;
	}


	public String getGplmdrp() {
		return gplmdrp;
	}


	public void setGplmdrp(String gplmdrp) {
		this.gplmdrp = gplmdrp;
	}


	public int getOdgperp() {
		return odgperp;
	}


	public void setOdgperp(int odgperp) {
		this.odgperp = odgperp;
	}


	public int getOmdperp() {
		return omdperp;
	}


	public void setOmdperp(int omdperp) {
		this.omdperp = omdperp;
	}


	public int getOjrperp() {
		return ojrperp;
	}


	public void setOjrperp(int ojrperp) {
		this.ojrperp = ojrperp;
	}


	public String getOplperp() {
		return oplperp;
	}


	public void setOplperp(String oplperp) {
		this.oplperp = oplperp;
	}


	public String getOakperp() {
		return oakperp;
	}


	public void setOakperp(String oakperp) {
		this.oakperp = oakperp;
	}


	public String getOdoperp() {
		return odoperp;
	}


	public void setOdoperp(String odoperp) {
		this.odoperp = odoperp;
	}


	public String getGegperp() {
		return gegperp;
	}


	public void setGegperp(String gegperp) {
		this.gegperp = gegperp;
	}


	public String getGegvdrp() {
		return gegvdrp;
	}


	public void setGegvdrp(String gegvdrp) {
		this.gegvdrp = gegvdrp;
	}


	public String getGegmdrp() {
		return gegmdrp;
	}


	public void setGegmdrp(String gegmdrp) {
		this.gegmdrp = gegmdrp;
	}


	public String getProblmp() {
		return problmp;
	}


	public void setProblmp(String problmp) {
		this.problmp = problmp;
	}


	public int getPsbdgp() {
		return psbdgp;
	}


	public void setPsbdgp(int psbdgp) {
		this.psbdgp = psbdgp;
	}


	public int getPsbmdp() {
		return psbmdp;
	}


	public void setPsbmdp(int psbmdp) {
		this.psbmdp = psbmdp;
	}


	public int getPsbjrp() {
		return psbjrp;
	}


	public void setPsbjrp(int psbjrp) {
		this.psbjrp = psbjrp;
	}


	public String getPsbnrp() {
		return psbnrp;
	}


	public void setPsbnrp(String psbnrp) {
		this.psbnrp = psbnrp;
	}


	public String getOpdrnr() {
		return opdrnr;
	}


	public void setOpdrnr(String opdrnr) {
		this.opdrnr = opdrnr;
	}


	public String getDatum() {
		return datum;
	}


	public void setDatum(String datum) {
		this.datum = datum;
	}


	public String getInit() {
		return init;
	}


	public void setInit(String init) {
		this.init = init;
	}


	public String getVersie() {
		return versie;
	}


	public void setVersie(String versie) {
		this.versie = versie;
	}


	public String getOnderzko() {
		return onderzko;
	}


	public void setOnderzko(String onderzko) {
		this.onderzko = onderzko;
	}


	public String getOpdrnro() {
		return opdrnro;
	}


	public void setOpdrnro(String opdrnro) {
		this.opdrnro = opdrnro;
	}


	public String getDatumo() {
		return datumo;
	}


	public void setDatumo(String datumo) {
		this.datumo = datumo;
	}


	public String getInito() {
		return inito;
	}


	public void setInito(String inito) {
		this.inito = inito;
	}


	public String getVersieo() {
		return versieo;
	}


	public void setVersieo(String versieo) {
		this.versieo = versieo;
	}

	

	public String getOrderNumberI() {
		return orderNumberI;
	}


	public void setOrderNumberI(String orderNumberI) {
		this.orderNumberI = orderNumberI;
	}


	public ArrayList<PkBrp> getProfessions() {
		return professions;
	}


	public void setProfessions(ArrayList<PkBrp> professions) {
		this.professions = professions;
	}


	public B4_ST getB4() {
		return b4;
	}


	public void setB4(B4_ST b4) {
		this.b4 = b4;
	}


	public ArrayList<PkAdres> getAddresses() {
		return addresses;
	}


	public void setAddresses(ArrayList<PkAdres> addresses) {
		this.addresses = addresses;
	}


	public ArrayList<PkEigknd> getChildren() {
		return children;
	}


	public void setChildren(ArrayList<PkEigknd> children) {
		this.children = children;
	}


	public ArrayList<PkHuw> getMarriages() {
		return marriages;
	}


	public void setMarriages(ArrayList<PkHuw> marriages) {
		this.marriages = marriages;
	}


	public ArrayList<PkByz> getRemarks() {
		return remarks;
	}


	public void setRemarks(ArrayList<PkByz> remarks) {
		this.remarks = remarks;
	}


	public P7 getP7() {
		return p7;
	}


	public void setP7(P7 p7) {
		this.p7 = p7;
	}


	public ArrayList<P8> getP8() {
		return p8;
	}


	public void setP8(ArrayList<P8> p8) {
		this.p8 = p8;
	}

	
   	
   	
}
