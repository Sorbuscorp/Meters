package com.example.meters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HTTPResponse {
    private String body;
    private String[] status = new String[3];
    private Map<String, String> headers = new HashMap<String, String>();


    boolean bodyIsJArray = false;
    boolean bodyIsJObject = false;
    Map<String, String> bodyJObject = null;
    ArrayList<Map<String, String>> bodyJArray = null;



    public void setHeader(String name, String value) {
        if(name!=null)
            this.headers.put(name.toLowerCase(), value);
    }

    public String getHeader(String key) {
        String v = this.headers.get(key.toLowerCase());
        return v;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getStatus() {
        StringJoiner joiner= new StringJoiner(" ");
        for (String v:
             this.status) {
            joiner.add(v);
        }

        return joiner.toString();
    }

    public int getStatusCode(){
        return parseInt(this.status[1]);
    }

    public void setStatus(String version, String statusCode, String statusText) {
        this.status[0]=version;
        this.status[1]=statusCode;
        this.status[2]=statusText;

    }

    public void setStatus(String statusCode) {
        this.status[0]="HTTP/1.1";
        this.status[1]=statusCode;
        this.status[2]="";
    }
    public void setStatus(int statusCode) {
        this.status[0]="HTTP/1.1";
        this.status[1]= String.valueOf(statusCode);
        this.status[2]="";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseBodyFieldAsJsonArray(String fieldName){
        JsonElement jelement = new JsonParser().parse(this.body);
        this.bodyJArray = parseJArray( jelement.getAsJsonObject().get(fieldName).getAsJsonArray() );
        this.bodyIsJArray =true;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseBodyAsJson(){
        JsonElement jelement = new JsonParser().parse(this.body);
        if(jelement.isJsonObject())
        {
            JsonObject jobject = jelement.getAsJsonObject();
            this.bodyIsJObject=true;
            this.bodyJObject = parseJObject(jobject);
        }
        else if (jelement.isJsonArray())
        {
            JsonArray jarray = jelement.getAsJsonArray();
            this.bodyIsJArray =true;
            this.bodyJArray = parseJArray(jarray);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    ArrayList<Map<String, String>> parseJArray(JsonArray jarray){
        ArrayList<Map<String, String>> res = new ArrayList<>();
        jarray.forEach(o->{
            res.add(parseJObject(o.getAsJsonObject()));
        });
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    Map<String, String> parseJObject(JsonObject jobject){
        Map<String, String> res = new HashMap<>();
        jobject.keySet().forEach(k->{
            res.put(k.toLowerCase(),jobject.get(k).getAsString());
        });
        return res;
    }

    public boolean isOk(){
        if(this.getStatusCode()/100!=2)
            return false;

        return true;
    }


}
