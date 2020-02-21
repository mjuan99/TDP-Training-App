package com.example.trainingapp.utilidad.notificaciones;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.trainingapp.R;
import com.example.trainingapp.activities.activitySeguimiento.SeguimientoActivity;
import com.example.trainingapp.utilidad.registroTiempo.Fecha;
import com.example.trainingapp.utilidad.registroTiempo.Hora;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.activities.activityVerRutina.VerRutinaActivity;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;
import com.example.trainingapp.rutina.Rutina;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase utilizada para agendar y mostrar notificaciones
 */
public class Notificaciones extends BroadcastReceiver {

    public final static String stRutina = "rutina";
    public final static String stTiempo = "tiempo";
    public final static String actComenzar = "comenzar";
    public final static String actPostergar = "post";
    public final static String actNotificar = "notificar";
    public final static String actReiniciar = "android.intent.action.BOOT_COMPLETED";
    public final static String channelId = "Rutinas";

    /**
     * Cancela las notificaciones agendadas para cierta rutina
     * @param id Id de la rutina a la que se le cancelaran las notificaciones
     */
    public static void cancelarNotificaciones(Context context,long id){
        Intent intent;
        PendingIntent pendingIntent;
        int intentId;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        for(int i = 0;i<=8;i++){

            //cancela cada notificacion que pudo haber sido agendada
            //0 notificacion de unica vez
            //1-7 notificaciones por semana
            //8 notificacion postergada
            intent = new Intent(context,Notificaciones.class);
            intent.putExtra(stRutina,id);
            intentId = (int)Long.parseLong(id+String.valueOf(i));
            pendingIntent=PendingIntent.getBroadcast(context,intentId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
    }

    /**
     * Muestra una notificacion de la rutina
     * @param rutina Rutina para mostrar la notificacion
     */
    public static void crearNotificacion(Context context,Rutina rutina){
        long id = rutina.getId();
        Intent mainIntent,postIntent1,postIntent2;
        PendingIntent mainPending,postPending1,postPending2;
        NotificationCompat.Builder builder;
        NotificationManagerCompat notificationManager;

        //intent ejecutado al clickear la notificacion
        mainIntent = new Intent(context, Notificaciones.class);
        mainIntent.putExtra(stRutina, id);
        mainIntent.setAction(actComenzar);
        mainPending = PendingIntent.getBroadcast(context, (int) id, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //intent para postergar la notificacion media hora
        postIntent1 = new Intent(context, Notificaciones.class);
        postIntent1.putExtra(stRutina, id);
        postIntent1.putExtra(stTiempo, 30 * 60 * 1000);
        postIntent1.setAction(actPostergar);
        postPending1 = PendingIntent.getBroadcast(context, (int) id + 1, postIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

        //intent para postergar la notificacion una hora
        postIntent2 = new Intent(context, Notificaciones.class);
        postIntent2.putExtra(stRutina, id);
        postIntent2.putExtra(stTiempo, 60 * 60 * 1000);
        postIntent2.setAction(actPostergar);
        postPending2 = PendingIntent.getBroadcast(context, (int) id + 2, postIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

        //configurar la notificacion
        builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(rutina.getNombre())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(mainPending)
                .addAction(R.mipmap.ic_launcher_round,context.getString(R.string.posponer30),postPending1)
                .addAction(R.mipmap.ic_launcher_round,context.getString(R.string.posponer1),postPending2)
                .setAutoCancel(true);
        if(rutina.getSeries().size()==0) {
            builder.setContentText(context.getString(R.string.tocaver));
        }else{
            builder.setContentText(context.getString(R.string.tocacomenzar));
        }

        //crear notificacion
        notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify((int)id, builder.build());
    }

    /**
     * Agenda notificaciones semanales para una rutina
     * @param id Id de la rutina
     * @param frecuencia Frecuencia de las notificaciones
     */
    public static void setNotificaciones(Context context, long id, FrecuenciaPorSemana frecuencia){
        Calendar calendar;
        Intent intent;
        PendingIntent pendingIntent;
        int intentId;
        boolean[] dias=frecuencia.getDias();
        Hora hora = frecuencia.getHora();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        for(int i = 0;i<7;i++){
            if(dias[i]){

                //si el dia esta marcado en la frecuencia se agendan las notificaciones
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                //(i+1)%7+1 pasa de formato (lunes[0]-domingo[6]) a (domingo[1]-sabado[7])
                calendar.set(Calendar.DAY_OF_WEEK,(i+1)%7+1);
                calendar.set(Calendar.HOUR_OF_DAY,hora.getHora());
                calendar.set(Calendar.MINUTE,hora.getMinuto());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                if(calendar.before(new GregorianCalendar())){

                    //si la fecha es anterior a la actual se agregan 7 dias
                    calendar.add(Calendar.DAY_OF_WEEK,7);
                }
                intent = new Intent(context,Notificaciones.class);
                intent.putExtra(stRutina,id);
                intent.setAction(actNotificar);
                intentId = (int)Long.parseLong(id+String.valueOf(i+1));
                pendingIntent = PendingIntent.getBroadcast(context,intentId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }
        }
    }

    /**
     * Agenda una notificacion para una rutina
     * @param id Id de la rutina
     * @param frecuencia Frecuencia que indica fecha y hora de la notificacion
     */
    public static void setNotificaciones(Context context, long id, FrecuenciaUnicaVez frecuencia){
        Intent intent;
        PendingIntent pendingIntent;
        AlarmManager alarmManager;
        Hora hora = frecuencia.getHora();
        Fecha fecha = frecuencia.getFecha();
        int intentId = (int)Long.parseLong(id+"0");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR,fecha.getAnio());
        calendar.set(Calendar.MONTH,fecha.getMes()-1);
        calendar.set(Calendar.DAY_OF_MONTH,fecha.getDia());
        calendar.set(Calendar.HOUR_OF_DAY,hora.getHora());
        calendar.set(Calendar.MINUTE,hora.getMinuto());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        if(calendar.after(new GregorianCalendar())){

            //si la fecha y hora es posterior a la actual se agenda la notificacion
            intent = new Intent(context,Notificaciones.class);
            intent.putExtra(stRutina,id);
            intent.setAction(actNotificar);
            pendingIntent = PendingIntent.getBroadcast(context,intentId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent;
        Rutina rutina;
        NotificationManagerCompat notificationManager;
        AlarmManager alarmManager;
        PendingIntent pendingIntent;
        VisitorSetNotificaciones visitor;
        int postergarId,tiempo;
        long id;
        if(intent.getAction()!=null)
            if (intent.getAction().equals(actReiniciar)){

                //resetea las notificaciones cuando se reinicia el celular
                visitor = new VisitorSetNotificaciones(context);
                for(Rutina rut: ListaRutinas.getRutinas(context)){
                    visitor.setId(rut.getId());
                    rut.getFrecuencia().accept(visitor);
                }

            }
            else{
                id=intent.getLongExtra(stRutina,-1);
                rutina = ListaRutinas.getRutina(context,id);
                if(rutina!=null){
                    if(intent.getAction().equals(actComenzar)){

                        //abre la pantalla "Seguimiento" o "Ver Rutina" dependiendo de si la rutina tiene ejercicios cargados
                        if(rutina.getSeries().isEmpty())
                            newIntent = new Intent(context,VerRutinaActivity.class);
                        else
                            newIntent = new Intent(context, SeguimientoActivity.class);
                        ListaRutinas.setActual(rutina);
                        context.startActivity(newIntent);
                    }
                    else{
                        if(intent.getAction().equals(actPostergar)){

                            //posterga la notificacion el tiempo indicado
                            notificationManager = NotificationManagerCompat.from(context);
                            notificationManager.cancel((int)id);
                            tiempo = intent.getIntExtra(stTiempo,0);
                            alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                            newIntent = new Intent(context,Notificaciones.class);
                            newIntent.putExtra(stRutina,id);
                            newIntent.setAction(actNotificar);
                            postergarId = (int)Long.parseLong(id+"8");
                            pendingIntent = PendingIntent.getBroadcast(context,postergarId,newIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+tiempo,pendingIntent);
                        }
                        else{
                            if(intent.getAction().equals(actNotificar)){

                                //crea la notificacion para la rutina
                                crearNotificacion(context,rutina);
                            }
                        }
                    }
                }
            }
    }
}
