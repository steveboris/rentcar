/**
 * CarService - Help to perform the CRUD operation for cars to the DB.
 * @author Steve
 */
package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.mysql.cj.exceptions.NumberOutOfRange;

import entity.Car;
import exception.CRException;
import repository.ICarRepository;

public class CarService implements ICarRepository {

	private DBService dbService;
	
	public CarService() throws CRException {
		dbService = new DBService();
	}
	
	@Override
	public boolean addCar(Car car) throws CRException  {
		// Query to insert a new car
		final String QUERY = "INSERT INTO fahrzeuge VALUES(default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		// Read values from the new car object and save each value to the respective column into the table on DB 
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			preparedStatement.setString(1, car.getPurchaseDate());
			preparedStatement.setString(2, car.getIdentificationNb());
			preparedStatement.setString(3, car.getColor());
			preparedStatement.setString(4, car.getMarker());
			preparedStatement.setString(5, car.getModel());
			preparedStatement.setString(6, car.getType());
			preparedStatement.setFloat(7, car.getPrice());
			preparedStatement.setInt(8, car.getVolume());
			preparedStatement.setInt(9, car.getSpeed());
			preparedStatement.setString(10, car.getInsuranceNb());
			preparedStatement.setString(11, car.getInspectionDate());
			// execute the query 
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			// After the action finally close the connection
			dbService.close();
		}
		// Successfully done than we return true
		return true;
	}

	@Override
	public boolean deleteCar(int id) throws CRException {
		// Query to delete the car with the giving id
		final String QUERY = "DELETE FROM fahrzeuge WHERE FahrzeugID = ?;";

		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			dbService.close();
		}
		
		return true;
	}

	@Override
	public boolean updateCar(Car car) throws CRException {
		final String QUERY = "UPDATE fahrzeuge SET KaufsDatum=?, Kennzeichnen=?, Farbe=?,"
				+ " Hersteller=?, Model=?, Typ=?, Preis=?, TankVolume=?,"
				+ " Geschwindigkeit=?, VersicherungsNr=?, Tuev=?"
				+ " WHERE FahrzeugID = ?;";
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			// set the data
			preparedStatement.setString(1, car.getPurchaseDate());
			preparedStatement.setString(2, car.getIdentificationNb());
			preparedStatement.setString(3, car.getColor());
			preparedStatement.setString(4, car.getMarker());
			preparedStatement.setString(5, car.getModel());
			preparedStatement.setString(6, car.getType());
			preparedStatement.setFloat(7, car.getPrice());
			preparedStatement.setInt(8, car.getVolume());
			preparedStatement.setInt(9, car.getSpeed());
			preparedStatement.setString(10, car.getInsuranceNb());
			preparedStatement.setString(11, car.getInspectionDate());
			preparedStatement.setInt(12, car.getCarId());
			// confirm the execution of the query
			preparedStatement.executeUpdate();
		} catch (SQLException | NumberOutOfRange e) {
			throw new CRException("Tankvolume, die Geschwindigkeit oder den Preis ist zu Gross. ");
		} finally {
			dbService.close();
		}
		return true;
	}

	@Override
	public Car getCar(int id) throws CRException {
		Car car = new Car();
		// Query to retrieve the customer with the giving id
		final String QUERY = "SELECT * FROM fahrzeuge WHERE FahrzeugID = " + id;
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			// Execute the query and get the data
			ResultSet res = preparedStatement.executeQuery(QUERY);
			while (res.next()) {
				car.setCarId(res.getInt("FahrzeugID"));
				car.setPurchaseDate(res.getString("KaufsDatum"));
				car.setIdentificationNb(res.getString("Kennzeichnen"));
				car.setType(res.getString("Typ"));
				car.setMarker(res.getString("Hersteller"));
				car.setModel(res.getString("Model"));
				car.setColor(res.getString("Farbe"));
				car.setPrice(res.getFloat("Preis"));
				car.setVolume(res.getInt("TankVolume"));
				car.setSpeed(res.getInt("Geschwindigkeit"));
				car.setInsuranceNb(res.getString("VersicherungsNr"));
				car.setInspectionDate(res.getString("Tuev"));
			}
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			dbService.close();
		}
		return car;
	}

	@Override
	public ArrayList<Car> getAllCars() throws CRException {
		// List of cars to be returned
		ArrayList<Car> cars = new ArrayList<Car>();
		// Query to retrieve all cars from the DB 
		final String QUERY = "SELECT * FROM fahrzeuge";
		// Read from the DB all cars and add each retrieving objects to the list of car
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			// Execute the query and get the data
			ResultSet res = preparedStatement.executeQuery(QUERY);
			// retrieve the cars
			while (res.next()) {
				// while there is a car we get it and put it into the list
				// New oBject car
				Car car = new Car();
				car.setCarId(res.getInt("FahrzeugID"));
				car.setPurchaseDate(res.getString("KaufsDatum"));
				car.setIdentificationNb(res.getString("Kennzeichnen"));
				car.setType(res.getString("Typ"));
				car.setMarker(res.getString("Hersteller"));
				car.setModel(res.getString("Model"));
				car.setColor(res.getString("Farbe"));
				car.setPrice(res.getFloat("Preis"));
				car.setVolume(res.getInt("TankVolume"));
				car.setSpeed(res.getInt("Geschwindigkeit"));
				car.setInsuranceNb(res.getString("VersicherungsNr"));
				car.setInspectionDate(res.getString("Tuev"));
				// Add the reading car into the list of car
				cars.add(car);
			}
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			dbService.close();
		}

		// We sort the list by Marker
		Comparator<Car> compareByHersteller = (Car c1, Car c2) -> c1.getMarker().compareTo(c2.getMarker());
		Collections.sort(cars, compareByHersteller);
		// than we return the list of car
		return cars;
	}

	@Override
	public int getCarCount() throws CRException {
		return getAllCars().size();
	}

}
