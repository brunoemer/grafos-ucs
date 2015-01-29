package trabalho;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class Init {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Interface error: "+e.getMessage());
				}
			}
		});
	}

}
