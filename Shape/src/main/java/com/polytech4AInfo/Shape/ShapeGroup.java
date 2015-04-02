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

    @Override
    public String toString(){
        return super.toString() + " " + this.number;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof ShapeGroup){
            ShapeGroup s = (ShapeGroup) o;
            if(super.equals(o) && this.number == s.number){
                return true;
            }
        }
        return false;
    }
}
