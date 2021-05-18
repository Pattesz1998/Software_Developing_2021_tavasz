package hu.unideb.inf.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage window) {
        MenuScene menuScene = new MenuScene(window);
        menuScene.display();
    }
}
