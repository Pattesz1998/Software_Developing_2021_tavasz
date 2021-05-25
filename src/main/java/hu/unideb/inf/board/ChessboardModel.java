package hu.unideb.inf.board;

import hu.unideb.inf.game.Game;

/**
 * create the ChessboardModel class, which will make our gameboard, and the variables..
 */
public class ChessboardModel {
    public final static int size = 8;
    private FieldModel[][] chessboard;
    private FieldModel fieldModelSelected = null;
    private Game game = new Game();
    public static Integer n = 0;

    /**
     * create ChessboardModel constructor for the ChessboardModel class, where we have to filling the chessboard with Rectangles.
     */
    public ChessboardModel() {
        //Filling the chessboard with Rectangles
        chessboard = new FieldModel[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                chessboard[i][j] = new FieldModel(i, j);
            }
        }
    }

    /**
     * We have to examine that if we did a validMove in the chessboard
     * @param row number of row
     * @param col number of column
     * @return we got row and column when our Move was valid when all of our conditions are true.
     */
    public boolean isValidMove(int row, int col) {
        return ((0 <= col) && (col < ChessboardModel.size) &&
                (0 <= row) && (row < ChessboardModel.size) &&
                !(chessboard[row][col].isTaken()));
    }

    /**
     * Here we have the FieldModel where we stored the fields of the chessboard.
     * @param row number of row
     * @param col number of col
     * @return we got the "free" field, so if the selected field is not taken, our move will be vaild...
     */
    public FieldModel getField(int row, int col) {
        return chessboard[row][col];
    }


    public FieldModel getFieldModelSelected() {
        return fieldModelSelected;
    }

    public void setFieldModelSelected(FieldModel fieldModelSelected) {
        this.fieldModelSelected = fieldModelSelected;
    }

    public Class turnOf() {
        return game.turnOf();
    }

    /**
     * In the changeTurn method, we declare that change the turn for the next player
     */
    public void changeTurn() {
        n += 1;

        game.changeTurn();
    }

    public void saveMove(Game.Move move) {
        game.saveMove(move);
    }

    public Game.Move popMove() {
        return game.popMove();
    }

    /**
     * examination of the possibility of step-back
     * @return we got the value of that can we step-back the last move or not..
     */
    public boolean canUndo() {
        return game.canUndo();
    }

    public void setSheepMoveUp(boolean sheepMoveUp) {
        this.game.sheepMoveUp = sheepMoveUp;
    }
}
