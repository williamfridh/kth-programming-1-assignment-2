// SynonymHandler

/****************************************************************

SynonymHandler handles information about synonyms for various
words.

The synonym data can be read from a file and handled in various
ways. These data consists of several lines, where each line begins
with a word, and this word is followed with a number of synonyms.

The synonym line for a given word can be obtained. It is possible
to add a synonym line, and to remove the synonym line for a given
word. Also a synonym for a particular word can be added, or
removed. The synonym data can be sorted. Lastly, the data can be
written to a given file.

Author: Fadil Galjic

****************************************************************/

import java.io.*;    // FileReader, BufferedReader, PrintWriter,
                     // IOException
import java.util.*;  // LinkedList

class SynonymHandler
{
	// readSynonymData reads the synonym data from a given file
	// and returns the data as an array
    public static String[] readSynonymData (String synonymFile)
                                         throws IOException
    {
        BufferedReader reader = new BufferedReader(
	        new FileReader(synonymFile));
        LinkedList<String> synonymLines = new LinkedList<>();
        String synonymLine = reader.readLine();
        while (synonymLine != null)
        {
			synonymLines.add(synonymLine);
			synonymLine = reader.readLine();
		}
		reader.close();

		String[] synonymData = new String[synonymLines.size()];
		synonymLines.toArray(synonymData);

		return synonymData;
    }

    // writeSynonymData writes a given synonym data to a given
    // file
    public static void writeSynonymData (String[] synonymData,
        String synonymFile) throws IOException
    {
        PrintWriter writer = new PrintWriter(synonymFile);
        for (String synonymLine : synonymData)
            writer.println(synonymLine);
        writer.close();

    }

    // synonymLineIndex accepts synonym data, and returns the
    // index of the synonym line corresponding to a given word.
    // If the given word is not present, an exception of
    // the type IllegalArgumentException is thrown.
	private static int synonymLineIndex (String[] synonymData,
        String word)
    {
		int delimiterIndex = 0;
		String w = "";
		int i = 0;
		boolean wordFound = false;
		while (!wordFound  &&  i < synonymData.length)
		{
		    delimiterIndex = synonymData[i].indexOf('|');
		    w = synonymData[i].substring(0, delimiterIndex).trim();
		    if (w.equalsIgnoreCase(word))
				wordFound = true;
			else
				i++;
	    }

	    if (!wordFound)
	        throw new IllegalArgumentException(
		        word + " not present");

	    return i;
	}

    // getSynonymLine accepts synonym data, and returns
    // the synonym line corresponding to a given word.
    // If the given word is not present, an exception of
    // the type IllegalArgumentException is thrown.
    public static String getSynonymLine (String[] synonymData,
        String word)
    {
		int index = synonymLineIndex(synonymData, word);

	    return synonymData[index];
	}

    // addSynonymLine accepts synonym data, and adds a given
    // synonym line to the data
	public static String[] addSynonymLine (String[] synonymData,
	    String synonymLine)
	{
		String[] synData = new String[synonymData.length + 1];
		for (int i = 0; i < synonymData.length; i++)
		    synData[i] = synonymData[i];
		synData[synData.length - 1] = synonymLine;

	    return synData;
	}

    // removeSynonymLine accepts synonym data, and removes
    // the synonym line corresponding to a given word.
    // If the given word is not present, an exception of
    // the type IllegalArgumentException is thrown.
	public static String[] removeSynonymLine (String[] synonymData,
	    String word)
	{
		/* ========================================= STUDENT CODE ========================================= */
        try {
            List<String> stringArrayList = new ArrayList<String>();
            for (String line: synonymData) {
                if (!line.contains(word + " | ")) {
                    stringArrayList.add(line);
                }
            }
            return stringArrayList.toArray(new String[stringArrayList.size()]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(word + " not present");
        }
        /* ========================================= END STUDENT CODE ========================================= */
	}

    // addSynonym accepts synonym data, and adds a given
    // synonym for a given word.
    // If the given word is not present, an exception of
    // the type IllegalArgumentException is thrown.
	public static void addSynonym (String[] synonymData,
	    String word, String synonym)
	{
        /* ========================================= STUDENT CODE ========================================= */
        try {
            int i = synonymLineIndex(synonymData, word);
            synonymData[i] += ", " + synonym;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(word + " not present");
        }
        /* ========================================= END STUDENT CODE ========================================= */
	}

    // removeSynonym accepts synonym data, and removes a given
    // synonym for a given word.
    // If the given word or the given synonym is not present, an
    // exception of the type IllegalArgumentException is thrown.
	public static void removeSynonym (String[] synonymData,
	    String word, String synonym)
	{
        /* ========================================= STUDENT CODE ========================================= */
        try {

            int index = synonymLineIndex(synonymData, word);

            String[] newStringArray =
                synonymData[index]
                .replace(word, "") // Remove word
                .replaceAll("[|\\s]", "") // Remove unwanted characters
                .split(","); // Split into array

            String newString = word + " | "; // Start building new string

            for (String synonymTarget: newStringArray) {
                if (!synonymTarget.equalsIgnoreCase(synonym)) {
                    newString += synonymTarget + ", ";
                }
            }

            newString = newString.substring(0, newString.length() - 2); // Remove last two characters (", ")

            synonymData[index] = newString;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(word + " not present");
        }
        /* ========================================= END STUDENT CODE ========================================= */
	}

    // sortIgnoreCase sorts an array of strings, using
    // the selection sort algorithm
    private static void sortIgnoreCase (String[] strings)
    {
        /* ========================================= STUDENT CODE ========================================= */
        String tmp;
        for (int i = 0; i < strings.length; i++) { // Loop string array.
            for (int j = i + 1; j < strings.length; j++) { // Loop string array.
                if (strings[i].compareToIgnoreCase(strings[j]) > 0) {
                    // Swap places.
                    tmp = strings[i];
                    strings[i] = strings[j];
                    strings[j] = tmp;
                }
            }
        }
        /* ========================================= END STUDENT CODE ========================================= */
	}

    // sortSynonymLine accepts a synonym line, and sorts
    // the synonyms in this line
    private static String sortSynonymLine (String synonymLine)
    {
        /* ========================================= STUDENT CODE ========================================= */

        // Split up data into array.
        String[] synonymArray =
            synonymLine
            .replace(" ", "")
            .split("[|,]");

        // Clear array and sort.
        synonymArray[0] = "";
        sortIgnoreCase(synonymArray);

        // Create new stirng.
        String newString = "";
        for (String synonym : synonymArray) {
            if (synonym.length() > 0){
                newString += synonym + ", ";
            }
        }

        newString = newString.substring(0, newString.length() - 2);

        return newString;
        /* ========================================= END STUDENT CODE ========================================= */
	}

    // sortSynonymData accepts synonym data, and sorts its
    // synonym lines and the synonyms in these lines
	public static void sortSynonymData (String[] synonymData)
	{
        /* ========================================= STUDENT CODE ========================================= */
        for (int i = 0; i < synonymData.length; i++) {
            String[] split =
            synonymData[i]
                .replace(" ", "")
                .split("[|,]");
            // Sort synonyms
            synonymData[i] = split[0] + " | " + sortSynonymLine(synonymData[i]);
        }

        // Sort words
        sortIgnoreCase(synonymData);
        /* ========================================= END STUDENT CODE ========================================= */
	}
}
