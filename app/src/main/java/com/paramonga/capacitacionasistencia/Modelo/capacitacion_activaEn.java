package com.paramonga.capacitacionasistencia.Modelo;

/**
 * Created by jquihue on 16/02/2016.
 */
public class capacitacion_activaEn {
    String nro_programacion,desc_capacitacion,tipo_capacitacion,clase_curso,fecha_registro, tipo_capacitacion_cod;

    public capacitacion_activaEn() {
    }

    public capacitacion_activaEn(String nro_programacion, String desc_capacitacion, String tipo_capacitacion, String clase_curso, String fecha_registro, String tipo_capacitacion_cod) {
        this.nro_programacion   = nro_programacion;
        this.desc_capacitacion  = desc_capacitacion;
        this.tipo_capacitacion  = tipo_capacitacion;
        this.clase_curso        = clase_curso;
        this.fecha_registro     = fecha_registro;
        this.tipo_capacitacion_cod=tipo_capacitacion_cod;
    }

    public String getNro_programacion() {
        return nro_programacion;
    }

    public void setNro_programacion(String nro_programacion) {
        this.nro_programacion = nro_programacion;
    }

    public String getDesc_capacitacion() {
        return desc_capacitacion;
    }

    public void setDesc_capacitacion(String desc_capacitacion) {
        this.desc_capacitacion = desc_capacitacion;
    }

    public String getTipo_capacitacion() {
        return tipo_capacitacion;
    }

    public void setTipo_capacitacion(String tipo_capacitacion) {
        this.tipo_capacitacion = tipo_capacitacion;
    }

    public String getClase_curso() {
        return clase_curso;
    }

    public void setClase_curso(String clase_curso) {
        this.clase_curso = clase_curso;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getTipo_capacitacion_cod() {
        return tipo_capacitacion_cod;
    }

    public void setTipo_capacitacion_cod(String tipo_capacitacion_cod) {
        this.tipo_capacitacion_cod = tipo_capacitacion_cod;
    }
}
