package am.chess.game;

import am.chess.board.Board;
import am.chess.board.FillBoard;
import am.chess.board.Position;
import am.chess.pieces.Empty;
import am.chess.pieces.King;
import am.chess.pieces.Piece;
import am.chess.properties.PieceColor;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/*
 * Main game class
 */
public class Game {
	private static final Scanner sc = new Scanner(System.in);
	private final Piece[][] boardArr = new Piece[8][8];
	private final Board board = new Board(boardArr);
	private final FillBoard fillBoard = new FillBoard(boardArr);
	private boolean game = true;
	private PieceColor winnerColor;
	private PieceColor kingCheck = PieceColor.NONE;
	private int kingChecksCount = 0;
	private int kingCheckmateCount = 0;
	private PieceColor turn = PieceColor.WHITE;

	/*
	 * Start point for game
	 */
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

	/*
	 * Always recursive call this method to continue game
	 */
	private void play() {
		Piece.boardArr = boardArr;
		checkKingsAttack();
		// If no any checks
		if (kingCheck != PieceColor.NONE) {
			ColorPrint.printWarning("PLEASE CHECK THE KING [" +
					kingCheck.toString() + "]");
		}
		// Ask for figure to move
		Piece thisFigure = getInputPosition();
		Position thisPosition = thisFigure.getPosition();
		// Get available positions to move for this figure
		List<Position> figureAvailableMoves = thisFigure.getAvailableMoves();
		// If no any moves is available
		if (figureAvailableMoves.size() == 0) {
			board.refresh();
			ColorPrint.printWarning("NOT AVAILABLE POSITIONS TO MOVE" +
					" FOR THIS FIGURE");
		} else {
			board.refresh();
			int x = thisPosition.getX();
			int y = thisPosition.getY();
			// Get new position for move
			int newY = askForYPosition("New");
			int newX = askForXPosition("New");
			Position oldPosition = new Position(x, y);
			Position newPosition = new Position(newX, newY);
			// If other figures in move path
			if (thisFigure.isOtherFigureOnWay(oldPosition, newPosition)) {
				fillBoard.fillEmpties();
				board.refresh();
				ColorPrint.printWarning("ANOTHER FIGURE IS DECLARED" +
						" ON THE WAY");
			} else {
				moveFigureIfAvailable(oldPosition, newPosition);
				if (!game) {
					ColorPrint.printSuccess(winnerColor.toString() +
							"'s WON!!!");
					return;
				}
			}
		}
		play();
	}

	/*
	 * Ask user for figure current position
	 */
	private Piece getInputPosition() {
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

		return thisFigure;
	}

	/*
	 * Check both kings for check
	 */
	private void checkKingsAttack() {
		Piece[][] whitePieces = fillBoard.getWhitePieces();
		Piece[][] blackPieces = fillBoard.getBlackPieces();
		boolean whiteCheck = checkAttacks(whitePieces,
				FillBoard.kingB.getPosition());
		boolean blackCheck = checkAttacks(blackPieces,
				FillBoard.kingW.getPosition());
		kingCheck = whiteCheck ? PieceColor.BLACK :
			blackCheck ? PieceColor.WHITE :
			PieceColor.NONE;
		if (kingCheck != PieceColor.NONE) {
			if (kingChecksCount == 0) {
				kingChecksCount = 1;
			} else {
				if (kingChecksCount == 2) {
					kingCheckmateCount += 1;
				}
				kingChecksCount = 2;
			}
		} else {
			kingChecksCount = 0;
			kingCheckmateCount = 0;
		}
		fillBoard.fillEmpties();
	}

