package com.example.meters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Meter {

    class MeterData{
        String value;
        String timestamp;
    }

    String id = "";
    String url = "/meter/";
    String name= "";
    String description= "";
    String lastData= "";
    String verificationDate= "";
    ArrayList<MeterData> dataArrayList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    Boolean get() throws IOException {
        ServerConnecter connecter = new ServerConnecter();
        HTTPResponse res = connecter.fetch(url+id ,"GET", null);
        if (res.isOk())
            ;//this.dataArrayList = res.getBody();
        return res.isOk();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    Boolean post(String newData, String newVerificationDate) throws IOException {
        ServerConnecter connecter = new ServerConnecter();
        HashMap<String, String> params = new HashMap<>();
        params.put("Data",newData);
        params.put("NewVerification",newVerificationDate);
        HTTPResponse res = connecter.fetch(url+id,"POST", params);
        return res.isOk();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    Boolean delete(String pwd) throws IOException {
        ServerConnecter connecter = new ServerConnecter();
        HashMap<String, String> params = new HashMap<>();
        params.put("UserPassword",pwd);
        HTTPResponse res = connecter.fetch(url+id,"'DELETE'", params);
        return res.isOk();
    }
}
