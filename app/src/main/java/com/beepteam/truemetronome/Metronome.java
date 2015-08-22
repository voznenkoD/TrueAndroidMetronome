package com.beepteam.truemetronome;

import android.content.Context;

import java.io.IOException;

/**
 * Created by eljetto on 8/17/15.
 */
public class Metronome {
    private double bpm;
    private int beat;
    private int silence;
    private int bufferSize;

    private boolean play = true;

    private final int tick;

    private AudioGeneratorWav audioGenerator;


    public Metronome(Context context) throws IOException {
        audioGenerator = new AudioGeneratorWav(context);
        audioGenerator.createPlayer();
        tick = audioGenerator.getWaveInfo().getDataSize();
        bufferSize = tick * 2;
    }

    public void calcSilence() {
        silence = (int) (((60/bpm)*bufferSize)-tick);
    }

    public void play() {
        calcSilence();

        byte[] sound = new byte[bufferSize];
        byte[] ding = audioGenerator.getSound();

        int t = 0,s = 0,b = 0;
        do {
            for(int i=0;i<sound.length&&play;i++) {
                if(t<this.tick) {
                    sound[i] = ding[t];
                    t++;
                } else {
                    sound[i] = 0;
                    s++;
                    if(s >= this.silence) {
                        t = 0;
                        s = 0;
                        b++;
                        if(b > (this.beat-1))
                            b = 0;
                    }
                }
            }
            audioGenerator.writeSound(sound);
        } while(play);
    }

    public void stop() {
        play = false;
        audioGenerator.destroyAudioTrack();
    }

    public void setBpm(double bpm) {
        this.bpm = bpm;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }
}
