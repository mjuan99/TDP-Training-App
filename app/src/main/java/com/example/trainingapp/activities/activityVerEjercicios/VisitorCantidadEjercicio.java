package com.example.trainingapp.activities.activityVerEjercicios;

import com.example.trainingapp.utilidad.Formato;
import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;
import com.example.trainingapp.utilidad.Visitor;

/**
 * Visitor utilizado para obtener la cantidad (repeticiones o duracion) de un ejercicio
 */
public class VisitorCantidadEjercicio implements Visitor {

    protected String cantidad;

    /**
     * Devuelve la cantidad (repeticiones o duracion) en formato texto del ejercicio indicado
     * @return Cantidad del ejercicio
     */
    public String getCantidad(){
        return cantidad;
    }

    @Override
    public void visit(EjPorRepeticiones ejPorRepeticiones) {
        cantidad = String.valueOf(ejPorRepeticiones.getRepeticiones());
    }

    @Override
    public void visit(EjDescanso ejDescanso) {
        cantidad = Formato.formatoTextoTiempo(ejDescanso.getDuracion());
    }

    @Override
    public void visit(EjPorTiempo ejPorTiempo) {
        cantidad = Formato.formatoTextoTiempo(ejPorTiempo.getDuracion());
    }

    @Override
    public void visit(FrecuenciaPorSemana frecuencia) {}

    @Override
    public void visit(FrecuenciaUnicaVez frecuencia) {}
}
