package com.example.trainingapp.utilidad.registroTiempo;

import androidx.annotation.NonNull;

import com.example.trainingapp.utilidad.Formato;
import java.util.Calendar;

/**
 * Representacion basica para Hora
 */
public class Hora {

    protected int hora;
    protected int minuto;

    /**
     * Constructor de la clase
     * @param hora Hora (0-23)
     * @param minuto Minuto (0-59)
     */
    public Hora(int hora,int minuto){
        this.hora = hora;
        this.minuto = minuto;
    }

    /**
     * Devuelva las horas
     * @return Horas
     */
    public int getHora(){
        return hora;
    }

    /**
     * Devuelve los minutos
     * @return Minutos
     */
    public int getMinuto(){
        return minuto;
    }

    @Override
    public @NonNull String toString(){
        return Formato.formatoHora(hora,minuto);
    }

    /**
     * Compara la hora con la hora actual
     * @return 1 si la hora es posterior a la hora actual, 0 si son iguales, -1 si es anterior
     */
    public int compareNow(){
        Calendar c = Calendar.getInstance();
        int horaNow = c.get(Calendar.HOUR_OF_DAY)*60+c.get(Calendar.MINUTE);
        int horaThis = hora*60+minuto;
        return Integer.compare(horaThis, horaNow);
    }

}
