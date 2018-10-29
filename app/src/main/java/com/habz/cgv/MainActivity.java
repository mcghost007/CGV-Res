package com.habz.cgv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "59e5f92ab5b6ee7f326e70e80da06df7";

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.setting){
            startActivity(new Intent(this, LanguageActivity.class));

        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Interface apiService =
                Api.getClient().create(Interface.class);

        Call<ModelResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse>call, Response<ModelResponse> response) {
                final List<ModelMovie> modelMovies = response.body().getResults();
                Log.d(TAG, "Number of modelMovies received: " + modelMovies.size());
                Toast.makeText(MainActivity.this, "Number of modelMovies received: " + modelMovies.size(), Toast.LENGTH_LONG).show();
                recyclerView.setAdapter(new LinkMovie(modelMovies, R.layout.list_movie, getApplicationContext()));
                /*perintah klik recyclerview*/
                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                        public boolean onSingleTapUp(MotionEvent e){
                            return true;
                        }
                    });

                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        View child = rv.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && gestureDetector.onTouchEvent(e)){
                            int position = rv.getChildAdapterPosition(child);
                            /*Intent i = new Intent(getApplicationContext(), MovieActivity.class);
                            i.putExtra("id", modelMovies.get(position).getId());
                            getApplicationContext().startActivity(i);*/
                            Toast.makeText(getApplicationContext(), "Id : " + modelMovies.get(position).getId() + " selected", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(MainActivity.this, MovieActivity.class);
                            i.putExtra("title", modelMovies.get(position).getTitle());
                            i.putExtra("date", modelMovies.get(position).getReleaseDate());
                            i.putExtra("vote", modelMovies.get(position).getVoteAverage().toString());
                            i.putExtra("overview", modelMovies.get(position).getOverview());
                            i.putExtra("bg", modelMovies.get(position).getPosterPath());
                            MainActivity.this.startActivity(i);


                        }
                        return false;
                    }

                    @Override
                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ModelResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
