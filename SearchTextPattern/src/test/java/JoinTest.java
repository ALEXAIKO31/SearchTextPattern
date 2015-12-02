import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ALEX on 20/11/2015.
 * Pruebas unitarias para expresión Suma
 */
public class JoinTest {

    @Test
    public void TestJoinWithPrimitive(){
        Expression expression;
        expression= new Join();
        expression.expression1= new Primitive('a');
        expression.expression2= new Primitive('b');
        expression.buildAFN(true); // Se crea la expresión regular r= a+b
        assertTrue(expression.start.goNextState("a")); // Acepta a
        assertTrue(expression.start.goNextState("b")); // Acepta b
        assertFalse(expression.start.goNextState("aa")); // Rechaza si hay mas de un caracter aunque sea el caracter correcto
        assertFalse(expression.start.goNextState("c")); // Rechaza si el caracter no es ni a, ni b
        assertFalse(expression.start.goNextState("")); // Rechaza cadena vacía
    }
}
