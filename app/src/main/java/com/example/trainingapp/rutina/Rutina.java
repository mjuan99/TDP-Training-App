package com.example.trainingapp.rutina;

import com.example.trainingapp.rutina.frecuencia.Frecuencia;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Representacion de una Rutina de entrenamiento
 */
public class Rutina {

    protected String nombre;
    protected Frecuencia frecuencia;
    protected List<Serie> series;
    protected long id;

    /**
     * Constructor de la clase, setea nombre y frecuencia de la rutina
     * @param nombre Nombre de la rutina
     * @param frecuencia Frecuencia de la rutina
     */
    public Rutina(String nombre,Frecuencia frecuencia){
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        series = new ArrayList<>();
        id = System.currentTimeMillis();
    }

    /**
     * Constructor de la clase, crea una rutina sin nombre y sin frecuencia
     */
    public Rutina(){
        this("",null);
    }

    /**
     * Setea el nombre de la rutina
     * @param nombre Nombre de la rutina
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * Devuelve la frecuencia de la rutina
     * @return Frecuencia de la rutina
     */
    public Frecuencia getFrecuencia(){
        return frecuencia;
    }

    /**
     * Setea la frecuencia de la rutina
     * @param frecuencia Frecuencia de la rutina
     */
    public void setFrecuencia(Frecuencia frecuencia){
        this.frecuencia = frecuencia;
    }

    /**
     * Setea las series de la rutina
     * @param series Series de la rutina
     */
    public void setSeries(Collection<Serie> series){
        this.series.clear();
        this.series.addAll(series);
    }

    /**
     * Devuelve las series de la rutina
     * @return Series de la rutina
     */
    public List<Serie> getSeries(){
        return series;
    }

    /**
     * Setea la id de la rutina
     * @param id Id de la rutina
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * Devuelve la id de la rutina
     * @return Id de la rutina
     */
    public long getId(){
        return id;
    }

    /**
     * Devuelve el nombre de la rutina
     * @return Nombre de la rutina
     */
    public String getNombre(){
        return nombre;
    }
}
