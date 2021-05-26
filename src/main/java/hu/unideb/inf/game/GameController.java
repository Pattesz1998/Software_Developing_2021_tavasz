package hu.unideb.inf.game;


import hu.unideb.inf.board.ChessboardModel;
import hu.unideb.inf.board.ChessboardView;
import hu.unideb.inf.board.FieldController;
import hu.unideb.inf.board.FieldView;
import hu.unideb.inf.database.Database;
import hu.unideb.inf.database.DatabaseRepository;
import hu.unideb.inf.pieces.*;
import javafx.scene.layout.GridPane;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hu.unideb.inf.board.ChessboardModel.n;



public class GameController implements PieceMovedObserver{
    private DatabaseRepository databaseRepository;
    private ChessboardModel chessboardModel;
    private ChessboardView chessboardView;
    private boolean sheepMoveUp;
    private List<PieceModel> pieces;
    private List<GameOverObserver> observers;

    public static GameController initGame(boolean playerControlsSheep) {
        ChessboardModel cm = new ChessboardModel();
        cm.setSheepMoveUp(playerControlsSheep);
        ChessboardView cv = new ChessboardView(cm);
        PieceFactory pieceFactory = new PieceFactory(cm, cv);
        List<PieceModel> pieces = pieceFactory.placePieces(playerControlsSheep);
        return new GameController(cm, cv, pieces, playerControlsSheep, new DatabaseRepository());
    }

    public GameController(ChessboardModel chessboardModel, ChessboardView chessboardView,
                          List<PieceModel> pieces, boolean sheepMoveUp, DatabaseRepository databaseRepository) {
        this.chessboardModel = chessboardModel;
        this.chessboardView = chessboardView;
        chessboardView.addObserver(this);
        this.pieces = pieces;
        this.sheepMoveUp = sheepMoveUp;
        this.observers = new ArrayList<>();
        this.databaseRepository = databaseRepository;
    }

    public void addObserver(GameOverObserver observer) {
        observers.add(observer);
    }

    public void undoLastMove() {
        if (chessboardModel.canUndo()) {
            FieldController fieldController = new FieldController(chessboardModel, chessboardView);
            fieldController.unselectField();

            Game.Move lastMove = chessboardModel.popMove();
            PieceModel pieceMoved = lastMove.to.getPieceModel();
            FieldView movedTo = chessboardView.getFieldView(lastMove.to);
            PieceView pieceViewMoved = movedTo.getPieceView();
            movedTo.colorField(FieldView.ColorTo.PLAYABLE);
            //without the above, field remains darker after undo
            //get's darker after move because mouse leaves it after it's fill is set back to playable
            pieceMoved.moveWithoutCheck(lastMove.from);
            pieceViewMoved.moveWithoutColorChange(lastMove.from);
        }
    }

    public boolean sheepWon() {
        return getWolf().getPossibleMoves().size() == 0 && getWolf().hasTurnNow();
    }

    public boolean wolfWon() {
        List<Integer> sheepRows = getSheep().stream().
                map(a -> a.getFieldModel().getRow()).sorted().collect(Collectors.toList());
        int lastSheepRowNumber = sheepMoveUp ? sheepRows.get(sheepRows.size() - 1) : sheepRows.get(0);
        return getWolf().getFieldModel().getRow() == lastSheepRowNumber;
    }

    public GridPane getChessboardGrid() {
        return chessboardView.getChessboardGrid();
    }

    public List<PieceModel> getSheep() {
        return pieces.stream().filter(a -> a instanceof SheepModel).collect(Collectors.toList());
    }

    public PieceModel getWolf() {
        return pieces.stream().filter(a -> a instanceof WolfModel).collect(Collectors.toList()).get(0);
    }


    @Override
    /**
     * Here we declare the winner of the game (sheep or wolfs, and store 3 dates in the database (winner, local date and time, and number of rounds for the win.)
     */
    public void pieceMoved() {
        Database newGame = new Database();
        if (sheepWon()) {
            newGame.setNumOfRounds(n);
            newGame.setWinner("Sheep");
            newGame.setDate(LocalDate.now());
            databaseRepository.uploadResultToDatabase(newGame);

            observers.forEach(GameOverObserver::sheepHaveWon);
        }
        if (wolfWon()) {
            newGame.setNumOfRounds(n);
            newGame.setWinner("Wolf");
            newGame.setDate(LocalDate.now());
            databaseRepository.uploadResultToDatabase(newGame);

            observers.forEach(GameOverObserver::wolfHasWon);
        }
    }
}
