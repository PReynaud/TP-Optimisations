import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;
import com.polytech4AInfo.Solution.Pattern;
import com.polytech4AInfo.Solution.Solution;

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

        ArrayList<ShapeGroup> patterns = new ArrayList<ShapeGroup>(Arrays.asList(file.getPattern()));
        for(int i = 0; i < patterns.size(); i++){
            order[i] = patterns.get(i).getNumber();
            patterns.get(i).setNumber((int)Math.round(Math.random()));
            /*patterns.get(i).setNumber(1);*/
        }

        for(int i = 0; i < listOfPatterns.length; i++){
            listOfPatterns[i] = new Pattern(patterns, new Sheet(file.getLx(), file.getLy()));
        }

        firstSolution = new Solution(listOfPatterns, order);
        return firstSolution;
    }
}
