package com.example.trainingapp.activities.activitySeguimiento;

import com.example.trainingapp.utilidad.Visitor;
import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;

/**
 * Visitor utilizado para indicar el tipo de ejercicio en la pantalla "Seguimiento"
 */
public class VisitorSeguimiento implements Visitor {

    protected SeguimientoActivity activity;

    /**
     * Constructor de la clase, setea la activity
     * @param activity Activity que usa al visitor
     */
    public VisitorSeguimiento(SeguimientoActivity activity){
        this.activity = activity;
    }


    @Override
    public void visit(EjPorRepeticiones ejPorRepeticiones) {
        activity.mostrarEjercicio(ejPorRepeticiones);
    }

    @Override
    public void visit(EjDescanso ejDescanso) {
        activity.mostrarEjercicio(ejDescanso,false);
    }

    @Override
    public void visit(EjPorTiempo ejPorTiempo) {
        activity.mostrarEjercicio(ejPorTiempo,true);
    }

    @Override
    public void visit(FrecuenciaPorSemana frecuencia) {}

    @Override
    public void visit(FrecuenciaUnicaVez frecuencia) {}
}
