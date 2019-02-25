package Gui;

import Logic.UserState;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;

public class LeaderBoard {
    private BorderPane leaderPane;
    private VBox scoreList;
    private LinkedList<UserState> rankList;
    private Button exitButton;
    private static LeaderBoard leaderBorad = new LeaderBoard();
    private Stage temp = new Stage();
    private LeaderBoard(){initialize();}
    private boolean flag = false;
    public static LeaderBoard getInstance(){return leaderBorad;}

    public void initialize() {
        leaderPane = new BorderPane();
        leaderPane.setMinHeight(200);
        leaderPane.setMinWidth(220);
        rankList = new LinkedList<>();

        scoreList = new VBox();
        scoreList.getStyleClass().add("bonusStyle");
        final Effect glow = new Glow(0.6);
        scoreList.setEffect(glow);
        leaderPane.setCenter(scoreList);

        exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(e -> {temp.hide(); Menu.getInstance().showMenuPane();});
        leaderPane.setBottom(exitButton);
        temp.setScene(new Scene(leaderPane));
    }

    public void addNewPlayerInfo(UserState userState){
        int currentScore = userState.getScore();
        if(rankList.size() == 5)
            if (rankList.getFirst().getScore() >= currentScore)
                return;
            else rankList.removeFirst();
        if(rankList.size() == 0)
            rankList.addFirst(userState);
        else rankList.add(traverse(currentScore), userState);
        flag = true;
    }

    int traverse(int currentScore){
        int index = 0;
        for(UserState each : rankList)
            if(each.getScore() > currentScore)
               break;
            else index++;
        return index;
    }

    public void showLeaderBoard() {
        if(flag == true){
            scoreList.getChildren().clear();
            String st = String.format("%-23s%-10c%-1s\n" , "User ID",' ',"Score");
            for (UserState each : rankList)
                st += each.toString()+'\n';
            scoreList.getChildren().add(new Text(st));
            flag = false;
        }
        temp.show();
    }
}
