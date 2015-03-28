package com.polytech4AInfo.Positioning.Pattern;

import com.polytech4AInfo.Positioning.Positioning;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

import java.util.Hashtable;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class Pattern {
    private Hashtable<Shape,Integer> pattern;

    public Pattern(Hashtable<Shape,Integer> pattern) {
        this.pattern = pattern;
    }

    public boolean isPossible(Positioning positioning,Sheet sheet) {
        return positioning.isPossible(sheet,pattern);
    }
}
