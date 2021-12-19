package com.peakmerit.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private static final String MYPREFERENCES = "MyPrefs";
    EditText emailField, passwordField;
    Button btnLogin;
    TextView btnGotoSignup;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        emailField = findViewById(R.id.emailLogin);
        passwordField = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.buttonLogin);
        btnGotoSignup = findViewById(R.id.gotoRegister);

        btnGotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password= passwordField.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().login(email, password);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            String userId = result.substring(1, result.length() -1);
                            Log.d("Response String, ", userId);
                            if(result.equals("Fail")){
                                Log.d("Success", "Real Success");
                                Toast.makeText(Login.this, "User Already Exists, Please Login", Toast.LENGTH_LONG).show();
                            }
                            else{
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userId", userId);
                                editor.putString("logged", "logged");
                                editor.commit();
                                Intent intent = new Intent(Login.this, Home.class);
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
                        Log.d("Failed", "onFailure: " + t.getMessage());
                        Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}