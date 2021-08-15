package am.chess.pieces;

import am.chess.board.Position;
import am.chess.properties.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King (PieceColor color, Position position) {
        super('♔', '♚', color, position);
    }

    @Override
    public List<Position> getAvailableMoves() {
        List<Position> positions = new ArrayList<>();

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        if (y - 1 > 0) {
            if (boardArr[x - 1][y - 2] instanceof Empty) {
                boardArr[x - 1][y - 2] = new Empty(new Position(x, y - 1), '*');
            }
            positions.add(new Position(x, y - 1));
        } if (x + 1 < 9 && y - 1 > 0) {
            if (boardArr[x][y - 2] instanceof Empty) {
                boardArr[x][y - 2] = new Empty(new Position(x + 1, y - 1), '*');
            }
            positions.add(new Position(x + 1, y - 1));
        } if (x + 1 < 9) {
            if (boardArr[x][y - 1] instanceof Empty) {
                boardArr[x][y - 1] = new Empty(new Position(x + 1, y), '*');
            }
            positions.add(new Position(x + 1, y));
        } if (x + 1 < 9 && y + 1 < 9) {
            if (boardArr[x][y] instanceof Empty) {
                boardArr[x][y] = new Empty(new Position(x + 1, y + 1), '*');
            }
            positions.add(new Position(x + 1, y + 1));
        } if (y + 1 < 9) {
            if (boardArr[x - 1][y] instanceof Empty) {
                boardArr[x - 1][y] = new Empty(new Position(x, y + 1), '*');
            }
            positions.add(new Position(x, y + 1));
        } if (x - 1 > 0 && y + 1 < 9) {
            if (boardArr[x - 2][y] instanceof Empty) {
                boardArr[x - 2][y] = new Empty(new Position(x - 1, y + 1), '*');
            }
            positions.add(new Position(x - 1, y + 1));
        } if (x - 1 > 0) {
            if (boardArr[x - 2][y - 1] instanceof Empty) {
                boardArr[x - 2][y - 1] = new Empty(new Position(x - 1, y), '*');
            }
            positions.add(new Position(x - 1, y));
        } if (x - 1 > 0 && y - 1 > 0) {
            if (boardArr[x - 2][y - 2] instanceof Empty) {
                boardArr[x - 2][y - 2] = new Empty(new Position(x - 1, y - 1), '*');
            }
            positions.add(new Position(x - 1, y - 1));
        }
        return positions;
    }

    @Override
    public boolean isOtherFigureOnWay(Position currentPosition, Position newPosition) {
        for (int i = newPosition.getX() - 1; i < newPosition.getX() + 2; i++) {
            for (int j = newPosition.getY() - 1; j < newPosition.getY() + 2; j++) {
				if ((i > 0 && i < 8) && (j > 0 && j < 8)) {
					if (boardArr[i][j] instanceof King) {
						if (!this.getColor().equals(boardArr[i][j].getColor())) {
							return true;
						}
					}
				}
            }
        }
        return false;
    }

}
