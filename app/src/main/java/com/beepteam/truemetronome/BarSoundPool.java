package com.beepteam.truemetronome;

/**
 * Created by eljetto on 11/5/2014.
 */
public class BarSoundPool extends Bar {

    public BarSoundPool(int tempo, TimeSignature timeSignature) {
        super(tempo, timeSignature);
    }

    @Override
    public void play() {
        before = System.currentTimeMillis();
        if (isWithAccent && currentBeat == 1) {
            beep(2);
        } else {
            beep(1);
        }
        after = System.currentTimeMillis();
        try {
            Thread.sleep(pause - (after - before));
            if (currentBeat == timeSignature.getNumberOfBeats()) currentBeat = 1;
            else currentBeat++;
        } catch (Exception ignored) {
        }
    }

    private void beep(int soundPoolBeepId) {
        SoundManager.soundPool.play(soundPoolBeepId, 0.5f, 1.0f, 1, 0, 1.0f);
    }
}
