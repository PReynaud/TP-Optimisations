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
            System.out.println("findNeighbour");
            int numberOfTheInvalidShape = currentSolution.hasAtLeastOneShape();
            if(numberOfTheInvalidShape == -1){
                int numberOfTheInvalidPattern = currentSolution.isPackable();
                if(numberOfTheInvalidPattern == -1){
                    if(firstIteration){
                        incrementOrDecrementSolutionRandomly(currentSolution, 50);
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

    private static void incrementSolutionRandomly(Solution currentSolution){
        int randomNumberForPattern = (int) (Math.random() * (currentSolution.getSolutionArray().length));
        int randomNumberForShape = (int) (Math.random() * (currentSolution.getSolutionArray()[randomNumberForPattern].length));
        currentSolution.getSolutionArray()[randomNumberForPattern][randomNumberForShape] += 1;
    }

    private static void incrementSolutionForOneShape(Solution currentSolution, int numberOfTheShape){
        int randomNumberForPattern = (int) (Math.random() * (currentSolution.getSolutionArray().length));
        currentSolution.getSolutionArray()[randomNumberForPattern][numberOfTheShape] += 1;
    }

    private static void decrementSolutionRandomly(Solution currentSolution){
        boolean isModified = false;
        while(!isModified){
            int randomNumberForPattern = (int) (Math.random() * (currentSolution.getSolutionArray().length));
            int randomNumberForShape = (int) (Math.random() * (currentSolution.getSolutionArray()[randomNumberForPattern].length));
            if(currentSolution.getSolutionArray()[randomNumberForPattern][randomNumberForShape] > 0)
            {
                currentSolution.getSolutionArray()[randomNumberForPattern][randomNumberForShape] -= 1;
                isModified = true;
            }
        }
    }

    private static void decrementSolutionOnOnePattern(Solution currentSolution, int numberOfThePattern){
        boolean isModified = false;
        while(!isModified){
            int randomNumberForShape = (int) (Math.random() * (currentSolution.getSolutionArray()[numberOfThePattern].length));
            if(currentSolution.getSolutionArray()[numberOfThePattern][randomNumberForShape] > 0)
            {
                currentSolution.getSolutionArray()[numberOfThePattern][randomNumberForShape] -= 1;
                isModified = true;
            }
        }
    }

    private static void incrementOrDecrementSolutionRandomly(Solution currentSolution, int percentageOfIncrementation){
        double randomValue = Math.random();
        if(randomValue * 100 > percentageOfIncrementation){
            decrementSolutionRandomly(currentSolution);
        }
        else{
            incrementSolutionRandomly(currentSolution);
        }
    }
}
