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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import ru.denfad.studturism.Model.UserPoint;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;

public class AddMarkActivity extends AppCompatActivity {

    private static final int READ_EXTERNAL_STORAGE = 200;
    private static final int PICK_IMAGE = 100;
    private  ImageButton load;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark);


        Intent intent = getIntent();
        double X = intent.getDoubleExtra("X", 0);
        double Y = intent.getDoubleExtra("Y", 0);

        load = findViewById(R.id.load_image);
        load.setClipToOutline(true);
        load.setOnClickListener(view -> {
            //запрос на доступ к галлереи
            view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation));
            int permissionStatus = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(AddMarkActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE);
            }

        });

        EditText markName = findViewById(R.id.mark_name);
        EditText description = findViewById(R.id.mark_description);

        Button add = findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainService.getInstance().addUserPoint(new UserPoint(markName.getText().toString(), X, Y, description.getText().toString(), R.drawable.peterburg1));
                Intent intent = new Intent(AddMarkActivity.this, MainActivity.class);
                intent.putExtra("from_map", true);
                startActivity(intent);
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
            File finalCompress = compress;
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    Credentials credentials = new Credentials("denfad2003", "AQAAAAArZCXAAADLWwB7Pep9mEcuhrs_TcTcdUs");
//                    RestClient client = new RestClient(credentials);
//                    Log.e("API", "Create");
//                    String serverPath = sUsrId + ".jpg"; //путь на самом диске
//                    try {
//                        Link link = client.getUploadLink(serverPath, true);
//                        client.uploadFile(link, true, finalCompress, new UploadProgressListener());
//                    } catch (ServerException | IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }).start();
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