package am.chess.pieces;

import am.chess.board.Position;
import am.chess.properties.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Empty extends Piece {
    public Empty(Position position) {
        super(' ', ' ', PieceColor.NONE, position);
    }

    public Empty(Position position, char character) {
        super(character, character, PieceColor.NONE, position);
    }

    @Override
    public List<Position> getAvailableMoves() {
        return new ArrayList<>();
    }

    @Override
    public boolean isOtherFigureOnWay(Position currentPosition, Position newPosition) {
        return false;
    }

}
