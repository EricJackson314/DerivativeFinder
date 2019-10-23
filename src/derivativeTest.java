import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class derivativeTest {

    @Test
    void test() {
        // test addition and subtraction
        String a= "x-y+a-c";
        assertEquals("x'-y'+a'-c'", derivative.derive(a));
        // test division
        String b= "x/y";
        assertEquals("((y*x'+x*y')/(y)^2)", derivative.derive(b));
        // test multiplication
        String c= "x*y";
        assertEquals("(x*y'+y*x')", derivative.derive(c));
        // Putting it together...
        String d= "x/y+a*b-e";
        assertEquals("((y*x'+x*y')/(y)^2)+(a*b'+b*a')-e'", derivative.derive(d));
    }

}
