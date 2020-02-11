/**
 * CustomerPanel - Panel that displaying the list of all registered customers to be manage
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

import entity.Customer;
import exception.CRException;
import repository.ICustomerRepository;
import service.CustomerService;

public class CustomerPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4324546681760750953L;
	private JButton addBtn;
	private JButton delBtn;
	private JButton editBtn;
	private JLabel lblInfo;
	private JTable table;
	private CustomerTableModel customertable;

	public CustomerPanel() throws CRException {
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
		ICustomerRepository customerManagement;
		customerManagement = new CustomerService();
		// Get all registered customers
		List<Customer> customers = (List<Customer>) customerManagement.getAllCustomers();
		customertable = new CustomerTableModel(customers);
		table = new JTable(customertable);
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
		hbox.add(lblInfo);
		hbox.add(Box.createHorizontalStrut(400));
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
		AddCustomerView frame = new AddCustomerView(customertable);
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
			int selectedRow = table.getSelectedRow();
			CustomerTableModel tableModel = (CustomerTableModel) table.getModel();
			if (selectedRow > -1) {
				// Show the addView with the retrieving informations
				frame.setAction("Kunde Informationen bearbeiten");
				frame.setId(Integer.valueOf(tableModel.getValueAt(selectedRow, 0).toString()));
				frame.setFirstname(tableModel.getValueAt(selectedRow, 1).toString());
				frame.setLastname(tableModel.getValueAt(selectedRow, 2).toString());
				frame.setBirthday(tableModel.getValueAt(selectedRow, 3).toString());
				frame.setAddress(tableModel.getValueAt(selectedRow, 4).toString());
				frame.setCity(tableModel.getValueAt(selectedRow, 5).toString());
				frame.setCode(tableModel.getValueAt(selectedRow, 6).toString());
				// open the new frame as modal
				frame.setVisible(true);
				frame.setAlwaysOnTop(true);
				frame.setModal(true);
			}
		} else {
			// If delete button pressed than remove the selected item into the DB and the
			// table also
			int selectedRow = table.getSelectedRow();
			CustomerTableModel model = (CustomerTableModel) table.getModel();
			if (selectedRow > -1) {
				try {
					CustomerService service = new CustomerService();
					// remove into the DB
					service.deleteCustomer(Integer.valueOf(model.getValueAt(selectedRow, 0).toString()));
					// remove into the displaying table
					model.removeRow(selectedRow);
					// Notify the user
					lblInfo.setText(new String("Der Kunde wurde erfolgreich gelöscht!"));
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
