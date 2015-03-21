package com.polytech4AInfo;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 15/03/2015.
 */
public abstract class Positioning {

    protected Sheet sheet;

    public Positioning(Sheet sheet) {
        this.sheet = sheet;
    }

    protected abstract void sortShapes(ArrayList<Shape> shapes);

    protected abstract void guillotine(int index, Shape shape);

    public abstract int isPossible(ArrayList<Integer> pattern);

    /**
     * Tries to put a shape into sheet
     * @param shape Shape to put into sheet
     * @return true if the shape can be put in
     */
    public int put(PlacedShape shape){
        int res = sheet.putShape(shape);
        if (res>=0)
            return res;
        else{
            shape.turnShape();
            return sheet.putShape(shape);
        }
    }
}
