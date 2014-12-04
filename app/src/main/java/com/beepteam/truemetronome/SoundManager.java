package com.beepteam.truemetronome;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by eljetto on 11/5/2014.
 */
public class SoundManager {
    public static HashMap metronomeSounds = new HashMap();
    public static SoundPool soundPool;


    static {
        initSoundPool();
    }

    public static void loadSoundPackageToPool(Context context, SoundPackages soundPackage){
        //description of soundpackages in some props file
        String oneFromPackage = null;
        loadSoundToPool(context, oneFromPackage);
    }

    public static void loadSoundToPool(Context context, String soundName) {
        int id = soundPool.load(getFileDescriptorFromContext(context, soundName), 1);
        metronomeSounds.put(soundName, id);
    }

    private static void initSoundPool(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().build();
        } else {
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        }
    }

    private static AssetFileDescriptor getFileDescriptorFromContext(Context context, String soundName){
        try {
            return context.getAssets().openFd(soundName);
        } catch (IOException e) {

            //TODO LOGGER
        }
        return null;
    }

    //todo sound.release(); onCloseOfApplication to release the sources
}
