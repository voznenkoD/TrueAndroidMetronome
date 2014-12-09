package com.beepteam.truemetronome;

/**
 * Created by d.Voznenko on 12/9/2014.
 */
public abstract class Bar {
    protected int tempo;
    protected TimeSignature timeSignature;
    protected int pause;
    protected boolean isWithAccent;
    protected int currentBeat = 1;
    protected long before = 0L;
    protected long after = 0L;

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

    public void play(){

    }


    public int getPause() {
        return pause;
    }

}
