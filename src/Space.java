/*tile]
* observer*/

public abstract class Space implements Observer{

    protected World world;

    protected char symbol;
    protected String color;

    public char getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }

    public String toString() {
        return Color.get_message_with_color(symbol, color);
    }

    public abstract boolean accessible();
}
