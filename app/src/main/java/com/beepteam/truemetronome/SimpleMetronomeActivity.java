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
        bar = new BarAudioTrack(160,Constants.DEFAULT_TIME_SIGNATURE,this.getApplicationContext());
        //bar = new BarSoundPool(160,Constants.DEFAULT_TIME_SIGNATURE);

    }

    private class MetronomeAsyncTask extends AsyncTask<Bar,Void,String> {
        MetronomeAsyncTask() {

        }
        @Override
        protected String doInBackground(Bar... bars) {
            long before;
            long after;
            long maxDelay = Integer.MIN_VALUE;
            long minDelay = Integer.MAX_VALUE;
            long delay = 0;
            while(isRunning){
                for (Bar bar : bars) {
                    //before = System.currentTimeMillis();
                    bar.play();
                    //after = System.currentTimeMillis();
                    //delay = after - before - bar.getPause();
                    //if(delay < minDelay) minDelay = delay;
                    //else if(delay > maxDelay) maxDelay = delay;
                }
            }
            System.out.println("MAX DELAY" + maxDelay + "   MIN DELAY" + minDelay);
            return null;
        }
        public void stop() {

        }
    }
}
