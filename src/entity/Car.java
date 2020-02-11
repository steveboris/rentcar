/**
 * Car - Define an object car 
 * 
 * @author Danielle Monthe, Marie
 */
package entity;

public class Car {
	private int carId;
	private String purchaseDate;
	private String identificationNb;
	private String marker;
	private String model;
	private String color;
	private String type;
	private float price;
	private int volume;
	private int speed;
	private String insuranceNb;
	private String inspectionDate;
	
	
	public Car(int carId, String purchaseDate, String identificationNb, String color, String marker, String model, 
			String type, float price, int volume, int speed, String insuranceNb, String inspectionDate) {
		super();
		this.carId = carId;
		this.purchaseDate = purchaseDate;
		this.identificationNb = identificationNb;
		this.marker = marker;
		this.model = model;
		this.color = color;
		this.type = type;
		this.price = price;
		this.volume = volume;
		this.speed = speed;
		this.insuranceNb = insuranceNb;
		this.inspectionDate = inspectionDate;
	}

	
	public Car(String purchaseDate, String identificationNb, String color, String marker, String model, 
			String type, float price, int volume, int speed, String insuranceNb, String inspectionDate) {
		super();
		this.purchaseDate = purchaseDate;
		this.identificationNb = identificationNb;
		this.marker = marker;
		this.model = model;
		this.color = color;
		this.type = type;
		this.price = price;
		this.volume = volume;
		this.speed = speed;
		this.insuranceNb = insuranceNb;
		this.inspectionDate = inspectionDate;
	}


	public Car() {
		super();
	}
	
	public int getCarId() {
		return carId;
	}


	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getIdentificationNb() {
		return identificationNb;
	}


	public void setIdentificationNb(String identificationNb) {
		this.identificationNb = identificationNb;
	}


	public String getInsuranceNb() {
		return insuranceNb;
	}


	public void setInsuranceNb(String insuranceNb) {
		this.insuranceNb = insuranceNb;
	}


	public String getMarker() {
		return marker;
	}


	public void setMarker(String marker) {
		this.marker = marker;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getVolume() {
		return volume;
	}


	public void setVolume(int volume) {
		this.volume = volume;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public String getPurchaseDate() {
		return purchaseDate;
	}


	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}


	public String getInsuranceNr() {
		return insuranceNb;
	}


	public void setInsuranceNr(String insuranceNr) {
		this.insuranceNb = insuranceNr;
	}


	public String getInspectionDate() {
		return inspectionDate;
	}


	public void setInspectionDate(String inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Car ID: " + carId + "\n");
		sb.append("Kaufsdatum: " + purchaseDate + "\n");
		sb.append("Kennzeichen: " + identificationNb + "\n");
		sb.append("Hersteller: " + marker + ", Model: " + model + ", Farbe: " + color + "\n");
		sb.append("Typ: " + type + "\n");
		sb.append("Mietpreis: " + price + "\n");
		sb.append("Tankvolumen: " + volume + ", Geschwindigkeit: " + speed + "\n");
		sb.append("VersicherungsNr: " + insuranceNb + "\n");
		sb.append("Tuev: " + inspectionDate + "\n");
		return sb.toString();
	}
	
}
