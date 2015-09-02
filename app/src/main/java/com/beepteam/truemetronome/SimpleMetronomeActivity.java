package com.beepteam.truemetronome;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        metronome.setBpm(30); //I don't know why but it is actually twice bigger

        final Button button = (Button) findViewById(R.id.SS_BTN);

        EditText mEditBpm = (EditText)findViewById(R.id.bpmValue);

        mEditBpm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input;
                input = v.getText().toString();
                metronome.setBpm(Double.parseDouble(input));
                return true; // consume.
            }
        });


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

        EditText mEditMeasure = (EditText)findViewById(R.id.measureValue);

        mEditMeasure.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input;
                input = v.getText().toString();
                metronome.setBeatsInBar(Integer.parseInt(input));
                return true; // consume.
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
