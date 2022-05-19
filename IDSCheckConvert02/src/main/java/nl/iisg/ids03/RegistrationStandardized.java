/*
 * Naam:    RegistrationStandardized
 * Version: 0.1
 *  Author: Cor Munnik
 * Copyright
 */

package nl.iisg.ids03;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.iisg.hsncommon.Common1;
import nl.iisg.hsncommon.ConstRelations2;
import nl.iisg.hsncommon.ConstRelations;
import nl.iisg.ref.*;


/**
 * 
 * This class handles a standardized registration
 *
 */

@Entity
@Table(name="b4_st")
public class RegistrationStandardized {

	@Id @Column(name = "B1IDBG")	  	private int       keyToSourceRegister;
	@Id @Column(name = "B2DIBG")  		private String    entryDateHead;                      
	@Id @Column(name = "IDNR")   		private int       keyToRP;     

	@Column(name = "B2FDBG")      		private String    entryDateRP;

	@Column(name = "REGISTER_PAGE")     private String    pageNumber;                    
	@Column(name = "REGISTER_LINE")     private int       numberOfHousehold;                  
	@Column(name = "NAME_HEAD_GK")      private String    nameHeadGK;       // GK = GezinsKaart = FamilyCard           
	@Column(name = "SPECIAL_CODE")      private String    specialCode;               
	@Column(name = "SPECIAL_REMARKS")   private String    remarks;  

	@Column(name = "VERSIE")      		private String    versionLastTimeOfDataEntry;
	@Column(name = "ONDRZKO")     		private String    researchCodeOriginal;
	@Column(name = "VERSIEO")     		private String    versionOriginalDataEntry;
	@Column(name = "DATUM")     		private String    date0;

	@Transient                          private Registration originalRegistration;
	@Transient                          private ArrayList<PersonStandardized> personsStandardizedInRegistration   = new ArrayList<PersonStandardized>();
	@Transient  						private ArrayList<RegistrationAddressStandardized>   addressesStandardizedOfRegistration = new ArrayList<RegistrationAddressStandardized>();
	
	@Transient  				        private OP        op = null;
	@Transient  				        private ArrayList<Marriages> marriagesHead = 		 new ArrayList<Marriages>();


	
	RegistrationStandardized(){} // No-args constructor for JPA
	
	/**
	 * 
	 * This routine initializes a RegistrationStandardized object from a Registration object 
	 * 
	 * 
	 * @param r
	 */
	
	RegistrationStandardized(Registration r){ 
		
		setKeyToSourceRegister(r.getKeyToSourceRegister());
		setKeyToRP(r.getKeyToRP());
		
		setPageNumber(r.getPageNumberOfSource());
		setNumberOfHousehold(r.getNumberOfHousehold());
		setNameHeadGK(r.getInfoFamilyCardsSystem());
		setSpecialCode(r.getSpecialDataEntryCodes());
		setRemarks(r.getRemarks());
		
		setVersionLastTimeOfDataEntry(r.getVersionLastTimeOfDataEntry());
		setResearchCodeOriginal(r.getResearchCodeOriginal());
		setVersionOriginalDataEntry(r.getVersionOriginalDataEntry());
		
	}
	
    public void transform(Registration r){
		
    	
		
		//int x = 1/0;

		transformHeadOfHouseholdDate(r);
		transformEntryDateResearchPerson(r);
		

	}
	
    public void transformHeadOfHouseholdDate(Registration r){
		
    	if(Common1.dateIsValid(r.getDayEntryHead() , r.getMonthEntryHead(), r.getYearEntryHead()) == 0) {
    		String temp = String.format("%02d-%02d-%04d", r.getDayEntryHead() , r.getMonthEntryHead(), r.getYearEntryHead());
    		setEntryDateHead(temp);		
    	}
    }

    public void transformEntryDateResearchPerson(Registration r){
		
    	if(Common1.dateIsValid(r.getDayEntryRP(), r.getMonthEntryRP(), r.getYearEntryRP()) == 0) {
    		String temp = String.format("%02d-%02d-%04d", r.getDayEntryRP(), r.getMonthEntryRP(), r.getYearEntryRP());
    		setEntryDateRP(temp);		
    	}
    }

    public void print(){

    	showFields();
    	//System.out.println();
    	//if(getMarriagesHead().size() != 0){
    	//	System.out.println("Marriages Head: ");
    	//	for(Marriages marriage: getMarriagesHead())
    	//		marriage.print();
    	//}
   		System.out.println();
    	for(PersonStandardized ps: personsStandardizedInRegistration){
    		ps.print();
    	}
    	//System.out.println();
   		
   		for(RegistrationAddressStandardized ras: getAddressesStandardizedOfRegistration())
   			ras.print();
   		
    }
    
    /**
	 * 
	 * This routine looks at all persons in the registry and and their dynamic data elements with civil status information
	 * If it finds such an element, and it has a related person, this related person is inspected
	 * Its dynamic data elements with civil status information are searched to see if there one which has related person = original person
	 * If not, such a record is created from data of the original dynamic data element
	 * 
	 * 
	 */

