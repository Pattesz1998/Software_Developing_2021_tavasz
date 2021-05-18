package game;

import hu.unideb.inf.board.ChessboardModel;
import hu.unideb.inf.board.ChessboardView;
import hu.unideb.inf.board.FieldModel;
import hu.unideb.inf.database.DatabaseRepository;
import hu.unideb.inf.game.GameController;
import hu.unideb.inf.pieces.PieceFactory;
import hu.unideb.inf.pieces.PieceModel;
import hu.unideb.inf.pieces.PieceView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class GameControllerTest {

    @Test
    void undoLastMove() {
        ChessboardModel chessboardModel = new ChessboardModel();
        ChessboardView chessboardView = new ChessboardView(chessboardModel);
        PieceFactory pieceFactory = new PieceFactory(chessboardModel, chessboardView);
        GameController gameController = new GameController(chessboardModel, chessboardView,
                pieceFactory.placePieces(true), true, Mockito.mock(DatabaseRepository.class));
        assert !chessboardModel.canUndo();
        FieldModel wolfField = chessboardModel.getField(0, 5);
        FieldModel toMove = chessboardModel.getField(1, 4);
        PieceModel wolf = wolfField.getPieceModel();
        PieceView wolfView = chessboardView.getFieldView(wolfField).getPieceView();
        wolfView.move(toMove);
        assert !wolf.hasTurnNow();
        assert wolf == chessboardModel.getField(1, 4).getPieceModel();
        assert null == chessboardModel.getField(0, 5).getPieceModel();
        gameController.undoLastMove();
        assert wolf == chessboardModel.getField(0, 5).getPieceModel();
        assert wolf.hasTurnNow();
    }

    @Test
    void getSheep() {
        GameController gameController = GameController.initGame(true);
        gameController.getSheep().stream().
                map(a -> a.getFieldModel().getRow()).sorted().forEach(a -> {
                    assert a == 7;
        } );
    }

    @Test
    void getWolf() {
        GameController gameController = GameController.initGame(true);
        assert gameController.getWolf().getFieldModel().getRow() == 0;
    }

    @Test
    void somebodyWon() {
        GameController gameController = GameController.initGame(true);
        assert !gameController.wolfWon();
        assert !gameController.sheepWon();
    }
}