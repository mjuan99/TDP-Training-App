package com.example.trainingapp.utilidad;

/**
 * Clase utilizada para representar varios formatos
 */
public class Formato {

    /**
     * Pasa de cantidad de segundos a formato hh:mm:ss
     * @param segundos Cantidad de segundos
     * @return Formato hh:mm:ss
     */
    public static String formatoReloj(int segundos){
        int hora = segundos/3600;
        int min = (segundos/60)%60;
        int seg = segundos%60;
        return (hora>0?hora+":":"")+(min>9?"":"0")+min+":"+(seg>9?"":"0")+seg;
    }

    /**
     * Pasa de cantidad de segundos a formato m' s"
     * @param segundos Cantidad de segundos
     * @return Formato m' s"
     */
    public static String formatoTextoTiempo(int segundos){
        int min = segundos/60;
        int seg = segundos%60;
        return (min>0?min+"'"+(seg>0?" ":""):"")+(seg>0?seg+"\"":"");
    }

    /**
     * Pasa de formato hh:mm:ss a cantidad de segundos
     * @param hora Hora en formato hh:mm:ss
     * @return Cantidad de segundos
     */
    public static int formatoSegundos(String hora){
        String[] numeros = hora.split(":");
        int segs = 0;
        int mins = 0;
        int horas = 0;
        if(numeros.length>0){
            segs = Integer.parseInt(numeros[numeros.length-1]);
            if(numeros.length>1){
                mins = Integer.parseInt(numeros[numeros.length-2]);
                if(numeros.length>2)
                    horas = Integer.parseInt(numeros[numeros.length-3]);
            }
        }
        return horas*3600+mins*60+segs;
    }

    /**
     * Da formato de fecha dd/mm/aaaa
     * @param dia Dia de la fecha
     * @param mes Mes de la fecha
     * @param anio Anio de la fecha
     * @return Formato dd/mm/aaaa
     */
    public static String formatoFecha(int dia,int mes,int anio){
        return (dia>9?"":"0")+dia+"/"+(mes>9?"":"0")+(mes)+"/"+anio;
    }

    /**
     * Da formato de hora hh:mm
     * @param hora Hora
     * @param minutos Minutos
     * @return Formato hh:mm
     */
    public static String formatoHora(int hora,int minutos){
        return (hora>9?"":"0")+hora+":"+(minutos>9?"":"0")+minutos;
    }
}