	/*
	 * Check given king for attacks
	 * arg: pieces - All pieces of enemy color
	 * arg: kingPos - The position of king to check
	 */
	private boolean checkAttacks(Piece[][] pieces, Position kingPos) {
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				Piece thisPiece = pieces[i][j];
				if(!(thisPiece instanceof Empty) && thisPiece != null) {
					List<Position> availablePositions =
						thisPiece.getAvailableMoves();
					for (Position position : availablePositions) {
						if (position.getX() == kingPos.getX()) {
							if (position.getY() == kingPos.getY()) {
								if (!thisPiece.isOtherFigureOnWay(thisPiece
											.getPosition(), kingPos)) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	/*
	 * Move the given figure to new position if no any conflicts
	 * arg: oldPosition - Current position of the figure
	 * arg: newPosition - The new position to move figure
	 */
	private void moveFigureIfAvailable(Position oldPosition,
			Position newPosition) {
		int x = oldPosition.getX();
		int y = oldPosition.getY();
		int newX = newPosition.getX();
		int newY = newPosition.getY();
		List<Position> availablePositions =
			boardArr[x - 1][y - 1].getAvailableMoves();
		Piece thisFigure = boardArr[x - 1][y - 1];
		boolean incorrectPosition = true;

		for (Position availablePosition : availablePositions) {
			if (availablePosition.getX() == newX) {
				if (availablePosition.getY() == newY) {
					byte isPositionFree = Piece.isPositionFree(
							newPosition, boardArr[x - 1][y - 1]);
					if (isPositionFree == 0) {
						Piece.figureMove(oldPosition, newPosition,
								boardArr[x - 1][y - 1]);
						Piece figure = boardArr[newX - 1][newY - 1];

						if (figure instanceof King) {
							FillBoard.setKingPosition(figure.getColor(),
									newPosition);
						}

						checkKingsAttack();
						if (kingChecksCount == 2) {
							recoverMove(newPosition, oldPosition, figure);
						} else if (kingChecksCount == 1) {
							if (figure.getColor() == kingCheck) {
								recoverMove(newPosition, oldPosition, figure);
								ColorPrint.printWarning("NOT CORRECT MOVE" +
										" BECAUSE KING CHECK [" +
										kingCheck.toString() + "]");
							} else {
								changeOrder();
							}
						} else {
							changeOrder();
						}
						if (kingCheckmateCount == 4) {
							winnerColor = figure.getColor();
							game = false;
						}
					} else if (isPositionFree == 1) {
						if (thisFigure.getColor() == PieceColor.WHITE) {
							if (FillBoard.kingB.getPosition().getX() == newX) {
								if (FillBoard.kingB.getPosition().getY() ==
										newY) {
									winnerColor = PieceColor.WHITE;
									game = false;
								}
							}
						} else if (thisFigure.getColor() == PieceColor.BLACK) {
							if (FillBoard.kingW.getPosition().getX() == newX) {
								if (FillBoard.kingW.getPosition().getY() ==
										newY) {
									winnerColor = PieceColor.BLACK;
									game = false;
								}
							}
						}
						Piece.figureMove(oldPosition, newPosition,
								boardArr[x - 1][y - 1]);
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
		}
		if (incorrectPosition) {
			fillBoard.fillEmpties();
			board.refresh();
			ColorPrint.printWarning("INCORRECT POSITION");
		}
	}

	/*
	 * Recover figure to his previous position
	 * arg: from - From where move the figure
	 * arg: to - Where move the figure
	 * arg: figure - Figure to move
	 */
	private void recoverMove(Position from, Position to, Piece figure) {
		Piece.figureMove(from, to, figure);
		if (figure instanceof King) {
			FillBoard.setKingPosition(figure.getColor(), to);
		}
	}

	/*
	 * Check for right turn
	 * arg: figure - To compare with turn color
	 */
	private boolean checkTurn(Piece figure) {
		return turn == figure.getColor();
	}

	/*
	 * Change the order of players
	 */
	private void changeOrder() {
		turn = turn.reverse();
	}

	/*
	 * Get from user x position
	 * arg: text - Text to print [can be current/new]
	 */
	private int askForXPosition(String text) {
		try {
			System.out.print("[" + turn.toString() + "]:\t" + text +
					" number -> ");
			int x = sc.nextInt();
			if (x < 1 || x > 8) {
				ColorPrint.printWarning("ENTER THE CORRECT POSITION [1:8]");
				return askForXPosition(text);
			}
			return x;
		} catch (InputMismatchException e) {
			ColorPrint.printError("INPUT ERROR");
			System.exit(1);
			return 0;
		}
	}

	/*
	 * Get from user y position
	 * arg: text - Text to print [can be current/new]
	 */
	private int askForYPosition(String text) {
		System.out.print("[" + turn.toString() + "]:\t" + text + " letter -> ");
		String yStr = sc.next();
		int y = Piece.letterToNumber(yStr);
		if (yStr.equals("pass")) {
			game = false;
			winnerColor = turn.reverse();
			ColorPrint.printSuccess(winnerColor.toString() + "'s WON!!!");
			System.exit(0);
		} else {
			if (y < 1 || y > 8) {
				ColorPrint.printWarning("ENTER THE CORRECT POSITION [A:H]");
				return askForYPosition(text);
			}
		}
		return y;
	}

	/*
	 * Print debug information
	 */
	private void printInformation(int x, int y, int newX, int newY) {
		System.out.println("--------------------------------------");
		System.out.println("CURRENT - " +
				boardArr[x - 1][y - 1].getPosition().getX() + " | " +
				boardArr[x - 1][y - 1].getPosition().getY());
		for (int i = 0; i < boardArr[x - 1][y - 1].getAvailableMoves().size();
				i++) {
			System.out.println(boardArr[x - 1][y - 1]
					.getAvailableMoves().get(i).getX() + " | " +
					boardArr[x - 1][y - 1].getAvailableMoves().get(i).getY());
		}
		System.out.println("POSITION_FREE - " + Piece.isPositionFree(
					new Position(newX, newY), boardArr[x - 1][y - 1]));
		System.out.println("--------------------------------------");
	}
}
