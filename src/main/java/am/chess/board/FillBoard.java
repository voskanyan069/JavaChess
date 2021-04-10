package am.chess.board;

import am.chess.pieces.*;
import am.chess.properties.PieceColor;

public class FillBoard {
    private final Piece[][] whitePieces = new Piece[8][8];
    private final Piece[][] blackPieces = new Piece[8][8];
    private static Piece[][] boardArr;
    public static Board board;
    public static Piece kingW;
    public static Piece kingB;

    public FillBoard(Piece[][] boardArr) {
        FillBoard.boardArr = boardArr;
    }

    public void whitePlayersInit() {
        kingW = new King(PieceColor.WHITE, new Position(1, 4));
        Piece queenW = new Queen(PieceColor.WHITE, new Position(1, 5));
        Piece[] rooksW = new Piece[2];
        rooksW[0] = new Rook(PieceColor.WHITE, new Position(1, 1));
        rooksW[1] = new Rook(PieceColor.WHITE, new Position(1, 8));
        Piece[] knightsW = new Piece[2];
        knightsW[0] = new Knight(PieceColor.WHITE, new Position(1, 2));
        knightsW[1] = new Knight(PieceColor.WHITE, new Position(1, 7));
        Piece[] bishopsW = new Piece[2];
        bishopsW[0] = new Bishop(PieceColor.WHITE, new Position(1, 3));
        bishopsW[1] = new Bishop(PieceColor.WHITE, new Position(1, 6));
        Piece[] pawnsW = new Piece[8];
        for (int i = 0; i < pawnsW.length; i++) {
            pawnsW[i] = new Pawn(PieceColor.WHITE, new Position(2, i + 1));
        }

        initFigure(whitePieces, kingW, "king");
        initFigure(whitePieces, queenW, "queen");
        initFigure(whitePieces, rooksW[0], "rook");
        initFigure(whitePieces, rooksW[1], "rook");
        initFigure(whitePieces, knightsW[0], "knight");
        initFigure(whitePieces, knightsW[1], "knight");
        initFigure(whitePieces, bishopsW[0], "bishop");
        initFigure(whitePieces, bishopsW[1], "bishop");
        for (Piece pawn : pawnsW) {
            initFigure(whitePieces, pawn, "pawn");
        }
    }

    public void blackPlayersInit() {
        kingB = new King(PieceColor.BLACK, new Position(8, 4));
        Piece queenB = new Queen(PieceColor.BLACK, new Position(8, 5));
        Piece[] rooksB = new Piece[2];
        rooksB[0] = new Rook(PieceColor.BLACK, new Position(8, 1));
        rooksB[1] = new Rook(PieceColor.BLACK, new Position(8, 8));
        Piece[] knightsB = new Piece[2];
        knightsB[1] = new Knight(PieceColor.BLACK, new Position(8, 7));
        knightsB[0] = new Knight(PieceColor.BLACK, new Position(8, 2));
        Piece[] bishopsB = new Piece[2];
        bishopsB[0] = new Bishop(PieceColor.BLACK, new Position(8, 3));
        bishopsB[1] = new Bishop(PieceColor.BLACK, new Position(8, 6));
        Piece[] pawnsB = new Piece[8];
        for (int i = 0; i < pawnsB.length; i++) {
            pawnsB[i] = new Pawn(PieceColor.BLACK, new Position(7, i + 1));
        }

        initFigure(blackPieces, kingB, "king");
        initFigure(blackPieces, queenB, "queen");
        initFigure(blackPieces, rooksB[0], "rook");
        initFigure(blackPieces, rooksB[1], "rook");
        initFigure(blackPieces, knightsB[0], "knight");
        initFigure(blackPieces, knightsB[1], "knight");
        initFigure(blackPieces, bishopsB[0], "bishop");
        initFigure(blackPieces, bishopsB[1], "bishop");
        for (Piece pawn : pawnsB) {
            initFigure(blackPieces, pawn, "pawn");
        }
    }

    private void initFigure(Piece[][] pieces, Piece piece, String className) {
        Position thisFigurePosition = new Position(piece.getPosition().getX(), piece.getPosition().getY());
        switch (className) {
            case "king":
                pieces[piece.getPosition().getX() - 1][piece.getPosition().getY() - 1] =
                        new King(piece.getColor(), thisFigurePosition);
                break;
            case "queen":
                pieces[piece.getPosition().getX() - 1][piece.getPosition().getY() - 1] =
                        new Queen(piece.getColor(), thisFigurePosition);
                break;
            case "rook":
                pieces[piece.getPosition().getX() - 1][piece.getPosition().getY() - 1] =
                        new Rook(piece.getColor(), thisFigurePosition);
                break;
            case "knight":
                pieces[piece.getPosition().getX() - 1][piece.getPosition().getY() - 1] =
                        new Knight(piece.getColor(), thisFigurePosition);
                break;
            case "bishop":
                pieces[piece.getPosition().getX() - 1][piece.getPosition().getY() - 1] =
                        new Bishop(piece.getColor(), thisFigurePosition);
                break;
            case "pawn":
                pieces[piece.getPosition().getX() - 1][piece.getPosition().getY() - 1] =
                        new Pawn(piece.getColor(), thisFigurePosition);
                break;
        };
    }

    public void fillEmpties() {
        for (int i = 0; i < boardArr.length; i++) {
            for (int j = 0; j < boardArr[0].length; j++) {
                if (boardArr[i][j] == null ||
                        boardArr[i][j].getCharacterW() == '*') {
                    boardArr[i][j] = new Empty(new Position(i + 1, j + 1));
                }
            }
        }
    }

    public void connectPlayers() {
        for (int i = 0; i < whitePieces.length; i++) {
            for (int j = 0; j < whitePieces[0].length; j++) {
                if (whitePieces[i][j] != null) {
                    boardArr[i][j] = whitePieces[i][j];
                }
                if (blackPieces[i][j] != null) {
                    boardArr[i][j] = blackPieces[i][j];
                }
            }
        }
    }
}
