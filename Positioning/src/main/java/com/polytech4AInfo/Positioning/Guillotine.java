package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

/**
 * Created by Dimitri on 21/03/2015.
 */
public interface Guillotine {
    /**
     * Cut the index bin of the sheet into 2 free bins.
     * @param sheet content for the shapes
     * @param index position of the bin to place the shape in
     * @param shape Shape to put on the sheet
     */
    void cut(Sheet sheet, int index, Shape shape);
}
