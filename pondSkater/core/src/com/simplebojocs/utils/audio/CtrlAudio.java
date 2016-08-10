package com.simplebojocs.utils.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.simplebojocs.utils.FileLoader;

public class CtrlAudio extends FileLoader {
    private static CtrlAudio instance;

    /**
     * Indica si se debe eliminar el anterior BGM si se carga correctamente el nuevo BGM
     */
    public boolean disposeOnLoadBGM = false;

    public final PlayList BGM;
    public final PlayList SFX;

    private float volume;

    private boolean muted;

    private iAudio playingBGM;

    public static CtrlAudio getInstance(){
        if(instance == null)
            instance = new CtrlAudio();
        return instance;
    }

    private CtrlAudio(){
        BGM = new PlayList();
        SFX = new PlayList();
        volume = 1;
        muted = false;
    }

    public boolean play(){
        return play(playingBGM);
    }
    public boolean pause(){
        return pause(playingBGM);
    }
    public boolean stop(){
        return stop(playingBGM);
    }
    public boolean remove(){
        return remove(playingBGM);
    }

    public boolean play(iAudio audio){
        PlayList playlist = getPlayList(audio);

        if(playlist == null)
            return false;

        return playlist.play(audio);
    }
    public boolean pause(iAudio audio){
        PlayList playlist = getPlayList(audio);

        if(playlist == null)
            return false;

        return playlist.pause(audio);
    }
    public boolean stop(iAudio audio){
        PlayList playlist = getPlayList(audio);

        if(playlist == null)
            return false;

        return playlist.stop(audio);
    }
    public boolean remove(iAudio audio){
        PlayList playlist = getPlayList(audio);

        if(playlist == null)
            return false;

        boolean ret = playlist.remove(audio);
        if(audio == playingBGM)
            playingBGM = null;

        return ret;
    }

    /**
     * Genera una instancia de music
     * @param audio
     * @return
     */
    public Music load(iAudio audio){
        Music music = null;
        FileHandle audioFile = loadFile(audio);
        if(audioFile != null){
            music =  Gdx.audio.newMusic(audioFile);
            music.setLooping(audio.loop());
        }
        return music;
    }

    /**
     * Carga el audio si no esta cargado
     * @param audio
     * @param autoPlay
     * @return se ha cargado correctamente o ya estaba cargado
     */
    public boolean loadAudio(iAudio audio, boolean autoPlay){
        return loadAudio(audio, autoPlay, null);
    }

    public boolean loadAudio(iAudio audio, boolean autoPlay, Music.OnCompletionListener finishListener){
        PlayList playlist = getPlayList(audio);

        if(playlist == null)
            return false;

        if(!playlist.contains(audio)) {
            Music music = load(audio);
            if(finishListener != null)
                music.setOnCompletionListener(finishListener);

            if (music == null)
                return false;

            playlist.add(audio, music);
        }

        if(audio.getAudioType() == iAudio.AudioType.BGM && playingBGM != audio){
            if(disposeOnLoadBGM)
                playlist.remove(playingBGM);
            playingBGM = audio;
        }


        if(autoPlay)
            playlist.play(audio);

        return true;
    }

    public void clear(){
        playingBGM = null;
        BGM.clearList();
        SFX.clearList();
    }

    public float getVolume(){
        return volume;
    }
    public void setVolume(float volume){
        this.volume = volume;
        BGM.syncVolume();
        SFX.syncVolume();
    }
    public boolean isMuted(){
        return muted;
    }
    public void setMuted(boolean muted){
        this.muted = muted;
        BGM.syncVolume();
        SFX.syncVolume();
    }

    private PlayList getPlayList(iAudio audio) {
        if(audio == null)
            return null;

        switch (audio.getAudioType()) {
            case BGM:
                return BGM;
            case SFX:
                return SFX;
            default:
                return null;
        }
    }
}
