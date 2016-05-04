package com.paramonga.capacitacionasistencia.Datos;

import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;

import java.util.List;

//import ejemplofragmentdinamico.academiamoviles.com.ejemplofragmentdinamico.model.Usuarios;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
//http://www.agroparamonga.com/cap_asistencia/Afiliacion.jsp?funcion=2&cod_app=AP00000004&cod_usr=cnegre&iddispositivo=b06e1fa81f2fc185
/**
 * Created by JUAN JOSE LEDESMA on 30/04/2016.
 */
public interface afiliacionIn {

    @GET("/Afiliacion.jsp")
    void validarUsuario(@Query("funcion") String email,
                        @Query("cod_app") String password,
                        @Query("cod_usr") String cod_usr,
                        @Query("iddispositivo") String iddispositivo,
                        Callback<List<respuestaEn>> cb);

}
