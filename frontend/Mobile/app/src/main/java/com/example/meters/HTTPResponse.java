package com.example.meters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static java.lang.Integer.parseInt;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HTTPResponse {
    private String body;
    private String[] status = new String[3];
    private Map<String, String> headers = new HashMap<String, String>();

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

    public String parseBodyAsJson(){
        JsonElement jelement = new JsonParser().parse(this.body);
        JsonObject jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("data");
        JsonArray jarray = jobject.getAsJsonArray("translations");
        jobject = jarray.get(0).getAsJsonObject();
        String result = jobject.get("translatedText").getAsString();
        return result;
    }

    public boolean isOk(){
        if(this.getStatusCode()/100!=2)
            return false;

        return true;
    }


}
