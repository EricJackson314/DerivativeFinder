package Trees;

/** An instance represents a variable in an expression tree */
public class VarLeaf extends Expression {

    Variable v; // value of this leaf

    /** Constructor: instance with variable v */
    VarLeaf(Variable v) {
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
    public @Override Expression differentiate(Variable v) {
        if (this.v.equals(v)) return new DblLeaf(1);
        return new DblLeaf(0);
    }

    /** Returns a simplified version of this tree */
    public Expression simplify() {
        return new VarLeaf(v);
    }

    /** Returns true iff <br>
     * 1. ob is a VariableLeaf <br>
     * 2. ob and this have the same variable */
    public @Override boolean equals(Object ob) {
        if (ob == null || ob.getClass() != getClass()) return false;
        VarLeaf vob= (VarLeaf) ob;
        return vob.v.equals(v);

    }

}
