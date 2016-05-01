package com.paramonga.capacitacionasistencia.Utilitario;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.DatePicker;

import com.paramonga.capacitacionasistencia.Modelo.campo3En;
import com.paramonga.capacitacionasistencia.Modelo.respuestaEn;
import com.paramonga.capacitacionasistencia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jquihue on 23/06/2015.
 */
public class funciones {
//Posibles valores de la propiedad estado desde de la WS
// 0=sin data, 1= data , -1=error, 2= Transacción exitosa (insert, update, delete)
// 3= error de conexion
    public boolean validarCorreo(CharSequence correo)
    {
        return !TextUtils.isEmpty(correo)&& Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    public boolean conectado(Context context)
    {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return ( activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public String formatoNumeroDoc(String numeroDoc)
    {
        NumberFormat nf;
        nf=new DecimalFormat("00000000");
        //PR5678
        String cod_origen = numeroDoc.substring(0,2).toUpperCase();
        Log.e("cod_origen",cod_origen);

        Log.e("numeroDoc leng",String.valueOf( numeroDoc.length()));
        String numero=numeroDoc.substring(2,numeroDoc.length());
        Log.e("numero",numero);

        return cod_origen + nf.format( Integer.parseInt(numero));
    }

    public String formatoNumero2digitos(double numero){
        NumberFormat nf;
        nf=new DecimalFormat("###.00");

        return nf.format(numero);
    }

    public void messageBox(Context constext,String titulo, String mensaje)
    {
        AlertDialog.Builder msg=new AlertDialog.Builder(constext);
        msg.setCancelable(false);
        if(!titulo.isEmpty()){
            msg.setTitle(titulo);
            msg.setIcon(R.drawable.ic_launcher);
        }
        msg.setMessage(mensaje);
        msg.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            //
            }
        });
        msg.show();

    }
    public void messageBox(Context constext, String mensaje)
    {
        AlertDialog.Builder msg=new AlertDialog.Builder(constext);
        msg.setCancelable(false);
        msg.setMessage(mensaje);
        msg.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        msg.show();
    }

    public respuestaEn ResultadoJson(String url) {
        respuestaEn respuesta=new respuestaEn();
        String jsonString = null;
        HttpURLConnection linkConnection = null;
        try {
            URL linkurl = new URL(url.replace(" ","%20").replace("'","%27").replace("(","%28").replace(")","%29"));
            Log.e("linkurl getPath",url.replace(" ","%20").replace("'","%27").replace("(","%28").replace(")","%29"));
            linkConnection = (HttpURLConnection) linkurl.openConnection();
            int responseCode = linkConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream linkinStream = linkConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream(200000);
                int j = 0;
                byte[] buffer = new byte[200000];//buffer
                while ((j = linkinStream.read(buffer)) != -1) {
                    baos.write(buffer,0,j);
                }
                byte[] data = baos.toByteArray();
                jsonString = new String(data);
            }
            Log.e("repuesta json ", jsonString);
            //obtener los valores
            JSONArray jsonResponse = new JSONArray(jsonString);
            JSONObject json_data= jsonResponse.getJSONObject(0);
            /*Log.e("estado",json_data.getString("estado"));
            Log.e("mensaje",json_data.getString("mensaje"));
            Log.e("data",json_data.getJSONArray("data").toString());
            Log.e("totalRegistro",String.valueOf(json_data.getInt("totalRegistro")));*/

            respuesta.estado=json_data.getInt("estado");
            respuesta.mensaje=json_data.getString("mensaje");
            //respuesta.data=json_data.getJSONArray("data").toString();
            respuesta.totalRegistro=json_data.getInt("totalRegistro");
        }
        catch (JSONException e)
        {
            respuesta.estado=3;
            respuesta.mensaje="Error de formato.";
            //respuesta.data="";
            respuesta.totalRegistro=0;
        }
        catch (java.net.MalformedURLException e) {
            respuesta.estado=3;
            respuesta.mensaje="Error de conexión al servidor; vuelva a intentarlo en un momento.";
            //respuesta.data="";
            respuesta.totalRegistro=0;
        }
        catch(Exception e){
            respuesta.estado=3;
            respuesta.mensaje="Error general de conexión.";
            //respuesta.data="";
            respuesta.totalRegistro=0;
        }
        finally {
            if (linkConnection != null) {
                linkConnection.disconnect();
            }
        }
        return respuesta;
    }




    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }


    public List<campo3En> campo3Lista(String dataJson){
        List<campo3En> respuesta=new ArrayList<>();
        try {
            JSONArray data = new JSONArray(dataJson);
            //listaAcceso.clear();
            campo3En item;
            for(int i =0 ; i < data.length(); i++){
                JSONObject fila=data.getJSONObject(i);
                item=new campo3En();
                item.setCampo1(fila.getString("CAMPO1"));
                item.setCampo2(fila.getString("CAMPO2"));
                item.setCampo3(fila.getString("CAMPO3"));
                respuesta.add(item);
            }
        }
        catch(JSONException e){
            Log.e("Errror JSONException",e.getMessage());
        }
        return respuesta;
    }

    private int mYear, mMonth, mDay, mHour, mMinute;
    public String fechaActual()
    {
        String fecha="";

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        fecha=String.format("%02d",mDay) + "/" + String.format("%02d",mMonth) + "/" + String.format("%02d",mYear);
        return fecha;
    }
    public String horaActual()
    {
        String hora="";

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute= c.get(Calendar.MINUTE);
        hora= String.format("%02d",mHour) + ":" + String.format("%02d",mMinute);
        return hora;
    }
    public String fechahoraActual()
    {
        String fecha="";

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute= c.get(Calendar.MINUTE);
        fecha=String.format("%02d",mDay) + "/" + String.format("%02d",mMonth) + "/" + String.format("%02d",mYear) + " " + String.format("%02d",mHour) + ":" + String.format("%02d",mMinute);
        return fecha;
    }

/*    public String obtenerFechaHora(Context context){
        Dialog dialog= new Dialog(context);
        dialog.setContentView(R.layout.lc_fechahora);
        dialog.setTitle("Fecha hora");

        dialog.show();
        return "";
    }*/

    public String fecha(Context context){
        final String[] fechaSelec = {""};
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        fechaSelec[0] = (dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
        return fechaSelec[0];
    }

    public String nombreTerminal()
    {
        String nombre="";
        String model = android.os.Build.MODEL;
        String marca = android.os.Build.MANUFACTURER;
        nombre= model + "-" + marca;
        if(nombre.length()>30){
            nombre=nombre.substring(0,30);
        }
        return nombre;
    }

    public String obtenerMacAdress(Context context){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo= wifiManager.getConnectionInfo();
        String mac = wifiInfo.getMacAddress();

        if(mac==null){
            mac="sin mac";
        }
        return mac;
    }

    /**
     * Obtiene ruta, nombre y extension del archivo seleccionado, al momento de importar archivo generado por titanium;
     * @return
     */
    public String dialogoNombreArchivo()
    {

        return "";
    }

    /**
     * Establece la ubicación del direcctorio donde se guardará el archivo generado
     * @return
     */

    public String dialogoUbicacionArchivo(){

        return "";
    }

}
