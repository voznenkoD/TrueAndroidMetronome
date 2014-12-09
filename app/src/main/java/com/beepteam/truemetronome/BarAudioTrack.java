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
public class BarAudioTrack extends Bar {

    private AudioTrack track;
    byte[] soundBytes = null;

    public BarAudioTrack(int tempo, TimeSignature timeSignature, Context context) {
        super(tempo, timeSignature);
        try {
            initAudioTrack(context);
        } catch (IOException e) {
            e.printStackTrace();//todo proper exception handling
        }
    }

    @Override
    public void play() {

        if (isWithAccent && currentBeat == 1) {
        } else {
            before = System.currentTimeMillis();
            track.play();
            after = System.currentTimeMillis();
        }
        try {
            Thread.sleep(pause - (after - before));
            if (currentBeat == timeSignature.getNumberOfBeats()) currentBeat = 1;
            else currentBeat++;
        } catch (Exception ignored) {
        }
    }


    private void initAudioTrack(Context context) throws IOException {
        int stream = AudioManager.STREAM_MUSIC;
        int rate = 44100;
        int channel = AudioFormat.CHANNEL_OUT_STEREO;
        int format = AudioFormat.ENCODING_PCM_16BIT;
        int mode = AudioTrack.MODE_STATIC;
        int b;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream input = context.getResources().openRawResource(R.raw.defaultclick);

        while ((b = input.read()) != -1) {
            out.write(b);
        }

        input.close();
        soundBytes = out.toByteArray();
        int bufferSize = AudioTrack.getMinBufferSize(rate, channel, format);
        track = new AudioTrack(stream, rate, channel, format, bufferSize, mode);
        track.write(soundBytes, 0, soundBytes.length);
    }
}
