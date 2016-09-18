package com.simplebojocs.utils.audio;

import com.simplebojocs.utils.iResource;

public interface iAudio extends iResource {
    enum AudioType{
        BGM, SFX
    }
    AudioType getAudioType();
    boolean loop();
}
