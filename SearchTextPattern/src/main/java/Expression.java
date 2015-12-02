/**
 * Created by ALEX on 12/11/2015.
 * Una clase abstracta de la cual heredan los tipos de expresiones regulares que existen
 */
public abstract class Expression {

    State start;        //Estado inicial del AFN resultante de la expresi�n regular
    State last;         //Estado final del AFN resultante de la expresi�n regular
    Expression expression1; //Expresi�n interna para las expresiones unarias y binarias
    Expression expression2; //Expresi�n interna para las expresiones binarias
    char type;

    public abstract State buildAFN(boolean isFinal);    //Funci�n de creaci�n de AFN, a partir de la expresi�n regular
            // Esta funci�n es sobrescribida por las clases que la heredan, los tipos de expresiones regulares que hay.
}
