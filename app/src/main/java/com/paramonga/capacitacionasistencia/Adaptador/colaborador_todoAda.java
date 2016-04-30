package com.paramonga.capacitacionasistencia.Adaptador;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paramonga.capacitacionasistencia.Modelo.colaboradorEn;
import com.paramonga.capacitacionasistencia.Modelo.colaborador_todoEn;
import com.paramonga.capacitacionasistencia.R;
import com.paramonga.capacitacionasistencia.Utilitario.funciones;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jquihue on 17/02/2016.
 */
public class colaborador_todoAda extends ArrayAdapter<colaborador_todoEn>
{
    private List<colaborador_todoEn> colaboradorEnLista =null;
    private ArrayList<colaborador_todoEn>   colaboradorEnListaArrayFiltro;
    funciones funcion;
    Context consContext;

    public colaborador_todoAda(Context context, ArrayList<colaborador_todoEn> lista) {
        super(context, 0,lista);
        funcion=new funciones();
        consContext=context;
        this.colaboradorEnLista =lista;
        colaboradorEnListaArrayFiltro=new ArrayList<colaborador_todoEn>();
        this.colaboradorEnListaArrayFiltro.addAll(this.colaboradorEnLista);
    }

    public colaborador_todoEn getItem(int posicion)
    {
        if(colaboradorEnLista != null)
            return colaboradorEnLista.get(posicion);
        return null;
    }

     public View getView(final int position,View convertView, ViewGroup parent)
     {
         if(convertView ==null)
         {
             convertView= LayoutInflater.from(getContext()).inflate(R.layout.ilv_participantes_todo,parent,false);
         }
         final colaborador_todoEn item     = getItem(position);

         TextView tvNro_item        = (TextView)convertView.findViewById(R.id.tvNro_item);
         TextView tvDni             = (TextView)convertView.findViewById(R.id.tvDni);
         TextView tvNombre_completo = (TextView)convertView.findViewById(R.id.tvNombre_completo);
         TextView tvLugar           = (TextView)convertView.findViewById(R.id.tvLugar);
         TextView tvFecha_inicio   = (TextView)convertView.findViewById(R.id.tvFecha_inicio);
         TextView tvFecha_fin       = (TextView)convertView.findViewById(R.id.tvFecha_fin);
         LinearLayout llColaborador     = (LinearLayout)convertView.findViewById(R.id.llColaborador);

         tvNro_item.setText(item.getLetra());
         tvDni.setText(item.getNro_doc());
         tvNombre_completo.setText(item.getNombre());
         tvLugar.setText(item.getLugar());
         tvFecha_inicio.setText(item.getFecha_inicio());
         tvFecha_fin.setText(item.getFecha_fin());

         if(item.getFlag_asistencia().contains("1")){
             llColaborador.setBackgroundResource(R.color.color_verde);
         }else{
             llColaborador.setBackgroundResource(R.color.color_rojo);
         }
         return convertView;
     }

    public void buscar(String criterio)
    {
        criterio=criterio.toLowerCase();
        colaboradorEnLista.clear();
        if(criterio.isEmpty() || criterio.length() == 0)
        {
            colaboradorEnLista.addAll(colaboradorEnListaArrayFiltro);
        }
        else
        {
            for(colaborador_todoEn fila: colaboradorEnListaArrayFiltro)
            {
                if(fila.getNombre().toLowerCase().contains(criterio)||  fila.getNro_doc().toLowerCase().contains(criterio))
                {
                    colaboradorEnLista.add(fila);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String totalRegistros(){
        return String.valueOf(colaboradorEnLista.size()) ;
    }

    public void deleteItem(colaborador_todoEn item){
        colaboradorEnLista.remove(item);
        notifyDataSetChanged();
    }

    public void actualizarAsistencia(int posicion,String flag_estado){
        colaborador_todoEn item= colaboradorEnListaArrayFiltro.get(posicion);
        item.setFlag_asistencia(flag_estado);
        colaboradorEnListaArrayFiltro.set(posicion,item);
        int i=0;
        for(colaborador_todoEn reg:colaboradorEnLista){
            if(reg.getNro_doc() == item.getNro_doc()){
                colaboradorEnLista.set(i,item);
                Log.e("item","actualizado");
            }
            i++;
        }
        //colaboradorEnLista.set(posicion, )
    }

    public void actualizarAsistencia(colaborador_todoEn item){
        if(item.getFlag_asistencia().contains("1")){
            item.setFlag_asistencia("0");
        }else{
            item.setFlag_asistencia("1");
        }

        int i=0;
        for(colaborador_todoEn cola:colaboradorEnLista){
            if(cola.getNro_doc()==item.getNro_doc()){
               colaboradorEnLista.set(i,item);
                notifyDataSetChanged();
            }
            i++;
        }
    }



}
