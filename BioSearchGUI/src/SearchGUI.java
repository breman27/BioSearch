import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * This class creates the main GUI program that pops up during start up
 * Throughout the run this GUI is updated with new text to the textArea or the
 * text is erased, depending on the buttons pushed.
 * 
 * @author Brett Long
 * @version 2.0.0 / Apr. 2013
 */
public class SearchGUI {

	private ArrayList<String> animalDoc, heredityDoc, plantsDoc, cellDoc;
	private String aText, pText, hText, cText, newSearch;
	private Highlighter hilight;
	private JFrame frame;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JCheckBox checkBox, checkBox2;
	private JTextField txtSearchTerms, textField, textField_1;
	
	/**
	 * Launch the application.
	 */

	public void run(ArrayList<String> animalDoc, ArrayList<String> heredityDoc,
			ArrayList<String> plantsDoc, ArrayList<String> cellDoc) {
		try {
			SearchGUI window = new SearchGUI(animalDoc, heredityDoc, plantsDoc,
					cellDoc);
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public SearchGUI(ArrayList<String> animalDoc,
			ArrayList<String> heredityDoc, ArrayList<String> plantsDoc,
			ArrayList<String> cellDoc) {
		this.animalDoc = animalDoc;
		this.heredityDoc = heredityDoc;
		this.plantsDoc = plantsDoc;
		this.cellDoc = cellDoc;
		// Duplicates the "-" character
		String rep = new String(new char[50]).replace("\0", "-");

		aText = rep + "Animal Text" + rep + "\n";
		hText = rep + "Heredity Text" + rep + "\n";
		pText = rep + "Plant Text" + rep + "\n";
		cText = rep + "Cell Text" + rep + "\n";
		newSearch = rep + rep + rep + "\n";

		hilight = new DefaultHighlighter();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Creates the main frame and names it
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 505);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		tabbedPane.setBounds(0, 6, 794, 89);
		frame.getContentPane().add(tabbedPane);
		frame.setTitle("Bio-Mimcry Search");
		frame.setResizable(false);

		// Enables scrolling
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 120, 647, 347);
		frame.getContentPane().add(scrollPane);

		// This creates the text area where search results are shown
		final JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setBackground(Color.WHITE);

		// Option to search related engineering and biology terms
		checkBox = new JCheckBox("Use Engineering to Biology Thesaurus?");
		checkBox.setSelected(true);
		checkBox.setBounds(16, 92, 270, 23);
		frame.getContentPane().add(checkBox);

		// First panel used for "exact terms"
		JPanel panel = new JPanel();
		panel.setBackground(new Color(211, 211, 211));
		panel.setBorder(null);

		tabbedPane.addTab("Exact Terms", null, panel, null);
		panel.setLayout(null);

		// Sets the search field
		txtSearchTerms = new JTextField();
		txtSearchTerms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Searches through all of the documents and writes the results
				Search word = new Search(newSearch);
				ArrayList<String> animalText = new ArrayList<String>();
				ArrayList<String> heredityText = new ArrayList<String>();
				ArrayList<String> plantText = new ArrayList<String>();
				ArrayList<String> cellText = new ArrayList<String>();
				animalText = word.exactSearch(txtSearchTerms.getText()
						.toLowerCase(), animalDoc, checkBox.isSelected());
				heredityText = word.exactSearch(txtSearchTerms.getText()
						.toLowerCase(), heredityDoc, checkBox.isSelected());
				plantText = word.exactSearch(txtSearchTerms.getText()
						.toLowerCase(), plantsDoc, checkBox.isSelected());
				cellText = word.exactSearch(txtSearchTerms.getText()
						.toLowerCase(), cellDoc, checkBox.isSelected());
				textArea.append(aText);
				write(textArea, animalText, txtSearchTerms.getText());
				textArea.append(hText);
				write(textArea, heredityText, txtSearchTerms.getText());
				textArea.append(pText);
				write(textArea, plantText, txtSearchTerms.getText());
				textArea.append(cText);
				write(textArea, cellText, txtSearchTerms.getText());
				write(textArea, new ArrayList<String>(),
						txtSearchTerms.getText());
				textArea.append(newSearch);
			}
		});
		txtSearchTerms.setText("Search Terms...");
		txtSearchTerms.setBounds(6, 6, 626, 28);
		panel.add(txtSearchTerms);
		txtSearchTerms.setColumns(10);

		// Creates the Search button
		JButton btnSearch_1 = new JButton("Search");
		// If the search button is pushed, then take this action
		btnSearch_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Searches through all of the documents and writes the results
				Search word = new Search(newSearch);
				ArrayList<String> animalText = new ArrayList<String>();
				ArrayList<String> heredityText = new ArrayList<String>();
				ArrayList<String> plantText = new ArrayList<String>();
				ArrayList<String> cellText = new ArrayList<String>();
				animalText = word.exactSearch(txtSearchTerms.getText()
						.toLowerCase(), animalDoc, checkBox.isSelected());
				heredityText = word.exactSearch(txtSearchTerms.getText()
						.toLowerCase(), heredityDoc, checkBox.isSelected());
				plantText = word.exactSearch(txtSearchTerms.getText()
						.toLowerCase(), plantsDoc, checkBox.isSelected());
				cellText = word.exactSearch(txtSearchTerms.getText()
						.toLowerCase(), cellDoc, checkBox.isSelected());
				textArea.append(aText);
				write(textArea, animalText, txtSearchTerms.getText());
				textArea.append(hText);
				write(textArea, heredityText, txtSearchTerms.getText());
				textArea.append(pText);
				write(textArea, plantText, txtSearchTerms.getText());
				textArea.append(cText);
				write(textArea, cellText, txtSearchTerms.getText());
				write(textArea, new ArrayList<String>(),
						txtSearchTerms.getText());
				textArea.append(newSearch);
			}
		});
		btnSearch_1.setBounds(644, 7, 117, 29);
		panel.add(btnSearch_1);

		// This panel creates the "partial terms" type of search
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Partial Terms", null, panel_1, null);
		panel_1.setLayout(null);

		// Creates the search field
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Searches through all of the documents and writes the results
				Search word = new Search(newSearch);
				ArrayList<String> animalText = new ArrayList<String>();
				ArrayList<String> heredityText = new ArrayList<String>();
				ArrayList<String> plantText = new ArrayList<String>();
				ArrayList<String> cellText = new ArrayList<String>();
				animalText = word.partialWordSearch(textField.getText()
						.toLowerCase(), animalDoc, checkBox.isSelected());
				heredityText = word.partialWordSearch(textField.getText()
						.toLowerCase(), heredityDoc, checkBox.isSelected());
				plantText = word.partialWordSearch(textField.getText()
						.toLowerCase(), plantsDoc, checkBox.isSelected());
				cellText = word.partialWordSearch(textField.getText()
						.toLowerCase(), cellDoc, checkBox.isSelected());
				textArea.append(aText);
				write(textArea, animalText, textField.getText());
				textArea.append(hText);
				write(textArea, heredityText, textField.getText());
				textArea.append(pText);
				write(textArea, plantText, textField.getText());
				textArea.append(cText);
				write(textArea, cellText, textField.getText());
				write(textArea, new ArrayList<String>(), textField.getText());
				textArea.append(newSearch);
			}
		});
		textField.setBounds(6, 6, 626, 28);
		textField.setText("Search Terms...");
		textField.setColumns(10);
		panel_1.add(textField);

		// Creates the search button for partial searching
		JButton button = new JButton("Search");
		// See the first search button for details
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Search word = new Search(newSearch);
				ArrayList<String> animalText = new ArrayList<String>();
				ArrayList<String> heredityText = new ArrayList<String>();
				ArrayList<String> plantText = new ArrayList<String>();
				ArrayList<String> cellText = new ArrayList<String>();
				animalText = word.partialWordSearch(textField.getText()
						.toLowerCase(), animalDoc, checkBox.isSelected());
				heredityText = word.partialWordSearch(textField.getText()
						.toLowerCase(), heredityDoc, checkBox.isSelected());
				plantText = word.partialWordSearch(textField.getText()
						.toLowerCase(), plantsDoc, checkBox.isSelected());
				cellText = word.partialWordSearch(textField.getText()
						.toLowerCase(), cellDoc, checkBox.isSelected());
				textArea.append(aText);
				write(textArea, animalText, textField.getText());
				textArea.append(hText);
				write(textArea, heredityText, textField.getText());
				textArea.append(pText);
				write(textArea, plantText, textField.getText());
				textArea.append(cText);
				write(textArea, cellText, textField.getText());
				write(textArea, new ArrayList<String>(), textField.getText());
				textArea.append(newSearch);
			}
		});
		button.setBounds(644, 7, 117, 29);
		panel_1.add(button);

		// Panel for derivative terms
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Derivative Terms", null, panel_2, null);
		panel_2.setLayout(null);

		// Adds the search field for derivative terms
		textField_1 = new JTextField();
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search word = new Search(newSearch);
				ArrayList<String> animalText = new ArrayList<String>();
				ArrayList<String> heredityText = new ArrayList<String>();
				ArrayList<String> plantText = new ArrayList<String>();
				ArrayList<String> cellText = new ArrayList<String>();
				animalText = word.derivitiveSearch(textField_1.getText()
						.toLowerCase(), animalDoc, checkBox.isSelected());
				heredityText = word.derivitiveSearch(textField_1.getText()
						.toLowerCase(), heredityDoc, checkBox.isSelected());
				plantText = word.derivitiveSearch(textField_1.getText()
						.toLowerCase(), plantsDoc, checkBox.isSelected());
				cellText = word.derivitiveSearch(textField_1.getText()
						.toLowerCase(), cellDoc, checkBox.isSelected());
				textArea.append(aText);
				write(textArea, animalText, textField_1.getText());
				textArea.append(hText);
				write(textArea, heredityText, textField_1.getText());
				textArea.append(pText);
				write(textArea, plantText, textField_1.getText());
				textArea.append(cText);
				write(textArea, cellText, textField_1.getText());
				write(textArea, new ArrayList<String>(), textField_1.getText());
				textArea.append(newSearch);
			}
		});
		textField_1.setText("Search Terms...");
		textField_1.setColumns(10);
		textField_1.setBounds(6, 6, 626, 28);
		panel_2.add(textField_1);

		JButton button_1 = new JButton("Search");
		// See the other search buttons for more details
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search word = new Search(newSearch);
				ArrayList<String> animalText = new ArrayList<String>();
				ArrayList<String> heredityText = new ArrayList<String>();
				ArrayList<String> plantText = new ArrayList<String>();
				ArrayList<String> cellText = new ArrayList<String>();
				// Search through each document
				animalText = word.derivitiveSearch(textField_1.getText()
						.toLowerCase(), animalDoc, checkBox.isSelected());
				heredityText = word.derivitiveSearch(textField_1.getText()
						.toLowerCase(), heredityDoc, checkBox.isSelected());
				plantText = word.derivitiveSearch(textField_1.getText()
						.toLowerCase(), plantsDoc, checkBox.isSelected());
				cellText = word.derivitiveSearch(textField_1.getText()
						.toLowerCase(), cellDoc, checkBox.isSelected());

				// Write the results on the textArea
				textArea.append(aText);
				write(textArea, animalText, textField_1.getText());
				textArea.append(hText);
				write(textArea, heredityText, textField_1.getText());
				textArea.append(pText);
				write(textArea, plantText, textField_1.getText());
				textArea.append(cText);
				write(textArea, cellText, textField_1.getText());
				write(textArea, new ArrayList<String>(), textField_1.getText());
				textArea.append(newSearch);
			}
		});
		button_1.setBounds(644, 7, 117, 29);
		panel_2.add(button_1);

		// Creates a help button used to help the user
		JButton btnHelpMe = new JButton("Help Me!");
		// if the button is clicked call helpGUI
		btnHelpMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				HelpGUI help = new HelpGUI();
				help.run();
			}
		});
		btnHelpMe.setBounds(662, 119, 117, 29);
		frame.getContentPane().add(btnHelpMe);

		// Creates a clear button, clears results if clicked
		JButton btnSearch = new JButton("Clear Results");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		btnSearch.setBounds(659, 390, 117, 29);
		frame.getContentPane().add(btnSearch);

		// Save button, saves the results to a txt file if clicked
		JButton btnSaveSearch = new JButton("Save Results");
		btnSaveSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveGUI save = new SaveGUI(textArea);
				save.run(textArea);
			}
		});
		btnSaveSearch.setBounds(659, 425, 117, 29);
		frame.getContentPane().add(btnSaveSearch);

	}

	private void highlightWord(JTextArea textArea, String wordToHighlight) {
		String content = textArea.getText();
		int indexNum = content.indexOf(wordToHighlight); // Sets the first index

		Highlighter.HighlightPainter painter = null; // Creating a painter
		painter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
		while (indexNum >= 0 && !wordToHighlight.isEmpty()) {
			// Next index of the word
			indexNum = content.indexOf(wordToHighlight, indexNum + 1);
			if (indexNum >= 0) { // match found
				try {
					int end = indexNum + wordToHighlight.length();
					// Highlight the word!
					hilight.addHighlight(indexNum, end, painter);
					textArea.setCaretPosition(end);
				} catch (BadLocationException e) {
				}
			}
		}
	}

	/**
	 * This method is used to write a string onto the text area of the GUI
	 * application.
	 * 
	 * @param textArea
	 * @param str
	 */
	private void write(JTextArea textArea, ArrayList<String> doc, String word) {
		Search search = new Search(newSearch);
		textArea.setHighlighter(hilight);

		String wordToHighlight = word.toLowerCase().trim();
		String partialWordSyn = search.isPartialWordSynonym(wordToHighlight);
		String[] syns; // the search word's synonyms
		// Highlight the words
		highlightWord(textArea, wordToHighlight);
		// If the search word has a synoynm then go execute this
		if (search.hasSynonym(wordToHighlight) || partialWordSyn != null) {
			if (partialWordSyn != null) {
				syns = search.getSynonym(partialWordSyn).split(" ");
			} else {
				syns = search.getSynonym(wordToHighlight).split(" ");
			}
			// Loop through the synonyms
			for (String syn : syns) {
				highlightWord(textArea, syn);
			}
		}
		for (String l : doc) {
			textArea.append(l);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
		}
		textArea.append("\n");
	}
}
