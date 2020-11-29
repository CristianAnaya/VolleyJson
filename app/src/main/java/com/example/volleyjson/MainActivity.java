package com.example.volleyjson;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button conectar, listar;
    TextView dato;
    String url = "https://www.datos.gov.co/resource/jj37-fvz6.json";


    ArrayList<Competition> cs = new ArrayList<Competition>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         conectar = findViewById(R.id.tbnconectar);
         dato = findViewById(R.id.txtdatos);
         listar = findViewById(R.id.btnlistado);

        
                 requestDatos();
           

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListadoEquipos.class);
                i.putParcelableArrayListExtra("equipos", cs);
                startActivity(i);
            }
        });
    }

    public void requestDatos(){
        RequestQueue cola = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                      //dato.setText(response.toString());
                      parserJson(response);
                        Toast.makeText(MainActivity.this, "Conectado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en la conexion", Toast.LENGTH_LONG).show();
            }
            })
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
               // headers.put("Content-Type", "application/json");
                headers.put("X-App-Token", "0dEsjuoFdSw1zO2cCxJrZAfTH");
                return headers;
            }
        };
        cola.add(jsonObjectRequest);
    }

        public void parserJson(JSONArray response){
        try {
            String cadena = "";
            for (int i = 0 ; i<response.length(); i++) {
                JSONObject com = new JSONObject(response.get(i).toString());
                String id = com.getString("nombresitio");
                String nomcomp = com.getString("tipo");
                String nomarea = com.getString("descripcion");
                String municpio = com.getString("nombremunicipio");
                cadena = cadena + id + "," + nomcomp + "," + nomarea + "\n";
                Competition co = new Competition(id,nomcomp,nomarea,municpio);
                cs.add(co);
            }
          //Toast.makeText(getApplicationContext(),"Id = "+ cs.get(1).getId(), Toast.LENGTH_LONG).show();
            dato.setText(cadena);
        } catch (JSONException e) {
           Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
