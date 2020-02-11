/**
 * Orders - Define an object Order 
 * 
 * @author Steve
 */
package entity;

public class Orders {

	private int ID;
	private int carID;
	private int customerID;
	private String rentalDate;
	private String returnDate;
	private float caution;
	private float price;
	private int rentalPeriod;
	
	private Customer customer;
	private Car car;
	
	
	public Orders(int id, int carID, int customerID, String rentalDate, String returnDate, float caution,
			int rentalPeriod) {
		super();
		this.ID = id;
		this.carID = carID;
		this.customerID = customerID;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.caution = caution;
		this.rentalPeriod = rentalPeriod;
		this.price = computeTotalPrice();
	}


	public Orders(int carID, int customerID, String rentalDate, String returnDate, float caution, int rentalPeriod) {
		super();
		this.carID = carID;
		this.customerID = customerID;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.caution = caution;
		this.rentalPeriod = rentalPeriod;
		this.price = computeTotalPrice();
	}


	public Orders(int id, Car car, Customer customer, int rentalPeriod) {
		super();
		this.ID = id;
		this.customer = customer;
		this.car = car;
		this.rentalPeriod = rentalPeriod;
		this.price = computeTotalPrice();
	}
	
	public Orders(int id, Car car, Customer customer) {
		super();
		this.ID = id;
		this.customer = customer;
		this.car = car;
	}


	public Orders() {
		super();
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public int getCarID() {
		return carID;
	}


	public void setCarID(int carID) {
		this.carID = carID;
	}


	public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}


	public String getRentalDate() {
		return rentalDate;
	}


	public void setRentalDate(String rentalDate) {
		this.rentalDate = rentalDate;
	}


	public String getReturnDate() {
		return returnDate;
	}


	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}


	public float getCaution() {
		return caution;
	}


	public void setCaution(float caution) {
		this.caution = caution;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getRentalPeriod() {
		return rentalPeriod;
	}


	public void setRentalPeriod(int rentalPeriod) {
		this.rentalPeriod = rentalPeriod;
	}
	
	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	public float computeTotalPrice() {
		return getCar().getPrice() * rentalPeriod; 
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Information zu der BestellungID: "+ ID + "\n");
		sb.append("******************************************\n");
		sb.append("Das Fahrzeug mit ID: " + getCar().getCarId() + " wurde fuer den Kunde mit ID: " +getCustomer().getId() + "\n");
		sb.append("Von: " + rentalDate + " bis " + returnDate + " reserviert.\n\n");
		sb.append("FahrzeugID: "+ getCar().getCarId() + "\n");
		sb.append("Kennzeichnen: " + getCar().getIdentificationNb() + "\n");
		sb.append("Hersteller: " + getCar().getMarker() + "\n");
		sb.append("Model: " + getCar().getModel() + "\n");
		sb.append("Kostet pro Tag: " + getCar().getPrice() + " €\n\n");
		sb.append("Kunde ID: " + getCustomer().getId() + "\n");
		sb.append("Name: " + getCustomer().getFirstname() + " " + getCustomer().getLastname() + "\n");
		sb.append("Geboren am: " + getCustomer().getBirthday() + "\n");
		sb.append("Wohnort: " + getCustomer().getCity() + "\n\n");
		sb.append("Caution: " + caution + " €\n");
		sb.append("Gesammt Kosten: " + getPrice() + " €.\n");
		sb.append("_______________________");
		return sb.toString();
	}
	
}
