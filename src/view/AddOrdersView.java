/**
 * AddOrdersView - Help to display the panel to add a new orders or edit an existing orders
 * @author Steve
 */
package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import entity.Car;
import entity.Customer;
import entity.Orders;
import exception.CRException;
import repository.ICarRepository;
import repository.ICustomerRepository;
import repository.IOrdersRepository;
import service.CarService;
import service.CustomerService;
import service.OrdersService;

public class AddOrdersView extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox<Integer> carId;
	private JComboBox<Integer> customerId;
	private JTextField tfPeriod;
	private JTextField tfRentalDate;
	private JTextField tfReturnDate;
	private JTextField tfCaution;
	private JLabel lblAction;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JLabel lblNotification;
	// help to get the id on the last inserting object from the DB
	private int id;
	private OrdersTableModel tablemodel;
	private IOrdersRepository ordersService;
	private ICustomerRepository customerService;
	private ICarRepository carService;

	// choice models
	private ComboBoxModel<Integer> carIdsModel;
	private ComboBoxModel<Integer> customerIdsModel;

	public AddOrdersView(OrdersTableModel tablemodel) throws CRException {
		// Initialization
		this.tablemodel = tablemodel;
		
		ordersService = new OrdersService();
		// Retrieve the objects id from the DB
		ArrayList<Integer> availableCarIDList = ordersService.getAllCarId();
		ArrayList<Integer> availableCustomerIDList = ordersService.getAllCustomerId();
		// Building the choice of registered cars and customers
		Integer[] allCarId = new Integer[availableCarIDList.size()];
		Integer[] allCustomerId = new Integer[availableCustomerIDList.size()];
		for (int i = 0; i < availableCarIDList.size(); ++i) {
			allCarId[i] = availableCarIDList.get(i);
		}
		for (int i = 0; i < availableCustomerIDList.size(); ++i) {
			allCustomerId[i] = availableCustomerIDList.get(i);
		}
		// Initialize the choice into the object comboBox
		carIdsModel = new DefaultComboBoxModel<Integer>(allCarId);
		customerIdsModel = new DefaultComboBoxModel<Integer>(allCustomerId);
		
		// Settings of the main panel of this window
		Container contentPane = getContentPane();
		((JComponent) contentPane).setBorder(new LineBorder(Color.CYAN, 1));
		setContentPane(contentPane);
		// main windows panel
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));

		// Panel to define the layout.
		JPanel panel = new JPanel(new GridBagLayout());
		// Constraints for the layout
		GridBagConstraints constr = new GridBagConstraints();
		constr.insets = new Insets(5, 5, 5, 5);
		constr.anchor = GridBagConstraints.WEST;
		// Label to display the action
		JPanel header = new JPanel();
		lblAction = new JLabel("Bestellung abbuchen");
		lblAction.setForeground(Color.WHITE);
		header.add(lblAction);
		// Set the initial grid values to 0,0
		constr.gridx = 0;
		constr.gridy = 0;

		// Label to displaying construction
		JLabel lblCarID = new JLabel("CarID:");
		JLabel lblCustomerID = new JLabel("KundeID:");
		JLabel lblPeriod = new JLabel("Anzahl der Tage: ");
		JLabel lblRentalDate = new JLabel("Von (MietDatum):");
		JLabel lblReturnDate = new JLabel("Bis (RückgabeDatum):");
		JLabel lblCaution = new JLabel("Caution (€): ");

		// Get the date
		LocalDate today = LocalDate.now();
		String value = today.toString();
		String[] parts = value.split("-");
		StringBuilder sb = new StringBuilder();
		sb.append(parts[2] + "/" + parts[1] + "/" + parts[0]);
		
		// Building of the fields
		carId = new JComboBox<Integer>(carIdsModel);
		customerId = new JComboBox<Integer>(customerIdsModel);
		tfPeriod = new JTextField(20);
		tfRentalDate = new JTextField(20);
		tfRentalDate.setText(sb.toString());
		tfRentalDate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tfRentalDate.setText("");
			}

			public void mouseExited(MouseEvent me) {
				if (tfRentalDate.getText().length() == 0) {
					tfRentalDate.setText(sb.toString());
				}
			}
		});
		tfReturnDate = new JTextField(20);
		tfReturnDate.setText(sb.toString());
		tfReturnDate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tfReturnDate.setText("");
			}

			public void mouseExited(MouseEvent me) {
				if (tfReturnDate.getText().length() == 0) {
					String value = today.toString();
					String[] parts = value.split("-");
					StringBuilder sb = new StringBuilder();
					sb.append(parts[2] + "/" + parts[1] + "/" + parts[0]);
					tfReturnDate.setText(sb.toString());
				}
			}
		});
		tfCaution = new JTextField(20);

		// Adding labels and fields to the window
		panel.add(lblCarID, constr);
		constr.gridx = 1;
		panel.add(carId, constr);
		constr.gridx = 0;
		constr.gridy = 1;

		panel.add(lblCustomerID, constr);
		constr.gridx = 1;
		panel.add(customerId, constr);
		constr.gridx = 0;
		constr.gridy = 2;

		panel.add(lblPeriod, constr);
		constr.gridx = 1;
		panel.add(tfPeriod, constr);
		constr.gridx = 0;
		constr.gridy = 3;

		panel.add(lblRentalDate, constr);
		constr.gridx = 1;
		panel.add(tfRentalDate, constr);
		constr.gridx = 0;
		constr.gridy = 4;

		panel.add(lblReturnDate, constr);
		constr.gridx = 1;
		panel.add(tfReturnDate, constr);
		constr.gridx = 0;
		constr.gridy = 5;

		panel.add(lblCaution, constr);
		constr.gridx = 1;
		panel.add(tfCaution, constr);
		constr.gridy = 6;

		constr.anchor = GridBagConstraints.CENTER;
		// Button with text "Speichern"
		saveBtn = new JButton("Speichern");
		saveBtn.addActionListener(this);
		// Button with text "Abbrechen"
		cancelBtn = new JButton("Abbrechen");
		cancelBtn.addActionListener(this);

		// Add label and button to panel
		Box hbox = Box.createHorizontalBox();
		hbox.add(saveBtn);
		hbox.add(Box.createHorizontalStrut(5));
		hbox.add(cancelBtn);
		panel.add(hbox, constr);
		constr.gridy = 7;

		// add panel for error
		JPanel panelError = new JPanel();
		lblNotification = new JLabel();
		panelError.add(lblNotification);

		// Panels customizing
		header.setBackground(new Color(0, 153, 204));
		panel.setBackground(Color.LIGHT_GRAY);
		panelError.setBackground(new Color(0, 153, 204));

		// Adding panels on the main panel to be showing
		mainpanel.add(header);
		mainpanel.add(panel);
		mainpanel.add(panelError);

		contentPane.add(mainpanel);

		// Main frame customizing
		setTitle("Kunden Verwaltung");
		setUndecorated(true);
		pack();
		setSize(400, 400);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelBtn) {
			// if the cancel button pressed we close this view
			AddOrdersView.this.dispose();
		} else {
			// if the add button pressed
			try {
				ordersService = new OrdersService();
				// help to retrieve the object car from the DB
				carService = new CarService();
				// Help to retrieve the object Customer from the DB
				customerService = new CustomerService();
				
				// Reading input on text fields
				int id = getId(); // Orders id
				Car car = carService.getCar(getCarID());
				Customer customer = customerService.getCustomer(getCustomerID());
				int rentalPeriod = getPeriod();
				String rentalDate = getRentalDate();
				String returnDate = getReturnDate();
				float caution = getCaution();
				
				// Create the object orders
				Orders orders = new Orders(id, car, customer, rentalPeriod);
				orders.setCarID(car.getCarId());
				orders.setCustomerID(customer.getId());
				orders.setRentalDate(rentalDate);
				orders.setReturnDate(returnDate);
				orders.setCaution(caution);
				orders.setPrice(orders.getPrice());
				/* Check what operation the user want to be executed 
				* that the label showing the action on the window can be read, let getting what action to
				* be done. The choice are (Add or Edit)
				*/
				if (!getAction().contains("bearbeiten")) { 
					// The user want to add a new orders, than save the new orders into the DB
					ordersService.addOrders(orders);
					//we also need the new added orders to be displaying on the list of all orders
					tablemodel.addRow(orders);
					// Than notify the user that the operation done well.
					lblNotification.setForeground(Color.GREEN);
					lblNotification.setText("Die Abbuchung wurde Erfolgreich abgearbeitet!");
				} else {
					// Otherwise the user want to make an update on orders
					// Call the method for updating in the DB
					ordersService.updateOrders(orders);
					// we also need to update the row on the table
					tablemodel.updateRow(orders);
					// Than notify the user that the operation done well.
					lblNotification.setForeground(Color.GREEN);
					lblNotification.setText("Aenderungen Erfolgreich abgearbeitet!");
					// And we must set the label displaying the action to make to something else.
					setAction("Bestellung abbuchen");
				}
				// If everything done well than set the fields to the default values
				// Get the date
				LocalDate today = LocalDate.now();
				String value = today.toString();
				String[] parts = value.split("-");
				StringBuilder sb = new StringBuilder();
				sb.append(parts[2] + "/" + parts[1] + "/" + parts[0]);
				tfRentalDate.setText(sb.toString());
				tfReturnDate.setText(sb.toString());
				tfCaution.setText("");
				tfPeriod.setText("");
			} catch (CRException cre) {
				// if error than displaying to the user
				lblNotification.setForeground(Color.RED);
				lblNotification.setText(cre.getMessage());
			}
		}
	}

	// Get input from fields
	public int getId() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getCarID() {
		return (int) carId.getSelectedItem();
	}

	public void setCarId(int id) {
		carId.setSelectedItem(id);
	}

	public int getCustomerID() {
		return (int) customerId.getSelectedItem();
	}

	public void setCustomerId(int id) {
		customerId.setSelectedItem(id);
	}
	
	public int getPeriod() throws CRException {
		if (tfPeriod.getText().toString().equals("") || tfPeriod.getText() == null) {
			tfPeriod.requestFocusInWindow();
			throw new CRException("Die Anzahl von Tage bitte ueberpruefen");
		} else {
			try {
				int period = Integer.parseInt(tfPeriod.getText().toString());
				return period;
			} catch (NumberFormatException nfe) {
				tfPeriod.requestFocusInWindow();
				throw new CRException("Die Anzahl von Tage bitte ueberpruefen");
			}
		}
	}

	public String getRentalDate() throws CRException {
		LocalDate today = LocalDate.now();
		if (tfRentalDate.getText().toString().equals("") || tfRentalDate.getText() == null
				|| tfRentalDate.getText().length() < 10 || tfRentalDate.getText().length() > 10) {
			tfRentalDate.requestFocusInWindow();
			throw new CRException("Das MietDatum bitte richtig eingeben! (Bsp." + today.toString() +")");
		} else {
			String[] parts = tfRentalDate.getText().toString().split("/");
			// check each part of the date containing the day, month and year
			try {
				// try converting each part to an integer
				int day = Integer.valueOf(parts[0]);
				int month = Integer.valueOf(parts[1]);
				int year = Integer.valueOf(parts[2]);

				if (year < 2020) {
					tfRentalDate.requestFocusInWindow();
					throw new CRException("Das Jahr des Mietdatums stimmt eher nicht");
				}
				
				if (day < today.getDayOfMonth()) {
					tfRentalDate.requestFocusInWindow();
					throw new CRException("Den Tag des Mietdatums stimmt eher nicht");
				}
				
				if (day < 0 || day > 31) {
					tfRentalDate.requestFocusInWindow();
					throw new CRException("Den Tag des Mietdatums bitte ueberprufen!");
				}

				if (month < 0 || month > 12) {
					tfRentalDate.requestFocusInWindow();
					throw new CRException("Den Monat des Mietdatums bitte ueberprufen!");
				}

				if (month == 2 && day > 29) {
					tfRentalDate.requestFocusInWindow();
					throw new CRException("Den Monat oder den Tag des Mietdatums bitte ueberprufen!");
				}

				StringBuilder sb = new StringBuilder();
				if (day < 10) {
					sb.append("0"+day+"/");
				} else {
					sb.append(day+"/");
				}
				
				if (month < 10) {
					sb.append("0"+month+"/");
				} else {	
					sb.append(month+"/");
				}
				
				sb.append(year);
				return sb.toString();
			} catch (NumberFormatException nfe) {
				tfRentalDate.requestFocusInWindow();
				throw new CRException("Das MietDatum bitte richtig eingeben! (Bsp." + today.toString() +")");
			}
		}
	}
	
	public void setRentalDate(String date) {
		tfRentalDate.setText(date);
	}
	
	public String getReturnDate() throws CRException {
		LocalDate today = LocalDate.now();
		if (tfReturnDate.getText().toString().equals("") || tfReturnDate.getText() == null
				|| tfReturnDate.getText().length() < 10 || tfReturnDate.getText().length() > 10) {
			tfReturnDate.requestFocusInWindow();
			throw new CRException("Das Rückgabedatum bitte richtig eingeben! (Bsp." + today.toString() +")");
		} else {
			String[] parts = tfReturnDate.getText().toString().split("/");
			// check each part of the date containing the day, month and year
			try {
				// try converting each part to an integer
				int day = Integer.valueOf(parts[0]);
				int month = Integer.valueOf(parts[1]);
				int year = Integer.valueOf(parts[2]);

				if (year < 2020) {
					tfReturnDate.requestFocusInWindow();
					throw new CRException("Das Jahr des Rückgabedatums stimmt eher nicht");
				}
				
				if (day < today.getDayOfMonth() && month == today.getMonthValue() || day < today.getDayOfMonth() && month < today.getMonthValue()) {
					tfReturnDate.requestFocusInWindow();
					throw new CRException("Den Tag des Rückgabedatums stimmt eher nicht");
				}
				
				if (day < 0 || day > 31) {
					tfReturnDate.requestFocusInWindow();
					throw new CRException("Den Tag des Rückgabedatums bitte ueberprufen!");
				}

				if (month < 0 || month > 12) {
					tfReturnDate.requestFocusInWindow();
					throw new CRException("Den Monat des Rückgabedatums bitte ueberprufen!");
				}

				if (month == 2 && day > 29) {
					tfReturnDate.requestFocusInWindow();
					throw new CRException("Den Monat oder den Tag des Rückgabedatums bitte ueberprufen!");
				}

				StringBuilder sb = new StringBuilder();
				if (day < 10) {
					sb.append("0"+day+"/");
				} else {
					sb.append(day+"/");
				}
				
				if (month < 10) {
					sb.append("0"+month+"/");
				} else {	
					sb.append(month+"/");
				}
				
				sb.append(year);
				// check if the returning date is greater than the taking date
				String[] rentalDate = getRentalDate().split("/");
				String[] returnDate = sb.toString().split("/");
				
				LocalDate takingOn = LocalDate.of(Integer.valueOf(rentalDate[2]), 
						Integer.valueOf(rentalDate[1]), Integer.valueOf(rentalDate[0]));
				LocalDate returningOn = LocalDate.of(Integer.valueOf(returnDate[2]), 
						Integer.valueOf(returnDate[1]), Integer.valueOf(returnDate[0]));
				
				if (takingOn.isAfter(returningOn)) {
					throw new CRException("Das Rückgabe/Abholsdatum bitte richtig eingeben!");
				} else {
					return sb.toString();
				}
			} catch (NumberFormatException nfe) {
				tfReturnDate.requestFocusInWindow();
				throw new CRException("Das Rückgabedatum bitte richtig eingeben! (Bsp." + today.toString() +")");
			}
		}
	}
	
	public void setReturnDate(String date) {
		tfReturnDate.setText(date);
	}
	
	public float getCaution() throws CRException {
		if (tfCaution.getText().toString().equals("") || tfCaution.getText() == null || tfCaution.getText().contains(",")) {
			tfCaution.requestFocusInWindow();
			throw new CRException("Das Caution bitte richtig eingeben (Bsp. 225.75).");
		} else {
			try {
				float caution = Float.parseFloat(tfCaution.getText().toString());
				return caution;
			} catch (NumberFormatException e) {
				tfCaution.requestFocusInWindow();
				throw new CRException("Das Caution bitte richtig eingeben (Bsp. 225.75).");
			}
		}
	}
	
	public void setCaution(String caution) {
		tfCaution.setText(caution);
	}
	
	// Help to know the action to be done
		public String getAction() {
			return lblAction.getText().toString();
		}
		
		public void setAction(String value) {
			lblAction.setText(value);
		}

}
