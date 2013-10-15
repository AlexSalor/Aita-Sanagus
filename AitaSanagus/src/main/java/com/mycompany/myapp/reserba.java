package com.mycompany.myapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.soziedadea.aitasanagus.R;

import java.util.LinkedList;

public class reserba extends Activity {


    Spinner miLista;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserba_layout);
        miLista=(Spinner)findViewById(R.id.listaSpinner);
        //Creamos la lista
        LinkedList<Socios> listaSocios = new LinkedList<Socios>();
        //La poblamos con los ejemplos
        listaSocios.add(new Socios(1, "Salchichas"));
        listaSocios.add(new Socios(2, "Huevos"));
        listaSocios.add(new Socios(3, "Tomate"));
        //Creamos el adaptador
        ArrayAdapter<Socios> spinner_adapter = new ArrayAdapter<Socios>(this, android.R.layout.simple_spinner_item,listaSocios);
       //Añadimos el layout para el menú y se lo damos al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        miLista.setAdapter(spinner_adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reserba, menu);
        return true;
    }
    
}

 class Socios{
    int id;
    String nombre;
    //Constructor
    public Socios(int id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return nombre;
    }
    public int getId() {
        return id;
    }
}

 class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
     Integer idr;
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        if (parent.getId() == R.id.listaSpinner) {
            idr = ((Socios) parent.getItemAtPosition(pos)).getId();
        }
        //Podemos hacer varios ifs o un switchs por si tenemos varios spinners.
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing.
    }
}