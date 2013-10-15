package com.soziedadea.aitasanagus;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.google.gson.*;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    FusionTables ft;
    TextView Resultado;
    ListView lsvAndroidOS;
    Gson gson ;
    Integer listViewPeopleId;
    Usuarios UsusrioActivo=null;
    Usuarios[] listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resultado = new TextView(this);
        Resultado=(TextView)findViewById(R.id.ipini);
        lsvAndroidOS = (ListView)findViewById(R.id.lst);
        new ObtenerUsuarios().execute("");

        lsvAndroidOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView customerAdapter, View footer, int selectedInt, long selectedLong) {

                Integer i;
                for (i=0; i<= listaUsuarios.length;i++){
                    if (listaUsuarios[i].Nombre.equals((lsvAndroidOS.getItemAtPosition(selectedInt)))){
                       UsusrioActivo= listaUsuarios[i];
                    }
                }

            }
        });



    }

    private void refreshMoviesList(String[] ListaUsuarios)
    {

       lsvAndroidOS.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListaUsuarios));


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void obtenerUsuario(){

    }



public class ObtenerUsuarios extends AsyncTask <String, Float, String>{

    String URL, MiKey,Usuarios,Sentencia;
    String SQL;

    public ObtenerUsuarios() {

        URL= "https://www.googleapis.com/fusiontables/v1/query?";
        MiKey="&key=AIzaSyDgF_jRMOGaXdpLEptoRa35zYqOEQU5lWo";
        Usuarios="1QRK9IVsg26Xs1GM8aE2S73UwrTeBRKnG2BqQEsk";
        SQL = "sql=SELECT%20*%20FROM%20";

        Sentencia= URL+SQL+Usuarios+MiKey;

    }
    protected void onPreExecute() {
        //dialog.setProgress(0);
        //dialog.setMax(100);
        //dialog.show(); //Mostramos el diálogo antes de comenzar
    }

    protected String doInBackground(String... url) {

        String responseString="";

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(Sentencia));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
                //..more logic
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return responseString;
    }

    protected void onProgressUpdate (Float... valores) {
        //int p = Math.round(100*valores[0]);
        //diStrialog.setProgress(p);
    }
    protected void onPostExecute(String ResultadoQ) {

        //Resultado.setText(ResultadoQ);

        try
        {

            JSONObject jsonResponse = new JSONObject(ResultadoQ);
            JSONArray usuarios = jsonResponse.getJSONArray("rows");

            listaUsuarios = new Usuarios[usuarios.length()];
            String[] listaNombres = new String[usuarios.length()];

            for (int i = 0; i < usuarios.length(); i++)
            {

                JSONArray usuarios2 =usuarios.getJSONArray(i);

                Usuarios usu = new Usuarios();
                usu.ID=usuarios2.getString(0);
                usu.Nombre=usuarios2.getString(1);

                listaUsuarios[i] =usu;
                listaNombres[i]=usuarios2.getString(1);

            }

            // update the UI
           refreshMoviesList(listaNombres);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();

        }

    }

}
    public class Usuarios
    {
        String ID;
        String Nombre;

    }
}
