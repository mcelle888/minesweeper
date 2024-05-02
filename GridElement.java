public enum GridElement {
    HIDDEN('.'), EMPTY('0'), BOMB('*'), ONE('1'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8');

    private char symbol;
    private static  GridElement[] values = {
            EMPTY, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, BOMB
    };

    GridElement(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static GridElement fromInt(int bombsCount) {
        return values[bombsCount];
    }
}