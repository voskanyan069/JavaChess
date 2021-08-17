package am.chess.game;

/*
 * Prints with colors
 */
public class ColorPrint {
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void printSuccess(String text) {
        System.out.println(ANSI_GREEN + '\n' + text + '\n' + ANSI_RESET);
    }

    public static void printWarning(String text) {
        System.out.println(ANSI_YELLOW + '\n' + text + '\n' + ANSI_RESET);
    }

    public static void printError(String text) {
        System.out.println(ANSI_RED + '\n' + text + '\n' + ANSI_RESET);
    }
}
