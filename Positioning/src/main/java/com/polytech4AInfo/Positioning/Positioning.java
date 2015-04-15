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
    /**
     * Comparator used for the sorting of the positioning
     */
    private Comparator<Shape> comparator;

    /**
     * Method to cut the sheet into bins
     */
    private Guillotine guillotine;

    /**
     * Constructor
     * @param guillotine Guillotine of the positioning
     * @param comparator Comparator of the positioning
     */
    public Positioning (Guillotine guillotine,Comparator<Shape> comparator){
        this.guillotine = guillotine;
        this.comparator = comparator;
    }

    /**
     * Cut the index bin of the sheet where the shape will be placed into 2 free bins
     * @param sheet Content for the bins
     * @param index Index of the bin where the shape will be placed
     * @param shape Shape to put on the sheet
     */
    protected void guillotine(Sheet sheet, int index, Shape shape){
        guillotine.cut(sheet, index, shape);
    }

    /**
     * Tries to put the shapes of the pattern on the sheet
     * @param _sheet Content for the shapes
     * @param _pattern List of shapes to put on the sheet
     * @return true if the shapes can be put on the sheet
     */
    public boolean isPossible(Sheet _sheet, ArrayList<ShapeGroup> _pattern) {
        int i;
        Sheet sheet = _sheet.clone();
        ArrayList<ShapeGroup> pattern = new ArrayList<ShapeGroup>();
        for(ShapeGroup p : _pattern){
            pattern.add(p.clone());
        }

        pattern.sort((Comparator)comparator);
        for(ShapeGroup shape: pattern) {
            for (int j=0; j<shape.getNumber();j++) {
                i = put(shape, sheet);
                if (i < 0) return false;
                guillotine(sheet, i, shape);
            }
        }
        //ToImg img = new ToImg();
        //img.savePattern("/Tests/",sheet);
        return true;
    }

    /**
     * Tries to put a shape on the different bins of the sheet
     * without turning it
     * @param shape Shape to put on the sheet
     * @param sheet Content for the bins
     * @return Int : index of the shape if it cans be placed
     * on the sheet else return -1
     */
    private synchronized int putShape (PlacedShape shape, Sheet sheet){
        for (PlacedShape bin: sheet.getBins()){
            if (shape.isLying()==bin.isLying() && shape.getBreadth() <= bin.getBreadth() && shape.getLength() <= bin.getLength()){
                shape.setPositionx(bin.getPositionx());
                shape.setPositiony(bin.getPositiony());
                sheet.getContent().add(shape.clone());
                return sheet.getBins().indexOf(bin);
            }
            else if (shape.isLying()!=bin.isLying() && shape.getLength()<= bin.getBreadth() && shape.getBreadth()<= bin.getLength()) {
                shape.setPositionx(bin.getPositionx());
                shape.setPositiony(bin.getPositiony());
                sheet.getContent().add(shape.clone());
                return sheet.getBins().indexOf(bin);
            }
        }
        return -1;
    }


    /**
     * Tries to put a shape on a sheet
     * Turns it if not possible
     * @param shape Shape to put on the sheet
     * @param sheet Content for the bins
     * @return Int : index of the shape if it cans be placed
     * on the sheet else return -1
     */
    protected int put(PlacedShape shape, Sheet sheet) {
        int res = putShape(shape, sheet);
        if (res>=0) return res;
        shape.turnShape();
        return putShape(shape,sheet);
    }


}