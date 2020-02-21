package com.example.trainingapp.utilidad.lecturaEscritura;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.trainingapp.utilidad.ListaRutinas;
import com.example.trainingapp.rutina.ejercicio.Ejercicio;
import com.example.trainingapp.rutina.frecuencia.Frecuencia;
import com.example.trainingapp.rutina.Rutina;
import com.example.trainingapp.rutina.Serie;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilizada para leer y guardar la informacion de la aplicacion utilizando un formato tipo XML
 * (Creada porque otros metodos para guardar datos presentaban problemas con las ArrayList y con el polimorfismo)
 */
/*
    Ejemplo de codificacion:

    <rutina>Rutina 1
        <frecuencia>
            <tipo>TIPO_SEMANA
            <hora>17:00
            <repetir>1001000            (dias de lunes a domingo 1=true 0=false)
        <serie>4
            <ejercicio>
                <tipo>TIPO_REPETICIONES
                <nombre>Ejercicio 1
                <cantidad>12
            <ejercicio>
                <tipo>TIPO_REPETICONES
                <nombre>Ejercicio 2
                <cantidad>10
        <serie>3
            <ejercicio>
                <tipo>TIPO_TIEMPO
                <nombre>Ejercicio 3
                <cantidad>45
            <ejercicio>
                <tipo>TIPO_DESCANSO
                <nombre>Descanso
                <cantidad>30
    <rutina>Rutina 2
        <frecuencia>
            <tipo>TIPO_UNICA
            <hora>19:00
            <repetir>20/03/2020
        .
        .
        .
 */
public class LecturaEscritura {

    public final static int TIPO_REPETICIONES = 0;
    public final static int TIPO_TIEMPO = 1;
    public final static int TIPO_DESCANSO = 4;
    public final static int TIPO_SEMANA = 2;
    public final static int TIPO_UNICA = 3;
    public final static String stPreferences = "preferences";
    public final static String stRutinas = "rutinas";
    public final static String stRutina = "rutina";
    public final static String stId = "id";
    public final static String stSerie = "serie";
    public final static String stFrecuencia = "frecuencia";
    public final static String stTipo = "tipo";
    public final static String stHora = "hora";
    public final static String stRepetir = "repetir";
    public final static String stEjercicio = "ejercicio";
    public final static String stNombre = "nombre";
    public final static String stCantidad = "cantidad";

