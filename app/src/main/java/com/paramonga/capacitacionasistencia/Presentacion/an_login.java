package com.paramonga.capacitacionasistencia.Presentacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;
import com.paramonga.capacitacionasistencia.R;
import com.paramonga.capacitacionasistencia.Utilitario.constantes;
import com.paramonga.capacitacionasistencia.Utilitario.funciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class an_login extends Activity {
    Button btnAceptar;
    TextView tvAfiliar,tvMensajeError,tvValidando;
    EditText etUsuario;
    LinearLayout llMensajeError,llValindando;
    ProgressBar pbProgreso;
    TextView imgbCerrar;

    Context consContext;
    funciones funcion;
    String idDispositivo=null,cod_usr=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ln_login);

        etUsuario       =(EditText)findViewById(R.id.etUsuario);
        llMensajeError  =(LinearLayout)findViewById(R.id.llMensajeError);
        tvMensajeError  =(TextView)findViewById(R.id.tvMensajeError);
        llValindando    =(LinearLayout)findViewById(R.id.llValidando);
        btnAceptar      =(Button)findViewById(R.id.btnAceptar);
        tvValidando     =(TextView)findViewById(R.id.tvValidando);
        pbProgreso      =(ProgressBar)findViewById(R.id.pbProgreso);
        tvAfiliar       =(TextView)findViewById(R.id.tvAfiliar);
        imgbCerrar      =(TextView)findViewById(R.id.tvCerrar);

        idDispositivo   = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        consContext     =this;
        funcion         =new funciones();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar usuario
                llMensajeError.setVisibility(View.INVISIBLE);
                if(!funcion.conectado(consContext))
                {
                    llMensajeError.setVisibility(View.VISIBLE);
                    tvMensajeError.setText(R.string.mensaje_error_conexion);
                    etUsuario.requestFocus();
                    return;
                }
                if(etUsuario.getText().toString().isEmpty())
                {
                    llMensajeError.setVisibility(View.VISIBLE);
                    tvMensajeError.setText("Ingrese un nombre de usuario.");
                    etUsuario.requestFocus();
                    return;
                }
                InputMethodManager imm=(InputMethodManager)getSystemService(consContext.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etUsuario.getWindowToken(),0);

                Log.e("Iniciando", "consulta a servidor");
                usuario_Async u=new usuario_Async();
                cod_usr=etUsuario.getText().toString();
                u.execute(cod_usr,idDispositivo);
                //startActivity(new Intent(consContext,an_acceso.class));

            }
        });
        etUsuario.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO){
                    btnAceptar.performClick();
                    return true;
                }
                return false;
            }
        });

        imgbCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvAfiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bUsuario=new Bundle();
                bUsuario.putString("cod_usr",cod_usr);
                Intent i =new Intent(consContext,an_afiliacion.class);
                cod_usr= etUsuario.getText().toString();
                i.putExtras(bUsuario);
                startActivity(i);
            }
        });

    }

    private class usuario_Async extends AsyncTask<String, Void, respuestaEn>
    {
        private respuestaEn respuesta=null;

        protected void onPreExecute()
        {
            llValindando.setVisibility(View.VISIBLE);
            tvValidando.setText("Verificando registro de usuario...");
            pbProgreso.setVisibility(View.VISIBLE);
            btnAceptar.setEnabled(false);
        }
        @Override
        protected respuestaEn doInBackground(String... params) {
            String url= constantes.IP_CAPC + "Afiliacion.jsp?funcion=2&cod_app=" + constantes.COD_APP + "&cod_usr=" + params[0] + "&iddispositivo=" + params[1];
            Log.e("url sw",url);
            respuesta = funcion.ResultadoJson(url);
            return respuesta;
        }
        protected void onPostExecute(respuestaEn result)
        {
            Log.e("mensaje respuesta", result.mensaje);

            if(result.estado==0){
                llValindando.setVisibility(View.VISIBLE);
                pbProgreso.setVisibility(View.GONE);
                btnAceptar.setEnabled(true);
                tvValidando.setText(result.mensaje);
                return;
            }

            if(result.estado==3)
            {
                llValindando.setVisibility(View.VISIBLE);
                pbProgreso.setVisibility(View.GONE);
                btnAceptar.setEnabled(true);
                tvValidando.setText(result.mensaje);
                return;
            }

            if(result.estado==1){

                Intent i =new Intent(consContext,an_capacitacion_activa.class);
                String nombre="",origen_alt="",cod_trabajador="";
                Bundle bUsuario=new Bundle();
                try {
                    JSONArray data = new JSONArray(result.data);
                    JSONObject json_data= data.getJSONObject(0);
                    nombre = json_data.getString("NOMBRE");
                    origen_alt=json_data.getString("ORIGEN_ALT");
                    cod_trabajador=json_data.getString("COD_TRABAJADOR");
                }
                catch(JSONException e){
                    Log.e("Errror JSONException",e.getMessage());
                }

                bUsuario.putString("cod_usr",cod_usr);
                bUsuario.putString("nombre", nombre );
                bUsuario.putString("origen_alt",origen_alt);
                bUsuario.putString("cod_trabajador",cod_trabajador);
                i.putExtras(bUsuario);
                startActivity(i);
                finish();
            }

        }
    }

}