    public void improveReciprocity(){

    	ArrayList<PersonDynamicStandardized> a = new ArrayList<PersonDynamicStandardized>();
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){

    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){

    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT && pds.getValueOfRelatedPerson() != 0){

    				for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){

    					if(ps1.getKeyToPersons() == pds.getValueOfRelatedPerson()){

    						boolean found = false;
    						for(PersonDynamicStandardized pds1: ps1.getDynamicDataOfPersonStandardized()){
    							if(pds1.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT && pds1.getValueOfRelatedPerson() == ps.getKeyToPersons()){
    								found = true;
    								break;
    								
    							}
    						}
    						if(found == false){
    							
    							PersonDynamicStandardized pdsnew = pds.copyPersonDynamicStandardized();
    							pdsnew.setKeyToRegistrationPersons(ps1.getKeyToPersons());
    							pdsnew.setValueOfRelatedPerson(ps.getKeyToPersons());
    							pdsnew.setNatureOfPerson(ps1.getNatureOfPerson());
    							pdsnew.setOriginalPersonDynamic(null);    							
    							pdsnew.setPersonStandardizedToWhomDynamicDataRefers(ps1);
    							a.add(pdsnew);
    							
    						}
    					}
    				}
    			}

    		}
    	}
    	
    	for(PersonDynamicStandardized pds: a)
    		pds.getPersonStandardizedToWhomDynamicDataRefers().getDynamicDataOfPersonStandardized().add(pds);

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration())
    		PersonDynamicStandardized.renumber(ps.getDynamicDataOfPersonStandardized());
         			

    }
    /**
	 * 
	 * This routine looks at all persons in the registry who died and their dynamic data elements with civil status marriage information
	 * If it finds such an element, and it has a related person, this related person is inspected
	 * Its dynamic data elements with civil status information are searched to see if there one which has related person = original person and status = widowed, 
	 * with the correct date (date of decease of original person).
	 * If not, such a record is created from data of the original dynamic data element
	 * If the date of the dynamic data element is not specified, it is updated with the value of date of decease of original person (no new record)
	 * 
	 * 
	 */

    public void improveReciprocity2(){

   	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		
    		if(Common1.dateIsValid(ps.getDateOfDecease()) != 0) continue;
    		
    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){

    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT && pds.getValueOfRelatedPerson() != 0 &&
    					((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){

    				for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){

    					if(Common1.dateIsValid(ps1.getDateOfDecease()) != 0) continue;
    					
    					if(!ps1.getDateOfDecease().equals("00-00-0000") && Utils.dayCount(ps1.getDateOfDecease()) <= Utils.dayCount(ps.getDateOfDecease()))  // person dead already
    						continue;
    					
    					if(ps1.getKeyToPersons() == pds.getValueOfRelatedPerson()){

    						boolean found = false;
    						for(PersonDynamicStandardized pds1: ps1.getDynamicDataOfPersonStandardized()){
    							if(pds1.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT && ((PDS_CivilStatus)pds1).getContentOfDynamicData() == ConstRelations2.WEDUWNAAR_WEDUWE &&    									
    									pds1.getValueOfRelatedPerson() == ps.getKeyToPersons()){
    								if(Common1.dateIsValid(pds1.getDateOfMutation2()) == 0 && 	Common1.dayCount(ps.getDateOfDecease()) - Utils.dayCount(pds1.getDateOfMutation2()) <= 10){
        								found = true;
        								break;
    								}
    								else
    									if(pds1.getDateOfMutation2() != null && pds1.getDateOfMutation2().equals("00-00-0000")){
    										pds1.setDateOfMutation(ps.getDateOfDecease());
    										found = true;
    										break;
    									}
    							}
    						}
    						if(found == false){
    						
    							// we must add a widowed civil status at the right place, after the marriage to the deceased person

    							int count = 0;
        						for(PersonDynamicStandardized pds1: ps1.getDynamicDataOfPersonStandardized()){
        						    count++;
        							if(pds1.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT && ((PDS_CivilStatus)pds1).getContentOfDynamicData() == ConstRelations2.GEHUWD &&    									
        									pds1.getValueOfRelatedPerson() == ps.getKeyToPersons())
        								break; // found marriage pds to the deceased person
        						}

    							
    							PersonDynamicStandardized pdsnew = pds.copyPersonDynamicStandardized();
    							((PDS_CivilStatus)pdsnew).setContentOfDynamicData(ConstRelations2.WEDUWNAAR_WEDUWE);
    							pdsnew.setDateOfMutation(ps.getDateOfDecease());
    							pdsnew.setKeyToRegistrationPersons(ps1.getKeyToPersons());
    							pdsnew.setValueOfRelatedPerson(ps.getKeyToPersons());
    							pdsnew.setNatureOfPerson(ps1.getNatureOfPerson());
    							pdsnew.setOriginalPersonDynamic(null);    							
    							pdsnew.setPersonStandardizedToWhomDynamicDataRefers(ps1);
    							//a.add(pdsnew);
    							if(count > 0)
    								ps1.getDynamicDataOfPersonStandardized().add(count, pdsnew);
    							
    						}
    					}
    				}
    			}

    		}
    	}
    	
    	//for(PersonDynamicStandardized pds: a)
    		//pds.getPersonStandardizedToWhomDynamicDataRefers().getDynamicDataOfPersonStandardized().add(pds);

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration())
    		PersonDynamicStandardized.renumber(ps.getDynamicDataOfPersonStandardized());
         			

    }
    
    /**
     * This routine looks for persons who arrived as a group
     * This means 1) undated arrival from same origin OR
     *            2) dated (same) arrival and no origin
     * 
     */
    
    public void CreateEqualArrivalGroups(){
    	
    	String previousDate        = "";
    	String previousOrigin      = "";
    	int    previousGroupNumber = 0;
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){

    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){

    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    				
    				// System.out.println("---> " + ((PDS_PlaceOfOrigin)pds).getOriginStandardized());

    				boolean add = false;
    				
    				if(pds.getDateOfMutation2() != null && pds.getDateOfMutation2().equals("00-00-0000")){

    					if(previousDate.equals("00-00-0000")){ 

    						if(((PDS_PlaceOfOrigin)pds).getOriginStandardized().trim().equals(previousOrigin) && previousOrigin.length() != 0) 
    							add = true;

    					}

    				}
    				else{
    					if(pds.getDateOfMutation2() != null && pds.getDateOfMutation2().equals(previousDate) && !previousDate.equals("00-00-0000"))
    						if(((PDS_PlaceOfOrigin)pds).getOriginStandardized().trim().equals(previousOrigin))
    							add = true;

    						else
    							add = false;	

    				}
    				
    				// Check if person not already in group
    				
    				if(add == true){

    					for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){

    						if(ps1 == ps)
    							break;
    						
    						if(ps1.getPersonID() == ps.getPersonID()){
    							
    							for(PersonDynamicStandardized pds1:  ps1.getDynamicDataOfPersonStandardized()){
    								
    								if(pds1.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    									
    									if(((PDS_PlaceOfOrigin)pds).getOriginGroup() == previousGroupNumber){
    										
    										add = false;
    										break;
    										
    									}
    								}
    							}
    						}
    					}
    				}
    				
                    if(add == false)
                    	previousGroupNumber++;
                    
                    ((PDS_PlaceOfOrigin)pds).setOriginGroup(previousGroupNumber);    				
    				previousDate =  pds.getDateOfMutation2();
    				previousOrigin = ((PDS_PlaceOfOrigin)pds).getOriginStandardized().trim();
    				
    				break;
    			}
    		}
    	}

    }

    /**
     * This routine looks for persons who departed as a group
     * This means 1) undated departure to same destination OR
     *            2) dated (same) and no destination
     * 
     */
    
    public void CreateEqualDepartureGroups(){
    	
    	String previousDate        = "";
    	String previousDestination      = "";
    	int    previousGroupNumber = 0;
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){

    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){

    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK){
    				
    				boolean add = false;
    				
    				if(pds.getDateOfMutation2() != null && pds.getDateOfMutation2().equals("00-00-0000")){

    					if(previousDate.equals("00-00-0000")){ 

    						if(((PDS_PlaceOfDestination)pds).getDestinationStandardized().trim().equals(previousDestination) && previousDestination.length() != 0) 
    							add = true;

    					}

    				}
    				else{
    					if(pds.getDateOfMutation2() != null && pds.getDateOfMutation2().equals(previousDate) && !previousDate.equals("00-00-0000"))
    						if(((PDS_PlaceOfDestination)pds).getDestinationStandardized().trim().equals(previousDestination))
    							add = true;

    						else
    							add = false;	

    				}
    				
    				// Check if person not already in group
    				
    				if(add == true){

    					for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){

    						if(ps1 == ps)
    							break;
    						
    						if(ps1.getPersonID() == ps.getPersonID()){
    							
    							for(PersonDynamicStandardized pds1:  ps1.getDynamicDataOfPersonStandardized()){
    								
    								if(pds1.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK){
    									
    									if(((PDS_PlaceOfDestination)pds).getDestinationGroup() == previousGroupNumber){
    										
    										add = false;
    										break;
    										
    									}
    								}
    							}
    						}
    					}
    				}
    				
                    if(add == false)
                    	previousGroupNumber++;
                    
                    ((PDS_PlaceOfDestination)pds).setDestinationGroup(previousGroupNumber);    				
    				previousDate =  pds.getDateOfMutation2();
    				previousDestination = ((PDS_PlaceOfDestination)pds).getDestinationStandardized().trim();
    				
    				break;
    			}
    		}
    	}

    }

    
    
    /**
     * 
     * This routine looks for records that have subsequent records with an earlier date
     * It moves these records after the subsequent record with the most near (=latest) earlier date
     * 
     */
    
    public void MoveLaterDatesAfterEarlierDates(){

    	boolean cont = true;
    	while(cont == true){
    		
 			PersonStandardized ps3 = null;    		
 			PersonStandardized ps4 = null;    		

    		for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){

    			if(Common1.dateIsValid(ps1.getStartDate()) != 0) continue;
    			
    			int ps1Count = Utils.dayCount(ps1.getStartDate());

    			boolean start = false;
   
    			ps3 = null;
    			ps4 = ps1;
    			for(PersonStandardized ps2: getPersonsStandardizedInRegistration()){

    				if(ps1 == ps2){
    					start = true;
    					continue;
    				}

    				if(start == true)
    					if(Common1.dateIsValid(ps2.getStartDate()) == 0 && Utils.dayCount(ps2.getStartDate()) < ps1Count) 
    						if(ps3 == null || Common1.dateIsValid(ps3.getStartDate()) == 0 && Utils.dayCount(ps2.getStartDate()) > Utils.dayCount(ps3.getStartDate()))
    							ps3 = ps2;
    			}
    			
    			if(ps3 != null)
    				break;

    		}
    		
    		//System.out.println("ps 3 = " + ps3 + ", ps4 = " + ps4);
    		
    		if(ps3 != null && ps4 != null){
    			
    			int i4 = getPersonsStandardizedInRegistration().indexOf(ps4);    			
    			getPersonsStandardizedInRegistration().remove(i4);
    			int i3 = getPersonsStandardizedInRegistration().indexOf(ps3);
    			getPersonsStandardizedInRegistration().add(i3 + 1, ps4);
    			message("4361", 0);
    			//System.out.println("Moved Person from " + i4 + " to " + i3);
    		}
    		else
    			cont = false;
    	}

    }

    /**
     * 
     * This routines renumbers the PersonStandardized and their PersonDynamicStandardized
     * starting from 1 and in the order they are now sorted in
     * It also updates the related person in PersonDynamicStandardized
     * (B3RGLN - valueOfRelatedPerson)
     * 
     * 
     */
    public void renumber(){
    	
    	int currentNumber = 1;
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		
    		int oldNumber = ps.getKeyToPersons();
    		ps.setKeyToPersons(currentNumber);
    		
    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized())
    			pds.setKeyToRegistrationPersons(currentNumber);
    		
    		// Also renumber the sequence numbers of the dd elements
    		
    		PersonDynamicStandardized.renumber(ps.getDynamicDataOfPersonStandardized());
    		
    		// now we must check all persons to see if they have this person as related person
    		
        	for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
        		for(PersonDynamicStandardized pds1: ps1.getDynamicDataOfPersonStandardized()){
        			if(pds1.getValueOfRelatedPerson() == oldNumber)
        				pds1.setValueOfRelatedPerson(currentNumber + 100); // this is to keep original values and values set in this routine apart
        		}
        	}
    		
    		currentNumber++;		
    		
    	}
    	
    	// now we must subtract 100 for those related persons > 100
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		for(PersonDynamicStandardized pds1: ps.getDynamicDataOfPersonStandardized()){
    			if(pds1.getValueOfRelatedPerson() > 100)
    				pds1.setValueOfRelatedPerson(pds1.getValueOfRelatedPerson() - 100); // this is to keep original values and values set in this routine apart
    		}
    	}
    	
    	
    	
    }
    
    /**
     * 
     * This routine gives "end of source date" to all entries that do no have a date yet
     * 
     * Secondly, it looks for end dates that are later than the end of source date 
     * If end-flag in 54-60, the entry is truncated at the end of source date.
     * If end-flag in 51-53, an extension up to two year is allowed
     * 
     */


    public void setEndOfRegisterDate(){


    	Ref_AINB ainb = Ref.getAINB(getKeyToSourceRegister());		
    	if(ainb == null) return;
    	
    	int endYear = ainb.getEndYearRegister();
    	if(endYear == 0) return;
    	int endCount  = Utils.dayCount("31-12-" + endYear); 
    	int endCount2 = Utils.dayCount("31-12-" + (endYear + 2)); 


    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){

    		if(ps.getEndDate() != null && ps.getEndDate().length() == 10){  // because we had a partial date once
    			
    			if(Utils.dateIsValid(ps.getEndDate()) == 0 && Utils.dayCount(ps.getEndDate()) > endCount){

    				if(ps.getEndFlag() >= 54){
    					ps.setEndDate("31-12-" + endYear); 
    					ps.setEndEst(141);
    					ps.setEndFlag(61); 
    				}
    				else{
    					if(Utils.dayCount(ps.getEndDate()) > endCount2){
    						ps.setEndDate("31-12-" + (endYear + 2)); 
    						ps.setEndEst(141);
    						ps.setEndFlag(61);
    						// 
    					}
    					message("4365", ps.getKeyToPersons());
    				}
    			}
    		}
    		else{
    			ps.setEndDate("31-12-" + endYear); 
    			ps.setEndEst(141);
    			ps.setEndFlag(61);    				
    		}
    	}
    }

    /**
     * 
     * This routine estimates records that still have start flag = 1
     * 
     */
    
    public void minMax(){
    	
    	
    	//System.out.println("AAAAAA");
    	//for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    	//	System.out.println("" + ps.getKeyToPersons() + " " + ps.getStartDate() + " " + ps.getStartFlag());
    	//	
    	//}
	
		// set maximum start date = end date of same record in this registration

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(!cont(ps))
    			continue;
    		if(ps.getEndFlag() <= 57)
    			if(Common1.dateIsValid(ps.getEndDate()) == 0)
    				ps.setMaxStartDate(ps.getEndDate());
    	}

		// check if person in  group, if so set lowest of group dates

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		
    		if(!cont(ps) || ps.getMaxStartDate() == null || Utils.dateIsValid(ps.getMaxStartDate()) != 0)
    			continue;

    		
    		//System.out.println("---" + ps.getKeyToPersons());
    		
			String minStartDate = ps.getMaxStartDate();
    		int minStartDays = Utils.dayCount(minStartDate);

    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    			
    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    				
    				int group = ((PDS_PlaceOfOrigin)pds).getOriginGroup();
    				
    				// Check all other group members
    				
    				for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    		    		for(PersonDynamicStandardized pds1: ps1.getDynamicDataOfPersonStandardized()){
    		    			if(pds1.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    		    				if(((PDS_PlaceOfOrigin)pds1).getOriginGroup() == group){
    		    					if(ps1.getMaxStartDate() != null && Utils.dateIsValid(ps1.getMaxStartDate()) == 0 && Utils.dayCount(ps1.getMaxStartDate()) < minStartDays){
    		    						minStartDate = ps1.getMaxStartDate();
    		    						minStartDays = Utils.dayCount(minStartDate);
    		    					}
    		    				}
    		    			}
    		    		}
    				}    				
    			}
    		}
    		
    		ps.setMaxStartDate(minStartDate);
    	}

		// Find next Person with start flag != 1

    	boolean start = false;
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(!cont(ps))
    			continue;
    		
    		//System.out.println("+++" + ps.getKeyToPersons());
    		PersonStandardized psNext = null;
    		for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    			if(ps1 == ps){
    				start = true;
    				continue;    				
    			}
    			
    			if(start == true){    				
    				if(!cont(ps1)){
    					psNext = ps1;
    					break;
    				}
    			}
    		}
    		
    	    if(psNext != null){	
    	    	ps.setMaxStartDate(psNext.getStartDate());
        		//System.out.println("@@@" + ps.getKeyToPersons() + "   " + psNext.getStartDate());

    	    }
    	    else{
        		//System.out.println("&&&" + ps.getKeyToPersons());
    	    	Ref_AINB ainb = Ref.getAINB(getKeyToSourceRegister());
    	    	if(ainb != null && ainb.getEndYearRegister() > 0) {
    	    		int endYear = ainb.getEndYearRegister();
    	    		ps.setMaxStartDate("31-12-" + endYear);
    	    		ps.setEndEst(143);
    	    	}
    	    }
    	}    	

		// set minimum start date = earlier end date of same person in this registration
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(!cont(ps))
    			continue;
    		
    		PersonStandardized psBefore = null;
        	for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
        		
        		if(ps1 == ps)
        			break;
        		
        		if(ps1.getPersonID() == ps.getPersonID())
        			psBefore = ps1;
        		
        	}
        	
        	if(psBefore != null && psBefore.getEndFlag() <= 57) {
    			//System.out.println("ABBC 1 " + psBefore.getEndDate());

        		ps.setMinStartDate(psBefore.getEndDate());
        	}
    		
    	}	

    	
    	
		// check if person has minStartDate 
		// if person in group, set all group members to this minStartDate

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(!cont(ps))
    			continue;
    		
    		if(ps.getMinStartDate() == null)
    			continue;
    		
    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    			
    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    				
    				int group = ((PDS_PlaceOfOrigin)pds).getOriginGroup();
    				
    				// Check all other group members
    				
    				for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    		    		for(PersonDynamicStandardized pds1: ps1.getDynamicDataOfPersonStandardized()){
    		    			if(pds1.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    		    				if(((PDS_PlaceOfOrigin)pds1).getOriginGroup() == group){
    		    	    			//System.out.println("ABBC 4 " + ps.getMinStartDate());

    		    					ps1.setMinStartDate(ps.getMinStartDate());
    		    				}
    		    			}
    		    		}
    				}    				
    			}
    		}

    	}
    	
    	// Starting date of person just before group/person
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(!cont(ps))
    			continue;
    		
    		int group = 0;
    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    				 group = ((PDS_PlaceOfOrigin)pds).getOriginGroup();
    				 break;
    			}
    		}
    		
    		PersonStandardized psPrevious = null;
    		for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    			
    			if(ps1 == ps)   				
    				break;
    			for(PersonDynamicStandardized pds: ps1.getDynamicDataOfPersonStandardized()){
        			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
        				 if(((PDS_PlaceOfOrigin)pds).getOriginGroup() == group)
        					 break;
        			}
    			}
    			psPrevious = ps1;
    			
    		}
    			
    		if(psPrevious != null) {
    			//System.out.println("ABBC 2 " + psPrevious.getStartDate());

    			ps.setMinStartDate(psPrevious.getStartDate());
    		}
    		else {
    			//System.out.println("ABBC 3 " + ps.getEntryDateHead());

    			ps.setMinStartDate(ps.getEntryDateHead());
    		}
    		
    	}
    	
    	//System.out.println("BBBBBB");
    	//for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    	//	System.out.println("" + ps.getKeyToPersons() + " " + ps.getMinStartDate() + " " + ps.getMaxStartDate());
    	//	
    	//}
	


    	// Now, estimate the start dates
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(!cont(ps))
    			continue;
    		
    		int group = 0;
    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    				 group = ((PDS_PlaceOfOrigin)pds).getOriginGroup();
    				 break;
    			}
    		}
    		
    		// count group members
    		
    		int groupCount = 1;
        	for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
        		for(PersonDynamicStandardized pds: ps1.getDynamicDataOfPersonStandardized()){
        			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
        				 if(ps1 != ps && ((PDS_PlaceOfOrigin)pds).getOriginGroup() == group){
        					 groupCount++;
        				     break;
        				 }
        			}
        		}
        	}
    		
    		int interval = 0;
    		if(Common1.dateIsValid(ps.getMaxStartDate()) == 0 && Common1.dateIsValid(ps.getMinStartDate()) == 0)
    			interval = (Utils.dayCount(ps.getMaxStartDate()) - Utils.dayCount(ps.getMinStartDate())) / (groupCount + 1);
    			
    		if(group == 0){    	
    			//System.out.println("ABBC " + ps.getMinStartDate());
    			ps.setStartDate(Utils.dateFromDayCount(Utils.dayCount(ps.getMinStartDate()) + interval));
    			ps.setStartFlag(11);	
    		}
    		else{    			
    			int index = 0;
    			//System.out.println("Start loop, interval = " + ps.getMinStartDate() + " - " + ps.getMaxStartDate());
    			for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    				//System.out.println("In loop, index = " + index);
    				for(PersonDynamicStandardized pds: ps1.getDynamicDataOfPersonStandardized()){
    					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST){
    						if(((PDS_PlaceOfOrigin)pds).getOriginGroup() == group){
    							if(Common1.dateIsValid(ps1.getMaxStartDate()) == 0 && Common1.dateIsValid(ps1.getMinStartDate()) == 0) {
    								ps1.setStartDate(Utils.dateFromDayCount(Utils.dayCount(ps.getMinStartDate()) + (interval * index)));
    								index++;
    								//System.out.println("--> " + ps1.getStartDate() + "   " + ps1.getRegistrationStandardizedPersonAppearsIn().getKeyToRP() + " " + ps.getStartFlag());
    								ps1.setStartFlag(11);  	
    								break;
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	// Check if there are persons with start date > end date
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(Utils.dateIsValid(ps.getStartDate()) == 0 && Utils.dateIsValid(ps.getEndDate()) == 0 && Utils.dayCount(ps.getStartDate()) > Utils.dayCount(ps.getEndDate()))
    			//	message("7136", "" + ps.getPersonID());
    			;
    	}
    	
   
    }
    
    private static boolean cont(PersonStandardized ps){
    	
    	if(ps.getStartFlag() == 1)
    		return true;
    	
    	if(ps.getStartFlag() == 8 || ps.getStartFlag() == 9) // more records or group can still have head date 
    		if(Common1.dateIsValid(ps.getStartDate()) == 0 && Common1.dateIsValid(ps.getEntryDateHead()) == 0)
    			if(ps.getStartDate().equals(ps.getEntryDateHead()))
    	    		return true;
    	    
   	    return false;
    	
    	
    }
    
    /**
     * 
     * This routine looks at persons (A) that have
     * 
     * -- end flag = 61
     * -- an undated departure
     * 
     * First, for all persons in the registration that have end flag != 61 
     * the highest end date is found. Then, all persons (A) get this end date, 
     * provided it is later than their start date. If it is earlier, 
     * their end date is put a start year + 1
     * 
     * 
     * 
     */
    
    
    public void handleUndatedDeparture(){
    	
    	// get highest end date with flag != 61
    	
    	String highestEndDate = "01-01-1600";
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration())
    		if(ps.getEndFlag() != 61  && Utils.dateIsValid(ps.getEndDate()) == 0 &&
    				Utils.dateIsValid(highestEndDate) == 0 && Utils.dayCount(ps.getEndDate()) > Utils.dayCount(highestEndDate))
    			highestEndDate = ps.getEndDate();
    		
    	
    	// locate persons with undated departure and endflag == 61

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(ps.getEndFlag() == 61){ 
    			for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    				if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK && pds.getDateOfMutation2().equals("00-00-0000")){
    					if(Utils.dateIsValid(ps.getStartDate()) == 0 && Utils.dateIsValid(highestEndDate) == 0 && 
    							Utils.dayCount(highestEndDate) > Utils.dayCount(ps.getStartDate()))  
    						ps.setEndDate(highestEndDate);
    					else
    						if(Utils.dateIsValid(ps.getStartDate()) == 0)
    							ps.setEndDate(Utils.dateFromDayCount((Utils.dayCount(ps.getStartDate())) + 365));
    					ps.setEndFlag(62);
    				}
    			}
    		}
    	}
    }
    
    /**
     * 
     * This routine gives start -end dates to the dynamic data elements in the registration
     * 
     */
    
    
    public void dateDynamicElements(){
    	
		// set the dating info
		
		for(PersonStandardized ps: getPersonsStandardizedInRegistration())
			ps.dateDynamicElements();
		
		dateAddresses();
    	
    }
    
    /**
     * 
     * This module looks for marriages of
     * 
     * 1 the head
     * 2 one new explicit head
     * 
     *  It builds an explicit structure for the marriages 
     *  It also gives the heads a new code for their relation to head
     *  
     *  The following messaqes are issued by this routine
     *  
     *  4152
     *  4459
     * 
     */
    
    public void buildMarriageStructure(){

    	
    	// Split code HOOFD in 3 different codes   	
    	
    	PersonStandardized head = null;
    	PersonStandardized headSuccessor = null;
    	
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		
    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    			
    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){
    				
    				if(((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.HOOFD){
    					
    					if(getPersonsStandardizedInRegistration().size() == 1){
    						head = ps;
    						((PDS_RelationToHead)pds).setContentOfDynamicData(ConstRelations2.EXPLICIET_HOOFD_ALLENSTAAND);
    					}
    					else{
    						if(ps.getOriginalPerson().getIsHead() == true && ps.getOriginalPerson().getIsHeadFirstSuccessor() == false){
    							head = ps;
    							((PDS_RelationToHead)pds).setContentOfDynamicData(ConstRelations2.EXPLICIET_HOOFD);
    						}
    						else{
    							if(ps.getOriginalPerson().getIsHeadFirstSuccessor() == true){   
    								headSuccessor = ps;
    								((PDS_RelationToHead)pds).setContentOfDynamicData(ConstRelations2.EXPLICIET_HOOFD_EERSTE_OPVOLGER );
    							}
    						}
    					}
    				}
    				
    			}
    		}
    	}		
    		
    	// Find marriages of Head
    	

    	if(head != null){

    		for(PersonDynamicStandardized pds: head.getDynamicDataOfPersonStandardized()){


    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
    				if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){

    					PersonStandardized partner = null;
    					for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){

    						if(ps1.getKeyToPersons() == pds.getValueOfRelatedPerson()){
    							partner = ps1;
    							break;
    						}
    					}
    					if(partner != null){	
    						Marriages marriage = new Marriages();
    						marriage.setHead(head);
    						marriage.setSpouse(partner);
    						marriage.setStartDate(pds.getStartDate());
    						marriage.setEndDate(pds.getEndDate());
    						getMarriagesHead().add(marriage);
    					}  	

    				}
    			}
    		}
    	}

    	
    	// Find marriages of Successor
    	
    	
    	if(headSuccessor != null){


    		for(PersonDynamicStandardized pds: headSuccessor.getDynamicDataOfPersonStandardized()){

    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
    				if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){

    					PersonStandardized partner = null;
    					for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){

    						if(ps1.getKeyToPersons() == pds.getValueOfRelatedPerson()){
    							partner = ps1;
    							break;
    						}
    					}
    					if(partner != null){	
    						Marriages marriage = new Marriages();
    						marriage.setHead(headSuccessor);
    						marriage.setSpouse(partner);
    						marriage.setStartDate(pds.getStartDate());
    						marriage.setEndDate(pds.getEndDate());
    						getMarriagesHead().add(marriage);
    					}  	

    				}
    			}
    		}
    	}
    	
    	// Now some tests on the new structure
    	
    	Marriages previousMarriage = null;
    	for(Marriages marriage: getMarriagesHead()){
    		if(previousMarriage != null){
    			if(previousMarriage.getHead() == marriage.getHead()){
    				if(Common1.dateIsValid(previousMarriage.getEndDate()) == 0 && Common1.dateIsValid(marriage.getStartDate()) == 0 &&
    				//if(previousMarriage.getEndDate() != null && marriage.getStartDate() != null && 
    						Utils.dayCount(previousMarriage.getEndDate()) > Utils.dayCount(marriage.getStartDate()))
    					if(previousMarriage.getSpouse() != null && marriage.getSpouse() != null &&
    							previousMarriage.getSpouse().getPersonID() != marriage.getSpouse().getPersonID())		
    						message("4152", 0, marriage.getHead().getFamilyName() + ", " +  marriage.getHead().getFirstName());
    			}
    		}
    		previousMarriage = marriage;
    	}

    	
    	String endDate = null;
    	String startDate = null;
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		
    		if(ps.getOriginalPerson().getIsHead() == true && ps.getOriginalPerson().getIsHeadFirstSuccessor() != true){
    			if(Common1.dateIsValid(ps.getEndDate()) == 0)
    				endDate = ps.getEndDate();
    			
    		}
    		if(ps.getOriginalPerson().getIsHeadFirstSuccessor() == true && ps.getOriginalPerson().getIsHead() != true){
    			if(Common1.dateIsValid(ps.getStartDate()) == 0)
    				startDate = ps.getStartDate();
    			break;
    		}
    		
    	}
    	
    	if(Common1.dateIsValid(startDate) == 0 && Common1.dateIsValid(endDate) == 0 &&
    			Utils.dayCount(startDate) < Utils.dayCount(endDate))
    		message("4459", 0, "" + (Utils.dayCount(endDate) - Utils.dayCount(startDate)));
    	

    }
    
    /**
     * This routine establishes the father and the mother of each person in the registration
     * 
     */
    
    public void findFatherAndMother(){

    	// A via line number
    	
    	PersonStandardized ps0 = null;
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		ps0 = null;
    	x:	for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.OUDER_KIND){
    				if(pds.getValueOfRelatedPerson() > 0){
    					//System.out.println("Related person = " + pds.getValueOfRelatedPerson());
    					//System.out.println("Relation: " + ((PDS_ParentsAndChildren)pds).getRelation());    					
    					for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    						if(ps1.getKeyToPersons() == pds.getValueOfRelatedPerson()){
    							ps0 = ps1;
    							if(((PDS_ParentsAndChildren)pds).getContentOfDynamicData() == 11){ // father
    								ps.setPersonID_FA(ps1.getPersonID());
    								ps.setPersonID_FA_FG(((PDS_ParentsAndChildren)pds).getRelation());
    								//System.out.println("Related person is father");
    								break;
    							}
    							else{
    								if(((PDS_ParentsAndChildren)pds).getContentOfDynamicData() == 12){ // mother
    									ps.setPersonID_MO(ps1.getPersonID());
    									ps.setPersonID_MO_FG(((PDS_ParentsAndChildren)pds).getRelation());
    									//System.out.println("Related person is mother");
    									break;
    								}
    							}
    						}
    					}
    					if(ps0 != null)
    						break x;
    				}
    			}
    		}
    		if(ps0 != null){  // found one parent, try find the other one
    			PersonDynamicStandardized pds0 = null;
    			if(Common1.dateIsValid(ps.getDateOfBirth()) == 0) {
    				int searchDays = Utils.dayCount(ps.getDateOfBirth()) + 300;
    				for(PersonDynamicStandardized pds: ps0.getDynamicDataOfPersonStandardized()){
    					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
    						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
    							if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0 && Utils.dayCount(pds.getDateOfMutation2()) < searchDays){
    								pds0 = pds;
    							}
    						}
    					}
    				}
    			}

    			if(pds0 != null){ // found marriage
    				PersonStandardized ps00 = null;
    				for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    					if(ps1.getKeyToPersons() == pds0.getValueOfRelatedPerson()){
    						ps00 = ps1;
    						break;
    					}
    				}

    				if(ps00 != null){ // found related person

    					if(ps.getPersonID_FA() != 0){
    						ps.setPersonID_MO(ps00.getPersonID());
    						ps.setPersonID_MO_FG(ps00.getPersonIDFlag());
    					}
    					else{
    						if(ps.getPersonID_MO() != 0){
    							ps.setPersonID_FA(ps00.getPersonID());
    							ps.setPersonID_FA_FG(ps00.getPersonIDFlag());
    						}
    						else{ // no relation specified
    							if(ps0.getSex().equalsIgnoreCase("V")){
    	    						ps.setPersonID_MO(ps0.getPersonID());
    	    						ps.setPersonID_MO_FG(30);
        							ps.setPersonID_FA(ps00.getPersonID());
        							ps.setPersonID_FA_FG(34);
    							}
    							else{
    	    						ps.setPersonID_MO(ps00.getPersonID());
    	    						ps.setPersonID_MO_FG(34);
        							ps.setPersonID_FA(ps0.getPersonID());
        							ps.setPersonID_FA_FG(30);
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	//if(1==1)return;
    	
    	// B via Birth Certificate 
    	
    	//System.out.println("____ "  + Ref.getAINB(getKeyToSourceRegister()).getTypeRegister());

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){

    		if(ps.getNatureOfPerson() == ConstRelations2.FIRST_APPEARANCE_OF_OP){

    			
    			Ref_RP rp = Ref.getRP(ps.getKeyToRP());
    			
    			if(rp != null){
    				
    				
    				String RPBirthDay = String.format("%02d-%02d-%04d", rp.getDayOfBirthRP(), rp.getMonthOfBirthRP(), rp.getYearOfBirthRP());
    				if(!RPBirthDay.equalsIgnoreCase(ps.getDateOfBirth())) {
    					
    					System.out.format("%s  %s  %s  %s %d %s %s\n", ps.getKeyToRP(), ps.getEntryDateHead(), ps.getKeyToSourceRegister(), ps.getKeyToPersons(), ps.getNatureOfPerson(),
    							ps.getFirstName(), ps.getFamilyName());
    					ps.setDateOfBirth(RPBirthDay);
    					message("4440", ps.getKeyToPersons());
    					
    				}

    				String       lastNameFather   = rp.getLastNameFather();

    				String       firstName1Father = null;
    				String       firstName2Father = null;
    				String       firstName3Father = null;
    				
    				int          firstNameFatherN = 0;
    				

    				if(rp.getFirstNameFather() != null){
    					String[] a  = rp.getFirstNameFather().split(" ");

    					if(a.length > 0 && a[0] != null){
    						firstName1Father = a[0];
    						firstNameFatherN++;

    					}

    					if(a.length > 1 && a[1] != null){
    						firstName2Father = a[1];
    						firstNameFatherN++;
    					}

    					if(a.length > 2 && a[2] != null){
    						firstName3Father = a[2];
    						firstNameFatherN++;
    					}
    				}

    				String       lastNameMother   = rp.getLastNameMother();

    				String       firstName1Mother = null;
    				String       firstName2Mother = null;
    				String       firstName3Mother = null;
    				
    				int          firstNameMotherN = 0; 


    				if(rp.getFirstNameMother() != null){
    					String[] b  = rp.getFirstNameMother().split(" ");

    					if(b.length > 0 && b[0] != null){
    						firstName1Mother = b[0];
    						firstNameMotherN++;

    					}
    					if(b.length > 1 && b[1] != null){
    						firstName2Mother = b[1];
    						firstNameMotherN++;

    					}
    					if(b.length > 2 && b[2] != null){
    						firstName3Mother = b[2];
    						firstNameMotherN++;
    					}
    				}


    				boolean foundFather = false;
    				boolean foundMother = false;
    				
    				for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){  // -

    					   					
    					if(ps1 == ps)
    						continue;
    					
    					if(ps.getPersonID_FA() == ps1.getPersonID() || ps.getPersonID_MO() == ps1.getPersonID()) { // father or mother according to bevolkingsregister
    						
    						
    						if(ps1.getFamilyName() == null) continue;
        					String lastName   = ps1.getFamilyName();

        					if(ps1.getFirstName() == null) continue;
        					String [] firstName = ps1.getFirstName().split(" ");

        					int NfirstNames = firstName.length; 

        					String firstName1 = firstName.length > 0 ? firstName[0] : null;
        					String firstName2 = firstName.length > 1 ? firstName[1] : null;
        					String firstName3 = firstName.length > 2 ? firstName[2] : null;

        					int compareResultLastName = 0;
        					int compareResultFirstName1 = 0;
        					int compareResultFirstName2 = 0;
        					int compareResultFirstName3 = 0;
    						
        					if(ps.getPersonID_FA() == ps1.getPersonID()){

        						if(ps.getKeyToRP() == 200256) System.out.println("cas " + lastName + " " + lastNameFather);
        						compareResultLastName = compareNames(lastName, lastNameFather);
        						if(compareResultLastName > 2) {
        							
        							message("4150", ps.getKeyToPersons(), rp.getLastNameFather(), rp.getFirstNameFather(), ps1.getFamilyName(), ps1.getFirstName());
        							continue;
        				    	}

        						compareResultFirstName1 = compareNames(firstName1, firstName1Father);
        						if(compareResultFirstName1 > 2)
        							continue;

        						int nrMatchingFirstNames = 1;


        						// 2nd names must match or both not specified

        						if(firstName2 != null &&  firstName2Father != null){
        							compareResultFirstName2 = compareNames(firstName2, firstName2Father);
        							if(compareResultFirstName2 > 2){
            							
            							message("4150", ps.getKeyToPersons(), rp.getLastNameFather(), rp.getFirstNameFather(), ps1.getFamilyName(), ps1.getFirstName());
            							continue;
            				    	}
        							nrMatchingFirstNames = 2;
        						}
        						else{
    								if((firstName2 != null &&  firstName2Mother == null) || (firstName2 == null &&  firstName2Mother != null)){
            							
            							message("4150", ps.getKeyToPersons(), rp.getLastNameFather(), rp.getFirstNameFather(), ps1.getFamilyName(), ps1.getFirstName());
            							continue;
            				    	}
        							else; // both null is OK
        						}

        						// 3rd names must match if both specified

        						if(firstName3 != null &&  firstName3Father != null){
        							compareResultFirstName3 = compareNames(firstName3, firstName3Father);
        							if(compareResultFirstName3 > 2)	{
            							
            							message("4150", ps.getKeyToPersons(), rp.getLastNameFather(), rp.getFirstNameFather(), ps1.getFamilyName(), ps1.getFirstName());
            							continue;
            				    	}
        							nrMatchingFirstNames = 3;
        						}
        						
        						foundFather = true;
        						

        						int shiftInMessageTableForCRegister = 0;
        						if(Ref.getAINB(getKeyToSourceRegister()).getTypeRegister().equalsIgnoreCase("C")) // C-Register
        							shiftInMessageTableForCRegister = 10;


        						if(!rp.getLastNameFather().equalsIgnoreCase(ps1.getFamilyName()))
        							message("" + (4435 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(), 
        									rp.getLastNameFather(), ps1.getFamilyName());

        						if(!rp.getFirstNameFather().equalsIgnoreCase(ps1.getFirstName()))
        							message("" + (4436 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(), 
        									rp.getFirstNameFather(), ps1.getFirstName());

        						if(nrMatchingFirstNames == 1  && (NfirstNames > 1 || firstNameFatherN > 1))
        							message("" + (4437 - shiftInMessageTableForCRegister),  ps1.getKeyToPersons(),
        									ps1.getFirstName());

        						if(!rp.getLastNameFather().equalsIgnoreCase(ps.getFamilyName()))  // OP = ps
        							if(Ref.getAINB(getKeyToSourceRegister()).getTypeRegister().equalsIgnoreCase("C"))
        								message("4428" , ps.getKeyToPersons(), rp.getLastNameFather(), ps.getFamilyName());
        							else
        								message("4429" , ps.getKeyToPersons(), rp.getLastNameFather(), ps.getFamilyName());
        						

        					}
        					




        					if(ps.getPersonID_MO() == ps1.getPersonID()) { // mother according to bevolkingsregister

        						compareResultLastName = compareNames(lastName, lastNameMother);
        						if(compareResultLastName > 2) {
        							message("4149", ps.getKeyToPersons(), rp.getLastNameMother(), rp.getFirstNameMother(), ps1.getFamilyName(), ps1.getFirstName());
        							continue;
        				    	}


        						compareResultFirstName1 = compareNames(firstName1, firstName1Mother);
        						if(compareResultFirstName1 > 2)	{
        							
        							message("4149", ps.getKeyToPersons(), rp.getLastNameMother(), rp.getFirstNameMother(), ps1.getFamilyName(), ps1.getFirstName());
        							continue;
        				    	}

        						int nrMatchingFirstNames = 1;


        						// 2nd names must match if both specified

        						if(firstName2 != null &&  firstName2Mother != null){
        							compareResultFirstName2 = compareNames(firstName2, firstName2Mother);
        							if(compareResultFirstName2 > 2)	{
            							
            							message("4149", ps.getKeyToPersons(), rp.getLastNameMother(), rp.getFirstNameMother(), ps1.getFamilyName(), ps1.getFirstName());
            							continue;
            				    	}
        							nrMatchingFirstNames = 2;
        						}
        						else{
    								if((firstName2 != null &&  firstName2Mother == null) || (firstName2 == null &&  firstName2Mother != null)){
            							
            							message("4149", ps.getKeyToPersons(), rp.getLastNameMother(), rp.getFirstNameMother(), ps1.getFamilyName(), ps1.getFirstName());
            							continue;
            				    	}
        							else; // both null is OK
        						}

        						// 3rd names must match if both specified

        						if(firstName3 != null &&  firstName3Mother != null){
        							compareResultFirstName3 = compareNames(firstName3, firstName3Mother);
        							if(compareResultFirstName3 > 2)	{
            							
            							message("4149", ps.getKeyToPersons(), rp.getLastNameMother(), rp.getFirstNameMother(), ps1.getFamilyName(), ps1.getFirstName());
            							continue;
            				    	}
        							nrMatchingFirstNames = 3;
        						}
        						
        						foundMother = true;
        						int shiftInMessageTableForCRegister = 0;
								if(Ref.getAINB(getKeyToSourceRegister()).getTypeRegister().equalsIgnoreCase("C")) // C-Register
									shiftInMessageTableForCRegister = 10;

								if(!rp.getLastNameMother().equalsIgnoreCase(ps1.getFamilyName()))
									message("" + (4432 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(),
											rp.getLastNameMother(), ps1.getFamilyName());

								if(!rp.getFirstNameMother().equalsIgnoreCase(ps1.getFirstName()))
									message("" + (4433 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(), 
											rp.getFirstNameMother(), ps1.getFirstName());

								if(nrMatchingFirstNames == 1  && (NfirstNames > 1 || firstNameMotherN > 1))
									message("" + (4434 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(),
											ps1.getFirstName());
        						
        						

        					}

    					}
    				} // -
    				
    				// See if other persons in registration are father or mother
    				
    				if(!foundMother || !foundFather) {
    					    					    					
    					for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){  // -

    						if(ps1 == ps)
    							continue;



    						if(ps1.getFamilyName() == null) continue;
    						String lastName   = ps1.getFamilyName();

    						if(ps1.getFirstName() == null) continue;
    						String [] firstName = ps1.getFirstName().split(" ");

    						int NfirstNames = firstName.length; 

    						String firstName1 = firstName.length > 0 ? firstName[0] : null;
    						String firstName2 = firstName.length > 1 ? firstName[1] : null;
    						String firstName3 = firstName.length > 2 ? firstName[2] : null;

    						int compareResultLastName = 0;
    						int compareResultFirstName1 = 0;
    						int compareResultFirstName2 = 0;
    						int compareResultFirstName3 = 0;

    						if(!foundFather && ps1.getSex().equalsIgnoreCase("M")){

    							compareResultLastName = compareNames(lastName, lastNameFather);
    							if(compareResultLastName > 2)
    								continue;

    							compareResultFirstName1 = compareNames(firstName1, firstName1Father);
    							if(compareResultFirstName1 > 2)
    								continue;

    							int nrMatchingFirstNames = 1;


    							// 2nd names must match or both not specified

    							if(firstName2 != null &&  firstName2Father != null){
    								compareResultFirstName2 = compareNames(firstName2, firstName2Father);
    								if(compareResultFirstName2 > 2)
    									continue;
    								nrMatchingFirstNames = 2;
    							}
    							else{
    								if((firstName2 != null &&  firstName2Mother == null) || (firstName2 == null &&  firstName2Mother != null))
    									continue;
    								else; // both null is OK
    							}

    							// 3rd names must match if both specified

    							if(firstName3 != null &&  firstName3Father != null){
    								compareResultFirstName3 = compareNames(firstName3, firstName3Father);
    								if(compareResultFirstName3 > 2)
    									continue;
    								nrMatchingFirstNames = 3;
    							}

    							foundFather = true;    							
    							ps.setPersonID_FA(ps1.getPersonID());
    							
    							message("4439", ps.getKeyToPersons());

    							int shiftInMessageTableForCRegister = 0;
    							if(Ref.getAINB(getKeyToSourceRegister()).getTypeRegister().equalsIgnoreCase("C")) // C-Register
    								shiftInMessageTableForCRegister = 10;


    							if(!rp.getLastNameFather().equalsIgnoreCase(ps1.getFamilyName()))
    								message("" + (4435 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(), 
    										rp.getLastNameFather(), ps1.getFamilyName());

    							if(!rp.getFirstNameFather().equalsIgnoreCase(ps1.getFirstName()))
    								message("" + (4436 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(), 
    										rp.getFirstNameFather(), ps1.getFirstName());

    							if(nrMatchingFirstNames == 1  && (NfirstNames > 1 || firstNameFatherN > 1))
    								message("" + (4437 - shiftInMessageTableForCRegister),  ps1.getKeyToPersons(),
    										ps1.getFirstName());

    							if(!rp.getLastNameFather().equalsIgnoreCase(ps.getFamilyName()))  // OP = ps
    								if(Ref.getAINB(getKeyToSourceRegister()).getTypeRegister().equalsIgnoreCase("C"))
    									message("4428" , ps.getKeyToPersons(), rp.getLastNameFather(), ps.getFamilyName());
    								else
    									message("4429" , ps.getKeyToPersons(), rp.getLastNameFather(), ps.getFamilyName());

    							
    						}





    						if(!foundMother && ps1.getSex().equalsIgnoreCase("V")) { 

    							compareResultLastName = compareNames(lastName, lastNameMother);
    							if(compareResultLastName > 2)
    								continue;


    							compareResultFirstName1 = compareNames(firstName1, firstName1Mother);
    							if(compareResultFirstName1 > 2)
    								continue;

    							int nrMatchingFirstNames = 1;


    							// 2nd names must match or both not specified

    							if(firstName2 != null &&  firstName2Mother != null){
    								compareResultFirstName2 = compareNames(firstName2, firstName2Mother);
    								if(compareResultFirstName2 > 2)
    									continue;
    								nrMatchingFirstNames = 2;
    							}
    							else{
    								if((firstName2 != null &&  firstName2Mother == null) || (firstName2 == null &&  firstName2Mother != null))
    									continue;
    								else; // both null is OK
    							}

    							// 3rd names must match if both specified

    							if(firstName3 != null &&  firstName3Mother != null){
    								compareResultFirstName3 = compareNames(firstName3, firstName3Mother);
    								if(compareResultFirstName3 > 2)
    									continue;
    								nrMatchingFirstNames = 3;
    							}

    							foundMother = true;
    							ps.setPersonID_MO(ps1.getPersonID());
    							
    							message("4438", ps.getKeyToPersons());
    							
    							int shiftInMessageTableForCRegister = 0;
    							if(Ref.getAINB(getKeyToSourceRegister()).getTypeRegister().equalsIgnoreCase("C")) // C-Register
    								shiftInMessageTableForCRegister = 10;

    							if(!rp.getLastNameMother().equalsIgnoreCase(ps1.getFamilyName()))
    								message("" + (4432 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(),
    										rp.getLastNameMother(), ps1.getFamilyName());

    							if(!rp.getFirstNameMother().equalsIgnoreCase(ps1.getFirstName()))
    								message("" + (4433 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(), 
    										rp.getFirstNameMother(), ps1.getFirstName());

    							if(nrMatchingFirstNames == 1  && (NfirstNames > 1 || firstNameMotherN > 1))
    								message("" + (4434 - shiftInMessageTableForCRegister), ps1.getKeyToPersons(),
    										ps1.getFirstName());



    						}


        				} // -
    					
    				}

    			}	
    		}
    	}


    	
    	// C Via relation to head

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){
    				if(((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.ZOON || ((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.DOCHTER){

    					if(getPersonsStandardizedInRegistration().get(0).getSex().equalsIgnoreCase("M")){
    						if(ps.getPersonID_FA() == 0){
    							ps.setPersonID_FA(getPersonsStandardizedInRegistration().get(0).getPersonID());
    							ps.setPersonID_FA_FG(42);
    						}

    					}
    					else{
    						if(ps.getPersonID_MO() == 0){
    							ps.setPersonID_MO(getPersonsStandardizedInRegistration().get(0).getPersonID());
    							ps.setPersonID_MO_FG(42);
    						}
    					}
    					
    		        	boolean headDied = getPersonsStandardizedInRegistration().get(0).getDateOfDecease().equals("00-00-0000") ? false : true;
    		        	PersonStandardized person = null;

    		        	
    		        	if(Utils.dateIsValid(ps.getDateOfBirth()) == 0) {
    		        		int dayCount = Utils.dayCount(ps.getDateOfBirth()) + 300;

    		        		for(Marriages marriage: getMarriagesHead()){

    		        			if(Common1.dateIsValid(marriage.getStartDate()) != 0 || 
    		        					Common1.dateIsValid(marriage.getEndDate()) != 0)
    		        				continue;

    		        			int start = Utils.dayCount(marriage.getStartDate());
    		        			int end   = Utils.dayCount(marriage.getEndDate());
    		        			if(headDied)
    		        				end += 270; // 9 months pregnancy period allowed

    		        			if(start <= dayCount && end >= dayCount){
    		        				person = marriage.getSpouse();
    		        				break;
    		        			}
    		        		}
    		        	}
    		    		
    		    		if(person != null){
    		    			if(person.getSex().equalsIgnoreCase("V") && ps.getPersonID_MO() == 0){
    		    				ps.setPersonID_MO(person.getPersonID());
    		    				ps.setPersonID_MO_FG(43);
    		    			}
    		    			else
        		    			if(person.getSex().equalsIgnoreCase("M") && ps.getPersonID_FA() == 0){
        		    				ps.setPersonID_FA(person.getPersonID());
        		    				ps.setPersonID_FA_FG(43);
        		    			}
    		    		}    					
    				}
    			}
    		}
    	}
    	
    	// D Stepchildren
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		
    		if(Common1.dateIsValid(ps.getDateOfBirth()) != 0) continue;
    		
    		for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){
    				if(((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.STIEFZOON || ((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.STIEFDOCHTER ||
    						((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.PLEEGZOON || ((PDS_RelationToHead)pds).getContentOfDynamicData() == ConstRelations2.PLEEGDOCHTER){
    					
    					
    					int dayCount = Utils.dayCount(ps.getDateOfBirth());
    					
    					for(Marriages marriage: getMarriagesHead()){

    		    			if(Common1.dateIsValid(marriage.getStartDate()) != 0)
    		    				continue;
    		    			
    		    			int start = Utils.dayCount(marriage.getStartDate());

    		    			if(start > dayCount){
    		    				if(getPersonsStandardizedInRegistration().get(0).getSex().equalsIgnoreCase("M")){
    		    					if(ps.getPersonID_MO() == 0){
    		    						ps.setPersonID_MO(marriage.getSpouse().getPersonID());
    		    						ps.setPersonID_MO_FG(44);
    		    					}
    		    				}
    		    				if(getPersonsStandardizedInRegistration().get(0).getSex().equalsIgnoreCase("V")){
    		    					if(ps.getPersonID_FA() == 0){
    		    						ps.setPersonID_FA(marriage.getSpouse().getPersonID());
    		    						ps.setPersonID_FA_FG(44);
    		    					}
    		    				}
    		    				break;
    		    			}
    		    		}
    					break;
    				}
    			}
    		}
    	}
    	
    	// E Propagate parent information - moved to OP.java
    	
    	
    }
    
    /*
     * This routine establishes the father and the mother of each person in the registration
     * Continuation of findFatherAndMother()
     * 
     *  The following messages are issued by this routine:
     *  
     *  4153
     *  4411
     *  4412
     *  4413
     *  4414
     * 
     * 
     */

    public void findFatherAndMother2(){
    	
    	// F 
    	
    	if(Ref.getAINB(getKeyToSourceRegister()) == null) return;
    	
    	if(Ref.getAINB(getKeyToSourceRegister()).getTypeRegister().equalsIgnoreCase("C")){ // C-Register
    		for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    			if(ps.getPersonID_FA() == 0 && ps.getPersonID_MO() == 0){
    				if(getPersonsStandardizedInRegistration().get(0).getFamilyName().equalsIgnoreCase(ps.getFamilyName())){
    					for(Marriages marriage: getMarriagesHead()){
    						if(Common1.dateIsValid(marriage.getSpouse().getDateOfBirth()) == 0 && Common1.dateIsValid(ps.getDateOfBirth()) == 0  &&
    								Utils.dayCount(marriage.getSpouse().getDateOfBirth()) + 17 * 365 > Utils.dayCount(ps.getDateOfBirth())){
    							if(ps.getKeyToPersons() > marriage.getSpouse().getKeyToPersons()){
    								ps.setPersonID_MO(marriage.getSpouse().getPersonID());
    								ps.setPersonID_MO_FG(46);
    								ps.setPersonID_FA(getPersonsStandardizedInRegistration().get(0).getPersonID());
    								ps.setPersonID_FA_FG(46);

    								// update b312_st record for person
    								//for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    								//	if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.OUDER_KIND){
    								//		if(ps.getSex().equalsIgnoreCase("M"))
    								//			((PDS_ParentsAndChildren)pds).setContentOfDynamicData(ConstRelations2.ZOON);
    								//		else
    								//			((PDS_ParentsAndChildren)pds).setContentOfDynamicData(ConstRelations2.DOCHTER);
    								//	}
    								//}
    								break;
    							}
    						}
    					}
    				}
    			}
    		}
    	}

    	
    	// G Check inconsistencies by way of birth dates
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(ps.getPersonID_FA() != 0){
    	    	for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    	    		if(ps1.getPersonID() == ps.getPersonID_FA()){
    	    			if(Utils.dateIsValid(ps.getDateOfBirth()) == 0  && Utils.dateIsValid(ps1.getDateOfBirth()) == 0 && 
    	    					Utils.dayCount(ps.getDateOfBirth()) - Utils.dayCount(ps1.getDateOfBirth()) < 16 * 365)
    	    				message("4411", ps.getKeyToPersons(), ps.getDateOfBirth(), ps1.getDateOfBirth());
    	    			if(Utils.dateIsValid(ps.getDateOfBirth()) == 0  && Utils.dateIsValid(ps1.getDateOfBirth()) == 0 && 
    	    					Utils.dayCount(ps.getDateOfBirth()) - Utils.dayCount(ps1.getDateOfBirth()) > 100 * 365)
    	    				message("4412", ps.getKeyToPersons(), ps.getDateOfBirth(), ps1.getDateOfBirth());
    	    			break;
    	    		}
    	    	}
    		}
    		if(ps.getPersonID_MO() != 0){
    	    	for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    	    		if(ps1.getPersonID() == ps.getPersonID_MO()){
    	    			if(Utils.dateIsValid(ps.getDateOfBirth()) == 0  && Utils.dateIsValid(ps1.getDateOfBirth()) == 0 &&
    	    					Utils.dayCount(ps.getDateOfBirth()) - Utils.dayCount(ps1.getDateOfBirth()) < 15 * 365)
    	    				message("4413", ps.getKeyToPersons(), ps.getDateOfBirth(), ps1.getDateOfBirth());
    	    			if(Utils.dateIsValid(ps.getDateOfBirth()) == 0  && Utils.dateIsValid(ps1.getDateOfBirth()) == 0 && 
    	    					Utils.dayCount(ps.getDateOfBirth()) - Utils.dayCount(ps1.getDateOfBirth()) > 52 * 365)
    	    				message("4414", ps.getKeyToPersons(), ps.getDateOfBirth(), ps1.getDateOfBirth());
    	    			
    	    			// Moved to OP.java
    	    			/*
    	    			if(Utils.dateIsValid(ps.getDateOfBirth()) == 0  && Utils.dateIsValid(ps1.getDateOfDecease()) == 0)
        	    			if(Utils.dayCount(ps.getDateOfBirth()) - Utils.dayCount(ps1.getDateOfDecease()) > 9 * 30)
        	    				message("4153",  ps.getKeyToPersons(),"" + (Utils.dayCount(ps.getDateOfBirth()) - Utils.dayCount(ps1.getDateOfDecease())));
        	    				*/
    	    			break;
    	    		}
    	    	}
    		}
    	}
    	
    	// H Check inconsistencies by way of identifiers - moved to OP.java
    	
    	
    	// I Improve C-Registers 

    	if(Ref.getAINB(getKeyToSourceRegister()).getTypeRegister().equalsIgnoreCase("C")){ // C-Register
    		
    		// use marriage table to set relation ECHTGENOTE_HOOFD
    		
    		for(Marriages marriage: getMarriagesHead()){
    			
    			if(Utils.dateIsValid(marriage.getStartDate()) != 0  || Utils.dateIsValid(marriage.getSpouse().getStartDate()) != 0) continue;
    			
    			PDS_RelationToHead pdsNew = null;
    			int i = 0;
    			for(PersonDynamicStandardized pds: marriage.getSpouse().getDynamicDataOfPersonStandardized()){
        			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){        				
        				if(Utils.dayCount(marriage.getStartDate()) <  Utils.dayCount(marriage.getSpouse().getStartDate()))
        					((PDS_RelationToHead)pds).setContentOfDynamicData(ConstRelations2.ECHTGENOTE_HOOFD);
        				else{
        					// Spouse changed status to married after she entered the household.
        					// So we need an extra record for the married period
        					
        					pdsNew = (PDS_RelationToHead )pds.copyPersonDynamicStandardized(); 
        					((PDS_RelationToHead)pdsNew).setContentOfDynamicData(ConstRelations2.ECHTGENOTE_HOOFD);
        					pdsNew.setDateOfMutation(marriage.getStartDate());
        					pds.setEndDate(Utils.dateFromDayCount(Utils.dayCount(marriage.getStartDate()) - 1));
        					
        					i = marriage.getSpouse().getDynamicDataOfPersonStandardized().indexOf(pds);
        				}
        			}
    			}
    			if(pdsNew != null)
					marriage.getSpouse().getDynamicDataOfPersonStandardized().add(i + 1, pdsNew); // add after old pds
    			
    		}
    			
        	// use personID father to set ZOON/DOCHTER relation		
        			
    		for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    			
    			if(ps.getPersonID_FA() ==  getPersonsStandardizedInRegistration().get(0).getPersonID()){
        			for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
            			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){    				
            				if(ps.getSex().equalsIgnoreCase("M"))
            					((PDS_RelationToHead)pds).setContentOfDynamicData(ConstRelations2.ZOON);
            				if(ps.getSex().equalsIgnoreCase("V"))
            					((PDS_RelationToHead)pds).setContentOfDynamicData(ConstRelations2.DOCHTER);
            			}
        			}
    			}
    		}
    	}
    }
    
    
    /**
     * 
     * This routine compares two Names.
     * 
     * Compare OK if both are not null and:
     * 
     * Levenshtein = 0
     * Levenshtein = 1 
     * Levenshtein = 2 and both strings longer than 5
     * 
     * Returns:
     * 
     * 0 - exact match
     * 1 - Levenshtein distance 1 
     * 2 - Levenshtein distance 2 but OK
     * 3 - Not OK      
     * 
     */
    
    private static int compareNames(String s1, String s2){

    	if(s1 == null || s2 == null)
    		return 3;
    
		int distance = Common1.LevenshteinDistance(s1, s2);

		if(distance > 2)  // greater than 2 not allowed
			return 3;

		if(distance == 2 && (s1.length() <= 5 || s2.length() <= 5))  // distance = 2 is allowed, but not for small strings
			return 3;
		
		return distance;

    }
    
    /**
     * 
     * This routine looks for situations where the head or the second explicit head has died.
     * It will appoint a new (implicit) Head (new b312 entry) and recode all existing relations-to-Head in terms of the new head (new b312 entries)
     * Change the end date of the previous relation to head to the start date of the new relation minus 1.
     * 
     * New records in the Marriage table for the implicit head
     * 
     */
    
    public void implicitHeads(){
    	
    	if(getPersonsStandardizedInRegistration().size() == 0) return;

    	PersonStandardized lastHead = getPersonsStandardizedInRegistration().get(0); 
    	
    	// 
    	// Look for the *last* head
    	
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(ps.getOriginalPerson().getIsHead()){
    			lastHead = ps;
	    	}
    	}

    	// See if there is 1 explicit head
    	// Look for the *last* one

    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		if(ps.getOriginalPerson().getIsHeadFirstSuccessor()){
    			lastHead = ps;
    			//break; // Look for the *last* one
    		}
    	}

    	  	
    	int endRegister = 0;
    	Ref_AINB ainb = Ref.getAINB(getKeyToSourceRegister());	
    	
    	if(ainb != null && ainb.getEndYearRegister() > 0)
    	    endRegister = Utils.dayCount(31,12, ainb.getEndYearRegister());

    	if(personEndDate(lastHead) >= endRegister - 40)
    		return;


    	// we need implicit head(s)


    	while(lastHead != null && personEndDate(lastHead) < endRegister - 40){

    		ArrayList a = selectImplicitHead(personEndDate(lastHead));
    		PersonStandardized implicitHead = null;
    		
    		if(a != null){
    			implicitHead = (PersonStandardized) a.get(0);
    			int codeNewHead = ((Integer)a.get(1)).intValue();

    			if(implicitHead != null){

    				int endDateImplicitHead = personEndDate(implicitHead);

    				// get relation new head to original head

    				int newToOriginal = 0;


    				for(PersonDynamicStandardized pds: implicitHead.getDynamicDataOfPersonStandardized()){
    					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){
    						newToOriginal = ((PDS_RelationToHead)pds).getContentOfDynamicData();
    						break;
    					}
    				}


    				// All persons in the registration that are still alive when the previous head dies get a new code (= new PDS) for their relation to the new head

    				for(PersonStandardized ps: getPersonsStandardizedInRegistration()){


    					if(personEndDate(ps) > personEndDate(lastHead)){

    						int relToOriginalHead = 0;
    						PersonDynamicStandardized pdsLast = null;


    						for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){

    							if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){
    								if(relToOriginalHead == 0){
    									relToOriginalHead = ((PDS_RelationToHead)pds).getContentOfDynamicData();
    									//pds.print();
    								}
    								pdsLast = pds;
    							}
    						}
    						

    						if(relToOriginalHead <= 0)
    							continue;  
    						
    						pdsLast.setEndDate(Utils.dateFromDayCount(personEndDate(lastHead)));

    						PersonDynamicStandardized pdsnew = pdsLast.copyPersonDynamicStandardized();

    						pdsnew.setDateOfMutation("00-00-0000");


    						if(ps == implicitHead)
    							((PDS_RelationToHead)pdsnew).setContentOfDynamicData(codeNewHead);

    						else{

    							int newCode = 0;

    							switch(newToOriginal){


    							case ConstRelations2.ZOON:      							
    								newCode = ConstRelations2.marriedSon[relToOriginalHead]; break;

    							case ConstRelations2.BROER:
    								newCode = ConstRelations2.brother[relToOriginalHead]; break;

    							case ConstRelations2.ECHTGENOTE_HOOFD:
    								newCode = ConstRelations2.widow[relToOriginalHead]; break;		

    							case ConstRelations2.SCHOONZOON:
    								newCode = ConstRelations2.sonInLaw[relToOriginalHead]; break;

    							case ConstRelations2.DOCHTER:
    								newCode = ConstRelations2.marriedSon[relToOriginalHead]; break;  // daughter and (married) son have identical table

    							case ConstRelations2.SCHOONBROER_ZWAGER:
    								newCode = ConstRelations2.brotherInLaw[relToOriginalHead]; break;

    							case ConstRelations2.ZUSTER:
    								newCode = ConstRelations2.sister[relToOriginalHead]; break;


    							default:

    								if(ConstRelations2.b3kode1_Related[newToOriginal] != null)
    									newCode = ConstRelations2.VERWANT;
    								else
    									newCode = ConstRelations2.GEEN_VERWANTSCHAP;



    							}
    							

    							if(newCode > 1000)
    								newCode = Math.abs(newCode % 1000); // transform

    							((PDS_RelationToHead)pdsnew).setContentOfDynamicData(newCode);

    						}


    						pdsnew.setStartDate(Utils.dateFromDayCount(personEndDate(lastHead) + 1));
    						pdsnew.setStartFlag(pdsLast.getEndFlag());
    						pdsnew.setStartEst(pdsLast.getEndEst());

    						pdsnew.setEndDate(Utils.dateFromDayCount(endDateImplicitHead)); 
    						//pdsnew.setEndDate("31-12-" + ainb.getEndYearRegister()); // Kan niet altijd
    						pdsnew.setEndFlag(pdsLast.getEndFlag());
    						pdsnew.setEndEst(100);

    						int x = ps.getDynamicDataOfPersonStandardized().indexOf(pdsLast);
    						ps.getDynamicDataOfPersonStandardized().add(x + 1, pdsnew);


    					}
    				}   				
    				
    				// Marriage table for new head
    				
    	    		for(PersonDynamicStandardized pds: implicitHead.getDynamicDataOfPersonStandardized()){
    	    			if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
    	    				if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
    	    					PersonStandardized partner = null;
    	    					for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){

    	    						if(ps1.getKeyToPersons() == pds.getValueOfRelatedPerson()){
    	    							partner = ps1;
    	    							break;
    	    						}
    	    					}
    	    					if(partner != null){	
    	    						Marriages marriage = new Marriages();
    	    						marriage.setHead(implicitHead);
    	    						marriage.setSpouse(partner);
    	    						marriage.setStartDate(pds.getStartDate());
    	    						marriage.setEndDate(pds.getEndDate());
    	    						getMarriagesHead().add(marriage);
    	    					}  	
    	    				}
    	    			}
    	    		}
    			}
    		}
    		lastHead = implicitHead;
    	}
    }

    /**
     * 
     * This routine will determine the end date of a person in this registration based on:
     * 
     *   -- departure without subsequent arrival
     *   -- decease
     *   -- end of register
     * 
     * 
     * @param ps
     * @return
     */
    
    private int personEndDate(PersonStandardized ps){
    	
       	Ref_AINB ainb = Ref.getAINB(getKeyToSourceRegister());	
        int endRegister = 0;
        if(ainb != null)
        	endRegister = Utils.dayCount(31,12, ainb.getEndYearRegister());
        
        // see if person departs for good
        
        int departure = 0;
        for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
        	if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.VERTREK)
        		if(pds.getDateOfMutation2() != null && !pds.getDateOfMutation2().equals("00-00-0000"))
        			if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0)
        				departure =  Utils.dayCount(pds.getDateOfMutation2());
        }
        
        int arrival = 0;
        for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
        	if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.AANKOMST)
        		if(pds.getDateOfMutation2() != null && !pds.getDateOfMutation2().equals("00-00-0000"))
        			if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0)
        				arrival =  Utils.dayCount(pds.getDateOfMutation2());
        }
        
    	if(departure > 0 && departure > arrival){ // left for good
    		return departure;
    	}
    	
    	if(Common1.dateIsValid(ps.getDateOfDecease()) == 0) {

    		if(Utils.dayCount(ps.getDateOfDecease()) > Utils.dayCount("01-01-1600")){
    			return Utils.dayCount(ps.getDateOfDecease());
    		}
    	}

    	return endRegister;
    }
    
    
    
    /**
     * 
     * This routine will select a new implicit Head for this household based on the end date of the previous Head.
     * The persons remaining in the household are inspected and one of them is chosen to be implicit Head.
     * The order of persons searched for is: 
     *                                        GEHUWDE_ZOON, 
     *			                              BROER,
     *                                        TWEEDE_MAN_WEDUWE,    	
     * 	                                      WEDUWE(OF_MAN_WEG),  
     * 	                                      ONGEHUWDE_ZOON, 
     * 	                                      SCHOONZOON,
     * 	                                      DOCHTER,
     *	                                      ZWAGER, 
     *	                                      ZUSTER,
     *	                                      OVERIGE_VERWANT,    	                
     *	                                      GEEN_VERWANT
     *	
     * 
     * 
     * @param dayCountPreviousHead
     * @return
     */
    
    private ArrayList selectImplicitHead(int dayCountPreviousHead){
    	
    	
    	ArrayList a = new ArrayList();
    	
    	
    	int [] potentialImplicitHead = {ConstRelations2.IMPLICIET_HOOFD_GEHUWDE_ZOON, 
    			                        ConstRelations2.IMPLICIET_HOOFD_BROER,
                                        ConstRelations2.IMPLICIET_HOOFD_TWEEDE_MAN,    	
    	                                ConstRelations2.IMPLICIET_HOOFD_WEDUWE_OF_MAN_WEG,  
    	                                ConstRelations2.IMPLICIET_HOOFD_ONGEHUWDE_ZOON, 
    	                                ConstRelations2.IMPLICIET_HOOFD_SCHOONZOON,
    	                                ConstRelations2.IMPLICIET_HOOFD_DOCHTER,
    	                                ConstRelations2.IMPLICIET_HOOFD_ZWAGER, 
    	                                ConstRelations2.IMPLICIET_HOOFD_ZUSTER,
    	                                ConstRelations2.IMPLICIET_HOOFD_OVERIGE_VERWANT,    	                
    	                                ConstRelations2.IMPLICIET_HOOFD_GEEN_VERWANT};
    	
    	
    	PersonStandardized newHead = null;
    	
    	
    	for(int i = 0; i < potentialImplicitHead.length; i++){

    		for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    			
    			// get relation to original head
    			
    			
    			int relToHead = 0;
    			
    			for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    	        	if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){
    	        		relToHead = ((PDS_RelationToHead)pds).getContentOfDynamicData();
    	        		//pds.print();
    	        		break;
    	        	}
    			}
    			
    			if(relToHead < 0)
    				continue;
    			
    			if(personEndDate(ps) <= dayCountPreviousHead)    				
    				continue;
    			
    			
    			switch(potentialImplicitHead[i]){

    			case ConstRelations2.IMPLICIET_HOOFD_GEHUWDE_ZOON:

    				if(relToHead != ConstRelations2.ZOON)
    					break;

    				// see if son is married

    				for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
    						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
    							a.add(ps);
    							a.add(ConstRelations2.IMPLICIET_HOOFD_GEHUWDE_ZOON);
    							return a;
    						}
    					}
    				}
    				break;	

    			case ConstRelations2.IMPLICIET_HOOFD_BROER:
    				
    				if(relToHead != ConstRelations2.BROER)
    					break;


    				for(PersonDynamicStandardized pds: ps.getDynamicDataOfPersonStandardized()){
    					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
    						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
    							a.add(ps);
    							a.add(ConstRelations2.IMPLICIET_HOOFD_BROER);
    							return a;

    						}
    					}
    				}

    				if(Common1.dateIsValid(ps.getDateOfBirth()) == 0 && dayCountPreviousHead - Utils.dayCount(ps.getDateOfBirth()) >= 21 * 365){
    					
						a.add(ps);
						a.add(ConstRelations2.IMPLICIET_HOOFD_BROER);
						return a;
    				}

    				break;

    			case ConstRelations2.IMPLICIET_HOOFD_TWEEDE_MAN:      	


    				// find wife 
    				
    				PersonStandardized psWife = null;
    				for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    					for(PersonDynamicStandardized pds: ps1.getDynamicDataOfPersonStandardized()){
        					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
        						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
        							if(pds.getValueOfRelatedPerson() == ConstRelations2.HOOFD){
        								psWife = ps1;
        								break;
        								
        							}
        						}
        					}
    					}
    					
    				}
    				
    				if(psWife == null)
    					break;
    				
    				// look for first marriage of wife after end date previous head

    				int relatedPerson = 0;
    				for(PersonDynamicStandardized pds: psWife.getDynamicDataOfPersonStandardized()){
    					if(pds.getKeyToDistinguishDynamicDataType() == ConstRelations2.BURGELIJKE_STAAT){
    						if(((PDS_CivilStatus)pds).getContentOfDynamicData() == ConstRelations2.GEHUWD){
    							if(Common1.dateIsValid(pds.getDateOfMutation2()) == 0) {
    								if(Utils.dayCount(pds.getDateOfMutation2()) > dayCountPreviousHead){
    									relatedPerson = pds.getValueOfRelatedPerson();
    									break;

    								}
    							}
    						}
    					}
    				}
					
					if(relatedPerson == 0)
						break;
    				
					// find related person
					
    				for(PersonStandardized ps1: getPersonsStandardizedInRegistration()){
    					if(ps1.getKeyToPersons() == relatedPerson){
    						a.add(ps);
    						a.add(ConstRelations2.IMPLICIET_HOOFD_TWEEDE_MAN);
    						return a;
    					}
    				}
					

    				break;
    				
    			case ConstRelations2.IMPLICIET_HOOFD_WEDUWE_OF_MAN_WEG:      	
    				
    				
    				if(relToHead != ConstRelations2.ECHTGENOTE_HOOFD)
    					break;

					a.add(ps);
					a.add(ConstRelations2.IMPLICIET_HOOFD_WEDUWE_OF_MAN_WEG);
					return a;

    			case ConstRelations2.IMPLICIET_HOOFD_ONGEHUWDE_ZOON:      	
    				
    				if(relToHead != ConstRelations2.ZOON)
    					break;

    				if(dayCountPreviousHead - Utils.dayCount(ps.getDateOfBirth()) >= 21 * 365){
						a.add(ps);
						a.add(ConstRelations2.IMPLICIET_HOOFD_ONGEHUWDE_ZOON);
						return a;
    				}

    				break;
    				
    				

    			case ConstRelations2.IMPLICIET_HOOFD_SCHOONZOON:
    				
    				if(relToHead != ConstRelations2.SCHOONZOON)
    					break;

					a.add(ps);
					a.add(ConstRelations2.IMPLICIET_HOOFD_SCHOONZOON);
					return a;

    			case ConstRelations2.IMPLICIET_HOOFD_DOCHTER:

    				if(relToHead != ConstRelations2.DOCHTER)
    					break;

    				
    				if(dayCountPreviousHead - Utils.dayCount(ps.getDateOfBirth()) >= 21 * 365){
						a.add(ps);
						a.add(ConstRelations2.IMPLICIET_HOOFD_DOCHTER);
						return a;
    				}

    				break;

    			case ConstRelations2.IMPLICIET_HOOFD_ZWAGER:
    				
    				if(relToHead != ConstRelations2.SCHOONBROER_ZWAGER)
    					break;
    				
					a.add(ps);
					a.add(ConstRelations2.IMPLICIET_HOOFD_ZWAGER);
					return a;

    			case ConstRelations2.IMPLICIET_HOOFD_ZUSTER:

    				if(relToHead != ConstRelations2.ZUSTER)
    					break;

    				
    				if(dayCountPreviousHead - Utils.dayCount(ps.getDateOfBirth()) >= 21 * 365){
						a.add(ps);
						a.add(ConstRelations2.IMPLICIET_HOOFD_ZUSTER);
						return a;
    				}

    				break;

    			case ConstRelations2.IMPLICIET_HOOFD_OVERIGE_VERWANT:

    				//System.out.println("Relation to head" + relToHead);
    				if(ConstRelations2.b3kode1_Related[relToHead] != null){
						a.add(ps);
						a.add(ConstRelations2.IMPLICIET_HOOFD_OVERIGE_VERWANT);
						return a;
    				}

    				break;

    			case ConstRelations2.IMPLICIET_HOOFD_GEEN_VERWANT:

					a.add(ps);
					a.add(ConstRelations2.IMPLICIET_HOOFD_GEEN_VERWANT);
					return a;
    			}
    		}
    	}
    	
    	return null;
    	
    }
    /**
     * 
     * This routine determines the relation of every person in the registration to every other person
     * in the registration (except to him/herself)
     * 
     */
    
    public void relateAllToAll(){

    	
    	boolean trace = false;

    	//if(getKeyToRP() == traceKey && getKeyToSourceRegister() == traceReg) {
    	//	trace = true;
    	//	System.out.println("\nRegistration: " + getKeyToRP() + "  " + getKeyToSourceRegister());
    	//}

    	for(PersonStandardized psA: getPersonsStandardizedInRegistration()){

    		int x1 = 0;
    		int x2 = 0;
    		int x3 = 0;
    		int x4 = 0;


    		int count = 0;

    		int relToHeadA = 0; 
    		for(PersonDynamicStandardized pdsA: psA.getDynamicDataOfPersonStandardized()){

    			if(pdsA.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){

    				relToHeadA = ((PDS_RelationToHead)pdsA).getContentOfDynamicData();

    				x1 = relToHeadA;

    				//if(relToHeadA <= 0){
    				//	if(getKeyToRP() == traceKey)
    				//		System.out.println(psA.getFirstName() + " " + psA.getFamilyName() +  "  "  + x1);
    				//	continue;
    				//}

    				if((relToHeadA == ConstRelations2.EXPLICIET_HOOFD  || relToHeadA == ConstRelations2.EXPLICIET_HOOFD_EERSTE_OPVOLGER || relToHeadA == ConstRelations2.EXPLICIET_HOOFD_TWEEDE_OPVOLGER) && 
    						psA.getSex().equalsIgnoreCase("V"))
    					relToHeadA = ConstRelations2.IMPLICIET_HOOFD_WEDUWE_OF_MAN_WEG;

    				x1 = relToHeadA;
    				int relToHeadB = 0; 

    				for(PersonStandardized psB: getPersonsStandardizedInRegistration()){

    					if(psB == psA || psB.getPersonID() == psA.getPersonID()){

    						continue;
    					}

    					for(PersonDynamicStandardized pdsB: psB.getDynamicDataOfPersonStandardized()){

    						if(pdsB.getKeyToDistinguishDynamicDataType() == ConstRelations2.RELATIE_TOT_HOOFD_ST){


    							relToHeadB = ((PDS_RelationToHead)pdsB).getContentOfDynamicData();

    							x2 = relToHeadB;

    							//if(relToHeadB <= 0){
    							//	if(getKeyToRP() == traceKey)
    							//		System.out.println(psA.getFirstName() + " " + psA.getFamilyName() +  "  "  + x1 + "  " + psB.getFirstName() + " " + psB.getFamilyName() + "  " + x2);
    							//	continue;
    							//}

    							if((relToHeadB == ConstRelations2.EXPLICIET_HOOFD  || relToHeadB == ConstRelations2.EXPLICIET_HOOFD_EERSTE_OPVOLGER || relToHeadB == ConstRelations2.EXPLICIET_HOOFD_TWEEDE_OPVOLGER) && 
    									psB.getSex().equalsIgnoreCase("V"))
    								relToHeadB = ConstRelations2.IMPLICIET_HOOFD_WEDUWE_OF_MAN_WEG;


    							x2 = relToHeadB;
    							
    							int[] relABa = Common1.getRelation(relToHeadA, relToHeadB);

    							//if(trace && psB.getPersonID() == 1){
    							//	System.out.println();
    							//	System.out.println(psA.getPersonID() +  " " + relToHeadA);
    							//	System.out.println(psB.getPersonID() +  " " + relToHeadB);
    							//	for(int x: relABa) System.out.println(x);
    							//}

    							int relAB = ConstRelations2.GEEN_VERWANTSCHAP;  // preset 'geen verwantschap'
    							if(relABa != null){   								

    								// See if -1 was returned
    								
    								if(relABa[0] <= 0);
    									//System.out.println(psA.getFirstName() + " " + psA.getFamilyName() +  "  "  + x1 + "  " + psB.getFirstName() + " " + psB.getFamilyName() + "  " + x2 +  "   " + relABa[0] +  " no relation found!");



    								else{


    									// See if relToHeadA occurs in relAB, in this case relAB = relToHeadA

    									for(int i: relABa )
    										if(i == relToHeadA){
    											relAB = i;
    											break;    										
    										}



    									// See if male/female form is needed

    									if(relAB == ConstRelations2.GEEN_VERWANTSCHAP)
    										for(int i: relABa )
    											if((ConstRelations2.b3kode1_Female[i] != null && psA.getSex().equalsIgnoreCase("V")) ||
    													(ConstRelations2.b3kode1_Male[i]   != null && psA.getSex().equalsIgnoreCase("M"))){
    												relAB = i;
    												break;    										
    											}

    									// take first value

    									if(relAB == ConstRelations2.GEEN_VERWANTSCHAP)
    										relAB = relABa[0]; 
    								}
    							}


    							x3 = relAB;



    							//if(relAB <= 0){
    							//	if(getKeyToRP() == traceKey)
    							//		System.out.println(psA.getFirstName() + " " + psA.getFamilyName() +  "  "  + x1 + "  " + psB.getFirstName() + " " + psB.getFamilyName() + "  " + x2 + "  " + x3 + "  " + x4);
    							//	continue;
    							//}

    							
    							//if(getKeyToRP() == traceKey){
    							//	
    							//	System.out.format("%d  %s %d   %d   %d   %d   %d  %d \n", 
    							//			relAB, psA.getSex(), psA.getPersonID(), psB.getPersonID(), psA.getPersonID_FA(), psA.getPersonID_MO(),
        						//			psB.getPersonID_FA(), psB.getPersonID_MO());
    							//}
    							relAB = resolveRelation(relAB, psA.getSex(), psA.getPersonID(), psB.getPersonID(), psA.getPersonID_FA(), psA.getPersonID_MO(),
    									psB.getPersonID_FA(), psB.getPersonID_MO());

    							x4 = relAB;

    							//if(getKeyToRP() == traceKey)
    							//if(getKeyToRP() == traceKey)
    							//	System.out.println("0 " +  psA.getFirstName() + " " + psA.getFamilyName() +  "  "  + ConstRelations2.b3kode1[x1] + "  " + psB.getFirstName() + " " + psB.getFamilyName() + "  " + ConstRelations2.b3kode1[x2] + "  " + ConstRelations2.b3kode1[x3] + "  " + ConstRelations2.b3kode1[x4]);
    							//if(getKeyToRP() == traceKey &&  (psA.getNatureOfPerson() != 2   || psB.getNatureOfPerson() != 2)){
    							//	System.out.println("Person A = " + psA.getPersonID() + "  " + psA.getFirstName() + "  " + psA.getFamilyName() + "  , relation to Head = " + relToHeadA);
    							//	System.out.println("Person B = " + psB.getPersonID() + "  " + psB.getFirstName() + "  " + psB.getFamilyName() + "  , relation = " + relToHeadB);
    							//	System.out.println("Person A to B relation: " + relAB);
    							//}



    							if(ConstRelations2.b3kode1_Related[relAB] == null){ // not blood related, so lets try to date
    								if(pdsA.getStartDate() != null || pdsB.getStartDate() != null){
    						//			if(Common1.dateIsValid(pdsA.getStartDate()) == 0 && Common1.dateIsValid(pdsA.getEndDate()) == 0 && 
    						//					Common1.dateIsValid(pdsB.getStartDate()) == 0 && Common1.dateIsValid(pdsB.getEndDate()) == 0) {
    										String  []  intersection = Common1.getIntersection(pdsA.getStartDate(), pdsA.getEndDate(), pdsB.getStartDate(), pdsB.getEndDate());
    										if(intersection != null){

    											//if(getKeyToRP() == traceKey)
    											//	System.out.println("1 " +  psA.getFirstName() + " " + psA.getFamilyName() +  "  "  + ConstRelations2.b3kode1[x1] + "  " + psB.getFirstName() + " " + psB.getFamilyName() + "  " + ConstRelations2.b3kode1[x2] + "  " + ConstRelations2.b3kode1[x3] + "  " + ConstRelations2.b3kode1[x4]);


    											PDS_AllToAll pds = new PDS_AllToAll();

    											pds.setStartDate(intersection[0]);
    											pds.setEndDate(intersection[1]);

    											pds.setKeyToSourceRegister(psA.getKeyToSourceRegister());
    											pds.setEntryDateHead(psA.getEntryDateHead());
    											pds.setKeyToRP(psA.getKeyToRP());
    											pds.setKeyToRegistrationPersons(psA.getKeyToPersons());
    											pds.setKeyToDistinguishDynamicDataType(34);
    											pds.setDynamicDataSequenceNumber(psA.getToAll().size() + 1); 
    											pds.setContentOfDynamicData(relAB); 

    											pds.setValueOfRelatedPerson(psB.getKeyToPersons()); 
    											pds.setNatureOfPerson(psA.getNatureOfPerson());

    											pds.setDateOfMutation("00-00-0000");
    											pds.setDateOfMutationFlag(0);

    											pds.setVersionLastTimeOfDataEntry(psA.getVersionLastTimeOfDataEntry());
    											pds.setResearchCodeOriginal(psA.getResearchCodeOriginal());
    											pds.setVersionOriginalDataEntry(psA.getVersionOriginalDataEntry());
    											pds.setDate0(psA.getDate0());

    											//pds.setOriginalPersonDynamic(null);
    											pds.setPersonStandardizedToWhomDynamicDataRefers(psA);

    											psA.getToAll().add(pds);

    										}
    									//}
    								}
    								else{ // not blood related but no dates
    									
		    							//if(getKeyToRP() == traceKey)
		    								//System.out.println("2 " +  psA.getFirstName() + " " + psA.getFamilyName() +  "  "  + ConstRelations2.b3kode1[x1] + "  " + psB.getFirstName() + " " + psB.getFamilyName() + "  " + ConstRelations2.b3kode1[x2] + "  " + ConstRelations2.b3kode1[x3] + "  " + ConstRelations2.b3kode1[x4]);

    									
    									PDS_AllToAll pds = new PDS_AllToAll();

    									pds.setKeyToSourceRegister(psA.getKeyToSourceRegister());
    									pds.setEntryDateHead(psA.getEntryDateHead());
    									pds.setKeyToRP(psA.getKeyToRP());
    									pds.setKeyToRegistrationPersons(psA.getKeyToPersons());
    									pds.setKeyToDistinguishDynamicDataType(34);
    									pds.setDynamicDataSequenceNumber(psA.getToAll().size() + 1); 
    									pds.setContentOfDynamicData(relAB); 

    									pds.setValueOfRelatedPerson(psB.getKeyToPersons()); 
    									pds.setNatureOfPerson(psA.getNatureOfPerson());

    									pds.setDateOfMutation("00-00-0000");
    									pds.setDateOfMutationFlag(0);

    									pds.setVersionLastTimeOfDataEntry(psA.getVersionLastTimeOfDataEntry());
    									pds.setResearchCodeOriginal(psA.getResearchCodeOriginal());
    									pds.setVersionOriginalDataEntry(psA.getVersionOriginalDataEntry());
    									pds.setDate0(psA.getDate0());

    									//pds.setOriginalPersonDynamic(null);
    									pds.setPersonStandardizedToWhomDynamicDataRefers(psA);

    									psA.getToAll().add(pds);
    								}
    							}
    							else{ // blood related, do not date
    								
	    							//if(getKeyToRP() == traceKey)
	    							//	System.out.println("3 " +  psA.getFirstName() + " " + psA.getFamilyName() +  "  "  + ConstRelations2.b3kode1[x1] + "  " + psB.getFirstName() + " " + psB.getFamilyName() + "  " + ConstRelations2.b3kode1[x2] + "  " + ConstRelations2.b3kode1[x3] + "  " + ConstRelations2.b3kode1[x4]);


    								PDS_AllToAll pds = new PDS_AllToAll();

    								pds.setKeyToSourceRegister(psA.getKeyToSourceRegister());
    								pds.setEntryDateHead(psA.getEntryDateHead());
    								pds.setKeyToRP(psA.getKeyToRP());
    								pds.setKeyToRegistrationPersons(psA.getKeyToPersons());
    								pds.setKeyToDistinguishDynamicDataType(34);
    								pds.setDynamicDataSequenceNumber(psA.getToAll().size() + 1); 
    								pds.setContentOfDynamicData(relAB); 

    								pds.setValueOfRelatedPerson(psB.getKeyToPersons()); 
    								pds.setNatureOfPerson(psA.getNatureOfPerson());

    								pds.setDateOfMutation("00-00-0000");
    								pds.setDateOfMutationFlag(0);

    								pds.setVersionLastTimeOfDataEntry(psA.getVersionLastTimeOfDataEntry());
    								pds.setResearchCodeOriginal(psA.getResearchCodeOriginal());
    								pds.setVersionOriginalDataEntry(psA.getVersionOriginalDataEntry());
    								pds.setDate0(psA.getDate0());

    								//pds.setOriginalPersonDynamic(null);
    								pds.setPersonStandardizedToWhomDynamicDataRefers(psA);

    								psA.getToAll().add(pds);

    							}
    						}
    					}
    				}
    			}
    		}
    	}
    }
    
    
   
    /**
     * This routine resolves the actual relation based on the relation returned by getRelation
     * It corrects for sex, and determines parents and siblings based on person_ID
     *  
     *  
     *  
     * @param relAB
     * @param sex (of A)
     * @param A_ID
     * @param B_ID
     * @param A_ID_FA
     * @param A_ID_MO
     * @param B_ID_FA
     * @param B_ID_MO
     * @return
     */
    

	private int resolveRelation(int relAB, String sex, int A_ID, int B_ID, int A_ID_FA, int A_ID_MO, int B_ID_FA, int B_ID_MO){
		
		// Adapt for sex

		if(sex.equalsIgnoreCase("V") && ConstRelations2.b3kode1_Female[relAB] == null)
			if(ConstRelations2.mToF[relAB] != 0)
				relAB = ConstRelations2.mToF[relAB];
		
		if(sex.equalsIgnoreCase("M") && ConstRelations2.b3kode1_Male[relAB] == null)
			if(ConstRelations2.fToM[relAB] != 0)
				relAB = ConstRelations2.fToM[relAB];
		
			
		// resolve sibling, halfsibling, stepsibling
		
		if(relAB == ConstRelations2.BROER || relAB == ConstRelations2.ZUSTER)
			relAB = handleSiblings(relAB, A_ID_FA, A_ID_MO, B_ID_FA, B_ID_MO);
		
		// resolve mother, she may be stepmother
		
		if(relAB == ConstRelations2.MOEDER)
			if(B_ID_MO != 0)
				if(B_ID_MO == A_ID)
					relAB =  ConstRelations2.MOEDER;
				else
					relAB =  ConstRelations2.STIEFMOEDER;
			else
				relAB =  ConstRelations2.MOEDER_OF_STIEFMOEDER;
		
		
		// resolve father, he may be stepfather
		
		if(relAB == ConstRelations2.VADER)
			if(B_ID_FA != 0)
				if(B_ID_FA == A_ID)
					relAB =  ConstRelations2.VADER;
				else
					relAB =  ConstRelations2.STIEFVADER;
			else
				relAB =  ConstRelations2.VADER_OF_STIEFVADER;
		
		// resolve aunt, she may be mother

		if(relAB == ConstRelations2.TANTE)
			if(B_ID_MO != 0)
				if(B_ID_MO == A_ID)
					relAB =  ConstRelations2.MOEDER;
		
		// resolve uncle, he may be father

		if(relAB == ConstRelations2.OOM)
			if(B_ID_FA != 0)
				if(B_ID_FA == A_ID)
					relAB =  ConstRelations2.VADER;
		
		return relAB;
		

		
	}
	private int handleSiblings(int relAB, int A_ID_FA, int A_ID_MO, int B_ID_FA, int B_ID_MO){
		
		int motherEqual = 0;  // 1 = equal, 2 = not equal, 3 = unknown
		int fatherEqual = 0;  // 1 = equal, 2 = not equal, 3 = unknown  
		
		// Set father status
		
		if(A_ID_FA != 0){
			if(B_ID_FA != 0){
				if(A_ID_FA == B_ID_FA)
					fatherEqual = 1;
				else
					fatherEqual = 2;
			}
			else
				fatherEqual = 3;
		}
		else
			fatherEqual = 3; 	
		
		// Set mother status
		
		if(A_ID_MO != 0){
			if(B_ID_MO != 0){
				if(A_ID_MO == B_ID_MO)
					motherEqual = 1;
				else
					motherEqual = 2;
			}
			else
				motherEqual = 3;
		}
		else
			motherEqual = 3; 	
		
		
		
		// Determine sibling relation
		
		
		switch(fatherEqual){
		
		case 1:

			switch(motherEqual){
			
			case 1: return relAB == ConstRelations2.BROER ? ConstRelations2.BROER : ConstRelations2.ZUSTER;
				
			case 2: return relAB == ConstRelations2.BROER ? ConstRelations2.HALFBROER : ConstRelations2.HALFZUSTER;
				
			case 3: return relAB == ConstRelations2.BROER ? ConstRelations2.BROER_OF_HALFBROER : ConstRelations2.ZUSTER_OF_HALFZUSTER;
			
			}
			
		case 2:

			switch(motherEqual){
			
			case 1: return relAB == ConstRelations2.BROER ? ConstRelations2.HALFBROER : ConstRelations2.HALFZUSTER;
				
			case 2: return relAB == ConstRelations2.BROER ? ConstRelations2.STIEFBROER : ConstRelations2.STIEFZUSTER;
				
			case 3: return relAB == ConstRelations2.BROER ? ConstRelations2.HALFBROER_OF_STIEFBROER : ConstRelations2.HALFZUSTER_OF_STIEFZUSTER;
			
			}
			
		case 3:
			
			switch(motherEqual){
			
			case 1: return relAB == ConstRelations2.BROER ? ConstRelations2.BROER_OF_HALFBROER : ConstRelations2.ZUSTER_OF_HALFZUSTER;
				
			case 2: return relAB == ConstRelations2.BROER ? ConstRelations2.HALFBROER_OF_STIEFBROER : ConstRelations2.HALFZUSTER_OF_STIEFZUSTER;
				
			case 3: return relAB == ConstRelations2.BROER ? ConstRelations2.STIEF_OF_HALF_OF_BROER : ConstRelations2.STIEF_OF_HALF_OF_ZUSTER;
			
			}
		}
		
		
		return 0;
	}

    
    
    /**
     * 
     * This routine gives a start- and end date to all address elements of this registration
     * 
     * 
     */
    
    private void dateAddresses(){
    	
    	if(getAddressesStandardizedOfRegistration().size() == 0)
    		return;
    	
    	if(getPersonsStandardizedInRegistration().size() == 0)
    		return;
    	
    	// get lowest start date and highest end date among persons in registration
    	
    	String startDate = getPersonsStandardizedInRegistration().get(0).getStartDate();  // must be lowest start date
    	int    startEst  = getPersonsStandardizedInRegistration().get(0).getStartEst();
    	int    startFlg  = getPersonsStandardizedInRegistration().get(0).getStartFlag();
    	
    	String endDate = "";
    	int endEst = 0;
    	int endFlg = 0;
    	
    	int endDays = 0;
    	for(PersonStandardized ps: getPersonsStandardizedInRegistration()){
    		
    		if(Common1.dateIsValid(ps.getEndDate()) == 0 && Utils.dayCount(ps.getEndDate()) > endDays){
    			endDate = ps.getEndDate();
    			endEst  = ps.getEndEst();
    			endFlg  = ps.getEndFlag();
    			
    		}
    			
    	}
    	
    	if(getAddressesStandardizedOfRegistration().size() == 1){
    		
    		RegistrationAddressStandardized ras = getAddressesStandardizedOfRegistration().get(0);
    		ras.setStartDate(startDate);
    		ras.setStartFlag(startFlg);
    		ras.setStartEst(startEst);
    		
    		ras.setEndDate(endDate);
    		ras.setEndFlag(endFlg);
    		ras.setEndEst(endEst);
    		
    	}
    	
    	else{
    		
    		// make dayCount array of all addresses
    		
    		int [] dayCount = new int[getAddressesStandardizedOfRegistration().size()];
    		int [] flags    = new int[getAddressesStandardizedOfRegistration().size()];
    		int [] ests     = new int[getAddressesStandardizedOfRegistration().size()];
    		
    		int count = 0;
    		for(RegistrationAddressStandardized ras: getAddressesStandardizedOfRegistration()){
    			
    			if(ras.getDateOfAddress() != null && !ras.getDateOfAddress().substring(6).equals("0000")){
    				if(Common1.dateIsValid(ras.getDateOfAddress()) == 0){
    					dayCount[count] = Utils.dayCount(ras.getDateOfAddress());
    				}
    				else
    					dayCount[count] = 0;
    			}
    			else{
    				dayCount[count] = 0;
    			}
    			count++;
    			
    		}
    		
    		// first date is fixed
    		
    		RegistrationAddressStandardized ras = getAddressesStandardizedOfRegistration().get(0);
    		ras.setStartDate(startDate);
    		ras.setStartFlag(startFlg);
    		ras.setStartEst(startEst);
    		
    		if(Common1.dateIsValid(startDate) == 0) {
    			dayCount[0] = Utils.dayCount(startDate);
    			flags[0] = startFlg;
    			ests[0] = startEst;
    		}
    		
    		// last date is fixed
    		
    		ras = getAddressesStandardizedOfRegistration().get(getAddressesStandardizedOfRegistration().size() -1);
    		
    		ras.setEndDate(endDate);
    		ras.setEndFlag(endFlg);
    		ras.setEndEst(endEst);
    		
    		
    		for(int i = 1; i < getAddressesStandardizedOfRegistration().size(); i++){
    			
    			if(dayCount[i] == 0){
    				
    				flags[i] = 54;
    				
    				int cnt = 0;
    				int daysAddressBefore = dayCount[i - 1]; // always possible
    				int daysAddressAfter  = 0;        
    				if(Common1.dateIsValid(endDate) == 0)
    					daysAddressAfter  = Utils.dayCount(endDate); // preset to end of range
    				
    				for(int j = i; j < getAddressesStandardizedOfRegistration().size(); j++){
    					cnt++;
    					if(dayCount[j] != 0){
    						daysAddressAfter = dayCount[j];
    						break;
    					}
    					else
    						flags[j] = 54;
    				}
    				
    				int c = 1;
					int div = (daysAddressAfter - daysAddressBefore) / (cnt + 1);

    				for(int j = i; j < i + cnt; j++){
    					if(dayCount[j] == 0)  // this is necessary
    						dayCount[j] = daysAddressBefore + c * div;
    					c++;
    				}
    			}
    		}

    		int c = 0;
    		for(RegistrationAddressStandardized ras1: getAddressesStandardizedOfRegistration()){

    			if(ras1.getStartDate() == null){
    				
    				ras1.setStartDate(Utils.dateFromDayCount(dayCount[c]));
    				ras1.setStartFlag(flags[c]);
    				ras1.setStartEst(0); // ask Kees
    			}
    			
    			if(ras1.getEndDate() == null){

    				ras1.setEndDate(Utils.dateFromDayCount(dayCount[c + 1] - 1));
    				ras1.setEndFlag(flags[c + 1]);
    				ras1.setEndEst(0);
    			}
    			
    			c++;
    		}
    	}
    }
    
    /**
     * This routine prints out various fields of a Registration
     * 
     * 
     */
    
    private void showFields(){
		
		String EntryDateHead = getEntryDateHead();
		String EntryDateOP   = getEntryDateRP();
		
		Ref_AINB ainb = Ref.getAINB(getKeyToSourceRegister());	
		String municipality = ainb.getMunicipality();
		int startYear = ainb.getStartYearRegister();
		int endYear = ainb.getEndYearRegister();
		String registerInfo = String.format("%s  %d-%d", municipality, startYear, endYear);
		
		
		System.out.println();
		System.out.println("" +
		  			"" + getKeyToRP() +
		  			"  " + EntryDateHead +
		  			"  " + getKeyToSourceRegister() +
		  			"  OP date: " + EntryDateOP +
		  			"  Register:  " + registerInfo +
		  			"  Page: " + getPageNumber().trim() +
		  			"  Line: " + getNumberOfHousehold() 		  			
		  			);
		
	}

	/**
	 * 
	 * This routine truncates fields that are too long for the corresponding database columns
	 * 
	 */
	
	
	public void truncate(){	
		
		String field = getPageNumber();
		int allowedSize = ConstTables.Smallstring;
		if(field != null && field.length() > allowedSize){
			message("1500", 0, "B4_ST", "REGISTER_PAGE", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setPageNumber(field);
		}
			
		field = getNameHeadGK();
		allowedSize = ConstTables.XBigstring;

		if(field != null && field.length() > allowedSize){
			message("1500", 0, "B4_ST", "NAME_HEAD_GK", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setNameHeadGK(field);
		}
			
		field = getSpecialCode();
		allowedSize = ConstTables.XBigstring;

		if(field != null && field.length() > allowedSize){
			message("1500", 0, "B4_ST", "SPECIAL_CODE", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setSpecialCode(field);
		}
			
		field = getRemarks();
		allowedSize = ConstTables.XBigstring;

		if(field != null && field.length() > allowedSize){
			message("1500", 0, "B4_ST", "SPECIAL_REMARKS", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setRemarks(field);
		}
			
		
		for(PersonStandardized p: personsStandardizedInRegistration){
			p.truncate();
		}
		
		for(RegistrationAddressStandardized ras: addressesStandardizedOfRegistration){
			ras.truncate();
		}
		
	}
	

    
	/**
	 * 
	 * This routine writes the RegistrationStandardized object to the database.
	 * Also, all persons in the registration and all addresses for the registration are 
	 * written to the database by calling their write() routines
	 * 
	 */
	
	
	public void write(){	
		
		
		Utils.persist(this);
		
		for(PersonStandardized p: personsStandardizedInRegistration){
			p.write();
		}
		
		for(RegistrationAddressStandardized ras: addressesStandardizedOfRegistration){
			ras.write();
		}
		
	}
	

    
    
	/**
	 * 
	 * This routine allocates a Message object and fills it with the parameters.
	 * Additional information is added to identify the B*-row(s) to which the message applies
	 * 
	 * @param number
	 * @param fills
	 */

	private void message(String number, int personNumber, String... fills){
		
		Message m = new Message(number);
		
		m.setKeyToRP(getKeyToRP());
		
		m.setKeyToSourceRegister(getKeyToSourceRegister());
		
		m.setDayEntryHead((new Integer(getEntryDateHead().substring(0,2)).intValue()));
		m.setMonthEntryHead((new Integer(getEntryDateHead().substring(3,5)).intValue()));
		m.setYearEntryHead((new Integer(getEntryDateHead().substring(6,10)).intValue()));
		
		
		m.setDayEntryRP((new Integer(getEntryDateRP().substring(0,2)).intValue()));
		m.setMonthEntryRP((new Integer(getEntryDateRP().substring(3,5)).intValue()));
		m.setYearEntryRP((new Integer(getEntryDateRP().substring(6,10)).intValue()));
		
		m.setKeyToRegistrationPersons(personNumber);
		m.setNatureOfPerson(0);
		
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
	public void setEntryDateHead(String dateEntryHead) {
		this.entryDateHead = dateEntryHead;
	}
	public int getKeyToRP() {
		return keyToRP;
	}
	public void setKeyToRP(int keyToRP) {
		this.keyToRP = keyToRP;
	}
	public String getEntryDateRP() {
		return entryDateRP;
	}
	public void setEntryDateRP(String estimatedEntryDateResearchPerson) {
		this.entryDateRP = estimatedEntryDateResearchPerson;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getNumberOfHousehold() {
		return numberOfHousehold;
	}
	public void setNumberOfHousehold(int numberOfHousehold) {
		this.numberOfHousehold = numberOfHousehold;
	}
	
	
	public String getNameHeadGK() {
		return nameHeadGK;
	}

	public void setNameHeadGK(String nameHeadGK) {
		this.nameHeadGK = nameHeadGK;
	}

	public String getSpecialCode() {
		return specialCode;
	}

	public void setSpecialCode(String specialCode) {
		this.specialCode = specialCode;
	}

	public String getDate0() {
		return date0;
	}

	public void setDate0(String date) {
		this.date0 = date;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Registration getOriginalRegistration() {
		return originalRegistration;
	}

	public void setOriginalRegistration(Registration originalRegistration) {
		this.originalRegistration = originalRegistration;
	}

	public ArrayList<PersonStandardized> getPersonsStandardizedInRegistration() {
		return personsStandardizedInRegistration;
	}

	public void setPersonsStandardizedInRegistration(
			ArrayList<PersonStandardized> personsStandardizedInRegistration) {
		this.personsStandardizedInRegistration = personsStandardizedInRegistration;
	}

	public OP getOp() {
		return op;
	}

	public void setOp(OP op) {
		this.op = op;
	}

	public ArrayList<Marriages> getMarriagesHead() {
		return marriagesHead;
	}

	public void setMarriagesHead(ArrayList<Marriages> marriagesHead) {
		this.marriagesHead = marriagesHead;
	}

	public ArrayList<RegistrationAddressStandardized> getAddressesStandardizedOfRegistration() {
		return addressesStandardizedOfRegistration;
	}

	public void setAddressesStandardizedOfRegistration(
			ArrayList<RegistrationAddressStandardized> addressesStandardizedOfRegistration) {
		this.addressesStandardizedOfRegistration = addressesStandardizedOfRegistration;
	}

	

}
