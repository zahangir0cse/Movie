package com.example.zahangiralam.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.zahangiralam.movie.adapter.MovieAdapter;
import com.example.zahangiralam.movie.dao.DatabaseHelper;
import com.example.zahangiralam.movie.login.LoginActivity;
import com.example.zahangiralam.movie.model.Movie;
import com.example.zahangiralam.movie.session.UserSessionManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserSessionManager sessionManager;
    private MovieAdapter adapter;
    private List<Movie> movieList;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new UserSessionManager(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(this);
        DatabaseHelper helper = new DatabaseHelper(this);
        movieList = helper.getAllMovies();
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loginOrRegister:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.logout:
                sessionManager.logoutUser();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.goHome:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
