package am.chess.pieces;

import am.chess.board.Position;
import am.chess.properties.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop (PieceColor color, Position position) {
        super('♗', '♝', color, position);
    }

    @Override
    public List<Position> getAvailableMoves() {
        List<Position> positions = new ArrayList<>();

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        for (int i = 1; i <= 8; i++) {
            if (x + i > 8 || y + i > 8) {
                break;
            }
            if (!(boardArr[x + i - 1][y + i - 1] instanceof Empty)) {
                if (this.getColor() == boardArr[x + i - 1][y + i - 1].getColor()) {
                    break;
                }
            } else {
                boardArr[x + i - 1][y + i - 1] = new Empty(new Position(x + i, y + i), '*');
            }
            positions.add(new Position(x + i, y + i));
        }
        for (int i = 1; i <= 8; i++) {
            if (x + i > 8 || y - i < 1) {
                break;
            }
            if (!(boardArr[x + i - 1][y - i - 1] instanceof Empty)) {
                if (this.getColor() == boardArr[x + i - 1][y - i - 1].getColor()) {
                    break;
                }
            } else {
                boardArr[x + i - 1][y - i - 1] = new Empty(new Position(x + i, y - i), '*');
            }
            positions.add(new Position(x + i, y - i));
        }
        for (int i = 1; i <= 8; i++) {
            if (x - i < 1 || y + i > 8) {
                break;
            }
            if (!(boardArr[x - i - 1][y + i - 1] instanceof Empty)) {
                if (this.getColor() == boardArr[x - i - 1][y + i - 1].getColor()) {
                    break;
                }
            } else {
                boardArr[x - i - 1][y + i - 1] = new Empty(new Position(x - i, y + i), '*');
            }
            positions.add(new Position(x - i, y + i));
        }
        for (int i = 1; i <= 8; i++) {
            if (x - i < 1 || y - i < 1) {
                break;
            }
            if (!(boardArr[x - i - 1][y - i - 1] instanceof Empty)) {
                if (this.getColor() == boardArr[x - i - 1][y - i - 1].getColor()) {
                    break;
                }
            } else {
                boardArr[x - i - 1][y - i - 1] = new Empty(new Position(x - i, y - i), '*');
            }
            positions.add(new Position(x - i, y - i));
        }
        return positions;
    }

    @Override
    public boolean isOtherFigureOnWay(Position currentPosition, Position newPosition) {
        int[] steps = new int[2];

        if (currentPosition.getY() > newPosition.getY()) {
            steps[0] = -1;
            if (currentPosition.getX() > newPosition.getX()) {
                steps[1] = -1;
            } else if (newPosition.getX() > currentPosition.getX()) {
                steps[1] = 1;
            }
        } else if (newPosition.getY() > currentPosition.getY()) {
            steps[0] = 1;
            if (currentPosition.getX() > newPosition.getX()) {
                steps[1] = -1;
            } else if (newPosition.getX() > currentPosition.getX()) {
                steps[1] = 1;
            }
        }

        int stepsCount = Math.max(Math.abs(currentPosition.getX() - newPosition.getX()),
                Math.abs(currentPosition.getY() - newPosition.getY()));

        int x = currentPosition.getX() + steps[1];
        int y = currentPosition.getY() + steps[0];

        for (int i = 0; i < stepsCount; i++) {
            if (!(boardArr[x - 1][y - 1] instanceof Empty)) {
                return true;
            }
        }
        return false;
    }
}
