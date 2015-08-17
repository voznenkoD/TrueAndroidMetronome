package com.beepteam.truemetronome;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/*
 * Created by a.sergienko
 */
public class SimpleMetronomeActivity extends Activity {
    private Boolean isRunning = false;
    private Bar bar;
    private MetronomeAsyncTask task = new MetronomeAsyncTask();
    public Metronome metronome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_metronome);
        metronome = new Metronome();
        metronome.setBpm(160);

        final Button button = (Button) findViewById(R.id.SS_BTN);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isRunning = !isRunning;
                if(!isRunning){
                    button.setText("Start");
                    task = new MetronomeAsyncTask();
                    Runtime.getRuntime().gc();
                }
                else{
                    button.setText("Stop");
                    task.execute();
                }
            }
        });
    }

    private class MetronomeAsyncTask extends AsyncTask<Bar,Void,String> {
        MetronomeAsyncTask() {

        }
        @Override
        protected String doInBackground(Bar... bars) {
            while(isRunning){
                metronome.play();
            }
            return null;
        }
    }
}
