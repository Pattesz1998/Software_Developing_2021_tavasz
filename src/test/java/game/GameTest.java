package game;


import hu.unideb.inf.board.ChessboardModel;
import hu.unideb.inf.pieces.PieceModel;
import hu.unideb.inf.pieces.SheepModel;
import hu.unideb.inf.pieces.WolfModel;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void turnTest() {
        ChessboardModel chessboardModel = new ChessboardModel();
        assert chessboardModel.turnOf() == WolfModel.class;
        chessboardModel.changeTurn();
        assert chessboardModel.turnOf() == SheepModel.class;
        PieceModel sheep = new SheepModel(chessboardModel, false);
        assert chessboardModel.turnOf().isInstance(sheep);
        PieceModel wolf = new WolfModel(chessboardModel);
        assert !chessboardModel.turnOf().isInstance(wolf);
    }
}