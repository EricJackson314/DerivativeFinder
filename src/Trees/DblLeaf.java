package Trees;

public class DblLeaf extends Expression {
    double val;

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
