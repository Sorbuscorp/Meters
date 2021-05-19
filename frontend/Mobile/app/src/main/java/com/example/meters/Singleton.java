package com.example.meters;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.String.valueOf;

//Класс используется для хранения сессии и cookie в приложении.
public class Singleton {
    public static final Singleton INSTANCE = new Singleton();

    private Singleton(){}

    boolean isSessionSet = false;
    String sessionId="";
    Date liveTime = null;

    MeterList meters = new MeterList();

    boolean SetSession(String fullSessionCookie) {
        String[] patterns = {	"yyyy.MM.dd G 'at' HH:mm:ss z",
                "EEE, MMM d, ''yy",
                "h:mm a",
                "hh 'o''clock' a, zzzz",
                "K:mm a, z",
                "yyyyy.MMMMM.dd GGG hh:mm aaa",
                "EEE, d MMM yyyy HH:mm:ss Z",
                "yyMMddHHmmssZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                "YYYY-'W'ww-u"};
        //.substring(5,16) "d MMM y"
        Locale[] locales = {	Locale.getDefault(),
                Locale.US,
                Locale.FRANCE};
        String[] arr = fullSessionCookie.split(";");
        boolean converted =false;
        for(String pattern: patterns){
            for(Locale loc: locales){
                try {
                    this.liveTime = new SimpleDateFormat(pattern,loc ).parse(arr[1].split("=")[1]);
                    converted=true;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if(!converted)
            return false;
        this.sessionId=arr[0].split("=")[1];

        this.isSessionSet = true;
        return true;
    }

}
