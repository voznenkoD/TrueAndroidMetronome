package com.beepteam.truemetronome;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.Objects;

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

        metronome.setBpm(60); //I don't know why but it is actually twice bigger

        final Button button = (Button) findViewById(R.id.SS_BTN);
        final TextView currentBPM = (TextView) findViewById(R.id.currentBPM);


        RotaryKnobView jogView = (RotaryKnobView)findViewById(R.id.jogView);
        jogView.setKnobListener(new RotaryKnobView.RotaryKnobListener() {
            @Override
            public void onKnobChanged(int arg) {
                if (arg > 0) {
                    metronome.setBpm(metronome.getBpm() + 1);
                    currentBPM.setText(String.valueOf(metronome.getBpm()*2));
                } else {
                    metronome.setBpm(metronome.getBpm() - 1);
                    currentBPM.setText(String.valueOf(metronome.getBpm()*2));
                }

            }});


            final Spinner measureSpinner = (Spinner) findViewById(R.id.measureValue);

            button.setOnClickListener(new View.OnClickListener()

            {
                public void onClick (View v){
                isRunning = !isRunning;
                metronome.setBeatsInBar(Integer.parseInt(measureSpinner.getSelectedItem().toString().split("/")[0]));
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
            }

            );
        }

        @Override
    protected void onPause(){
        super.onPause();
        task.cancel(true);
        metronome.stop();
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
