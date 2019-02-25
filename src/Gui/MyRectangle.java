package Gui;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MyRectangle extends Rectangle {

    public void superSetting(){
        super.setArcWidth(15);
        super.setArcHeight(15);
    }

    MyRectangle(double widAndHeight, Paint color){
        super(widAndHeight, widAndHeight, color);
        superSetting();
   }

    MyRectangle(double widAndHeight){
        super(widAndHeight, widAndHeight, Color.TRANSPARENT);
        superSetting();
   }
}
