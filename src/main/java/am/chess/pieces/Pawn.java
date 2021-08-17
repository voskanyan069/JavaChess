package am.chess.pieces;

import am.chess.board.Position;
import am.chess.properties.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private Position currentPosition;
    private Position newPosition;

    public Pawn (PieceColor color, Position position) {
        super('♙', '♟', color, position);
    }

    @Override
    public List<Position> getAvailableMoves() {
        List<Position> positions = new ArrayList<>();

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int move = 1;

        if (this.getColor() == PieceColor.BLACK) {
            move = -1;
        }

        if (x > 1 && x < 8) {
            if (boardArr[x + move - 1][y - 1] instanceof Empty) {
                boardArr[x + move - 1][y - 1] =
					new Empty(new Position(x + move, y), '*');
            }
            positions.add(new Position(x + move, y));
        } if (this.getColor() == PieceColor.WHITE) {
            if (x == 2) {
                if (boardArr[x + 1][y - 1] instanceof Empty) {
                    boardArr[x + 1][y - 1] =
						new Empty(new Position(x + 2, y), '*');
                }
                positions.add(new Position(x + 2, y));
            }
        } else if (this.getColor() == PieceColor.BLACK) {
            if (x == 7) {
                if (boardArr[x - 3][y - 1] instanceof Empty) {
                    boardArr[x - 3][y - 1] =
						new Empty(new Position(x - 2, y), '*');
                }
                positions.add(new Position(x - 2, y));
            }
        }

        if (y != 1) {
            if (!(boardArr[x + move - 1][y - 2] instanceof Empty)) {
                positions.add(new Position(x + move, y - 1));
            }
        } if (y != 8) {
            if (!(boardArr[x + move - 1][y] instanceof Empty)) {
                positions.add(new Position(x + move, y + 1));
            }
        }
        return positions;
    }

    @Override
    public boolean isOtherFigureOnWay(Position currentPosition,
			Position newPosition) {
        this.currentPosition = currentPosition;
        this.newPosition = newPosition;
        int move = 1;

        if (this.getColor() == PieceColor.BLACK) {
            move = -1;
        } if (onOneStep(move)) {
            return true;
        }
        return onDoubleStep(move);
    }

    private boolean onOneStep(int step) {
        if (currentPosition.getX() + step == newPosition.getX()) {
            return !(boardArr[newPosition.getX() - 1][newPosition.getY() - 1]
					instanceof Empty);
        }
        return false;
    }
    
    private boolean onDoubleStep(int step) {
        if (currentPosition.getX() + (2 * step) == newPosition.getX()) {
            if (!(boardArr[newPosition.getX()][newPosition.getY() - 1]
						instanceof Empty)) {
                return true;
            }
            return !(boardArr[newPosition.getX() + step]
					[newPosition.getY() - 1] instanceof Empty);
        }
        return false;
    }
}
