package com.polytech4AInfo.Positioning.Pattern;

import com.polytech4AInfo.Positioning.Positioning;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class Pattern {
    private ArrayList<ShapeGroup> pattern;

    public Pattern(ArrayList<ShapeGroup> pattern) {
        this.pattern = pattern;
    }

    public boolean isPossible(Positioning positioning,Sheet sheet) {
        return positioning.isPossible(sheet,pattern);
    }
}