package com.example.trainingapp.rutina.ejercicio;


import androidx.annotation.NonNull;
import com.example.trainingapp.utilidad.Visitor;

/**
 * Representacion de un Ejercicio por Tiempo
 */
public class EjPorTiempo extends Ejercicio {

    protected int duracion;

    /**
     * Constructor de la clase, setea el nombre del ejercicio y su duracion
     * @param nombre Nombre del ejercicio
     * @param duracion Duracion del ejercicio, en segundos
     */
    public EjPorTiempo(String nombre, int duracion){
        super(nombre);
        this.duracion = duracion;
    }

    /**
     * Constructor de la clase, crea un ejercicio sin nombre y con duracion de 0 segundos
     */
    public EjPorTiempo(){
        super("");
        duracion = 0;
    }

    /**
     * Setea la duracion del ejercicio
     * @param duracion Duracion del ejercicio
     */
    public void setDuracion(int duracion){
        this.duracion = duracion;
    }

    /**
     * Devuelve la duracion del ejercicio
     * @return Duracion del ejercicio
     */
    public int getDuracion(){
        return duracion;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    @Override
    public @NonNull EjPorTiempo clone(){
        return new EjPorTiempo(nombre,duracion);
    }
}
