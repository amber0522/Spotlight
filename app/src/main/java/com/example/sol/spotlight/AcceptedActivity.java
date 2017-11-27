package com.example.sol.spotlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AcceptedActivity extends AppCompatActivity {

    Button btn;
    Button chatBtn;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);

        btn =  (Button) findViewById(R.id.logout);
        chatBtn = (Button) findViewById(R.id.temp_chat_btn);
        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(AcceptedActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AcceptedActivity.this, ChatRoomActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
