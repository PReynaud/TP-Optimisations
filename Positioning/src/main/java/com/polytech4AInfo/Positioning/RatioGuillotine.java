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
        Sheet sheet1 = sheet.clone(), sheet2 = sheet.clone();
        new BreadthGuillotine().cut(sheet1,index,shape);
        new LengthGuillotine().cut(sheet2, index, shape);

        sheet.getBins().remove(index);
        PlacedShape bin1 = sheet1.getBins().get(0),
                bin2 = sheet1.getBins().get(1),
                bin3 = sheet2.getBins().get(0),
                bin4 =sheet2.getBins().get(1);

        if ((bin1.getRatio() +bin2.getRatio())/2 >= (bin3.getRatio()+bin4.getRatio())/2){
            if(bin1.getRatio()<=bin2.getRatio()){
                sheet.getBins().add(bin1);
                sheet.getBins().add(bin2);
            }else{
                sheet.getBins().add(bin2);
                sheet.getBins().add(bin1);
            }
        }else{
            if(bin3.getRatio()<=bin4.getRatio()){
                sheet.getBins().add(bin3);
                sheet.getBins().add(bin4);
            }else{
                sheet.getBins().add(bin4);
                sheet.getBins().add(bin3);
            }
        }
    }
}
