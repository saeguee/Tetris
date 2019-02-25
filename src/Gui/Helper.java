package Gui;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Helper {
    private BorderPane helperBorad;
    private Image helperContent;
    private ImageView helperContentView;
    private Button exitButton;
    private static Helper helper = new Helper();
    private Stage temp = new Stage();
    private Helper(){initialize();}
    
    public static Helper getInstance(){return helper;}
    
    public void initialize(){
        helperBorad = new BorderPane();
        helperBorad.setMinHeight(50);
        helperBorad.setMinWidth(50);

        helperContent = new Image("/Gui/star.jpg");
        helperContentView = new ImageView(helperContent);
        helperBorad.setCenter(helperContentView);

        exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(e -> {temp.hide(); Menu.getInstance().showMenuPane();});
        temp.setScene(new Scene(helperBorad));
        helperBorad.setBottom(exitButton);
    }

    public void showHelperBorad() {
        temp.show();
    }
}
 