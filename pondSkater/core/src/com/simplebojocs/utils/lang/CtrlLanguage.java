package com.simplebojocs.utils.lang;

import com.simplebojocs.utils.PropertiesReader;

public class CtrlLanguage {
    private static CtrlLanguage instance;

    private PropertiesReader properties;
    private iLanguage lang;

    public static CtrlLanguage getInstance(){
        if(instance == null)
            instance = new CtrlLanguage();
        return instance;
    }

    private CtrlLanguage(){
        properties = new PropertiesReader();
    }
    public void setLanguage(iLanguage lang){
        if(properties.load(lang))
            this.lang = lang;
    }
    public iLanguage getLanguage(){
        return lang;
    }
    public String get(String key, String... params){
        return properties.get(key, params);
    }
}
