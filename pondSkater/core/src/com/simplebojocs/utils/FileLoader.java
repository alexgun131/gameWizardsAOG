package com.simplebojocs.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;

public abstract class FileLoader extends InputAdapter {
    protected FileHandle loadFile(iResource resource){
        switch(resource.getResourceType()){
            case INTERNAL:
                Gdx.input.setInputProcessor(this);
                return Gdx.files.internal((resource.getPath() != null? (resource.getPath() + "/"):"") + resource.getFile());
            default:
                return null;
        }
    }
}
