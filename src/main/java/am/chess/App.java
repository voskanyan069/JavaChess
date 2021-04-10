package am.chess;

import am.chess.game.Game;

/*
 * TODO: Show available moves for this piece (Remain: Rook, Queen)
 * TODO: Change piece color type from string to am.chess.properties.PieceColor enum object
 * FIXME: Queen show available moves function work incorrectly
 * TODO: Checkmate check
 * TODO: "Shax"
*/

public class App {
    public static void main( String[] args ) {
        Game game = new Game();
        game.start();
    }
}
