/*
 * DBService - Help to connect the application with the Database
 *  
 *  @author Danielle Monthe, Marie ...
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import exception.CRException;

public class DBService {

	private Connection connection = null;
	private Statement statement = null;
	private String hostname = "localhost"; // default host
	private String username = "root"; // default local username
	private String passwd = ""; // default local password
	private String dbname = "car_rent";

	public DBService(String hostname, String dbname, String user, String passwd) {
		this.hostname = hostname;
		this.dbname = dbname;
		this.username = user;
		this.passwd = passwd;
	}

	// default constructor to connection to local database
	public DBService() throws CRException {
		super();
		connect();
	}

	/*
	 * Method to establish the connection using the MySql Server driver
	 */
	public void connect() throws CRException {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Setup the connection with the default DB parameters
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + hostname + "/" + dbname + "?" + "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, passwd);// + "user=" + username + "&password=" + passwd);
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			// set the default database to be use
			statement.executeQuery("use " + dbname);

		} catch (SQLException ex) {
			// handle any errors
			throw new CRException(
					"Fehler(" + ex.getSQLState() + "): \nVerbindung zur Datenbank festgeschlagen!\nDen Webserver ist bestimmt aus.");
		} catch (ClassNotFoundException e) {
			// handle driver error
			throw new CRException("Mysql Treiber ist nicht erkennt!");
		}
	}

	/*
	 * Method to close the connection
	 */
	public void close() throws CRException {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			throw new CRException("Fehler: Die Datenbank Verbindung kann nicht zugemacht werden!\n" + e.getMessage());
		}
	}

	// Getters and Setters
	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
		
	public Connection getConnection() {
		return connection;
	}
}
