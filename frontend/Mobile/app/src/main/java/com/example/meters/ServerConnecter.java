package com.example.meters;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

//Класс для реализации взаимодействия с сервером
public class ServerConnecter extends  AsyncTask<HttpURLConnection, Void, HTTPResponse>{
    private static final String TAG = "ServerConnecter";
    String server = "http://192.168.0.101:8080"; //Адрес сервера API
    Singleton singleton = Singleton.INSTANCE; //получаем экземпляр синлтона для доступа к сессии

    //Реализация fetch схожая с JS версией (выполняется в отдельном потоке)
    @RequiresApi(api = Build.VERSION_CODES.N)
    HTTPResponse fetch(String url, String method, HashMap<String, String> params ) throws IOException {

        //настраиваем параметры которые будем передавать через ? в url
        String query ="";
        if (params != null && params.size() !=0) {
            StringJoiner joiner= new StringJoiner("&");
            params.forEach(
                    (k, v) -> {
                        joiner.add(k + '=' + v);
                    }
            );
            query="?"+joiner.toString();
        }
        URL endpoint = null;

        //создаем соединение
        HttpURLConnection connection = null;
        try {
            endpoint = new URL(server+url+query);
            connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod(method.toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpURLConnection finalConnection = connection;

        //если присутствует сессионный cookie то добавляем его в запрос
        if (singleton.isSessionSet && singleton.liveTime.after(new Date()))
        {
            finalConnection.setRequestProperty("Cookie", "sessionid"+"="+singleton.sessionId);
        }

        HTTPResponse res=new HTTPResponse();
        this.execute(finalConnection); //запускаем асинхронный поток на выполнение
        try {
            res=this.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected HTTPResponse doInBackground(HttpURLConnection... connections) {
        HttpURLConnection finalConnection = connections[0];
        HTTPResponse response = new HTTPResponse();
        try {
            //парсим ответ от сервера
            BufferedReader br = null;
            int i=finalConnection.getResponseCode();
            if(finalConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(finalConnection.getInputStream()));

                response.setBody(br.lines().collect(Collectors.joining()));
                response.setStatus(valueOf(HttpURLConnection.HTTP_OK));
                finalConnection.getHeaderFields().forEach((k, v) -> {
                    response.setHeader(k, v.get(0));
                });
            }
            else
            {
                response.setStatus(finalConnection.getResponseCode());
            }
                return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
