package com.example.trainingapp.activities.activityEditarRutina;

import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.trainingapp.R;
import com.example.trainingapp.utilidad.Visitor;
import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;

/**
 * Visitor utilizado para mostrarle al usuario la frecuencia de la rutina en la pantalla "Editar Rutina"
 */
public class VisitorEditarRutina implements Visitor {

    protected EditarRutinaActivity activity;

    /**
     * Constructor de la clase, setea la activity
     * @param activity Activity que usa al visitor
     */
    public VisitorEditarRutina(EditarRutinaActivity activity){
        this.activity = activity;
    }

    @Override
    public void visit(EjPorRepeticiones ejPorRepeticiones) {}

    @Override
    public void visit(EjDescanso ejDescanso) {}

    @Override
    public void visit(EjPorTiempo ejPorTiempo) {}

    @Override
    public void visit(FrecuenciaPorSemana frecuencia) {
        boolean[] dias = frecuencia.getDias();
        ToggleButton btnDia;
        int[] ids = {R.id.btnEdRutDia0,R.id.btnEdRutDia1,R.id.btnEdRutDia2,R.id.btnEdRutDia3,R.id.btnEdRutDia4,R.id.btnEdRutDia5,R.id.btnEdRutDia6};
        RadioButton rbutton = activity.findViewById(R.id.rbtnEdRutSem);
        rbutton.setChecked(true);
        for (int i = 0;i<7;i++){

            //recuperar los botones correspondientes a cada dia
            btnDia = activity.findViewById(ids[i]);

            //configurar grafica de los botones
            btnDia.setChecked(dias[i]);
            if(dias[i]){
                btnDia.setTextColor(Color.WHITE);
                btnDia.setBackground(activity.getDrawable(R.drawable.circulo));
            }
            else{
                btnDia.setTextColor(Color.BLACK);
                btnDia.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    public void visit(FrecuenciaUnicaVez frecuencia) {

        //obtener elementos graficos
        TextView txtFecha = activity.findViewById(R.id.txtInEdRutFecha);
        RadioButton rbutton = activity.findViewById(R.id.rbtnEdRutUna);

        //configurar valores de los elementos
        rbutton.setChecked(true);
        txtFecha.setText(frecuencia.getFecha().toString());
    }
}
