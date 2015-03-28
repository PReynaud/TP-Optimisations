package com.polytech4AInfo.Shape;

import java.util.Comparator;

/**
 * Created by Pierre on 13/03/2015.
 */
public class Shape implements Comparable<Shape> {
    private int height;
    private int width;
    private int positionx;
    private int positiony;

    public Shape(int height, int width, int x, int y) {
        this.height = height;
        this.width = width;
        this.positionx = x;
        this.positiony = y;
    }

    public Shape(int height, int width) {
        this.height = height;
        this.width = width;
        this.positionx = 0;
        this.positiony = 0;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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
    public int compareTo(Shape o) {
        return Comparators.AREA.compare(this, o);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Shape){
            Shape s = (Shape) o;
            if(s.getWidth() == this.getWidth()
                    && s.getHeight() == this.getHeight()
                    && s.getPositionx() == this.getPositionx()
                    && s.getPositiony() == this.getPositiony()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return this.getPositionx() + " " +  this.getPositiony() + " " + this.getHeight() + " " + this.getWidth();
    }

    public static class Comparators {

        public static Comparator<Shape> AREA = new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                return o1.getWidth() * o1.getHeight() - o2.getWidth() * o2.getHeight();
            }
        };
        public static Comparator<Shape> LENGHT = new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                return Integer.max(o1.getWidth(), o1.getHeight()) - Integer.max(o2.getWidth(), o2.getHeight());
            }
        };
        public static Comparator<Shape> BREADTH = new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                return Integer.min(o1.getWidth(), o1.getHeight()) - Integer.min(o2.getWidth(), o2.getHeight());
            }
        };
        public static Comparator<Shape> RATIO = new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                float r1 = Integer.max(o1.getWidth(), o1.getHeight()) / Integer.min(o1.getWidth(), o1.getHeight()),
                        r2 = Integer.max(o2.getWidth(), o2.getHeight()) / Integer.min(o2.getWidth(), o2.getHeight());
                return (int) (r1 - r2);
            }
        };
    }
}
