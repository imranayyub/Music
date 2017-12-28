package com.example.im.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Im on 23-12-2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userEmail, password;
    Button login, register;
    String email, passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        userEmail = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.register: {
                Intent registration = new Intent(this, RegistrationPage.class);
                startActivity(registration);
                break;
            }
            case R.id.login: {
//                Toast.makeText(getApplicationContext(), "login.. ", Toast.LENGTH_SHORT).show();
                Login();

            }

        }
    }

    public void Login() {
        initialize();
        if (!Validate()) {
        } else {
            onLoginSuccess();

        }
    }

    public void initialize() {
        email = userEmail.getText().toString();
        passwords = password.getText().toString();
    }

    public boolean Validate() {
        boolean valid = true;
            isValiduserEmailAddress(email);
            if (userEmail_result) {
                userEmail.setText(email);
            } else {
                valid =false;
                userEmail.setError("Enter valid userEmail address.");
            }
                if (passwords.isEmpty()) {
                    valid =false;
            password.setError("Please enter password");
        } else {
            if (passwords.length() >= 8 && isValidPassword(password.getText().toString())) {
                password.setText(passwords);
            } else {
                valid =false;
                password.setError("1.Password must contain alphanumeric value +\n+" +
                        "2.Atleast one special character" + "\n" +
                        "3.Password length mustbe atleast 8");
            }
        }
        return valid;
    }

    boolean userEmail_result;

    public boolean isValiduserEmailAddress(String userEmail) {

        if (userEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+com+"))//userEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        {
            userEmail_result = true;
            return userEmail_result;
        }
        return userEmail_result;
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public void onLoginSuccess() {
        //After Successful Registration
        //Retrofit to retrieve JSON data from server.

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())     //Using GSON to Convert JSON into POJO.
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        try {
//            String username = userEmail.getText().toString();
//            String passwords = password.getText().toString();

            Registrationinfo registerationInfo = new Registrationinfo(email, passwords, "");
            registerationInfo.setUsername(email);
            registerationInfo.setPassword(passwords);
//            registerationInfo.setPhone(phone);
            apiService.login(registerationInfo).enqueue(new Callback<JsonObject>() {
                //        apiService.savePost(username, password, phone).enqueue(new Callback<Registrationinfo>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
//                            showResponse(
                        Toast.makeText(getApplicationContext(),response.body().toString(), Toast.LENGTH_SHORT).show();
                        Log.i("here:", "post submitted to API." + response.body().toString());
                        Toast.makeText(getApplicationContext(), "Login Successful..!! ", Toast.LENGTH_SHORT).show();
                        Intent main= new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(main);
                        finish();

                    } else if (response.code() == 200) {
                        Toast.makeText(getApplicationContext(), "Login Successful.. ", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.code() == 500) {
                        Toast.makeText(getApplicationContext(), "Some Error occured(Iternal ", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(getApplicationContext(),"Wrong Email or Password..", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("here", "Unable to submit post to API.");
                    Toast.makeText(getApplicationContext(), "Registeration failed ", Toast.LENGTH_SHORT).show();

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
