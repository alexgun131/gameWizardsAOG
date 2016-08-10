package com.simplebojocs.utils;

import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Properties;

public class PropertiesReader extends FileLoader {
    Properties properties;
    iResource resource;

    public PropertiesReader(){
        properties = new Properties();
    }

    /**
     * Carga en memoria las properties
     * @param resource
     * @return Se ha cargado correctamente
     */
    public boolean load(iResource resource){
        unload();
        Reader reader = null;
        FileHandle file = loadFile(resource);

        if(file == null)
            return false;

        try{
            reader = file.reader();
            properties.load(reader);
            this.resource = resource;
            return true;
        }catch(IOException e){
            return false;
        }finally{
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {}
        }
    }

    /**
     * Recarga las properties con los valores de disco
     * @return Se ha cargado correctamente
     */
    public boolean reload(){
        return load(resource);
    }

    /**
     * Elimina las properties de memoria
     */
    public void unload(){
        properties.clear();
    }

    /**
     * Obtiene el parametro de la property cambiando las variables por los parametros pasados
     * Los parametros en las properties van en formato {N} empezado con N = 0
     * @param key
     * @param param
     * @return
     */
    public String get(String key, String... param){
        return MessageFormat.format(properties.getProperty(key, key), param);
    }

    /**
     * Guarda el valor en las properties (NO GUARDA EN DISCO)
     * @param key
     * @param value
     */
    public void set(String key, String value){
        properties.setProperty(key, value);
    }

    /**
     * Guarda las properties
     * @param title
     * @return
     */

    public boolean save(String title){
        return save(title, resource);
    }

    /**
     * Guarda las properties (con los valores modificados) en el recurso seleccionado
     * @param title
     * @param resource
     * @return
     */
    public boolean save(String title, iResource resource){
        if(resource.getResourceType() == iResource.ResourceType.INTERNAL) {
            return false;
        }

        //NOT TESTED, Solo tenemos de tipo internal y estos no se pueden modificar asi que el codigo siguiente no se ha testeado
        FileHandle file = loadFile(resource);
        Writer writer = null;

        if(file == null)
            return false;

        try{
            writer = file.writer(false);
            properties.store(writer, title);
            return true;
        }catch(IOException e){
            return false;
        }catch(Exception e){
            return false;
        }finally{
            try {
                if(writer != null)
                    writer.close();
            } catch (IOException e) {}
        }
    }
}
