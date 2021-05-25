package am.chess.pieces;

import am.chess.board.Board;
import am.chess.board.FillBoard;
import am.chess.board.Position;
import am.chess.properties.PieceColor;

import java.util.List;

public abstract class Piece {
    private final char characterW;
    private final char characterB;
    private final PieceColor color;
    private Position position;
    public static Piece[][] boardArr;
    public static Board board;

    public Piece(char characterW, char characterB, PieceColor color, Position position) {
        this.characterW = characterW;
        this.characterB = characterB;
        this.color = color;
        this.position = position;
    }

    public Piece() {
        this.characterW = ' ';
        this.characterB = ' ';
        this.color = PieceColor.NONE;
    }

    public abstract List<Position> getAvailableMoves();

    public abstract boolean isOtherFigureOnWay(Position currentPosition, Position newPosition);

    public static byte isPositionFree(Position newPos, Piece piece) {
        if (boardArr[newPos.getX() - 1][newPos.getY() - 1] instanceof Empty) {
            return 0;
        } else if (boardArr[newPos.getX() - 1][newPos.getY() - 1].getColor() != piece.getColor()) {
            return 1;
        } else if (boardArr[newPos.getX() - 1][newPos.getY() - 1].getColor() == piece.getColor()) {
            return 2;
        }
        return 9;
    }

    public static int letterToNumber(String letter) {
        switch (letter.toLowerCase()) {
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
            default:
                return 0;
        }
    }

    public static void figureMove(Position from, Position to, Piece piece) {
        FillBoard fillBoard = new FillBoard(boardArr);
        Board board = new Board(boardArr);

        boardArr[from.getX() - 1][from.getY() - 1] = null;
        boardArr[to.getX() - 1][to.getY() - 1] = piece;
        piece.setPosition(new Position(to.getX(), to.getY()));
        fillBoard.fillEmpties();
        board.init();
        board.refresh();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PieceColor getColor() {
        return color;
    }

    public char getCharacterW() {
        return characterW;
    }

    public char getCharacterB() {
        return characterB;
    }
}
