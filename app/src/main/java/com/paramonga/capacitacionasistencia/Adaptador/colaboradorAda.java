package com.paramonga.capacitacionasistencia.Adaptador;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paramonga.capacitacionasistencia.Modelo.colaboradorEn;
import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;
import com.paramonga.capacitacionasistencia.Modelo.sesionEn;
import com.paramonga.capacitacionasistencia.R;
import com.paramonga.capacitacionasistencia.Utilitario.constantes;
import com.paramonga.capacitacionasistencia.Utilitario.funciones;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jquihue on 17/02/2016.
 */
public class colaboradorAda extends ArrayAdapter<colaboradorEn>
{
    private List<colaboradorEn> colaboradorEnLista =null;
    private ArrayList<colaboradorEn>   colaboradorEnListaArrayFiltro;
    funciones funcion;
    Context consContext;

    public colaboradorAda(Context context, ArrayList<colaboradorEn> lista) {
        super(context, 0,lista);
        funcion=new funciones();
        consContext=context;
        this.colaboradorEnLista =lista;
        colaboradorEnListaArrayFiltro=new ArrayList<colaboradorEn>();
        this.colaboradorEnListaArrayFiltro.addAll(this.colaboradorEnLista);
    }

    public colaboradorEn getItem(int posicion)
    {
        if(colaboradorEnLista != null)
            return colaboradorEnLista.get(posicion);
        return null;
    }

     public View getView(final int position,View convertView, ViewGroup parent)
     {
         if(convertView ==null)
         {
             convertView= LayoutInflater.from(getContext()).inflate(R.layout.ilv_participantes,parent,false);
         }
         final colaboradorEn item     = getItem(position);

         TextView tvNro_item        = (TextView)convertView.findViewById(R.id.tvNro_item);
         TextView tvDni             = (TextView)convertView.findViewById(R.id.tvDni);
         TextView tvNombre_completo = (TextView)convertView.findViewById(R.id.tvNombre_completo);
         LinearLayout llColaborador     = (LinearLayout)convertView.findViewById(R.id.llColaborador);

         tvNro_item.setText(item.getLetra());
         tvDni.setText(item.getNro_doc());
         tvNombre_completo.setText(item.getNombre());

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
            for(colaboradorEn fila: colaboradorEnListaArrayFiltro)
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

    public String totalMarcado(){
        int i=0;
        for(colaboradorEn item:colaboradorEnLista){
            if(item.getFlag_asistencia().contains("1")){
                i++;
            }
        }
        return String.valueOf(i);
    }
    public void deleteItem(colaboradorEn item){
        colaboradorEnLista.remove(item);
        notifyDataSetChanged();
    }

    public void actualizarAsistencia(int posicion,String flag_estado){
        colaboradorEn item= colaboradorEnListaArrayFiltro.get(posicion);
        item.setFlag_asistencia(flag_estado);
        colaboradorEnListaArrayFiltro.set(posicion,item);
        int i=0;
        for(colaboradorEn reg:colaboradorEnLista){
            if(reg.getNro_doc() == item.getNro_doc()){
                colaboradorEnLista.set(i,item);
                Log.e("item","actualizado");
            }
            i++;
        }
        //colaboradorEnLista.set(posicion, )
    }

    public void actualizarAsistencia(colaboradorEn item){
        if(item.getFlag_asistencia().contains("1")){
            item.setFlag_asistencia("0");
        }else{
            item.setFlag_asistencia("1");
        }

        int i=0;
        for(colaboradorEn cola:colaboradorEnLista){
            if(cola.getNro_doc()==item.getNro_doc()){
               colaboradorEnLista.set(i,item);
                notifyDataSetChanged();
            }
            i++;
        }
    }



}
