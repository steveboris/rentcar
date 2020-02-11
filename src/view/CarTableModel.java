/**
 * CarTableModel - Implement the model table of for car 
 * @author Steve
 */
package view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Car;

public class CarTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3612532985082928399L;

	private static final int COLUMN_NB = 0;
	private static final int COLUMN_KAUFSDATUM = 1;
	private static final int COLUMN_KENMZEICHNEN = 2;
	private static final int COLUMN_COLOR = 3;
	private static final int COLUMN_HERSTELLER = 4;
	private static final int COLUMN_MODEL = 5;
	private static final int COLUMN_TYP = 6;
	private static final int COLUMN_PREIS = 7;
	private static final int COLUMN_VOLUME = 8;
	private static final int COLUMN_SPEED = 9;
	private static final int COLUMN_VERSICHERUNGSNR = 10;
	private static final int COLUMN_TUEV = 11;
	private String[] columnNames = { 
		"CAR_NR ", 
		"KAUFSDATUM", 
		"KENMZEICHNEN",
		"FARBE",
		"HERSTELLER",
		"MODEL",
		"TYP",
		"PREIS (€)", 
		"VOLUME (L)",
		"SPEED (Km/H)", 
		"VERSICHERUNGSNR",
		"TUEV"
	};
	private List<Car> cars;
	// Help to get from the DB the id of the last inserting car.
	private static int i = 1;

	public CarTableModel(List<Car> cars) {
		this.cars = cars;
		// retrieve the id of the last inserting car
		for (Car car : cars) {
			int id = car.getCarId() + 1;
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
		return cars.size();
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
		if (cars.isEmpty()) {
			return Object.class;
		}
		return getValueAt(0, columnIndex).getClass();
	}

	/*
	 * Method the get the selected item of the table
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Car car = cars.get(rowIndex);
		Object returnValue = null;

		switch (columnIndex) {
		case COLUMN_NB:
			returnValue = car.getCarId();
			break;
		case COLUMN_KAUFSDATUM:
			returnValue = car.getPurchaseDate();
			break;
		case COLUMN_KENMZEICHNEN:
			returnValue = car.getIdentificationNb();
			break;
		case COLUMN_COLOR:
			returnValue = car.getColor();
			break;
		case COLUMN_HERSTELLER:
			returnValue = car.getMarker();
			break;
		case COLUMN_MODEL:
			returnValue = car.getModel();
			break;
		case COLUMN_TYP:
			returnValue = car.getType();
			break;
		case COLUMN_PREIS:
			returnValue = car.getPrice();
			break;
		case COLUMN_VOLUME:
			returnValue = car.getVolume();
			break;
		case COLUMN_SPEED:
			returnValue = car.getSpeed();
			break;
		case COLUMN_VERSICHERUNGSNR:
			returnValue = car.getInsuranceNb();
			break;
		case COLUMN_TUEV:
			returnValue = car.getInspectionDate();
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
		Car car = cars.get(rowIndex);
		if (columnIndex == COLUMN_NB) {
			car.setCarId((int) value);
		}
	}

	/*
	 * This method help to remove the selected row
	 */
	public void removeRow(int rowIndex) {
		Car car = cars.get(rowIndex);
		cars.remove(car);
		// sort the car list
		sortCarList();
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/*
	 * This method help to add a new row to the table
	 */
	public void addRow(Car car) {
		car.setCarId(i++);
		cars.add(car);
		// sort the car list
		sortCarList();
		fireTableDataChanged();
	}

	/*
	 * This method help to update the value of the selected item after editing
	 */
	public void updateRow(Car c) {
		for (Car car : cars) {
			if (car.getCarId() == c.getCarId()) {
				car.setPurchaseDate(c.getPurchaseDate());
				car.setIdentificationNb(c.getIdentificationNb());
				car.setType(c.getType());
				car.setMarker(c.getMarker());
				car.setModel(c.getModel());
				car.setColor(c.getColor());
				car.setPrice(c.getPrice());
				car.setVolume(c.getVolume());
				car.setSpeed(c.getSpeed());
				car.setInsuranceNb(c.getInsuranceNb());
				car.setInspectionDate(c.getInspectionDate());
				break;
			}
		}
		// sort the car list
		sortCarList();
		// refresh the table
		fireTableDataChanged();
	}

	/*
	 * Help to sort the list of car sorted by Marker
	 */
	public List<Car> sortCarList() {
		Comparator<Car> compareByHersteller = (Car c1, Car c2) -> c1.getMarker().compareTo(c2.getMarker());
		Collections.sort(cars, compareByHersteller);
		
		return cars;
	}
}
