package com.example.zahangiralam.movie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.zahangiralam.movie.dao.DatabaseHelper;
import com.example.zahangiralam.movie.exceptions.ImageDownloadException;
import com.example.zahangiralam.movie.login.LoginActivity;
import com.example.zahangiralam.movie.model.Movie;
import com.example.zahangiralam.movie.session.UserSessionManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieActivity extends AppCompatActivity {

    private EditText movieNameET, movieUrlET, movieRattingET, movieDescriptionET, movieGenreET;
    private Button saveMovieButton;
    private UserSessionManager sessionManager;
    private ProgressDialog mProgressDialog;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        sessionManager = new UserSessionManager(getApplicationContext());
        movieNameET = findViewById(R.id.movieNameText);
        movieDescriptionET = findViewById(R.id.movieDescriptionText);
        movieGenreET = findViewById(R.id.movieGenreText);
        movieRattingET = findViewById(R.id.movieRattingText);
        movieUrlET = findViewById(R.id.movieUrlText);
        saveMovieButton = findViewById(R.id.movieSaveButton);
        image = findViewById(R.id.image);

        saveMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = movieNameET.getText().toString();
                String mUrl = movieUrlET.getText().toString();
                String mRatting = movieRattingET.getText().toString();
                String mDescription = movieDescriptionET.getText().toString();
                String mGenre = movieGenreET.getText().toString();
                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                if (mName.trim().length() > 0 && mUrl.trim().length() > 0 && mDescription.trim().length() > 0 && mGenre.trim().length() > 0 && mRatting.trim().length() > 0) {
                    new DownloadImage().execute(mUrl);
                    image.buildDrawingCache();
                    Bitmap bitmap = image.getDrawingCache();
                    Movie movie = new Movie();
                    movie.setMovieName(mName);
                    movie.setMovieDescription(mDescription);
                    movie.setMovieImage(bitmap);
                    movie.setMovieGenre(mGenre);
                    movie.setMovieRatting(Double.valueOf(mRatting));
                    //movie.setUser(sessionManager.getSessionUser(getApplicationContext()));
                    helper.saveMovie(movie);
                    startActivity(new Intent(MovieActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "User name or password must be greater than 1 character", Toast.LENGTH_LONG).show();
                }

            }
        });
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
                Intent intent = new Intent(this, MainActivity.class);
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

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MovieActivity.this);
            mProgressDialog.setTitle("Download Image");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL){
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                InputStream input = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (MalformedURLException e) {
                try {
                    throw new ImageDownloadException("url not correct");
                } catch (ImageDownloadException e1) {

                }
            } catch (IOException e) {
                try {
                    throw new ImageDownloadException("connection failed");
                } catch (ImageDownloadException e1) {

                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mProgressDialog.dismiss();
            image.setImageBitmap(result);

        }
    }

}
