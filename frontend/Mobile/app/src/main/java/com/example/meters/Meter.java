package com.example.meters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Meter {

    @RequiresApi(api = Build.VERSION_CODES.N)
    Meter(String id){
        this.id = id;
        try {
            this.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Meter(String name,String description,String lastData,String verificationDate){
        this.name=name;
        this.description=description;
        this.lastData=lastData;
        this.verificationDate=verificationDate;
    }

    String id = "";
    String url = "/meters/";
    String name= "";
    String description= "";
    String lastData= "";
    String verificationDate= "";
    ArrayList<Map<String,String>> dataArrayList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    Boolean get() throws IOException {
        ServerConnecter connecter = new ServerConnecter();
        HTTPResponse res = connecter.fetch(url+id ,"GET", null);
        if (res.isOk()) {

            res.parseBodyFieldAsJsonArray("MeterDataList");
            dataArrayList = res.bodyJArray;

        }
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
    Boolean delete() throws IOException {
        ServerConnecter connecter = new ServerConnecter();
        HashMap<String, String> params = new HashMap<>();
        //params.put("UserPassword",pwd);
        HTTPResponse res = connecter.fetch(url+id,"DELETE", null);
        return res.isOk();
    }

      @RequiresApi(api = Build.VERSION_CODES.N)
      String dataListAsText(){
        StringJoiner joiner= new StringJoiner("\n");
        for (Map map:
             this.dataArrayList) {

            joiner.add("Дата: "+((String) map.get("datetime")).substring(0,10)+" Показания:"+((String) map.get("data")));

        }
        return joiner.toString();
    }
}
