package main.distle;

import java.util.*;

public class EditDistanceUtils {
    
    /**
     * Returns the completed Edit Distance memoization structure, a 2D array
     * of ints representing the number of string manipulations required to minimally
     * turn each subproblem's string into the other.
     * 
     * @param s0 String to transform into other
     * @param s1 Target of transformation
     * @return Completed Memoization structure for editDistance(s0, s1)
     */

    public static int[][] getEditDistTable (String s0, String s1) {
        // >> [TN] Remember to delete unneeded comments. Some of the inline comments 
        // are also not needed since the code isn't too complex that it is unreadable
        // [!] TODO!
        int[][] table = new int[s0.length()+1][s1.length()+1];

        //GUTTERS
        for(int row = 0; row < s0.length()+1; row++){ table[row][0] = row; }
        for(int col = 0; col < s1.length()+1; col++){ table[0][col] = col; } 

        //NON-GUTTERS
        for(int row = 1; row < s0.length()+1; row++){
            for(int col = 1; col < s1.length()+1; col++){               

                int insertion = Integer.MAX_VALUE, deletion = Integer.MAX_VALUE, replacement = Integer.MAX_VALUE, transposition = Integer.MAX_VALUE;
                
                insertion = table[row][col-1] + 1;
                deletion = table[row-1][col] + 1;
                replacement = table[row-1][col-1] + (s1.charAt(col-1) != s0.charAt(row-1)?1:0);
                if(row >= 2 && col >= 2){
                    if(s0.charAt(row-1) == s1.charAt(col-2) && s1.charAt(col-1) == s0.charAt(row-2)){
                        transposition = table[row-2][col-2] + 1;
                    }
                }
                table[row][col] = Math.min(insertion,Math.min(deletion, Math.min(replacement, transposition)));
            }
        }
        return table;
    }
    
    /**
     * Returns one possible sequence of transformations that turns String s0
     * into s1. The list is in top-down order (i.e., starting from the largest
     * subproblem in the memoization structure) and consists of Strings representing
     * the String manipulations of:
     * <ol>
     *   <li>"R" = Replacement</li>
     *   <li>"T" = Transposition</li>
     *   <li>"I" = Insertion</li>
     *   <li>"D" = Deletion</li>
     * </ol>
     * In case of multiple minimal edit distance sequences, returns a list with
     * ties in manipulations broken by the order listed above (i.e., replacements
     * preferred over transpositions, which in turn are preferred over insertions, etc.)
     * @param s0 String transforming into other
     * @param s1 Target of transformation
     * @param table Precomputed memoization structure for edit distance between s0, s1
     * @return List that represents a top-down sequence of manipulations required to
     * turn s0 into s1, e.g., ["R", "R", "T", "I"] would be two replacements followed
     * by a transposition, then insertion.
     */
    public static List<String> getTransformationList (String s0, String s1, int[][] table) {
        // >> [TN] Again, we don't really "need" the inline comments here for each case
        // [!] TODO!
        List<String> result = new ArrayList<String>();
        if(s0.equals(s1)){
            return result;
        }
        int row = s0.length(), col = s1.length();
         
        while(table[row][col] > 0){
            //REPLACEMENT SKIP CASE  (replacement but didnt add)
            if(row>=1 && col>=1 && table[row-1][col-1] == table[row][col] && s0.charAt(row-1) == s1.charAt(col-1)){
                row -= 1;
                col -= 1;  
                continue;
            } //REPLACEMENT CASE
            else if(row>=1 && col>=1 && table[row-1][col-1] == table[row][col]-1){
                row -= 1;
                col -= 1;
                result.add("R");
                continue;
            }   
            //TRANSPOSITION
            else if(row >=2 && col >=2 && table[row-2][col-2] == table[row][col]-1 && s0.charAt(row-1) == s1.charAt(col-2) && s1.charAt(col-1) == s0.charAt(row-2)){
                row -= 2;
                col -= 2;
                result.add("T");
                continue;
            }   
            //INSERTION
            else if(col >= 1 && table[row][col-1] == table[row][col]-1){
                col -= 1;
                result.add("I");
                continue;
            }   
            //DELETION
            else if(row >= 1 && table[row-1][col] == table[row][col]-1){
                row -= 1;  
                result.add("D");
                continue;
            }      
        }return result;
    }

    
    
    /**
     * Returns the edit distance between the two given strings: an int
     * representing the number of String manipulations (Insertions, Deletions,
     * Replacements, and Transpositions) minimally required to turn one into
     * the other.
     * 
     * @param s0 String to transform into other
     * @param s1 Target of transformation
     * @return The minimal number of manipulations required to turn s0 into s1
     */
    public static int editDistance (String s0, String s1) {
        if (s0.equals(s1)) { return 0; }
        return getEditDistTable(s0, s1)[s0.length()][s1.length()];
    }
    
    /**
     * See {@link #getTransformationList(String s0, String s1, int[][] table)}.
     */
    public static List<String> getTransformationList (String s0, String s1) {
        return getTransformationList(s0, s1, getEditDistTable(s0, s1));
    }

}

// ===================================================
// >>> [TN] Summary
// Very well done! Your edit distance utils is well implemented and
// the Distle player is excellent.
// Stylistically, the code is clean, with only some comments
// not really needed. But other than that, good work!
// ---------------------------------------------------
// >>> [TN] Style Checklist
// [X] = Good, [~] = Mixed bag, [ ] = Needs improvement
//
// [X] Variables and helper methods named and used well
// [X] Proper and consistent indentation and spacing
// [X] Proper JavaDocs provided for ALL methods
// [X] Logic is adequately simplified
// [X] Code repetition is kept to a minimum
// ---------------------------------------------------
// Correctness:         100 / 100
// -> EditDistUtils:    20 / 20  (-2 / missed test)
// -> DistlePlayer:     279 / 265 (-0.5 / below threshold; max -25)
// Style Penalty:       -0
// Total:               100 / 100
// ===================================================