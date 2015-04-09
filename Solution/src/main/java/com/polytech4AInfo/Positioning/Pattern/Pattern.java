package com.polytech4AInfo.Positioning.Pattern;

import com.polytech4AInfo.Positioning.Positioning;
import com.polytech4AInfo.Positioning.PositioningStream;
import com.polytech4AInfo.Shape.ShapeGroup;
import com.polytech4AInfo.Shape.Sheet;

import java.util.ArrayList;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class Pattern {
    private ArrayList<ShapeGroup> pattern;

    public Pattern(ArrayList<ShapeGroup> pattern) {
        this.pattern = pattern;
    }

    public boolean isPossible(PositioningStream positioning,Sheet sheet) {
        return positioning.isPossible(sheet,pattern);
    }
}
