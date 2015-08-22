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
    private Boolean isRunning = true;
    private MetronomeAsyncTask task = new MetronomeAsyncTask();
    public Metronome metronome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_metronome);
        try {
            metronome = new Metronome(this.getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        metronome.setBpm(100);

        final Button button = (Button) findViewById(R.id.SS_BTN);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isRunning = !isRunning;
                if(!isRunning){
                    task = new MetronomeAsyncTask();
                    button.setText("Stop");
                    metronome.setPlay(true);
                    Runtime.getRuntime().gc();
                    task.execute();
                }
                else{
                    button.setText("Start");
                    metronome.setPlay(false);
                    task.cancel(true);
                }
            }
        });
    }

    private class MetronomeAsyncTask extends AsyncTask<Bar,Void,String> {
        MetronomeAsyncTask() {

        }
        @Override
        protected String doInBackground(Bar... bars) {
                metronome.play();
            return null;
        }
    }
}
