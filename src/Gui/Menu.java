package Gui;

import Logic.Game;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu{
    private FlowPane menuPane;
    private Button startButton;
    private Button exitButton;
    private Button leaderBoardButton;
    private Button helperButton;
    private Game game;
    private Stage temp = new Stage();
    private static Menu menu = new Menu();

    private Menu(){}

    public static Menu getInstance(){return menu;}

    public void initialize(){
        menuPane = new FlowPane(Orientation.VERTICAL,0,10);
        menuPane.setAlignment(Pos.CENTER);
        menuPane.setBackground(new Background(new BackgroundImage(new Image("/Gui/menubg.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(300, 300, true, true, true, true))));

        startButton = new Button("Start");
        startButton.setEffect(new DropShadow());
        exitButton = new Button("Exit");
        leaderBoardButton = new Button("LeaderBoard");
        helperButton = new Button("Help");
        helperButton.setVisible(false);
        startButton.setOnMouseClicked(e -> {newGamePaneSetting(); temp.hide();});
        exitButton.setOnMouseClicked(e -> {System.exit(-1); temp.hide();});
        leaderBoardButton.setOnMouseClicked(e -> {LeaderBoard.getInstance().showLeaderBoard(); temp.hide();});
        helperButton.setOnMouseClicked(e -> {Helper.getInstance().showHelperBorad(); temp.hide();});
        Label manipulateInfo = new Label("PRESS W(⬆) A(⬇) S(⬅) D(➡) \n TO MOVE THE BLOCKS");
        manipulateInfo.getStyleClass().add("bonusStyle");
        manipulateInfo.setStyle("-fx-background-color: #336699;");
        manipulateInfo.setTextFill(Color.WHITESMOKE);
        menuPane.getChildren().addAll(startButton, leaderBoardButton, exitButton, helperButton, manipulateInfo);
        temp.setScene(new Scene(menuPane));
        temp.setWidth(300);
        temp.setHeight(300);
        showMenuPane();
    }

    public void newGamePaneSetting(){
        game = Game.getInstance();
        game.initial(30, 15);
        temp.setTitle("TetrisJFX - User" + game.getUserState().getName() + "is playing ...");
        Controller.getInstance().restart();
    }

    public void showMenuPane(){
        temp.show();
    }
}
