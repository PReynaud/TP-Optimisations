package com.polytech4AInfo.Solution;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Pierre on 26/04/2015.
 */
public class Neighbour {
    private static Logger logger = Logger.getLogger(ProgramMain.class);

    public static Solution findNeighbour(Solution initialSolution){
        ArrayList<Solution> solutionsTab = new ArrayList<>();
        Solution finalSolution = null;

        double randomValue = Math.random();
        if(randomValue * 100 > ProgramMain.PERCENTAGE_OF_ADDING_A_PATTERN) {
            for (int i = 0; i < 10; i++) {
                solutionsTab.add(initialSolution);
            }

            try {
                finalSolution =
                        solutionsTab
                                .parallelStream()
                                .map(p -> {
                                    try {
                                        Solution tempNeighbour = findOneNeighbour(p);
                                        tempNeighbour.calculCost();
                                        return tempNeighbour;
                                    } catch (Exception e) {
                                        logger.error("Error in parallel streams for the search of a neighbour");
                                        return initialSolution;
                                    }
                                })
                                .min(Comparator.comparing(p -> p.getCost()))
                                .get();
            } catch (Exception e) {
                logger.error("Error in parallel streams for the search of a neighbour");
            }
        }
        else{
            finalSolution = initialSolution.clone();
            finalSolution.addOnePattern();
        }
        return finalSolution;
    }

    /**
     * Will look for a neighbour and return it
     *
     * @param initialSolution
     * @return A clone of the initial solution but which has been modified
     */
    public static Solution findOneNeighbour(Solution initialSolution) {
        boolean incorrectSolution = true;
        boolean firstIteration = true;
        Solution currentSolution = initialSolution.clone();
        do {
            int numberOfTheInvalidShape = currentSolution.hasAtLeastOneShape();
            if(numberOfTheInvalidShape == -1){
                int numberOfTheInvalidPattern = currentSolution.isPackable();
                if(numberOfTheInvalidPattern == -1){
                    if(firstIteration){
                        incrementOrDecrementSolutionRandomly(currentSolution);
                        firstIteration = false;
                    }
                    else{
                        return currentSolution;
                    }
                }
                else{
                    decrementSolutionOnOnePattern(currentSolution, numberOfTheInvalidPattern);
                    firstIteration = false;
                }
            }
            else{
                incrementSolutionForOneShape(currentSolution, numberOfTheInvalidShape);
                firstIteration = false;
            }
        }while (incorrectSolution);
        return currentSolution;
    }

    /**
     * Increment one random shape on one random pattern
     * @param currentSolution
     */
    private static void incrementSolutionRandomly(Solution currentSolution){
        int randomNumberForPattern = (int) (Math.random() * (currentSolution.getSolutionArray().length));
        int randomNumberForShape = (int) (Math.random() * (currentSolution.getSolutionArray()[0].length));
        currentSolution.getSolutionArray()[randomNumberForPattern][randomNumberForShape] += 1;
        currentSolution.getPatterns()[randomNumberForPattern].getPattern().get(randomNumberForShape)
                .setNumber(currentSolution.getSolutionArray()[randomNumberForPattern][randomNumberForShape]);
    }

    /**
     * Increment one random pattern for a given shape. Use if we need one patticular shape
     * @param currentSolution
     * @param numberOfTheShape
     */
    private static void incrementSolutionForOneShape(Solution currentSolution, int numberOfTheShape){
        double randomValue = Math.random();
        if(randomValue * 100 > ProgramMain.PERCENTAGE_OF_ADDING_A_PATTERN) {
            int randomNumberForPattern = (int) (Math.random() * (currentSolution.getSolutionArray().length));
            currentSolution.getSolutionArray()[randomNumberForPattern][numberOfTheShape] += 1;
            currentSolution.getPatterns()[randomNumberForPattern].getPattern().get(numberOfTheShape)
                    .setNumber(currentSolution.getSolutionArray()[randomNumberForPattern][numberOfTheShape]);
        }
        else{
            currentSolution.addOnePattern();
        }
    }

    /**
     * Decrement one random shape on one random pattern
     * If the selected shape is already to 0, we decrement an other one
     * After the decrementation we verify that the selected pattern is not empty, if it is, it's delete from the list
     * @param currentSolution
     */
    private static void decrementSolutionRandomly(Solution currentSolution){
        boolean isModified = false;
        double randomValue = Math.random();
        if(randomValue * 100 > ProgramMain.PERCENTAGE_OF_REMOVING_A_PATTERN) {
            while (!isModified) {
                int randomNumberForPattern = (int) (Math.random() * (currentSolution.getSolutionArray().length));
                int randomNumberForShape = (int) (Math.random() * (currentSolution.getSolutionArray()[0].length));
                if (currentSolution.getSolutionArray()[randomNumberForPattern][randomNumberForShape] > 0) {
                    currentSolution.getSolutionArray()[randomNumberForPattern][randomNumberForShape] -= 1;
                    currentSolution.getPatterns()[randomNumberForPattern].getPattern().get(randomNumberForShape)
                            .setNumber(currentSolution.getSolutionArray()[randomNumberForPattern][randomNumberForShape]);
                    isModified = true;

                    //Remove the pattern if it's empty
                    if (isEmptyPattern(currentSolution, randomNumberForPattern)) {
                        currentSolution.removeOnePattern(randomNumberForPattern);
                    }
                }
            }
        }
        else{
            int randomNumberForPattern = (int) (Math.random() * (currentSolution.getSolutionArray().length));
            currentSolution.removeOnePattern(randomNumberForPattern);
        }
    }

