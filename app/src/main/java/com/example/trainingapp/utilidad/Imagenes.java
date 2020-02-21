package com.example.trainingapp.utilidad;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.example.trainingapp.R;
import com.example.trainingapp.rutina.Rutina;
import java.util.List;

/**
 * Clase utilizada para obtener imagenes en ejecucion
 */
public class Imagenes {

    protected final static int[] imagenesCortas = {R.drawable.imagencorta1,R.drawable.imagencorta2,R.drawable.imagencorta3,R.drawable.imagencorta4,R.drawable.imagencorta5};
    protected final static int[] imagenesGrandes = {R.drawable.imagengrande1,R.drawable.imagengrande2,R.drawable.imagengrande3,R.drawable.imagengrande4,R.drawable.imagengrande5};

    /**
     * Devuelve la imagen corta correspondiente a la rutina
     * @param rutina Rutina para la cual se pide la imagen
     * @return Imagen corta correspondiente a la rutina
     */
    public static Drawable getImagenCorta(Context context,Rutina rutina){
        List<Rutina> rutinas = ListaRutinas.getRutinas(context);
        int indice = rutinas.indexOf(rutina);
        if(indice==-1)
            indice = rutinas.size();
        return context.getDrawable(imagenesCortas[indice%5]);
    }

    /**
     * Devuelve la imagen grande correspondiente a la rutina
     * @param rutina Rutina para la cual se pide la imagen
     * @return Imagen grande correspondiente a la rutina
     */
    public static Drawable getImagenGrande(Context context, Rutina rutina){
        List<Rutina> rutinas = ListaRutinas.getRutinas(context);
        int indice = rutinas.indexOf(rutina);
        if(indice==-1)
            indice = rutinas.size();
        return context.getDrawable(imagenesGrandes[indice%5]);
    }

    /**
     * Devuelve la imagen requerida de attributes
     * @param id Id de la imagen
     * @return Imagen requerida
     */
    public static Drawable getImagenAttrs(Context context,int id){
        int[] attrs = {id};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable imagen = ta.getDrawable(0);
        imagen.setTint(Color.BLACK);
        ta.recycle();
        return imagen;
    }
}
