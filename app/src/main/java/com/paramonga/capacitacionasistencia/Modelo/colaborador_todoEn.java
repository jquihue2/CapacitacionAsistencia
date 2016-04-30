package com.paramonga.capacitacionasistencia.Modelo;

/**
 * Created by jquihue on 23/02/2016.
 */
public class colaborador_todoEn {

    public colaborador_todoEn() {
    }

    String letra, nro_item, nro_doc, nombre, cod_trabajador, fecha_inicio, fecha_fin, lugar, flag_asistencia;

    public colaborador_todoEn(String letra, String nro_item, String nro_doc, String nombre, String cod_trabajador, String fecha_inicio, String fecha_fin, String lugar, String flag_asistencia) {
        this.letra = letra;
        this.nro_item = nro_item;
        this.nro_doc = nro_doc;
        this.nombre = nombre;
        this.cod_trabajador = cod_trabajador;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.lugar = lugar;
        this.flag_asistencia =flag_asistencia;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getNro_item() {
        return nro_item;
    }

    public void setNro_item(String nro_item) {
        this.nro_item = nro_item;
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

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFlag_asistencia() {
        return flag_asistencia;
    }

    public void setFlag_asistencia(String flag_asistencia) {
        this.flag_asistencia = flag_asistencia;
    }
}