    /**
     * Guarda la informacion de la aplicacion
     */
    public static void guardar(Context context) {
        List<Rutina> rutinas = ListaRutinas.getRutinas(context);
        StringBuilder guardar = new StringBuilder();
        Frecuencia frecuencia;
        VisitorCodificar visitor = new VisitorCodificar();
        SharedPreferences preferences = context.getSharedPreferences(stPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (Rutina rutina : rutinas) {
            guardar.append("<"+stRutina+">"+rutina.getNombre()+"<"+stId+">"+rutina.getId());
            frecuencia = rutina.getFrecuencia();
            if (frecuencia != null) {
                frecuencia.accept(visitor);
                guardar.append(visitor.getCodificado());
            }
            for (Serie serie : rutina.getSeries()) {
                guardar.append("<"+stSerie+">"+serie.getVueltas());
                for (Ejercicio ejercicio : serie.getEjercicios()) {
                    ejercicio.accept(visitor);
                    guardar.append(visitor.getCodificado());
                }
            }
        }

        editor.putString(stRutinas, guardar.toString());
        editor.apply();
    }

    /**
     * Lee la informacion de la aplicacion
     * @return Lista de rutinas guardadas
     */
    public static List<Rutina> leerRutinas(Context context){
        SharedPreferences preferences = context.getSharedPreferences(stPreferences,Context.MODE_PRIVATE);
        String leido = preferences.getString(stRutinas,"");
        List<Rutina> rutinas = new ArrayList<>();
        String[] etiquetas //arreglo con todas las etiquetas leidas
                ,etiquetaActual; //arreglo con la etiqueta actual, ej: "<rutina>Nombre" --> etiquetaActual={"rutina","Nombre"}
        Rutina rutinaActual = null;
        Serie serieActual = null;
        BuilderEjercicios builderEjercicios;
        BuilderFrecuencia builderFrecuencia;
        int indice = 1;
        if(!leido.equals("")){
            try{
                etiquetas = leido.split("<");
                builderEjercicios = new BuilderEjercicios();
                builderFrecuencia = new BuilderFrecuencia();
                while(indice<etiquetas.length){

                    //mientras queden etiquetas por leer se avanza el puntero y se decodifica
                    etiquetaActual = etiquetas[indice++].split(">");
                    if(etiquetaActual[0].equals(stRutina)){

                        //decodificar rutina
                        rutinaActual = new Rutina();
                        rutinaActual.setNombre(etiquetaActual[1]);
                        etiquetaActual = etiquetas[indice++].split(">"); //avanzar puntero
                        if(etiquetaActual[0].equals(stId)){
                            rutinaActual.setId(Long.parseLong(etiquetaActual[1]));
                            rutinas.add(rutinaActual);
                        }
                    }
                    else{
                        if(etiquetaActual[0].equals(stFrecuencia)){

                            //decodificar frecuencia
                            etiquetaActual = etiquetas[indice++].split(">"); //avanzar puntero
                            if(etiquetaActual[0].equals(stTipo)){
                                if(etiquetaActual[1].equals(String.valueOf(TIPO_SEMANA))){
                                    builderFrecuencia.setTipo(BuilderFrecuencia.TIPO_SEMANA);
                                }
                                else{
                                    if(etiquetaActual[1].equals(String.valueOf(TIPO_UNICA))){
                                        builderFrecuencia.setTipo(BuilderFrecuencia.TIPO_UNICA);
                                    }
                                }
                                etiquetaActual = etiquetas[indice++].split(">"); //avanzar puntero
                                if(etiquetaActual[0].equals(stHora)){
                                    builderFrecuencia.setHora(etiquetaActual[1]);
                                    etiquetaActual = etiquetas[indice++].split(">"); //avanzar puntero
                                    if(etiquetaActual[0].equals(stRepetir)){
                                        builderFrecuencia.setRepetir(etiquetaActual[1]);
                                        rutinaActual.setFrecuencia(builderFrecuencia.build());
                                    }
                                }
                            }
                        }
                        else {
                            if (etiquetaActual[0].equals(stSerie)) {

                                //decodificar serie
                                serieActual = new Serie();
                                serieActual.setVueltas(Integer.parseInt(etiquetaActual[1]));
                                rutinaActual.getSeries().add(serieActual);
                            } else {
                                if (etiquetaActual[0].equals(stEjercicio)) {

                                    //decodificar ejercicio
                                    etiquetaActual = etiquetas[indice++].split(">"); //avanzar puntero
                                    if (etiquetaActual[0].equals(stTipo)) {
                                        if (etiquetaActual[1].equals(String.valueOf(TIPO_REPETICIONES))) {
                                            builderEjercicios.setTipo(BuilderEjercicios.TIPO_REPETICIONES);
                                        } else {
                                            if (etiquetaActual[1].equals(String.valueOf(TIPO_TIEMPO))) {
                                                builderEjercicios.setTipo(BuilderEjercicios.TIPO_TIEMPO);
                                            }
                                            else{
                                                if(etiquetaActual[1].equals(String.valueOf(TIPO_DESCANSO))){
                                                    builderEjercicios.setTipo(BuilderEjercicios.TIPO_DESCANSO);
                                                }
                                            }
                                        }
                                        etiquetaActual = etiquetas[indice++].split(">"); //avanzar puntero
                                        if (etiquetaActual[0].equals(stNombre)) {
                                            builderEjercicios.setNombre(etiquetaActual[1]);
                                            etiquetaActual = etiquetas[indice++].split(">"); //avanzar puntero
                                            if (etiquetaActual[0].equals(stCantidad)) {
                                                builderEjercicios.setCantidad(Integer.parseInt(etiquetaActual[1]));
                                                serieActual.getEjercicios().add(builderEjercicios.build());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e){

                //si el archivo estuviera corrompido devulve una lista vacia
                rutinas = new ArrayList<>();
            }
        }
        return rutinas;
    }
}
