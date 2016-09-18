package com.simplebojocs.utils.Scene;

import com.badlogic.gdx.math.Vector2;

public interface iClickable {
    boolean isClicked(Vector2 worldTouch);
    void click(Vector2 worldTouch);
}
