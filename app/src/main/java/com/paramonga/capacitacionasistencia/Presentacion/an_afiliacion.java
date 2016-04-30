package com.paramonga.capacitacionasistencia.Presentacion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;
import com.paramonga.capacitacionasistencia.R;
import com.paramonga.capacitacionasistencia.Utilitario.constantes;
import com.paramonga.capacitacionasistencia.Utilitario.funciones;

public class an_afiliacion extends Activity {
    TextView tvMensajeError,tvCerrar;
    EditText etIdDispositivo,etUsuario,etCorreo;
    Button btnGenerarsolicitud;
    LinearLayout llMensajeError;
    Context consContext;
    funciones funcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ln_afiliacion);
        tvMensajeError  =(TextView)findViewById(R.id.tvMensajeError);
        etIdDispositivo = (EditText)findViewById(R.id.etIdDispositivo);
        etUsuario       = (EditText)findViewById(R.id.etUsuario);
        etCorreo        = (EditText)findViewById(R.id.etCorreo);
        llMensajeError  = (LinearLayout)findViewById(R.id.llMensajeError);
        btnGenerarsolicitud = (Button)findViewById(R.id.btnGenerarSolicitud);
        tvCerrar        = (TextView)findViewById(R.id.tvCerrar);

        consContext=this;
        funcion=new funciones();
        //Asignar el ID del dispositivo
        etIdDispositivo.setText(Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));

        Intent i=getIntent();
        final Bundle b =i.getExtras();
        etUsuario.setText(b.getString("cod_usr"));

        btnGenerarsolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnGenerarsolicitud.getText().toString().contains("Salir")){
                    finish();
                    return;
                }
                //Validar el ingreso de dato
                if(etUsuario.getText().toString().isEmpty())
                {
                    llMensajeError.setVisibility(View.VISIBLE);
                    tvMensajeError.setText("Ingrese un nombre de usuario.");
                    etUsuario.setFocusable(true);
                    return;
                }
                if(etCorreo.getText().toString().isEmpty())
                {
                    llMensajeError.setVisibility(View.VISIBLE);
                    tvMensajeError.setText("Ingrese un correo válido");
                    etCorreo.setFocusable(true);
                    return;
                }
                if(!funcion.validarCorreo(etCorreo.getText()))
                {
                    llMensajeError.setVisibility(View.VISIBLE);
                    tvMensajeError.setText("Correo inválido, ingrese correo.");
                    etCorreo.setFocusable(true);
                    return;
                }
                //insertar registro
                // new afiliar_dispositivo_insert_Async(etIdDipositivo.getText().toString(),etUsuario.getText().toString(),etCorreo.getText().toString()).execute();
                new afiliarDispositivo_Async().execute(etIdDispositivo.getText().toString(),etUsuario.getText().toString(),etCorreo.getText().toString(), constantes.COD_APP);
            }
        });

        etUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                llMensajeError.setVisibility(View.INVISIBLE);
                tvMensajeError.setText("Ingrese un nombre de usuario");
                etUsuario.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                llMensajeError.setVisibility(View.INVISIBLE);
                tvMensajeError.setText("Ingrese un correo válido");
                etCorreo.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class afiliarDispositivo_Async extends AsyncTask<String,Void,respuestaEn> {
        private ProgressDialog pdEspera;
        protected void onPreExecute(){
            pdEspera=new ProgressDialog(consContext);
            pdEspera.setMessage("Enviando registro...");
            pdEspera.show();
        }

        @Override
        protected respuestaEn doInBackground(String... params) {

            String iddispositivo    =params[0];
            String cod_usr          =params[1];
            String mail             =params[2];
            String cod_app          =params[3];
            String mac              =funcion.obtenerMacAdress(consContext);
            String modeloEquipo     =funcion.nombreTerminal();
            String url              = constantes.IP_CAPC + "Afiliacion.jsp?funcion=1&cod_app="+cod_app+"&cod_usr="+ cod_usr + "&mail="+ mail+"&iddispositivo="+iddispositivo + "&mac=" + mac + "&modelo=" + modeloEquipo;
            respuestaEn item        =funcion.ResultadoJson(url);
            return item;

        }

        protected void onPostExecute(respuestaEn result){
            pdEspera.dismiss();
            if (result.totalRegistro>1){
                funcion.messageBox(consContext,result.mensaje);
            }
            else{
                funcion.messageBox(consContext,result.mensaje);
                btnGenerarsolicitud.setText("Salir");
            }
        }
    }
}
