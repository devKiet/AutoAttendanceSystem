package com.java.autoattendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.java.autoattendancesystem.model.Login;
import com.java.autoattendancesystem.reotrfit.LoginApi;
import com.java.autoattendancesystem.reotrfit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import androidx.appcompat.app.AppCompatActivity;



public class RegistrationadminActivity extends AppCompatActivity {


    private EditText userName, userPassword;
    private Button regButton;
    private TextView userLogin;
    private Spinner userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationadmin);
        setupUIViews();
        RetrofitService retrofitService = new RetrofitService();
        LoginApi loginApi = retrofitService.getRetrofit().create(LoginApi.class);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String username  = userName.getText().toString();
                    String password = userPassword.getText().toString();
                    String type = userType.getSelectedItem().toString();

                    Login login = new Login();
                    login.setUsername(username);
                    login.setPassword(password);
                    login.setType(type);
                    loginApi.save(login)
                            .enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            Toast.makeText(RegistrationadminActivity.this,"Register Successful!!",Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(RegistrationadminActivity.this, "Register failed!!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(RegistrationadminActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationadminActivity.this, MainActivity.class));
            }
        });

    }

    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userType = (Spinner) findViewById(R.id.etType);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView)findViewById(R.id.tvUserLogin);
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("Teacher");
        typeList.add("Manager");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                typeList
        );
        userType.setAdapter(typeAdapter);
    }

    private boolean validate(){
        Boolean result = false;
        String name = userName.getText().toString();
        String password = userPassword.getText().toString();

        if(name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

}