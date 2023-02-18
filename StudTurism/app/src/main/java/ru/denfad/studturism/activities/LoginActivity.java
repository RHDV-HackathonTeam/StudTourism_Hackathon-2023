package ru.denfad.studturism.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import ru.denfad.studturism.R;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        Button signIn = findViewById(R.id.sign_in);
        Button registration = findViewById(R.id.registration);
        TextInputEditText email = findViewById(R.id.email_text);
        TextInputEditText password = findViewById(R.id.password_text);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("email", email.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.apply();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });


    }
}