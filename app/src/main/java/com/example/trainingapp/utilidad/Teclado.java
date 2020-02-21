package com.example.trainingapp.utilidad;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Clase utilizada para mostrar y ocultar el teclado
 */
public class Teclado {

    /**
     * Oculta el teclado
     */
    public static void ocultarTeclado(Activity context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = context.getCurrentFocus();
        if(view==null)
            view = new View(context);
        else
            view.clearFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    /**
     * Muestra el teclado
     * @param focus View en la que se pondra el focus
     */
    public static void mostrarTeclado(Activity context,View focus){
        focus.requestFocus();
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focus,InputMethodManager.SHOW_IMPLICIT);
    }
}
