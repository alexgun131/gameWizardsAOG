package com.simplebojocs.pondskater.utils;

import com.simplebojocs.utils.audio.iAudio;

public enum Audio implements iAudio {
    MOSKITO     ("Kito_the_Moskito.mid", AudioType.SFX, true),
    EAT_LARVAE  ("eatLarvae.mp3",        AudioType.SFX, false),
    EAT_MOSKITO ("eatMoskito.mp3",       AudioType.SFX, false),
    POND_SKATER ("PondSkaterTheme.mid",  AudioType.BGM, true),
    DEATH       ("Death_sound_2.mp3",    AudioType.SFX, false),
    DEATH_THEME ("Death_theme_2.mid",    AudioType.BGM, true);

    private final String file;
    AudioType audioType;
    boolean loop;
    Audio(String file,  AudioType audioType, boolean loop){
        this.file = file;
        this.audioType = audioType;
        this.loop = loop;
    }

    @Override
    public String getPath(){
        return null;
    }
    @Override
    public String getFile(){
        return file;
    }
    @Override
    public ResourceType getResourceType(){
        return ResourceType.INTERNAL;
    }
    @Override
    public AudioType getAudioType(){
        return audioType;
    }
    @Override
    public boolean loop(){
        return loop;
    }
}
