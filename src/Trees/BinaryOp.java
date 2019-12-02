package Trees;

/** Represents a binary operation */
public class BinaryOp extends Expression {
    Expression leftOp; // left Operand
    Expression riteOp; // right Operand

    String op; // Operator

    BinaryEvaluation e; // Evaluation
    BinaryDerivative d; // Derivative

    /** Constructor: <br>
     * Creates a Binary Expression with left Operand lOp, right Operand rOp, Operator o, Evaluation
     * ev, and Derivative d. */
    public BinaryOp(Expression lOp, Expression rOp, String o, BinaryEvaluation ev,
        BinaryDerivative dr) {
        leftOp= lOp;
        riteOp= rOp;

        op= o;

        e= ev;
        d= dr;
    }

    public @Override double eval() {
        return e.eval(leftOp.eval(), riteOp.eval());
    }

    public @Override Expression differentiate(Variable v) {
        return d.differentiate(leftOp, riteOp, v);
    }

    public @Override String inOrder() {
        return "(" + leftOp + op + riteOp + ")";
    }

    public @Override boolean equals(Object ob) {
        if (ob == null || getClass() != ob.getClass()) return false;
        BinaryOp bob= (BinaryOp) ob;
        return leftOp.equals(bob.leftOp) && riteOp.equals(bob.riteOp);
    }

}

/** Addition */
class Add extends BinaryOp {
    public Add(Expression lOp, Expression rOp) {
        super(lOp, rOp,
            "+",
            (l, r) -> l + r,
            (l, r, v) -> new Add(l.differentiate(v), r.differentiate(v)));
    }
}

/** Subtraction */
class Sub extends BinaryOp {
    public Sub(Expression lOp, Expression rOp) {
        super(lOp, rOp,
            "-",
            (l, r) -> l - r,
            (l, r, v) -> new Sub(l.differentiate(v), r.differentiate(v)));
    }
}

/** Multiplication */
class Mul extends BinaryOp {
    public Mul(Expression lOp, Expression rOp) {
        super(lOp, rOp,
            "*",
            (l, r) -> l * r,
            (l, r, v) -> new Add(new Mul(l.differentiate(v), r), new Mul(l, r.differentiate(v))));
    }
}

/** Division */
class Div extends BinaryOp {
    public Div(Expression lOp, Expression rOp) {
        super(lOp, rOp,
            "/",
            (l, r) -> l / r,
            (l, r, v) -> new Div(
                new Sub(new Mul(l.differentiate(v), r), new Mul(l, r.differentiate(v))),
                new Pow(r, new DblLeaf(2))));
    }
}

/** Exponentiation */
class Pow extends BinaryOp {
    public Pow(Expression lOp, Expression rOp) {
        super(lOp, rOp,
            "^",
            (l, r) -> Math.pow(l, r),
            (l, r, v) -> new Mul(new Pow(l, r), new Add(new Mul(new Div(r, l), l.differentiate(v)),
                new Mul(new Log(l), r.differentiate(v)))));
    }
}
