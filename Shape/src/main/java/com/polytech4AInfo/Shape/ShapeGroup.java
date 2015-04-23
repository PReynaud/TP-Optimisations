package com.polytech4AInfo.Shape;

/**
 * Created by Dimitri on 02/04/2015.
 */
public class ShapeGroup extends PlacedShape {
    private int number;

    public ShapeGroup(int height, int width, int number) {
        super(height, width);
        this.number = number;
    }

    public ShapeGroup(int height, int width, int x, int y, int number, boolean isLying) {
        super(height, width, x, y, isLying);
        this.number=number;
    }

    public ShapeGroup(PlacedShape shape, int number) {
        super(shape);
        this.number=number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public ShapeGroup clone(){
        return new ShapeGroup(length, breadth, this.getPositionx(), this.getPositiony(), number, this.isLying);
    }
}
