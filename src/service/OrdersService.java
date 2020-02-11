/**
 * OrdersService - Help to perform the CRUD operation for orders to the DB.
 * @author Steve
 */
package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Orders;
import exception.CRException;
import repository.IOrdersRepository;

public class OrdersService implements IOrdersRepository {

	private DBService dbService;

	public OrdersService() throws CRException {
		dbService = new DBService();
	}

	@Override
	public boolean addOrders(Orders orders) throws CRException {
		// Query to insert a new orders
		final String QUERY = "INSERT INTO bestellungen VALUES (default, ?, ?, ?, ?, ?, ?)";

		try {
			// connect to DB execute the method and close the connection
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			preparedStatement.setInt(1, orders.getCar().getCarId());
			preparedStatement.setInt(2, orders.getCustomer().getId());
			preparedStatement.setString(3, orders.getRentalDate());
			preparedStatement.setString(4, orders.getReturnDate());
			preparedStatement.setFloat(5, orders.getCaution());
			preparedStatement.setFloat(6, orders.getPrice());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			dbService.close();
		}
		// Successfully done than we return true
		return true;
	}

	@Override
	public boolean deleteOrders(int id) throws CRException {
		// Query to delete the orders with the giving id
		final String QUERY = "DELETE FROM bestellungen WHERE BestellungsID = ?;";
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
	public boolean updateOrders(Orders orders) throws CRException {
		final String QUERY = "UPDATE bestellungen SET FahrzeugID=?, KundeID=?, AbholungsDatum=?,"
				+ " RueckgabeDatum=?, MietCaution=?, MietKosten=? "
				+ " WHERE BestellungsID = ?;";
		
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			preparedStatement.setInt(1, orders.getCar().getCarId());
			preparedStatement.setInt(2, orders.getCustomer().getId());
			preparedStatement.setString(3, orders.getRentalDate());
			preparedStatement.setString(4, orders.getReturnDate());
			preparedStatement.setFloat(5, orders.getCaution());
			preparedStatement.setFloat(6, orders.getPrice());
			preparedStatement.setInt(7,  orders.getID());
			// confirm the execution of the query
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CRException("Nicht aufgenommen");
		} finally {
			dbService.close();
		}
		
		return true;
	}

	@Override
	public Orders getOrders(int id) throws CRException {
		Orders orders = new Orders();
		// Query to retrieve the orders with the giving id
		final String QUERY = "SELECT * FROM bestellungen WHERE BestellungsID = "+id;
		
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			// Execute the query and get the data
			ResultSet res = preparedStatement.executeQuery(QUERY);
			while(res.next()) {
				orders.setID(res.getInt("BestellungsID"));
				orders.setCarID(res.getInt("FahrzeugID"));
				orders.setCustomerID(res.getInt("KundID"));
				orders.setRentalDate(res.getString("AbholungsDatum"));
				orders.setReturnDate(res.getString("RueckgabeDatum"));
				orders.setCaution(res.getFloat("MietCaution"));
				orders.setPrice(res.getFloat("MietKosten"));
			}
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			dbService.close();
		}

		return orders;
	}

	@Override
	public ArrayList<Orders> getAllOrders() throws CRException {
		// Orders list
		ArrayList<Orders> orders = new ArrayList<Orders>();
		// Query to retrieve all cars from the DB 
		final String QUERY = "SELECT * FROM bestellungen";
		
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			// Execute the query and get the data
			ResultSet res = preparedStatement.executeQuery(QUERY);
			while(res.next()) {
				Orders obj = new Orders();
				obj.setID(res.getInt("BestellungsID"));
				obj.setCarID(res.getInt("FahrzeugID"));
				obj.setCustomerID(res.getInt("KundeID"));
				obj.setRentalDate(res.getString("AbholungsDatum"));
				obj.setReturnDate(res.getString("RueckgabeDatum"));
				obj.setCaution(res.getFloat("MietCaution"));
				obj.setPrice(res.getFloat("MietKosten"));
				orders.add(obj);
			}
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			dbService.close();
		}
		
		//
		return orders;
	}

	@Override
	public int getOrdersCount() throws CRException {
		return getAllOrders().size();
	}

	@Override
	public ArrayList<Integer> getAllCarId() throws CRException {
		ArrayList<Integer> carIds = new ArrayList<Integer>();
		// Query to retrieve the id of registered cars
		String QUERY = "SELECT FahrzeugID FROM fahrzeuge";
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			ResultSet res = preparedStatement.executeQuery(QUERY);
			while(res.next()) {
				carIds.add(res.getInt("FahrzeugID"));
			}
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			// Don't close the DB connection yet because we also need the customer id 
			//dbService.close();
		}

		return carIds;
	}

	@Override
	public ArrayList<Integer> getAllCustomerId() throws CRException {
		ArrayList<Integer> customerIds = new ArrayList<Integer>();
		// Query to retrieve the id of registered customers
		String QUERY = "SELECT KundeID FROM kunden";
		try {
			PreparedStatement preparedStatement = dbService.getConnection().prepareStatement(QUERY);
			ResultSet res = preparedStatement.executeQuery(QUERY);
			while(res.next()) {
				customerIds.add(res.getInt("KundeID"));
			}
		} catch (SQLException e) {
			throw new CRException(e.getMessage());
		} finally {
			dbService.close();
		}
		return customerIds;
	}
}
