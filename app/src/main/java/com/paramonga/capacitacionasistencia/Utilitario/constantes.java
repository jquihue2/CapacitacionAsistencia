package com.paramonga.capacitacionasistencia.Utilitario;



import java.util.List;

/**
 * Created by jquihue on 12/08/2015.
 */
final public class constantes {
    //private constructor to prevent instantiation/inheritance
    private constantes()
    {
    }

    public static final String IP_CAPC ="http://www.agroparamonga.com/cap_asistencia/";// "http://10.100.16.115:8080/cap_asistencia/";//  "http://10.100.17.11:8080/cap_asistencia/";

    public static final String COD_APP = "AP00000004";

/*    public static boolean tieneAcceso(List<accesoEn> accesos,String objecto){
        for (accesoEn fila: accesos){
            if(fila.getObjecto().equals(objecto) && fila.getFlag_estado().equals("1"))
            {
                return true;
            }
        }
        return false;
    }*/

}
