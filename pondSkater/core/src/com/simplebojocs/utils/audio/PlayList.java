package com.simplebojocs.utils.audio;

import com.badlogic.gdx.audio.Music;

import java.util.HashMap;
import java.util.Map;

public class PlayList {
    private Map<iAudio, Music> playlist;
    private float volume;
    private boolean muted;

    public PlayList(){
        playlist = new HashMap<iAudio, Music>();
        volume = 1;
        muted = false;
    }

    public boolean play(iAudio audio){
        Music music = playlist.get(audio);
        if(music != null)
            music.play();
        return music != null;
    }
    public boolean pause(iAudio audio){
        Music music = playlist.get(audio);
        if(music != null)
            music.pause();
        return music != null;
    }
    public boolean stop(iAudio audio){
        Music music = playlist.get(audio);
        if(music != null)
            music.stop();
        return music != null;
    }

    public boolean contains(iAudio audio){
        return playlist.containsKey(audio);
    }
    public void add(iAudio audio, Music music){
        CtrlAudio cAudio = CtrlAudio.getInstance();
        playlist.put(audio, music);
        music.setVolume((!cAudio.isMuted() && !muted) ? (cAudio.getVolume() * volume) : 0.0f);
    }
    public boolean remove(iAudio audio){
        Music music = playlist.remove(audio);
        if(music != null)
            music.dispose();
        return music != null;
    }
    public void clearList(){
        for(Map.Entry<iAudio, Music> entry: playlist.entrySet())
            entry.getValue().dispose();
        playlist.clear();
    }

    public float getVolume(){
        return volume;
    }
    public void setVolume(float volume){
        this.volume = volume;
        syncVolume();
    }
    public boolean isMuted(){
        return muted;
    }
    public void setMuted(boolean muted){
        this.muted = muted;
        syncVolume();
    }
    public void syncVolume(){
        CtrlAudio cAudio = CtrlAudio.getInstance();
        for(Map.Entry<iAudio, Music> entry: playlist.entrySet())
            entry.getValue().setVolume((!cAudio.isMuted() && !muted) ? (cAudio.getVolume() * volume) : 0.0f);
    }
}
