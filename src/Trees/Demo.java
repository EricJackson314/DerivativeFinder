package Trees;

public class Demo {

    public static void main(String[] args) {

        Variable x= new Variable("X");

        // 3 * sin(x)
        ExpressionTree lf1= new VariableLeaf(x);
        ExpressionTree lf2= new DoubleLeaf(3.0);
        ExpressionTree sin= new UniaryOp(UniaryOp.Operator.SIN, lf1);
        ExpressionTree mul= new BinaryOp(lf2, BinaryOp.Operator.MULTIPLY, sin);

        // exp(cos(x))
        ExpressionTree lf3= new VariableLeaf(x);
        ExpressionTree cos= new UniaryOp(UniaryOp.Operator.COS, lf3);
        ExpressionTree exp= new UniaryOp(UniaryOp.Operator.EXP, cos);

        // 3* sin(x) + exp(cos(x))
        ExpressionTree expression= new BinaryOp(mul, BinaryOp.Operator.ADD, exp);

        ExpressionTree derivative= expression.differentiate(x);

        System.out.println("  f(x)= " + expression);
        System.out.println(" f'(x)= " + derivative);
        System.out.println("f''(x)= " + derivative.differentiate(x));
    }

}
