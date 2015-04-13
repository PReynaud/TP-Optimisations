package com.polytech4AInfo.Shape;

/**
 * Created by Dimitri on 19/03/2015.
 */
public class PlacedShape extends Shape {
    private int positionx;
    private int positiony;

    public PlacedShape(int height, int width) {
        super(height, width);
        positionx = 0;
        positiony = 0;
    }

    public PlacedShape(int height, int width, int x, int y) {
        super(height, width);
        this.positionx = x;
        this.positiony = y;
    }

    public PlacedShape(int height, int width, int x, int y,boolean isLying) {
        super(height, width,isLying);
        this.positionx = x;
        this.positiony = y;
    }

    public int getPositionx() {
        return positionx;
    }

    public int getPositiony() {
        return positiony;
    }

    public void setPositiony(int positiony) {
        this.positiony = positiony;
    }

    public void setPositionx(int positionx) {
        this.positionx = positionx;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PlacedShape) {
            PlacedShape s = (PlacedShape) o;
            if (super.equals(o)
                    && s.getPositionx() == this.getPositionx()
                    && s.getPositiony() == this.getPositiony()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.positionx + " " + this.positiony;
    }

    @Override
    public PlacedShape clone(){
        return new PlacedShape(this.length,this.getBreadth(), this.getPositionx(),this.getPositiony(),this.isLying);
    }
}
