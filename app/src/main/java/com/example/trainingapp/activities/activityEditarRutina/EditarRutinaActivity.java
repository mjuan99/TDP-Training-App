package com.example.trainingapp.activities.activityEditarRutina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import com.example.trainingapp.activities.activityEditarEjercicios.EditarEjerciciosActivity;
import com.example.trainingapp.utilidad.Advertencia;
import com.example.trainingapp.utilidad.Formato;
import com.example.trainingapp.utilidad.Imagenes;
import com.example.trainingapp.utilidad.lecturaEscritura.LecturaEscritura;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.activities.activityMain.MainActivity;
import com.example.trainingapp.utilidad.notificaciones.Notificaciones;
import com.example.trainingapp.R;
import com.example.trainingapp.utilidad.notificaciones.VisitorSetNotificaciones;
import com.example.trainingapp.utilidad.registroTiempo.Fecha;
import com.example.trainingapp.utilidad.registroTiempo.Hora;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;
import com.example.trainingapp.rutina.frecuencia.Frecuencia;
import com.example.trainingapp.rutina.Rutina;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Activity de la pantalla "Editar Rutina"
 */
public class EditarRutinaActivity extends AppCompatActivity {

    protected Rutina rutina;
    protected List<Rutina> rutinas;
    protected VisitorEditarRutina visitor;

