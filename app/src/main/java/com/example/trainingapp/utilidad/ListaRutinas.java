package com.example.trainingapp.utilidad;

import android.content.Context;
import com.example.trainingapp.rutina.Rutina;
import com.example.trainingapp.utilidad.lecturaEscritura.LecturaEscritura;
import java.util.Iterator;
import java.util.List;

/**
 * Clase utilizada para obtener la lista de rutinas y la rutina actual desde cualquier clase de la aplicacion
 * Funcionamiento tipo Singleton
 */
public class ListaRutinas {

    protected static List<Rutina> rutinas;
    protected static Rutina actual;

    /**
     * Devuelve la lista de rutinas
     * @return Lista de rutinas
     */
    public static List<Rutina> getRutinas(Context context){
        if (rutinas==null)
            rutinas = LecturaEscritura.leerRutinas(context);
        return rutinas;
    }

    /**
     * Devuelve la rutina requerida
     * @param id Id de la rutina requerida
     * @return Rutina requerida
     */
    public static Rutina getRutina(Context context,long id){
        boolean encontrado = false;
        Rutina rutina = null;
        Iterator<Rutina> iterator;
        if (rutinas==null)
            rutinas = LecturaEscritura.leerRutinas(context);
        iterator = rutinas.iterator();
        while(iterator.hasNext()&&!encontrado){
            rutina = iterator.next();
            if(id==rutina.getId()){
                encontrado = true;
            }
        }
        if(!encontrado)
            rutina = null;
        return rutina;
    }

    /**
     * Setea la rutina actual sobre la cual se esta trabajando
     * @param rutina Rutina actual
     */
    public static void setActual(Rutina rutina){
        actual = rutina;
    }

    /**
     * Devuelve la rutina actual sobre la cual se esta trabajando
     * @return Rutina actual
     */
    public static Rutina getActual(){
        return actual;
    }
}
