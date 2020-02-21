package com.example.trainingapp.rutina;

import androidx.annotation.NonNull;

import com.example.trainingapp.rutina.ejercicio.Ejercicio;
import java.util.ArrayList;
import java.util.List;

/**
 * Representacion de una Serie de ejercicios
 */
public class Serie {

    protected int vueltas;
    protected List<Ejercicio> ejercicios;

    /**
     * Constructor de la clase, crea una serie con cantidad de vueltas igual a 0 y sin ejercicios
     */
    public Serie(){
        vueltas = 0;
        ejercicios = new ArrayList<>();
    }

    /**
     * Devuelve los ejercicios de la serie
     * @return Ejercicios de la serie
     */
    public List<Ejercicio> getEjercicios(){
        return ejercicios;
    }

    /**
     * Setea la cantidad de vueltas de la serie
     * @param cant Cantidad de la serie
     */
    public void setVueltas(int cant){
        this.vueltas = cant;
    }

    /**
     * Devuelve la cantidad de la serie
     * @return Cantidad de la serie
     */
    public int getVueltas(){
        return vueltas;
    }

    @Override
    public @NonNull Serie clone(){
        Serie clon = new Serie();
        clon.setVueltas(vueltas);
        for(Ejercicio ejercicio:ejercicios)
            clon.getEjercicios().add(ejercicio.clone());
        return clon;
    }

}
