/**
 * CustomerService - Help to perform the CRUD operation for customers to the DB.
 * @author Danielle Monthe, Marie ..
 */
package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.Customer;
import exception.CRException;
import repository.ICustomerRepository;

public class CustomerService implements ICustomerRepository {

	private DBService db;

	public CustomerService() throws CRException {
		db = new DBService();
	}

	@Override
	public boolean addCustomer(Customer customer) throws CRException {
		// Query to insert a new customer
		final String QUERY = "INSERT INTO kunden VALUES (default, ?, ?, ?, ?, ?, ?, ?)";
		try {
			// connect to DB execute the method and close the connection
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(QUERY);
			preparedStatement.setString(1, customer.getLastname());
			preparedStatement.setString(2, customer.getFirstname());
			preparedStatement.setString(3, customer.getBirthday());
			preparedStatement.setString(4, customer.getAddress());
			preparedStatement.setString(5, customer.getCity());
			preparedStatement.setInt(6, customer.getCode());
			preparedStatement.setString(7, customer.getJoinDate());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			db.close();
		}
		// Successfully done than we return true
		return true;
	}

	@Override
	public Customer getCustomer(int id) throws CRException {
		Customer customer = new Customer();
		// Query to retrieve the customer with the giving id
		final String QUERY = "SELECT * FROM kunden WHERE KundeID = "+id;
		try {
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(QUERY);
			
			// Execute the query and return the customer
			ResultSet res = preparedStatement.executeQuery(QUERY);
			while (res.next()) {
				customer.setId(res.getInt("KundeID"));
				customer.setLastname(res.getString("Name"));
				customer.setFirstname(res.getString("Vorname"));
				customer.setBirthday(res.getString("Geburstsdatum"));
				customer.setAddress(res.getString("Adresse"));
				customer.setCity(res.getString("Ort"));
				customer.setCode(res.getInt("Postleitzahl"));
				customer.setJoinDate(res.getString("AnmeldungsDatum"));
			}
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			db.close();
		}
		return customer;
	}

	@Override
	public boolean deleteCustomer(int id) throws CRException {
		// Query to delete the customer with the giving id
		final String QUERY = "DELETE FROM kunden WHERE KundeID = ?;";

		try {
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(QUERY);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			db.close();
		}
		return true;
	}

	@Override
	public boolean updateCustomer(Customer customer) throws CRException {
		// Query to retrieve the customer with the giving id to perform the update
		final String QUERY = "UPDATE kunden SET Name=?, Vorname=?, Geburstsdatum=?,"
				+ " Adresse=?, Ort=?, Postleitzahl=? WHERE KundeID = ?;";
		try {
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(QUERY);
			// set the data
			preparedStatement.setString(1, customer.getLastname());
			preparedStatement.setString(2, customer.getFirstname());
			preparedStatement.setString(3, customer.getBirthday());
			preparedStatement.setString(4, customer.getAddress());
			preparedStatement.setString(5, customer.getCity());
			preparedStatement.setInt(6, customer.getCode());
			preparedStatement.setInt(7, customer.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CRException("Fehler bei der durchfuehrung");
		} finally {
			db.close();
		}
		return true;
	}

	@Override
	public int getCustomerCount() throws CRException {
		return getAllCustomers().size();
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws CRException {
		// List of customers
		ArrayList<Customer> customers = new ArrayList<Customer>();
		// Query to retrieve all customers from the DB
		final String QUERY = "SELECT * FROM kunden";
		try {
			// We have a successfully connection,then make the query
			PreparedStatement preparedStatement = db.getConnection().prepareStatement(QUERY);
			// Execute the query and get the data
			ResultSet res = preparedStatement.executeQuery(QUERY);
			while (res.next()) {
				Customer customer = new Customer();
				customer.setId(res.getInt("KundeID"));
				customer.setLastname(res.getString("Name"));
				customer.setFirstname(res.getString("Vorname"));
				customer.setBirthday(res.getString("Geburstsdatum"));
				customer.setAddress(res.getString("Adresse"));
				customer.setCity(res.getString("Ort"));
				customer.setCode(res.getInt("Postleitzahl"));
				customer.setJoinDate(res.getString("AnmeldungsDatum"));
				// Add the Customer to the customers list
				customers.add(customer);
			}
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			db.close();
		}

		// We sort the list of customers by name
		Comparator<Customer> compareByName = (Customer c1, Customer c2) -> c1.getLastname().compareTo(c2.getLastname());
		Collections.sort(customers, compareByName);
		// We return the list
		return customers;
	}

}
