package com.example.zahangiralam.movie.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zahangiralam.movie.MainActivity;
import com.example.zahangiralam.movie.MovieActivity;
import com.example.zahangiralam.movie.R;
import com.example.zahangiralam.movie.dao.DatabaseHelper;
import com.example.zahangiralam.movie.model.User;
import com.example.zahangiralam.movie.registration.RegisterActivity;
import com.example.zahangiralam.movie.session.UserSessionManager;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton, registerButton;
    private UserSessionManager sessionManager;
    private EditText textUserName, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new UserSessionManager(getApplicationContext());

        loginButton = findViewById(R.id.loginButton);
        textUserName = findViewById(R.id.emailLoginText);
        textPassword = findViewById(R.id.passwordLoginText);
        registerButton = findViewById(R.id.registerButton);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = textUserName.getText().toString();
                String password = textPassword.getText().toString();
                if (userName.trim().length()>0 && password.trim().length()>0){
                    DatabaseHelper helper = new DatabaseHelper(LoginActivity.this);
                    User user = helper.getUserByEmailAndPassword(userName, password);
                    if (user != null){
                        sessionManager.createUserLoginSession("session", user.getUserEmail());
                        if (user.getUserEmail().equals("zahangir@gmail.com")){
                            startActivity(new Intent(LoginActivity.this, MovieActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "User name or password incorrect", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "User name or password must be greater than 1 character", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
