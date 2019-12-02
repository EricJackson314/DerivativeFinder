package Trees;

/** An instance represents a double in an expression tree */
public class DblLeaf extends Expression {
    double val; // value of this leaf

    /** Constructor: instance with value val */
    public DblLeaf(double val) {
        this.val= val;
    }

    @Override
    public double eval() {
        return val;
    }

    @Override
    public String inOrder() {
        return Double.toString(val);
    }

    @Override
    public Expression differentiate(Variable v) {
        return new DblLeaf(0);
    }

}
