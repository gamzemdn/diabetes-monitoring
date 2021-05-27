package info.androidhive.loginandregistration;
public class Results {
	
	double value,kilo;
	String tarih,gender,email;
	int age;
int Id;
	
	public Results(){
		super();
	}
	
	public Results(double value, String tarih, double kilo, int age,String gender, String email) {
		super();
		this.value=value;
		this.tarih=tarih;
		this.age=age;
		this.kilo=kilo;
		this.gender=gender;
		this.email=email;
	}
	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	public double getKilo() {
		return kilo;
	}

	public void setKilo(double kilo) {
		this.kilo = kilo;
	}

	public String getDate() {
		return tarih;
	}

	public void setDate(String tarih) {
		this.tarih = tarih;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
