package com.example.meters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.CookieHandler;
import java.text.ParseException;
import java.util.HashMap;

public class User {
    String url = "/user/";
    Singleton singleton = Singleton.INSTANCE;
    ServerConnecter connecter = new ServerConnecter();


    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean login(String login, String password) throws IOException {

        HashMap<String, String> params = new HashMap<>();
        params.put("Login",login);
        params.put("Password",password);

        HTTPResponse res = connecter.fetch(url,"GET", params);
        if(!res.isOk())
            return false;

        singleton.SetSession( res.getHeader("Set-cookie"));
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean register(String login, String password, String name, String email, String address) throws IOException {


        HashMap<String, String> params = new HashMap<>();
        params.put("Login",login);
        params.put("Password",password);
        params.put("Name",name);
        params.put("Email",email);
        params.put("Address",address);
        HTTPResponse res = connecter.fetch(url,"PUT", params);
        if(!res.isOk())
            return false;
        singleton.SetSession( res.getHeader("Set-cookie"));
        return true;

    }
}
