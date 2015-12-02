/**
 * Created by ALEX on 12/11/2015.
 * Hereda de expression
 * Representa la unión en expresiones regulares
 * Operación binaria(Dos operandos)
 */
public class Join extends Expression {

    //expression1 Contiene la expresión a unir de arriba
    //expression2 Contiene la expresión a unir de abajo
    //Abajo y arriba refieren a la posicion que se toma conceptualmente en la unión de expresiones regulares en el AFN

    public Join(){
        this.type = '+';
    }

    @Override
    public State buildAFN(boolean isFinal) {
        this.expression1.buildAFN(false);
        this.expression2.buildAFN(false);
        this.start = new State(false,'\u0000');     //Crea el nuevo estado inicial
        this.last = new State(isFinal,'\u0000');    //Crea el nuevo estado final
        this.start.nextState1=this.expression1.start;      //Hace la transición del nuevo estado inical, al estado inical de la expresión de arriba
        this.start.nextState2=this.expression2.start;    //Hace la transición del nuevo estado inical, al estado inical de la expresión de abajo
        this.expression1.last.nextState1=this.last;        //Hace la transición del estado final de la expresión de arriba, al nuevo estado final
        this.expression2.last.nextState1=this.last;      //Hace la transición del estado final de la expresión de abajo, al nuevo estado final
        return this.start;
    }
}