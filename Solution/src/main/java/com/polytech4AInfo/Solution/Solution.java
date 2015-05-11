package com.polytech4AInfo.Solution;

import com.polytech4AInfo.Positioning.Distribution;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;
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

        solutionArray = transformPatternArrayInSolutionArray(patterns);
    }

    /**
     * Transform a pattern array in solution array which is more practical for the algorithm
     * @param patterns
     * @return
     */
    private int[][] transformPatternArrayInSolutionArray(Pattern[] patterns){
        int[][] tempTab;
        int nbPatterns = patterns.length;
        int nbShapes = patterns[0].getNumberOfShapes();
        tempTab = new int[nbPatterns][nbShapes];
        for (int i = 0; i < nbPatterns; i++) {
            for (int j = 0; j < nbShapes; j++) {
                tempTab[i][j] = patterns[i].getPattern().get(j).getNumber();
            }
        }
        return tempTab;
    }

    public void transformPatternArrayInSolutionArray(){
        this.solutionArray = this.transformPatternArrayInSolutionArray(this.patterns);
    }

    public Solution() {
    }

    public boolean verifyPatternArrayAndSolutionArray(){
        int nbPatterns = patterns.length;
        int nbShapes = patterns[0].getNumberOfShapes();
        for (int i = 0; i < nbPatterns; i++) {
            for (int j = 0; j < nbShapes; j++) {
                if(patterns[i].getPattern().get(j).getNumber() != solutionArray[i][j]){
                    return false;
                }
            }
        }
        return true;
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

        if(cost == 0){
            throw new Exception("Error in the calcul of the cost, the solution is probably not valid");
        }
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
        newSolution.setOrder(this.getOrder().clone());

        int[][] clone_solution = new int[this.getSolutionArray().length][this.getSolutionArray()[0].length];
        for(int i = 0; i < this.getSolutionArray().length; i++){
            for(int j = 0; j < this.getSolutionArray()[0].length; j++){
                clone_solution[i][j] = this.getSolutionArray()[i][j];
            }
        }
        newSolution.setSolutionArray(clone_solution);

        Pattern[] clonePattern = new Pattern[this.getPatterns().length];
        for(int i = 0; i < this.getPatterns().length; i++){
            clonePattern[i] = this.getPatterns()[i].clone();
        }
        newSolution.setPatterns(clonePattern);

        return newSolution;
    }

    /**
     * Add one pattern with a random value
     * @return true if the insertion has been done correctly
     */
    public boolean addOnePattern(){
        //We clone the tab of patterns
        Pattern[] newPatternArray = new Pattern[patterns.length +1];
        for(int i = 0; i < patterns.length; i++){
            newPatternArray[i] = patterns[i];
        }
        //We add a new pattern and clone every shape in the new one
        ArrayList<ShapeGroup> listOfShapes = new ArrayList<ShapeGroup>();
        for(ShapeGroup newShapeGroup: patterns[0].getPattern()){
            ShapeGroup tempShapeGroup = newShapeGroup.clone();
            tempShapeGroup.setNumber((int)Math.round(Math.random()));
            listOfShapes.add(tempShapeGroup);
        }
        Sheet newSheet = new Sheet(patterns[0].getSheet().getLength(), patterns[0].getSheet().getBreadth());
        newPatternArray[newPatternArray.length - 1] = new Pattern(listOfShapes, newSheet);
        patterns = newPatternArray;

        //We update the solution array
        solutionArray = transformPatternArrayInSolutionArray(patterns);

        return true;
    }

    /**
     * Remove one pattern from the list of patterns
     * @return true if the deletion has been done correctly
     */
    public boolean removeOnePattern(int index){
        if(index < 0 || index >= patterns.length){
            return false;
        }

        //We clone the tab of patterns
        Pattern[] newPatternArray = new Pattern[patterns.length - 1];
        for(int i = 0; i < patterns.length; i++){
            if(i < index){
                newPatternArray[i] = patterns[i];
            }
            if(i > index){
                newPatternArray[i - 1] = patterns[i];
            }
        }
        patterns = newPatternArray;

        //We update the solution array
        solutionArray = transformPatternArrayInSolutionArray(patterns);

        return true;
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

    /**
     * Save all the patterns of the solution in png files
     */
    public void saveSolutionInFiles(){
        new ToImg().save("/Images/",this);
    }

    public ArrayList<Integer> getFillingRatio(){
        ArrayList<Integer> res = new ArrayList<>();
        for (int i=0; i<patterns.length; i++){
            res.add((int)patterns[i].getFillingRatio());
        }
        return res;
    }
}
