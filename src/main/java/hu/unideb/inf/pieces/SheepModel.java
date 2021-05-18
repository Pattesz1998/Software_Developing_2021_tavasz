package hu.unideb.inf.pieces;


import hu.unideb.inf.board.ChessboardModel;

public class SheepModel extends PieceModel {

    public SheepModel(ChessboardModel chessboardModel, boolean playerControlsSheep) {
        super(chessboardModel);
        movingWay = playerControlsSheep ? new MovesUpward() : new MovesDownward();
    }
}
