package com.paramonga.capacitacionasistencia.Modelo;

/**
 * Created by jquihue on 17/02/2016.
 */
public class sesionEn {

    public sesionEn(){
        super();
    }
    String nro_item, fecha_inicio, fecha_fin, descripcion;

    public sesionEn(String nro_item, String fecha_inicio, String fecha_fin, String descripcion) {
        this.nro_item = nro_item;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.descripcion = descripcion;
    }

    public String getNro_item() {
        return nro_item;
    }

    public void setNro_item(String nro_item) {
        this.nro_item = nro_item;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
