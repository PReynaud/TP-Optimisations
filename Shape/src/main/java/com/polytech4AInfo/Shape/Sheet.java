package com.polytech4AInfo.Shape;

import java.util.ArrayList;

/**
 * Created by Dimitri on 16/03/2015.
 */
public class Sheet{
    /**
     * Longest side of the sheet
     */
    protected int length;

    /**
     * Shortest side of the sheet
     */
    protected int breadth;

    /**
     * List of the different shape which has been placed in the sheet
     */
    private ArrayList<PlacedShape> content;

    /**
     * List of the bins which are contained into the sheet
     */
    private ArrayList<PlacedShape> bins;

    /**
     * Constructor of the class, a first bin is also created wich is initialized at the size of the sheet
     * @param length Longest side of the sheet
     * @param breadth Shortest side of the sheet
     */
    public Sheet(int length, int breadth) {
        if(length > breadth){
            this.length = length;
            this.breadth = breadth;
        }
        else{
            this.length = breadth;
            this.breadth = length;
        }

        this.content = new ArrayList<PlacedShape>();
        this.bins = new ArrayList<PlacedShape>();
        this.bins.add(new PlacedShape(length, breadth));
    }

    public ArrayList<PlacedShape> getContent() {
        return content;
    }

    public ArrayList<PlacedShape> getBins() {
        return bins;
    }

    public int getLength() {
        return length;
    }

    public int getBreadth() {
        return breadth;
    }

    public int putShape(PlacedShape shape) {
        for (PlacedShape bin: bins){
            if (shape.isLying && shape.breadth <= bin.breadth && shape.length <= bin.length){
                shape.setPositionx(bin.getPositionx());
                shape.setPositiony(bin.getPositiony());
                content.add(shape);
                return bins.indexOf(bin);
            }
            else if (!shape.isLying && shape.getLength()<= bin.getBreadth() && shape.getBreadth()<= bin.getLength()) {
                shape.setPositionx(bin.getPositionx());
                content.add(shape);
                return bins.indexOf(bin);
            }
        }
        return -1;
    }
}
