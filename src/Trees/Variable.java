package Trees;

/** An instance represents a variable with both a symbol and a value */
public class Variable {
    private String symbol; // String which represents this variable
    private Double value; // Value stored in this variable

    /** Constructor: instance with symbol s and value 0 */
    public Variable(String s) {
        symbol= s;
        value= 0.0;
    }

    /** Constructor: instance with symbol s and value v */
    public Variable(String s, double v) {
        symbol= s;
        value= v;
    }

    /** returns symbol */
    public String getSymbol() {
        return symbol;
    }

    /** returns value */
    public double getValue() {
        return value;
    }

    /** set value to v */
    public void setValue(double v) {
        value= v;
    }

    /** Representation of this variable--its symbol */
    public @Override String toString() {
        return getSymbol();
    }

}
