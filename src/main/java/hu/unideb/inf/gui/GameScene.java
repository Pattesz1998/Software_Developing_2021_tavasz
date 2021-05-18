package hu.unideb.inf.gui;

import hu.unideb.inf.game.GameController;
import hu.unideb.inf.game.GameOverObserver;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class GameScene implements GameOverObserver {
    private Stage window;

    GameScene(Stage window) {
        this.window = window;
    }

    void display() {
        prepareStage();
        GameController gameController = GameController.initGame(true);
        gameController.addObserver(this);
        GridPane boardView = getChessboardGrid(window, gameController);
        HBox layout = prepareLayout(gameController, boardView);
        Scene scene = new Scene(layout, 820, 620);
        window.setScene(scene);
        window.show();
        GuiUtils.centerWindow(window);
    }

    private HBox prepareLayout(GameController gameController, GridPane boardView) {
        HBox layout = new HBox();
        layout.setPadding(new Insets(10, 0, 10, 10));
        VBox rightMenu = new VBox(30);
        addButtons(gameController, rightMenu);
        rightMenu.setPadding(new Insets(20,0,20,20));
        rightMenu.setAlignment(Pos.BASELINE_RIGHT);
        layout.getChildren().addAll(boardView, rightMenu);
        return layout;
    }

    private void addButtons(GameController gameController, VBox rightMenu) {
        Button undoButton = new Button("Vissza");
        Button restartButton = new Button("Ujrainditas");
        Button quitButton = new Button("Vissza a menube");
        Button[] arr = {restartButton, undoButton};
        GuiUtils.setButtonHeightAndBindSizes(50, Arrays.asList(arr), quitButton);
        undoButton.setOnAction(event -> gameController.undoLastMove());
        restartButton.setOnAction(event -> {
            if (ConfirmationBox.display("Jatek ujrainditasa",
                    "Biztos vagy benne, hogy ujra szeretned inditani a jatekot? A jatek korabbi allapota elveszik!")) {
                restart();
            }
        });
        quitButton.setOnAction(event -> {
            if (ConfirmationBox.display("Kilepes a menube",
                    "Biztos vagy benne, hogy ki szeretnel lepni a menube? A jatek korabbi allapota elveszik! ")) {
                MenuScene menuScene = new MenuScene(window);
                menuScene.display();
            }
        });

        rightMenu.getChildren().addAll(undoButton, restartButton, quitButton);
    }


    private void prepareStage() {
        window.setTitle("Farkas és Baranyok - Rokafogo jatek implementáció - Java nyelven");
        window.setResizable(false);
        window.setOnCloseRequest(event -> {
            event.consume();
            closeWindows();
        });
    }

    private GridPane getChessboardGrid(Stage window, GameController gameController) {
        GridPane boardView = gameController.getChessboardGrid();
        boardView.prefWidthProperty().bind(window.widthProperty().subtract(200));
        return boardView;
    }

    private void closeWindows() {
        boolean answer = ConfirmationBox.display("Kilepes a jatekbol",
                "Biztos vagy benne, hogy ki szeretnel lepni a jatekbol? A jatek korabbi allapota elveszik!");
        if (answer) {
            Platform.exit();
        }
    }

    @Override
    public void wolfHasWon() {
        AlertBox.display("Jatek vege", "Barany gyozott!!");
        restart();
    }

    @Override
    public void sheepHaveWon() {
        AlertBox.display("Jatek vege", "Farkasok gyoztek!!");
        restart();
    }

    private void restart() {
        this.window.close();
        this.display();
    }
}
