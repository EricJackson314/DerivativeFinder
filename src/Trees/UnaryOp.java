package Trees;

public class UnaryOp extends Expression {
    Expression operand;

    String op;

    UnaryEvaluation e;
    UnaryDerivative d;

    public UnaryOp(Expression Op, String o, UnaryEvaluation ev, UnaryDerivative dr) {
        operand= Op;

        op= o;

        e= ev;
        d= dr;
    }

    public @Override double eval() {
        return e.eval(operand.eval());
    }

    public @Override Expression differentiate(Variable v) {
        return new Mul(d.differentiate(operand, v), operand.differentiate(v));
    }

    public @Override String inOrder() {
        return op.toString() + operand;
    }

    public @Override boolean equals(Object ob) {
        if (ob == null || getClass() != ob.getClass()) return false;
        UnaryOp uob= (UnaryOp) ob;
        return operand.equals(uob.operand);
    }

}

class Abs extends UnaryOp {
    public Abs(Expression op) {
        super(op,
            "abs",
            (o) -> Math.abs(o),
            (o, v) -> { throw new RuntimeException(); });
    }
}

class Neg extends UnaryOp {
    public Neg(Expression op) {
        super(op,
            "-",
            (o) -> -o,
            (o, v) -> new DblLeaf(-1));
    }
}

class Sin extends UnaryOp {
    public Sin(Expression op) {
        super(op,
            "sin",
            (o) -> Math.sin(o),
            (o, v) -> new Cos(o));
    }
}

class Cos extends UnaryOp {
    public Cos(Expression op) {
        super(op,
            "cos",
            (o) -> Math.cos(o),
            (o, v) -> new Neg(new Sin(o)));
    }
}

class Tan extends UnaryOp {
    public Tan(Expression op) {
        super(op,
            "tan",
            (o) -> Math.tan(o),
            (o, v) -> new Pow(new Sec(o), new DblLeaf(2)));
    }
}

class Csc extends UnaryOp {
    public Csc(Expression op) {
        super(op,
            "csc",
            (o) -> 1 / Math.sin(o),
            (o, v) -> new Neg(new Mul(new Csc(o), new Cot(o))));
    }
}

class Sec extends UnaryOp {
    public Sec(Expression op) {
        super(op,
            "sec",
            (o) -> 1 / Math.cos(o),
            (o, v) -> new Mul(new Sec(o), new Tan(o)));
    }
}

class Cot extends UnaryOp {
    public Cot(Expression op) {
        super(op,
            "cot",
            (o) -> 1 / Math.tan(o),
            (o, v) -> new Neg(new Pow(new Csc(o), new DblLeaf(2))));
    }
}

class ArcSin extends UnaryOp {
    public ArcSin(Expression op) {
        super(op,
            "arcsin",
            (o) -> Math.asin(o),
            (o, v) -> new Div(new DblLeaf(1),
                new Pow(new Sub(new DblLeaf(1), new Pow(o, new DblLeaf(2))), new DblLeaf(.5))));
    }
}

class ArcCos extends UnaryOp {
    public ArcCos(Expression op) {
        super(op,
            "arccos",
            (o) -> Math.acos(o),
            (o, v) -> new Neg(new Div(new DblLeaf(1),
                new Pow(new Sub(new DblLeaf(1), new Pow(o, new DblLeaf(2))), new DblLeaf(.5)))));
    }
}

class ArcTan extends UnaryOp {
    public ArcTan(Expression op) {
        super(op,
            "arccot",
            (o) -> Math.atan(o),
            (o, v) -> new Div(new DblLeaf(1), new Add(new Pow(o, new DblLeaf(2)), new DblLeaf(1))));
    }
}

class ArcCsc extends UnaryOp {
    public ArcCsc(Expression op) {
        super(op,
            "arccsc",
            (o) -> Math.asin(1 / o),
            (o, v) -> new Neg(new Div(new DblLeaf(1), new Mul(new Abs(o),
                new Pow(new Sub(new Pow(o, new DblLeaf(2)), new DblLeaf(1)), new DblLeaf(.5))))));
    }
}

class ArcSec extends UnaryOp {
    public ArcSec(Expression op) {
        super(op,
            "arcsec",
            (o) -> Math.acos(1 / o),
            (o, v) -> new Div(new DblLeaf(1), new Mul(new Abs(o),
                new Pow(new Sub(new Pow(o, new DblLeaf(2)), new DblLeaf(1)), new DblLeaf(.5)))));
    }
}

class ArcCot extends UnaryOp {
    public ArcCot(Expression op) {
        super(op,
            "arccot",
            (o) -> Math.atan(1 / o),
            (o, v) -> new Neg(
                new Div(new DblLeaf(1), new Add(new Pow(o, new DblLeaf(2)), new DblLeaf(1)))));
    }
}

class Log extends UnaryOp {
    public Log(Expression op) {
        super(op,
            "log",
            (o) -> Math.log(o),
            (o, v) -> new Div(new DblLeaf(1), o));
    }
}

class Exp extends UnaryOp {
    public Exp(Expression op) {
        super(op,
            "exp",
            (o) -> Math.exp(o),
            (o, v) -> new Exp(o));
    }
}