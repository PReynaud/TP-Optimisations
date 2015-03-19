package com.polytech4AInfo;

import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.optimization.linear.SimplexSolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Pierre on 13/03/2015.
 */

public class Distribution {
    int nbPatterns;
    int nbShapes;
    int[] order;
    int[][] shapesOnPatterns;

    public Distribution(int nbPatterns, int nbShapes) {
        this.nbPatterns = nbPatterns;
        this.nbShapes = nbShapes;
        this.shapesOnPatterns = new int[nbPatterns][];
    }

    public boolean addOrder(int[] order){
        if(order.length == nbShapes){
            this.order = order;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean addShapesForOnePattern(int indexOfPattern, int[] shapesOnPattern){
        if(shapesOnPattern.length == nbShapes){
            if(indexOfPattern >= 0 && indexOfPattern < nbPatterns){
                shapesOnPatterns[indexOfPattern] = shapesOnPattern;
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
            e.printStackTrace();
        }

        return solution;
    }
}

