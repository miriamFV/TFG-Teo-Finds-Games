package com.teo_finds_games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;


public class MapScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Texture map;
    private Image imageBackground;
    private TextButton backButton;

    public MapScreen(Application app){
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);

        map = new Texture(Gdx.files.internal("images/pantallaprincipal/map.png"));

        setBackground();
        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.9f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        app.batch.begin();
        app.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        map.dispose();
        stage.dispose();
    }

    public void setBackground(){
        //Setting background
        //You can put a background behind actors by adding the background as an Image (subclass of Actor) to the Stage and using the z-index to make sure it is drawn as a background.
        imageBackground = new Image(map);
        imageBackground.setPosition(app.camera.position.x - app.camera.viewportWidth/2, app.camera.position.y - app.camera.viewportHeight/2);
        imageBackground.setSize(app.camera.viewportWidth, app.camera.viewportHeight);

        stage.addActor(imageBackground);
    }

    public void initButtons(){
        //backButton
        backButton = new TextButton("VOLVER", Application.skin);
        backButton.setSize(110, 65);
        backButton.setPosition(app.camera.position.x + app.camera.viewportWidth/2 - backButton.getWidth() - 40, app.camera.position.y - app.camera.viewportHeight/2 + 40);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });

        stage.addActor(backButton);
    }

}
