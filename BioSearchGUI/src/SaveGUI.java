import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SaveGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextArea text;

	/**
	 * Launch the application.
	 */
	public void run(JTextArea text) {
		try {
			SaveGUI window = new SaveGUI(text);
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public SaveGUI(JTextArea text) {
		this.text = text;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 450, 151);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Save File");

		textField = new JTextField();
		textField.setBounds(74, 26, 350, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblFileName = new JLabel("File Name:");
		lblFileName.setBounds(6, 32, 83, 16);
		frame.getContentPane().add(lblFileName);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					saveFile(textField);
					frame.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(307, 66, 117, 29);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(187, 66, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
	}

	/**
	 * This method is called when the save button is pressed Should write a new
	 * file
	 * 
	 * @throws IOException
	 */
	public void saveFile(JTextField textField) throws IOException {
		FileIO file;
		if (textField.getText().isEmpty()) {
			file = new FileIO("saved.txt");
		} else {
			file = new FileIO(textField.getText() + ".txt");
		}
		boolean canWrite = file.openWriter();
		ArrayList<String> saved = new ArrayList<String>();
		saved.add(text.getText());

		if (canWrite) {
			for (int i = 0; i < saved.size(); i++) {
				file.write(saved.get(i));
			}
			file.closeWriter();
		}
	}

}
