/**
 * Created by ALEX on 12/11/2015.
 * Hereda de expression
 * Representa la concatenación en expresiones regulares
 * Operación binaria(Dos operandos)
 */
public class Concatenation extends Expression{

    //expression1 Contiene la expresión a concatenar del lado izquierdo
    //expression2 Contiene la expresión a concatenar del lado derecho

    public Concatenation(){
        this.type = '.';
    }

    @Override
    public State buildAFN(boolean isFinal) {
        this.expression1.buildAFN(false);
        this.expression2.buildAFN(false);
        this.start=new State(false,'\u0000');       //Crea el nuevo estado inicial
        this.last=new State(isFinal,'\u0000');      //Crea el nuevo estado final
        this.start.nextState1=this.expression1.start;      //Hace la transición del estado inicial, al estado inical de la expresión de la izquierda
        this.expression1.last.nextState1=this.expression2.start; //Hace la transición del estado final de la expresión de la izquierda, al estado inical de la expresión de la derecha
        this.expression2.last.nextState1=this.last;       //Hace la transición del estado final de la expresion de la derecha, al nuevo estado final
        return this.start;                          //Regresa el nuevo estado inicial
    }
}
