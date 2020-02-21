package com.example.trainingapp.activities.activitySeguimiento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.trainingapp.R;
import com.example.trainingapp.activities.activityMain.MainActivity;
import com.example.trainingapp.utilidad.Formato;
import com.example.trainingapp.utilidad.Imagenes;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.rutina.ejercicio.Ejercicio;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.Rutina;
import com.example.trainingapp.rutina.Serie;
import java.util.Iterator;

/**
 * Activity de la pantalla "Seguimiento"
 */
public class SeguimientoActivity extends AppCompatActivity {

    protected Iterator<Ejercicio> ejercicios;
    protected Iterator<Serie> series;
    protected Serie serie;
    protected VisitorSeguimiento visitor;
    protected CountDownTimer contador;
    protected int indiceVueltas, cantVueltas;

    protected ToneGenerator toneGenerator;
    protected Vibrator vibrator;

    protected TextView txtEjercicio,txtSerieCant,txtTipo,txtCantidad;
    protected LinearLayout loutInfo;
    protected Button btnAccion;
    protected ConstraintLayout loutPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);

        //obtener rutina actual sobre la cual se esta trabajando
        Rutina rutina = ListaRutinas.getActual();

        //configuracion de la barra de app
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(rutina.getNombre());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(Imagenes.getImagenAttrs(this,R.attr.homeAsUpIndicator));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //obtener elementos graficos
        txtEjercicio = findViewById(R.id.txtSegEjercicio);
        txtCantidad = findViewById(R.id.txtSegCantidad);
        txtSerieCant = findViewById(R.id.txtSegSerieNum);
        txtTipo = findViewById(R.id.txtSegTipo);
        loutInfo = findViewById(R.id.loutSegInfo);
        btnAccion = findViewById(R.id.btnSegAccion);
        loutPrincipal = findViewById(R.id.loutSegPrincipal);

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        visitor = new VisitorSeguimiento(this);
        series = rutina.getSeries().iterator();

        loutPrincipal.setBackground(Imagenes.getImagenGrande(this,rutina));

        //busca el primer ejercicio para mostrar
        siguienteSerie();
    }

    /**
     * Busca el siguiente ejercicio y lo muestra, si se terminaron los ejercicios le indica al usuario que ha finalizado
     */
    private void siguienteEjercicio(){
        if(ejercicios.hasNext()) {
            ejercicios.next().accept(visitor);
        }
        else{
            indiceVueltas++;
            if(indiceVueltas <= cantVueltas){
                ejercicios = serie.getEjercicios().iterator();
                ejercicios.next().accept(visitor);
                txtSerieCant.setText(indiceVueltas +"|"+ cantVueltas);
            }
            else{
                siguienteSerie();
            }
        }
    }

    /**
     * Busca la proxima serie valida y muestra su primer ejercicio, saltea series sin ejercicios o con cantidad de vueltas igual a 0
     * Si no encuentra una serie le indica al usuario que ha finalizado
     */
    private void siguienteSerie(){
        if(series.hasNext()){
            serie = series.next();
            while((serie.getEjercicios().size()==0||serie.getVueltas()==0)&&series.hasNext()){

                //saltear series sin ejercicios o con cantidad de vueltas igual a 0
                serie = series.next();
            }
            if(serie.getEjercicios().size()!=0&&serie.getVueltas()!=0){

                //si encuentra una serie valida se muestra su primer ejercicio
                ejercicios = serie.getEjercicios().iterator();
                indiceVueltas = 1;
                cantVueltas = serie.getVueltas();
                txtSerieCant.setText(indiceVueltas +"|"+ cantVueltas);
                ejercicios.next().accept(visitor);
            }
            else
                finalizar();
        }
        else
            finalizar();
    }

    /**
     * Indica al usuario que ha terminado todos los ejercicios
     */
    private void finalizar(){
        txtEjercicio.setText(getString(R.string.finalizado));
        loutInfo.setVisibility(View.INVISIBLE);
        btnAccion.setText(getString(R.string.salir));
        btnAccion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Muestra el ejercicio al usuario y la cantidad de repeticiones
     * @param ejercicio Ejercicio que debe mostrarse
     */
    public void mostrarEjercicio(EjPorRepeticiones ejercicio){
        txtEjercicio.setText(ejercicio.getNombre());
        txtTipo.setText(getString(R.string.repeticiones));
        txtCantidad.setText(String.valueOf(ejercicio.getRepeticiones()));
        btnAccion.setText(getString(R.string.siguiente));
        btnAccion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                siguienteEjercicio();
            }
        });
    }

    /**
     * Muestra el ejercicio al usuario y un cronometro con la duracion
     * @param ejercicio Ejercicio que debe mostrarse
     * @param esperarClick Indica si se debe esperar a que el usuario lo indique para que el cronometro empiece a correr
     */
    public void mostrarEjercicio(final EjPorTiempo ejercicio, boolean esperarClick){
        txtEjercicio.setText(ejercicio.getNombre());
        txtTipo.setText(getString(R.string.tiempo));
        txtCantidad.setText(Formato.formatoReloj(ejercicio.getDuracion()));
        if(esperarClick){

            //si se indica que se debe esperar el click
            //configurar el boton de accion para comenar a correr el cronometro cuando se haga click
            btnAccion.setText(getString(R.string.empezar));
            btnAccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    empezar(ejercicio.getDuracion());
                }
            });
        }
        else{

            //si no se debe esperar el click, comienza a correr el cronometro
            empezar(ejercicio.getDuracion());
        }
    }

    /**
     * Comienza a correr el cronometro del ejercicio por tiempo
     * @param segundos Duracion en segundos del ejercicio
     */
    private void empezar(int segundos){

        //configurar el boton para que cancele el cronometro y pase al siguiente ejercicio
        btnAccion.setText(getString(R.string.saltar));
        btnAccion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                contador.cancel();
                siguienteEjercicio();
            }
        });

        //comenzar a correr el cronometro
        contador=new CountDownTimer(segundos*1000,100){

            @Override
            public void onTick(long millisUntilFinished) {
                txtCantidad.setText(Formato.formatoReloj((int) (millisUntilFinished/1000)));
            }

            @Override
            public void onFinish() {

                //sonar y vibrar al terminar el cronometro
                toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,800);
                vibrator.vibrate(VibrationEffect.createOneShot(800,VibrationEffect.EFFECT_HEAVY_CLICK));

                siguienteEjercicio();
            }
        };
        contador.start();
    }

    @Override
    public void onBackPressed(){

        //vuelve a la pantalla principal
        if(contador!=null)
            contador.cancel();
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}
