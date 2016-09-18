package com.simplebojocs.pondskater.utils;

import com.simplebojocs.utils.lang.iLanguage;

public enum Language implements iLanguage {
    en_US ("en_US.properties", "en_US", "English"),
    es_ES ("es_ES.properties", "es_ES", "Castellano"),
    zh_CN ("zh_CN.properties", "zh_CN", "中文"),
    ja_JP ("ja_JP.properties", "ja_JP", "日本の"),
    ko_KR ("ko_KR.properties", "ko_KR", "한국의"),
    ar_SA ("ar_SA.properties", "ar_SA", "No Puedorrr con arabe");

    

    private final String file;
    private final String locale;
    private final String name;
    Language(String file, String locale, String name){
        this.file = file;
        this.locale = locale;
        this.name = name;
    }
    @Override
    public String getPath(){
        return "data/";
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
    public String getLocale(){
        return locale;
    }
    @Override
    public String getName(){
        return name;
    }
}
