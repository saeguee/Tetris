package Gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
        public void start(Stage primaryStage) throws Exception {
            Menu menu = Menu.getInstance();
            menu.initialize();
        }
}

