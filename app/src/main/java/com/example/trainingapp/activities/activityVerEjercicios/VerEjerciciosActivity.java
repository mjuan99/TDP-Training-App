package com.example.trainingapp.activities.activityVerEjercicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.trainingapp.activities.activityEditarEjercicios.EditarEjerciciosActivity;
import com.example.trainingapp.R;
import com.example.trainingapp.utilidad.Imagenes;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.rutina.ejercicio.Ejercicio;
import com.example.trainingapp.rutina.Rutina;
import com.example.trainingapp.rutina.Serie;
import java.util.List;

/**
 * Activity de la pantalla "Ver Ejercicios"
 */
public class VerEjerciciosActivity extends AppCompatActivity {

    protected Rutina rutina;

    protected LinearLayout loutSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicios);

        //configuracion de la barra de app
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(Imagenes.getImagenAttrs(this,R.attr.homeAsUpIndicator));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //obtener elementos graficos
        loutSeries = findViewById(R.id.loutVerSeries);

        //obtener rutina actual sobre la cual se esta trabajando
        rutina = ListaRutinas.getActual();

        actualizarInfo();
    }

    @Override
    public void onResume(){
        super.onResume();
        actualizarInfo();
    }

    /**
     * Muestra los ejercicios de la rutina
     */
    private void actualizarInfo(){
        VisitorCantidadEjercicio visitor = new VisitorCantidadEjercicio();
        List<Serie> series = rutina.getSeries();
        View vwSerie,vwEjercicio;
        TextView txtTitulo,txtVueltas,txtNombre,txtEjCant;
        LinearLayout loutEjercicios;
        loutSeries.removeAllViews();
        for(int i=0;i<series.size();i++){

            //mostrar cada serie de la rutina
            vwSerie = LayoutInflater.from(this).inflate(R.layout.view_ver_serie,loutSeries,false);
            txtTitulo = vwSerie.findViewById(R.id.txtVerSerTitulo);
            txtVueltas = vwSerie.findViewById(R.id.txtVerSerCant);
            loutEjercicios = vwSerie.findViewById(R.id.loutVerSerEjercicios);
            txtTitulo.setText(getString(R.string.serie)+" "+(i+1));
            txtVueltas.setText(String.valueOf(series.get(i).getVueltas()));
            for(Ejercicio ejercicio:series.get(i).getEjercicios()){

                //mostrar cada ejercicio de la serie
                vwEjercicio = LayoutInflater.from(this).inflate(R.layout.view_ver_ejercicio,loutEjercicios,false);
                txtNombre = vwEjercicio.findViewById(R.id.txtVerEjerNombre);
                txtEjCant = vwEjercicio.findViewById(R.id.txtVerEjerCant);
                txtNombre.setText(ejercicio.getNombre());
                ejercicio.accept(visitor);
                txtEjCant.setText(visitor.getCantidad());
                loutEjercicios.addView(vwEjercicio);
            }
            loutSeries.addView(vwSerie);
        }
    }

    /**
     * Pasa a la pantalla "Editar Ejercicios"
     */
    public void editar(View view){
        Intent i = new Intent(this, EditarEjerciciosActivity.class);
        startActivity(i);
    }
}
