import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HelpGUI {

	private JFrame frame;

	public void run() {
		try {
			HelpGUI window = new HelpGUI();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public HelpGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 463, 345);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Help Menu");

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 438, 235);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JTextPane txtpnalkjdf = new JTextPane();
		txtpnalkjdf.setEditable(false);
		txtpnalkjdf.setOpaque(false);
		txtpnalkjdf.setBounds(0, 0, 438, 235);
		txtpnalkjdf
				.setText("To use the search engine, input your search terms into the search box. "
						+ "Use the tabs at the top to search using either the exact terms, derivatives"
						+ " of the terms, or partials of the terms. \n\nUse the check box to select whether"
						+ " or not you want the search engine to recursively search through the text using"
						+ " replacement terms from our engineering-to-biology thesaurus.\n\nThe results will"
						+ " appear in the main box, and can be saved or cleared using the buttons to the right."
						+ " \n\nThank you for using our system! \n\n\u00A9 Bridge Systems 2013");
		panel.add(txtpnalkjdf);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 247, 438, 70);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnNewButton.setBounds(315, 22, 117, 29);
		panel_1.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 438, 70);
		panel_1.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("Logo_full copy.png"));
	}
}
