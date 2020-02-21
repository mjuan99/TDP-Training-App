package com.example.trainingapp.activities.activityEditarEjercicios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.example.trainingapp.R;
import com.example.trainingapp.utilidad.Advertencia;
import com.example.trainingapp.utilidad.Formato;
import com.example.trainingapp.rutina.ejercicio.EjDescanso;
import com.example.trainingapp.rutina.ejercicio.EjPorRepeticiones;
import com.example.trainingapp.rutina.ejercicio.EjPorTiempo;
import com.example.trainingapp.rutina.ejercicio.Ejercicio;
import java.util.regex.Pattern;

/**
 * Dialogo usado para la carga de ejercicios
 */
public class DialogEjercicio extends DialogFragment {

    protected Ejercicio ejercicioBase;
    protected CommandConfirmarEjercicio command;

    protected EditText txtNombre;
    protected EditText txtRepeticiones;
    protected EditText txtDuracion;
    protected Spinner spinner;

    /**
     * Setear command
     * @param command Command que se ejecutar al presionar OK en el dialogo
     */
    public void setCommand(CommandConfirmarEjercicio command){
        this.command = command;
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alert;
        Button btnOk,btnCancel;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View ejercicio = inflater.inflate(R.layout.dialog_ejercicio,null);
        String[] tipos = {getString(R.string.porrepeticiones),getString(R.string.portiempo),getString(R.string.descanso)};

        //obtener elementos graficos
        txtNombre = ejercicio.findViewById(R.id.txtDialEjerNombre);
        txtRepeticiones = ejercicio.findViewById(R.id.txtDialEjerRepeticiones);
        txtDuracion = ejercicio.findViewById(R.id.txtDialEjerDuracion);
        spinner = ejercicio.findViewById(R.id.spinDialEjerTipo);

        //configurar elementos graficos
        spinner.setOnItemSelectedListener(new OyenteSpinner());
        spinner.setAdapter(new ArrayAdapter<>(getContext(),R.layout.item_spinner,tipos));
        builder.setView(ejercicio)
                .setPositiveButton(getString(R.string.ok), new OyenteOk())
                .setNegativeButton(getString(R.string.cancelar),null);
        if(ejercicioBase!=null)
            ejercicioBase.accept(new VisitorDialogEjercicio(ejercicio));
        alert = builder.create();
        alert.show();
        btnOk = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        btnCancel = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        btnOk.setTextAppearance(R.style.fuente);
        btnCancel.setTextAppearance(R.style.fuente);
        return alert;
    }

    /**
     * Oyente del boton dialogo
     */
    protected class OyenteOk implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String nombre = txtNombre.getText().toString();
            String cantidad;
            String errores = "";
            Ejercicio ejercicio = null;

            //controlar que los datos ingresados sean validos
            if(nombre.equals("")|| Pattern.matches(".*[<>].*",nombre))
                errores+= getString(R.string.nombreinvalido);
            if(spinner.getSelectedItemPosition()==0){
                cantidad = txtRepeticiones.getText().toString();
                if(!Pattern.matches("[1-9][0-9]*",cantidad))
                    errores+= (errores.equals("")?"":"\n")+getString(R.string.repinvalido);
            }
            else{
                cantidad = txtDuracion.getText().toString();
                if(Pattern.matches("(0*:)?(0*:)?0*",cantidad))
                    errores+= (errores.equals("")?"":"\n")+getString(R.string.duracioninvalida);
                if(!Pattern.matches("(([1-9][0-9]*:)?([0-5]?[0-9]:))?[0-5]?[0-9]",cantidad))
                    errores+= (errores.equals("")?"":"\n")+getString(R.string.tiempoinvalido);
            }
            if(errores.equals("")){

                //si no hubo errores se crea el ejercicio nuevo y se confirma
                switch (spinner.getSelectedItemPosition()){
                    case 0:
                        ejercicio = new EjPorRepeticiones(nombre,Integer.parseInt(cantidad));
                        break;
                    case 1:
                        ejercicio = new EjPorTiempo(nombre, Formato.formatoSegundos(cantidad));
                        break;
                    case 2:
                        ejercicio = new EjDescanso(nombre,Formato.formatoSegundos(cantidad));
                }
                command.confirmarEjercicio(ejercicio);
            }
            else {

                //si hubo errores se advierte al usuario
                Advertencia.mostrarMensaje(getContext(),getString(R.string.error),errores,null);
            }
        }
    }

    /**
     * Oyente del spinner del dialogo
     */
    protected class OyenteSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            //configurar elementos graficos acorde al elemento seleccionado
            if(position==2){
                txtNombre.setText(getString(R.string.descanso));
                txtNombre.setEnabled(false);
                txtDuracion.setVisibility(View.VISIBLE);
                txtRepeticiones.setVisibility(View.GONE);
            }
            else{
                if(txtNombre.getText().toString().equals(getString(R.string.descanso)))
                    txtNombre.setText("");
                txtNombre.setEnabled(true);
                if(position==1){
                    txtDuracion.setVisibility(View.VISIBLE);
                    txtRepeticiones.setVisibility(View.GONE);
                }
                else{
                    txtDuracion.setVisibility(View.GONE);
                    txtRepeticiones.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

    /**
     * Setear ejercicio base
     * @param ejercicio Ejercicio base a utilizar en caso de que se quiera editar
     */
    public void setEjercicioBase(Ejercicio ejercicio){
        ejercicioBase = ejercicio;
    }

    /**
     * Command para indicar la accion a realizar cuando se ingresan los datos de un ejercicio
     */
    public interface CommandConfirmarEjercicio{

        /**
         * Confirma la accion
         * @param ejercicio Ejercicio nuevo
         */
        void confirmarEjercicio(Ejercicio ejercicio);
    }
}
