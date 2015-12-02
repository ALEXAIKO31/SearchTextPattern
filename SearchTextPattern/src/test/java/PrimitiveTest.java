import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ALEX on 20/11/2015.
 * Pruebas unitarias para la expresión primitiva
 */
public class PrimitiveTest {

    @Test
    public void TestOneChar(){
        Expression expression;

        expression= new Primitive('a');
        expression.buildAFN(true); // Se crea la expresión regular r=a
        assertTrue(expression.start.goNextState("a")); //Solo debe aceptar a
        assertFalse(expression.start.goNextState("aa")); // Rechaza si hay mas de un caracter aunque sea el caracter correcto
        assertFalse(expression.start.goNextState("b")); // Rechaza si el caracter no fue el esperado en este caso b
        assertFalse(expression.start.goNextState("")); // Rechaza cadena vacia

        expression= new Primitive('b'); // Se repiten las pruebas con otro caracter
        expression.buildAFN(true); // Se crea la expresión regular r=b
        assertFalse(expression.start.goNextState("a")); // Rechaza si el caracter no fue el esperado en este caso a
        assertFalse(expression.start.goNextState("bb")); // Rechaza si hay mas de un caracter aunque sea el caracter correcto
        assertTrue(expression.start.goNextState("b")); //Solo debe aceptar b
        assertFalse(expression.start.goNextState("")); // Rechaza cadena vacia
    }
}
