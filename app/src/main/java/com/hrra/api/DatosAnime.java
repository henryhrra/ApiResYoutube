package com.hrra.api;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DatosAnime extends YouTubeBaseActivity {
    //YouTubePlayerView yt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_anime);
        //getSupportActionBar().hide();
        //getActionBar().hide();
        datos();
        //yt = findViewById(R.id.yt_player);

        /*YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("qAHMCZBwYo4");
                youTubePlayer.pause();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(),"Fallo yt",Toast.LENGTH_SHORT).show();
            }
        };
        yt.initialize("AIzaSyCzRpuvy6eb8P6jUqVW3asTnitsmkz06eM",listener);*/
    }
    public void datos(){
        //TextView name = findViewById(R.id.name);



        String url = getIntent().getStringExtra("url");
        Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        LinearLayout contenedor = findViewById(R.id.contendor);

        StringRequest pelicula = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject anime = json.getJSONObject("data");
                    //View infoAnime = getLayoutInflater().inflate(R.layout.datos_anime,null);

                    ImageView img = findViewById(R.id.img);
                    TextView txtNombre = findViewById(R.id.name);
                    TextView txtSinopsis = findViewById(R.id.sinopsis);
                    TextView txtNombreJ = findViewById(R.id.name_J);
                    TextView txtCapitulos = findViewById(R.id.episodios);
                    TextView txtEstado = findViewById(R.id.estado);
                    TextView txtEstreno = findViewById(R.id.estreno);
                    TextView txtClasificacion = findViewById(R.id.clasificacion);
                    Picasso.get()
                            .load(anime.getJSONObject("images").getJSONObject("webp").get("image_url").toString())
                            .error(R.mipmap.ic_launcher_round)
                            .into(img);
                    txtNombre.setText(anime.get("title").toString());
                    //txtNombre.setText("Kenichi");

                    txtSinopsis.setText(anime.get("synopsis").toString());
                    txtNombreJ.setText("Titulo en Japones: "+ anime.get("title_japanese").toString());
                    txtCapitulos.setText("Episodios: "+ anime.get("episodes").toString());
                    txtEstado.setText("Estado: "+ anime.get("status").toString());
                    txtEstreno.setText("Año de estreno: "+ anime.get("year").toString());
                    txtClasificacion.setText("Clasificación: "+ anime.get("rating").toString());

                    YouTubePlayerView yt = findViewById(R.id.yt_player);
                    String idYt = anime.getJSONObject("trailer").get("youtube_id").toString();
                    YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer.loadVideo(idYt);
                            youTubePlayer.play();
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            Toast.makeText(getApplicationContext(),"Fallo yt",Toast.LENGTH_SHORT).show();
                        }
                    };
                    yt.initialize("AIzaSyCzRpuvy6eb8P6jUqVW3asTnitsmkz06eM",listener);

                    //Codigo para la parte superior
                    /*LinearLayout head = new LinearLayout(DatosAnime.this);
                    head.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    head.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView img = new ImageView(DatosAnime.this);
                    img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,2));
                    Picasso.get()
                            .load(anime.getJSONObject("images").getJSONObject("webp").get("image_url").toString())
                            .error(R.mipmap.ic_launcher_round)
                            .into(img);
                    head.addView(img);

                    LinearLayout info = new LinearLayout(DatosAnime.this);
                    info.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView name = new TextView(DatosAnime.this);
                    name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,7));
                    name.setText(anime.get("title").toString());
                    name.setTextColor(Color.BLACK);
                    name.setTextSize(20);
                    name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    info.addView(name);

                    ScrollView scroll = new ScrollView(DatosAnime.this);

                    LinearLayout Scroll = new LinearLayout(DatosAnime.this);
                    Scroll.setOrientation(LinearLayout.VERTICAL);



                    TextView sinopsis = new TextView(DatosAnime.this);
                    sinopsis.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    sinopsis.setText(anime.get("synopsis").toString());
                    Scroll.addView(sinopsis);
                    scroll.addView(Scroll);
                    info.addView(scroll);
                    head.addView(info);
                    contenedor.addView(head);

                    LinearLayout datos = new LinearLayout(DatosAnime.this);
                    datos.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    datos.setBackgroundColor(Color.RED);
                    datos.setOrientation(LinearLayout.HORIZONTAL);

                    TextView tituloJ = new TextView(DatosAnime.this);
                    tituloJ.setText("Titulo en Japones: "+ anime.get("title_japanese").toString());
                    tituloJ.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    datos.addView(tituloJ);

                    TextView episode = new TextView(DatosAnime.this);
                    episode.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    episode.setText("Episodios: "+ anime.get("episodes").toString());
                    datos.addView(episode);

                    TextView estado = new TextView(DatosAnime.this);
                    estado.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    estado.setText("Estado: "+ anime.get("status").toString());
                    datos.addView(estado);
                    contenedor.addView(datos);

                    //Codigo trailer
                    LinearLayout body = new LinearLayout(DatosAnime.this);
                    body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    body.setBackgroundColor(Color.BLACK);
                    contenedor.addView(body);*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(pelicula);

    }
}