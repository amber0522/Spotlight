package com.example.sol.spotlight;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class I_like extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_like);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    // todo 화면 붙이기
                    case R.id.menu1:
                        Intent m1 = new Intent(I_like.this, ChatRoomActivity.class);
                        startActivity(m1);
                        break;
                    case R.id.menu2:
                        Intent m2 = new Intent(I_like.this, Like_me.class);
                        startActivity(m2);
                        break;
                    case R.id.menu3:
                        Intent m3 = new Intent(I_like.this, ViewPagerPracticeActivity.class);
                        startActivity(m3);
                        break;
                }
                return true;
            }
        });

    }
}
