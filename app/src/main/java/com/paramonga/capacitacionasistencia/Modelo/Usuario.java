package com.paramonga.capacitacionasistencia.Modelo;

/**
 * Created by xcode on 5/1/16.
 */
public class Usuario {
    String nombre,email,flag_estado,cod_trabajador,origen_alt,funcion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFlag_estado() {
        return flag_estado;
    }

    public void setFlag_estado(String flag_estado) {
        this.flag_estado = flag_estado;
    }

    public String getCod_trabajador() {
        return cod_trabajador;
    }

    public void setCod_trabajador(String cod_trabajador) {
        this.cod_trabajador = cod_trabajador;
    }

    public String getOrigen_alt() {
        return origen_alt;
    }

    public void setOrigen_alt(String origen_alt) {
        this.origen_alt = origen_alt;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
}
