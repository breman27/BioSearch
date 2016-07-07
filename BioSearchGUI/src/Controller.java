import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The controller class starts the program by reading in the documents, then
 * passing them to the searchGUI class
 * 
 * @author Brett Long
 * @version 2.0.0 / Apr. 2013
 */
public class Controller {

	/**
	 * Controller class/main class of the program. Reads in the 4 documents that
	 * need to be searched through. Then passes them in as ArrayLists to
	 * searchGUI class
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		ArrayList<String> animalDoc = new ArrayList<String>();
		ArrayList<String> heredityDoc = new ArrayList<String>();
		ArrayList<String> plantsDoc = new ArrayList<String>();
		ArrayList<String> cellDoc = new ArrayList<String>();
		new FileIO("saved.txt");

		try {
			animalDoc = DocumentReader.readLines("Animals.txt");
			heredityDoc = DocumentReader.readLines("Heredity.txt");
			plantsDoc = DocumentReader.readLines("Plants.txt");
			cellDoc = DocumentReader.readLines("TheCell.txt");
		}
		/**
		 * Error cases handled if the file isn't found or is out of bounds
		 */
		catch (FileNotFoundException fnfe) {
			FnfeGUI e = new FnfeGUI();
			e.run();
		}

		catch (IndexOutOfBoundsException ioobe) {
			IoobeGUI e = new IoobeGUI();
			e.run();
		}
		SearchGUI search = new SearchGUI(animalDoc, heredityDoc, plantsDoc,
				cellDoc);
		search.run(animalDoc, heredityDoc, plantsDoc, cellDoc);
	}
}