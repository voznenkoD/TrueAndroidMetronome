package com.beepteam.truemetronome;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by eljetto on 11/5/2014.
 */
public class BarAudioTrack {
    private int tempo;
    private TimeSignature timeSignature;
    private int pause;
    private boolean isWithAccent;
    long before;
    long after;

    private int stream = AudioManager.STREAM_MUSIC;
    private int rate = 44100;
    private int channel = AudioFormat.CHANNEL_OUT_STEREO;
    private int format = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferSize = 4096;
    private int mode = AudioTrack.MODE_STREAM;
    private AudioTrack track;
    byte[] soundBytes = null;
    Context context;

    private int currentBeat = 1;
    public BarAudioTrack() {
    }

    public BarAudioTrack(int tempo, TimeSignature timeSignature) {
        this();
        this.tempo = tempo;
        this.timeSignature = timeSignature;
        this.pause = Constants.MINUTE_MILLISEC / tempo;
    }

    public BarAudioTrack(int tempo, TimeSignature timeSignature, Context context) throws IOException {
        this(tempo,timeSignature);
        this.context = context;
        soundBytes = this.load(R.raw.defaultclick);
        bufferSize = AudioTrack.getMinBufferSize(rate, channel, format);
        track = new AudioTrack(stream, rate, channel, format, bufferSize, mode);
        track.play();
    }

    public BarAudioTrack(int tempo, TimeSignature timeSignature, boolean isWithAccent) {
        this(tempo,timeSignature);
        this.isWithAccent = isWithAccent;
    }

    public void play(){
            if (isWithAccent && currentBeat == 1){
               // SoundManager.soundPool.play(2, 0.5f, 1.0f, 1, 0, 1.0f);
            } else {
               // SoundManager.soundPool.play(1, 0.5f, 1.0f, 1, 0, 1.0f);
                track.write(soundBytes, 0, soundBytes.length);
            }
            try {
                before = System.currentTimeMillis();
                Thread.sleep(pause);
                after = System.currentTimeMillis();
                if(currentBeat == timeSignature.getNumberOfBeats())currentBeat=1;
                else currentBeat++;
            } catch (Exception ignored) {
            }
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


    private byte[] load(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        this.copy(in, out);

        return out.toByteArray();
    }
    private byte[] load(int resId) throws IOException {
        byte[] output = null;

        InputStream input = context.getResources().openRawResource(resId);
        output = this.load(input);
        input.close();

        return output;
    }
    private void copy(InputStream in, OutputStream out) throws IOException {
        int b;

        while ((b = in.read()) != -1) {
            out.write(b);
        }
    }


    public int getPause() {
        return pause;
    }
}
