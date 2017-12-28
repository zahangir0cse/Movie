package com.example.zahangiralam.movie.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.zahangiralam.movie.MainActivity;
import com.example.zahangiralam.movie.dao.DatabaseHelper;
import com.example.zahangiralam.movie.login.LoginActivity;
import com.example.zahangiralam.movie.model.User;

import java.util.HashMap;

/**
 * Created by Zahangir Alam on 2017-12-25.
 */

public class UserSessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "AndroidExamplePref";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

    public UserSessionManager(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createUserLoginSession(String name, String email){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public boolean isUserLogin(){
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    public boolean checkLogin(){
        if (!this.isUserLogin()){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return true;

        }
        return false;
    }

    public User getSessionUser(Context context){
        DatabaseHelper helper = new DatabaseHelper(context);
        User user = helper.getUserByEmail(sharedPreferences.getString(KEY_EMAIL, null));
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

}

