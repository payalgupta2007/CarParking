package table_customers;

public class CustBean {

	String name;
	String mob;
	String address;
	String city;
	String gender;
	
	public CustBean(){}

	public CustBean(String name, String mob, String address, String city, String gender) {
		super();
		this.name = name;
		this.mob = mob;
		this.address = address;
		this.city = city;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
}
