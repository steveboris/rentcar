/**
 * ICarRepository - Interface for perform the CRUD(CREATE; READ; UPDATE; DELETE) operation 
 * to the DB.
 * 
 * @author Danielle Monthe, Marie
 */
package repository;

import java.util.ArrayList;

import entity.Car;
import exception.CRException;

public interface ICarRepository {

	// Add a car to the DB
	public boolean addCar(Car car) throws CRException;

	// Get a car from the DB
	public boolean deleteCar(int id) throws CRException;
	
	// Delete a car to the DB
	public boolean updateCar(Car car) throws CRException;

	// Update/Edit a car to the DB
	public Car getCar(int id) throws CRException;;

	// Get the number of all registered cars from the DB
	public ArrayList<Car> getAllCars() throws CRException;;

	// Get the list of all cars from the DB
	public int getCarCount() throws CRException;;
}
