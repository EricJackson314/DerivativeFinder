package Trees;

public class Demo {

    public static void main(String[] args) {

        Variable x= new Variable("X");

        ExpressionTree leaf1= new DoubleLeaf(3.0);
        ExpressionTree leaf2= new VariableLeaf(x);

        ExpressionTree sin= new UniaryOp(UniaryOp.Operator.SIN, leaf2);
        ExpressionTree expression= new BinaryOp(leaf1, BinaryOp.Operator.MULTIPLY, sin);

        ExpressionTree derivative= expression.differentiate(x);
        Expression express= new Expression(derivative);

        express.simplify();
        System.out.println("Find the derivative of " + expression.toString() + " with respect to " +
            x.toString() + ".\n");
        System.out.println(express);

        System.out.println(express.derivative(x));

        System.out.println(express.derivative(x).derivative(x));

        System.out.println(express.derivative(x).derivative(x).derivative(x));
    }

}
