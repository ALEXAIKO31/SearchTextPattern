/**
 * Created by ALEX on 12/11/2015.
 * Hereda de expression
 * Representa las expresiones regulares primitivas
 */
public class Primitive extends Expression{

    char value;

    public Primitive(char value){
        this.value = value;
        this.type = 'p';
    }
    @Override
    public State buildAFN(boolean isFinal) {
        this.start= new State(false,value);     //Crea el estado inicial del AFN y la transición con el símbolo indicado
        this.last= new State(isFinal,'\u0000'); //Crea el estado final
        this.start.nextState1= this.last;       //Crea la transición del estado inicial al final
        return this.start;                      //Regresa el estado inicial del AFN
    }
}
