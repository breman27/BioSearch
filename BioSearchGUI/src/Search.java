import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/********************************************************************
 * Meat of the program. When a search button is pushed this class will be called
 * and a searching algorithm will be performed. This class will hold three types
 * of search methods. Regular(exact), Partial and Derivative search.
 * 
 * @author Brett Long
 * @version 2.0.0/ Apr.2013
 *******************************************************************/
public class Search {
	private ArrayList<String> ignore;
	private ArrayList<String> thesaurus;
	private ArrayList<String> result;

	private HashMap<String, String> map = new HashMap<String, String>();
	private static String wordSearched;

	public Search(String searchWord) {
		// Get the ignore and synonym txt file
		ignore = new ArrayList<String>();
		thesaurus = new ArrayList<String>();
		// If it isn't there, throw a fnf error
		try {
			ignore = DocumentReader.readDocument("ignore.txt");
			thesaurus = DocumentReader.readSpecial("thesaurus.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		createThesaurus();
	}

	/******************** Public Methods ***********************/
	/**
	 * Takes in the root word and searches for the derivitives of it. basically
	 * a partial word search using a full word
	 * 
	 * @param searchWord
	 * @param doc
	 * @return
	 */
	public ArrayList<String> derivitiveSearch(String searchWord,
			ArrayList<String> doc, boolean thesaurus, boolean sent) {
		ArrayList<String> result = new ArrayList<String>();
		int pos = 0;
		String fullWord;
		String[] line;
		String[] theSynonyms;
		setSearchWord(searchWord);
		if (isInvalid(searchWord)) {
			result.add("Please enter an actual word!");
			return result;
		}
		// If the check box is selected for using the thesaurus, search through
		// synonyms.
		if (thesaurus) {
			// If the word is in the thesaurus keep going
			fullWord = isPartialWordSynonym(searchWord);
			if (fullWord != null) {
				setSearchWord(fullWord);
				// Gets the synonyms of this word
				theSynonyms = getSynonym(fullWord).split(" ");
				addSynonyms(theSynonyms, fullWord, doc, sent);
			}
		}
		// If the word isn't ignored, then keep searching
		if (!isIgnored(ignore, searchWord) && searchWord.length() >= 3) {
			// Search through the document
			for (int i = 0; i < doc.size() - 1; i++) {
				try {
					// Create an array of Strings to hold the words in the line
					line = doc.get(i).split(" ");
					// Loop through the line
					for (int j = 0; j < line.length; j++) {
						// If the word is larger or equal to the search word,
						// continue
						if (line[j].length() >= searchWord.length()) {
							// If the word is equal to the search word, add it.
							if (searchWord.equals(line[j].substring(0,
									searchWord.length()).toLowerCase())) {
								result.add("" + "\n\n" + "Search Word: "
										+ line[j] + "\n\n"
										+ "Paragraph Number in text " + (i + 1)
										+ " Word Number " + (j + 1));

								if (!sent) {
									result.add(doc.get(i));
								} else {
									pos = doc.get(i).indexOf(line[j]);
									result.add(doc
											.get(i)
											.substring(
													doc.get(i).lastIndexOf(".",
															pos) + 1,
													doc.get(i)
															.indexOf(".", pos) + 1));
								}
							}
						}
					}
				} catch (IndexOutOfBoundsException ioobe) {
				}
			}
		}
		// If its ignored, let the user know
		else {
			result.add("This word is being ignored in searching or is shorter than 3 characters"
					+ ", please pick another word to search");
		}
		if (result.isEmpty()) {
			result.add("No search results found for this word!");
		}
		return result;
	}

	/**
	 * Method for searching using the exact word. If the word is in one of the
	 * text files, the results will be printed on the text field.
	 * 
	 * @param searchWord
	 * @param doc
	 * @param sent
	 * @return result
	 */
	public ArrayList<String> exactSearch(String searchWord,
			ArrayList<String> doc, boolean thesaurus, boolean sentence) {
		result = new ArrayList<String>();
		String[] synonyms = null;
		setSearchWord(searchWord);

		if (isInvalid(searchWord)) {
			result.add("Please enter an actual word!");
			return result;
		}
		// If the cross dictionary check box is selected, then do this
		if (thesaurus && hasSynonym(searchWord)) {
			addSynonyms(synonyms, searchWord, doc, sentence);
		}
		if (isIgnored(ignore, searchWord)) {
			result.add("This word is ignored in search results,"
					+ " please select a different word to search");
		} else {
			// search for the word in the document
			BoyerMooreSearch s = new BoyerMooreSearch(searchWord);
			while(s.search(doc.toString()) != -1){
				System.out.println(s.search(doc.toString()));
			}
			search(doc, searchWord);
		}
		if (result.isEmpty()) {
			result.add("No search results found for this word!");
		}
		return result;
	}

	public static String getSearchWord() {
		return wordSearched;
	}

	/**
	 * Uses the hashmap to search through the synonyms of the search word
	 * 
	 * @param searchWord
	 * @return the synonyms if they exist
	 */
	public String getSynonym(String searchWord) {
		if (map.containsKey(searchWord)) {
			return map.get(searchWord);
		}
		return null;
	}

	/**
	 * Checks to see if the word has a synonym in the thesaurus, if it is return
	 * true if not, return false.
	 * 
	 * @param word
	 * @return boolean value
	 */
	public boolean hasSynonym(String word) {
		return map.containsKey(word);
	}

	/**
	 * Checks a partial word against the list of synonyms, if its in there it
	 * returns that word, if not it returns null. A slight change from the above
	 * synonym checker to save a lot of headache.
	 * 
	 * @param word
	 * @return
	 */
	public String isPartialWordSynonym(String word) {
		Set<String> set;
		String search;
		set = map.keySet();
		for (int x = 0; x < set.size(); x++) {
			search = set.toArray()[x].toString();
			if (search.length() >= word.length()) {
				if (search.substring(0, word.length()).equals(word)) {
					return search;
				}
			}
		}
		return null;
	}

	/**
	 * This method is used to search through the textfiles using a partial word.
	 * If the word is found then it will be printed on the text field.
	 * 
	 * @param searchWord
	 * @param doc
	 * @return
	 */
	public ArrayList<String> partialWordSearch(String searchWord,
			ArrayList<String> doc, boolean thesaurus, boolean sent) {
		result = new ArrayList<String>();
		int pos = 0;
		String fullWord;
		String[] line;
		String[] theSynonyms;
		setSearchWord(searchWord);

		if (isInvalid(searchWord)) {
			result.add("Please enter an actual word!");
			return result;
		}
		// If the radio button for using the thesaurus is pushed, search through
		// synonyms.
		if (thesaurus) {
			// If the word is in the thesaurus keep going
			fullWord = isPartialWordSynonym(searchWord);
			if (fullWord != null) {
				// Gets the synonyms of this word
				theSynonyms = getSynonym(fullWord).split(" ");
				addSynonyms(theSynonyms, fullWord, doc, sent);
			}
		}
		// If the word isn't ignored, then keep searching
		if (!isIgnored(ignore, searchWord) && searchWord.length() >= 3) {
			// Search through the document
			for (int i = 0; i < doc.size() - 1; i++) {
				try {
					// Create an array of Strings to hold the words in the line
					line = doc.get(i).split(" ");
					// Loop through the line
					for (int j = 0; j < line.length; j++) {
						// If the word is larger or equal to the search word,
						// continue
						if (line[j].length() >= searchWord.length()) {
							// If the word is equal to the search word, add it.
							if (searchWord.equals(line[j].substring(0,
									searchWord.length()).toLowerCase())) {
								result.add("" + "\n\n" + "Search Word: "
										+ line[j] + "\n\n"
										+ "Paragraph Number in text " + (i + 1)
										+ " Word Number " + (j + 1));
								if (!sent) {
									result.add(doc.get(i));
								} else {
									pos = doc.get(i).indexOf(line[j]);
									result.add(doc
											.get(i)
											.substring(
													doc.get(i).lastIndexOf(".",
															pos) + 1,
													doc.get(i)
															.indexOf(".", pos) + 1));
								}
							}
						}
					}
				} catch (IndexOutOfBoundsException ioobe) {

				}
			}
		}
		// If its ignored, let the user know
		else {
			result.add("This word is being ignored in searching or is shorter than 3 characters"
					+ ", please pick another word to search");
		}
		if (result.isEmpty()) {
			result.add("No search results found for this word!");
		}
		return result;
	}

	/**************************** Private Methods *******************************/
	private void addSynonyms(String[] synonyms, String searchWord,
			ArrayList<String> doc, boolean sentence) {
		// The value returned from getSynonym is a long string
		// So this variable splits the words up in the string
		try {
			synonyms = getSynonym(searchWord).split(" ");
			// iterate through all the words
			for (int i = 0; i < synonyms.length; i++) {
				// If the word isn't null then search it
				if (synonyms[i] != null) {
					for (int index = 0; index < exactSearchSynonym(synonyms[i],
							doc, sentence).size(); index++) {
						result.add(exactSearchSynonym(synonyms[i], doc,
								sentence).get(index));
					}
				}
			}
		} catch (Exception e) {
			System.out.println("No synonyms");
		}
	}

	/**
	 * Creates a hashmap to keep track of the words that are considered to be
	 * synonyms.
	 * 
	 * Returns nothing
	 */
	private void createThesaurus() {
		String[] key;
		String[] keyHolder;
		String[] valueHolder;
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 0; i < thesaurus.size(); i++) {
			key = thesaurus.get(i).split(":");
			keyHolder = key[0].split("\n");
			valueHolder = key[1].split("\n");
			for (int x = 0; x < keyHolder.length; x++) {
				keys.add(keyHolder[x]);
			}
			for (int index = 0; index < valueHolder.length; index++) {
				values.add(valueHolder[index]);
			}
		}
		for (int index = 0; index < keys.size(); index++) {
			map.put(keys.get(index).trim(), values.get(index).trim());
		}
	}

	/**
	 * Search the synonyms of the word that has been determined to be a synonym.
	 * 
	 * @param searchWord
	 * @param doc
	 */
	private ArrayList<String> exactSearchSynonym(String searchWord,
			ArrayList<String> doc, boolean sent) {

		result = new ArrayList<String>();
		setSearchWord(searchWord);
		searchWord = searchWord.trim().toLowerCase();
		// Search for the synonym
		BoyerMooreSearch s = new BoyerMooreSearch(searchWord);
		search(doc, searchWord);
		if (result.isEmpty()) {
			result.add(" ");
			result.add("\nNo search results found for the word " + searchWord);
		}

		return result;
	}

	/**
	 * Checks if the word is in the ignored list, if it is return true if it
	 * isn't return false.
	 * 
	 * @param ignored
	 * @param word
	 * @return boolean value
	 */
	private boolean isIgnored(ArrayList<String> ignored, String word) {
		return ignored.contains(word);
	}

	private boolean isInvalid(String searchWord) {
		searchWord = searchWord.toLowerCase();
		return searchWord.trim().isEmpty();
	}

	private void search(ArrayList<String> doc, String searchWord) {
		int pos = 0, start = 0, end = 0;
		for (String l : doc) {
			if (l != null && l.contains(searchWord)) {
				pos = l.indexOf(searchWord + " ");
				start = l.lastIndexOf(".", pos);
				end = l.indexOf(".", pos);
				if (start > -1 && end > 0) {
					result.add("" + "\n\n" + "Search Word: " + searchWord
							+ "\n");
					result.add(l.substring(start + 1, end + 1));
				}
			}
		}
	}

	/*
	 * @params ArrayList doc - document to convert to String
	 * 
	 * Converts a document to a string
	 */
	private String docToString(ArrayList<String> doc) {
		String str = "";
		for (String s : doc) {
			str += s + " ";
		}
		return str;
	}

	/*
	 * Sets the search word
	 */
	private void setSearchWord(String word) {
		wordSearched = word;
	}
}