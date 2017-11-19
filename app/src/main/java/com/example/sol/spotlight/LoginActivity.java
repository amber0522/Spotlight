package com.example.sol.spotlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String user_id = auth.getCurrentUser().getUid();

        mDatabase.child("Users").child(user_id).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object chk_status = dataSnapshot.getValue();
                if(chk_status == null) {
                    mDatabase.child("Users").child(user_id).child("status").setValue("-1");
                }
                else {
                    String status = chk_status.toString();
                    Log.d("TAG", chk_status.toString());
                    if(status.equals("1")) { // 가입 심사가 아직 이루어지지 않은 경우
                        Intent intent = new Intent(LoginActivity.this, RegisterResultActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (status.equals("-1")) { // 계정은 만들었는데 프로필 등록을 마치지 않은 경우
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (status.equals("0")) { // 가입 심사가 통과된 경우
                        Intent intent = new Intent(LoginActivity.this, AcceptedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (status.equals("-2")) { // 가입 심사가 거절된 경우

                        Intent intent = new Intent(LoginActivity.this, RejectedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
