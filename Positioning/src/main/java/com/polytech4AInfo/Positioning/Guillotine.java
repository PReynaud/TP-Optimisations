package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

/**
 * Created by Dimitri on 21/03/2015.
 */
public interface Guillotine {
    void cut(Sheet sheet, int index, Shape shape);
}
