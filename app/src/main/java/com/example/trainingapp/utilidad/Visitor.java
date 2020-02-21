package com.example.trainingapp.utilidad;

import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;


/**
 * Interfaz utilizada para diferenciar el comportamiento de la app respecto a los distintos tipos de ejercicios y frecuencias
 */
public interface Visitor {

    /**
     * Visita ejercicio por repeticiones
     * @param ejPorRepeticiones Ejercicio a visitar
     */
    void visit(EjPorRepeticiones ejPorRepeticiones);

    /**
     * Visita descanso
     * @param ejDescanso Ejercicio a visitar
     */
    void visit(EjDescanso ejDescanso);

    /**
     * Visita ejercicio por tiempo
     * @param ejPorTiempo Ejercicio a visitar
     */
    void visit(EjPorTiempo ejPorTiempo);

    /**
     * Visita frecuencia por semana
     * @param frecuencia Frecuencia a visitar
     */
    void visit(FrecuenciaPorSemana frecuencia);

    /**
     * Visita frecuencia de unica vez
     * @param frecuencia Frecuencia a visitar
     */
    void visit(FrecuenciaUnicaVez frecuencia);

}
