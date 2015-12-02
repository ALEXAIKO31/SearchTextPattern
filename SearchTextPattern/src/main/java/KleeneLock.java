/**
 * Created by ALEX on 12/11/2015.
 * Hereda de expression
 * Representa la cerradura de Kleene o cerradura estrella en expresiones regulares
 * Operaci�n unaria(Un operando)
 */
public class KleeneLock extends Expression {

    //expression1 Expresi�n  a la que se aplica la cerradura estrella
    State secondLast;           //Estado de apoyo para evitar un ciclo infinito
                                //La transici�n es equivalente dado a que es con cadena vac�a

    public KleeneLock(){
        this.type = '*';
    }

    @Override
    public State buildAFN(boolean isFinal) {
        this.expression1.buildAFN(false);
        this.start = new State(false,'\u0000');     //Crea el nuevo estado inical
        this.last = new State(isFinal,'\u0000');    //Crea el nuevo estado final
        this.secondLast = new State(false,'\u0000');//Crea el estado de apoyo
        if(expression1.type=='*') {
            this.start.nextState1=this.expression1.start;
            this.expression1.last.nextState1=this.last;
            return this.start;
        }

        this.start.nextState1=this.last;            //Hace la transici�n del nuevo estado inical, al nuevo estado final
        this.secondLast.nextState1=this.last;       //Hace la transici�n del estado de apoyo, al nuevo estado final
        this.secondLast.nextState2=this.start;      //Hace la transici�n del estado de apoyo, al nuevo estado inicial
        this.start.nextState2=this.expression1.start;    //Hace la transici�n del nuevo estado inicial, al estado inicial de la expresi�n regular
        this.expression1.last.nextState1=this.secondLast;//Hace la transici�n del estado final de la expresi�n regular, al estado de apoyo
        return this.start;                          //Regresa el nuevo estado inicial
    }
}
