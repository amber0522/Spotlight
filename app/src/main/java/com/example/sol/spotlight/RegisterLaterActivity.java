package com.example.sol.spotlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterLaterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    Button register_later_logout;
    Button register_later_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_later);

        auth = FirebaseAuth.getInstance();

        register_later_logout = (Button) findViewById(R.id.register_later_logout);
        register_later_return = (Button) findViewById(R.id.register_later_return);

        register_later_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(RegisterLaterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        register_later_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterLaterActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
