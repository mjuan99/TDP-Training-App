package com.example.trainingapp.activities.activityMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.trainingapp.R;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.utilidad.notificaciones.Notificaciones;
import com.example.trainingapp.activities.activityEditarRutina.EditarRutinaActivity;
import com.example.trainingapp.activities.activityVerRutina.VerRutinaActivity;
import com.example.trainingapp.rutina.Rutina;
import java.util.List;

/**
 * Activity de la pantalla principal
 */
public class MainActivity extends AppCompatActivity {

    protected List<Rutina> rutinas;

    protected ListView lstRutinas;
    protected ArrayAdapter<Rutina> adapterRutinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configuracion de la barra de app
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //obtener y configurar elementos graficos
        rutinas = ListaRutinas.getRutinas(this);
        lstRutinas = findViewById(R.id.lstMainRutinas);
        adapterRutinas = new AdapterRutinas(this,0,rutinas);
        lstRutinas.setAdapter(adapterRutinas);
        lstRutinas.setOnItemClickListener(new OyenteClick());

        createNotificationChannel();
    }

    /**
     * Crea el canal de notificacion para las rutinas
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Notificaciones.channelId, Notificaciones.channelId, importance);
            channel.setDescription(Notificaciones.channelId);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapterRutinas.notifyDataSetChanged();
    }

    /**
     * Pasa a la pantalla "Editar Rutina" indicando que no hay una rutina actual, por lo que se creara una nueva
     */
    public void agregarRutina(View view){
        ListaRutinas.setActual(null);
        Intent i = new Intent(this, EditarRutinaActivity.class);
        startActivity(i);
    }

    /**
     * Pasa a la pantalla "Ver Rutina" indicando la rutina actual que se debe mostrar
     */
    private void verRutina(int posRutina){
        ListaRutinas.setActual(rutinas.get(posRutina));
        Intent i = new Intent(this, VerRutinaActivity.class);
        startActivity(i);
    }

    /**
     * Oyente de click para los items (rutinas) de la lista
     */
    private class OyenteClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            verRutina(position);
        }
    }

}
