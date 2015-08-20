package com.beepteam.truemetronome;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioGeneratorWav {

    private int sampleRate;
    private AudioTrack audioTrack;
    byte[] generatedSnd;

    public AudioGeneratorWav(Context context) {
        sampleRate = 8000;
        generateSound(context);
    }

    public void generateSound (Context context){
        ByteArrayOutputStream out;
        out = new ByteArrayOutputStream();
        InputStream in = context.getResources().openRawResource(R.raw.defaultclick); //references the wav file to in
        int read;
        byte[] buff = new byte[16000];

        try {
            out.write(0);
            for(int i = 1; i < 2000; i++){
                read = in.read();
                out.write(read);//reads the wav file and places it inside in
            }
            for (int i = 1000; i < 16000; i++) {
                out.write(0);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            out.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //creates a byte Array and stores it into audioBytes
        generatedSnd = out.toByteArray();

    }

    public void createPlayer(){
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, sampleRate,
                AudioTrack.MODE_STREAM);

        audioTrack.play();
    }

    public void writeSound() {
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
    }

    public void destroyAudioTrack() {
        audioTrack.stop();
        audioTrack.release();
    }

}

