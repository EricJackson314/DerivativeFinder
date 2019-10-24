package Trees;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class ExpressionTreeTest {

    @Test
    void testUniaryOp() {
        // TODO: test Uniary Operations

    }

    @Test
    void testBinaryOp() {
        // TODO: Finish testing all Binary Operations
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
        Variable y= new Variable("Y", 3);

        ExpressionTree leaf1= new VariableLeaf(x);
        ExpressionTree leaf2= new VariableLeaf(y);

        // toString and inOrder
        assertEquals("X", leaf1.toString());
        // eval
        assertEquals(true, 3 == leaf2.eval());
        // differentiate
        assertEquals(new DoubleLeaf(1), leaf1.differentiate(x));
        assertEquals(new DoubleLeaf(0), leaf1.differentiate(y));
        // equals
        assertEquals(true, leaf1.equals(leaf1));
        assertEquals(false, leaf1.equals(null));
        assertEquals(false, leaf1.equals(leaf2));

    }

    @Test
    void testDoubles() {
        ExpressionTree x= new DoubleLeaf(3);
        ExpressionTree y= new DoubleLeaf(4);

        assertEquals(true, x.equals(x));
        assertEquals(false, x.equals(null));
        assertEquals(false, x.equals(y));
    }

    @Test
    void testDerivatives() {
        Variable x= new Variable("X");

        // 3* sin(x)
        ExpressionTree leaf1= new DoubleLeaf(3.0);
        ExpressionTree leaf2= new VariableLeaf(x);
        ExpressionTree sin= new UniaryOp(UniaryOp.Operator.SIN, leaf2);
        ExpressionTree expression= new BinaryOp(leaf1, BinaryOp.Operator.MULTIPLY, sin);

        ExpressionTree derivative= expression.differentiate(x);

        assertEquals("(3.0 * cos(X))", derivative.toString());
    }

    @Test
    void test() {
        ExpressionTree leaf1= new DoubleLeaf(3.0);
        ExpressionTree leaf2= new DoubleLeaf(4.0);
        ExpressionTree leaf3= new DoubleLeaf(7.0);

        ExpressionTree sin= new UniaryOp(UniaryOp.Operator.SIN, leaf3);
        ExpressionTree mul= new BinaryOp(leaf2, BinaryOp.Operator.MULTIPLY, sin);
        ExpressionTree add= new BinaryOp(leaf1, BinaryOp.Operator.ADD, mul);

        assertEquals("(3.0 + (4.0 * sin(7.0)))", add.toString());
        assertEquals(true, 5.627946394875156 == add.eval());
    }

}
