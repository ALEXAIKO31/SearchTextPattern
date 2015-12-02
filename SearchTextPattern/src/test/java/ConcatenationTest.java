import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ALEX on 20/11/2015.
 * Pruebas unitarias expresión concatenación
 */
public class ConcatenationTest {
    @Test
    public void TestConcatenationWithPrimitive(){
        Expression expression;
        expression= new Concatenation();
        expression.expression1= new Primitive('a');
        expression.expression2= new Primitive('b');
        expression.buildAFN(true); // Se crea la expresión regular r= a.b

        assertTrue(expression.start.goNextState("ab")); // Acepta ab
        assertFalse(expression.start.goNextState("a")); // Rechaza a
        assertFalse(expression.start.goNextState("b")); // Rechaza b
        assertFalse(expression.start.goNextState("ba")); // Rechaza si no es ab
        assertFalse(expression.start.goNextState("c")); // Rechaza si el caracter no es ni a, ni b
        assertFalse(expression.start.goNextState("")); // Rechaza cadena vacía
    }
}
