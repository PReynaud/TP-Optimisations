package com.polytech4AInfo.Positioning;

import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.optimization.linear.SimplexSolver;

import java.util.ArrayList;

/**
 * Created by Pierre on 13/03/2015.
 */

/**
 * Class which will resolve the linear equation of the number of sheets we have to print for the less cost
 */
public class Distribution {
    /**
     * Number of different patterns we have to tests
     */
    int nbPatterns;

    /**
     * Number of different shapes
     */
    int nbShapes;

    /**
     * It corresponds to the requested order of the shapes. We expect the solution for each shapes to be greater or
     * equal to that
     */
    int[] order;

    /**
     * Matrix of the number of shapes for each patterns
     * First dimension is the different patterns, second is for the shapes for one pattern
     */
    int[][] shapesOnPatterns;

    public Distribution(int nbPatterns, int nbShapes) {
        this.nbPatterns = nbPatterns;
        this.nbShapes = nbShapes;
        this.shapesOnPatterns = new int[nbPatterns][];
    }

    /**
     * Add an order before the resolution of the equation
     * @param order Array with values for each shapes we want
     * @return True if the order has been added correctly
     */
    public boolean addOrder(int[] order){
        if(order.length == nbShapes){
            this.order = order;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Add the number of shapes for one pattern
     * @param indexOfPattern The reference of the pattern in which we want to add the shapes
     * @param shapesOnPattern Array with values for each shapes we put on one pattern
     * @return True if the number of shapes for one pattern has been added successfully
     */
    public boolean addShapesForOnePattern(int indexOfPattern, int[] shapesOnPattern){
        if(shapesOnPattern.length == nbShapes){
            if(indexOfPattern >= 0 && indexOfPattern < nbPatterns){
                shapesOnPatterns[indexOfPattern] = shapesOnPattern.clone();
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * Add the number of shapes for all the patterns
     * @param shapesAllPattern Array with all the shapes for all the patterns
     * @return True if everything have been added correctly
     */
    public boolean addShapesForAllPattern(int[][] shapesAllPattern){
        if(shapesAllPattern.length == nbPatterns){
            if(shapesAllPattern[0].length == nbShapes){
                shapesOnPatterns = shapesAllPattern.clone();
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }


    /**
     * Will resolve the equation if all the parameter have been incorporated with success
     * @return The solution which can be accessed with solution.get()point[i] or null if there is no solution
     */
    public RealPointValuePair getSolution(){
        ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();

        for(int i = 0; i < nbShapes; i++){
            double[] temp = new double[nbPatterns];
            for(int j = 0; j < nbPatterns; j++){
                temp[j] = this.shapesOnPatterns[j][i];
            }
            LinearConstraint constraint = new LinearConstraint(temp, Relationship.GEQ, order[i]);
            constraints.add(constraint);
        }

        double[] objectiveToSolve = new double[nbPatterns];
        for(int i = 0; i < nbPatterns; i++){
            objectiveToSolve[i] = 1;
        }

        RealPointValuePair solution = null;
        LinearObjectiveFunction f = new LinearObjectiveFunction(objectiveToSolve, 0);
        try {
            solution = new SimplexSolver().optimize(f, constraints, GoalType.MINIMIZE, true);
        } catch (OptimizationException e) {
            return null;
        }

        return solution;
    }
}

