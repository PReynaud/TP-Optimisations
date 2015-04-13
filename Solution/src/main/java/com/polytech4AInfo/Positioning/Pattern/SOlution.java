package com.polytech4AInfo.Positioning.Pattern;

import com.polytech4AInfo.Positioning.Distribution;

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

    private int[] order;

    public int[][] getSolution() {
        return solution;
    }

    /**
     * Do the calcul of the cost before getting it
     * @return the cost of the pattern
     */
    public int getCost() {
        calculCost();
        return cost;
    }

    public int[] getOrder() {
        return order;
    }

    public Pattern[] getPatterns() {
        return patterns;
    }

    public void setSolution(int[][] solution) {
        this.solution = solution;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPatterns(Pattern[] patterns) {
        this.patterns = patterns;
    }

    public void setOrder(int[] order) {
        this.order = order;
    }

    /**
     * Will construct the array with all the results
     * @param patterns it's the group of pattern
     */
    public Solution(Pattern[] patterns, int[] order){
        this.patterns = patterns;
        this.order = order;

        int nbPatterns = patterns.length;
        int nbShapes = patterns[0].getNumberOfShapes();
        solution = new int[nbPatterns][nbShapes];
        for(int i = 0; i < nbPatterns; i++){
            for(int j = 0 ; j < nbShapes; j++){
                solution[i][j] = patterns[i].getPattern().get(j).getNumber();
            }
        }
    }

    public Solution(){
    }

    /**
     * Will send the cost of a pattern. It's our fitness function
     */
    public void calculCost(){
        Distribution solutionCalc = new Distribution(patterns.length, patterns[0].getNumberOfShapes());

        solutionCalc.addShapesForAllPattern(solution);

        solutionCalc.addOrder(order);
        double[] res = solutionCalc.getSolution().getPoint();
        for (int i = 0; i < res.length; i++){
            //TODO change that with the right values
            cost += res[i];
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

    /**
     * Clone the object solution and all of its attibutes
     * @return A clone of the current object
     */
    public Solution clone(){
        Solution newSolution = new Solution();

        newSolution.setCost(this.getCost());
        newSolution.setSolution(this.getSolution().clone());
        newSolution.setOrder(this.getOrder().clone());
        newSolution.setPatterns(this.getPatterns().clone());

        return newSolution;
    }
}
