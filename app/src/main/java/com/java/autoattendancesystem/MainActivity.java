package com.java.autoattendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.java.autoattendancesystem.model.Login;
import com.java.autoattendancesystem.reotrfit.LoginApi;
import com.java.autoattendancesystem.reotrfit.RetrofitService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText Name, Password;
    private Button Login;
    private TextView Info;
    private int counter = 5;
    private TextView userRegistration;
    private List<Login> loginList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIViews();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name  = Name.getText().toString();
                String pass = Password.getText().toString();
                if(validate())
                {
                    for(Login l : loginList)
                    {
                        if(name.equals(l.getUsername()) && pass.equals(l.getPassword()))
                        {
                            if(l.getType().equals("Teacher"))
                            {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, TeacherActivity.class));
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                ////////////////fix here
                                startActivity(new Intent(MainActivity.this, RegistrationadminActivity.class));
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            counter--;
                            Info.setText("No of attempt remaining : " + counter);
                            if(counter == 0) {
                                Login.setEnabled(false);
                            }
                        }
                    }
                }
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationadminActivity.class));
            }
        });
    }
    private void setupUIViews() {
        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        Info = (TextView) findViewById(R.id.tvInfo);
        userRegistration = (TextView) findViewById(R.id.tvRegister);
        Info.setText("No of attempt remaining : 5");
    }
    public void learnMore(View view) {
        Intent intent = new Intent(MainActivity.this, LearnMoreActivity.class);
        startActivity(intent);
    }

    public void aboutUs(View view) {
        Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void loadLogin() {
        RetrofitService retrofitService = new RetrofitService();
        LoginApi loginApi = retrofitService.getRetrofit().create(LoginApi.class);
        loginApi.getAllLogin()
                .enqueue(new Callback<List<Login>>() {
                    @Override
                    public void onResponse(Call<List<Login>> call, Response<List<Login>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Login>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed to load employees", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void populateListView(List<Login> log) {
        loginList = new ArrayList<>(log);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLogin();
    }
    private boolean validate() {
        Boolean result = false;
        String name = Name.getText().toString();
        String password = Password.getText().toString();

        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}