package com.example.trainingapp.utilidad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;
import com.example.trainingapp.R;

/**
 * Clase utilizada para mostrar advertencias al usuario
 */
public class Advertencia {

    /**
     * Muestra una advertencia al usuario
     * @param titulo Titulo de la advertencia
     * @param mensaje Mensaje de la advertencia
     * @param listener Oyente del boton "OK", si es null no se mostrara un boton de "Cancelar"
     */
    public static void mostrarMensaje(Context context, String titulo, String mensaje, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alert;
        TextView txtTitulo = new TextView(context);
        txtTitulo.setText(titulo);
        txtTitulo.setTextSize(30);
        txtTitulo.setTextColor(Color.BLACK);
        txtTitulo.setPadding(30,20,0,0);
        txtTitulo.setTextAppearance(R.style.fuente);
        builder.setCustomTitle(txtTitulo)
                .setMessage(mensaje)
                .setPositiveButton(context.getString(R.string.ok),listener);
        if(listener!=null){
            builder.setNegativeButton(context.getString(R.string.cancelar),null);
            alert =  builder.show();
            Button btnCancel = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
            btnCancel.setTextAppearance(R.style.fuente);
            btnCancel.setTextSize(20);
        }
        else
            alert = builder.show();
        TextView txtMensaje = alert.findViewById(android.R.id.message);
        txtMensaje.setTextAppearance(R.style.fuente);
        txtMensaje.setTextSize(25);
        txtMensaje.setPadding(40,30,40,30);
        Button btnOk = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        btnOk.setTextAppearance(R.style.fuente);
        btnOk.setTextSize(20);
    }
}
