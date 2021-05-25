package hu.unideb.inf.game;


import hu.unideb.inf.board.FieldModel;
import hu.unideb.inf.pieces.SheepModel;
import hu.unideb.inf.pieces.WolfModel;

import java.util.Stack;

/**
 * create the Game class where we declare that, Wolf will start the game.
 * We set the variables to true and then we do the move, that we selected, than we do the selected movement.
 */
public class Game {
    private boolean wolfsTurn = true;
    public boolean sheepMoveUp;
    private Stack<Move> listOfMoves = new Stack<>();

    public void saveMove(Move move) {
        listOfMoves.push(move);
    }
    public Move popMove() {
        return listOfMoves.pop();
    }

    /**
     * We change the players turn from wolf to the next player.
     */
    public void changeTurn() {
        wolfsTurn = !wolfsTurn;
    }

    /**
     * In the end of the turns, we change back the turns for the next player (wolf-sheep,sheep-wolf)
     */

    public Class turnOf() {
        return wolfsTurn ? WolfModel.class : SheepModel.class;
    }

    /**
     * We make an undo function , that allow us to undo the last move..
     * @return not empty list of moves..
     */
    public boolean canUndo() {
        return !listOfMoves.empty();
    }

    /**
     * Here is the move class, where we store the from value and to value (current field to next field)
     */
    public static class Move{
        public FieldModel from;
        public FieldModel to;

        /**
         * Moving method..
         * @param from field where we are now..
         * @param to field where we want to step..
         */

        public Move(FieldModel from, FieldModel to) {
            this.from = from;
            this.to = to;
        }
    }

}

