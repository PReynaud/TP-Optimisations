package com.polytech4AInfo.Shape;

/**
 * Created by Dimitri on 02/04/2015.
 */
public class ShapeGroup extends PlacedShape {
    int number;

    public ShapeGroup(int height, int width, int number) {
        super(height, width);
        this.number = number;
    }

    public ShapeGroup(int height, int width, int x, int y, int number) {
        super(height, width, x, y);
        this.number=number;
    }
}
