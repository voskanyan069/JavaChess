package am.chess.pieces;

import am.chess.board.Position;
import am.chess.properties.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight (PieceColor color, Position position) {
        super('♘', '♞', color, position);
    }

    @Override
    public List<Position> getAvailableMoves() {
        List<Position> positions = new ArrayList<>();

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        if (x + 1 < 9 && y - 2 > 0) {
            if (boardArr[x][y - 3] instanceof Empty) {
                boardArr[x][y - 3] =
					new Empty(new Position(x + 1, y - 2), '*');
            }
            positions.add(new Position(x + 1, y - 2));
        } if (x - 1 > 0 && y - 2 > 0) {
            if (boardArr[x - 2][y - 3] instanceof Empty) {
                boardArr[x - 2][y - 3] =
					new Empty(new Position(x - 1, y - 2), '*');
            }
            positions.add(new Position(x - 1, y - 2));
        } if (x + 2 < 9 && y - 1 > 0) {
            if (boardArr[x + 1][y - 2] instanceof Empty) {
                boardArr[x + 1][y - 2] =
					new Empty(new Position(x + 1, y - 2), '*');
            }
            positions.add(new Position(x + 2, y - 1));
        } if (x + 2 < 9 && y + 1 < 9) {
            if (boardArr[x + 1][y] instanceof Empty) {
                boardArr[x + 1][y] =
					new Empty(new Position(x + 2, y + 1), '*');
            }
            positions.add(new Position(x + 2, y + 1));
        } if (x + 1 < 9 && y + 2 < 9) {
            if (boardArr[x][y + 1] instanceof Empty) {
                boardArr[x][y + 1] =
					new Empty(new Position(x + 1, y + 2), '*');
            }
            positions.add(new Position(x + 1, y + 2));
        } if (x - 1 > 0 && y + 2 < 9) {
            if (boardArr[x - 2][y + 1] instanceof Empty) {
                boardArr[x - 2][y + 1] =
					new Empty(new Position(x - 1, y + 2), '*');
            }
            positions.add(new Position(x - 1, y + 2));
        } if (x - 2 > 0 && y + 1 < 9) {
            if (boardArr[x - 3][y] instanceof Empty) {
                boardArr[x - 3][y] =
					new Empty(new Position(x - 2, y + 1), '*');
            }
            positions.add(new Position(x - 2, y + 1));
        } if (x - 2 > 0 && y - 1 > 0) {
            if (boardArr[x - 3][y - 2] instanceof Empty) {
                boardArr[x - 3][y - 2] =
					new Empty(new Position(x - 2, y - 1), '*');
            }
            positions.add(new Position(x - 2, y - 1));
        }
        return positions;
    }

    @Override
    public boolean isOtherFigureOnWay(Position currentPosition,
			Position newPosition) {
        return false;
    }
}
