/**
 * CarPanel - Panel that displaying the list of all registered cars to be manage
 * @author @author Danielle Monthe, Marie ... 
 */
package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import entity.Car;
import exception.CRException;
import repository.ICarRepository;
import service.CarService;


public class CarPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6439293898629918454L;
	private JButton addBtn;
	private JButton delBtn;
	private JButton editBtn;
	private JLabel lblInfo;
	private JTable table;
	private CarTableModel carTableModel;
	
	public CarPanel() throws CRException {
		setLayout(new GridBagLayout());
		// Buttons creation
		addBtn = new JButton("Hinzufügen");
		editBtn = new JButton("Editiern");
		delBtn = new JButton("Entfernen");
		// By default set Edit and delete button to disable
		editBtn.setEnabled(false);
		delBtn.setEnabled(false);
		// Add actionListener to the buttons
		addBtn.addActionListener(this);
		editBtn.addActionListener(this);
		delBtn.addActionListener(this);
		//
		ICarRepository customerService;
		customerService = new CarService();
		// Get all registered customers 
		List<Car> cars = (List<Car>) customerService.getAllCars();
		carTableModel = new CarTableModel(cars);
		table = new JTable(carTableModel);
		// Inserting a scroll bar to the view table 
		JScrollPane scrollPane = new JScrollPane(table);
		// Put a listener to the table to know if an item is selected
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// If an item is selected than set the two buttons to enable
				editBtn.setEnabled(true);
				delBtn.setEnabled(true);
				// clear the information label
				lblInfo.setText("");
			}
		});
		// Allowing the selection of only one item from the table
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Setting of components on this main window panel
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		// Adding the object TabbedPane
		add(scrollPane, gc);

		// creation of a horizontal box to put the buttons and the notification label
		Box hbox = Box.createHorizontalBox();
		lblInfo = new JLabel();
		lblInfo.setForeground(Color.GREEN);
		hbox.add(lblInfo);
		hbox.add(Box.createHorizontalStrut(300));
		hbox.add(addBtn);
		hbox.add(Box.createHorizontalStrut(10));
		hbox.add(editBtn);
		hbox.add(Box.createHorizontalStrut(10));
		hbox.add(delBtn);
		hbox.add(Box.createHorizontalStrut(3));
		// adding of the horizontal box containing the buttons to this panel
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.EAST;
		add(hbox, gc);
	}

	/*
	 * Overriding methods to handle action on click of the buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		AddCarView frame = new AddCarView(carTableModel);
		carTableModel = (CarTableModel) table.getModel();
		int selectedRow = table.getSelectedRow();
		if (e.getSource() == addBtn) {
			// If the Add button pressed, than display the add windows frame
			// If an object is been deleted before than clear the notification label 
			lblInfo.setText("");
			// open the new frame as modal
			frame.setVisible(true);
			frame.setAlwaysOnTop(true);
			frame.setModal(true);
		} else if (e.getSource() == editBtn) {
			// If the edit button pressed than display the add windows with the existing
			// information to be updated
			if (selectedRow > -1) {
				frame.setAction("Fahrzeug Informationen bearbeiten");
				frame.setId(Integer.valueOf(carTableModel.getValueAt(selectedRow, 0).toString()));
				frame.setPurchaseDate(carTableModel.getValueAt(selectedRow, 1).toString());
				frame.setIdentificationNb(carTableModel.getValueAt(selectedRow, 2).toString());
				frame.setColor(carTableModel.getValueAt(selectedRow, 3).toString());
				frame.setMarker(carTableModel.getValueAt(selectedRow, 4).toString());
				frame.setModel(carTableModel.getValueAt(selectedRow, 5).toString());
				frame.setCarType(carTableModel.getValueAt(selectedRow, 6).toString());
				frame.setPrice(carTableModel.getValueAt(selectedRow, 7).toString());
				frame.setVolume(carTableModel.getValueAt(selectedRow, 8).toString());
				frame.setSpeed(carTableModel.getValueAt(selectedRow, 9).toString());
				frame.setInsuranceNb(carTableModel.getValueAt(selectedRow, 10).toString());
				frame.setInspectionDate(carTableModel.getValueAt(selectedRow, 11).toString());
				// open the new frame as modal
				frame.setVisible(true);
				frame.setAlwaysOnTop(true);
				frame.setModal(true);
			}
		} else {
			// If delete button pressed than remove the selected item into the DB and the table also
			if (selectedRow > -1) {
				try {
					ICarRepository service = new CarService();
					// remove into the DB
					service.deleteCar(Integer.valueOf(carTableModel.getValueAt(selectedRow, 0).toString()));
					// remove into the displaying table
					carTableModel.removeRow(selectedRow);
					// Notify the user
					lblInfo.setText(new String("Das Fahrzeug wurde erfolgreich gelöscht!"));
					// disable buttons
					editBtn.setEnabled(false);
					delBtn.setEnabled(false);
				} catch (CRException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} 
	}

}
