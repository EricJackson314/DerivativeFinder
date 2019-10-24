package Trees;

/** represents a leaf with a variable value */
class VariableLeaf extends ExpressionTree {

    Variable v; // value of this leaf

    /** Constructor: instance with variable v */
    VariableLeaf(Variable v) {
        this.v= v;
    }

    /** returns value of this tree */
    public @Override double eval() {
        return v.getValue();
    }

    /** Returns the symbol of this leaf as a string. */
    public @Override String inOrder() {
        return v.getSymbol();
    }

    /** Returns the derivative of this variable with respect to v. */
    public @Override ExpressionTree differentiate(Variable v) {
        if (this.v == v) return new DoubleLeaf(1);
        return new DoubleLeaf(0);
    }

    /** Returns a simplified version of this tree */
    public @Override ExpressionTree simplify() {
        return new VariableLeaf(v);
    }

    /** Returns true iff <br>
     * 1. ob is a VariableLeaf <br>
     * 2. ob and this have the same variable */
    public @Override boolean equals(Object ob) {
        if (ob == null || ob.getClass() != getClass()) return false;
        VariableLeaf vob= (VariableLeaf) ob;
        return vob.v == v;

    }

}
