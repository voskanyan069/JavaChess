package am.chess.game;

import am.chess.board.Board;
import am.chess.board.FillBoard;
import am.chess.board.Position;
import am.chess.pieces.Empty;
import am.chess.pieces.Piece;
import am.chess.properties.PieceColor;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final Scanner sc = new Scanner(System.in);
    private final Piece[][] boardArr = new Piece[8][8];
    private final Board board = new Board(boardArr);
    private final FillBoard fillBoard = new FillBoard(boardArr);
    private boolean game = true;
    private String winnerColor;
    private PieceColor turn = PieceColor.WHITE;

    public void start() {
        FillBoard.board = board;
        Piece.board = board;
        fillBoard.whitePlayersInit();
        fillBoard.blackPlayersInit();
        fillBoard.connectPlayers();
        fillBoard.fillEmpties();
        board.refresh();
        play();
    }

    private void play() {
        Piece.boardArr = boardArr;
        int x;
        int y;
        Piece thisFigure;
        boolean incorrectPosition;
        do {
            y = askForYPosition("Current");
            x = askForXPosition("Current");
            thisFigure = boardArr[x - 1][y - 1];
            if (thisFigure instanceof Empty) {
                board.refresh();
                incorrectPosition = true;
                ColorPrint.printWarning("YOUR ENTERED POSITION IS EMPTY");
            } else if (!checkTurn(thisFigure)) {
                board.refresh();
                incorrectPosition = true;
                ColorPrint.printWarning("NOT YOUR TURN");
            } else {
                incorrectPosition = false;
            }
        } while (incorrectPosition);
		List<Position> figureAvailableMoves = thisFigure.getAvailableMoves();
		if (figureAvailableMoves.size() == 0) {
			board.refresh();
			ColorPrint.printWarning("NOT AVAILABLE POSITIONS TO MOVE FOR THIS FIGURE");
		} else {
			board.refresh();
			int newY = askForYPosition("New");
			int newX = askForXPosition("New");
			Position oldPosition = new Position(x, y);
			Position newPosition = new Position(newX, newY);
			if (thisFigure.isOtherFigureOnWay(oldPosition, newPosition)) {
				fillBoard.fillEmpties();
				board.refresh();
				ColorPrint.printWarning("ANOTHER FIGURE IS DECLARED ON THE WAY");
			} else {
				moveFigureIfAvailable(oldPosition, x, y, newX, newY);
				if (!game) {
					ColorPrint.printSuccess(winnerColor + " WON!!!");
					return;
				}
			}
		}
		play();
    }

    private void moveFigureIfAvailable(Position oldPosition, int x, int y, int newX, int newY) {
        List<Position> availablePositions = boardArr[x - 1][y - 1].getAvailableMoves();
        Piece thisFigure = boardArr[x - 1][y - 1];
        boolean incorrectPosition = true;

        for (Position availablePosition : availablePositions) {
            if (availablePosition.getX() == newX && availablePosition.getY() == newY) {
                byte isPositionFree = Piece.isPositionFree(new Position(newX, newY), boardArr[x - 1][y - 1]);
                if (isPositionFree == 0) {
                    Piece.figureMove(oldPosition, new Position(newX, newY), boardArr[x - 1][y - 1]);
                    changeOrder();
                } else if (isPositionFree == 1) {
                    if (thisFigure.getColor() == PieceColor.WHITE) {
                        if (FillBoard.kingB.getPosition().getX() == newX &&
                                FillBoard.kingB.getPosition().getY() == newY) {
                            game = false;
                            winnerColor = "WHITE'S";
                        }
                    } else if (thisFigure.getColor() == PieceColor.BLACK) {
                        if (FillBoard.kingW.getPosition().getX() == newX &&
                                FillBoard.kingW.getPosition().getY() == newY) {
                            game = false;
                            winnerColor = "BLACK'S";
                        }
                    }
                    Piece.figureMove(oldPosition, new Position(newX, newY), boardArr[x - 1][y - 1]);
                    changeOrder();
                } else if (isPositionFree == 2) {
					fillBoard.fillEmpties();
					board.refresh();
					ColorPrint.printWarning("THE POSITION IS A BUSY");
                }
                incorrectPosition = false;
                break;
            }
        }
		if (incorrectPosition) {
			fillBoard.fillEmpties();
			board.refresh();
            ColorPrint.printWarning("INCORRECT POSITION");
        }
    }

    private boolean checkTurn(Piece figure) {
        return turn == figure.getColor();
    }

    private void changeOrder() {
        if (turn == PieceColor.WHITE) {
            turn = PieceColor.BLACK;
        } else if (turn == PieceColor.BLACK) {
            turn = PieceColor.WHITE;
        }
    }

    private int askForXPosition(String text) {
        try {
            System.out.print("[" + turn.toString() + "]:\t" + text + " number -> ");
            int x = sc.nextInt();
            if (x < 1 || x > 8) {
                ColorPrint.printWarning("Enter the correct position [1:8]");
                return askForXPosition(text);
            }
            return x;
        } catch (InputMismatchException e) {
            ColorPrint.printError("Input error");
            System.exit(1);
            return 0;
        }
    }

    private int askForYPosition(String text) {
        System.out.print("[" + turn.toString() + "]:\t" + text + " letter -> ");
        String yStr = sc.next();
        int y = Piece.letterToNumber(yStr);
        if (y < 1 || y > 8) {
            ColorPrint.printWarning("Enter the correct position [A:H]");
            return askForYPosition(text);
        }
        return y;
    }

    private void printInformation(int x, int y, int newX, int newY) {
        System.out.println("--------------------------------------");
        System.out.println("CURRENT - " + boardArr[x - 1][y - 1].getPosition().getX() + " | " + boardArr[x - 1][y - 1].getPosition().getY());
        for (int i = 0; i < boardArr[x - 1][y - 1].getAvailableMoves().size(); i++) {
            System.out.println(boardArr[x - 1][y - 1].getAvailableMoves().get(i).getX() + " | " + boardArr[x - 1][y - 1].getAvailableMoves().get(i).getY());
        }
        System.out.println("POSITION_FREE - " + Piece.isPositionFree(new Position(newX, newY), boardArr[x - 1][y - 1]));
        System.out.println("--------------------------------------");
    }
}
