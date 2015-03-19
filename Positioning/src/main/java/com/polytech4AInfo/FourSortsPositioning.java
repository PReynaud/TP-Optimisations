package com.polytech4AInfo;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class FourSortsPositioning extends Positioning {

    public FourSortsPositioning(Sheet sheet, ArrayList<Shape> shapes){
        super(sheet);
        sortShapes(shapes);
    }

    @Override
    protected void sortShapes(ArrayList<Shape> shapes) {
        shapes.sort(Shape.Comparators.AREA);
        shapes.sort(Shape.Comparators.BREADTH);
        shapes.sort(Shape.Comparators.LENGHT);
        shapes.sort(Shape.Comparators.RATIO);
    }

    @Override
    public int isPossible(ArrayList<Integer> pattern) {
        return 0;
    }
}
