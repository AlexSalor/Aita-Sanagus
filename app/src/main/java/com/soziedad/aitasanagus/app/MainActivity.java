package com.soziedad.aitasanagus.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;


public class MainActivity extends ActionBarActivity {



    //Definiciones

    //FusionTables ft;
    TextView Resultado;
    Spinner lsvAndroidOS;
   // Gson gson ;
    Integer listViewPeopleId;
    Usuarios UsusrioActivo=null;
    Usuarios[] listaUsuarios;
    //Boton
    Button Aceptar;
    //Calendario
    CalendarView Calendario;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //MAPEAMOS LOS CONTROLES DE LA UI
        Resultado = new TextView(this);
       // Resultado=(TextView)findViewById(R.id.ipini);
        lsvAndroidOS = (Spinner)findViewById(R.id.lst);
        Aceptar = (Button) findViewById(R.id.button) ;
        Calendario = (CalendarView) findViewById( (R.id.calendarView));

        //OBTENEMOS LOS USUARIOS
        new ObtenerUsuarios().execute("");




        //EVENTOS************************

        Aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Recogemos datos y enviamos la información

                String Socio = lsvAndroidOS.getSelectedItem().toString();


                       new RealizarReserba().execute("");




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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    //TAREAS

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


    public class RealizarReserba extends AsyncTask <String, Float, String>{

        String URL, MiKey,Reserbas,Sentencia;
        String SQL;

        public RealizarReserba() {

            URL= "https://www.googleapis.com/fusiontables/v1/query?";
            MiKey="&key=AIzaSyDgF_jRMOGaXdpLEptoRa35zYqOEQU5lWo";
            Reserbas="1kbVkjkfdAL1O5VUDQX5DKJQSQ0jpb4YBCM9XwXs";
            SQL = "sql=INSERT%20INTO%20" + Reserbas + "%20(socioID,Date,comentario)%20VALUES%20(1,2014-01-01,'vamoss')";

            Sentencia= URL+SQL+MiKey;

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
                HttpPost httppost = new HttpPost(Sentencia);
                HttpResponse  response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                if(entity != null){
                    return EntityUtils.toString(entity);
                }
                else{
                    return "No string.";
                }

               /* HttpResponse response = httpclient.execute(new HttpPost(Sentencia));
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
                }*/
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
        try{
                JSONObject jsonResponse = new JSONObject(ResultadoQ);

        } catch (Exception e){
            e.printStackTrace();
        }
        }

    }



    public class Usuarios
    {
        String ID;
        String Nombre;

    }


}
