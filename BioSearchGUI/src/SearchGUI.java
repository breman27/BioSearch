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
	private String aText, pText, hText, cText, searchBarrier;
	private Highlighter hilight;
	private JFrame frame;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JCheckBox checkBox;
	private JTextField exactSearchTerm, partialSearchField, derivitiveSearchField;

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
		String strBarrier = new String(new char[50]).replace("\0", "-");

		aText = strBarrier + "Animal Text" + strBarrier + "\n";
		hText = strBarrier + "Heredity Text" + strBarrier + "\n";
		pText = strBarrier + "Plant Text" + strBarrier + "\n";
		cText = strBarrier + "Cell Text" + strBarrier + "\n";
		searchBarrier = strBarrier + strBarrier + strBarrier + "\n";

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
		JPanel exactPanel = new JPanel();
		exactPanel.setBackground(new Color(211, 211, 211));
		exactPanel.setBorder(null);

		tabbedPane.addTab("Exact Terms", null, exactPanel, null);
		exactPanel.setLayout(null);

		// Sets the search field
		exactSearchTerm = new JTextField();
		// Allows user to push the enter button to search
		exactSearchTerm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Searches through all of the documents and writes the results
				Search word = new Search();
				performSearch(word, textArea, "exact");
			}
		});
		exactSearchTerm.setText("Search Terms...");
		exactSearchTerm.setBounds(6, 6, 626, 28);
		exactPanel.add(exactSearchTerm);
		exactSearchTerm.setColumns(10);

		// Creates the Search button
		JButton exactSearchBtn = new JButton("Search");
		// If the actual search button is pushed, then take this action
		exactSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Searches through all of the documents and writes the results
				Search word = new Search();
				performSearch(word, textArea, "exact");
			}
		});
		exactSearchBtn.setBounds(644, 7, 117, 29);
		exactPanel.add(exactSearchBtn);

		// This panel creates the "partial terms" type of search
		JPanel partialPanel = new JPanel();
		partialPanel.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Partial Terms", null, partialPanel, null);
		partialPanel.setLayout(null);

		// Creates the search field
		partialSearchField = new JTextField();
		partialSearchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search word = new Search();
				performSearch(word, textArea, "partial");
			}
		});
		partialSearchField.setBounds(6, 6, 626, 28);
		partialSearchField.setText("Search Terms...");
		partialSearchField.setColumns(10);
		partialPanel.add(partialSearchField);

		// Creates the search button for partial searching
		JButton partialSearchBtn = new JButton("Search");
		// See the first search button for details
		partialSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Search word = new Search();
				performSearch(word, textArea, "partial");
			}
		});
		partialSearchBtn.setBounds(644, 7, 117, 29);
		partialPanel.add(partialSearchBtn);

		// Panel for derivative terms
		JPanel derivitivePanel = new JPanel();
		derivitivePanel.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Derivative Terms", null, derivitivePanel, null);
		derivitivePanel.setLayout(null);

		// Adds the search field for derivative terms
		derivitiveSearchField = new JTextField();
		// Allows user to press enter to search
		derivitiveSearchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search word = new Search();
				performSearch(word, textArea, "derivitive");
			}
		});
		derivitiveSearchField.setText("Search Terms...");
		derivitiveSearchField.setColumns(10);
		derivitiveSearchField.setBounds(6, 6, 626, 28);
		derivitivePanel.add(derivitiveSearchField);

		JButton derivitiveSearchBtn = new JButton("Search");
		// See the other search buttons for more details
		derivitiveSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search word = new Search();
				performSearch(word, textArea, "derivitive");
			}
		});
		derivitiveSearchBtn.setBounds(644, 7, 117, 29);
		derivitivePanel.add(derivitiveSearchBtn);

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
		JButton clearResultsBtn = new JButton("Clear Results");
		clearResultsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		clearResultsBtn.setBounds(659, 390, 117, 29);
		frame.getContentPane().add(clearResultsBtn);

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

	/*
	 * Perform various searches depending on the type passed
	 * Removes repetitive code in the action listeners
	 */
	private void performSearch(Search word, JTextArea textArea, String type) {
		ArrayList<String> animalText = new ArrayList<String>();
		ArrayList<String> heredityText = new ArrayList<String>();
		ArrayList<String> plantText = new ArrayList<String>();
		ArrayList<String> cellText = new ArrayList<String>();
		
		// Search through each document
		if (type.equals("derivitive")) {
			animalText = word.derivitiveSearch(derivitiveSearchField.getText()
					.toLowerCase(), animalDoc, checkBox.isSelected());
			heredityText = word.derivitiveSearch(derivitiveSearchField.getText()
					.toLowerCase(), heredityDoc, checkBox.isSelected());
			plantText = word.derivitiveSearch(derivitiveSearchField.getText()
					.toLowerCase(), plantsDoc, checkBox.isSelected());
			cellText = word.derivitiveSearch(derivitiveSearchField.getText()
					.toLowerCase(), cellDoc, checkBox.isSelected());
		} else if (type.equals("exact")) {
			derivitiveSearchField.setText(exactSearchTerm.getText());
			animalText = word.exactSearch(exactSearchTerm.getText().toLowerCase(),
					animalDoc, checkBox.isSelected());
			heredityText = word.exactSearch(
					exactSearchTerm.getText().toLowerCase(), heredityDoc,
					checkBox.isSelected());
			plantText = word.exactSearch(exactSearchTerm.getText().toLowerCase(),
					plantsDoc, checkBox.isSelected());
			cellText = word.exactSearch(exactSearchTerm.getText().toLowerCase(),
					cellDoc, checkBox.isSelected());
		} else if (type.equals("partial")) {
			derivitiveSearchField.setText(partialSearchField.getText());
			animalText = word.partialWordSearch(partialSearchField.getText()
					.toLowerCase(), animalDoc, checkBox.isSelected());
			heredityText = word.partialWordSearch(partialSearchField.getText()
					.toLowerCase(), heredityDoc, checkBox.isSelected());
			plantText = word.partialWordSearch(partialSearchField.getText()
					.toLowerCase(), plantsDoc, checkBox.isSelected());
			cellText = word.partialWordSearch(partialSearchField.getText()
					.toLowerCase(), cellDoc, checkBox.isSelected());
		}
		// Write the results on the textArea
		textArea.append(aText);
		write(textArea, animalText, derivitiveSearchField.getText());
		textArea.append(hText);
		write(textArea, heredityText, derivitiveSearchField.getText());
		textArea.append(pText);
		write(textArea, plantText, derivitiveSearchField.getText());
		textArea.append(cText);
		write(textArea, cellText, derivitiveSearchField.getText());
		write(textArea, new ArrayList<String>(), derivitiveSearchField.getText());
		textArea.append(searchBarrier);
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
		Search search = new Search();
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
