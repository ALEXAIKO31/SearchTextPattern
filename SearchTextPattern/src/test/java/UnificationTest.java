import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ALEX on 20/11/2015.
 * Pruebas unitarias de la unificación de varias expresiones
 */
public class UnificationTest {

    @Test
    public void StartsWithAEndsWithC(){
        Expression father=null;
        Expression son1=null;
        Expression son2=null;

        Expression expression= new Concatenation();
        expression.expression1=new Primitive('A');
        expression.expression2=new Concatenation();

        father= expression.expression2;
        son2=new Primitive('C');
        son1=new KleeneLock();
        father.expression1=son1;
        father.expression2=son2;

        father= son1;
        son1= new Join();
        son2= null;
        father.expression1=son1;
        father.expression2=son2;

        father=son1;
        son1=new Primitive('A');
        son2=new Join();
        father.expression1=son1;
        father.expression2=son2;

        father=son2;
        son1= new Primitive('B');
        son2= new Primitive('C');
        father.expression1=son1;
        father.expression2=son2;

        expression.buildAFN(true); //Se crea la expresión regular r= A.(((A+(B+C))*).C) Alfabeto = {A,B,C}

        assertFalse(expression.start.goNextState("")); //No debe de aceptar cadena vacía
        assertTrue(expression.start.goNextState("AC")); //Acepta AC, probando que funciona la cerradura estrella con cadena vacia
        assertTrue(expression.start.goNextState("AAAACBCBCBACBABABABACBCAC")); //Acepta una cadena que inicia con A y termina con C sin importar lo que este en medio
        assertFalse(expression.start.goNextState("AAAACBCBCBACBABABABACBCACB")); //No acepta ya que no termina con C
        assertFalse(expression.start.goNextState("BAAAACBCBCBACBABABABACBCAC")); //No acepta ya que no inicia con A
        assertFalse(expression.start.goNextState("AAZBABC")); //No acepta ya que contiene Z que no pertenece al alfabeto
    }

}
