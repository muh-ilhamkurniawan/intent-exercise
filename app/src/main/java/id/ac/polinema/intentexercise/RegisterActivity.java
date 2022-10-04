package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private ImageView photo_profil;
    private TextInputEditText fullnameInput;
    private TextInputEditText emailInput;
    private TextInputEditText passInput;
    private TextInputEditText confir_passInput;
    private TextInputEditText homepageInput;
    private TextInputEditText aboutInput;
    private Button btnInput;
    private CircleImageView avatar, change_Avatar;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private Bitmap bitmap;
    private Uri imgUri = null;
    private boolean change_img = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        photo_profil = (ImageView) findViewById(R.id.imageView);
        fullnameInput = (TextInputEditText) findViewById(R.id.text_fullname);
        emailInput = (TextInputEditText) findViewById(R.id.text_email);
        passInput = (TextInputEditText) findViewById(R.id.text_password);
        confir_passInput = (TextInputEditText) findViewById(R.id.text_confirm_password);
        homepageInput = (TextInputEditText) findViewById(R.id.text_homepage);
        aboutInput = (TextInputEditText) findViewById(R.id.text_about);
        btnInput = findViewById(R.id.button_ok);
        avatar = findViewById(R.id.image_profile);


        photo_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(RegisterActivity.this, ProfileActivity.class);
                String fullname = fullnameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passInput.getText().toString();
                String con_password = confir_passInput.getText().toString();
                String homepage = homepageInput.getText().toString();
                String about = aboutInput.getText().toString();

                if (!change_img) {
                    Toast.makeText(RegisterActivity.this, "Image must be change", Toast.LENGTH_SHORT).show();
                } else if (fullname.isEmpty()) {
                    fullnameInput.setError("Fullname required");
                } else if (email.isEmpty()) {
                    emailInput.setError("Email required");
                } else if (password.isEmpty()) {
                    passInput.setError("Password required");
                } else if (con_password.isEmpty()) {
                    confir_passInput.setError("Confirm Password required");
                } else if (homepage.isEmpty()) {
                    homepageInput.setError("Homepage required");
                } else if (about.isEmpty()) {
                    aboutInput.setError("About required");
                } else if (!password.equals(con_password)) {
                    Toast.makeText(RegisterActivity.this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();
                } else {
                    String image = imgUri.toString();
                    pindah.putExtra("image", image);
                    pindah.putExtra("fullname", fullname);
                    pindah.putExtra("email", email);
                    pindah.putExtra("password", password);
                    pindah.putExtra("con_password", con_password);
                    pindah.putExtra("homepage", homepage);
                    pindah.putExtra("about", about);
                    startActivity(pindah);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Cancel input image", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (requestCode == GALLERY_REQUEST_CODE) {
                if (data != null) {
                    try {
                        change_img = true;
                        imgUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        avatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }
}