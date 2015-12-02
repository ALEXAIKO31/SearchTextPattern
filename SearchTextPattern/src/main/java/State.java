/**
 * Created by ALEX on 12/11/2015.
 * Estado del Aut�mata finito no determinista
 */
public class State {

    State nextState1;           //Cuenta con 2 posibles transiciones
    State nextState2;           //La segunda se encuentra en null si no se requiere
    boolean isFinal;            //Determina si es el estado final
    char transition;            //S�mbolo con el que se hace la transici�n

    public State(boolean isFinal,char transition){      //Constructor
        this.isFinal=isFinal;                           //El se asigna si es o no estado final
        this.transition=transition;                     //Asigna el s�mbolo de transici�n
        nextState1=null;                                //Inicializa las transiciones e null
        nextState2=null;                                //      estas se modifican durante
                                                        //      la creaci�n de aut�mata
    }

    public boolean goNextState(String sentence) {       //Funci�n de transici�n recursiva, recorre todos los estados
        if (transition == '\u0000')     //Revisa si la transici�m es de cadena vacia '\u0000' es al caracter null
            if (nextState1 == null)     //Si se cumple significa que no hay transiciones,
                return isFinal&&sentence.length()==0;//      por lo tanto regresa el valor del estado isFinal y si la cadena no est� vacia se regresa falso
            else if (nextState2 == null)//Si se cumple significa que solo tiene una transici�n
                return nextState1.goNextState(sentence); //Pasa al siguiente estado sin consumir ning�n s�mbolo
            else                        //Si se llega a este punto, hay 2 tranciciones
                return nextState1.goNextState(sentence) || nextState2.goNextState(sentence); //Pasa a los 2 siguientes transiciones sin gastar s�mbolos
        if (sentence.length() == 0)     //Si la cadena se consume
            return isFinal;             //      regresa el valor isFinal
        if (transition == sentence.charAt(0)) //El siguiente caracter est� definido para la trancici�n
            if (nextState2 == null)     //Solo tiene una transici�n
                return nextState1.goNextState(sentence.substring(1));   //Continua al siguiente estado gastando un caracter
            else                        //Tiene dos transiciones, lo cual es imposible dado que solo puede darse ese caso si hay una transici�n con cadenas vacias
                return nextState1.goNextState(sentence.substring(1)) || nextState2.goNextState(sentence.substring(1));  //Se puso por casos de pruebas futuras
        return false;                   //La transici�n con el s�mbolo no esta definida, por lo que termina en estado de error y sale de la funci�n
    }
}