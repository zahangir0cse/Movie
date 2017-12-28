package com.example.zahangiralam.movie.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zahangiralam.movie.R;
import com.example.zahangiralam.movie.dao.DatabaseHelper;
import com.example.zahangiralam.movie.login.LoginActivity;
import com.example.zahangiralam.movie.model.User;

public class RegisterActivity extends AppCompatActivity {

    private Button regButton;
    private EditText userNameET, emailET, passwordET, phoneNoET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = userNameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String phone = phoneNoET.getText().toString();
                if (uName.trim().length()>0 && password.trim().length()>0 && phone.trim().length()>0 && email.trim().length()>0){
                    DatabaseHelper helper = new DatabaseHelper(RegisterActivity.this);
                    User user = new User();
                    user.setUserName(uName);
                    user.setUserEmail(email);
                    user.setUserPhoneNumber(phone);
                    user.setUserPassword(password);
                    if (email.equals("zahangir@gmail.com")){
                        user.setUserRole("admin");
                    }else {
                        user.setUserRole("user");
                    }
                    helper.saveUser(user);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), "You can't make any field empty", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initComponent(){
        regButton = findViewById(R.id.regButton);
        userNameET = findViewById(R.id.unameText);
        emailET = findViewById(R.id.emailText);
        passwordET = findViewById(R.id.passwordText);
        phoneNoET = findViewById(R.id.phoneText);
    }
}
