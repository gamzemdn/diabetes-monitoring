package info.androidhive.loginandregistration;

public class GooglePlace {
	private String name;
	private String category;
	private String adress;
	private String lat;
	private String lng;
	private String number;

	public GooglePlace() {
		this.name = "";
	
		this.setCategory("");
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getAdress() {
		return adress;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLat() {
		return lat;
	}
	public String getLng() {
		return lng;
	}
    public void setLat(String lat) {
        this.lat= lat;
    }
    public void setLng(String lng) {
        this.lng= lng;
    }
}
