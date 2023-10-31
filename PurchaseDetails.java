package MobilePurchaseSystem.entity;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

//annotation to create table
@Entity
//annotation to give table name
@Table(name="purchasedetails")
public class PurchaseDetails {
	//annotation for primary key
	@Id
	//annotation to set column name
	@Column(name = "purchase id")
	//annotation to Generate automatically using sequence
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_id_seq")
    @SequenceGenerator(name = "purchase_id_seq", sequenceName = "purchase_id_seq", allocationSize = 1)
    private Long purchaseId;
	
	@Column(name = "cname")
	private String customerName;
	
	@Column(name = "mail id")
	private String mailId;
	
	@Column(name = "phoneno")
	private String phoneNo;
	
	@Column(name = "purchasedate")
	private Date purchaseDate = new Date(); // Set to the current system date by default
	
	@ManyToOne
    @JoinColumn(name = "mobileid")
    private Mobiles mobile;

	//setters and getter methods
	public Long getPurchaseId() {
		return purchaseId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}
	
	public Mobiles getMobile() {
		return mobile;
	}

	public void setMobile(Mobiles mobile) {
		this.mobile = mobile;
	}
	
	//overriding toString() methods
	@Override
	public String toString() {
		return "PurchaseDetails [purchaseId=" + purchaseId + ", customerName=" + customerName + ", mailId=" + mailId
				+ ", phoneNo=" + phoneNo + ", purchaseDate=" + purchaseDate + ", mobile=" + mobile + "]";
	}
	
}