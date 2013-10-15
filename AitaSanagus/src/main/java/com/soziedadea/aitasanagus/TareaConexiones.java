package com.soziedadea.aitasanagus;

/**
 * Created by AlexIDS on 3/07/13.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class TareaConexiones extends AsyncTask <String, Float, String>{

    private Context mContext;

    public TareaConexiones(Context context) {
        mContext = context;
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
                HttpResponse response = httpclient.execute(new HttpGet(url[0]));
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

        protected void onPostExecute(String Resultado) {
            //dialog.dismiss()
           // t.setText(Resultado); getApplicationContext getApplicationContext()


            LayoutInflater inflater = LayoutInflater.from(mContext);
            RelativeLayout layoutToAdd;


            View view = inflater.inflate(R.layout.activity_main, null);
            TextView tv = (TextView) view.findViewById(R.id.ipini); //id defined in camera.xml
            layoutToAdd = (RelativeLayout) view.findViewById(R.id.Lay);
            //id defined in camera.xml
            //tv.setText("sisisi");
            String Prueba = tv.getText().toString();
            //layoutToAdd.addView(view);
            tv.setText("SISISISISIS");

           Prueba = tv.getText().toString();


            layoutToAdd.addView(view);

        }

}
