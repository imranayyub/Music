package com.example.im.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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


public class RegistrationPage extends AppCompatActivity implements View.OnClickListener {

    EditText userName, userEmail, password, userPhone;
    String name, email, passwords, phone;
    //    String userName,password,phone;
    Button registerButton;
    boolean email_result = false;
    boolean validPhone = false;
    private Vibrator vib;
    Animation animShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);

        userName = (EditText) findViewById(R.id.userName);
        userEmail = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.registerButton);
        userPhone = (EditText) findViewById(R.id.userPhone);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        registerButton.setOnClickListener(this);

    }

    public void onRegistrationSuccess() {
        //After Successful Registration
        //Retrofit to retrieve JSON data from server.

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
//        OkHttpClient client = new OkHttpClient();
//        client.setConnectTimeout(10, TimeUnit.SECONDS);
//        client.setReadTimeout(30, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())     //Using GSON to Convert JSON into POJO.
                .build();
//        String userName = userEmail.getText().toString();
//        String userpassword = password.getText().toString();
//        String phone = userPhone.getText().toString();

        //Passing Interface to create an implementation.
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        try {
            Registrationinfo registerationInfo = new Registrationinfo(email, passwords, phone);
            registerationInfo.setUsername(email);
            registerationInfo.setPassword(passwords);
            registerationInfo.setPhone(phone);
            apiService.register(registerationInfo).enqueue(new Callback<JsonObject>() {
                //        apiService.savePost(userName, password, phone).enqueue(new Callback<Registrationinfo>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
//                            showResponse(
                               Toast.makeText(getApplicationContext(),response.body().toString(), Toast.LENGTH_SHORT).show();
                        Log.i("here:", "post submitted to API." + response.body().toString());
                        Toast.makeText(getApplicationContext(), "Registeration Successful.. ", Toast.LENGTH_SHORT).show();
                        Intent main= new Intent(RegistrationPage.this,MainActivity.class);
                        startActivity(main);
                        finish();

                    } else if (response.code() == 200) {
                        Toast.makeText(getApplicationContext(), "Registeration Successful.. ", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.code() == 500) {
                        Toast.makeText(getApplicationContext(), "Email id and phone no. already in use..", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Log.d("Error code : ", "" + response.code());
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


    public void register() {
        initialize();
        if (!Validate()) {
        } else {
            onRegistrationSuccess();

        }
    }

    public boolean Validate() {
        boolean valid = true;
        if (name.isEmpty() || name.length() >= 32) {
            userName.setError("Please Enter Valid name");
        }
        if (email.isEmpty()) {
            userEmail.setError("Please Enter Email");
              valid=false;
        } else {
            isValidEmailAddress(email);
            if (email_result) {
                userEmail.setText(email);
            } else {
                userEmail.setError("Enter valid email address.");
                valid=false;
            }
        }
        if (passwords.isEmpty()) {
            valid=false;
            password.setError("Please enter password");
        } else {
            if (passwords.length() >= 8 && isValidPassword(password.getText().toString())) {
                password.setText(passwords);
            } else {
                valid=false;
                password.setError("1.password must contain alphanumeric value +\n+" +
                        "2.Atleast one special character" + "\n" +
                        "3.password length mustbe atleast 8");
            }
        }
        if (phone.isEmpty()) {
            valid=false;
            userPhone.setError("Please enter phone");
        } else {
            isvalidDate(phone);
            if (validPhone) {
                userPhone.setText(phone);
            } else {
                valid=false;
                userPhone.setError("Phone number should be of 10 digits");
            }
        }

        return valid;
    }

    public boolean isvalidDate(String phoneNumber) {
       if(phoneNumber.length()==10 ){
           validPhone=true;
       }
        else {
            userPhone.setError("Invalid Phone Number");
        }
        return validPhone;
    }

    public boolean isValidEmailAddress(String email) {

        if (email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))//email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        {
            email_result = true;
            return email_result;
        }
        return email_result;
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void initialize() {
        name = userName.getText().toString();
        email = userEmail.getText().toString();
        passwords = password.getText().toString();
        phone = userPhone.getText().toString();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.registerButton: {
                register();
            }
        }
    }
}
