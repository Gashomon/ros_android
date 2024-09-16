package com.example.myplaces;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button conection;
    Socket s;
    PrintWriter writer;
    DataInputStream reader;
    TextView number;
    int i = 0;
    String send;
    String get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conection = (Button) findViewById(R.id.conection);
        number = (TextView) findViewById(R.id.number);

        onBtnClick();
    }

    public void onBtnClick() {

        conection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                BackGroundTask b1 = new BackGroundTask();
                b1.execute();
            }
        });
    }

    class BackGroundTask extends AsyncTask<String, Void, Void> {

        Handler h = new Handler();
        @Override
        protected Void doInBackground(String... voids) {
//            Log.i("i", "hello");
            try {
                if(s == null){
                    //change it to your IP
                    s = new Socket("192.168.137.94",6000);
                    writer = new PrintWriter(s.getOutputStream());
                    reader = new DataInputStream(
                            new BufferedInputStream(s.getInputStream()));
                    Log.i("i", "CONNECTED");    
                }
                if ((i%5) == 0) {
                    send = "fifth";
                    get = "fifth";
                }
                else{
                    send = String.valueOf(i);
//                    get = reader.readLine();
                }
                if(i> 10){s.close();}
                writer.write(send);
                writer.flush();
//                writer.close();
                i = i+1;
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        number.setText(get);
//                        Log.i("i", "lol");
                    }
                });
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
