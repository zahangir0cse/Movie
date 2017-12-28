package com.example.zahangiralam.movie.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.zahangiralam.movie.model.Movie;
import com.example.zahangiralam.movie.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zahangir Alam on 2017-12-27.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "movie_info";
    //  user
    private static final String USER_TABLE_NAME = "user";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_CONTACT_NO = "user_contact_no";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_USER_ROLE = "user_role";
    //  movie
    private static final String MOVIE_TABLE_NAME = "movie";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String KEY_MOVIE_RATTING = "movie_ratting";
    private static final String KEY_MOVIE_IMAGE = "movie_image_url";
    private static final String KEY_MOVIE_GENRE = "movie_genre";
    private static final String KEY_MOVIE_DESCRIPTION = "movie_description";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createUserTable = "create table " + USER_TABLE_NAME + " (" + KEY_USER_ID + " integer primary key, " + KEY_USER_NAME + " text, " + KEY_USER_EMAIL + " text, " + KEY_USER_CONTACT_NO + " text, " + KEY_USER_PASSWORD + " text, " + KEY_USER_ROLE + " text);";
        //String createMovieTable = "create table " + MOVIE_TABLE_NAME + " (" + KEY_MOVIE_ID + " integer primary key, " + KEY_MOVIE_NAME + " text, " + KEY_MOVIE_RATTING + " text, " + KEY_MOVIE_IMAGE + " blob, " + KEY_USER_ID + " integer, " + KEY_MOVIE_GENRE + " text, " + KEY_MOVIE_DESCRIPTION + " text, FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + USER_TABLE_NAME + "(" + KEY_USER_ID + "));";
        String createMovieTable = "create table " + MOVIE_TABLE_NAME + " (" + KEY_MOVIE_ID + " integer primary key, " + KEY_MOVIE_NAME + " text, " + KEY_MOVIE_RATTING + " text, " + KEY_MOVIE_IMAGE + " blob not null, " + KEY_MOVIE_GENRE + " text, " + KEY_MOVIE_DESCRIPTION + " text);";
        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createMovieTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // User CRUD
    public void saveUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(USER_TABLE_NAME, null, setUserData(user));
        sqLiteDatabase.close();

    }

    public void updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.update(USER_TABLE_NAME, setUserData(user), KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getUserId())});
        sqLiteDatabase.close();
    }

    public List<User> getAllUsers() {
        String sql = "select * from " + USER_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return getUserData(cursor);
    }

    public void deleteUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(USER_TABLE_NAME, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getUserId())});
        sqLiteDatabase.close();
    }

    public User getUserById(int id) {
        String sql = "select * from " + USER_TABLE_NAME + " where " + KEY_USER_ID + " = " + id;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return getUserData(cursor).get(0);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "select * from " + USER_TABLE_NAME + " where " + KEY_USER_EMAIL + " = '" + email + "' and " + KEY_USER_PASSWORD + " = '" + password + "'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return getUserData(cursor).get(0);
    }

    public User getUserByEmail(String email) {
        String sql = "select * from " + USER_TABLE_NAME + " where " + KEY_USER_EMAIL + " = '" + email + "'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return getUserData(cursor).get(0);
    }

    // Movie CRUD
    public void saveMovie(Movie movie) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(MOVIE_TABLE_NAME, null, setMovieContentValues(movie));
        sqLiteDatabase.close();
    }

    public void updateMovie(Movie movie) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(MOVIE_TABLE_NAME, setMovieContentValues(movie), KEY_MOVIE_ID + " = ?", new String[]{String.valueOf(movie.getMovieId())});
        sqLiteDatabase.close();
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        String sql = "select * from " + MOVIE_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return getMovieData(cursor, movieList);
    }

    public void deleteMovie(Movie movie) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(MOVIE_TABLE_NAME, KEY_MOVIE_ID + " = ?", new String[]{String.valueOf(movie.getMovieId())});
        sqLiteDatabase.close();
    }

    private List<Movie> getMovieData(Cursor cursor, List<Movie> movieList){
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setMovieId(cursor.getInt(0));
                movie.setMovieName(cursor.getString(1));
                movie.setMovieRatting(cursor.getDouble(2));
                movie.setMovieImage(BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length));
                //movie.setUser(getUserById(1));
                //movie.setUser(getUserById(cursor.getInt(4)));
                movie.setMovieGenre(cursor.getString(4));
                movie.setMovieDescription(cursor.getString(5));
                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        return movieList;
    }

    private ContentValues setMovieContentValues(Movie movie){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        movie.getMovieImage().compress(Bitmap.CompressFormat.PNG, 100, out);
        ContentValues values = new ContentValues();
        byte[] buffer = out.toByteArray();
        values.put(KEY_MOVIE_NAME, movie.getMovieName());
        values.put(KEY_MOVIE_IMAGE, buffer);
        values.put(KEY_MOVIE_RATTING, movie.getMovieRatting());
        //values.put(KEY_USER_ID, movie.getUser().getUserId());
        values.put(KEY_MOVIE_DESCRIPTION, movie.getMovieDescription());
        values.put(KEY_MOVIE_GENRE, movie.getMovieGenre());
        return values;
    }

    private List<User> getUserData(Cursor cursor){
        List<User> userList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(Integer.parseInt(cursor.getString(0)));
                user.setUserName(cursor.getString(1));
                user.setUserEmail(cursor.getString(2));
                user.setUserPhoneNumber(cursor.getString(3));
                user.setUserPassword(cursor.getString(4));
                user.setUserRole(cursor.getString(5));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }

    private ContentValues setUserData(User user){
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUserName());
        values.put(KEY_USER_CONTACT_NO, user.getUserPhoneNumber());
        values.put(KEY_USER_PASSWORD, user.getUserPassword());
        values.put(KEY_USER_EMAIL, user.getUserEmail());
        values.put(KEY_USER_ROLE, "user");
        return values;
    }

}
