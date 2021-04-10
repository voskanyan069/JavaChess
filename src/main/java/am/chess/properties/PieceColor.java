package am.chess.properties;

public enum PieceColor {
    WHITE("white"),
    BLACK("black"),
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
}
