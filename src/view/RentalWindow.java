/**
 * RentalWindow - Main window for the application displaying on three different tabs
 * respectively the panel of Cars, Customers and Orders to be manage.
 * @author Danielle Monthe, Marie ...
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import exception.CRException;

public class RentalWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1448749536229884183L;
	private CustomerPanel customerpanel;
	private CarPanel carPanel;
	private OrdersPanel ordersPanel;
	
	public static void main(String[] args) throws CRException {
		RentalWindow frame = new RentalWindow();
		frame.setVisible(true);
	}

	public RentalWindow() throws CRException {
		// Building of the window
		super("RENT CAR SERVICE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1050, 550);
		setLocationRelativeTo(null);
		Container contentPane = getContentPane();
		setContentPane(contentPane);
		// Panel settings
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;

		// Main panel of this window to place all components
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(0, 153, 204));
		panel.setBorder(BorderFactory.createTitledBorder(new String("Verwaltung")));
	
		// Create an object tabbedPane to add the three different panel 
		JTabbedPane tabpane = new JTabbedPane();
		try {
			// Construction of each panel
			carPanel  = new CarPanel();
			customerpanel = new CustomerPanel();
			ordersPanel = new OrdersPanel();
			
			// adding the panels to tabbedPane to be displaying to the user
			tabpane.add("Fahrzeuge", carPanel);
			tabpane.addTab("Kunden", customerpanel);
			tabpane.add("Bestellungen", ordersPanel);
		} catch (Exception e) {
			throw new CRException(e.getMessage());
		}

		// Add the main panel to this window and 
		panel.add(tabpane);
		getContentPane().add(panel, gc);
	}
}
