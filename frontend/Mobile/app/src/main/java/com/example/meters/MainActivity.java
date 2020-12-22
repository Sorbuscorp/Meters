package com.example.meters;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    boolean user_login_state = false;
    Singleton singletone = Singleton.INSTANCE;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!singletone.isSessionSet)
            runSignIn();
    }

    protected void runSignIn(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}