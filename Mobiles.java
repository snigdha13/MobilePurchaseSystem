package MobilePurchaseSystem.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//annotation to create table
@Entity  
//annotation to give table name
@Table(name="mobiles")
public class Mobiles {
	//annotation for primary key
	@Id
	//annotation to set column name
	@Column(name = "mobileid")
	private int mobileId;
	private String name;
	private double price;
	private int quantity;
	
	@OneToMany(mappedBy = "mobile")
    private List<PurchaseDetails> purchaseDetails;
	
	//setters and getter methods
	public int getMobileId() {
		return mobileId;
	}
	public void setMobileId(int i) {
		this.mobileId = i;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	//overriding toString() methods
	@Override
	public String toString() {
		return "Mobiles [mobileId=" + mobileId + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
	}
	
}

