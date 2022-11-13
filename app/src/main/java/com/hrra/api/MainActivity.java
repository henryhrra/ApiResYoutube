package com.hrra.api;

import static com.hrra.api.R.drawable.fondo_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarpelicula();
    }
    public void cargarpelicula(){
        //String api = "https://swapi.dev/api/films/";
        //String api = "https://api.jikan.moe/v4/anime";

        StringRequest Resulatdo = new StringRequest(Request.Method.GET, "https://api.jikan.moe/v4/anime", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);

                    //Log.d("pelicula", json.getString("title"));

                    JSONArray anime = json.getJSONArray("data");
                    LinearLayout contenedor = findViewById(R.id.Contenedor);

                    for (int i = 0; i < anime.length(); i++) {

                        JSONObject item = anime.getJSONObject(i);
                        //Log.d("pelicula", item.get("title").toString());
                        final CardView itemA = (CardView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, null);
                        TextView nombre = itemA.findViewById(R.id.nombreAnime);
                        nombre.setText(item.get("title").toString());
                        ImageView img = itemA.findViewById(R.id.imagen);
                        //nombre.setTag(item.get("mal_id").toString());
                        //id = item.get("mal_id").toString();
                        /*JSONObject images = item.getJSONObject("images");
                        JSONObject jpg = images.getJSONObject("jpg");
                        Picasso.get()
                                .load(jpg.get("image_url").toString())
                                        .error(R.mipmap.ic_launcher_round)
                                                .into(img);*/
                        Picasso.get()
                                .load(item.getJSONObject("images").getJSONObject("jpg").get("image_url").toString())
                                .error(R.mipmap.ic_launcher_round)
                                .into(img);
                        //tv.setTag(item.get("url").toString());
                       // int finalI = i+1;
                        //String id = item.get("mal_id").toString();
                       /* itemA.setOnClickListener(view -> {
                            //TextView t = (TextView) view;
                            //Log.d("pelicula", view.getTag().toString());
                            Intent e = new Intent(MainActivity.this, DatosAnime.class);
                            e.putExtra("url","https://api.jikan.moe/v4/anime/"+ item.get("mal_id").toString());
                            startActivity(e);
                        });*/
                        String id = item.get("mal_id").toString();
                        itemA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //Toast.makeText(MainActivity.this,"https://api.jikan.moe/v4/anime/"+ id,Toast.LENGTH_SHORT).show();
                                Intent e = new Intent(MainActivity.this, DatosAnime.class);
                                e.putExtra("url","https://api.jikan.moe/v4/anime/"+ id);
                                startActivity(e);
                            }
                        });
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        layoutParams.setMargins(5, 10, 5, 10);
                        itemA.setLayoutParams(layoutParams);
                        TextView epidosios = itemA.findViewById(R.id.numEpisodios);
                        epidosios.setText(item.get("episodes").toString());
                        TextView estado = itemA.findViewById(R.id.estado);
                        estado.setText(item.get("status").toString());
                        itemA.getBackground().setAlpha(120);
                        contenedor.addView(itemA);
                        contenedor.setBackgroundColor(Color.TRANSPARENT);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(this,"Error con la api",Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(Resulatdo);
    }
}