package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 21/03/2015.
 */
public class LengthPositioning extends Positioning {

    @Override
    protected void sortShapes(ArrayList<? extends Shape> shapes) {
        shapes.sort(Shape.Comparators.LENGTH);
    }

    @Override
    protected void guillotine(Sheet sheet, int index, Shape shape) {
        new BreadthGuillotine().cut(sheet, index, shape);
    }
}
