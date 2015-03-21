package com.polytech4AInfo.Shape;

import java.util.Comparator;

/**
 * Created by Pierre on 13/03/2015.
 */
public class Shape implements Comparable<Shape> {
    /**
     * Side of the shape which is the most large
     */
    protected int length;

    /**
     * Side of the shape which is the smallest
     */
    protected int breadth;

    /**
     * Boolean to indicate in which orientation the shape is placed
     */
    protected boolean isLying;

    public Shape(int width, int breadth) {
        if (width>= breadth){
            this.length = width;
            this.breadth = breadth;
        }else {
            this.length = breadth;
            this.breadth = width;
        }
        this.isLying = false;
    }

    /**
     * Getter of length
     * @return The longest side
     */
    public int getLength() {
        return length;
    }

    /**
     * Getter of breadth
     * @return The shortest side
     */
    public int getBreadth() {
        return breadth;
    }

    /**
     * Get the ratio of the shape
     * @return The ratio between the breadth and the length of the shape
     */
    public double getRatio() {
        return (double)breadth/(double)length;
    }

    /**
     * Get the area of the shape
     * @return The area of the shape
     */
    public int getArea(){
        return breadth*length;
    }

    /**
     * Will do the rotation of the shape to be able to place it
     */
    public void turnShape (){
        isLying = !isLying;
    }

    @Override
    public int compareTo(Shape o) {
        return Comparators.AREA.compare(this, o);
    }

    public static class Comparators {

        public static Comparator<Shape> AREA = new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                return o1.breadth * o1.length - o2.breadth * o2.length;
            }
        };
        public static Comparator<Shape> LENGTH = new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                return o1.length - o2.length;
            }
        };
        public static Comparator<Shape> BREADTH = new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                return o1.breadth - o2.breadth;
            }
        };
        public static Comparator<Shape> RATIO = new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                return (int) (o1.getRatio() - o2.getRatio());
            }
        };
    }
}
