package nl.iisg.ids05;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="indiv_context")
public class INDIV_CONTEXT {
	
 //   @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id@Column(name = "Id")  		        private int       id;
    @Column(name = "Id_D")  			private String    id_D;
    @Column(name = "Id_I")  			private int		  id_I;
    @Column(name = "Id_C")  			private int		  id_C;
    @Column(name = "Source")  			private String    source;
    @Column(name = "Relation") 			private String    relation;
    
    @Column(name = "Date_type")  		private String    date_type;
    @Column(name = "Estimation")  		private String    estimation;
    @Column(name = "Day")		  		private int       day;
    @Column(name = "Month")		  		private int       month;
    @Column(name = "Year")		  		private int       year;
    @Column(name = "Start_day")			private int       start_day;
    @Column(name = "Start_month")  		private int       start_month;
    @Column(name = "Start_year")		private int       start_year;
    @Column(name = "End_day")			private int       end_day;
    @Column(name = "End_month")  		private int       end_month;
    @Column(name = "End_year")			private int       end_year;
    @Column(name = "Missing")  			private String    missing;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getId_D() {
		return id_D;
	} 
	public void setId_D(String id_D) {
		this.id_D = id_D;
	}
	public int getId_I() {
		return id_I;
	}
	public void setId_I(int id_I) {
		this.id_I = id_I;
	}
	public int getId_C() {
		return id_C;
	}
	public void setId_C(int id_C) {
		this.id_C = id_C;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getDate_type() {
		return date_type;
	}
	public void setDate_type(String date_type) {
		this.date_type = date_type;
	}
	public String getEstimation() {
		return estimation;
	}
	public void setEstimation(String estimation) {
		this.estimation = estimation;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getStart_day() {
		return start_day;
	}
	public void setStart_day(int start_day) {
		this.start_day = start_day;
	}
	public int getStart_month() {
		return start_month;
	}
	public void setStart_month(int start_month) {
		this.start_month = start_month;
	}
	public int getStart_year() {
		return start_year;
	}
	public void setStart_year(int start_year) {
		this.start_year = start_year;
	}
	public int getEnd_day() {
		return end_day;
	}
	public void setEnd_day(int end_day) {
		this.end_day = end_day;
	}
	public int getEnd_month() {
		return end_month;
	}
	public void setEnd_month(int end_month) {
		this.end_month = end_month;
	}
	public int getEnd_year() {
		return end_year;
	}
	public void setEnd_year(int end_year) {
		this.end_year = end_year;
	}
	public String getMissing() {
		return missing;
	}
	public void setMissing(String missing) {
		this.missing = missing;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
    
    

}
