import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;
import com.polytech4AInfo.Solution.Annealing;
import com.polytech4AInfo.Solution.Pattern;
import com.polytech4AInfo.Solution.Solution;

import java.util.ArrayList;

/**
 * Created by Pierre on 13/04/2015.
 */
public class Main {
    public static void main(String args[]){
        System.out.println("Beginning of the program");

        ArrayList<ShapeGroup> pattern = new ArrayList<ShapeGroup>();
        pattern.add(new ShapeGroup(933,372,1));
        pattern.add(new ShapeGroup(700,333,1));
        pattern.add(new ShapeGroup(307,293,2));
        pattern.add(new ShapeGroup(307,100,5));
        pattern.add(new ShapeGroup(200, 50, 5));

        int[] order = {10, 4, 6, 8, 10};
        Pattern[] listOfPatterns = new Pattern[1];
        listOfPatterns[0] = new Pattern(pattern, new Sheet(1400, 700));

        Solution firstSolution = new Solution(listOfPatterns, order);

        Annealing algo = new Annealing();
        Solution finalSolution = algo.simulatedAnnealing(firstSolution, 1000.0);

        System.out.println(finalSolution);
        System.out.println("End of the program");
    }
}
