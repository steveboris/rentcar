/**
 * IOrdersRepository - Interface for perform the CRUD(CREATE; READ; UPDATE; DELETE) operation 
 * to the DB.
 * 
 * @author Danielle Monthe, Marie
 */
package repository;

import java.util.ArrayList;

import entity.Orders;
import exception.CRException;

public interface IOrdersRepository {

	// Add an orders to the DB
	public boolean addOrders(Orders orders) throws CRException;
	
	// Get an orders from the DB
	public boolean deleteOrders(int id) throws CRException;
	
	// Delete an orders to the DB
	public boolean updateOrders(Orders orders) throws CRException;
	
	// Update/Edit an orders to the DB
	public Orders getOrders(int id) throws CRException;

	// Get the number of all registered orders from the DB
	public ArrayList<Orders> getAllOrders() throws CRException;

	// Get the list of all orders from the DB
	public int getOrdersCount() throws CRException;
	
	// Return the id of cars
	public ArrayList<Integer> getAllCarId() throws CRException;
	
	// Return the list of customers
	public ArrayList<Integer> getAllCustomerId() throws CRException;
}
