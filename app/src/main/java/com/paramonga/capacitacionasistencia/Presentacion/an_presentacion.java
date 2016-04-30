package com.paramonga.capacitacionasistencia.Presentacion;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.paramonga.capacitacionasistencia.R;


public class an_presentacion extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ln_presentacion);
        android.os.Handler handler= new android.os.Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                Intent i = new Intent(an_presentacion.this ,an_login.class);
                startActivity(i);
                finish();
            }
        },2500);//2500

    }

}
