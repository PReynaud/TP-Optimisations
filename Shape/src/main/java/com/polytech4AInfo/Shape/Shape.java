package com.polytech4AInfo.Shape;

import java.util.Comparator;

/**
 * Created by Pierre on 13/03/2015.
 */
public class Shape implements Comparable<Shape> {
    private int height;
    private int width;

    public Shape(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int compareTo(Shape o) {
        return Comparators.AREA.compare(this, o);
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
