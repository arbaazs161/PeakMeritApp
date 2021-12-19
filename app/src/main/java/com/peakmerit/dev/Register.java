package com.peakmerit.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText nameField, emailField, passwordField;
    Button btnRegister;
    TextView btnGotologin;

    public static final String MYPREFERENCES = "MyPrefs";
    public static final String UserId = "UserId";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        nameField = findViewById(R.id.nameRegister);
        emailField = findViewById(R.id.emailRegister);
        passwordField = findViewById(R.id.passwordRegister);

        btnRegister = findViewById(R.id.buttonRegister);
        btnGotologin = findViewById(R.id.gotoLogin);

        btnGotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String email = emailField.getText().toString();
                String password= passwordField.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(name.isEmpty())
                {
                    nameField.setError("Mandatory Field!");
                    nameField.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
                {
                    emailField.setError("Enter Valid Email");
                    emailField.requestFocus();
                    return;
                }
                if(password.isEmpty() || password.length()<8)
                {
                    passwordField.setError("Password must be 8 or more characters");
                    passwordField.requestFocus();
                    return;
                }

                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().register(name, email, password);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            String userId = result.substring(1, result.length() -1);
                            Log.d("Response String, ", result);
                            if(result.equals("Fail")){
                                Log.d("Success", "Real Success");
                                Toast.makeText(Register.this, "User Already Exists, Please Login", Toast.LENGTH_LONG).show();
                            }
                            else{
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userId", userId);
                                editor.putString("logged", "logged");
                                editor.commit();
                                Intent intent = new Intent(Register.this, Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Log.d("Success Register", "Hahahahaha!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}