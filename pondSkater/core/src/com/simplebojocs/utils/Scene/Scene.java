package com.simplebojocs.utils.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public abstract class Scene extends InputAdapter implements Screen {
    private final Set<iRenderable> renderableList;
    private final Set<iUpdatable> updatableList;
    private final Set<iClickable> clickableList;
    private final Set<iDisposable> disposableList;

    private final SpriteBatch batch;
    protected final ExtendViewport viewport;
    public  boolean centerCamera = false;

    public boolean paused = false;

    public Scene(float width, float height){
        renderableList = new TreeSet<iRenderable>(new Comparator<iRenderable>() {
            @Override
            public int compare(iRenderable r1, iRenderable r2) {
                if(r1 == r2) return 0;
                if(r1.getDepth() == null) return -1;
                if(r2.getDepth() == null) return 1;
                if(r1.getDepth() < r2.getDepth()) return 1;
                if(r1.getDepth() > r2.getDepth()) return -1;
                return 1;
            }
        });
        updatableList = new HashSet<iUpdatable>();
        clickableList = new HashSet<iClickable>();
        disposableList = new HashSet<iDisposable>();


        viewport = new ExtendViewport(width, height);
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public final void render(float delta) {
        if(!paused)
            doUpdate(delta);
        doRender(delta);
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        for(iClickable clickable:clickableList)
            if(clickable.isClicked(worldTouch))
                clickable.click(worldTouch);

        return false;
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override
    public void dispose() {
        for(iDisposable disposable : disposableList)
            disposable.dispose();
        batch.dispose();
    }
    public void dispose(iDisposable disposable) {
        disposable.dispose();
        disposableList.remove(disposable);
    }

    protected void doUpdate(float delta){
        for(iUpdatable updatable : updatableList)
            if(!updatable.update(delta))
                updatableList.remove(updatable);
    }
    protected void doRender(float delta){
        renderInit();

        Collection<iRenderable> updatedDepthList = new ArrayList<iRenderable>();
        for(iRenderable renderable : renderableList){
            if(renderable.render(delta, batch)){
                renderableList.remove(renderable);
                updatedDepthList.add(renderable);
            }
        }

        for(iRenderable element : updatedDepthList)
            addRenderable(element);

        renderEnd();
    }
    protected void renderInit(){
        viewport.apply(centerCamera);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
    }
    protected void renderEnd(){
        batch.end();
    }

    public void add(Object element){
        if(element instanceof  iRenderable)
            addRenderable((iRenderable) element);
        if(element instanceof iUpdatable)
            addUpdatable((iUpdatable) element);
        if(element instanceof iClickable)
            addClickable((iClickable) element);
        if(element instanceof iDisposable)
            addDisposable((iDisposable) element);
    }
    public void addRenderable(iRenderable renderable){
        renderableList.add(renderable);
    }
    public void addUpdatable(iUpdatable updatable){
        updatableList.add(updatable);
    }
    public void addClickable(iClickable clickable){
        clickableList.add(clickable);
    }
    public void addDisposable(iDisposable disposable){
        disposableList.add(disposable);
    }
}
