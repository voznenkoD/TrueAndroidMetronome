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

        metronome.setBpm(30); //I don't know why but it is actually twice bigger

        final Button button = (Button) findViewById(R.id.SS_BTN);




        final EditText mEditBpm = (EditText)findViewById(R.id.bpmValue);
        final EditText mEditMeasure = (EditText)findViewById(R.id.measureValue);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isRunning = !isRunning;
                if (!isRunning) {
                    String valueBPM = mEditBpm.getText().toString();
                    int valueMeasure = Integer.parseInt(mEditMeasure.getText().toString());
                    if (!valueBPM.equals("")) {
                        metronome.setBpm(Double.parseDouble(valueBPM)/2);
                    } else {
                        metronome.setBpm(60);
                    }
                    metronome.setBeatsInBar(valueMeasure);
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
