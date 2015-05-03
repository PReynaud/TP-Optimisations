package com.polytech4AInfo.Solution;

/**
 * Created by Pierre on 26/04/2015.
 */
public class Neighbour {

    /**
     * Will look for a neighbour and return it
     *
     * @param initialSolution
     * @return A clone of the initial solution but which has been modified
     */
    public static Solution findNeighbour(Solution initialSolution) {
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
                }
            }
            else{
                incrementSolutionForOneShape(currentSolution, numberOfTheInvalidShape);
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
     * Otherwise, we decrement the value of one random shape on one random pattern
     * @param currentSolution
     */
    private static void incrementOrDecrementSolutionRandomly(Solution currentSolution){
        double randomValue = Math.random();
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
