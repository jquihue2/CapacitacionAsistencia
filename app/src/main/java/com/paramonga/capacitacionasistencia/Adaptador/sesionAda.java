package com.paramonga.capacitacionasistencia.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paramonga.capacitacionasistencia.Modelo.capacitacion_activaEn;
import com.paramonga.capacitacionasistencia.Modelo.sesionEn;
import com.paramonga.capacitacionasistencia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jquihue on 17/02/2016.
 */
public class sesionAda extends ArrayAdapter<sesionEn>
{
    private List<sesionEn> sesionEnLista =null;
    private ArrayList<sesionEn>   sesionEnListaArrayFiltro;

    public sesionAda(Context context, ArrayList<sesionEn> lista) {
        super(context, 0,lista);
        this.sesionEnLista =lista;
        sesionEnListaArrayFiltro=new ArrayList<sesionEn>();
        this.sesionEnListaArrayFiltro.addAll(this.sesionEnLista);
    }

    public sesionEn getItem(int posicion)
    {
        if(sesionEnLista != null)
            return sesionEnLista.get(posicion);
        return null;
    }

     public View getView(int position,View convertView, ViewGroup parent)
     {
         if(convertView ==null)
         {
             convertView= LayoutInflater.from(getContext()).inflate(R.layout.ilv_sesion,parent,false);
         }
         sesionEn item     = getItem(position);

         TextView tvNro_item        = (TextView)convertView.findViewById(R.id.tvNro_item);
         TextView tvFecha_inicio    = (TextView)convertView.findViewById(R.id.tvFecha_inicio);
         TextView tvFecha_fin       = (TextView)convertView.findViewById(R.id.tvFecha_fin);
         TextView tvDescripcion     = (TextView)convertView.findViewById(R.id.tvDescripcion);

         tvNro_item.setText(item.getNro_item());
         tvFecha_inicio.setText(item.getFecha_inicio());
         tvFecha_fin.setText(item.getFecha_fin());
         tvDescripcion.setText(item.getDescripcion());

         return convertView;
     }

    public void buscar(String criterio)
    {
        criterio=criterio.toLowerCase();
        sesionEnLista.clear();
        if(criterio.isEmpty() || criterio.length() == 0)
        {
            sesionEnLista.addAll(sesionEnListaArrayFiltro);
        }
        else
        {
            for(sesionEn fila: sesionEnListaArrayFiltro)
            {
                if(fila.getDescripcion().toLowerCase().contains(criterio)||  fila.getFecha_inicio().toLowerCase().contains(criterio)|| fila.getFecha_fin().toLowerCase().contains(criterio))
                {
                    sesionEnLista.add(fila);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String totalRegistros(){
        return String.valueOf(sesionEnLista.size()) ;
    }

    public void deleteItem(sesionEn item){
        sesionEnLista.remove(item);
        notifyDataSetChanged();
    }
}
