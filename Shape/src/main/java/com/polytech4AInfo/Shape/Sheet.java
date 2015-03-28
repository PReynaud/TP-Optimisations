package com.polytech4AInfo.Shape;

import java.util.ArrayList;

/**
 * Created by Dimitri on 16/03/2015.
 */
public class Sheet extends Shape {
    private ArrayList<PlacedShape> content;
    private ArrayList<PlacedShape> bins;

    public ArrayList<PlacedShape> getContent() {
        return content;
    }

    public ArrayList<PlacedShape> getBins() {
        return bins;
    }

    public Sheet(int height, int width) {
        super(height, width);
        content = new ArrayList<PlacedShape>();
        bins = new ArrayList<PlacedShape>();
        bins.add(new PlacedShape(height, width));
    }
}
