package com.paramonga.capacitacionasistencia.Presentacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.paramonga.capacitacionasistencia.Adaptador.capacitacion_activaAda;
import com.paramonga.capacitacionasistencia.Adaptador.sesionAda;
import com.paramonga.capacitacionasistencia.Modelo.capacitacion_activaEn;
import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;
import com.paramonga.capacitacionasistencia.Modelo.sesionEn;
import com.paramonga.capacitacionasistencia.R;
import com.paramonga.capacitacionasistencia.Utilitario.constantes;
import com.paramonga.capacitacionasistencia.Utilitario.funciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class an_sesiones extends Activity {
    LinearLayout llBuscando, llPerfilUsuario,llMensajeError;
    TextView tvMensajeError,tvCod_Usr,tvNombre,tvMensaje,tvOrigen,tvCerrar,tvTotalRegistros;
    ListView lvLista;
    SearchView svBuscar;
    ProgressBar pbBuscando;
    TextView tvNro_programacion,tvFecha_registro,tvDesc_capacitacion,tvTipo_capacitacion,tvClase_curso;
    funciones funcion;
    Context consContext;
    sesionAda ordencompraAdaptador;
    sesionEn itemSeleccionado;
    String ls_cod_usr,ls_tipo_capacitacion_cod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ln_sesiones);

        consContext     = this;
        funcion         = new funciones();
        tvCod_Usr       = (TextView)findViewById(R.id.tvCod_usr);
        tvOrigen        = (TextView)findViewById(R.id.tvOrigen);
        tvNombre        = (TextView)findViewById(R.id.tvNombre);
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

        tvNro_programacion  = (TextView)findViewById(R.id.tvNro_programacion);
        tvFecha_registro    = (TextView)findViewById(R.id.tvFecha_registro);
        tvDesc_capacitacion = (TextView)findViewById(R.id.tvDesc_capacitacion);
        tvTipo_capacitacion = (TextView)findViewById(R.id.tvTipo_capacitacion);
        tvClase_curso       = (TextView)findViewById(R.id.tvClase_curso);

        Intent i    = getIntent();
        Bundle b    = i.getExtras();
        ls_cod_usr  = b.getString("cod_usr");
        tvCod_Usr.setText(ls_cod_usr);
        tvOrigen.setText(b.getString("origen_alt"));
        tvNombre.setText(b.getString("nombre"));
        tvNro_programacion.setText(b.getString("nro_programacion"));
        tvFecha_registro.setText(b.getString("fecha_registro"));
        tvDesc_capacitacion.setText(b.getString("desc_capacitacion"));
        tvTipo_capacitacion.setText(b.getString("tipo_capacitacion"));
        tvClase_curso.setText(b.getString("clase_curso"));
        ls_tipo_capacitacion_cod=b.getString("tipo_capacitacion_cod");
        Log.e("tipo-capacitaciopn-cod",ls_tipo_capacitacion_cod);
        new listaSesiones_Async().execute(b.getString("nro_programacion"));
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
                Intent i = new Intent(consContext, an_colaborador.class);
                Bundle bUsuario=new Bundle();
                bUsuario.putString("cod_usr",tvCod_Usr.getText().toString());
                bUsuario.putString("nombre", tvNombre.getText().toString() );
                bUsuario.putString("origen_alt",tvOrigen.getText().toString());

                bUsuario.putString("nro_programacion",tvNro_programacion.getText().toString());
                bUsuario.putString("fecha_registro", tvFecha_registro.getText().toString() );
                bUsuario.putString("desc_capacitacion",tvDesc_capacitacion.getText().toString());
                bUsuario.putString("tipo_capacitacion",tvTipo_capacitacion.getText().toString());
                bUsuario.putString("clase_curso",tvClase_curso.getText().toString());

                bUsuario.putString("fecha_inicio",itemSeleccionado.getFecha_inicio() );
                bUsuario.putString("fecha_fin",itemSeleccionado.getFecha_fin());
                bUsuario.putString("descripcion",itemSeleccionado.getDescripcion());
                bUsuario.putString("nro_item",itemSeleccionado.getNro_item());
                bUsuario.putString("tipo_capacitacion_cod",ls_tipo_capacitacion_cod);

                i.putExtras(bUsuario);
                startActivity(i);
            }
        });
    }

    private class listaSesiones_Async extends AsyncTask<String,Void, respuestaEn> {

        ArrayList<sesionEn> listaOrdenCompraArray;
        protected void onPreExecute(){
            lvLista.setVisibility(View.GONE);
            llBuscando.setVisibility(View.VISIBLE);
            pbBuscando.setVisibility(View.VISIBLE);
            tvMensaje.setText("Obteniendo registros");
        }
        @Override
        protected respuestaEn doInBackground(String... params) {
            String nro_programacion=params[0];
            respuestaEn lista = funcion.ResultadoJson(constantes.IP_CAPC +"Capacitacion.jsp?funcion=2&nro_programacion=" + nro_programacion);
            return lista;
        }

        @Override
        protected void onPostExecute(respuestaEn result){

            if(result.getTotalRegistro()>0)
            {
                lvLista.setVisibility(View.VISIBLE);
                llBuscando.setVisibility(View.GONE);
                try {
                    JSONArray data = new JSONArray(result.getData());
                    listaOrdenCompraArray =new ArrayList<>();
                    //listaIncidencia.clear();
                    sesionEn item;
                    for(int i =0 ; i < data.length(); i++){
                        JSONObject fila=data.getJSONObject(i);
                        item=new sesionEn(
                                fila.getString("NRO_ITEM"),
                                fila.getString("FECHA_INICIO"),
                                fila.getString("FECHA_FIN"),
                                fila.getString("DESCRIPCION"));
                        listaOrdenCompraArray.add(item);
                    }
                    ordencompraAdaptador =  new sesionAda(consContext, listaOrdenCompraArray);
                    lvLista.setAdapter(ordencompraAdaptador);
                    tvTotalRegistros.setText("TOTAL DE SESIONES: " + ordencompraAdaptador.totalRegistros());
                }
                catch(JSONException e){
                    //result.estado=0;result.mensaje="Error formato de resultado. " + e.getMessage();
                }
            }
            /*if(result.estado<1 || result.estado==3){
                lvLista.setVisibility(View.GONE);
                pbBuscando.setVisibility(View.GONE);
                llBuscando.setVisibility(View.VISIBLE);
                tvMensaje.setText(result.mensaje);
            }*/
        }
    }
}
