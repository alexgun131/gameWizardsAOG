package com.simplebojocs.utils.Scene;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface iRenderable {
    /**
     *
     * @return si se va a cambiar el depth para el siguiente render
     * @param delta
     * @return
     */
    boolean render(float delta, SpriteBatch batch);

    /**
     * Cuanto mas grande sea el numero, más al fondo se encuentra
     * @return depth, si es nulo se elimina de la Scene
     */
    Float getDepth();
}
