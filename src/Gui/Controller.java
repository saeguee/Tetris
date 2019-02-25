package Gui;

import Logic.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Random;

public class Controller implements Serializable {
    private final int BLOCK_SIZE;
    private GridPane currentBlockPane;
    private GridPane fakeBlockPane;
    private GridPane nextBlockPane;
    private GridPane wallPane;
    private Pane PrimaryContainer;
    private ToggleButton pauseButton;
    private ToggleButton exitButton;
    private HBox scoreBoard;
    private HBox timeBoard;
    private final Label scoreNameLabel = new Label("Current Score ：");
    private Label scoreLabel;
    private Label timeLabel;
    private final Label nextBlockLabel = new Label("Shape of Next Block ：");;
    private Timeline animation;
    private Game game;
    private Random rand;
    private boolean [][] currentBlock;
    private boolean [][] nextBlock;
    private boolean [][] wall;
    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final static Effect glow = new Glow(0.6);
    private SimpleIntegerProperty score;
    private IntegerProperty time;
    private int time2;
    private Stage temp = new Stage();
    private Stage tempStage;
    private static Controller c = new Controller(20);
    public static Controller getInstance(){return c;}

    private Controller(int size){
        rand = new Random();
        BLOCK_SIZE = size;
        game = Game.getInstance();
        currentBlockPane = new GridPane();
        fakeBlockPane = new GridPane();
        nextBlockPane = new GridPane();
        wallPane = new GridPane();
        PrimaryContainer = new Pane();
        initialize();
    }

    public void restart(){
        time2 = 0;
        time.setValue(0);
        updateGame();
        animation.play();
        showPrimaryContainer();
    }

    private void updateGame(){
        updateCells();
        updatePanes();
        updatePos();
        score.setValue(game.getUserState().getScore());
    }

    private void updateCells(){
        currentBlock = game.getCurrentBlockCells();
        nextBlock = game.getNextBlockCells();
        wall = game.getWall();
    }

    private void updatePanes(){
        Paint randColor = getFillColor(rand.nextInt(7));
        clearPane();
        for(int i = 0; i < wall.length; i++)
            for(int j = 0; j < wall[0].length; j++)
                if (wall[i][j])
                    wallPane.add(new MyRectangle(BLOCK_SIZE,  Color.BLACK), j, i);
                else
                    wallPane.add(new MyRectangle(BLOCK_SIZE), j, i);
        for(int i = 0; i < currentBlock.length; i++)
            for(int j = 0; j < currentBlock[0].length; j++)
                if(currentBlock[i][j]){
                    currentBlockPane.add(new MyRectangle(BLOCK_SIZE, randColor), j, i);
                    fakeBlockPane.add(new MyRectangle(BLOCK_SIZE, Color.WHITESMOKE), j, i);
                }
                else{
                    currentBlockPane.add(new MyRectangle(BLOCK_SIZE), j, i);
                    fakeBlockPane.add(new MyRectangle(BLOCK_SIZE), j, i);
                }
        for(int i = 0; i < nextBlock.length; i++)
            for(int j = 0; j < nextBlock[0].length; j++)
                if (nextBlock[i][j])
                    nextBlockPane.add(new MyRectangle(BLOCK_SIZE, Color.WHITESMOKE), j, i);
                else
                    nextBlockPane.add(new MyRectangle(BLOCK_SIZE), j, i);
    }

    private void nextBlockPaneSetting(){
        nextBlockPane.setLayoutX(600);
        nextBlockPane.setLayoutY(150);

        nextBlockLabel.getStyleClass().add("bonusStyle");
        nextBlockLabel.setEffect(glow);
        nextBlockLabel.setStyle("-fx-background-color: #336699;");
        nextBlockLabel.setLayoutX(550);
        nextBlockLabel.setLayoutY(115);
        nextBlockLabel.setTextFill(Color.WHITESMOKE);

    }

