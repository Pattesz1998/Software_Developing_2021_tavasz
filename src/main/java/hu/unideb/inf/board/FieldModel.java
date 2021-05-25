
package hu.unideb.inf.board;


import hu.unideb.inf.pieces.PieceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Here in the FieldModel class we represent the fields of the gameboard (chessboard)
 * We have to integrate here the placeModel class, which creates the fields and checks for the possible moves for us.
 */
public class FieldModel {
    private int row;
    private int col;
    private PieceModel pieceModel;

    public FieldModel(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Here we investigate the possible moves from the list(FieldModel)
     * @return we got a new ArrayList which contains the possible moves..
     */

    public List<FieldModel> getPossibleMoves() {
        if (this.pieceModel == null) {
            return new ArrayList<>();
        }
        return this.pieceModel.getPossibleMoves();
    }

    public void setPieceModel(PieceModel pieceModel) {
        this.pieceModel = pieceModel;
    }

    public PieceModel getPieceModel() {
        return pieceModel;
    }

    public boolean isTaken() {
        return pieceModel != null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
