package com.paramonga.capacitacionasistencia.Datos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;

import java.util.List;

//import ejemplofragmentdinamico.academiamoviles.com.ejemplofragmentdinamico.model.Usuarios;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

//http://www.agroparamonga.com/cap_asistencia/Afiliacion.jsp?funcion=2&cod_app=AP00000004&cod_usr=cnegre&iddispositivo=b06e1fa81f2fc185
/**
 * Created by JUAN JOSE LEDESMA on 30/04/2016.
 */
public class RestLoginService {

    String url = "http://www.agroparamonga.com/cap_asistencia";
    Context context;

    public RestLoginService(Context context) {
        this.context = context;
    }

    public void validarUsuario(String funcion,String cod_App,String cod_usr, String iddispositivo) {



        RestAdapter adapter = new RestAdapter.Builder().
                setEndpoint(url).
                build();


        //Creating Rest Services
        UsuarioRepositoryService restInterface = adapter.create(UsuarioRepositoryService.class);

        restInterface.validarUsuario(funcion, cod_App,cod_usr,iddispositivo,  new Callback<List<respuestaEn>>() {


            @Override
            public void success(List<respuestaEn> usuarios, Response response) {
                Log.e("OK", "OK");
                // Log.d("Success 1", usuarios.get(0).getId_usurio());
                // Log.d("Success 2", usuarios.get(0).getEstado().toString());
                Log.e("len", ""+usuarios.size());
                //Log.e("Success 1", usuarios.get(0).getData().toString());
                Log.e("Success 2", (String.valueOf( usuarios.get(0).getEstado())));
                Log.e("Success 3", usuarios.get(0).getMensaje());

                /*if(usuarios.get(0).getEstado().equals("1")){
                    Toast.makeText(context,"Validado",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"Incorrecto",Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void failure(RetrofitError error) {
                String merror = error.getMessage();
                Log.e("logerror",merror);
            }
        });
    }
}
