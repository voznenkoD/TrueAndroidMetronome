package com.beepteam.truemetronome;

/**
 * Created by eljetto on 11/5/2014.
 */
public class TimeSignature {
    private int numberOfBeats;
    private int noteLength;

    public TimeSignature(int numberOfBeats, int noteLength) {
        this.numberOfBeats = numberOfBeats;
        this.noteLength = noteLength;
    }

    public int getNumberOfBeats() {
        return numberOfBeats;
    }

    public void setNumberOfBeats(int numberOfBeats) {
        this.numberOfBeats = numberOfBeats;
    }

    public int getNoteLength() {
        return noteLength;
    }

    public void setNoteLength(int noteLenght) {
        this.noteLength = noteLenght;
    }
}
