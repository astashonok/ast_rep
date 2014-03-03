package tests;

public class Venue {

	private String name;
	private String street;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private boolean hasReservedSeating;
	private int maxCapacity;
	
	
	
	public Venue(String name, String street, String street2, String city,
			String state, String zip, String country,
			boolean hasReservedSeating, int maxCapacity) {
		super();
		this.name = name;
		this.street = street;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.hasReservedSeating = hasReservedSeating;
		this.maxCapacity = maxCapacity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean isHasReservedSeating() {
		return hasReservedSeating;
	}
	public void setHasReservedSeating(boolean hasReservedSeating) {
		this.hasReservedSeating = hasReservedSeating;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxXapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	
	
}
