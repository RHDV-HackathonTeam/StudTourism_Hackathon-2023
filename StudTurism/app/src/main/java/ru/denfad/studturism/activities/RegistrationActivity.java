package ru.denfad.studturism.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.studturism.Model.User;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.NetworkService;

public class RegistrationActivity extends AppCompatActivity {


    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_registration);
        super.onCreate(savedInstanceState);

        Button signIn = findViewById(R.id.sign_in);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        TextInputEditText name = findViewById(R.id.name_text);
        TextInputEditText surname = findViewById(R.id.password_text);
        TextInputEditText secondName = findViewById(R.id.second_name_text);
        TextInputEditText password = findViewById(R.id.password_text);
        TextInputEditText login = findViewById(R.id.login_text);
        TextInputEditText referal = findViewById(R.id.referal_text);
        TextInputEditText email = findViewById(R.id.email_text);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .addUser(new User(login.getText().toString(), name.getText().toString(), surname.getText().toString(), secondName.getText().toString(), password.getText().toString(), email.getText().toString()))
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.e("Server", bodyToString(call.request().body()));
                                Log.e("Server", response.toString());
                                try {
                                    Log.e("Signin", response.body().string().toString());
                                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                                    editor.putString("email", email.getText().toString());
                                    editor.putString("username", login.getText().toString());
                                    editor.putString("password", password.getText().toString());
                                    editor.apply();
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                } catch (IOException e) {

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }
        });


    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}