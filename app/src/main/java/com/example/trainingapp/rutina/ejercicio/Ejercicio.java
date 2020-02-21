package com.example.trainingapp.rutina.ejercicio;

import com.example.trainingapp.utilidad.Visitor;


/**
 * Representacion de un Ejercicio
 */
public abstract class Ejercicio {

    protected String nombre;

    /**
     * Constructor de la clase, setea el nombre del ejercicio
     * @param nombre Nombre del ejercicio
     */
    protected Ejercicio(String nombre){
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del ejercicio
     * @return Nombre del ejercicio
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Setea el nombre del ejercicio
     * @param nombre Nombre del ejercicio
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * Acepta el visitor
     * @param visitor Visitor  a aceptar
     */
    public abstract void accept(Visitor visitor);

    public abstract Ejercicio clone();
}
