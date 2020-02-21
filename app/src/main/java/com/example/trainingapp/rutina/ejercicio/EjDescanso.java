package com.example.trainingapp.rutina.ejercicio;

import androidx.annotation.NonNull;
import com.example.trainingapp.utilidad.Visitor;

/**
 * Representacion de un Descanso
 */
public class EjDescanso extends EjPorTiempo {

    /**
     * Constructor de la clase, setea el nombre y duracion
     * @param nombre Nombre del descanso
     * @param duracion Duracion del descanso, en segundos
     */
    public EjDescanso(String nombre, int duracion){
        super(nombre,duracion);
    }

    /**
     * Constructor de la clase, crea un descanso sin nombre y con duracion de 0 segundos
     */
    public EjDescanso(){
        super("",0);
    }

    @Override
    public @NonNull EjDescanso clone(){
        return new EjDescanso(nombre,duracion);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
