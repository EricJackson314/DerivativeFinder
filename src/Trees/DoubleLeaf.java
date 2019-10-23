package Trees;

/** Represents a leaf with a double value */
class DoubleLeaf extends ExpressionTree {

    double value; // value of this leaf

    /** Constructor: an instance with value v. */
    DoubleLeaf(double v) {
        value= v;
    }

    /** Returns the value of this leaf. */
    public @Override double eval() {
        return value;
    }

    /** Return the value of this leaf as a string. */
    public @Override String inOrder() {
        return Double.toString(value);
    }

    /** Returns the derivative of this tree with respect to v. */
    public @Override ExpressionTree differentiate(Variable v) {
        return new DoubleLeaf(0);
    }

    /** Returns true iff <br>
     * 1. ob is a DoubleLeaf <br>
     * 2. ob and this have the same value */
    public @Override boolean equals(Object ob) {
        if (ob == null || ob.getClass() != getClass()) return false;
        DoubleLeaf dob= (DoubleLeaf) ob;
        return dob.value == value;
    }

}