    protected EditText txtNombre;
    protected Switch swRecordar;
    protected LinearLayout loutRecordar,loutDias,loutFecha;
    protected RadioButton rbtnSemana,rbtnUnica;
    protected TextView txtFecha,txtHora;
    protected ToggleButton[] btnDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rutina);

        Frecuencia frecuencia;

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

        //obtener elementos grafcios
        txtNombre = findViewById(R.id.txtEdRutNombre);
        swRecordar = findViewById(R.id.swEdRutRecordar);
        loutDias = findViewById(R.id.loutEdRutDias);
        loutRecordar = findViewById(R.id.loutEdRutRecordar);
        rbtnSemana = findViewById(R.id.rbtnEdRutSem);
        rbtnUnica = findViewById(R.id.rbtnEdRutUna);
        txtFecha = findViewById(R.id.txtInEdRutFecha);
        txtHora = findViewById(R.id.txtInEdRutHora);
        loutFecha = findViewById(R.id.loutEdRutFecha);
        int[] ids = {R.id.btnEdRutDia0,R.id.btnEdRutDia1,R.id.btnEdRutDia2,R.id.btnEdRutDia3,R.id.btnEdRutDia4,R.id.btnEdRutDia5,R.id.btnEdRutDia6};
        btnDias = new ToggleButton[7];
        for(int i = 0;i<7;i++){
            btnDias[i] = findViewById(ids[i]);

            //configuracion grafica de los botones de los dias
            btnDias[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        buttonView.setTextColor(Color.WHITE);
                        buttonView.setBackground(getDrawable(R.drawable.circulo));
                    }
                    else{
                        buttonView.setTextColor(Color.BLACK);
                        buttonView.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });
        }

        //obtener rutina actual sobre la cual se esta trabajando, en caso de que no la exista crea una nueva
        rutinas = ListaRutinas.getRutinas(this);
        rutina = ListaRutinas.getActual();
        if(rutina!=null)
            txtNombre.setText(rutina.getNombre());
        else{
            rutina = new Rutina();
            ListaRutinas.setActual(rutina);
        }

        //configurar elementos graficos y comportamiento
        txtNombre.setBackground(Imagenes.getImagenCorta(this,rutina));

        swRecordar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //muestra o esconde los elementos graficos de la seccion "Recordarme"
                int min,hora,dia,mes,anio;
                loutRecordar.setVisibility((isChecked?View.VISIBLE:View.GONE));
                if(isChecked){
                    rbtnSemana.setChecked(true);
                    for(int i = 0;i<7;i++){

                        //inicializa el estado de los botones de los dias como no marcados
                        btnDias[i].setChecked(false);
                        btnDias[i].setTextColor(Color.BLACK);
                        btnDias[i].setBackgroundColor(Color.TRANSPARENT);
                    }

                    //inicializa fecha y hora con la fecha y hora actuales
                    Calendar c = Calendar.getInstance();
                    min = c.get(Calendar.MINUTE);
                    hora = c.get(Calendar.HOUR_OF_DAY);
                    dia = c.get(Calendar.DAY_OF_MONTH);
                    mes = c.get(Calendar.MONTH)+1;
                    anio = c.get(Calendar.YEAR);
                    txtFecha.setText(Formato.formatoFecha(dia,mes,anio));
                    txtHora.setText(Formato.formatoHora(hora,min));
                }
            }
        });

        rbtnSemana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //muestra o esconde la seccion de recordar semanalmente y unica vez segun corresponda
                loutDias.setVisibility((isChecked?View.VISIBLE:View.GONE));
                loutFecha.setVisibility((isChecked?View.GONE:View.VISIBLE));
            }
        });

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //crea un dialogo para que el usuario ingrese la fecha, utilizando la fecha cargada como base
                String[] fecha = txtFecha.getText().toString().split("/");
                int dia = Integer.parseInt(fecha[0]);
                int mes = Integer.parseInt(fecha[1]);
                int anio = Integer.parseInt(fecha[2]);
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFecha.setText(Formato.formatoFecha(dayOfMonth,month+1,year));
                    }
                };
                DatePickerDialog datePiecker = new DatePickerDialog(EditarRutinaActivity.this,listener,anio,mes-1,dia);
                datePiecker.show();
            }
        });

        txtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //crea un dialogo para que el usuario ingrese la hora, utilizando la hora cargada como base
                String[] tiempo = txtHora.getText().toString().split(":");
                int hora = Integer.parseInt(tiempo[0]);
                int minuto = Integer.parseInt(tiempo[1]);
                TimePickerDialog.OnTimeSetListener listener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtHora.setText(Formato.formatoHora(hourOfDay,minute));
                    }
                };
                TimePickerDialog timePicker = new TimePickerDialog(EditarRutinaActivity.this,listener,hora,minuto,true);
                timePicker.show();
            }
        });

        //mostrar informacion sobre la frecuencia de la rutina
        frecuencia = rutina.getFrecuencia();
        if(frecuencia!=null){
            visitor=new VisitorEditarRutina(this);
            swRecordar.setChecked(true);
            frecuencia.accept(visitor);
            txtHora.setText(frecuencia.getHora().toString());
        }
    }

    /**
     * Guarda los datos ingresados por el usuario, muetra un mensaje de error en caso de que la entrada sea incorrecta
     */
    public void guardar(View view){
        Frecuencia frecuencia;
        boolean algunCheck = false;
        String errores = "";
        String[] partes;
        boolean[] dias;
        Hora hora = null;
        Fecha fecha = null;
        int compFecha,compHora;
        VisitorSetNotificaciones visitor;
        String nombre = txtNombre.getText().toString();

        //controlar que los datos ingresados sean validos
        if(nombre.equals("")||Pattern.matches(".*[<>].*",nombre))
            errores+= getString(R.string.nombreinvalido);
        if(swRecordar.isChecked()){
            partes = txtHora.getText().toString().split(":");
            hora = new Hora(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]));
            if(rbtnSemana.isChecked()){
                for(int i = 0;i<7&&!algunCheck;i++)
                    algunCheck = btnDias[i].isChecked();
                if(!algunCheck)
                    errores+= (errores.equals("")?"":"\n")+getString(R.string.ingresardia);
            }
            else{
                partes = txtFecha.getText().toString().split("/");
                fecha = new Fecha(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]),Integer.parseInt(partes[2]));
                compFecha = fecha.compareNow();
                compHora = hora.compareNow();
                if(compFecha==-1||(compFecha==0&&compHora<1))
                    errores+= (errores.equals("")?"":"\n")+getString(R.string.ingresarfechahora);
            }
        }
        if(errores.equals("")) {

            //si no hubo errores se cancelan las notificaciones previas asociadas a la rutina
            //y se recuperan los datos cargados por el usuario
            rutina.setNombre(nombre);
            Notificaciones.cancelarNotificaciones(this, rutina.getId());
            if (swRecordar.isChecked()) {
                if (rbtnSemana.isChecked()) {
                    dias = new boolean[7];
                    for (int i = 0; i < 7; i++) {
                        dias[i] = btnDias[i].isChecked();
                    }
                    frecuencia = new FrecuenciaPorSemana(hora, dias);
                }
                else {
                    frecuencia = new FrecuenciaUnicaVez(hora, fecha);
                }

                //agendar notificaciones
                visitor = new VisitorSetNotificaciones(this);
                visitor.setId(rutina.getId());
                frecuencia.accept(visitor);

                rutina.setFrecuencia(frecuencia);
            }

            //si la rutina es nueva se agrega a la lista de rutinas de la aplicacion
            if (!rutinas.contains(rutina))
                rutinas.add(rutina);

            //guardar los datos
            LecturaEscritura.guardar(this);

            onBackPressed();
        }
        else{

            //si hubo errores se advierte al usuario
            Advertencia.mostrarMensaje(this,getString(R.string.error),errores,null);
        }
    }

    /**
     * Pregunta al usuario si desea eliminar la rutina
     */
    public void eliminar(View view){
        Advertencia.mostrarMensaje(this,getString(R.string.eliminarrutina),getString(R.string.deseaeliminar),new DialogInterface.OnClickListener(){

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
        rutinas.remove(rutina);

        LecturaEscritura.guardar(this);

        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    /**
     * Pasa a la pantalla de "Ver Ejercicios"
     */
    public void verEjercicios(View view){
        Intent i = new Intent(this, EditarEjerciciosActivity.class);
        startActivity(i);
    }
}
