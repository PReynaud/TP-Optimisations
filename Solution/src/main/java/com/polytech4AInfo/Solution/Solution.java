package com.polytech4AInfo.Solution;

import com.polytech4AInfo.Positioning.Distribution;

import java.lang.reflect.Array;
import java.util.Arrays;

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

    public int getCost() {
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
     *
     * @param patterns it's the group of pattern
     */
    public Solution(Pattern[] patterns, int[] order) {
        this.patterns = patterns;
        this.order = order;

        int nbPatterns = patterns.length;
        int nbShapes = patterns[0].getNumberOfShapes();
        solution = new int[nbPatterns][nbShapes];
        for (int i = 0; i < nbPatterns; i++) {
            for (int j = 0; j < nbShapes; j++) {
                solution[i][j] = patterns[i].getPattern().get(j).getNumber();
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

        solutionCalc.addShapesForAllPattern(solution);

        solutionCalc.addOrder(order);
        double[] res = solutionCalc.getSolution().getPoint();
        for (int i = 0; i < res.length; i++) {
            // Will be our fitness
            if(res[i]>=0){
                //TODO réfléchir à l'arrondi
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
     * @return
     */
    public boolean isPackable(){
        boolean res = true;
        int i = 0;
        while (res && i < this.patterns.length) {
            res = res && this.patterns[i].isPossible();
            i++;
        }
        return res;
    }

    /**
     * Return if the list of pattern has at least one shape
     *
     * @return True if correct, false otherwise
     */
    public boolean isPossible() {
        boolean atLeastOneShape = true;
        int i = 0;
        while (i < this.patterns.length) {
            // Parcours pour vérifier qu'une solution va faire apparaître chaque piéce au moins une fois
            int[] sumOfShapes = new int[this.solution[0].length];
            for (int j = 0; j < sumOfShapes.length; j++){
                sumOfShapes[j] = 0;
            }
            for (int j = 0; j < this.solution.length; j++){
                for (int k = 0; k < this.solution[j].length; k++){
                    sumOfShapes[k] += this.solution[j][k];
                }
            }
            for (int j = 0; j < sumOfShapes.length; j++){
                if(sumOfShapes[j] <= 0){
                    atLeastOneShape = false;
                }
            }
            i++;
        }
        return atLeastOneShape;
    }


    /**
     * Clone the object solution and all of its attibutes
     *
     * @return A clone of the current object
     */
    public Solution clone() {
        Solution newSolution = new Solution();

        newSolution.setCost(this.getCost());
        /*int[][] clone_solution = new int[this.getSolution().length][this.getSolution()[0].length];*/
        /*for (int i = 0; i < clone_solution.length; i++) {
            for (int j = 0; j < clone_solution[0].length; j++) {
                clone_solution[i][j] = this.getSolution()[i][j];
            }
        }*/
        int[][] clone_solution = this.getSolution().clone();
        for(int i = 0; i < clone_solution.length;i++){
            clone_solution[i] = this.getSolution()[i].clone();
        }

        newSolution.setSolution(clone_solution);
        newSolution.setOrder(this.getOrder().clone());
        newSolution.setPatterns(this.getPatterns().clone());

        return newSolution;
    }

    @Override
    public String toString() {
        String tabSolution = " [";
        for (int i = 0; i < solution.length; i++){
            for(int j = 0; j < solution[0].length; j++){
                tabSolution += solution[i][j] + " ";
            }
            tabSolution += ", ";
        }
        tabSolution += "]";
        return "Solution{" +
                "solution=" + tabSolution +
                ", cost=" + cost +
                ", order=" + Arrays.toString(order) +
                '}';
    }
}
