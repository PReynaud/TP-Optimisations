package com.polytech4AInfo.Solution;

import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Pierre on 23/04/2015.
 */
public class FirstSolution {
    public static Solution generateFirstSolution(ContextUtils.Context file, int numberOfPatterns){
        Solution firstSolution;
        int[] order = new int[file.getPattern().length];
        Pattern[] listOfPatterns = new Pattern[numberOfPatterns];


        for(int i = 0; i < listOfPatterns.length; i++){
            ArrayList<ShapeGroup> listOfShapes = new ArrayList<ShapeGroup>(Arrays.asList(file.clone().getPattern()));

            for(int j = 0; j < listOfShapes.size(); j++){
                if(i == 0){
                    order[j] = listOfShapes.get(j).getNumber();
                }
                listOfShapes.get(j).setNumber((int)Math.round(Math.random()));
            }

            listOfPatterns[i] = new Pattern(listOfShapes, new Sheet(file.getLx(), file.getLy()));
        }

        firstSolution = new Solution(listOfPatterns, order);
        return firstSolution;
    }
}
