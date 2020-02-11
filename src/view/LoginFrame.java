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
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import exception.CRException;

public class LoginFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton okBtn;
	private JButton cancelBtn;
	private JTextField tfUser;
	private JPasswordField tfPwd;
	private JLabel lblError;
	private final static String USERNAME = "admin";
	private final static char[] PWD = {'G', 'e', 't', 'm', 'e', 'i', 'n', '2', '0', '2', '0'};
	

	public LoginFrame() {
		super("Login");
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
		JLabel lblTitle = new JLabel("Anmelden zur Verwltung");
		lblTitle.setForeground(Color.WHITE);
		header.add(lblTitle);
		// Set the initial grid values to 0,0
		constr.gridx = 0;
		constr.gridy = 0;
		
		// Fields
		tfUser = new JTextField(20);
		tfUser.setText("Benutzername");
		tfUser.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tfUser.setText("");
			}
			
			public void mouseExited(MouseEvent me) {
				if (tfUser.getText().contentEquals(""))
					tfUser.setText("Benutzername");
			}
		});
		
		tfPwd = new JPasswordField(20);
		tfPwd.setText("Password");
		tfPwd.setEchoChar('*');
		tfPwd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tfPwd.setText("");
			}
			public void mouseExited(MouseEvent me) {
				if (tfPwd.getPassword().length == 0) {
					tfPwd.setText("Password");
					
				}	
			}
		});
		
		// Adding labels and fields to the panel
		panel.add(tfUser, constr);
		constr.gridx = 0;
		constr.gridy = 1;
		
		panel.add(tfPwd, constr);
		constr.gridy = 2;
				
		constr.anchor = GridBagConstraints.CENTER;
		// Button with text "Anmelden"
		okBtn = new JButton("Anmelden");
		okBtn.addActionListener(this);
		// Button with text "Abbrechen"
		cancelBtn = new JButton("Abbrechen");
		cancelBtn.addActionListener(this);

		// Add label and button to panel
		Box hbox = Box.createHorizontalBox();
		hbox.add(okBtn);
		hbox.add(Box.createHorizontalStrut(5));
		hbox.add(cancelBtn);
		panel.add(hbox, constr);
		constr.gridy = 3;

		// add panel for error
		JPanel panelError = new JPanel();
		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		panelError.add(lblError);
		
		
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
		setUndecorated(true);
		pack();
		setSize(320, 400);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtn) {
			if (tfUser.getText().contentEquals("") || tfUser.getText()==null) {
				lblError.setText("Geben Sie bitte Ihren Benutzername");
			} 
			else if (!tfUser.getText().toString().contentEquals(USERNAME)) {
				lblError.setText("Den Benutzername stimmt nicht");
			}
			else if (!Arrays.equals(tfPwd.getPassword(), PWD)) {
				lblError.setText("Das Password stimmt nicht");
			}
			else {
				try {
					RentalWindow frame = new RentalWindow();
					frame = new RentalWindow();
					frame.setVisible(true);
					// close the login window
					LoginFrame.this.dispose();
				} catch (CRException e1) {
					
				}
			}
		} else {
			LoginFrame.this.dispose();
		}
	}
}
