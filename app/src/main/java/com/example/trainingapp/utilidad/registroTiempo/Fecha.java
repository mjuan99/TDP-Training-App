package com.example.trainingapp.utilidad.registroTiempo;

import androidx.annotation.NonNull;

import com.example.trainingapp.utilidad.Formato;
import java.util.Calendar;

/**
 * Representacion basica para Fecha
 */
public class Fecha {

    protected int anio;
    protected int mes;
    protected int dia;

    /**
     * Constructor de la clase
     * @param dia Dia de la fecha (1-31)
     * @param mes Mes de la fecha (1-12)
     * @param anio Anio de la fecha
     */
    public Fecha(int dia,int mes, int anio){
        this.anio = anio;
        this.mes = mes;
        this.dia = dia;
    }

    /**
     * Devuelve el anio de la fecha
     * @return Anio de la fecha
     */
    public int getAnio(){
        return anio;
    }

    /**
     * Devuelve el mes de la fecha
     * @return Mes de la fecha
     */
    public int getMes(){
        return mes;
    }

    /**
     * Devuelva el dia de la fecha
     * @return Dia de la fecha
     */
    public int getDia(){
        return dia;
    }

    @Override
    public @NonNull String toString(){
        return Formato.formatoFecha(dia,mes,anio);
    }

    /**
     * Compara la fecha con la fecha actual
     * @return 1 si la fecha es posterior a la fecha actual, 0 si son iguales, -1 si es anterior
     */
    public int compareNow(){
        Calendar c = Calendar.getInstance();
        int fechaNow = c.get(Calendar.YEAR)*31*12+(c.get(Calendar.MONTH)+1)*31+c.get(Calendar.DAY_OF_MONTH);
        int fechaThis = anio*31*12+mes*31+dia;
        return Integer.compare(fechaThis, fechaNow);
    }
}
