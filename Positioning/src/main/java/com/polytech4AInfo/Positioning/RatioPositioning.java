package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 21/03/2015.
 */
public class RatioPositioning extends Positioning {
    @Override
    protected void sortShapes(ArrayList<? extends Shape> shapes) {
        shapes.sort(Shape.Comparators.RATIO);
    }

    @Override
    protected void guillotine(Sheet sheet,int index, Shape shape) {
        new RatioGuillotine().cut(sheet,index,shape);
    }
}
