/**
 * OrdersPanel - Panel that displaying the list of all registered orders
 * @author @author Danielle Monthe, Marie ... 
 */
package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

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

public class OrdersPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gc;
	private JButton addBtn;
	private JButton delBtn;
	private JButton editBtn;
	private JLabel lblInfo;
	private JTextArea area;
	private JTable table;
	private OrdersTableModel ordersTableModel;

	public OrdersPanel() throws CRException {
		setLayout(new GridBagLayout());
		area = new JTextArea();
		area.setText(null);
		area.setMargin(new Insets(10, 10, 10, 10));
		area.setEditable(false);
		area.setText("Waehlen Sie eine Bestellung aus um mehr datails zu sehen");
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
		IOrdersRepository ordersService;
		ordersService = new OrdersService();
		// get all orders
		List<Orders> ordersList = ordersService.getAllOrders();
		ordersTableModel = new OrdersTableModel(ordersList);
		table = new JTable(ordersTableModel);
		// Allowing the selection of only one item from the table
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Put a listener to the table to know if an item is selected
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// If an item is selected than set the two buttons to enable
				editBtn.setEnabled(true);
				delBtn.setEnabled(true);
				// clear the information label
				lblInfo.setText("");
				// Get the selected row information for display to the textArea
				int selectedRow = table.getSelectedRow();
				//
				if (selectedRow != -1) {
					// We retrieve the information of the selected row
					int id = Integer.valueOf(ordersTableModel.getValueAt(selectedRow, 0).toString());
					int carId = Integer.valueOf(ordersTableModel.getValueAt(selectedRow, 1).toString());
					int customerId = Integer.valueOf(ordersTableModel.getValueAt(selectedRow, 2).toString());
					String rentalDate = ordersTableModel.getValueAt(selectedRow, 3).toString();
					String returnDate = ordersTableModel.getValueAt(selectedRow, 4).toString();
					String caution = ordersTableModel.getValueAt(selectedRow, 5).toString();
					String price = ordersTableModel.getValueAt(selectedRow, 6).toString();
					// retrieve the car and the customer with there are id
					try {
						ICustomerRepository customerService = new CustomerService();
						ICarRepository carService = new CarService();
						Car car = carService.getCar(carId);
						Customer customer = customerService.getCustomer(customerId);
						// Build the object Orders
						Orders orders = new Orders(id, car, customer);
						orders.setRentalDate(rentalDate);
						orders.setReturnDate(returnDate);
						orders.setCaution(Float.valueOf(caution));
						orders.setPrice(Float.valueOf(price));

						// set the output to the text area
						// We check if a registered car or Customer is deleted
						if (orders.getCar().getCarId() == 0 || orders.getCustomer().getId()== 0) {
							area.setText("Entfernen Sie diese, da den Kunde oder das Fahrzeug wurde weggelöscht");
						} else {
							area.setText(orders.toString());
						}
					} catch (CRException e) {
						//
					}
				}
			}
		});

		// Inserting a scroll bar to the view table
		JScrollPane scrollPane = new JScrollPane(table);
		// Setting of components on this main window panel
		gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		// Adding the object TabbedPane
		add(scrollPane, gc);
		// Adding the text area to display the selected orders informations
		add(area);

		// creation of a horizontal box to put the buttons and the notification label
		Box hbox = Box.createHorizontalBox();
		lblInfo = new JLabel();
		lblInfo.setForeground(Color.GREEN);
		hbox.add(lblInfo);
		hbox.add(Box.createHorizontalStrut(40));
		hbox.add(addBtn);
		hbox.add(Box.createHorizontalStrut(10));
		hbox.add(editBtn);
		hbox.add(Box.createHorizontalStrut(10));
		hbox.add(delBtn);

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
		area.setText("Waehlen Sie eine Bestellung aus um mehr datails zu sehen");
		try {
			AddOrdersView frame = new AddOrdersView(ordersTableModel);
			ordersTableModel = (OrdersTableModel) table.getModel();
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
					frame.setAction("Die Abbuchung bearbeiten");
					frame.setID(Integer.valueOf(ordersTableModel.getValueAt(selectedRow, 0).toString()));
					frame.setCarId(Integer.valueOf(ordersTableModel.getValueAt(selectedRow, 1).toString()));
					frame.setCustomerId(Integer.valueOf(ordersTableModel.getValueAt(selectedRow, 2).toString()));
					frame.setRentalDate(ordersTableModel.getValueAt(selectedRow, 3).toString());
					frame.setReturnDate(ordersTableModel.getValueAt(selectedRow, 4).toString());
					frame.setCaution(ordersTableModel.getValueAt(selectedRow, 5).toString());
					// open the new frame as modal
					frame.setVisible(true);
					frame.setAlwaysOnTop(true);
					frame.setModal(true);
				}
			} else {
				// If the delete button pressed than remove the selected item into the DB and the
				// table also
				if (selectedRow > -1) {
					try {
						IOrdersRepository ordersService = new OrdersService();
						// remove into the DB
						ordersService.deleteOrders(Integer.valueOf(ordersTableModel.getValueAt(selectedRow, 0).toString()));
						// remove into the displaying table
						ordersTableModel.removeRow(selectedRow);
						// Notify the user
						lblInfo.setText(new String("Die Abbuchung wurde erfolgreich gelöscht!"));
						// disable buttons
						editBtn.setEnabled(false);
						delBtn.setEnabled(false);
					
					} catch (CRException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} catch (CRException e1) {
			lblInfo.setText(e1.getMessage());
		}

	}

}
