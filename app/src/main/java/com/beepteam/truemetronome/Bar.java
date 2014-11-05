package com.beepteam.truemetronome;

import android.media.SoundPool;

/**
 * Created by d.Voznenko on 11/5/2014.
 */
public class Bar {
    public static SoundPool soundPool;
    private int tempo;
    private Measure measure;
    public Bar() {
    }

    public Bar(int tempo, Measure measure) {
        this.tempo = tempo;
        this.measure = measure;
    }

    public void play(){



    }

    private void beep(int soundPoolBeepId){
        soundPool.play(soundPoolBeepId, 0.5f, 1.0f, 1, 0, 1.0f);
    }
}