    private void wallBlockPaneSetting(){
        wallPane.setLayoutY(BLOCK_SIZE*5);
        wallPane.setLayoutX(BLOCK_SIZE*5);
        wallPane.setBackground(new Background(new BackgroundImage(new Image("/Gui/background_image.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        wallPane.setStyle("-fx-border-color: red");
        wallPane.setPadding(new Insets(5.5,5.5,5.5,5.5));
    }

    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.GRAY;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.ORANGE;
                break;
            case 5:
                returnPaint = Color.PURPLE;
                break;
            case 6:
                returnPaint = Color.DARKCYAN;
                break;
            default:
                returnPaint = Color.HOTPINK;
                break;
        }
        return returnPaint;
    }

    private void updatePos(){
        currentBlockPane.setLayoutY(wallPane.getLayoutX() + game.getCurX()*BLOCK_SIZE);
        currentBlockPane.setLayoutX(wallPane.getLayoutY() + game.getCurY()*BLOCK_SIZE);
        fakeBlockPane.setLayoutY(wallPane.getLayoutX() + game.getFakeX()*BLOCK_SIZE);
        fakeBlockPane.setLayoutX(wallPane.getLayoutY() + game.getCurY()*BLOCK_SIZE);
    }

    private void clearPane(){
        wallPane.getChildren().clear();
        currentBlockPane.getChildren().clear();
        fakeBlockPane.getChildren().clear();
        nextBlockPane.getChildren().clear();
    }

    private void keyPressedSetting(){
        currentBlockPane.setFocusTraversable(true);
        currentBlockPane.requestFocus();
        currentBlockPane.setOnKeyPressed(e -> {
            if (!game.isOver()) {
                game.executeCommand(e.getCode().toString());
                updateGame();
                e.consume();
            }
        });
    }

    private void pauseSetting(){
        pauseButton = new ToggleButton("Pause");
        pauseButton.selectedProperty().bindBidirectional(isPause);
        pauseButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    animation.pause();
                    pauseButton.setText("Resume");
                } else {
                    animation.play();
                    pauseButton.setText("Pause");
                    currentBlockPane.setFocusTraversable(true);
                    currentBlockPane.requestFocus();
                }
            }
        });
        pauseButton.setLayoutX(550);
        pauseButton.setLayoutY(450);
        pauseButton.getStyleClass().add("bonusStyle");
        pauseButton.setEffect(glow);
        pauseButton.setStyle("-fx-background-color: #336699;");
        pauseButton.setTextFill(Color.WHITESMOKE);
    }

    private void exitSetting(){
        exitButton = new ToggleButton("Exit");
        exitButton.setOnMouseClicked(e -> {animation.pause(); tempStage.show();});
        exitButton.setLayoutX(550);
        exitButton.setLayoutY(550);
        exitButton.getStyleClass().add("bonusStyle");
        exitButton.setEffect(glow);
        exitButton.setStyle("-fx-background-color: #336699;");
        exitButton.setTextFill(Color.WHITESMOKE);
    }

    private void exitInfoSetting(){
        tempStage = new Stage();
        BorderPane exitPane = new BorderPane();

        exitPane.setTop(new Label(" Restart or Exit? Your Score : "));
        Label exitInfoLabel = new Label();
        exitInfoLabel.textProperty().bind(score.asString());

        exitPane.setCenter(exitInfoLabel);

        Button reborn = new Button("Reborn");
        reborn.setOnMouseClicked(e -> {LeaderBoard.getInstance().addNewPlayerInfo(Game.getInstance().getRecordState()); tempStage.hide(); temp.close(); Menu.getInstance().newGamePaneSetting();});
        Button exit = new Button("Exit");
        exit.setOnMouseClicked(e -> { LeaderBoard.getInstance().addNewPlayerInfo(Game.getInstance().getRecordState()); tempStage.hide(); temp.hide(); Menu.getInstance().showMenuPane();});
        exitPane.setBottom(new HBox(30, reborn, exit));

        tempStage.setScene(new Scene(exitPane));
    }

    private void timeSetting(){
        time = new SimpleIntegerProperty();
        time2 = 0;
        time.setValue(0);
        timeLabel = new Label();
        timeLabel.textProperty().bind(time.asString());
        timeLabel.setTextFill(Color.WHITESMOKE);

        timeBoard = new HBox();
        timeBoard.getChildren().addAll(new Text("Survival Time : "),timeLabel,new Text(" seconds"));
        timeBoard.setLayoutX(550);
        timeBoard.setLayoutY(250);
        timeBoard.getStyleClass().add("bonusStyle");
        timeBoard.setEffect(glow);
        timeBoard.setStyle("-fx-background-color: #336699;");
    }

    private void scoreBoardSetting(){
        score = new SimpleIntegerProperty();

        scoreLabel = new Label();
        scoreLabel.textProperty().bind(score.asString());
        scoreLabel.setTextFill(Color.WHITESMOKE);
        scoreNameLabel.setTextFill(Color.WHITESMOKE);

        scoreBoard = new HBox();
        scoreBoard.getChildren().addAll(scoreNameLabel, scoreLabel);
        scoreBoard.setLayoutX(550);
        scoreBoard.setLayoutY(350);
        scoreBoard.getStyleClass().add("bonusStyle");
        scoreBoard.setEffect(glow);
        scoreBoard.setStyle("-fx-background-color: #336699;");
    }

    private void timelineSetting(double interval){
        EventHandler<ActionEvent> eventHandler = e -> {
            if (!game.isOver()) {
                currentBlockPane.requestFocus();
                time2++;
                time.setValue(time2/2);
                game.executeCommand("s");
                updateGame();
            }
            else{
                tempStage.show();
                animation.pause();
            }
        };
        animation = new Timeline(new KeyFrame(Duration.millis(interval), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void initialize(){
        PrimaryContainer.setMinSize(BLOCK_SIZE*40, BLOCK_SIZE*50);
        BackgroundImage bg = new BackgroundImage(new Image("/Gui/star.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BLOCK_SIZE*40, BLOCK_SIZE*50, true, true, true, true));
        PrimaryContainer.setBackground(new Background(bg));
        wallBlockPaneSetting();
        nextBlockPaneSetting();
        timelineSetting(500);
        timeSetting();
        pauseSetting();
        exitSetting();
        scoreBoardSetting();
        keyPressedSetting();
        updateGame();
        LeaderBoard.getInstance().initialize();
        exitInfoSetting();

        PrimaryContainer.getChildren().addAll(wallPane,currentBlockPane, fakeBlockPane, nextBlockPane, nextBlockLabel, pauseButton,scoreBoard, exitButton, timeBoard);
        temp.setScene(new Scene(PrimaryContainer));
        showPrimaryContainer();
    }

    private void showPrimaryContainer(){
        temp.show();
    }
}