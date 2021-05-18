package hu.unideb.inf.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;
import java.util.Arrays;


class MenuScene {
    private Stage window;
    final private String helpURL = "https://en.wikipedia.org/wiki/Fox_games#Fox_and_Hounds";

    MenuScene(Stage window) {
        this.window = window;
    }

    void display() {
        prepareStage();
        VBox layout = prepareLayout();
        Scene scene = new Scene(layout, 333, 333);
        window.setScene(scene);
        window.show();
        GuiUtils.centerWindow(window);
    }

    private VBox prepareLayout() {
        Label label = new Label(" Farkas és Bárányok");
        label.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 43px; -fx-font-style: oblique;");
        VBox layout = new VBox(30);
        layout.setPadding(new Insets(0,0,30,0));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);
        addButtons(layout);
        return layout;
    }

    private void addButtons(VBox layout) {
        Button singlePlayerButton = new Button("Single Player");
        Button multiPlayerButton = new Button("Two Players Mode");
        Button helpButton = new Button("Help");
        helpButton.getStyleClass().add("button-help");
        Button[] buttons = {singlePlayerButton, helpButton};
        GuiUtils.setButtonHeightAndBindSizes(50, Arrays.asList(buttons), multiPlayerButton);
        layout.getChildren().addAll(singlePlayerButton, multiPlayerButton, helpButton);

        singlePlayerButton.setOnAction(event -> AlertBox.display("Egyjatekos mod jelenleg nem tamogatott!",
                "Jelenleg nincsen rendelkezésre allo AI, emiatt a SinglePlayer mode nem elerheto!" +
                        " Hasznald a Two Players Mode gombot! :)"));

        multiPlayerButton.setOnAction(event -> {
            GameScene gameScene = new GameScene(this.window);
            gameScene.display();
        });

        helpButton.setOnAction(event -> openHelpInBrowser(helpButton));
    }

    private void openHelpInBrowser(Button button) {
        button.setCursor(Cursor.WAIT);
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(helpURL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareStage() {
        window.setTitle("Farkas és Baranyok - Rokafogo jatek implementáció - Java nyelven");
        window.setResizable(false);
        window.setOnCloseRequest(event -> Platform.exit());
    }
}
