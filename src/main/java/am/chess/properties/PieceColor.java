package am.chess.properties;

public enum PieceColor {
    WHITE("WHITE"),
    BLACK("BLACK"),
    NONE;

    String color;

    PieceColor() {}

    PieceColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }

	public PieceColor reverse() {
		if (this == WHITE) {
			return BLACK;
		}
		return WHITE;
	}
}
