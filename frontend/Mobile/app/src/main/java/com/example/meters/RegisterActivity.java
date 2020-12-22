package com.example.meters;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    Singleton singleton = Singleton.INSTANCE;

    Button regBtn;
    TextView loginInput;
    TextView pwdInput;
    TextView nameInput;
    TextView emailInput;
    TextView addressInput;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regBtn = (Button) findViewById(R.id.regBtnReg);
        loginInput = (TextView) findViewById(R.id.RegLogin);
        pwdInput = (TextView) findViewById(R.id.RegPassword);
        nameInput = (TextView) findViewById(R.id.RegName);
        emailInput = (TextView) findViewById(R.id.RegEMail);
        addressInput = (TextView) findViewById(R.id.RegAddr);
        regBtn.setOnClickListener(v->{
            String login=loginInput.getText().toString();
            String pwd=pwdInput.getText().toString();
            String name =nameInput.getText().toString();
            String email=emailInput.getText().toString();
            String address=addressInput.getText().toString();
            boolean succeed=false;
            try {
                succeed = new User().register(login,pwd,name,email,address);
            } catch (IOException e) {
                e.printStackTrace();
            }
            succeed=true;
            if(succeed)
                finish();

        });
    }
}
