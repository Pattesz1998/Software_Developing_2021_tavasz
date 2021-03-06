package board;

import hu.unideb.inf.board.ChessboardModel;
import hu.unideb.inf.board.FieldModel;
import hu.unideb.inf.pieces.SheepModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FieldModelTest {

    private FieldModel testFieldOne = new FieldModel(3, 3);
    private FieldModel testFieldTwo = new FieldModel(0, 7);

    @Test
    void isTaken() {
        assert !testFieldOne.isTaken();
        assert !testFieldTwo.isTaken();
        testFieldOne.setPieceModel(new SheepModel(new ChessboardModel(), true));
        assert testFieldOne.isTaken();
    }

    @Test
    void getPossibleMoves() {
        assertEquals(testFieldTwo.getPossibleMoves(), new ArrayList<FieldModel>());
        ChessboardModel chessboardModel = new ChessboardModel();
        chessboardModel.changeTurn();
        new SheepModel(chessboardModel, true).place(testFieldOne);
        ArrayList<FieldModel> possibleTestMoves = new ArrayList<>();
        possibleTestMoves.add(chessboardModel.getField(2, 2));
        possibleTestMoves.add(chessboardModel.getField(2, 4));
        assert testFieldOne.getPossibleMoves().containsAll(possibleTestMoves);
    }
}