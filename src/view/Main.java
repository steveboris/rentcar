/**
 * Main - Main class of the application
 * @author Danielle Monthe, Marie ... 
 */
package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2583920042763823564L;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
