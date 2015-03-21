package com.polytech4AInfo.Shape;

import java.util.Comparator;

/**
 * Created by Pierre on 13/03/2015.
 */
public class Shape implements Comparable<Shape> {
    protected int length;
    protected int breadth;
    protected boolean isLying;

    public Shape(int height, int breadth) {
        if (height>= breadth){
            this.length = height;
            this.breadth = breadth;
        }else {
            this.length = breadth;
            this.breadth = height;
        }
        this.isLying = false;
    }

    public int getLength() {
        return length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void turnShape (){
        int tamp = length;
        length = breadth;
        breadth = tamp;
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
                return (int) (o1.breadth /o1.length - o2.breadth /o2.length);
            }
        };
    }
}
