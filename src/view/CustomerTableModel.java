/**
 * CustomerTableModel - Implement the model table of for customer 
 * @author Danielle Monthe, Marie ... 
 */
package view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import entity.Customer;

public class CustomerTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -91474231421272207L;
	private static final int COLUMN_NB = 0;
	private static final int COLUMN_LASTNAME = 1;
	private static final int COLUMN_FIRSTNAME = 2;
	private static final int COLUMN_BIRTHDAY = 3;
	private static final int COLUMN_ADR = 4;
	private static final int COLUMN_ORT = 5;
	private static final int COLUMN_CODE = 6;
	private static final int COLUMN_DATUM = 7;

	private String[] columnNames = { "KUNDE_NR", "NAME", "VORNAME", "GEBURTSTDATUM", "ADRESSE", "ORT", "POSTLEITZAHL",
			"MIGLIED SEIT" };

	private List<Customer> customers;
	// Help to get from the DB the id of the last inserting car.
	private static int i = 1;

	public CustomerTableModel(List<Customer> customers) {
		this.customers = customers;
		// get the id of the last added customer
		for (Customer customer : customers) {
			int id = customer.getId() + 1;
			// Because the list is sorted by name
			if (id > i)
				i = id;
		}
	}

	/*
	 * return the total number of column
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/*
	 * return the selected row
	 */
	@Override
	public int getRowCount() {
		return customers.size();
	}

	/*
	 * return the name of each column
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	/*
	 * 
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (customers.isEmpty()) {
			return Object.class;
		}
		return getValueAt(0, columnIndex).getClass();
	}

	/*
	 * Method the get the selected item of the table
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Customer customer = customers.get(rowIndex);
		Object returnValue = null;

		switch (columnIndex) {
		case COLUMN_NB:
			returnValue = customer.getId();
			break;
		case COLUMN_LASTNAME:
			returnValue = customer.getLastname();
			break;
		case COLUMN_FIRSTNAME:
			returnValue = customer.getFirstname();
			break;
		case COLUMN_BIRTHDAY:
			returnValue = customer.getBirthday();
			break;
		case COLUMN_ADR:
			returnValue = customer.getAddress();
			break;
		case COLUMN_ORT:
			returnValue = customer.getCity();
			break;
		case COLUMN_CODE:
			returnValue = customer.getCode();
			break;
		case COLUMN_DATUM:
			returnValue = customer.getJoinDate();
			break;
		default:
			break;
		}

		return returnValue;
	}

	/*
	 * This method help to set the values of the to be update item
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Customer customer = customers.get(rowIndex);
		if (columnIndex == COLUMN_NB) {
			customer.setId((int) value);
		}
	}

	/*
	 * This method help to remove the selected row
	 */
	public void removeRow(int rowIndex) {
		Customer customer = customers.get(rowIndex);
		customers.remove(customer);
		// sort the list
		sortCustomerList();
		// Refresh the table after a delete
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/*
	 * This method help to add a new row to the table
	 */
	public void addRow(Customer customer) {
		customer.setId(i++);
		customers.add(customer);
		// sort the list
		sortCustomerList();
		// refresh the table after adding
		fireTableDataChanged();
	}

	/*
	 * This method help to update the value of the selected item after editing
	 */
	public void updateRow(Customer c) {
		// Trying to retrieve the customer from the customer list
		for (Customer customer : customers) {
			if (customer.getId() == c.getId()) {
				customer.setLastname(c.getLastname());
				customer.setFirstname(c.getFirstname());
				customer.setBirthday(c.getBirthday());
				customer.setAddress(c.getAddress());
				customer.setCity(c.getCity());
				customer.setCode(c.getCode());
				break;
			}
		}
		// sort the list
		sortCustomerList();
		// refresh the table
		fireTableDataChanged();
	}

	/*
	 * Help to sort the List customer by Name
	 */
	public List<Customer> sortCustomerList() {
		Comparator<Customer> compareByName = (Customer c1, Customer c2) -> c1.getLastname().compareTo(c2.getLastname());
		Collections.sort(customers, compareByName);
		return customers;
	}
}