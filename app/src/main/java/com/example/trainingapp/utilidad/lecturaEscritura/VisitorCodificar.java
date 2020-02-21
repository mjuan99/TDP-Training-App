package com.example.trainingapp.utilidad.lecturaEscritura;

import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaPorSemana;
import com.example.trainingapp.rutina.frecuencia.FrecuenciaUnicaVez;
import com.example.trainingapp.utilidad.Visitor;

/**
 * Visitor utilizado para codificar los elementos de una rutina y luego guardarlos
 */
public class VisitorCodificar implements Visitor {

    protected String codificado;

    public String getCodificado(){
        return codificado;
    }

    @Override
    public void visit(EjPorRepeticiones ejPorRepeticiones) {

        //codificar ejercicio
        codificado = "<"+LecturaEscritura.stEjercicio+"><"+LecturaEscritura.stTipo+">"+LecturaEscritura.TIPO_REPETICIONES
                +"<"+LecturaEscritura.stNombre+">"+ejPorRepeticiones.getNombre()
                +"<"+LecturaEscritura.stCantidad+">"+ejPorRepeticiones.getRepeticiones();
    }

    @Override
    public void visit(EjPorTiempo ejPorTiempo) {

        //codificar ejercicio
        codificado = "<"+LecturaEscritura.stEjercicio+"><"+LecturaEscritura.stTipo+">"+LecturaEscritura.TIPO_TIEMPO
                +"<"+LecturaEscritura.stNombre+">"+ejPorTiempo.getNombre()
                +"<"+LecturaEscritura.stCantidad+">"+ejPorTiempo.getDuracion();
    }

    @Override
    public void visit(EjDescanso ejDescanso) {

        //codificar ejercicio
        codificado = "<"+LecturaEscritura.stEjercicio+"><"+LecturaEscritura.stTipo+">"+LecturaEscritura.TIPO_DESCANSO
                +"<"+LecturaEscritura.stNombre+">"+ejDescanso.getNombre()
                +"<"+LecturaEscritura.stCantidad+">"+ejDescanso.getDuracion();
    }

    @Override
    public void visit(FrecuenciaPorSemana frecuencia) {

        //codificar frecuencia
        boolean[] dias=frecuencia.getDias();
        StringBuilder codDias = new StringBuilder();
        for(int i = 0;i<7;i++){

            //codificar dias marcados con 1 y no marcados con 0. Ej: 1001000
            codDias.append(dias[i]?"1":"0");
        }
        codificado = "<"+LecturaEscritura.stFrecuencia+"><"+LecturaEscritura.stTipo+">"+LecturaEscritura.TIPO_SEMANA
                +"<"+LecturaEscritura.stHora+">"+frecuencia.getHora().toString()
                +"<"+LecturaEscritura.stRepetir+">"+codDias;
    }

    @Override
    public void visit(FrecuenciaUnicaVez frecuencia) {

        //codificar frecuencia
        codificado = "<"+LecturaEscritura.stFrecuencia+"><"+LecturaEscritura.stTipo+">"+LecturaEscritura.TIPO_UNICA
                +"<"+LecturaEscritura.stHora+">"+frecuencia.getHora().toString()
                +"<"+LecturaEscritura.stRepetir+">"+frecuencia.getFecha().toString();
    }
}
