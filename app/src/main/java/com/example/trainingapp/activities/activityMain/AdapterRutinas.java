package com.example.trainingapp.activities.activityMain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.trainingapp.R;
import com.example.trainingapp.rutina.Rutina;
import com.example.trainingapp.utilidad.Imagenes;
import java.util.List;

/**
 * Adapter utilizado para mostrar la lista de rutinas en la pantalla principal
 */
public class AdapterRutinas extends ArrayAdapter<Rutina> {

    /**
     * Constructor de la clase
     * @param rutinas Lista de rutinas
     */
    public AdapterRutinas(@NonNull Context context, int resource, @NonNull List<Rutina> rutinas) {
        super(context, resource, rutinas);
    }

    @Override
    public @NonNull View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        Rutina rutina = getItem(position);
        if(listItem==null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.item_lista,parent,false);
        TextView nombre = listItem.findViewById(R.id.txtListaNombre);
        nombre.setText(rutina.getNombre());
        nombre.setBackground(Imagenes.getImagenCorta(getContext(),rutina));
        return listItem;
    }
}
