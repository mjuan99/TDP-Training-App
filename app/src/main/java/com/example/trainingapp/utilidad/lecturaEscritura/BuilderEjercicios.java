package com.example.trainingapp.utilidad.lecturaEscritura;

import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.ejercicio.Ejercicio;

/**
 * Builder utilizado para simplificar la creacion de ejercicios al decodificarlos en LecturaEscritura
 */
public class BuilderEjercicios {

    protected int tipo;
    protected EjPorRepeticiones ejPorRepeticiones;
    protected EjPorTiempo ejPorTiempo;
    protected EjDescanso ejDescanso;
    public final static int TIPO_REPETICIONES = 0;
    public final static int TIPO_TIEMPO =1 ;
    public final static int TIPO_DESCANSO = 2;

    /**
     * Constructor de la clase, inicializa atributos
     */
    public BuilderEjercicios(){
        tipo = -1;
        ejPorRepeticiones = null;
        ejPorTiempo = null;
        ejDescanso = null;
    }

    /**
     * Setea el tipo de ejercicio que se creara
     * @param tipo Tipo de ejercicio a crear
     */
    public void setTipo(int tipo){
        this.tipo = tipo;
        switch (tipo){
            case TIPO_REPETICIONES:
                ejPorRepeticiones = new EjPorRepeticiones();
                break;
            case TIPO_TIEMPO:
                ejPorTiempo = new EjPorTiempo();
                break;
            case TIPO_DESCANSO:
                ejDescanso = new EjDescanso();
                break;
        }
    }

    /**
     * Setea el nombre del ejercicio que se creara
     * @param nombre Nombre del ejercicio a crear
     */
    public void setNombre(String nombre){
        switch (tipo){
            case TIPO_REPETICIONES:
                ejPorRepeticiones.setNombre(nombre);
                break;
            case TIPO_TIEMPO:
                ejPorTiempo.setNombre(nombre);
                break;
            case TIPO_DESCANSO:
                ejDescanso.setNombre(nombre);
                break;
        }
    }

    /**
     * Setea la cantidad del ejercicio que se creara
     * @param cantidad Cantidad del ejercicio a crear
     */
    public void setCantidad(int cantidad){
        switch (tipo){
            case TIPO_REPETICIONES:
                ejPorRepeticiones.setRepeticiones(cantidad);
                break;
            case TIPO_TIEMPO:
                ejPorTiempo.setDuracion(cantidad);
                break;
            case TIPO_DESCANSO:
                ejDescanso.setDuracion(cantidad);
                break;
        }
    }

    /**
     * Crea el ejercicio
     * @return Ejercicio creado
     */
    public Ejercicio build(){
        Ejercicio ejercicio = null;
        switch (tipo){
            case TIPO_REPETICIONES:
                ejercicio = ejPorRepeticiones;
                ejPorRepeticiones = null;
                break;
            case TIPO_TIEMPO:
                ejercicio = ejPorTiempo;
                ejPorTiempo = null;
                break;
            case TIPO_DESCANSO:
                ejercicio = ejDescanso;
                ejDescanso = null;
                break;
        }
        tipo = -1;
        return ejercicio;
    }

}
