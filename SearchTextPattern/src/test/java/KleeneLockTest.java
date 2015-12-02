import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ALEX on 20/11/2015.
 * Pruebas unitarias para la expresión cerradura estrella
 */
public class KleeneLockTest {
    Expression expression;
    Expression father;
    Expression son1;
    Expression son2;

    @Test
    public void TestKleeneLockWithPrimitive(){

        expression= new KleeneLock();
        expression.expression1= new Primitive('a');
        expression.buildAFN(true); // Se crea la expresión regular r=(a)*
        assertTrue(expression.start.goNextState("aaaaaaa")); // Acepta a repetido las veces que sea
        assertFalse(expression.start.goNextState("aaaaba")); // Rechaza ya que hay una b
        assertTrue(expression.start.goNextState("a")); // Acepta a una vez
        assertTrue(expression.start.goNextState("")); // Acepta cadena vacía
        assertFalse(expression.start.goNextState("b")); // Rechaza ya que no acepta b
    }

    @Test
        public void TestKleeneLockWithJoin() {

        expression = new KleeneLock();
        expression.expression1 = new Join();

        father = expression.expression1;
        son1 = new Primitive('a');
        son2 = new Primitive('b');
        father.expression1 = son1;
        father.expression2 = son2;

        expression.buildAFN(true); //Se crea expresión regular r=(a+b)*

        assertTrue(expression.start.goNextState("abababababababbabababab")); // Acepta una cantidad n de a y b
        assertFalse(expression.start.goNextState("afshads")); // Rechaza si hay símbolos que no sean a o b
        assertTrue(expression.start.goNextState("b")); // Acepta una sola b
        assertTrue(expression.start.goNextState("a")); // Acepta un asola a
        assertTrue(expression.start.goNextState("bbbbbbbbb")); // Acepta una cantidad n de b
        assertTrue(expression.start.goNextState("aaaaaaaaaaaa")); // Acepta una cantidad n de a
        assertTrue(expression.start.goNextState("")); // Acepta cadena vacía
        assertFalse(expression.start.goNextState("c")); // Rechaza un símbolo que no es ni a, ni b
    }

    @Test
    public void TestKleeneLockWithConcatenation() {

        expression = new KleeneLock();
        expression.expression1 = new Concatenation();

        father = expression.expression1;
        son1 = new Primitive('a');
        son2 = new Primitive('b');
        father.expression1 = son1;
        father.expression2 = son2;

        expression.buildAFN(true); //Se crea expresión regular r=(a.b)*

        assertTrue(expression.start.goNextState("ab")); // Acepta ab una vez
        assertFalse(expression.start.goNextState("afshads")); // Rechaza si hay símbolos que no sean a o b
        assertFalse(expression.start.goNextState("b")); // Rechaza una sola b
        assertFalse(expression.start.goNextState("a")); // Rechaza un asola a
        assertTrue(expression.start.goNextState("ababababababababab")); // Acepta una cantidad n de ab
        assertFalse(expression.start.goNextState("babababababababababababab")); // Rechaza ya que hay una b al inicio
        assertTrue(expression.start.goNextState("")); // Acepta cadena vacía
    }
}
