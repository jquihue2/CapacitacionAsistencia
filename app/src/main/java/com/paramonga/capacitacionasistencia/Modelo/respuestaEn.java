package com.paramonga.capacitacionasistencia.Modelo;

import java.util.List;

/**
 * Created by jquihue on 11/08/2015.
 */
public class respuestaEn {

    int estado;
    String mensaje;
    List<Usuario> data;
    int totalRegistro;

    public respuestaEn() {
        super();
    }


    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Usuario> getData() {
        return data;
    }

    public void setData(List<Usuario> data) {
        this.data = data;
    }

    public int getTotalRegistro() {
        return totalRegistro;
    }

    public void setTotalRegistro(int totalRegistro) {
        this.totalRegistro = totalRegistro;
    }

}
