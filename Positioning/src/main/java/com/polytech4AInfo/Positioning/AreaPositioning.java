package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class AreaPositioning extends Positioning {
    @Override
    protected void sortShapes(ArrayList<? extends Shape> shapes) {
        shapes.sort(Shape.Comparators.AREA);
    }

    @Override
    protected void guillotine (Sheet sheet, int index, Shape shape){
        new RatioGuillotine().cut(sheet,index,shape);
    }
}
