/**
 * File not found GUI
 * 
 * @author Brett Long
 */
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FnfeGUI {

	private JFrame frame;

	/**
	 * Launch the window.
	 */
	public void run() {
		try {
			FnfeGUI window = new FnfeGUI();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the window.
	 */
	public FnfeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 170);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FnfeGUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/Error.gif")));
		lblNewLabel.setBounds(23, 36, 38, 39);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("The file you have selected to use could not be found :(");
		lblNewLabel_1.setBounds(73, 47, 352, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnOk.setBounds(163, 91, 117, 29);
		frame.getContentPane().add(btnOk);
	}
}
