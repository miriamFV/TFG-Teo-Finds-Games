package com.teo_finds_games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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


public class CongratulationsScreen implements Screen {

    private final Application app;

    private int numCoins;
    private Label bountyLabel;
    private Stage stage;
    private Texture congratulationsTexture;
    private Image textImage;

    public CongratulationsScreen(Application app){
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera)){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    System.exit(0);
                }
                return super.keyDown(keyCode);
            }
        };
        Gdx.input.setInputProcessor(stage);

        int oldNumCoins = app.getNumCoins();
        app.setNumCoins( oldNumCoins + numCoins); //add the amount to numCoins in Application

        initTextures();
        initActors();
        initLabels();

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.playScreen);
            }
        };

        textImage.setOrigin(textImage.getWidth() / 2, textImage.getHeight() / 2);
        textImage.setPosition(stage.getWidth() / 2 - textImage.getWidth()/2, stage.getHeight() + textImage.getHeight()/2);
        textImage.addAction(sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f, 2f, 2.5f, Interpolation.pow5),
                        moveTo(stage.getWidth() / 2 - textImage.getWidth()/2, stage.getHeight() / 2 - textImage.getHeight()/2, 2f, Interpolation.swing)),
                delay(0.25f), fadeOut(1.25f), run(transitionRunnable)));


    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.9f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
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
        congratulationsTexture.dispose();
    }

    public void initTextures(){
        congratulationsTexture = new Texture("images/pantallaprincipal/hasGanado.png");
    }

    public void initActors(){
        textImage = new Image(congratulationsTexture);
        textImage.setSize(app.vpWidth/2.5f, app.vpHeight/5);
        stage.addActor(textImage);
    }

    public void initLabels() {
        bountyLabel = new Label("Has ganado "+numCoins+" monedas", app.skin);
        bountyLabel.setWrap(true);
        bountyLabel.setSize(app.vpWidth/2 - bountyLabel.getWidth()/2, 150);
        bountyLabel.setFontScale(2);
        bountyLabel.setColor(Color.BLACK);
        bountyLabel.setPosition((app.vpWidth - bountyLabel.getWidth()) / 2, app.vpHeight / 2 + 150);
        stage.addActor(bountyLabel);
    }

    public int getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }

}
