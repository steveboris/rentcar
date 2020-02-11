/**
 * AddCarView - Help to display the panel to add a new car or edit an existing car
 * @author Danielle Monthe, Marie ... 
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
import exception.CRException;
import repository.ICarRepository;
import service.CarService;

public class AddCarView extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144440229039553635L;

	private JTextField tfPurchaseDate;
	private JTextField tfIdentificationNb;
	private JTextField tfMarker;
	private JTextField tfModel;
	private JComboBox<String> carColor;
	private JComboBox<String> carType;
	private JTextField tfPrice;
	private JTextField tfVolume;
	private JTextField tfSpeed;
	private JTextField tfInsuranceNb;
	private JTextField tfInspectionDate;

	private JLabel lblAction;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JLabel lblNotification;
	// help to get the id on the last inserting object from the DB
	private int id;
	private CarTableModel tablemodel;
	
	// Choice for models
	private ComboBoxModel<String> carColorsModel = new DefaultComboBoxModel<>(new String[] { "Schwartz", "Weiﬂ",
			"Rot", "Braun", "Blau", "Grau", "Silber"});
	private ComboBoxModel<String> carTypModel = new DefaultComboBoxModel<>(new String[] { "Mini/Kleinwagen", "Kompaktwagen",
			"kombi", "Cabrio", "SUV/Gelaendewagen", "Vans/Minivans", "Nutzfahrzeug" });
	

	public AddCarView(CarTableModel tablemodel) {
		this.tablemodel = tablemodel;
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
		lblAction = new JLabel("Fuege ein neues Auto hinzu");
		lblAction.setForeground(Color.WHITE);
		header.add(lblAction);
		// Set the initial grid values to 0,0
		constr.gridx = 0;
		constr.gridy = 0;
		
		// Displaying labels
		JLabel lblPurchaseDate = new JLabel("Kaufsdatum:");
		JLabel lblIdentification = new JLabel("Kenzeichnen:");
		JLabel lblCarType = new JLabel("Typ:");
		JLabel lblMarker = new JLabel("Hersteller:");
		JLabel lblModel = new JLabel("Model:");
		JLabel lblColor = new JLabel("Farber:");
		JLabel lblPrice = new JLabel("Preis (Mietkosten pro Tag):");
		JLabel lblVolume = new JLabel("Tankvolume (L):");
		JLabel lblSpeed = new JLabel("Geschwindigkeit (KmH):");
		JLabel lblInsurance = new JLabel("Versicherungsnummer:");
		JLabel lblInspectionDate = new JLabel("TUEV (Ablaufsdatum):");
		
		// Fields
		tfPurchaseDate = new JTextField(20);
		tfPurchaseDate.setText("20/01/2020");
		tfPurchaseDate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tfPurchaseDate.setText("");
			}
			public void mouseExited(MouseEvent me) {
				if (tfPurchaseDate.getText().length() == 0)
					tfPurchaseDate.setText("20/01/2020");
			}
		});
		tfIdentificationNb = new JTextField(20);
		carColor= new JComboBox<String>(carColorsModel);
		carColor.setSelectedItem(null);
		carColor.setEditable(true);
		tfMarker = new JTextField(20);
		tfModel = new JTextField(20);
		carType = new JComboBox<String>(carTypModel);
		tfPrice = new JTextField(20);
		tfVolume = new JTextField(20);
		tfSpeed = new JTextField(20);
		tfInsuranceNb = new JTextField(20);
		tfInspectionDate = new JTextField(20);
		tfInspectionDate.setText("20/01/2022");
		tfInspectionDate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tfInspectionDate.setText("");
			}
			public void mouseExited(MouseEvent me) {
				if (tfInspectionDate.getText().length() == 0)
					tfInspectionDate.setText("20/01/2022");
			}
		});
		
		// Adding labels and fields to the panel
		panel.add(lblPurchaseDate, constr);
		constr.gridx = 1;
		panel.add(tfPurchaseDate, constr);
		constr.gridx = 0;
		constr.gridy = 1;
		
		panel.add(lblIdentification, constr);
		constr.gridx = 1;
		panel.add(tfIdentificationNb, constr);
		constr.gridx = 0;
		constr.gridy = 2;
		
		panel.add(lblColor, constr);
		constr.gridx = 1;
		panel.add(carColor, constr);
		constr.gridx = 0;
		constr.gridy = 3;

		panel.add(lblMarker, constr);
		constr.gridx = 1;
		panel.add(tfMarker, constr);
		constr.gridx = 0;
		constr.gridy = 4;

		panel.add(lblModel, constr);
		constr.gridx = 1;
		panel.add(tfModel, constr);
		constr.gridx = 0;
		constr.gridy = 5;

		panel.add(lblCarType, constr);
		constr.gridx = 1;
		panel.add(carType, constr);
		constr.gridx = 0;
		constr.gridy = 6;

		panel.add(lblPrice, constr);
		constr.gridx = 1;
		panel.add(tfPrice, constr);
		constr.gridx = 0;
		constr.gridy = 7;
		
		panel.add(lblVolume, constr);
		constr.gridx = 1;
		panel.add(tfVolume, constr);
		constr.gridx = 0;
		constr.gridy = 8;
		
		panel.add(lblSpeed, constr);
		constr.gridx = 1;
		panel.add(tfSpeed, constr);
		constr.gridx = 0;
		constr.gridy = 9;
		
		panel.add(lblInsurance, constr);
		constr.gridx = 1;
		panel.add(tfInsuranceNb, constr);
		constr.gridx = 0;
		constr.gridy = 10;
		
		panel.add(lblInspectionDate, constr);
		constr.gridx = 1;
		panel.add(tfInspectionDate, constr);
		constr.gridy = 11;
		
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
		constr.gridy = 12;

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
		setTitle("Car Verwaltung");
		setUndecorated(true);
		pack();
		setSize(400, 440);
		setLocationRelativeTo(null);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == cancelBtn) {
			// if the cancel button pressed we close this view
			AddCarView.this.dispose();
		} else {
			// if the add button pressed 
			ICarRepository carService;
			try {
				carService = new CarService();
				// reading input on text fields
				int id = getId();
				String pd = getPurchaseDate();
				String idn = getIdentificationNb();
				String color = getColor();
				String marker = getMarker();
				String model = getModel();
				String type = getCarType();
				float price = getPrice();
				int v = getVolume();
				int speed = getSpeed();
				String insurance = getInsuranceNb();
				String inspection = getInspectionDate();
				
				// We create a new object Car.
				Car car = new Car(id, pd, idn, color, marker, model, type, price, v, speed, insurance, inspection);
				
				/* Check what operation the user want to be executed 
				* that the label showing the action on the window can be read, let getting what action to
				* be done. The choice are (Add or Edit)
				*/
				if (!getAction().contains("bearbeiten")) { 
					// The user want to add a new car, than save the new car into the DB
					carService.addCar(car);
					//we also need the new added car to be displaying on the list of all cars 
					tablemodel.addRow(car);
					// Than notify the user that the operation done well.
					lblNotification.setForeground(Color.GREEN);
					lblNotification.setText("Das Auto wurde Erfolgreich eingelegt!");
				} else {
					// Otherwise the user want to make an update on car
					// Call the method for updating in the DB
					carService.updateCar(car);
					// we also need to update the row on the table
					tablemodel.updateRow(car);
					// Than notify the user that the operation done well.
					lblNotification.setForeground(Color.GREEN);
					lblNotification.setText("Aenderungen Erfolgreich abgespeichert!");
					// And we must set the label displaying the action to make to something else.
					setAction("Fuege ein neues Auto hinzu");
				}
				// If everything done well than set the fields to the default values
				tfPurchaseDate.setText("20/01/2020");
				tfIdentificationNb.setText("");
				carColor.setSelectedItem("");
				tfMarker.setText("");
				tfModel.setText("");
				carType.setSelectedIndex(0);
				tfPrice.setText("");
				tfVolume.setText("");
				tfSpeed.setText("");
				tfInsuranceNb.setText("");
				tfInspectionDate.setText("20/01/2022");
			} catch (CRException cre) {
				// if error than displaying to the user
				lblNotification.setForeground(Color.RED);
				lblNotification.setText(cre.getMessage());
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPurchaseDate() throws CRException {
		if (tfPurchaseDate.getText().toString().equals("") || tfPurchaseDate.getText() == null
				|| tfPurchaseDate.getText().length() < 10 || tfPurchaseDate.getText().length() > 10) {
			tfPurchaseDate.requestFocusInWindow();
			throw new CRException("Das Kaufsdatum bitte richtig eingeben! (Bsp. 20/01/2020)");
		} else {
			String[] parts = tfPurchaseDate.getText().toString().split("/");
			// check each part of the date containing the day, month and year
			try {
				// try converting each part to an integer
				int day = Integer.valueOf(parts[0]);
				int month = Integer.valueOf(parts[1]);
				int year = Integer.valueOf(parts[2]);

				if (year < 2000 || year > 2020) {
					tfPurchaseDate.requestFocusInWindow();
					throw new CRException("Das Jahr des Kaufsdatums ist von (2000 bis 2020) ausgeschlossen");
				}

				if (day < 0 || day > 31) {
					tfPurchaseDate.requestFocusInWindow();
					throw new CRException("Den Tag des Kaufsdatums bitte ueberprufen!");
				}

				if (month < 0 || month > 12) {
					tfPurchaseDate.requestFocusInWindow();
					throw new CRException("Den Monat des Kaufsdatums bitte ueberprufen!");
				}

				if (month == 2 && day > 29) {
					tfPurchaseDate.requestFocusInWindow();
					throw new CRException("Den Monat oder den Tag des Kaufsdatums bitte ueberprufen!");
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
				tfPurchaseDate.requestFocusInWindow();
				throw new CRException("Das Kaufsdatum bitte richtig eingeben (Bsp. 20/01/2020)");
			}
		}
	}
	
	public void setPurchaseDate(String date) {
		tfPurchaseDate.setText(date);
	}

	public String getIdentificationNb() throws CRException {
		if (tfIdentificationNb.getText().toString().equals("") || tfIdentificationNb.getText() == null) {
			tfIdentificationNb.requestFocusInWindow();
			throw new CRException("Das Kennzeichnen bitte eingeben");
		} else {
			return tfIdentificationNb.getText().toUpperCase();
		}
	}
	
	public void setIdentificationNb(String idn) {
		tfIdentificationNb.setText(idn);
	}

	public String getMarker() throws CRException {
		if (tfMarker.getText().toString().equals("") || tfMarker.getText() == null) {
			tfMarker.requestFocusInWindow();
			throw new CRException("Den Hersteller bitte eingeben");
		} else {
			return tfMarker.getText().toUpperCase();
		}
	}
	
	public void setMarker(String maker) {
		tfMarker.setText(maker);
	}

	public String getModel() throws CRException {
		if (tfModel.getText().toString().equals("") || tfModel.getText() == null) {
			tfModel.requestFocusInWindow();
			throw new CRException("Das Model bitte eingeben");
		} else {
			return tfModel.getText().toUpperCase();
		}
	}
	
	public void setModel(String model) {
		tfModel.setText(model);
	}

	public String getColor() throws CRException {
		if ((String)carColor.getSelectedItem() == null) {
			carColor.requestFocusInWindow();
			throw new CRException("Die Farbe bitte eingeben");
		} else {
			return (String)carColor.getSelectedItem().toString().toUpperCase();
		}
	}
	
	public void setColor(String color) {
		carColor.setSelectedItem(color); 
	}

	public String getCarType() {
		return (String)carTypModel.getSelectedItem().toString().toUpperCase();
	}
	
	public void setCarType(String type) {
		carType.setSelectedItem(type);
	}

	public float getPrice() throws CRException {
		if (tfPrice.getText().toString().equals("") || tfPrice.getText() == null || tfPrice.getText().contains(",")) {
			tfPrice.requestFocusInWindow();
			throw new CRException("Den Preis bitte richtig eingeben (Bsp. 210.1)");
		} else {
			try {
				float price = Float.parseFloat(tfPrice.getText().toString());
				return price;
			} catch (NumberFormatException e) {
				tfPrice.requestFocusInWindow();
				throw new CRException("Den Preis bitte richtig eingeben (Bsp. 210.1)");
			}
		}
	}
	
	public void setPrice(String price) {
		tfPrice.setText(price);
	}

	public int getVolume() throws CRException {
		if (tfVolume.getText().toString().equals("") || tfVolume.getText() == null) {
			tfVolume.requestFocusInWindow();
			throw new CRException("Das Tankvolume bitte eingeben");
		} else {
			try {
				return Integer.parseInt(tfVolume.getText().toString());
			} catch (NumberFormatException nfe) {
				tfVolume.requestFocusInWindow();
				throw new CRException("Das Tankvolume bitte richtig eingeben");
			}
		}
	}
	
	public void setVolume(String volume) {
		tfVolume.setText(volume);
	}

	public int getSpeed() throws CRException {
		if (tfSpeed.getText().toString().equals("") || tfSpeed.getText() == null) {
			tfSpeed.requestFocusInWindow();
			throw new CRException("Die Geschwindigkeit bitte eingeben");
		} else {
			try {
				int value = Integer.parseInt(tfSpeed.getText().toString());
				return value;
			} catch (NumberFormatException nfe) {
				tfSpeed.requestFocusInWindow();
				throw new CRException("Die Geschwindigkeit bitte richtig eingeben");
			}
		}
	}
	
	public void setSpeed(String speed) {
		tfSpeed.setText(speed);
	}

	public String getInsuranceNb() throws CRException {
		if (tfInsuranceNb.getText().toString().equals("") || tfInsuranceNb.getText() == null) {
			tfInsuranceNb.requestFocusInWindow();
			throw new CRException("Das Auto Versicherungnummer bitte eingeben");
		} else {
			return tfInsuranceNb.getText().toString().toUpperCase();
		}
	}
	
	public void setInsuranceNb(String nb) {
		tfInsuranceNb.setText(nb);
	}

	public String getInspectionDate() throws CRException {
		if (tfInspectionDate.getText().toString().equals("") || tfInspectionDate.getText() == null
				|| tfInspectionDate.getText().length() < 10 || tfInspectionDate.getText().length() > 10) {
			tfInspectionDate.requestFocusInWindow();
			throw new CRException("Das Tuev Ablaufsdatum bitte richtig eingeben! (Bsp. 20/01/2022)");
		} else {
			String[] parts = tfInspectionDate.getText().toString().split("/");
			try {
				// try converting each part to an integer
				int day = Integer.valueOf(parts[0]);
				int month = Integer.valueOf(parts[1]);
				int year = Integer.valueOf(parts[2]);

				if (year < 2020) {
					tfInspectionDate.requestFocusInWindow();
					throw new CRException("Das Jahr des Tuev bitte ueberprufen");
				}

				if (day < 0 || day > 31) {
					tfInspectionDate.requestFocusInWindow();
					throw new CRException("Den Tag des Tuev bitte ueberprufen!");
				}

				if (month < 0 || month > 12) {
					tfInspectionDate.requestFocusInWindow();
					throw new CRException("Den Monat des Tuev bitte ueberprufen!");
				}

				if (month == 2 && day > 29) {
					tfInspectionDate.requestFocusInWindow();
					throw new CRException("Den Monat oder den Tag des Tuev bitte ueberprufen!");
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
				tfInspectionDate.requestFocusInWindow();
				throw new CRException("Das Tuev Ablaufsdatum bitte richtig eingeben (Bsp. 20/01/2022)");
			}
		}
	}
	
	public void setInspectionDate(String date) {
		tfInspectionDate.setText(date);
	}
	
	// Help to know the action to be done
	public String getAction() {
		return lblAction.getText().toString();
	}
	
	public void setAction(String value) {
		lblAction.setText(value);
	}
}
