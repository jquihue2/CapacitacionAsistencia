package com.paramonga.capacitacionasistencia.Modelo;

/**
 * Created by jquihue on 17/02/2016.
 */
public class colaboradorEn {
    public colaboradorEn() {
    }

    String letra, nro_doc, nombre, cod_trabajador, flag_asistencia;

    public colaboradorEn(String letra, String nro_doc, String nombre, String cod_trabajador, String flag_asistencia) {
        this.letra = letra;
        this.nro_doc = nro_doc;
        this.nombre = nombre;
        this.cod_trabajador = cod_trabajador;
        this.flag_asistencia    = flag_asistencia;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getNro_doc() {
        return nro_doc;
    }

    public void setNro_doc(String nro_doc) {
        this.nro_doc = nro_doc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCod_trabajador() {
        return cod_trabajador;
    }

    public void setCod_trabajador(String cod_trabajador) {
        this.cod_trabajador = cod_trabajador;
    }

    public String getFlag_asistencia() {
        return flag_asistencia;
    }

    public void setFlag_asistencia(String flag_asistencia) {
        this.flag_asistencia = flag_asistencia;
    }
}
