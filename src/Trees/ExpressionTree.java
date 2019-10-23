package Trees;

/** An instance represents an expression tree. */
public abstract class ExpressionTree {

    /** Return the value of this tree */
    abstract double eval();

    /** Return the in-order of this tree, with binary operations parenthesized. */
    abstract String inOrder();

    /** Represents this expression tree-- its in-order. */
    public @Override String toString() {
        return inOrder();
    }

    /** Returns the derivative of this tree with respect to variable v. */
    abstract ExpressionTree differentiate(Variable v);

}
