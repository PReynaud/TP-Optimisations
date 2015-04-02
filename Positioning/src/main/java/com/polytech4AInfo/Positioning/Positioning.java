package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Dimitri on 15/03/2015.
 */
public abstract class Positioning {

    protected abstract void sortShapes(ArrayList<? extends Shape> shapes);

    protected abstract void guillotine(Sheet sheet, int index, Shape shape);

    public boolean isPossible(Sheet sheet, Hashtable<PlacedShape,Integer> pattern) {
        Enumeration<PlacedShape> enumeration= pattern.keys();
        ArrayList<PlacedShape> shapes = new ArrayList<PlacedShape>();
        int i;
        while(enumeration.hasMoreElements()){
            shapes.add(enumeration.nextElement());
        }

        sortShapes(shapes);
        for(PlacedShape shape: shapes) {
            System.out.println(pattern.contains(shape));
            for (int j=0; j<pattern.get(shape);j++) {
                i = put(shape, sheet);
                if (i < 0) return false;
                guillotine(sheet, i, shape);
            }
        }
        return true;
    }

    private int putShape (PlacedShape shape, Sheet sheet){
        for (PlacedShape bin: sheet.getBins()){
            if (shape.isLying() && shape.getBreadth() <= bin.getBreadth() && shape.getLength() <= bin.getLength()){
                shape.setPositionx(bin.getPositionx());
                shape.setPositiony(bin.getPositiony());
                sheet.getContent().add(shape);
                return sheet.getBins().indexOf(bin);
            }
            else if (!shape.isLying() && shape.getLength()<= bin.getBreadth() && shape.getBreadth()<= bin.getLength()) {
                shape.setPositionx(bin.getPositionx());
                sheet.getContent().add(shape);
                return sheet.getBins().indexOf(bin);
            }
        }
        return -1;
    }

    protected int put(PlacedShape shape, Sheet sheet) {
        int res = putShape(shape, sheet);
        if (res>=0) return res;
        shape.turnShape();
        return putShape(shape,sheet);
    }
}
