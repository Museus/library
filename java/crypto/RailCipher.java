/**
 * This class was written to decrypt rail ciphers using a provided key
 * @author John Redding - jrr95 - 4058479
 */
public class RailCipher {
	private int key;
	
	/**
	 * This constructor returns a RailCipher object to be used for translation
	 * @param k The key to be used for translation
	 */
	public RailCipher(int k) {
		key = k;
	}

	/**
	 * This constructor returns an uninitialized RailCipher object.
	 */
	public RailCipher() {

	}

	/**
	 * Initialize a RailCipher object with a key.
	 * @param k The key to be used for translation
	 */
	public void init(int k) {
		key = k;
	}
	
	/**
	 * Encrypt a message using an initialized RailCipher object.
	 * @param line to be encrypted
	 * @return String representation of encrypted message
	 */
	public String encrypt(String line) {
		int longColumns, longLength, shortLength, lineIterator;
		longColumns = (line.length()%key);
		longLength = (int) Math.ceil(line.length() / (double) key);
		shortLength = longLength-1;
		lineIterator = 0;
		char[][] fence = new char[longLength][key];
		
		/*
		 * Iterate through the file, filling in the fence in order
		 */
		
		while(lineIterator < line.length()) {
			fence[lineIterator/key][lineIterator%key] = line.charAt(lineIterator++);
		}
		
		
		/*
		 * Append each column to a StringBuilder
		 */
		StringBuilder temp = new StringBuilder();

		
		// For each long column
		for(int i = 0; i < longColumns; i++) {
			// fill the column with long lines
			for(int j = 0; j < longLength; j++) {
				temp.append(fence[j][i]);
			}
		}
		
		// for each short column
		for(int i = longColumns; i < key; i++) {
			// fill the column with short lines
			for(int j = 0; j < shortLength; j++) {
				temp.append(fence[j][i]);
			}
		}

		return temp.toString();
	}

	/**
	 * Decrypt a method using an initialized RailCipher
	 */
	public String decrypt(String line) {
		int longColumns, longLength, shortLength, lineIterator;
		longColumns = (line.length()%key);
		longLength = (int) Math.ceil(line.length() / (double) key);
		shortLength = longLength-1;
		lineIterator = 0;
		char[][] fence = new char[longLength][key];
		
		/*
		 * Fill the "fence" array used to decrypt/encrypt the message
		 */
		// For each long column
		for(int i = 0; i < longColumns; i++) {
			// fill the column with long lines
			for(int j = 0; j < longLength; j++) {
				fence[j][i] = line.charAt(lineIterator++);
			}
		}
		
		// for each short column
		for(int i = longColumns; i < key; i++) {
			// fill the column with short lines
			for(int j = 0; j < shortLength; j++) {
				fence[j][i] = line.charAt(lineIterator++);
			}
		}
		
		/*
		 * Now that the fence is filled, use a StringBuilder to read the
		 * decrypted message from the fence
		 */
		StringBuilder temp = new StringBuilder();
		//for each row
		for(int i = 0; i < longLength; i ++) {
			// print each fence
			for(int j = 0; j < key; j++) {
				temp.append(fence[i][j]);
			}
		}
		
		/*
		 * Return the message in String format
		 */
		return temp.toString();
	}
}
