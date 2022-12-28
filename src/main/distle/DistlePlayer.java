package main.distle;

import static main.distle.EditDistanceUtils.*;

import java.util.*;

/**
 * AI Distle Player! Contains all logic used to automagically play the game of
 * Distle with frightening accuracy (hopefully).
 */
public class DistlePlayer {
    
    // [!] TODO: Any fields you want here!
    public Set<String> dictionary;
    public int turn;
    public int[][] arr = new int[26][14];
    /**
     * Constructs a new DistlePlayer.
     * [!] You MAY NOT change this signature, meaning it may not accept any arguments.
     * Still, you can use this constructor to initialize any fields that need to be,
     * though you may prefer to do this in the {@link #startNewGame(Set<String> dictionary, int maxGuesses)}
     * method.
     */
    public DistlePlayer () {
        // [!] TODO: Any initialization of fields you want here (can also leave empty)
    }
    
    /**
     * Called at the start of every new game of Distle, and parameterized by the
     * dictionary composing all possible words that can be used as guesses / one of
     * which is the correct.
     * 
     * @param dictionary The dictionary from which the correct answer and guesses
     * can be drawn.
     * @param maxGuesses The max number of guesses available to the player.
     */
    public void startNewGame (Set<String> dictionary, int maxGuesses) {
        // [!] TODO!
        this.dictionary = dictionary;
        turn = 1;
        for(int i = 0; i < arr.length; i++){
            for(int j=0; j< arr[0].length;j++){
                arr[i][j] = 0;
            }
        }
    }
    
    /**
     * Requests a new guess to be made in the current game of Distle. Uses the
     * DistlePlayer's fields to arrive at this decision.
     * 
     * @return The next guess from this DistlePlayer.
     */
    public String makeGuess () {
        // [!] TODO!
        if(turn >= 10){
            Random rand = new Random();
            ArrayList<String> dictArray = new ArrayList<>(dictionary);
            int randomitem = rand.nextInt(dictionary.size());
            return dictArray.get(randomitem);
        }
        String max = "";
        int maxVal = 0;
        for(String word: dictionary){
            for(int i = 0; i<word.length(); i++){
                arr[(int)word.charAt(i)-97][i] += 1;
            }    
        }
        for(String word: dictionary){
            int wordVal = 0;
            // i = letter , j = index of char in word
            for(int j = 0; j< word.length();j++){
                wordVal += arr[ (int)word.charAt(j)-97 ][j];
            }
            if(wordVal > maxVal){
                maxVal = wordVal;
                max = word;
            }  
        }
        turn++; 
        return max;
    }
    // histogram for every letter in the word, score each word
    // checks dictionary, for each of those words, go thru each letter and make a histogram of different letters showing up 
    // in the word, 2darray, first dimension for each letter, second dimension is spot it shows up in the word
    // 
    /**
     * Called by the DistleGame after the DistlePlayer has made an incorrect guess. The
     * feedback furnished is as follows:
     * <ul>
     *   <li>guess, the player's incorrect guess (repeated here for convenience)</li>
     *   <li>editDistance, the numerical edit distance between the guess and secret word</li>
     *   <li>transforms, a list of top-down transforms needed to turn the guess into the secret word</li>
     * </ul>
     * [!] This method should be used by the DistlePlayer to update its fields and plan for
     * the next guess to be made.
     * 
     * @param guess The last, incorrect, guess made by the DistlePlayer
     * @param editDistance Numerical distance between the guess and the secret word
     * @param transforms List of top-down transforms needed to turn the guess into the secret word 
     */
    public void getFeedback (String guess, int editDistance, List<String> transforms) {
        // [!] TODO!
        // Purpose: Filter out words that are no longer possible
        Set<String> tempDictionary = new HashSet<String>();
        for(String word : dictionary){  //theoretically this should also remove my guess from the dictionary
            if(getTransformationList(guess, word).equals(transforms)){
                tempDictionary.add(word);
            }
        }   
        dictionary = tempDictionary;
    }
}
