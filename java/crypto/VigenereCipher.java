/**
 * This class was written to decrypt vigenere ciphers using a provided key.
 * It will only work with lowercase messages and keys.
 * @author John Redding
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class VigenereCipher {
	int[] key;
	int keyIterator;
	LinkedHashMap<String, Integer> counts;
	LinkedHashMap<String, LinkedList<Integer>> matches;
	
	/**
	 * Create an uninitialized VigenereCipher object.
	 */
	public VigenereCipher() {
		
	}
	
	/**
	 * Create a VigenereCipher object using a key.
	 * @param k Key to be used for translation
	 */
	public VigenereCipher(String k) {
		// Assume key is lowercase
		key = new int[k.length()];
		keyIterator = 0;

		for(int i = 0; i < k.length(); i++) {
			// Shift key so that a == 0
			key[i] = (int)k.charAt(i) - 97;
		}	
	}
	
	/**
	 * Initialize a VigenereCipher object using a key.
	 * @param k Key to be used for translation
	 */
	public void init(String k) {
		// Assume key is lowercase
		key = new int[k.length()];
		for(int i = 0; i < k.length(); i++) {
			// Shift key so that a == 1
			key[i] = (int)k.charAt(i) - 97;
			//System.out.println(k.charAt(i) + " == " + key[i]);
		}
		keyIterator = 0;
	}
	
	/**
	 * Encrypt a message using a VigenereCipher initialized with a key.
	 * @param line The message to be encrypted
	 * @return String representation of encrypted message
	 */
	public String encrypt(String line) {
        StringBuffer result= new StringBuffer(); 
        
        /*
         * Sample message is entirely lowercase/numbers so not going to deal
         * with mixed case, especially because of having multiple ciphers.
         */
        for (char c : line.toCharArray()) { 
            if (Character.isLowerCase(c)) { 
            	// Shift char so a == 1
            	int cShift = (int) c - 96;
            	// Add key value
            	cShift += key[keyIterator];
            	// Mod by alphabet length
            	cShift = (cShift + 26) % 26;
            	// Shift char back to ascii
            	cShift += 97;
               
                result.append((char)cShift); 
                
                keyIterator = (keyIterator+1)%key.length;
            } 
            
        }
        return result.toString(); 
	}
	
	/**
	 * Decrypt a message using a VigenereCipher initialized with a key.
	 * @param line The message to be encrypted
	 * @return String representation of decrypted message
	 */
	public String decrypt(String line) {
		StringBuffer result = new StringBuffer();
		
		for (char c : line.toCharArray()) {
			if (c != ' ' && Character.isLowerCase(c)) { 
            	// Shift char so a == 26
            	int cShift = (int) c - 71;
            	// Shift left by key value
            	cShift -= key[keyIterator];
            	// Mod by 26 so a = 0 and z = 25
            	cShift = cShift % 26;
            	// Shift char back to ascii
            	cShift += 97;
               
                result.append((char)cShift); 
                keyIterator = (keyIterator+1)%key.length;

			} else {
				result.append(c);
			}
            
		}
		
		return result.toString();
	}
	
	/** FROM HERE DOWN IS INCOMPLETE, DO NOT USE **/

	/*

	public void substrCount(String message, int minLength) {
		counts = new LinkedHashMap<String, Integer>();
		matches = new LinkedHashMap<String, LinkedList<Integer>>();
		boolean foundSub = false;
		boolean matchesExist = false;
		
		// remove non-alphabet characters
		message = message.replaceAll("[^A-Za-z]", "");
		
		// Use a sliding window to check for substrings
		for ( int i = 0, j = minLength; j <= message.length();) {
			// Take substring
			String substr = message.substring(i, j);
			
			// If the substring is in the map, 
			if(counts.containsKey(substr) && matches.containsKey(substr)) {
				// increment count
				int count = counts.get(substr);
				counts.put(substr, count+1);
				
				// add index of substring
				LinkedList<Integer> temp = matches.get(substr);
				temp.add(i);
				matches.put(substr, temp);
				
				// Set found
		        foundSub = true;
		        matchesExist = true;
		        
			} else { // If the substring does not exist in map, add it
				counts.put(substr, 1);
				LinkedList<Integer> temp = new LinkedList<Integer>();
				temp.add(i);
				matches.put(substr, temp);
			}		
			
			// If matches were found
			if( j == message.length() && foundSub == true){
				foundSub = false;      
			    i = 0;
			    minLength += 1;
			    j = minLength;
			} else {
			    i++;
			    j++;
			}
		}
		
		
		// Use TreeMap to sort the results
		TreeMap<String, Integer> countsSorted = new TreeMap<String, Integer>();
		if(matchesExist) {
			int totals[] = new int[18];
			for(Map.Entry<String, LinkedList<Integer>> e : matches.entrySet()) {
				if(e.getValue().size() > 1) {
					for(Integer i : e.getValue()) {
						for(int j = 2; j < 20; j++) {
							if(i%j == 0) {
								totals[j-2]++;
							}
						}
					}
				}
			}
			for(int i = 0; i < totals.length; i++) {
				System.out.println("Multiples of " + (i+2) + ": " + totals[i]);
			}
		}
	}
	
	public void analyze(File file) {
		// Append all Strings
		StringBuilder temp = new StringBuilder();
		try (BufferedReader r = new BufferedReader(new FileReader(file))) {
			String line = r.readLine();
			while(line != null) {
				if(line.length() != 0){
					temp.append(line);
				}
				line = r.readLine();
			}
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		substrCount(temp.toString(), 3);
	}

	*/
}
