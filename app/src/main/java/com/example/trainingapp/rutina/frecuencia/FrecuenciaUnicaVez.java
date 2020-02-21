package com.example.trainingapp.rutina.frecuencia;

import com.example.trainingapp.utilidad.Visitor;
import com.example.trainingapp.utilidad.registroTiempo.Fecha;
import com.example.trainingapp.utilidad.registroTiempo.Hora;

/**
 * Representacino de una Frecuencia que indica que la Rutina debe realizarse una sola vez
 */
public class FrecuenciaUnicaVez extends Frecuencia {

    protected Fecha fecha;

    /**
     * Constructor de la clase, setea fecha y hora de la frecuencia
     * @param hora Hora de la frecuencia
     * @param fecha Fecha de la frecuencia
     */
    public FrecuenciaUnicaVez(Hora hora, Fecha fecha){
        super(hora);
        this.fecha = fecha;
    }

    /**
     * Constructor de la clase, crea una frecuencia con fecha 1/1/1 y hora 00:00
     */
    public FrecuenciaUnicaVez(){
        super(new Hora(0,0));
        fecha = new Fecha(1,1,1);
    }

    /**
     * Devuelve la fecha de la frecuencia
     * @return Fecha de la frecuencia
     */
    public Fecha getFecha(){
        return fecha;
    }

    /**
     * Setea la fecha de la frecuencia
     * @param fecha Fecha de la frecuencia
     */
    public void setFecha(Fecha fecha){
        this.fecha = fecha;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
