package com.example.trainingapp.activities.activityVerRutina;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.trainingapp.R;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.utilidad.Visitor;
import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;

/**
 * Visitor utilizado para mostrarle al usuario la frecuencia de la rutina en la pantalla "Ver Rutina"
 */
public class VisitorVerRutina implements Visitor {

    protected VerRutinaActivity activity;

    /**
     * Constructor de la clase, setea la activity
     * @param activity Activity que usa al visitor
     */
    public VisitorVerRutina(VerRutinaActivity activity){
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
        int[] ids = {R.id.txtVerRutDia0,R.id.txtVerRutDia1,R.id.txtVerRutDia2,R.id.txtVerRutDia3,R.id.txtVerRutDia4,R.id.txtVerRutDia5,R.id.txtVerRutDia6};

        //recuperar elementos graficos
        TextView txtDia
                ,txtFecha = activity.findViewById(R.id.txtVerRutFecha);
        LinearLayout loutDias = activity.findViewById(R.id.loutVerRutDias);

        //configurar elementos graficos
        loutDias.setVisibility(View.VISIBLE);
        txtFecha.setVisibility(View.GONE);

        for(int i = 0;i<7;i++){

            //recuperar el textView correspondiente a cada dia
            txtDia = activity.findViewById(ids[i]);

            //configurar grafica del textView
            if(dias[i]){
                txtDia.setTextColor(Color.WHITE);
                txtDia.setBackground(activity.getDrawable(R.drawable.circulo));
            }
            else{
                txtDia.setTextColor(Color.BLACK);
                txtDia.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    public void visit(FrecuenciaUnicaVez frecuencia) {

        //obtener elementos graficos
        TextView txtFecha = activity.findViewById(R.id.txtVerRutFecha);
        LinearLayout loutDias = activity.findViewById(R.id.loutVerRutDias)
                ,loutRecordar = activity.findViewById(R.id.loutVerRutRecordar);

        //comparar fecha y hora de la frecuencia con fecha y hora actual
        int compFecha = frecuencia.getFecha().compareNow();
        int compHora = frecuencia.getHora().compareNow();
        if(compFecha==1||(compFecha==0&&compHora==1)){

            //fecha y hora a futuro
            loutDias.setVisibility(View.GONE);
            txtFecha.setVisibility(View.VISIBLE);
            txtFecha.setText(frecuencia.getFecha().toString());
        }
        else{

            //fecha y hora pasada
            loutRecordar.setVisibility(View.GONE);
            ListaRutinas.getActual().setFrecuencia(null);
        }
    }
}
