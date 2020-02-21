package com.example.trainingapp.activities.activityEditarEjercicios;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.trainingapp.R;
import com.example.trainingapp.utilidad.Formato;
import com.example.trainingapp.utilidad.Visitor;
import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;

/**
 * Visitor utilizado para indicar el tipo de ejercicio al dialogo de carga de ejercicios
 */
public class VisitorDialogEjercicio implements Visitor {

    protected View vwDialog;

    /**
     * Constructor de la clase, setea el dialogo
     * @param vwDialog Vista del dialogo
     */
    public VisitorDialogEjercicio(View vwDialog){
        this.vwDialog = vwDialog;
    }

    @Override
    public void visit(EjPorRepeticiones ejPorRepeticiones) {
        Spinner spinner = vwDialog.findViewById(R.id.spinDialEjerTipo);
        TextView txtNombre = vwDialog.findViewById(R.id.txtDialEjerNombre);
        TextView txtRepeticiones = vwDialog.findViewById(R.id.txtDialEjerRepeticiones);
        spinner.setSelection(0);
        txtNombre.setText(ejPorRepeticiones.getNombre());
        txtRepeticiones.setText(String.valueOf(ejPorRepeticiones.getRepeticiones()));
    }

    @Override
    public void visit(EjDescanso ejDescanso) {
        Spinner spinner = vwDialog.findViewById(R.id.spinDialEjerTipo);
        TextView txtDuracion = vwDialog.findViewById(R.id.txtDialEjerDuracion);
        spinner.setSelection(2);
        txtDuracion.setText(Formato.formatoReloj(ejDescanso.getDuracion()));
    }

    @Override
    public void visit(EjPorTiempo ejPorTiempo) {
        Spinner spinner = vwDialog.findViewById(R.id.spinDialEjerTipo);
        TextView txtNombre = vwDialog.findViewById(R.id.txtDialEjerNombre);
        TextView txtDuracion = vwDialog.findViewById(R.id.txtDialEjerDuracion);
        spinner.setSelection(1);
        txtNombre.setText(ejPorTiempo.getNombre());
        txtDuracion.setText(Formato.formatoReloj(ejPorTiempo.getDuracion()));
    }

    @Override
    public void visit(FrecuenciaPorSemana frecuencia) {}

    @Override
    public void visit(FrecuenciaUnicaVez frecuencia) {}
}
