package com.example.trainingapp.activities.activityVerRutina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.trainingapp.activities.activityMain.MainActivity;
import com.example.trainingapp.utilidad.Advertencia;
import com.example.trainingapp.utilidad.Imagenes;
import com.example.trainingapp.utilidad.lecturaEscritura.LecturaEscritura;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.R;
import com.example.trainingapp.activities.activitySeguimiento.SeguimientoActivity;
import com.example.trainingapp.activities.activityVerEjercicios.VerEjerciciosActivity;
import com.example.trainingapp.activities.activityEditarRutina.EditarRutinaActivity;
import com.example.trainingapp.rutina.frecuencia.Frecuencia;
import com.example.trainingapp.rutina.Rutina;

/**
 * Activity de la pantalla "Ver Rutina"
 */
public class VerRutinaActivity extends AppCompatActivity {

    protected Rutina rutina;
    protected VisitorVerRutina visitor;

    protected LinearLayout loutRecordar,loutDias;
    protected TextView txtHora,txtFecha,txtNombre;
    protected TextView[] txtDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_rutina);

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
        txtNombre = findViewById(R.id.txtVerRutNombre);
        loutRecordar = findViewById(R.id.loutVerRutRecordar);
        loutDias = findViewById(R.id.loutVerRutDias);
        txtHora = findViewById(R.id.txtVerRutHora);
        txtFecha = findViewById(R.id.txtVerRutFecha);
        int[] ids = {R.id.txtVerRutDia0,R.id.txtVerRutDia1,R.id.txtVerRutDia2,R.id.txtVerRutDia3,R.id.txtVerRutDia4,R.id.txtVerRutDia5,R.id.txtVerRutDia6};
        txtDias = new TextView[7];
        for(int i = 0;i<7;i++)
            txtDias[i] = findViewById(ids[i]);

        //obtener rutina actual sobre la cual se esta trabajando
        rutina = ListaRutinas.getActual();

        visitor = new VisitorVerRutina(this);
        txtNombre.setBackground(Imagenes.getImagenCorta(this,rutina));

        actualizarInfo();
    }

    @Override
    public void onResume(){
        super.onResume();
        actualizarInfo();
    }

    /**
     * Actualiza la infromacion que que se muestra de la rutina
     */
    private void actualizarInfo(){
        Frecuencia frecuencia = rutina.getFrecuencia();
        txtNombre.setText(rutina.getNombre());
        if(frecuencia!=null){
            frecuencia.accept(visitor);
            loutRecordar.setVisibility(View.VISIBLE);
            txtHora.setText(frecuencia.getHora().toString());
        }
        else
            loutRecordar.setVisibility(View.GONE);
    }

    /**
     * Pasa a la pantalla "Editar Rutina"
     */
    public void editar(View view){
        Intent i = new Intent(this, EditarRutinaActivity.class);
        startActivity(i);
    }

    /**
     * Pasa a la pantalla "Ver Ejercicios"
     */
    public void verEjercicios(View view){
        Intent i = new Intent(this, VerEjerciciosActivity.class);
        startActivity(i);
    }

    /**
     * Pasa a la pantalla "Seguimiento"
     */
    public void empezar(View view){
        Intent i = new Intent(this, SeguimientoActivity.class);
        startActivity(i);
    }

    /**
     * Pregunta al usuario si desea eliminar la rutina
     */
    public void eliminar(View view){
        Advertencia.mostrarMensaje(this, getString(R.string.eliminarrutina), getString(R.string.deseaeliminar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarRutina();
            }
        });
    }

    /**
     * Elimina la rutina de la lista y guarda los datos
     */
    private void eliminarRutina(){
        ListaRutinas.getRutinas(this).remove(rutina);

        LecturaEscritura.guardar(this);

        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed(){

        //vuelve a la pantalla principal
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}
