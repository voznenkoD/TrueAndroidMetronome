package com.beepteam.truemetronome;

import android.media.SoundPool;

/**
 * Created by eljetto on 11/5/2014.
 */
public class Bar {
    private int tempo;
    private TimeSignature timeSignature;
    private int pause;
    private boolean isWithAccent;

    private int currentBeat = 1;
    public Bar() {
    }

    public Bar(int tempo, TimeSignature timeSignature) {
        this();
        this.tempo = tempo;
        this.timeSignature = timeSignature;
        this.pause = Constants.MINUTE_MILLISEC / tempo;
        countPauseBasedOnNoteLength();//todo refactor
    }

    public Bar(int tempo, TimeSignature timeSignature, boolean isWithAccent) {
        this(tempo,timeSignature);
        this.isWithAccent = isWithAccent;
    }

    public void play(){
            if (isWithAccent && currentBeat == 1){
                beep(2);
            } else {
                beep(1);
            }
            try {
                Thread.sleep(pause);
                if(currentBeat == timeSignature.getNumberOfBeats())currentBeat=1;
                else currentBeat++;
            } catch (Exception ignored) {
            }
    }

    private void beep(int soundPoolBeepId){
        SoundManager.soundPool.play(soundPoolBeepId, 0.5f, 1.0f, 1, 0, 1.0f);
    }

    private void countPauseBasedOnNoteLength(){
        switch (timeSignature.getNoteLength()) {
            case 1:
                pause = pause * 4;
                break;
            case 2:
                pause = pause * 2;
                break;
            case 8:
                pause = pause / 2;
                break;
            case 16:
                pause = pause / 4;
                break;
            case 32:
                pause = pause / 8;
                break;
            case 64:
                pause = pause / 16;
                break;
        }
    }
}
