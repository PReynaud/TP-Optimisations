package com.polytech4AInfo.Positioning;

import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Shape;
import com.polytech4AInfo.Shape.Sheet;

/**
 * Created by Dimitri on 21/03/2015.
 */
public class RatioGuillotine implements Guillotine {

    /**
     * {@inheritDoc}
     */
    public void cut(Sheet sheet, int index, Shape shape) {
        //TODO: Remake it
        PlacedShape bin = sheet.getBins().remove(index),
                bin1 =new PlacedShape(bin.getLength()-shape.getLength(),bin.getBreadth(),bin.getPositionx()+shape.getLength(),bin.getPositiony()),
                bin2 =new PlacedShape(shape.getLength(),bin.getBreadth()-shape.getBreadth(), bin.getPositionx(), bin.getPositiony()+shape.getBreadth()),
                bin3 =new PlacedShape(bin.getLength()-shape.getLength(),shape.getBreadth(),bin.getPositionx()+shape.getLength(),bin.getPositiony()),
                bin4 =new PlacedShape(bin.getLength(),bin.getBreadth()-shape.getBreadth(), bin.getPositionx(), bin.getPositiony()+shape.getBreadth());

        if ((bin1.getRatio() +bin2.getRatio())/2 <= (bin3.getRatio()+bin4.getRatio())/2){
            if(bin1.getArea()<=bin2.getArea()){
                sheet.getBins().add(bin1);
                sheet.getBins().add(bin2);
            }else{
                sheet.getBins().add(bin2);
                sheet.getBins().add(bin1);
            }
        }else{
            if(bin3.getArea()<=bin4.getArea()){
                sheet.getBins().add(bin3);
                sheet.getBins().add(bin4);
            }else{
                sheet.getBins().add(bin4);
                sheet.getBins().add(bin3);
            }
        }
    }
}
