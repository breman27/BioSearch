import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**********************************************************
 * The DocumentReader class includes utilities for extracting words from text
 * documents and returning them in an ArrayList. <br>
 * 
 * @author Nathan Sprague, edited by Brett Long
 * @version V2.0, 04/2012, V3.0, 04/2013
 **********************************************************/
public class DocumentReader {

	/*********************************************************
	 * This method opens a plain-text file and reads its contents into an
	 * ArrayList. All punctuation and whitespace is discarded. All words are
	 * converted to lower-case. The words in the ArrayList are stored in the
	 * order they appear in the document and include repetitions.
	 * 
	 * @param fileName
	 *            the name of a plain-text file
	 * @throws FileNotFoundException
	 * 
	 * @returns an arraylist containing the ordered list of words
	 ********************************************************/
	public static ArrayList<String> readDocument(String fileName)
			throws FileNotFoundException {
		Scanner scanner;
		ArrayList<String> words = new ArrayList<String>();
		scanner = new Scanner(new File(fileName));
		scanner.useDelimiter(" ");
		while (scanner.hasNext()) {
			String cur = scanner.next();
			if (!cur.equals("")) {
				words.add(cur.toLowerCase());
			}
		}
		scanner.close();
		return words;
	}

	/**
	 * Reads the full lines of the file that is passed in. Each line is then
	 * added to an array list and returned.
	 * 
	 * @param fileName
	 * @throws IOException
	 * 
	 * @returns an arraylist of the lines
	 */
	public static ArrayList<String> readLines(String fileName)
			throws IOException {
		BufferedReader reader;
		ArrayList<String> lines = new ArrayList<String>();
		reader = new BufferedReader(new FileReader(fileName));
		while (reader.readLine() != null) {
			lines.add(reader.readLine());
		}
		reader.close();
		return lines;
	}
	/*
	 * Looks for the special delimiter ";" to seperate each word/synonym pair
	 */
	public static ArrayList<String> readSpecial(String fileName)
			throws IOException {
		Scanner scanner;
		ArrayList<String> words = new ArrayList<String>();
		scanner = new Scanner(new File(fileName));
		scanner.useDelimiter(";");
		while (scanner.hasNext()) {
			String cur = scanner.next();
			if (!cur.equals("")) {
				words.add(cur.toLowerCase());
			}
		}
		scanner.close();
		return words;
	}
}
