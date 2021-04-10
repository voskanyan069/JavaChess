package am.chess.pieces;

import am.chess.board.Position;
import am.chess.properties.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    private final Rook rook;
    private final Bishop bishop;

    public Queen (PieceColor color, Position position) {
        super('♕', '♛', color, position);
        rook = new Rook(color, position);
        bishop = new Bishop(color, position);
    }

    @Override
    public List<Position> getAvailableMoves() {
        List<Position> rookPositions = rook.getAvailableMoves();
        List<Position> bishopPositions = bishop.getAvailableMoves();

        List<Position> positions = new ArrayList<>();
        positions.addAll(rookPositions);
        positions.addAll(bishopPositions);
        return positions;
    }

    @Override
    public boolean isOtherFigureOnWay(Position currentPosition, Position newPosition) {
        if (currentPosition.getX() == newPosition.getX() ||
                currentPosition.getY() == newPosition.getY()) {
            return rook.isOtherFigureOnWay(currentPosition, newPosition);
        }
        return bishop.isOtherFigureOnWay(currentPosition, newPosition);
    }

}
