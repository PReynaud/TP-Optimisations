package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

/**
 * Created by Dimitri on 21/03/2015.
 */
public class LengthGuillotine implements Guillotine {

    /**
     * {@inheritDoc}
     */
    public void cut(Sheet sheet, int index, Shape shape) {
        PlacedShape bin = sheet.getBins().remove(index);
        if (shape.isLying()&&bin.isLying()){
            sheet.getBins().add(new PlacedShape(bin.getLength()-shape.getLength(),shape.getBreadth(), bin.getPositionx()+shape.getLength(), bin.getPositiony()));
            sheet.getBins().add(new PlacedShape(bin.getLength(),bin.getBreadth()-shape.getBreadth(),bin.getPositionx(),bin.getPositiony()+shape.getBreadth()));
        }
        else if (!shape.isLying()&&bin.isLying()){
            sheet.getBins().add(new PlacedShape(bin.getLength()-shape.getBreadth(),shape.getLength(),bin.getPositionx()+shape.getBreadth(),bin.getPositiony()));
            sheet.getBins().add(new PlacedShape(bin.getLength(),bin.getBreadth()-shape.getLength(), bin.getPositionx(), bin.getPositiony()+shape.getLength()));
        }
        else if (shape.isLying()){
            sheet.getBins().add(new PlacedShape(bin.getBreadth()-shape.getLength(),shape.getBreadth(), bin.getPositionx()+shape.getLength(), bin.getPositiony()));
            sheet.getBins().add(new PlacedShape(bin.getBreadth(),bin.getLength()-shape.getBreadth(),bin.getPositionx(),bin.getPositiony()+shape.getBreadth()));
        }
        else{
            sheet.getBins().add(new PlacedShape(bin.getBreadth()-shape.getBreadth(),shape.getLength(),bin.getPositionx()+shape.getBreadth(),bin.getPositiony()));
            sheet.getBins().add(new PlacedShape(bin.getBreadth(),bin.getLength()-shape.getLength(), bin.getPositionx(), bin.getPositiony()+shape.getLength()));
        }
    }
}
