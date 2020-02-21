package com.example.trainingapp.utilidad.notificaciones;

import android.content.Context;
import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;
import com.example.trainingapp.utilidad.Visitor;

/**
 * Visitor utilizado para indicar de que manera agendar las notificaciones dependiendo del tipo de frecuencia de la rutina
 */
public class VisitorSetNotificaciones implements Visitor {

    protected long id;
    protected Context context;

    /**
     * Constructor de la clase, setea el context
     * @param context Context
     */
    public VisitorSetNotificaciones(Context context){
        this.context = context;
    }

    /**
     * Setea la id de la rutina
     * @param id Id de la rutina
     */
    public void setId(long id){
        this.id = id;
    }

    @Override
    public void visit(EjPorRepeticiones ejPorRepeticiones) {}

    @Override
    public void visit(EjDescanso ejDescanso) {}

    @Override
    public void visit(EjPorTiempo ejPorTiempo) {}

    @Override
    public void visit(FrecuenciaPorSemana frecuencia) {
        Notificaciones.setNotificaciones(context,id,frecuencia);
    }

    @Override
    public void visit(FrecuenciaUnicaVez frecuencia) {
        Notificaciones.setNotificaciones(context,id,frecuencia);
    }
}
