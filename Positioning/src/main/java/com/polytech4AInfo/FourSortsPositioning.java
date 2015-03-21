package com.polytech4AInfo;

import com.polytech4AInfo.Shape.PlacedShape;
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
        //TODO : Split into threads
        shapes.sort(Shape.Comparators.AREA);
        shapes.sort(Shape.Comparators.BREADTH);
        shapes.sort(Shape.Comparators.LENGTH);
        shapes.sort(Shape.Comparators.RATIO);
    }

    @Override
    protected void guillotine (int index, Shape shape){
        PlacedShape bin = sheet.getBins().remove(index);
        sheet.getBins().add(new PlacedShape(sheet.getLength()-bin.getLength(),bin.getBreadth(),bin.getPositionx()+bin.getLength(),bin.getPositiony()));
        sheet.getBins().add(new PlacedShape(sheet.getLength()-bin.getLength(),bin.getBreadth(),bin.getPositionx(),bin.getPositiony()+bin.getPositiony()));
    }

    @Override
    public int isPossible(ArrayList<Integer> pattern) {
        return 0;
    }
}
