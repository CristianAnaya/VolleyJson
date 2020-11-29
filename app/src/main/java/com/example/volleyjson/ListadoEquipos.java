package com.example.volleyjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;



public class ListadoEquipos extends AppCompatActivity {

    ArrayList<Competition> comp = new ArrayList<Competition>();
    ListView listado;
    SearchView searchView;
    EquipoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_equipos);
        Intent i = getIntent();
        comp = i.getParcelableArrayListExtra("equipos");
        listado = findViewById(R.id.lstcompetitions);
        searchView = findViewById(R.id.itemSearch);
        if (comp!=null && comp.size()>0){ adapter = new EquipoAdapter(this, comp);
           listado.setAdapter(adapter);
           adapter.notifyDataSetChanged();
           }
           else{
            Toast.makeText(this,"No hay datos" , Toast.LENGTH_LONG).show();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ListadoEquipos.this.adapter.getFilter().filter(s);

                return false;
            }
        });
    }
}
