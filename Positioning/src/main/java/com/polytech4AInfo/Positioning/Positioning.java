package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class Positioning {
    private Comparator<Shape> comparator;
    private Guillotine guillotine;

    public Positioning (Guillotine guillotine,Comparator<Shape> comparator){
        this.guillotine = guillotine;
        this.comparator = comparator;
    }

    protected void guillotine(Sheet sheet, int index, Shape shape){
        guillotine.cut(sheet, index, shape);
    }

    public boolean isPossible(Sheet sheet, ArrayList<ShapeGroup> pattern) {
        int i;
        pattern.sort((Comparator)comparator);
        for(ShapeGroup shape: pattern) {
            for (int j=0; j<shape.getNumber();j++) {
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
