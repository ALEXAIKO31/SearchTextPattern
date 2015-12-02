/**
 * Created by ALEX on 12/11/2015.
 * Una clase abstracta de la cual heredan los tipos de expresiones regulares que existen
 */
public abstract class Expression {

    State start;        //Estado inicial del AFN resultante de la expresión regular
    State last;         //Estado final del AFN resultante de la expresión regular
    Expression expression1; //Expresión interna para las expresiones unarias y binarias
    Expression expression2; //Expresión interna para las expresiones binarias
    char type;

    public abstract State buildAFN(boolean isFinal);    //Función de creación de AFN, a partir de la expresión regular
            // Esta función es sobrescribida por las clases que la heredan, los tipos de expresiones regulares que hay.
}
