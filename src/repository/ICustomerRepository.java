/**
 * ICustomerRepository - Interface for perform the CRUD(CREATE; READ; UPDATE; DELETE) operation 
 * to the DB.
 * 
 * @author Steve
 */
package repository;

import java.util.Collection;

import entity.Customer;
import exception.CRException;

public interface ICustomerRepository {

	// Add a customer to the DB
	public boolean addCustomer(Customer customer) throws CRException;
	
	// Get a customer from the DB
	public Customer getCustomer(int id) throws CRException;
	
	// Delete a customer to the DB
	public boolean deleteCustomer(int id) throws CRException;
	
	// Update/Edit a customer to the DB
	public boolean updateCustomer(Customer customer) throws CRException;
	
	// Get the number of all registered customers from the DB
	public int getCustomerCount() throws CRException;
	
	// Get the list of all customers from the DB
	public Collection<Customer> getAllCustomers() throws CRException; 
}
