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

    public boolean isLying() {
        return isLying;
    }

    /**
     * Boolean to indicate in which orientation the shape is placed
     */
    protected boolean isLying;

    public Shape(int length, int breadth) {
        if (length>= breadth){
            this.length = length;
            this.breadth = breadth;
            this.isLying = true;
        }else {
            this.length = breadth;
            this.breadth = length;
            this.isLying = false;
        }
    }

    public Shape(int length, int breadth, boolean isLying) {
        if (length>= breadth){
            this.length = length;
            this.breadth = breadth;
        }else {
            this.length = breadth;
            this.breadth = length;
        }
        this.isLying=isLying;
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

    public int compareTo(Shape o) {
        return Comparators.AREA.compare(this, o);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Shape){
            Shape s = (Shape) o;
            if(s.getLength() == this.getLength()
                    && s.getBreadth() == this.getBreadth()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return this.length + " " +  this.breadth;
    }

    public static class Comparators {

        public static Comparator<Shape> AREA = new Comparator<Shape>() {
            public int compare(Shape o1, Shape o2) {
                return o2.breadth * o2.length - o1.breadth * o1.length;
            }
        };
        public static Comparator<Shape> LENGTH = new Comparator<Shape>() {
            public int compare(Shape o1, Shape o2) {
                return o2.length-o1.length;
            }
        };
        public static Comparator<Shape> BREADTH = new Comparator<Shape>() {
            public int compare(Shape o1, Shape o2) {
                return o2.breadth - o1.breadth;
            }
        };
        public static Comparator<Shape> RATIO = new Comparator<Shape>() {
            public int compare(Shape o1, Shape o2) {
                return (int) (o2.getRatio()-o1.getRatio());
            }
        };
    }
}
