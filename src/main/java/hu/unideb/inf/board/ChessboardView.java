package hu.unideb.inf.board;

import hu.unideb.inf.game.PieceMovedObserver;
import hu.unideb.inf.pieces.PieceView;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;


/**
 * In the ChessboardView class we got the visual representation of the gameboard.
 */

public class ChessboardView {
    private GridPane chessboardGrid;
    private FieldView[][] chessboard;
    private List<PieceMovedObserver> observers = new ArrayList<>();

    /**
     * Here we declare the colors of the different fields, and declare a size variable which type is integer. It stores the size of the gameboard.
     * Color nonPlayable - Color of the non playable field
     * Color playable - Color of the playable field
     * Color prompt - Color of the prompt
     * Color selected - Color of the selected field
     * Color wolfColor - Color of the wolf player
     * Color sheepColor - Color of the sheep player
     */

    final int size;
    final static Color nonPlayable = Color.valueOf("#B8B8B8");
    final static Color playable = Color.valueOf("#383838");
    final static Color prompt = Color.valueOf("#6B83DD");
    final static Color selected = Color.valueOf("#908F09");
    public final static Color wolfColor = Color.valueOf("#9C2D2A");
    public final static Color sheepColor = Color.valueOf("#00B2EE");

    /**
     * Here we need the chessboardModel class, which makes our gameboard, but for the visual representation we need the Model aswell so we have to integrate the Model class..
     * FieldView[size][size], here the size will be 8x8, because this game is tradicionally plays on the chess gameboard which states from 8x8 = total of 64 fields.
     * with for cycle we have to go through the rows and columns of the chessboard and create the fields visually..
     */

    public ChessboardView(ChessboardModel chessboardModel) {
        this.size = ChessboardModel.size;
        chessboard = new FieldView[size][size];
        chessboardGrid = new GridPane();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                FieldView currentField = new FieldView(
                        chessboardModel.getField(row, col), this, chessboardModel
                        );
                chessboard[row][col] = currentField;
            }
        }
    }

    public FieldView getFieldView(FieldModel fieldModel) {
        return chessboard[fieldModel.getRow()][fieldModel.getCol()];
    }

    public void addObserver(PieceMovedObserver observer) {
        observers.add(observer);
    }

    public void movePiece(PieceView piece, int col, int row) {
        Circle pieceView = piece.getViewRepresentation();
        chessboardGrid.getChildren().remove(pieceView);
        chessboardGrid.add(pieceView, col, row);
        observers.forEach(PieceMovedObserver::pieceMoved);
    }

    public void addPiece(Circle piece, int col, int row) {
        chessboardGrid.add(piece, col, row);
    }

    public GridPane getChessboardGrid() {
        return chessboardGrid;
    }

    public DoubleBinding getPieceRadiusProperty(){
        return chessboardGrid.widthProperty().divide(size * 2 + 1);
    }
}
