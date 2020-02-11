/**
 * Customer - Define an object customer 
 * 
 * @author Danielle Monthe, Marie
 */
package entity;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Customer {

	private int id;
	private String firstname;
	private String lastname;
	private String birthday;
	private String address;
	private String city;
	private int code;
	// auto generate
	private String joinDate;
	
	public Customer() {
		super();
	}
	
	public Customer(int id, String firstname, String lastname, String birthday, String address, String city, int code) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthday = birthday;
		this.address = address;
		this.city = city;
		this.code = code;
		this.joinDate = getDateTime();
	}
	
	public Customer(String firstname, String lastname, String birthday, String address, String city, int code) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthday = birthday;
		this.address = address;
		this.city = city;
		this.code = code;
		this.joinDate = getDateTime();
	}

	// GETTERS AND SETTERS Methods
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
	// Auto generate date time
	public String getDateTime() {
		StringBuilder sb = new StringBuilder();
		Time time = Time.valueOf(LocalTime.now());
		LocalDate date = LocalDate.now();
		sb.append(date + " / " + time);
		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Kunde ID: " + id + "\n");
		sb.append("Name: " + firstname + " " + lastname + "\n");
		sb.append("Geburtstdatum: " + birthday + "\n");
		sb.append("Adresse: " + address + "," + code + " " + city + "\n");
		sb.append("Mietglied seit den: " + joinDate + "Uhr\n");
		return sb.toString();
	}
}
