package com.polytech4AInfo.Pattern;

import com.polytech4AInfo.Positioning;

import java.util.ArrayList;

/**
 * Created by Dimitri on 15/03/2015.
 */
public class Pattern {
    private ArrayList<Integer> pattern;

    public Pattern(ArrayList<Integer> pattern) {
        this.pattern = pattern;
    }

    public int isPossible(Positioning positioning) {
        return positioning.isPossible(pattern);
    }
}
