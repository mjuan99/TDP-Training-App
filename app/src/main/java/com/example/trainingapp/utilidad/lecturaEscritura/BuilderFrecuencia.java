package com.example.trainingapp.utilidad.lecturaEscritura;

import com.example.trainingapp.rutina.frecuencia.Frecuencia;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;
import com.example.trainingapp.utilidad.registroTiempo.Fecha;
import com.example.trainingapp.utilidad.registroTiempo.Hora;

/**
 * Builder utilizado para simplificar la creacion de frecuencias al decodificarlas en LecturaEscritura
 */
public class BuilderFrecuencia {

    protected int tipo;
    protected FrecuenciaPorSemana frecSemana;
    protected FrecuenciaUnicaVez frecUnica;
    public final static int TIPO_SEMANA = 0;
    public final static int TIPO_UNICA = 1;

    /**
     * Constructor de la clase, inicializa atributos
     */
    public BuilderFrecuencia(){
        tipo = -1;
        frecUnica = null;
        frecSemana = null;
    }

    /**
     * Setea el tipo de frecuencia que se creara
     * @param tipo Tipo de frecuencia a crear
     */
    public void setTipo(int tipo){
        this.tipo = tipo;
        switch (tipo){
            case TIPO_SEMANA:
                frecSemana = new FrecuenciaPorSemana();
                break;
            case TIPO_UNICA:
                frecUnica = new FrecuenciaUnicaVez();
                break;
        }
    }

    /**
     * Setea la hora de la frecuencia que se creara
     * @param horaSt Hora de la frecuencia a crear (hh:mm)
     */
    public void setHora(String horaSt){
        String[] partes = horaSt.split(":");
        Hora hora = new Hora(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]));
        switch (tipo){
            case TIPO_SEMANA:
                frecSemana.setHora(hora);
                break;
            case TIPO_UNICA:
                frecUnica.setHora(hora);
                break;
        }
    }

    /**
     * Setea como se repetira la frecuencia que se creara
     * @param repetir Forma en la que se repetira la frecuencia a crear
     */
    public void setRepetir(String repetir){
        String[] fecha;
        boolean[] dias;
        switch (tipo){
            case TIPO_SEMANA:
                dias = new boolean[7];
                for(int i = 0;i<7;i++)
                    dias[i] = repetir.charAt(i)=='1';
                frecSemana.setDias(dias);
                break;
            case TIPO_UNICA:
                fecha = repetir.split("/");
                frecUnica.setFecha(new Fecha(Integer.parseInt(fecha[0]),Integer.parseInt(fecha[1]),Integer.parseInt(fecha[2])));
                break;
        }
    }

    /**
     * Crea la frecuencia
     * @return Frecuencia creada
     */
    public Frecuencia build(){
        Frecuencia frecuencia = null;
        switch (tipo){
            case TIPO_SEMANA:
                frecuencia = frecSemana;
                frecUnica=null;
                break;
            case TIPO_UNICA:
                frecuencia = frecUnica;
                frecUnica=null;
                break;
        }
        tipo = -1;
        return frecuencia;
    }
}
