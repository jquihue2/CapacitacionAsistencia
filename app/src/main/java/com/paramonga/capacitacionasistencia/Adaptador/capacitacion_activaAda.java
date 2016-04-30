package com.paramonga.capacitacionasistencia.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.paramonga.capacitacionasistencia.Modelo.capacitacion_activaEn;
import com.paramonga.capacitacionasistencia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jquihue on 16/02/2016.
 */
public class capacitacion_activaAda extends ArrayAdapter<capacitacion_activaEn>
{
    private List<capacitacion_activaEn> conformidadpagoModLista =null;
    private ArrayList<capacitacion_activaEn>   conformidadpagoModListaArrayFiltro;

    public capacitacion_activaAda(Context context, ArrayList<capacitacion_activaEn> lista) {
        super(context, 0,lista);
        this.conformidadpagoModLista =lista;
        conformidadpagoModListaArrayFiltro=new ArrayList<capacitacion_activaEn>();
        this.conformidadpagoModListaArrayFiltro.addAll(this.conformidadpagoModLista);
    }

    public capacitacion_activaEn getItem(int posicion)
    {
        if(conformidadpagoModLista != null)
            return conformidadpagoModLista.get(posicion);
        return null;
    }

     public View getView(int position,View convertView, ViewGroup parent)
     {
         if(convertView ==null)
         {
             convertView= LayoutInflater.from(getContext()).inflate(R.layout.ilv_capacitacion_activa,parent,false);
         }
         capacitacion_activaEn item     = getItem(position);

         TextView tvNro_programacion    = (TextView)convertView.findViewById(R.id.tvNro_programacion);
         TextView tvFecha_registro      = (TextView)convertView.findViewById(R.id.tvFecha_registro);
         TextView tvDesc_capacitacion   = (TextView)convertView.findViewById(R.id.tvDesc_capacitacion);
         TextView tvTipo_capacitacion   = (TextView)convertView.findViewById(R.id.tvTipo_capacitacion);
         TextView tvClase_curso         = (TextView)convertView.findViewById(R.id.tvClase_curso);


         tvNro_programacion.setText(item.getNro_programacion());
         tvFecha_registro.setText(item.getFecha_registro());
         tvDesc_capacitacion.setText(item.getDesc_capacitacion());
         tvTipo_capacitacion.setText(item.getTipo_capacitacion());
         tvClase_curso.setText(item.getClase_curso());


         return convertView;
     }

    public void buscar(String criterio)
    {
        criterio=criterio.toLowerCase();
        conformidadpagoModLista.clear();
        if(criterio.isEmpty() || criterio.length() == 0)
        {
            conformidadpagoModLista.addAll(conformidadpagoModListaArrayFiltro);
        }
        else
        {
            for(capacitacion_activaEn fila: conformidadpagoModListaArrayFiltro)
            {
                if(fila.getNro_programacion().toLowerCase().contains(criterio)||  fila.getFecha_registro().toLowerCase().contains(criterio)|| fila.getDesc_capacitacion().toLowerCase().contains(criterio))
                {
                    conformidadpagoModLista.add(fila);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String totalRegistros(){
        return String.valueOf(conformidadpagoModLista.size()) ;
    }

    public void deleteItem(capacitacion_activaEn item){
        conformidadpagoModLista.remove(item);
        notifyDataSetChanged();
    }
}
