package com.example.sol.spotlight;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;


class UserRegister {
    public String school;
    public String occupation;
    public String gender;
    public String birthday_year;
    public String birthday_month;
    public String birthday_day;
    public String location;
    public String nickname;
    public String height;
    public String body;
    public String smoke;
    public String drink;
    public String religion;
    public String personality;
    public String status;

    public UserRegister(String school, String occupation, String gender, String birthday_year, String birthday_month, String birthday_day, String location, String nickname, String height, String body, String smoke, String drink, String religion, String personality) {
        this.school = school;
        this.occupation = occupation;
        this.gender = gender;
        this.birthday_year = birthday_year;
        this.birthday_month=birthday_month;
        this.birthday_day=birthday_day;
        this.location = location;
        this.nickname = nickname;
        this.height = height;
        this.body = body;
        this.smoke = smoke;
        this.drink = drink;
        this.religion = religion;
        this.personality = personality;
        this.status = "1";
    }
}

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    Button register_btn;
    Button gender_male_btn;
    Button gender_female_btn;
    Button register_later;
    Button body_1;
    Button body_2;
    Button body_3;
    Button body_4;
    Button body_5;
    Button body_6;
    Button imgBtn1;
    ImageView imgView;

    EditText school;
    EditText occupation;
    EditText workplace;
    Spinner birthday_year;
    Spinner birthday_month;
    Spinner birthday_day;
    Spinner location;
    EditText nickname;
    EditText height;
    EditText smoke;
    EditText drink;
    EditText religion;
    EditText personality;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    String gender_string = "NULL";
    String body_string = "NULL";
    Uri selectedImage;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_btn = (Button) findViewById(R.id.register_submit);
        gender_male_btn = (Button) findViewById(R.id.gender_male);
        gender_female_btn = (Button) findViewById(R.id.gender_female);
        register_later = (Button) findViewById(R.id.register_later);
        body_1 = (Button) findViewById(R.id.body_1);
        body_2 = (Button) findViewById(R.id.body_2);
        body_3 = (Button) findViewById(R.id.body_3);
        body_4 = (Button) findViewById(R.id.body_4);
        body_5 = (Button) findViewById(R.id.body_5);
        body_6 = (Button) findViewById(R.id.body_6);

        imgBtn1 = (Button) findViewById(R.id.buttonLoadPicture_1);
        imgView = (ImageView) findViewById(R.id.selected_img_view);

        school = (EditText) findViewById(R.id.register_school);
        occupation = (EditText) findViewById(R.id.register_occupation);
        workplace = (EditText) findViewById(R.id.register_workplace);
        birthday_year = (Spinner) findViewById(R.id.spinner_year);
        birthday_month = (Spinner) findViewById(R.id.spinner_month);
        birthday_day = (Spinner) findViewById(R.id.spinner_day);
        location = (Spinner) findViewById(R.id.register_location);
        nickname = (EditText) findViewById(R.id.register_nickname);
        height = (EditText) findViewById(R.id.register_height);
        smoke = (EditText) findViewById(R.id.register_smoke);
        drink = (EditText) findViewById(R.id.register_drink);
        religion = (EditText) findViewById(R.id.register_religion);
        personality = (EditText) findViewById(R.id.register_personality);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        final String user_id = auth.getCurrentUser().getUid();

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        //get the spinner from the xml.
        Spinner dropdown = (Spinner)findViewById(R.id.spinner_year);
        String[] items_year = new String[30];
            for(int i=0;i<items_year.length;i++){
                items_year[i] = Integer.toString(1980 + i);
            }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_year);
        dropdown.setAdapter(adapter);

        dropdown = (Spinner)findViewById(R.id.spinner_month);
        String[] items_month = new String[12];
            for(int i=0;i<items_month.length;i++){
                items_month[i] = Integer.toString(i+1);
            }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_month);
        dropdown.setAdapter(adapter);

        dropdown = (Spinner)findViewById(R.id.spinner_day);
        String[] items_day = new String[31];
        for(int i=0;i<items_day.length;i++){
            items_day[i] = Integer.toString(i+1);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_day);
        dropdown.setAdapter(adapter);

        dropdown = (Spinner)findViewById(R.id.register_location);
        String[] register_location = new String[]{"서울", "경기", "인천", "대전", "충북", "충남", "강원", "부산", "경북", "경남", "대구", "울산", "광주", "전북", "전남", "제주"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, register_location);
        dropdown.setAdapter(adapter);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedImage != null) {
                    pd.show();
                    UserRegister u = new UserRegister(
                            school.getText().toString(),
                            occupation.getText().toString(),
                            gender_string,
                            birthday_year.getSelectedItem().toString(),
                            birthday_month.getSelectedItem().toString(),
                            birthday_day.getSelectedItem().toString(),
                            location.getSelectedItem().toString(),
                            nickname.getText().toString(),
                            height.getText().toString(),
                            body_string,
                            smoke.getText().toString(),
                            drink.getText().toString(),
                            religion.getText().toString(),
                            personality.getText().toString());
                    mDatabase.child("Users").child(user_id).setValue(u);

                    StorageReference childRef = mStorage.child("User_Profile_Pic").child(user_id).child("profile");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(selectedImage);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(RegisterActivity.this, "사진 업로드 완료.", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "사진 업로드에 실패했습니다. -> " + e, Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });


                    Intent i = new Intent(getApplicationContext(), RegisterResultActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "사진이 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gender_male_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_string = "Male";
                body_5.setText("근육질");
                body_6.setText("건장한");
            }
        });

        gender_female_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_string = "Female";
                body_5.setText("살짝 볼륨");
                body_6.setText("글래머");
            }
        });

        body_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_string = body_1.getText().toString();
            }
        });
        body_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_string = body_2.getText().toString();
            }
        });
        body_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_string = body_3.getText().toString();
            }
        });
        body_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_string = body_4.getText().toString();
            }
        });
        body_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_string = body_5.getText().toString();
            }
        });
        body_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_string = body_6.getText().toString();
            }
        });


        register_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, RegisterLaterActivity.class);
                startActivity(i);
            }
        });

    }
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;


    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/* video/*");
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
                // Get the Image from data
                selectedImage = data.getData();
                Picasso.with(RegisterActivity.this).load(selectedImage).noPlaceholder().centerCrop().fit().into(imgView);
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                // Get the cursor
//                Cursor cursor = getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                // Move to first row
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgDecodableString = cursor.getString(columnIndex);
//                cursor.close();
//
//                final BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                Drawable d = new BitmapDrawable(getResources(), BitmapFactory.decodeFile(imgDecodableString, options));
//                // imgBtn1.setBackgroundResource(0);
//                imgView.setImageResource(0);
//                imgView.setBackground(d);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}


