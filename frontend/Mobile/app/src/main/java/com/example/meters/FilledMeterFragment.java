package com.example.meters;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class FilledMeterFragment extends Fragment {
    public static final String POS = "";
    int maxPos=0;
    Singleton singleton = Singleton.INSTANCE;
    TextView name;
    TextView description;
    TextView data;
    TextView date;
    TextView listData;
    Button send;
    Button delete;

    Button create;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();

        maxPos = singleton.meters.meterCollection.size();
        int curPos = args.getInt(POS);
        View view;
        if(curPos<maxPos)
        {
            view = inflater.inflate(R.layout.fragment, null);
            name = (TextView) view.findViewById(R.id.textViewName);
            description = (TextView) view.findViewById(R.id.textViewDescription);
            data = (TextView) view.findViewById(R.id.editTextData);
            date = (TextView) view.findViewById(R.id.editTextDate);
            listData = (TextView) view.findViewById(R.id.textViewListData);

            Meter m = singleton.meters.meterCollection.get(curPos);
            name.setText(m.name);
            description.setText(m.description);
            data.setText(m.lastData);
            date.setText(m.verificationDate);
            listData.setText(m.dataListAsText());

            send=(Button) view.findViewById(R.id.btnSend);
            delete=(Button) view.findViewById(R.id.btnDelete);

            send.setOnClickListener((v)->{
                Meter meter = singleton.meters.meterCollection.get(curPos);
                try {
                    meter.post(data.getText().toString(),date.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            delete.setOnClickListener((v)->{
                Meter meter = singleton.meters.meterCollection.get(curPos);
                try {
                    meter.delete();
                    singleton.meters.meterCollection.remove(curPos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_new, null);

            name = (TextView) view.findViewById(R.id.textViewName_new);
            description = (TextView) view.findViewById(R.id.textViewDescription_new);
            data = (TextView) view.findViewById(R.id.editTextData_new);
            date = (TextView) view.findViewById(R.id.editTextDate_new);
            create= (Button) view.findViewById(R.id.btnCreate);

            create.setOnClickListener((v)-> {
                String n = name.getText().toString();
                String desc =  description.getText().toString();
                String dataString =  data.getText().toString();
                String verDate =  date.getText().toString();
                Meter m = new Meter(n,desc,dataString,verDate);
                try {
                    singleton.meters.append(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }


}
