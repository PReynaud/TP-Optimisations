package com.polytech4AInfo.Solution;

import com.polytech4AInfo.Positioning.Distribution;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Pierre on 10/04/2015.
 */
public class Solution {
    /**
     * First dimension is the patterns, second dimension is the shapes on a pattern
     */
    private int[][] solutionArray;

    /**
     * Cost of the pattern
     */
    private int cost;

    /**
     * The group of pattern we use
     */
    private Pattern[] patterns;

    private int[] order;

    public int[][] getSolutionArray() {
        return solutionArray;
    }

    public int getCost() {
        return cost;
    }

    public int[] getOrder() {
        return order;
    }

    public Pattern[] getPatterns() {
        return patterns;
    }

    public void setSolutionArray(int[][] solutionArray) {
        this.solutionArray = solutionArray;
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
     *
     * @param patterns it's the group of pattern
     */
    public Solution(Pattern[] patterns, int[] order) {
        this.patterns = patterns;
        this.order = order;

        int nbPatterns = patterns.length;
        int nbShapes = patterns[0].getNumberOfShapes();
        solutionArray = new int[nbPatterns][nbShapes];
        for (int i = 0; i < nbPatterns; i++) {
            for (int j = 0; j < nbShapes; j++) {
                solutionArray[i][j] = patterns[i].getPattern().get(j).getNumber();
            }
        }
    }

    public Solution() {
    }

    /**
     * Will send the cost of a pattern. It's our fitness function
     */
    public int calculCost () throws Exception {
        cost = 0;
        Distribution solutionCalc = new Distribution(patterns.length, patterns[0].getNumberOfShapes());

        solutionCalc.addShapesForAllPattern(solutionArray);

        solutionCalc.addOrder(order);
        double[] res = solutionCalc.getSolution().getPoint();
        for (int i = 0; i < res.length; i++) {
            // Will be our fitness
            if(res[i]>=0){
                cost += (int)Math.ceil(res[i]);
            }
            else{
                //throw new Exception("Invalid simplex result");
            }
        }
        cost += res.length * 20;
        return cost;
    }

    /**
     * Check if each pattern of the list is packable
     * @return -1 if every pattern of the solution is packable,
     * otherwise return the number of the first pattern which is not possible
     */
    public int isPackable(){
        int i = 0;
        while (i < this.patterns.length) {
            if(!this.patterns[i].isPossible()){
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Return if the list of pattern has at least one shape
     *
     * @return -1 if everything is ok, the index of the first shape that is not present otherwise
     */
    public int hasAtLeastOneShape() {
        int[] sumOfShapes = new int[this.solutionArray[0].length];
        for (int j = 0; j < sumOfShapes.length; j++){
            sumOfShapes[j] = 0;
        }
        for (int j = 0; j < this.solutionArray.length; j++){
            for (int k = 0; k < this.solutionArray[j].length; k++){
                sumOfShapes[k] += this.solutionArray[j][k];
            }
        }
        for (int j = 0; j < sumOfShapes.length; j++){
            if(sumOfShapes[j] <= 0){
                return j;
            }
        }
        return -1;
    }


    /**
     * Clone the object solutionArray and all of its attibutes
     *
     * @return A clone of the current object
     */
    public Solution clone() {
        Solution newSolution = new Solution();

        newSolution.setCost(this.getCost());
        int[][] clone_solution = this.getSolutionArray().clone();
        for(int i = 0; i < clone_solution.length;i++){
            clone_solution[i] = this.getSolutionArray()[i].clone();
        }

        newSolution.setSolutionArray(clone_solution);
        newSolution.setOrder(this.getOrder().clone());
        newSolution.setPatterns(this.getPatterns().clone());

        return newSolution;
    }

    /**
     * Add one pattern with a random value
     * @return true if the insertion has been done correctly
     */
    public boolean addOnePattern(){
        //TODO : this
        return false;
    }

    /**
     * Remove one pattern from the list of patterns
     * @return true if the deletion has been done correctly
     */
    public boolean removeOnePattern(){
        //TODO : this
        return false;
    }

    @Override
    public String toString() {
        String tabSolution = " [";
        for (int i = 0; i < solutionArray.length; i++){
            for(int j = 0; j < solutionArray[0].length; j++){
                tabSolution += solutionArray[i][j] + " ";
            }
            tabSolution += ", ";
        }
        tabSolution += "]";
        return "Solution{" +
                "solutionArray=" + tabSolution +
                ", cost=" + cost +
                ", order=" + Arrays.toString(order) +
                '}';
    }
}
