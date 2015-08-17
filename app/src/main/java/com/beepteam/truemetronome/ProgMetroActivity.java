package com.beepteam.truemetronome;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.sergienko on 12/23/2014.
 */
public class ProgMetroActivity extends Activity {
    private Boolean isRunning = false;
    private Bar bar;
    private List<Bar> bars;
    private MetronomeAsyncTask task = new MetronomeAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bars = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog_metronome);
        final Button button = (Button) findViewById(R.id.SS_BTN);
        final Button addBarBtn = (Button) findViewById(R.id.add_bars);
        addBarBtn.setOnClickListener(listenerToAddBar);
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
                    task.execute(bars.toArray(new Bar[bars.size()]));
                }
            }
        });
        //bar = new BarAudioTrack(160,Constants.DEFAULT_TIME_SIGNATURE,this.getApplicationContext());
        bar = new BarSoundPool(160,Constants.DEFAULT_TIME_SIGNATURE);

    }

    private class MetronomeAsyncTask extends AsyncTask<Bar,Void,String> {
        MetronomeAsyncTask() {

        }
        @Override
        protected String doInBackground(Bar... bars) {
            long before;
            long after;
            double avg = 0L;
            double count = 0;
            long maxDelay = Integer.MIN_VALUE;
            long minDelay = Integer.MAX_VALUE;
            long delay;
            while(isRunning){
                for (Bar bar : bars) {
                    before = System.currentTimeMillis();
                    bar.play();
                    after = System.currentTimeMillis();
                    delay = after - before - bar.getPause();
                    if(delay < minDelay) minDelay = delay;
                    else if(delay > maxDelay) maxDelay = delay;
                    count++;
                    avg+=delay;
                }
            }
            System.out.println("MAX DELAY" + maxDelay + "   MIN DELAY" + minDelay);
            System.out.println("AVG DELAY" + avg/count);
            return null;
        }
        public void stop() {

        }
    }

    private void addBar(Bar bar){
        bars.add(bar);
        Button myButton = new Button(this);
        myButton.setText(bar.timeSignature.getNoteLength() +"/"+ bar.timeSignature.getNumberOfBeats() + " Chorus");
        LinearLayout ll = (LinearLayout)findViewById(R.id.bars_layout);
        ActionMenuView.LayoutParams lp = new ActionMenuView.LayoutParams(ActionMenuView.LayoutParams.MATCH_PARENT, ActionMenuView.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
    }

    private View.OnClickListener listenerToAddBar = new View.OnClickListener() {
        public void onClick(View v) {
            addBar(new BarSoundPool(160,Constants.DEFAULT_TIME_SIGNATURE));
        }
    };



}
