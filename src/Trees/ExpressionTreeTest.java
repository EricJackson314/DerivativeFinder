package Trees;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class ExpressionTreeTest {

    void testDoubleLeaf() {
        fail("Not yet implemented");
    }

    @Test
    void testUniaryOp() {

        Variable x= new Variable("X");
        ExpressionTree leaf1= new VariableLeaf(x);

        for (UniaryOp.Operator op : UniaryOp.Operator.values()) {

            ExpressionTree expression= new UniaryOp(op, leaf1);
            System.out.println(expression.toString());
            if (op != UniaryOp.Operator.ABS) {
                System.out.println(expression.differentiate(x).toString());
            }
            System.out.println("------------");
        }
    }

    @Test
    void testBinaryOp() {
        ExpressionTree leaf1= new DoubleLeaf(3.0);
        ExpressionTree leaf2= new DoubleLeaf(4.0);

        ExpressionTree tree;

        // test addition
        tree= new BinaryOp(leaf1, BinaryOp.Operator.ADD, leaf2);
        assertEquals(true, 7 == tree.eval());
        assertEquals("(3.0 + 4.0)", tree.toString());

        // test subtraction
        tree= new BinaryOp(leaf1, BinaryOp.Operator.SUBTRACT, leaf2);
        assertEquals(true, -1 == tree.eval());
        assertEquals("(3.0 - 4.0)", tree.toString());

        // test multiplication
        tree= new BinaryOp(leaf1, BinaryOp.Operator.MULTIPLY, leaf2);
        assertEquals(true, 12 == tree.eval());
        assertEquals("(3.0 * 4.0)", tree.toString());

    }

    @Test
    void testVariables() {

        Variable x= new Variable("X");

        ExpressionTree leaf1= new DoubleLeaf(3.0);
        ExpressionTree leaf2= new DoubleLeaf(4.0);
        ExpressionTree leaf3= new VariableLeaf(x);

        ExpressionTree sin= new UniaryOp(UniaryOp.Operator.SIN, leaf3);
        ExpressionTree mul= new BinaryOp(leaf2, BinaryOp.Operator.MULTIPLY, sin);
        ExpressionTree add= new BinaryOp(leaf1, BinaryOp.Operator.ADD, mul);

        assertEquals("(3.0 + (4.0 * sinX))", add.toString());
    }

    @Test
    void testDerivatives() {
        Variable x= new Variable("X");

        ExpressionTree leaf1= new DoubleLeaf(3.0);
        ExpressionTree leaf2= new VariableLeaf(x);

        ExpressionTree sin= new UniaryOp(UniaryOp.Operator.SIN, leaf2);
        ExpressionTree expression= new BinaryOp(leaf1, BinaryOp.Operator.MULTIPLY, sin);
        ExpressionTree derivative= expression.differentiate(x);

        assertEquals("((3.0 * (cosX * 1.0)) + (0.0 * sinX))", derivative.toString());

        System.out.println("Find the derivative of " + expression.toString() + " with respect to " +
            x.toString() + ".\n");
        System.out.println(derivative + "\n\n");

    }

    @Test
    void test() {
        ExpressionTree leaf1= new DoubleLeaf(3.0);
        ExpressionTree leaf2= new DoubleLeaf(4.0);
        ExpressionTree leaf3= new DoubleLeaf(7.0);

        ExpressionTree sin= new UniaryOp(UniaryOp.Operator.SIN, leaf3);
        ExpressionTree mul= new BinaryOp(leaf2, BinaryOp.Operator.MULTIPLY, sin);
        ExpressionTree add= new BinaryOp(leaf1, BinaryOp.Operator.ADD, mul);

        assertEquals("(3.0 + (4.0 * sin7.0))", add.toString());
        assertEquals(true, 5.627946394875156 == add.eval());
    }

}
