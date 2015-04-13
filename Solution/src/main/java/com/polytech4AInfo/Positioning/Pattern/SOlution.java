package com.polytech4AInfo.Positioning.Pattern;

/**
 * Created by Pierre on 10/04/2015.
 */
public class Solution {
    /**
     * First dimension is the patterns, second dimension is the shapes on a pattern
     */
    private int[][] solution;

    /**
     * Cost of the pattern
     */
    private int cost;

    /**
     * The group of pattern we use
     */
    private Pattern[] patterns;


    /**
     * Will construct the array with all the results
     * @param patterns it's the group of pattern
     */
    public void Solution(Pattern[] patterns){
        this.patterns = patterns;
        int nbPatterns = patterns.length;
        int nbShapes = patterns[0].getNumberOfShapes();
        solution = new int[nbPatterns][nbShapes];
        for(int i = 0; i < nbPatterns; i++){
            for(int j = 0 ; j < nbShapes; j++){
                solution[i][j] = patterns[i].getPattern().get(j).getNumber();
            }
        }
    }

    /**
     * Will return if the solution is correct or not
     * @return True if correct, false otherwise
     */
    public boolean isPossible(){
        boolean res = true;
        for(int i = 0; i < this.patterns.length; i++){
            //TODO fonction de validation d'un pattern
            /*res = res && this.patterns[i].isPossible()*/
        }

        return res;
    }
}
