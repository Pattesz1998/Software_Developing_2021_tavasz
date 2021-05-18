package hu.unideb.inf.pieces;


import hu.unideb.inf.board.ChessboardModel;
import hu.unideb.inf.board.FieldModel;

import java.util.ArrayList;

public class MovesUpward implements Movable {

    @Override
    public ArrayList<FieldModel> getPossibleMoves(ChessboardModel chessboard, FieldModel position) {
        return PossibleMovesFiller.fillPossibleMoves(chessboard, position, upRowMove);
    }
}