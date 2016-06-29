package com.teo_finds_games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class SplashScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Texture titleTexture;
    private Image titleImage;

    public SplashScreen(Application app){
        this.app = app;
    }


    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        initTextures();
        initImages();
    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.9f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();

        app.batch.begin();
        app.batch.end();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
        stage.dispose();
        titleTexture.dispose();
    }

    public void initTextures(){
        titleTexture = new Texture("images/pantallaprincipal/titleGame.png");
    }

    public void initImages(){
        titleImage = new Image(titleTexture);
        titleImage.setSize(app.vpWidth/2.5f, app.vpHeight/3);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.playHTPS);
            }
        };

        titleImage.setOrigin(titleImage.getWidth() / 2, titleImage.getHeight() / 2);
        titleImage.setPosition((app.vpWidth-titleImage.getWidth())/2, app.vpHeight/2 + titleImage.getHeight()/2);
        titleImage.addAction(sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f, 2f, 2.5f, Interpolation.pow5),
                        moveTo(stage.getWidth() / 2 - titleImage.getWidth()/2, stage.getHeight() / 2 - titleImage.getHeight()/2, 2f, Interpolation.swing)),
                delay(1.5f), fadeOut(1.25f), run(transitionRunnable)));

        stage.addActor(titleImage);
    }
}

