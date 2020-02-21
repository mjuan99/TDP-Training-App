package com.example.trainingapp.rutina.frecuencia;

import com.example.trainingapp.utilidad.Visitor;
import com.example.trainingapp.utilidad.registroTiempo.Hora;

/**
 * Representacion de la Frecuencia de una Rutina, indica cuando debe realizarse la Rutina
 */
public abstract class Frecuencia {

    protected Hora hora;

    /**
     * Constructor de la clase, setea la hora de la frecuencia
     * @param hora Hora de la frecuencia
     */
    protected Frecuencia(Hora hora){
        this.hora = hora;
    }

    /**
     * Devuelve la hora de la frecuencia
     * @return Hora de la frecuencia
     */
    public Hora getHora(){
        return hora;
    }

    /**
     * Setea la hora de la frecuencia
     * @param hora Hora de la frecuencia
     */
    public void setHora(Hora hora){
        this.hora = hora;
    }

    /**
     * Acepta el visitor
     * @param visitor Visitor a aceptar
     */
    public abstract void accept(Visitor visitor);
}
