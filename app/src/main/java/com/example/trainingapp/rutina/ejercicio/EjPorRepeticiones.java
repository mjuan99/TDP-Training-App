package com.example.trainingapp.rutina.ejercicio;

import androidx.annotation.NonNull;

import com.example.trainingapp.utilidad.Visitor;

/**
 * Representacion de un Ejercicio por Repeticiones
 */
public class EjPorRepeticiones extends Ejercicio {

    protected int repeticiones;

    /**
     * Constructor de la clase, setea el nombre del ejercicio y la cantidad de repeticiones
     * @param nombre Nombre del ejercicio
     * @param repeticiones Cantidad de repeticiones
     */
    public EjPorRepeticiones(String nombre, int repeticiones){
        super(nombre);
        this.repeticiones = repeticiones;
    }

    /**
     * Constructor de la clase, crea un ejercicio sin nombre y con cantidad de repeticiones igual a 0
     */
    public EjPorRepeticiones(){
        super("");
        repeticiones = 0;
    }

    /**
     * Setea la cantidad de repeticiones
     * @param repeticiones Cantidad de repeticiones
     */
    public void setRepeticiones(int repeticiones){
        this.repeticiones = repeticiones;
    }

    /**
     * Devuelve la cantidad de repeticiones
     * @return Cantidad de repeticiones
     */
    public int getRepeticiones(){
        return repeticiones;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    @Override
    public @NonNull EjPorRepeticiones clone(){
        return new EjPorRepeticiones(nombre,repeticiones);
    }
}
