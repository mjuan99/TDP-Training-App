package com.example.trainingapp.rutina.frecuencia;

import com.example.trainingapp.utilidad.Visitor;
import com.example.trainingapp.utilidad.registroTiempo.Hora;

/**
 * Representacion de una Frecuencia en la cual se indican los dias de la semana en los que debe realizarse una Rutina
 */
public class FrecuenciaPorSemana extends Frecuencia {

    protected boolean[] dias;

    /**
     * Constructor de la clase, setea la hora y dias de la frecuencia
     * @param hora Hora de la frecuencia
     * @param dias Dias de la frecuencia (lun[0] - dom[6])
     */
    public FrecuenciaPorSemana(Hora hora, boolean[] dias){
        super(hora);
        this.dias = new boolean[7];
        if(dias.length==7)
            System.arraycopy(dias, 0, this.dias, 0, 7);
    }

    /**
     * Constructor de clase, crea una frecuencia con todos los dias marcados para la hora 00:00
     */
    public FrecuenciaPorSemana(){
        super(new Hora(0,0));
        dias = new boolean[7];
        for (int i = 0;i<7;i++)
            dias[i] = true;
    }

    /**
     * Devuelve los los dias marcados de la frecuencia
     * @return Dias marcados de la frecuencia
     */
    public boolean[] getDias(){
        return dias;
    }

    /**
     * Setea los dias marcados de la frecuencia
     * @param dias Dias de la frecuencia
     */
    public void setDias(boolean[] dias){
        if(dias.length==7)
            System.arraycopy(dias, 0, this.dias, 0, 7);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
