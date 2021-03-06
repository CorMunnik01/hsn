package nl.iisg.convertPK;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="b37_st")
public class B37_ST  extends B3_ST{
	
	@Column(name = "DESTINATION_ID")  	private int         destinationID;
	@Column(name = "DESTINATION_ST")  	private String      destinationStandardized;
	@Column(name = "DESTINATION_FG")  	private int         destinationFlag;
	
	@Column(name = "DESTINATION_EQUAL") private int      	destinationGroup;


	@Column(name = "ADDRESS")       	private String      address;
	@Column(name = "REGISTER")      	private String      register;
	@Column(name = "CENSUS")        	private String      census;
	
	@Transient                          private B2_ST       person;  

	 
	
	B37_ST(){}
	
	/**
	 * 
	 * This routine truncates fields that are too long for the corresponding database columns
	 * 
	 */
	

	public void truncate(){	
		
		String field = getDestinationStandardized();
		int allowedSize = TableDefinitions.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B37_ST", "DESTINATION_ST", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setDestinationStandardized(field);
		}

		field = getAddress();
		allowedSize = TableDefinitions.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B37_ST", "ADDRESS", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setAddress(field);
		}

		field = getRegister();
		allowedSize = TableDefinitions.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B37_ST", "REGISTER", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setRegister(field);
		}

		field = getCensus();
		allowedSize = TableDefinitions.Bigstring;
		if(field != null && field.length() > allowedSize){
			message("1500", "B37_ST", "CENSUS", "" + allowedSize);
			field = field.substring(0, allowedSize);
			setCensus(field);
		}


	}



	public void save(EntityManager em){
		
		em.persist(this);
		
	}



	public int getDestinationID() {
		return destinationID;
	}



	public void setDestinationID(int destinationID) {
		this.destinationID = destinationID;
	}



	public String getDestinationStandardized() {
		return destinationStandardized;
	}



	public void setDestinationStandardized(String destinationStandardized) {
		this.destinationStandardized = destinationStandardized;
	}



	public int getDestinationFlag() {
		return destinationFlag;
	}



	public void setDestinationFlag(int destinationFlag) {
		this.destinationFlag = destinationFlag;
	}



	public int getDestinationGroup() {
		return destinationGroup;
	}



	public void setDestinationGroup(int destinationGroup) {
		this.destinationGroup = destinationGroup;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getRegister() {
		return register;
	}



	public void setRegister(String register) {
		this.register = register;
	}



	public String getCensus() {
		return census;
	}



	public void setCensus(String census) {
		this.census = census;
	}



	public B2_ST getPerson() {
		return person;
	}



	public void setPerson(B2_ST person) {
		this.person = person;
	}

	
	
}
