package am.chess.board;

import am.chess.pieces.Piece;
import am.chess.properties.PieceColor;

public class Board {
    private final Piece[][] board;
    private final String[] lines_numbers = new String[8];
    private final String[] boardImg = new String[10];

    public Board (Piece[][] board) {
        this.board = board;
    }

    public void init() {
        for (int i = 0; i < 8; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            Piece[] items = new Piece[8];
            int index = 0;
            for (int j = 0; j < 8; j++) {
                Piece item = board[i][j];
                items[index++] = item;
            }

            stringBuilder.append(" ").append(i + 1).append(" ⎪ ");
            for (int j = 0; j < 8; j++) {
                Piece item = items[j];
                if (item.getColor() == PieceColor.WHITE) {
                    stringBuilder.append(item.getCharacterW());
                } else {
                    stringBuilder.append(item.getCharacterB());
                }
                stringBuilder.append(" ⎪ ");
            }
            stringBuilder.append(i + 1).append(" ");

            lines_numbers[i] = stringBuilder.toString();
        }

        String line_letter = "   │ A ⎪ B ⎪ C ⎪ D ⎪ E ⎪ F ⎪ G ⎪ H ⎪   ";
        boardImg[0] = line_letter;
        boardImg[9] = line_letter;
        System.arraycopy(lines_numbers, 0, boardImg, 1, 8);
    }

    private String getBoardImg() {
//        final String line_break = "———┼———┼———┼———┼———┼———┼———┼———┼———┼———";
        final String line_break = "———————————————————————————————————————";
        return boardImg[0] + '\n' +
                line_break + '\n' +
                boardImg[1] + '\n' +
                line_break + '\n' +
                boardImg[2] + '\n' +
                line_break + '\n' +
                boardImg[3] + '\n' +
                line_break + '\n' +
                boardImg[4] + '\n' +
                line_break + '\n' +
                boardImg[5] + '\n' +
                line_break + '\n' +
                boardImg[6] + '\n' +
                line_break + '\n' +
                boardImg[7] + '\n' +
                line_break + '\n' +
                boardImg[8] + '\n' +
                line_break + '\n' +
                boardImg[9] + '\n';
    }

    public void refresh() {
        init();
        System.out.println('\n' + this.getBoardImg());
    }

    public Piece[][] getBoard() {
        return board;
    }
}
