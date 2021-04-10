package am.chess.pieces;

import am.chess.board.Position;
import am.chess.properties.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private int currentX;
    private int currentY;

    public Rook (PieceColor color, Position position) {
        super('♖', '♜', color, position);
    }

    @Override
    public List<Position> getAvailableMoves() {
        List<Position> positions = new ArrayList<>();

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        for (int i = 1; i <= 8; ++i) {
            if (i != y) {
                positions.add(new Position(x, i));
            } if (i != x) {
                positions.add(new Position(i, y));
            }
        }
        return positions;
    }

    @Override
    public boolean isOtherFigureOnWay(Position currentPosition, Position newPosition) {
        currentX = currentPosition.getX() - 1;
        currentY = currentPosition.getY() - 1;

        return checkPositions(currentPosition, newPosition);
    }

    private boolean checkPositions(Position currentPosition, Position newPosition) {
        if (currentPosition.getX() == newPosition.getX()) {
            if (currentPosition.getY() > newPosition.getY()) {
                return iterate(newPosition.getY(), currentPosition.getY() - 1, true);
            } else if (currentPosition.getY() < newPosition.getY()) {
                return iterate(currentPosition.getY(), newPosition.getY() - 1, true);
            }
        } else if (currentPosition.getY() == newPosition.getY()) {
            if (currentPosition.getX() > newPosition.getX()) {
                return iterate(newPosition.getX(), currentPosition.getX() - 1, false);
            } else if (currentPosition.getX() < newPosition.getX()) {
                return iterate(currentPosition.getX(), newPosition.getX() - 1, false);
            }
        }
        return false;
    }

    private boolean iterate(int from, int to, boolean isX) {
        int localX = currentX;
        int localY = currentY;

        for (int i = from; i < to; i++) {
            if (isX) {
                localY = i;
            } else {
                localX = i;
            }
            if (!(boardArr[localX][localY] instanceof Empty)) {
                return true;
            }
        }
        return false;
    }
}
