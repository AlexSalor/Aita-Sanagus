package com.soziedadea.aitasanagus;

/**
 * Created by AlexIDS on 2/07/13.
 */

import android.content.Context;



public class FusionTables {

    String URL, MiKey,Usuarios;
    private Context mContext;


      FusionTables (Context mmContext)
    {
     URL= "https://www.googleapis.com/fusiontables/v1/query?";
     MiKey="&key=AIzaSyDgF_jRMOGaXdpLEptoRa35zYqOEQU5lWo";
     Usuarios="1QRK9IVsg26Xs1GM8aE2S73UwrTeBRKnG2BqQEsk";
     mContext=mmContext;
    }

    public  String GetUsers(){
        String SQL = "sql=SELECT%20*%20FROM%20";
        String Sentencia="";

        // Se completa la sentencia

        Sentencia= URL+SQL+Usuarios+MiKey;

       new TareaConexiones(mContext).execute(Sentencia);

       return(Sentencia);

    }

}
