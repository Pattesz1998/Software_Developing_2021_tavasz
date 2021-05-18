package hu.unideb.inf.pieces;


import hu.unideb.inf.board.ChessboardModel;

public class WolfModel extends PieceModel {
    public WolfModel(ChessboardModel chessboardModel) {
        super(chessboardModel);
        movingWay = new MovesBothWays();
    }
}
