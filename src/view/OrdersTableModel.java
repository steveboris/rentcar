/**
S * OrdersTableModel - Implement the model table of for orders
 * @author Danielle Monthe, Marie ... 
 */
package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Orders;

public class OrdersTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int COLUMN_NB = 0;
	private static final int COLUMN_CARID = 1;
	private static final int COLUMN_CUSTOMERID = 2;
	private static final int COLUMN_RENTALDATE = 3;
	private static final int COLUMN_RETURNDATE = 4;
	private static final int COLUMN_CAUTION = 5;
	private static final int COLUMN_PRICE = 6;
	private String[] columnNames = { 
		"BESTELLUNG_NR ", 
		"FAHRZEUG_ID", 
		"KUNDE_ID",
		"ABHOLUNGSDATUM",
		"RÜCKGABEDATUM",
		"MIETCAUTION (€)",
		"MIETKOSTEN (€)",
	};
	private List<Orders> ordersList;
	// Help to get from the DB the id of the last inserting car.
	private static int i = 1;
		
	public OrdersTableModel(List<Orders> ordersList) {
		this.ordersList = ordersList;
		// Retrieve the id of the last inserting orders
		for(Orders orders : ordersList) {
			int id = orders.getID() + 1;
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
		return ordersList.size();
	}
	
	/*
	 * return the name of each column
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (ordersList.isEmpty()) {
			return Object.class;
		}
		return getValueAt(0, columnIndex).getClass();
	}
	
	/*
	 * Method the get the selected item of the table
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Orders orders = ordersList.get(rowIndex);
		Object returnValue = null;

		switch (columnIndex) {
		case COLUMN_NB:
			returnValue = orders.getID();
			break;
		case COLUMN_CARID:
			returnValue = orders.getCarID();
			break;
		case COLUMN_CUSTOMERID:
			returnValue = orders.getCustomerID();
			break;
		case COLUMN_RENTALDATE:
			returnValue = orders.getRentalDate();
			break;
		case COLUMN_RETURNDATE:
			returnValue = orders.getReturnDate();
			break;
		case COLUMN_CAUTION:
			returnValue = orders.getCaution();
			break;
		case COLUMN_PRICE:
			returnValue = orders.getPrice();
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
		Orders orders = ordersList.get(rowIndex);
		if (columnIndex == COLUMN_NB) {
			orders.setID((int) value);
		}
	}
	
	/*
	 * This method help to remove the selected row
	 */
	public void removeRow(int rowIndex) {
		Orders orders = ordersList.get(rowIndex);
		ordersList.remove(orders);
		// refresh the table
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/*
	 * This method help to add a new row to the table
	 */
	public void addRow(Orders orders) {
		orders.setID(i++);
		ordersList.add(orders);
		// refresh the items in to the table
		fireTableDataChanged();
	}

	/*
	 * This method help to update the value of the selected item after editing
	 */
	public void updateRow(Orders orders) {
		for (Orders obj : ordersList) {
			if (obj.getID() == orders.getID()) {
				obj.setCarID(orders.getCar().getCarId());
				obj.setCustomerID(orders.getCustomer().getId());
				obj.setRentalDate(orders.getRentalDate());
				obj.setReturnDate(orders.getReturnDate());
				obj.setCaution(orders.getCaution());
				obj.setPrice(orders.getPrice());
				break;
			}
		}
		// refresh the table
		fireTableDataChanged();
	}
	
}
