package com.paramonga.capacitacionasistencia.Presentacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.ImageReader;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.paramonga.capacitacionasistencia.Adaptador.colaboradorAda;
import com.paramonga.capacitacionasistencia.Adaptador.sesionAda;
import com.paramonga.capacitacionasistencia.Modelo.colaboradorEn;
import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;
import com.paramonga.capacitacionasistencia.Modelo.sesionEn;
import com.paramonga.capacitacionasistencia.R;
import com.paramonga.capacitacionasistencia.Utilitario.constantes;
import com.paramonga.capacitacionasistencia.Utilitario.funciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class an_colaborador extends Activity {
    LinearLayout llBuscando, llPerfilUsuario,llMensajeError,llCurso,llSesion,llVisualizar;
    TextView tvMensajeError,tvCod_Usr,tvNombre,tvMensaje,tvOrigen,tvCerrar,tvTotalRegistros,tvTotalMarcados;
    Button btnVolverCargar;
    ListView lvLista;
    SearchView svBuscar;
    ProgressBar pbBuscando;
    TextView tvNro_programacion,tvFecha_registro,tvDesc_capacitacion,tvTipo_capacitacion,tvClase_curso;
    TextView tvFecha_inicio,tvFecha_fin,tvNro_item,tvDescripcion;
    ImageButton imgbOcultar,imgbMostrar;

    funciones funcion;
    Context consContext;
    colaboradorAda colaboradoresAdaptador;
    colaboradorEn itemSeleccionado;

    String ls_cod_usr,ls_tipo_capacitacion_cod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ln_colaborador);

        consContext     = this;
        funcion         = new funciones();
        tvCod_Usr       = (TextView)findViewById(R.id.tvCod_usr);
        tvOrigen        = (TextView)findViewById(R.id.tvOrigen);
        tvNombre        = (TextView)findViewById(R.id.tvNombre);
        tvCerrar        = (TextView)findViewById(R.id.tvCerrar);
        tvTotalRegistros= (TextView)findViewById(R.id.tvTotalRegistros);
        tvTotalMarcados = (TextView)findViewById(R.id.tvTotalMarcados);

        btnVolverCargar = (Button)findViewById(R.id.btnVolverCargar);
        llBuscando      = (LinearLayout)findViewById(R.id.llBuscando);
        llPerfilUsuario = (LinearLayout)findViewById(R.id.llPerfilUsuario);
        llMensajeError  = (LinearLayout)findViewById(R.id.llMensajeError);
        llCurso         = (LinearLayout)findViewById(R.id.llCurso);
        llSesion        = (LinearLayout)findViewById(R.id.llSesion);
        llVisualizar    = (LinearLayout)findViewById(R.id.llVisualizar);
        tvMensajeError  = (TextView)findViewById(R.id.tvMensajeError);
        tvMensaje       = (TextView)findViewById(R.id.tvMensaje);
        lvLista         = (ListView)findViewById(R.id.lvLista);
        pbBuscando      = (ProgressBar)findViewById(R.id.pbBuscando);
        svBuscar        = (SearchView)findViewById(R.id.svBuscar);
        imgbOcultar     = (ImageButton)findViewById(R.id.imgbOcultar);
        imgbMostrar     = (ImageButton)findViewById(R.id.imgbMostrar);

        tvNro_programacion  = (TextView)findViewById(R.id.tvNro_programacion);
        tvFecha_registro    = (TextView)findViewById(R.id.tvFecha_registro);
        tvDesc_capacitacion = (TextView)findViewById(R.id.tvDesc_capacitacion);
        tvTipo_capacitacion = (TextView)findViewById(R.id.tvTipo_capacitacion);
        tvClase_curso       = (TextView)findViewById(R.id.tvClase_curso);

        tvFecha_inicio      = (TextView)findViewById(R.id.tvFecha_inicio);
        tvFecha_fin         = (TextView)findViewById(R.id.tvFecha_fin);
        tvDescripcion       = (TextView)findViewById(R.id.tvDescripcion);
        tvNro_item          = (TextView)findViewById(R.id.tvNro_item);

        Intent i    = getIntent();
        final Bundle b    = i.getExtras();
        ls_cod_usr  = b.getString("cod_usr");
        tvCod_Usr.setText(ls_cod_usr);
        tvOrigen.setText(b.getString("origen_alt"));
        tvNombre.setText(b.getString("nombre"));
        tvNro_programacion.setText(b.getString("nro_programacion"));
        tvFecha_registro.setText(b.getString("fecha_registro"));
        tvDesc_capacitacion.setText(b.getString("desc_capacitacion"));
        tvTipo_capacitacion.setText(b.getString("tipo_capacitacion"));
        tvClase_curso.setText(b.getString("clase_curso"));
        tvFecha_inicio.setText(b.getString("fecha_inicio"));
        tvFecha_fin.setText(b.getString("fecha_fin"));
        tvDescripcion.setText(b.getString("descripcion"));
        tvNro_item.setText(b.getString("nro_item"));
        ls_tipo_capacitacion_cod=b.getString("tipo_capacitacion_cod");
        //Log.e("tipo_capacitacion_cod",ls_tipo_capacitacion_cod);

        new listaColaborador_Async().execute(b.getString("nro_programacion"),b.getString("nro_item"),ls_tipo_capacitacion_cod);

        btnVolverCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new listaColaborador_Async().execute(b.getString("nro_programacion"),b.getString("nro_item"),ls_tipo_capacitacion_cod);
            }
        });
        svBuscar.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPerfilUsuario.setVisibility(View.INVISIBLE);
                tvTotalRegistros.setText("TOTAL DE CURSOS: " + colaboradoresAdaptador.totalRegistros());
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
                    colaboradoresAdaptador.buscar(newText);
                    tvTotalRegistros.setText("TOTAL DE PARTICIPANTES: " + colaboradoresAdaptador.totalRegistros());
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

        imgbOcultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llCurso.setVisibility(View.GONE);
                llSesion.setVisibility(View.GONE);
                imgbMostrar.setVisibility(View.VISIBLE);
                imgbOcultar.setVisibility(View.GONE);
            }
        });

        imgbMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llCurso.setVisibility(View.VISIBLE);
                llSesion.setVisibility(View.VISIBLE);
                imgbOcultar.setVisibility(View.VISIBLE);
                imgbMostrar.setVisibility(View.GONE);
            }
        });

        llVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgbOcultar.getVisibility()==View.VISIBLE){
                    imgbOcultar.performClick();
                }else{
                    imgbMostrar.performClick();
                }
            }
        });

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                llMensajeError.setVisibility(View.GONE);
                itemSeleccionado        = colaboradoresAdaptador.getItem(position);
                String tipo="";
                if(itemSeleccionado.getFlag_asistencia().contains("1")){
                    tipo="D";
                }else{
                    tipo="I";
                }
                new asistencia_Async().execute(tipo,tvNro_programacion.getText().toString(),tvNro_item.getText().toString(),itemSeleccionado.getCod_trabajador(),tvCod_Usr.getText().toString(),"Movil");
            }
        });
    }

    private class listaColaborador_Async extends AsyncTask<String,Void, respuestaEn> {

        ArrayList<colaboradorEn> listaColaboradorArray;
        protected void onPreExecute(){

            lvLista.setVisibility(View.GONE);
            llBuscando.setVisibility(View.VISIBLE);
            pbBuscando.setVisibility(View.VISIBLE);
            tvMensaje.setText("Obteniendo registros");
            btnVolverCargar.setVisibility(View.GONE);
        }
        @Override
        protected respuestaEn doInBackground(String... params) {
            String nro_programacion =params[0];
            String nro_item         =params[1];
            String tipo_capacitacion_cod = params[2];
            respuestaEn lista = funcion.ResultadoJson(constantes.IP_CAPC +"Capacitacion.jsp?funcion=3&nro_programacion=" + nro_programacion + "&nro_item=" + nro_item + "&tipo_capacitacion_cod="+tipo_capacitacion_cod);
            return lista;
        }

        @Override
        protected void onPostExecute(respuestaEn result){

            if(result.totalRegistro>0)
            {
                lvLista.setVisibility(View.VISIBLE);
                llBuscando.setVisibility(View.GONE);
                try {
                    JSONArray data = new JSONArray(result.data);
                    listaColaboradorArray =new ArrayList<>();
                    //listaIncidencia.clear();
                    colaboradorEn item;
                    for(int i =0 ; i < data.length(); i++){
                        JSONObject fila=data.getJSONObject(i);
                        item=new colaboradorEn(
                                fila.getString("LETRA"),
                                fila.getString("NRO_DOC"),
                                fila.getString("NOMBRE"),
                                fila.getString("COD_TRABAJADOR"),
                                "0");//valor por defecto - para el manejo de los estados
                        listaColaboradorArray.add(item);
                    }
                    colaboradoresAdaptador =  new colaboradorAda(consContext, listaColaboradorArray);
                    lvLista.setAdapter(colaboradoresAdaptador);
                    tvTotalRegistros.setText("TOTAL DE PARTICIPANTES: " + colaboradoresAdaptador.totalRegistros());
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

            Log.e("msg", String.valueOf(result.estado) + " x" +  result.mensaje);
        }
    }
    @Override
    public void onBackPressed()
    {
        BotonRetrocederMovil();
        //return;
    }

    public void BotonRetrocederMovil() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                consContext);

        alertDialog.setTitle("Salir?");
        alertDialog.setMessage("Está seguro que desea salir de la aplicación?");
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //android.os.Process.killProcess(android.os.Process.myPid());
                        finish();

                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class asistencia_Async extends AsyncTask<String, Void, respuestaEn>{
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            llMensajeError.setVisibility(View.GONE);
            super.onPreExecute();
            pd= new ProgressDialog(consContext);
            pd.setMessage ("Actualizando registro de asistencia para " + itemSeleccionado.getNombre());
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected respuestaEn doInBackground(String... params) {

            String tipo              = params[0];
            String nro_programacion  = params[1];
            String nro_item          = params[2];
            String cod_trabajador    = params[3];
            String cod_usr           = params[4];
            String estacion          = params[5];

            String url="Capacitacion.jsp?funcion=6&tipo=" + tipo + "&nro_programacion=" + nro_programacion + "&nro_item="+ nro_item + "&cod_trabajador=" + cod_trabajador + "&cod_usr="+cod_usr + "&estacion="+estacion;
            respuestaEn lista = funcion.ResultadoJson(constantes.IP_CAPC + url);
            return lista;
        }

        @Override
        protected void onPostExecute(respuestaEn respuestaEn) {
            super.onPostExecute(respuestaEn);
            pd.dismiss();
            if(respuestaEn.totalRegistro != 1){
                funcion.messageBox(consContext,"Asistencia",respuestaEn.mensaje);
            }
            if(respuestaEn.estado==0){
                llMensajeError.setVisibility(View.VISIBLE);
                tvMensajeError.setText(respuestaEn.mensaje);
                return;
            }

            if(respuestaEn.estado==3)
            {
                llMensajeError.setVisibility(View.VISIBLE);
                tvMensajeError.setText(respuestaEn.mensaje);
                return;
            }

            if(respuestaEn.estado==2){
                colaboradoresAdaptador.actualizarAsistencia(itemSeleccionado);
                tvTotalMarcados.setText("Marcados: " + colaboradoresAdaptador.totalMarcado());
                llMensajeError.setVisibility(View.GONE);
            }

        }
    }
}