    /**
     * Decrement one random shape on the given pattern. Useful if we know that the shape can not be placed on the pattern.
     * After one shape has been deleted, we verify that the selected pattern is not null, if it is, it is deleted from the list
     * @param currentSolution
     * @param numberOfThePattern
     */
    private static void decrementSolutionOnOnePattern(Solution currentSolution, int numberOfThePattern){
        boolean isModified = false;

        while(!isModified){
            int randomNumberForShape = (int) (Math.random() * (currentSolution.getSolutionArray()[0].length));
            if(currentSolution.getSolutionArray()[numberOfThePattern][randomNumberForShape] > 0)
            {
                currentSolution.getSolutionArray()[numberOfThePattern][randomNumberForShape] -= 1;
                currentSolution.getPatterns()[numberOfThePattern].getPattern().get(randomNumberForShape)
                        .setNumber(currentSolution.getSolutionArray()[numberOfThePattern][randomNumberForShape]);
                isModified = true;
            }
        }
        //Remove the pattern if it's empty
        if(isEmptyPattern(currentSolution, numberOfThePattern)){
            currentSolution.removeOnePattern(numberOfThePattern);
        }
    }

    /**
     * Has a percentage of chance to add a new pattern
     * Has a percentage of chance to increment one random shape on one random pattern
     * Has a percentage of chance to invert two random shapes from two random patterns
     * Otherwise, we decrement the value of one random shape on one random pattern
     * @param currentSolution
     */
    private static void incrementOrDecrementSolutionRandomly(Solution currentSolution){
        /*double randomValue = Math.random();
        if(randomValue * 100 > ProgramMain.PERCENTAGE_OF_INVERTING_TWO_SHAPES){
            randomValue = Math.random();
            if(randomValue * 100 > ProgramMain.PERCENTAGE_OF_ADDING_A_PATTERN){
                randomValue = Math.random();
                if(randomValue * 100 > ProgramMain.PERCENTAGE_OF_INCREMENTATION){
                    decrementSolutionRandomly(currentSolution);
                }
                else{
                    incrementSolutionRandomly(currentSolution);
                }
            }
            else{
                currentSolution.addOnePattern();
            }
        }
        else{
            invertTwoShapesBetweenTwoPatterns(currentSolution);
        }*/

        double randomValue = Math.random();
        int randomOperation = (int) Math.random() * 3;
        if(randomOperation == 0){
            if(randomValue * 100 <= ProgramMain.PERCENTAGE_OF_INVERTING_TWO_SHAPES){
                invertTwoShapesBetweenTwoPatterns(currentSolution);
                return;
            }
        }
        else if(randomOperation == 1){
            if(randomValue * 100 <= ProgramMain.PERCENTAGE_OF_ADDING_A_PATTERN && currentSolution.getPatterns().length<currentSolution.getPatterns()[0].getNumberOfShapes()){
                currentSolution.addOnePattern();
                return;
            }
        }
        else if(randomOperation == 2){
            if(randomValue * 100 <= ProgramMain.PERCENTAGE_OF_REMOVING_A_PATTERN){
                int randomNumberForPattern = (int) (Math.random() * (currentSolution.getSolutionArray().length));
                currentSolution.removeOnePattern(randomNumberForPattern);
                return;
            }
        }

        randomValue = Math.random();
        if(randomValue * 100 > ProgramMain.PERCENTAGE_OF_INCREMENTATION){
            decrementSolutionRandomly(currentSolution);
        }
        else{
            incrementSolutionRandomly(currentSolution);
        }

    }

    /**
     * Intervert two random shapes from two random pattern
     * @param initialSolution
     */
    private static void invertTwoShapesBetweenTwoPatterns(Solution initialSolution){
        int randomNumberForFirstPattern = (int) (Math.random() * (initialSolution.getSolutionArray().length));
        int randomNumberForSecondPattern = (int) (Math.random() * (initialSolution.getSolutionArray().length));
        int randomNumberForFirstShape = (int) (Math.random() * (initialSolution.getSolutionArray()[0].length));
        int randomNumberForSecondShape = (int) (Math.random() * (initialSolution.getSolutionArray()[0].length));

        int buffer1 = initialSolution.getSolutionArray()[randomNumberForFirstPattern][randomNumberForFirstShape];
        int buffer2 = initialSolution.getSolutionArray()[randomNumberForSecondPattern][randomNumberForSecondShape];

        initialSolution.getSolutionArray()[randomNumberForFirstPattern][randomNumberForFirstShape] = buffer2;
        initialSolution.getPatterns()[randomNumberForFirstPattern].getPattern().get(randomNumberForFirstShape).setNumber(buffer2);
        initialSolution.getSolutionArray()[randomNumberForSecondPattern][randomNumberForSecondShape] = buffer1;
        initialSolution.getPatterns()[randomNumberForSecondPattern].getPattern().get(randomNumberForSecondShape).setNumber(buffer1);
    }

    /**
     * Verify if a pattern is empty or not
     * @param currentSolution
     * @param numberOfThePattern
     * @return
     */
    private static boolean isEmptyPattern(Solution currentSolution, int numberOfThePattern){
        int res = 0;
        for(int i = 0; i <currentSolution.getSolutionArray()[numberOfThePattern].length; i++){
            res += currentSolution.getSolutionArray()[numberOfThePattern][i];
        }
        return res == 0;
    }
}
