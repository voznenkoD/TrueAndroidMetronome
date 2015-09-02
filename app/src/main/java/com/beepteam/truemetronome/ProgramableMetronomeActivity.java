package com.beepteam.truemetronome;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by dmytro on 02/09/15.
 */
public class ProgramableMetronomeActivity extends Activity {
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
        metronome.setBpm(30); //I don't know why but it is actually twice bigger

        final Button button = (Button) findViewById(R.id.SS_BTN);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isRunning = !isRunning;
                if (!isRunning) {
                    task = new MetronomeAsyncTask();
                    button.setText("Stop");
                    metronome.setPlay(true);
                    Runtime.getRuntime().gc();
                    task.execute();
                } else {
                    button.setText("Start");
                    metronome.setPlay(false);
                    task.cancel(true);
                }
            }
        });
    }

    private class MetronomeAsyncTask extends AsyncTask {
        MetronomeAsyncTask() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            metronome.play();
            return null;
        }

    }
}