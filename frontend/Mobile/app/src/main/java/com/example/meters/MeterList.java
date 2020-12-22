package com.example.meters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MeterList {
    String url = "/meter/";
    ArrayList<Meter> meterCollection;
    @RequiresApi(api = Build.VERSION_CODES.N)

    boolean load() throws IOException {
        ServerConnecter connecter = new ServerConnecter();
        HTTPResponse res = connecter.fetch(url , "GET", null);
        if (res.isOk())
            ;//this.meterCollection=res.getBody();
        return res.isOk();
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
