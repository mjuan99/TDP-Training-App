package com.example.trainingapp.activities.activityEditarEjercicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.trainingapp.R;
import com.example.trainingapp.utilidad.Imagenes;
import com.example.trainingapp.utilidad.lecturaEscritura.LecturaEscritura;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.utilidad.Teclado;
import com.example.trainingapp.activities.activityVerEjercicios.VisitorCantidadEjercicio;
import com.example.trainingapp.rutina.ejercicio.Ejercicio;
import com.example.trainingapp.rutina.Rutina;
import com.example.trainingapp.rutina.Serie;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity de la pantalla "Editar Ejercicios"
 */
public class EditarEjerciciosActivity extends AppCompatActivity {

    protected int cantSeries;
    protected List<Serie> clonSeries;
    protected Rutina rutina;

    protected LinearLayout loutSeries;
    protected List<TextView> titulos;
    protected ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ejercicios);

        //configuracion de la barra de app
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.editar));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(Imagenes.getImagenAttrs(this,R.attr.homeAsUpIndicator));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //obtener elementos graficos
        loutSeries = findViewById(R.id.loutCargarSeries);
        scroll = findViewById(R.id.scrlEdSerSeries);

        //obtener rutina actual sobre la cual se esta trabajando
        rutina = ListaRutinas.getActual();

        cantSeries = 0;

        //se clonan las series para no modificar la rutina original hasta que el usuario lo confirme
        clonSeries = new ArrayList<>();
        for(Serie serie:rutina.getSeries())
            clonSeries.add(serie.clone());

        titulos = new ArrayList<>();

        for(Serie serie:clonSeries){
            mostrarSerie(serie);
        }
    }

    /**
     * Muestra la serie
     * @param serie Serie que se mostrara
     * @return Vista creada
     */
    private View mostrarSerie(Serie serie){

        //obtener elementos graficos a agregar
        View vwSerie = LayoutInflater.from(this).inflate(R.layout.view_editar_serie, loutSeries,false);
        TextView txtTitulo = vwSerie.findViewById(R.id.txtEdSerTitulo)
                ,txtEliminar = vwSerie.findViewById(R.id.txtEdSerEliminar)
                ,txtAgregarEjercicio = vwSerie.findViewById(R.id.txtEdSerEjercicio);
        EditText txtVueltas = vwSerie.findViewById(R.id.txtEdSerCant);
        LinearLayout loutEjercicios = vwSerie.findViewById(R.id.loutEdSerEjercicios);

        //configurar elementos graficos
        int vueltas = serie.getVueltas();
        txtTitulo.setText( getString(R.string.serie)+" "+(++cantSeries));
        titulos.add(txtTitulo);
        txtVueltas.setText(vueltas==0?"":String.valueOf(vueltas));
        txtVueltas.addTextChangedListener(new OyenteVueltas(serie,txtVueltas));
        txtEliminar.setOnClickListener(new OyenteEliminarSerie(serie,vwSerie));
        txtAgregarEjercicio.setOnClickListener(new OyenteAgregarEjercicio(serie,loutEjercicios));

        //mostrar ejercicios de la serie
        for(Ejercicio ejercicio:serie.getEjercicios()){
            mostrarEjercicio(ejercicio,serie,loutEjercicios,-1);
        }

        loutSeries.addView(vwSerie);
        return vwSerie;
    }

    /**
     * Muestra el ejercicio
     * @param ejercicio Ejercicio que se mostrara
     * @param serie Serie a la que pertenece el ejercicio
     * @param loutPadre Layout donde se insertara la vista del ejercicio
     * @param indice indica en que orden se mostrara el ejercicio, -1 si se desea insertar al final
     */
    private void mostrarEjercicio(Ejercicio ejercicio,Serie serie,LinearLayout loutPadre,int indice){
        VisitorCantidadEjercicio visitor = new VisitorCantidadEjercicio();

        //obtener elementos graficos a agregar
        View vwEjercicio = LayoutInflater.from(EditarEjerciciosActivity.this).inflate(R.layout.view_editar_ejercicio,loutPadre,false);
        TextView txtNombre = vwEjercicio.findViewById(R.id.txtEdEjerNombre)
                ,txtEjCant = vwEjercicio.findViewById(R.id.txtEdEjerCant)
                ,txtEliminar = vwEjercicio.findViewById(R.id.txtEdEjerEliminar);
        LinearLayout loutInfo = vwEjercicio.findViewById(R.id.loutEdEjerInfo);

        //configurar elementos graficos
        txtNombre.setText(ejercicio.getNombre());
        ejercicio.accept(visitor);
        txtEjCant.setText(visitor.getCantidad());
        txtEliminar.setOnClickListener(new OyenteEliminarEjercicio(serie,ejercicio,vwEjercicio,loutPadre));
        loutInfo.setOnClickListener(new OyenteEditarEjercicio(ejercicio,serie,loutPadre,vwEjercicio));

        loutPadre.addView(vwEjercicio,indice);
    }

    /**
     * Actualiza los titulos de las series cuando alguna es eliminada
     * @param eliminado Indice de la serie eliminada
     */
    private void actualizarTitulos(int eliminado){
        titulos.remove(eliminado-1);
        for(int i = eliminado-1;i<cantSeries;i++){
            titulos.get(i).setText(getString(R.string.serie)+" "+(i+1));
        }
    }

    /**
     * Crea una serie nueva y la muestra
     */
    public void agregarSerie(View view){
        Serie serie = new Serie();
        final EditText txtVueltas;
        clonSeries.add(serie);

        //bajar hasta la serie agregada y poner el focus en el EditText donde se carga la cantidad de vueltas de la serie
        txtVueltas = mostrarSerie(serie).findViewById(R.id.txtEdSerCant);
        Teclado.mostrarTeclado(this,txtVueltas);
        scroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
                txtVueltas.requestFocus();
            }
        },100);
    }

    /**
     * Guarda los cambios realizados y vuelve a la pantalla anterior
     */
    public void guardar(View view){
        rutina.setSeries(clonSeries);
        LecturaEscritura.guardar(this);
        onBackPressed();
    }

    /**
     * Cancela los cambios hechos por el usuario
     */
    public void cancelar(View view){
        onBackPressed();
    }

    /**
     * Oyente del EditText de la cantidad de vueltas de la series
     */
    protected class OyenteVueltas implements TextWatcher {

        protected Serie serie;
        protected EditText editText;

        /**
         * Constructor de la clase, inicializa atrubutos
         * @param serie Serie
         * @param editText EditText donde se ingresa la cantidad de vueltas de la serie
         */
        public OyenteVueltas(Serie serie, EditText editText){
            this.serie = serie;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            int vueltas;
            try{

                //obtener el numero ingresado por el usuario y setearlo como cantidad de vueltas de la serie
                vueltas = Integer.parseInt(editText.getText().toString());
                serie.setVueltas(vueltas);
            }catch(NumberFormatException e){
                serie.setVueltas(0);
            }
        }
    }

    /**
     * Oyente del boton para eliminar serie
     */
    protected class OyenteEliminarSerie implements View.OnClickListener{

        protected Serie serie;
        protected View vwSerie;

        /**
         * Constructor de la clase, inicializa atributos
         * @param serie Serie
         * @param vwSerie View asociada a la serie
         */
        public OyenteEliminarSerie(Serie serie,View vwSerie){
            this.serie = serie;
            this.vwSerie = vwSerie;
        }

        @Override
        public void onClick(View v) {

            //eliminar la serie de la rutina y su View asociada
            TextView txtTitulo = vwSerie.findViewById(R.id.txtEdSerTitulo);
            int indice = Integer.parseInt(txtTitulo.getText().toString().split(" ")[1]);
            cantSeries--;
            actualizarTitulos(indice);
            clonSeries.remove(serie);
            loutSeries.removeView(vwSerie);
            Teclado.ocultarTeclado(EditarEjerciciosActivity.this);
        }
    }

    /**
     * Oyente para agregar ejercicio
     */
    protected class OyenteAgregarEjercicio implements View.OnClickListener,DialogEjercicio.CommandConfirmarEjercicio{

        protected Serie serie;
        protected LinearLayout loutPadre;

        /**
         * Constructor de la clase, inicializa atributos
         * @param serie Serie a la que se le agregaran los nuevos ejercicios
         * @param loutPadre Layout en el que se agregaran los nuevos ejercicios
         */
        public OyenteAgregarEjercicio(Serie serie,LinearLayout loutPadre){
            this.serie = serie;
            this.loutPadre = loutPadre;
        }

        @Override
        public void onClick(View v) {

            //abrir dialogo para la carga de ejercicio
            Teclado.ocultarTeclado(EditarEjerciciosActivity.this);
            DialogEjercicio dialog = new DialogEjercicio();
            dialog.setCommand(this);
            dialog.show(getSupportFragmentManager(),getString(R.string.agregarejercicio));
        }

        @Override
        public void confirmarEjercicio(Ejercicio ejercicio){

            //agregar el ejercicio a la serie y mostrarlo
            serie.getEjercicios().add(ejercicio);
            mostrarEjercicio(ejercicio,serie,loutPadre,-1);
        }
    }

    /**
     * Oyente para editar ejercicio
     */
    protected class OyenteEditarEjercicio implements View.OnClickListener,DialogEjercicio.CommandConfirmarEjercicio{

        protected Ejercicio ejercicio;
        protected Serie serie;
        protected LinearLayout loutPadre;
        protected View vwEjercicio;

        /**
         * Constructor de la clase, inicializa atributos
         * @param ejercicio Ejercicio
         * @param serie Serie a la cual pertenece el ejercicio
         * @param loutPadre Layout padre de vwEjercicio
         * @param vwEjercicio View asociada al ejercicio
         */
        public OyenteEditarEjercicio(Ejercicio ejercicio,Serie serie,LinearLayout loutPadre,View vwEjercicio){
            this.ejercicio = ejercicio;
            this.serie = serie;
            this.loutPadre = loutPadre;
            this.vwEjercicio = vwEjercicio;
        }

        @Override
        public void onClick(View v) {

            //abrir dialogo para la carga de ejercicio
            Teclado.ocultarTeclado(EditarEjerciciosActivity.this);
            DialogEjercicio dialog = new DialogEjercicio();
            dialog.setEjercicioBase(ejercicio);
            dialog.setCommand(this);
            dialog.show(getSupportFragmentManager(),getString(R.string.editarejercicio));
        }

        @Override
        public void confirmarEjercicio(Ejercicio ejercicioNuevo) {

            //se elimina el ejercicio viejo y se reemplaza por el nuevo
            mostrarEjercicio(ejercicioNuevo,serie,loutPadre,loutPadre.indexOfChild(vwEjercicio));
            loutPadre.removeView(vwEjercicio);
            serie.getEjercicios().add(serie.getEjercicios().indexOf(ejercicio),ejercicioNuevo);
            serie.getEjercicios().remove(ejercicio);
        }
    }

    /**
     * Oyente del boton para eliminar ejercicio
     */
    protected class OyenteEliminarEjercicio implements View.OnClickListener{

        protected Serie serie;
        protected Ejercicio ejercicio;
        protected View vwEjercicio;
        protected LinearLayout loutPadre;

        /**
         * Constructor de la clase, inicializa atributos
         * @param serie Serie a la cual perteneces el ejercicio
         * @param ejercicio Ejercicio
         * @param vwEjercicio View asociada al ejercicio
         * @param loutPadre Layout padre de vwEjercicio
         */
        public OyenteEliminarEjercicio(Serie serie,Ejercicio ejercicio,View vwEjercicio,LinearLayout loutPadre){
            this.serie = serie;
            this.ejercicio = ejercicio;
            this.vwEjercicio = vwEjercicio;
            this.loutPadre = loutPadre;
        }

        @Override
        public void onClick(View v) {

            //eliminar el ejercicio de la serie y su View asociada
            serie.getEjercicios().remove(ejercicio);
            loutPadre.removeView(vwEjercicio);
            Teclado.ocultarTeclado(EditarEjerciciosActivity.this);
        }
    }
}
