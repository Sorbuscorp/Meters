package com.example.meters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MeterList {
    String url = "/meters/";
    ArrayList<Meter> meterCollection = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.N)
    boolean load() throws IOException {
        ServerConnecter connecter = new ServerConnecter();
        HTTPResponse res = connecter.fetch(url , "GET", null);
        if (res.isOk())
        {
            res.parseBodyAsJson();
            setMeterCollection(res.bodyJArray);
        }
        return res.isOk();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setMeterCollection(ArrayList<Map<String,String>> collection) {
        this.meterCollection.clear();
        for (Map map:
                collection) {
            Meter m = new Meter((String) map.get("id"));
            m.name = (String) map.get("name");
            m.description = (String) map.get("description");
            m.lastData =(String) map.get("lastdata");
            m.verificationDate = (String) map.get("verificationdate");
            this.meterCollection.add(m);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    boolean append(Meter meter) throws IOException {

        ServerConnecter connecter = new ServerConnecter();
        HashMap<String, String> params = new HashMap<>();
        params.put("Name",meter.name);
        params.put("Description",meter.description);
        params.put("LastData",meter.lastData);
        params.put("VerificationDate",meter.verificationDate);
        HTTPResponse res = connecter.fetch(url,"PUT", params);
        if (res.isOk()) {
            meter.id=res.getBody();
            this.meterCollection.add(meter);
        }

        return res.isOk();

    }
}
