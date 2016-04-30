package com.paramonga.capacitacionasistencia.Presentacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.paramonga.capacitacionasistencia.Adaptador.capacitacion_activaAda;
import com.paramonga.capacitacionasistencia.Modelo.capacitacion_activaEn;
import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;
import com.paramonga.capacitacionasistencia.R;
import com.paramonga.capacitacionasistencia.Utilitario.constantes;
import com.paramonga.capacitacionasistencia.Utilitario.funciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class an_capacitacion_activa extends Activity {

    LinearLayout llBuscando, llPerfilUsuario,llMensajeError;
    TextView tvMensajeError,tvCod_Usr,tvNombre,tvMensaje,tvOrigen,tvCerrar,tvTotalRegistros;
    ListView lvLista;
    SearchView svBuscar;
    ProgressBar pbBuscando;
    Button btnVolverCargar;

    Context consContext;
    capacitacion_activaAda ordencompraAdaptador;
    capacitacion_activaEn itemSeleccionado;
    funciones funcion;
    String ls_cod_usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ln_capacitacion_activa);

        consContext     = this;
        funcion         = new funciones();
        tvCod_Usr       = (TextView)findViewById(R.id.tvCod_usr);
        tvOrigen        = (TextView)findViewById(R.id.tvOrigen);
        tvNombre        = (TextView)findViewById(R.id.tvNombre);
        btnVolverCargar = (Button)findViewById(R.id.btnVolverCargar);
        tvCerrar        = (TextView)findViewById(R.id.tvCerrar);
        tvTotalRegistros= (TextView)findViewById(R.id.tvTotalRegistros);
        llBuscando      = (LinearLayout)findViewById(R.id.llBuscando);
        llPerfilUsuario = (LinearLayout)findViewById(R.id.llPerfilUsuario);
        llMensajeError  = (LinearLayout)findViewById(R.id.llMensajeError);
        tvMensajeError  = (TextView)findViewById(R.id.tvMensajeError);
        tvMensaje       = (TextView)findViewById(R.id.tvMensaje);
        lvLista         = (ListView)findViewById(R.id.lvLista);
        pbBuscando      = (ProgressBar)findViewById(R.id.pbBuscando);
        svBuscar        = (SearchView)findViewById(R.id.svBuscar);

        Intent i=getIntent();
        final Bundle b=i.getExtras();

        ls_cod_usr=b.getString("cod_usr");
        tvCod_Usr.setText(ls_cod_usr);
        tvOrigen.setText(b.getString("origen_alt"));
        tvNombre.setText(b.getString("nombre"));

        new  listaCapacitacionesActivas_Async().execute(ls_cod_usr.trim());

        btnVolverCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  listaCapacitacionesActivas_Async().execute(ls_cod_usr.trim());
            }
        });

        svBuscar.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPerfilUsuario.setVisibility(View.INVISIBLE);
                tvTotalRegistros.setText("TOTAL DE CURSOS: " + ordencompraAdaptador.totalRegistros());
            }
        });

        svBuscar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                llPerfilUsuario.setVisibility(View.VISIBLE);
                return false;
            }
        });

        svBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ordencompraAdaptador.buscar(newText);
                    tvTotalRegistros.setText("TOTAL DE CURSOS: " + ordencompraAdaptador.totalRegistros());
                }
                catch (Exception e){}
                return false;
            }
        });

        tvCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSeleccionado      = ordencompraAdaptador.getItem(position);
                Intent i = new Intent(consContext, an_sesiones.class);
                Bundle bUsuario=new Bundle();
                bUsuario.putString("cod_usr",tvCod_Usr.getText().toString());
                bUsuario.putString("nombre", tvNombre.getText().toString() );
                bUsuario.putString("origen_alt",tvOrigen.getText().toString());
                bUsuario.putString("nro_programacion",itemSeleccionado.getNro_programacion());
                bUsuario.putString("fecha_registro",itemSeleccionado.getFecha_registro());
                bUsuario.putString("desc_capacitacion",itemSeleccionado.getDesc_capacitacion());
                bUsuario.putString("tipo_capacitacion",itemSeleccionado.getTipo_capacitacion());
                bUsuario.putString("clase_curso",itemSeleccionado.getClase_curso());
                bUsuario.putString("tipo_capacitacion_cod",itemSeleccionado.getTipo_capacitacion_cod());

                i.putExtras(bUsuario);
                startActivity(i);
            }
        });

    }

    private class listaCapacitacionesActivas_Async extends AsyncTask<String,Void, respuestaEn> {

        ArrayList<capacitacion_activaEn> listaOrdenCompraArray;
        protected void onPreExecute(){
            lvLista.setVisibility(View.GONE);
            llBuscando.setVisibility(View.VISIBLE);
            pbBuscando.setVisibility(View.VISIBLE);
            tvMensaje.setText("Obteniendo registros");
        }
        @Override
        protected respuestaEn doInBackground(String... params) {
            respuestaEn lista = funcion.ResultadoJson(constantes.IP_CAPC +"Capacitacion.jsp?funcion=1");
            return lista;
        }

        @Override
        protected void onPostExecute(respuestaEn result){

            if(result.totalRegistro>0)
            {
                lvLista.setVisibility(View.VISIBLE);
                llBuscando.setVisibility(View.GONE);
                btnVolverCargar.setVisibility(View.GONE);
                try {
                    JSONArray data = new JSONArray(result.data);
                    listaOrdenCompraArray =new ArrayList<>();
                    //listaIncidencia.clear();
                    capacitacion_activaEn item;
                    for(int i =0 ; i < data.length(); i++){
                        JSONObject fila=data.getJSONObject(i);
                        item=new capacitacion_activaEn(
                                fila.getString("NRO_PROGRAMACION"),
                                fila.getString("DESC_CAPACITACION"),
                                fila.getString("TIPO_CAPACITACION"),
                                fila.getString("CLASE_CURSO"),
                                fila.getString("FECHA_REGISTRO"),
                                fila.getString("TIPO_CAPACITACION_COD"));
                        listaOrdenCompraArray.add(item);
                    }
                    ordencompraAdaptador =  new capacitacion_activaAda(consContext, listaOrdenCompraArray);
                    lvLista.setAdapter(ordencompraAdaptador);
                    tvTotalRegistros.setText("TOTAL DE CURSOS: " + ordencompraAdaptador.totalRegistros());
                }
                catch(JSONException e){
                    result.estado=0;result.mensaje="Error formato de resultado. " + e.getMessage();
                }
            }
            if(result.estado<1 || result.estado==3){
                lvLista.setVisibility(View.GONE);
                pbBuscando.setVisibility(View.GONE);
                llBuscando.setVisibility(View.VISIBLE);
                tvMensaje.setText(result.mensaje);
                btnVolverCargar.setVisibility(View.VISIBLE);
            }
        }
    }

}
