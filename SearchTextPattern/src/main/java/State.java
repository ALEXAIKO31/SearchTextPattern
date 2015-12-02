/**
 * Created by ALEX on 12/11/2015.
 * Estado del Autómata finito no determinista
 */
public class State {

    State nextState1;           //Cuenta con 2 posibles transiciones
    State nextState2;           //La segunda se encuentra en null si no se requiere
    boolean isFinal;            //Determina si es el estado final
    char transition;            //Símbolo con el que se hace la transición

    public State(boolean isFinal,char transition){      //Constructor
        this.isFinal=isFinal;                           //El se asigna si es o no estado final
        this.transition=transition;                     //Asigna el símbolo de transición
        nextState1=null;                                //Inicializa las transiciones e null
        nextState2=null;                                //      estas se modifican durante
                                                        //      la creación de autómata
    }

    public boolean goNextState(String sentence) {       //Función de transición recursiva, recorre todos los estados
        if (transition == '\u0000')     //Revisa si la transicióm es de cadena vacia '\u0000' es al caracter null
            if (nextState1 == null)     //Si se cumple significa que no hay transiciones,
                return isFinal&&sentence.length()==0;//      por lo tanto regresa el valor del estado isFinal y si la cadena no está vacia se regresa falso
            else if (nextState2 == null)//Si se cumple significa que solo tiene una transición
                return nextState1.goNextState(sentence); //Pasa al siguiente estado sin consumir ningún símbolo
            else                        //Si se llega a este punto, hay 2 tranciciones
                return nextState1.goNextState(sentence) || nextState2.goNextState(sentence); //Pasa a los 2 siguientes transiciones sin gastar símbolos
        if (sentence.length() == 0)     //Si la cadena se consume
            return isFinal;             //      regresa el valor isFinal
        if (transition == sentence.charAt(0)) //El siguiente caracter está definido para la trancición
            if (nextState2 == null)     //Solo tiene una transición
                return nextState1.goNextState(sentence.substring(1));   //Continua al siguiente estado gastando un caracter
            else                        //Tiene dos transiciones, lo cual es imposible dado que solo puede darse ese caso si hay una transición con cadenas vacias
                return nextState1.goNextState(sentence.substring(1)) || nextState2.goNextState(sentence.substring(1));  //Se puso por casos de pruebas futuras
        return false;                   //La transición con el símbolo no esta definida, por lo que termina en estado de error y sale de la función
    }
}