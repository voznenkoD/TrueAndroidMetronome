package com.beepteam.truemetronome;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 * Created by a.sergienko
 */
public class SimpleMetronomeActivity extends Activity {
    private Boolean isRunning = false;
    private Bar bar = new Bar(120,Constants.DEFAULT_TIME_SIGNATURE);
    private MetronomeAsyncTask task = new MetronomeAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_metronome);


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
                    task.execute(bar);
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
                for (Bar bar : bars) {
                    bar.play();
                }
            }
            return null;
        }
        public void stop() {

        }
    }
}
