package com.example.meters;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class SignInActivity extends AppCompatActivity {

    Singleton dataSingleton = Singleton.INSTANCE;

    Button loginBtn;
    Button regBtn;
    TextView loginInput;
    TextView pwdInput;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        regBtn = (Button) findViewById(R.id.regBtn);
        loginInput = (TextView) findViewById(R.id.editTextLogin);
        pwdInput = (TextView) findViewById(R.id.editTextPassword);
        loginBtn.setOnClickListener(v->{
                String login=loginInput.getText().toString();
                String pwd=pwdInput.getText().toString();
                boolean succeed=false;
                try {
                    succeed = new User().login(login, pwd);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(succeed)
                    finish();

        });
        regBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);


        });
    }

//               try {
//        if(user.Login("ales", "789456"))
//            this.user_login_state=true;
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
}
