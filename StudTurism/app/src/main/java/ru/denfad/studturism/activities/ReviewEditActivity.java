package ru.denfad.studturism.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.studturism.Model.Review;
import ru.denfad.studturism.Model.UserPoint;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.Sevice.NetworkService;

public class ReviewEditActivity extends AppCompatActivity {


    private static final int READ_EXTERNAL_STORAGE = 200;
    private static final int PICK_IMAGE = 100;
    private ImageButton load;
    private Uri imageUri;
    private File mainFile;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_edit);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextInputEditText text = findViewById(R.id.news_description);

        load = findViewById(R.id.load_image);
        load.setClipToOutline(true);
        load.setOnClickListener(view -> {
            //запрос на доступ к галлереи
            view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation));
            int permissionStatus = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(ReviewEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE);
            }

        });

        Button add = findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mainFile);

// MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", mainFile.getName(), requestFile);

                NetworkService.getInstance().getJSONApi().uploadImage(body)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String href = response.body().string().toString();
                                    List<String> hrefs = new ArrayList<>();
                                    hrefs.add(href);
                                    Log.e("href", href);
                                    Review review = new Review(mSharedPreferences.getString("username", "1"), text.getText().toString(), "0", hrefs);
                                    NetworkService.getInstance().getJSONApi()
                                            .addReview(review).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            Intent intent = new Intent(ReviewEditActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });


            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            String filePath = getRealPathFromURI(imageUri);
            load.setImageURI(imageUri);

            File file = new File(filePath);
            File compress = null;
            try {
                compress = new Compressor(getApplicationContext()).compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //restApi
            mainFile = compress;

        } else if (resultCode == RESULT_CANCELED && requestCode == READ_EXTERNAL_STORAGE) {
            Toast.makeText(getApplicationContext(), "Пожалуйста, разрешите приложению доступ к галлереи", Toast.LENGTH_SHORT);
        }
    }

    //метод поиска полного пути до выбранного фото
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}