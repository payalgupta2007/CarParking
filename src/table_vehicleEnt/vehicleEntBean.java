package table_vehicleEnt;

public class vehicleEntBean {
	String vno;
	String vtype;
	String ctype;
	String floor;
	String entTime;
	String entDate;
	public vehicleEntBean(){}
	
	public vehicleEntBean(String vno, String vtype, String ctype, String floor, String entTime, String entDate) {
		super();
		this.vno = vno;
		this.vtype = vtype;
		this.ctype = ctype;
		this.floor = floor;
		this.entTime = entTime;
		this.entDate = entDate;
	}



	public String getVno() {
		return vno;
	}
	public void setVno(String vno) {
		this.vno = vno;
	}
	public String getVtype() {
		return vtype;
	}
	public void setVtype(String vtype) {
		this.vtype = vtype;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getEntTime() {
		return entTime;
	}
	public void setEntTime(String entTime) {
		this.entTime = entTime;
	}
	public String getEntDate() {
		return entDate;
	}
	public void setEntDate(String entDate) {
		this.entDate = entDate;
	}
	

}
