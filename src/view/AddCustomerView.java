/**
 * AddCustomerView - Help to display the panel to add a new customer or edit an existing customer
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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import entity.Customer;
import exception.CRException;
import repository.ICustomerRepository;
import service.CustomerService;

public class AddCustomerView extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8132796010571911567L;

	private JTextField tfFirstname;
	private JTextField tfLastname;
	private JTextField tfBirthday;
	private JTextField tfAdr;
	private JTextField tfCity;
	private JTextField tfCode;
	private JLabel lblAction;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JLabel lblNotification;
	// help to get the id on the last inserting object from the DB
	private int id;
	private CustomerTableModel model;

	public AddCustomerView(CustomerTableModel model) {
		this.model = model;
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
		lblAction = new JLabel("Fuege neuen Kunde ein");
		lblAction.setForeground(Color.WHITE);
		header.add(lblAction);
		// Set the initial grid values to 0,0
		constr.gridx = 0;
		constr.gridy = 0;
		// Displaying labels
		JLabel lblFirstname = new JLabel("Vorname:");
		JLabel lblLastname = new JLabel("Name:");
		JLabel lblGeburt = new JLabel("Geburtstdatum:");
		JLabel lblAdr = new JLabel("Adresse:");
		JLabel lblCity = new JLabel("Stadt:");
		JLabel lblCode = new JLabel("Postleitzahl:");

		// fields
		tfFirstname = new JTextField(20);
		tfLastname = new JTextField(20);
		tfBirthday = new JTextField(20);
		tfBirthday.setText("02/02/2002");
		tfBirthday.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tfBirthday.setText("");
			}

			public void mouseExited(MouseEvent me) {
				if (tfBirthday.getText().length() == 0)
					tfBirthday.setText("02/02/2002");
			}
		});
		tfAdr = new JTextField(20);
		tfAdr.setText("Brandenburgerstr. 1");
		tfAdr.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tfAdr.setText("");
			}

			public void mouseExited(MouseEvent me) {
				if (tfAdr.getText().length() == 0)
					tfAdr.setText("Brandenburgerstr. 1");
			}
		});
		tfCity = new JTextField(20);
		tfCode = new JTextField(20);

		panel.add(lblLastname, constr);
		constr.gridx = 1;
		panel.add(tfFirstname, constr);
		constr.gridx = 0;
		constr.gridy = 1;

		panel.add(lblFirstname, constr);
		constr.gridx = 1;
		panel.add(tfLastname, constr);
		constr.gridx = 0;
		constr.gridy = 2;

		panel.add(lblGeburt, constr);
		constr.gridx = 1;
		panel.add(tfBirthday, constr);
		constr.gridx = 0;
		constr.gridy = 3;

		panel.add(lblAdr, constr);
		constr.gridx = 1;
		panel.add(tfAdr, constr);
		constr.gridx = 0;
		constr.gridy = 4;

		panel.add(lblCity, constr);
		constr.gridx = 1;
		panel.add(tfCity, constr);
		constr.gridx = 0;
		constr.gridy = 5;

		panel.add(lblCode, constr);
		constr.gridx = 1;
		panel.add(tfCode, constr);
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
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/*
	 * Overriding methods to handle action on click of the buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelBtn) {
			// If the cancel button pressed close the current window
			AddCustomerView.this.dispose();
		} else {
			// The save button pressed
			ICustomerRepository service;
			try {
				service = new CustomerService();
				// reading input on text fields
				int id = getId();
				String fn = getFirstname();
				String ln = getLastname();
				String birth = getBirthday();
				String adr = getAddress();
				String city = getCity();
				int code = getCode();

				Customer customer = new Customer(id, ln, fn, birth, adr, city, code);
				/* Check what operation the user want to be executed
				* that the label showing the action on the window can be read, let getting what action to
				* be done. The choice are (Add or Edit)
				*/
				if (!getAction().contains("bearbeiten")) {
					// The user want to add a new car, than save the new car into the DB
					service.addCustomer(customer);
					//we also need the new adding car to be displaying on the list of all customers 
					model.addRow(customer);
					// Than notify the user that the operation done well.
					lblNotification.setForeground(Color.GREEN);
					lblNotification.setText("Den Kunde wurde Erfolgreich eingelegt!");
				} else {
					// Otherwise the user want to make and update on customer
					// Call the method for updating in the DB
					service.updateCustomer(customer);
					// Update also the affected row
					model.updateRow(customer);
					// Than notify the user that the operation done well.
					lblNotification.setForeground(Color.GREEN);
					lblNotification.setText("Aenderungen Erfolgreich abgespeichert!");
					// And we must set the label displaying the action to make to something else.
					setAction("Fuege neuen Kunde ein");
				}

				// If everything done well than set the fields to the default values
				tfFirstname.setText("");
				tfLastname.setText("");
				tfBirthday.setText("02/02/2002");
				tfAdr.setText("Brandenburgerstr. 1");
				tfCity.setText("");
				tfCode.setText("");

			} catch (CRException ex) {
				lblNotification.setForeground(Color.RED);
				lblNotification.setText(ex.getMessage());
			}
		}
	}

	// Methods to read each input on the fields
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() throws CRException {
		if (tfFirstname.getText() == null || tfFirstname.getText().length() == 0) {
			tfFirstname.requestFocusInWindow();
			throw new CRException("Der Name bitte ueberpruefen!");
		} else {
			return tfFirstname.getText().toUpperCase();
		}
	}

	public void setFirstname(String firstname) {
		tfFirstname.setText(firstname);
	}

	public String getLastname() throws CRException {
		if (tfLastname.getText() == null || tfLastname.getText().length() == 0) {
			tfLastname.requestFocusInWindow();
			throw new CRException("Der Vorname bitte ueberpruefen!");
		} else {
			return tfLastname.getText().toUpperCase();
		}
	}

	public void setLastname(String lastname) {
		tfLastname.setText(lastname);
	}

	public String getBirthday() throws CRException {
		if (tfBirthday.getText() == null || tfBirthday.getText().length() < 10 || tfBirthday.getText().length() > 10) {
			tfBirthday.requestFocusInWindow();
			throw new CRException("Geburtstdatum bitte Ueberprufen!");
		} else {
			String[] parts = tfBirthday.getText().toString().split("/");
			// check each part of the date containing the day, month and year
			try {
				// try converting each part to an integer
				int day = Integer.valueOf(parts[0]);
				int month = Integer.valueOf(parts[1]);
				int year = Integer.valueOf(parts[2]);

				if (year < 1960) {
					tfBirthday.requestFocusInWindow();
					throw new CRException("Den Kunde ist älter als 60.");
				}

				if (year > 2002) {
					tfBirthday.requestFocusInWindow();
					throw new CRException("Den Kunde ist jünger als 18.");
				}

				if (day < 0 || day > 31) {
					tfBirthday.requestFocusInWindow();
					throw new CRException("Den Tag des Geburtstdatums bitte ueberprufen!");
				}

				if (month < 0 || month > 12) {
					tfBirthday.requestFocusInWindow();
					throw new CRException("Den Monat des Geburtstdatums bitte ueberprufen!");
				}

				if (month == 2 && day > 29) {
					tfBirthday.requestFocusInWindow();
					throw new CRException("Den Monat oder den Tag des Geburtstdatums bitte ueberprufen!");
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
				tfBirthday.requestFocusInWindow();
				throw new CRException("Das Geburtstdatum bitte richtig eingeben (Bsp. 20/01/2000)");
			}
		}
	}

	public void setBirthday(String birth) {
		tfBirthday.setText(birth);
	}

	public String getAddress() throws CRException {
		if (tfAdr.getText() == null || tfAdr.getText().length() == 0) {
			tfAdr.requestFocusInWindow();
			throw new CRException("Adresse bitte Ueberprufen!");
		} else {
			return tfAdr.getText();
		}
	}

	public void setAddress(String adr) {
		tfAdr.setText(adr);
	}

	public String getCity() throws CRException {
		if (tfCity.getText() == null || tfCity.getText().length() == 0) {
			tfCity.requestFocusInWindow();
			throw new CRException("Wohnort bitte Ueberprufen!");
		} else {
			return tfCity.getText();
		}
	}

	public void setCity(String city) {
		tfCity.setText(city);
	}

	public int getCode() throws CRException {
		setCode(tfCode.getText().toString());
		if (tfCode.getText() == null || tfCode.getText().length() == 0) {
			tfCode.requestFocusInWindow();
			throw new CRException("Postleitzahl bitte Ueberprufen!");
		} else {
			try {
				int code = Integer.parseInt(String.valueOf(tfCode.getText().toString()));
				return code;
			} catch (NumberFormatException nfe) {
				tfCode.requestFocusInWindow();
				throw new CRException("Postleitzahl bitte Ueberprufen!");
			}
		}
	}

	public void setCode(String code) {
		tfCode.setText(code);
	}

	// Help to know the action to be done
	public String getAction() {
		return lblAction.getText().toString();
	}

	public void setAction(String value) {
		lblAction.setText(value);
	}
}
