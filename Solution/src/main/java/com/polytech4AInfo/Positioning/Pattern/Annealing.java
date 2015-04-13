package com.polytech4AInfo.Positioning.Pattern;

/**
 * Created by benoitvuillemin on 10/04/2015.
 * RECUIT SIMULE
 */
public class Annealing {
    private static int LIMIT = 1000;
    private int counter;

    public void simulatedAnnealing(Solution s) {
        Solution s2 = findNeighbour(s);
    }

    private Solution findNeighbour(Solution s) {
        Solution s2;
        do {
            s2 = s;
            int a = (int) (Math.random() * (s.getSolution().length));
            int b = (int) (Math.random() * (s.getSolution()[a].length));
            s2.getSolution()[a][b] -= 1;
        }
        while(s2.isPossible());
        return s2;
    }

    private class Solution {
        int[][] solution;

        public Solution() {
        }

        public Solution(int[][] solution) {
            this.solution = solution;
        }

        public int[][] getSolution() {
            return solution;
        }

        public void setSolution(int[][] solution) {
            this.solution = solution;
        }
        public boolean isPossible(){
            return true;
        }
    }
}
