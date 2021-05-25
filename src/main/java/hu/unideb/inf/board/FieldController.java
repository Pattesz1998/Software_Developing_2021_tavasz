package hu.unideb.inf.board;


import hu.unideb.inf.pieces.PieceView;

import java.util.List;

/**
 * We create the FieldController class, which refers to the field selection and unselection, and for the moving of the pieces.
 */
public class FieldController {
    private ChessboardModel chessboardModel;
    private ChessboardView chessboardView;

    /**
     * Here we integrate the two classes, that needs for the FieldController java class.
     * @param chessboardModel class which creates the gameboard.
     * @param chessboardView class which makes the virtual representation of the gameboard.
     */

    public FieldController(ChessboardModel chessboardModel, ChessboardView chessboardView) {
        this.chessboardModel = chessboardModel;
        this.chessboardView = chessboardView;
    }
    /**
     * Here we have to investigate, that if the selected (clicked) field is available to move, we do the move, else the current field will be unselected.
     * Every time when we clicks on a taken field, we have to unselect the current field selection by clicking on it or select a new place where we wants to move..
     */
    void mouseClicked(FieldView fieldView) {
        if (chessboardModel.getFieldModelSelected() != null && !fieldView.getFieldModel().isTaken()) {
            moveSelectedPiece(fieldView.getFieldModel());
        } else if (fieldView.getFieldModel().isTaken()) {
            if (fieldView.getFieldModel().equals(chessboardModel.getFieldModelSelected()))
                unselectField();
            else
                selectField(fieldView);
        }
    }
    /**
     * Here we declare, that how the "field unselectation" will be get place. How it works. We investigate that the selected field from the List of FieldModel moves is playable or not. If not, than clicking on it and it will be unselected.
     */
    public void unselectField() {
        FieldModel fieldModel = chessboardModel.getFieldModelSelected();
        if (fieldModel == null) return;
        List<FieldModel> moves = fieldModel.getPossibleMoves();
        for (FieldModel f : moves) {
            chessboardView.getFieldView(f).setFill(ChessboardView.playable);
        }
        chessboardView.getFieldView(fieldModel).setFill(ChessboardView.playable);
        chessboardModel.setFieldModelSelected(null);
    }
    /**
     * Here we declare, that how the "field selection" will be get place. How it works. We investigate that the selected field from the List of FieldModel moves is playable or not. If yes, than clicking on the new place, that we want to select (a non "reserved" place), and than the moving will be done.
     */
    private void selectField(FieldView fieldView) {
        unselectField();
        List<FieldModel> moves = fieldView.getFieldModel().getPossibleMoves();
        if (moves.size() == 0 ) return;
        for (FieldModel f : moves) {
            chessboardView.getFieldView(f).setFill(ChessboardView.prompt);
        }
        chessboardModel.setFieldModelSelected(fieldView.getFieldModel());
        fieldView.setFill(ChessboardView.selected);
    }
    /**
     * An finally here we declare, that how the "moving" will be get place. How it works. The current player will be moved to the selected place (if its not reserved)..
     */
    private void moveSelectedPiece(FieldModel destination) {
        FieldModel fieldSelected = chessboardModel.getFieldModelSelected();
        PieceView pieceViewToMove = chessboardView.getFieldView(fieldSelected).getPieceView();
        List<FieldModel> moves = fieldSelected.getPossibleMoves();
        unselectField();
        if (moves.contains(destination) ) {
            pieceViewToMove.move(destination);
        }
    }

}
