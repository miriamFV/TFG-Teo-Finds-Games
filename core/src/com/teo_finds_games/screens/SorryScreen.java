package com.teo_finds_games.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SorryScreen implements Screen {

    private final Application app;
    private Stage stage;
    private SpriteBatch batch;
    private Screen screen;
    private Texture sorryTexture;
    private Image sorryImage;
    private Label sorryLabel;
    private TextButton exitButton, playAgainButton;

    public SorryScreen(Application app, Screen screen){
        this.app = app;
        this.screen = screen;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        initTextures();
        initImages();
        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.9f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        batch.begin();
        batch.end();
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
        stage.dispose();
        sorryTexture.dispose();
    }

    public void initTextures(){
        sorryTexture = new Texture(Gdx.files.internal("images/pantallaprincipal/hasPerdido.png"));
    }

    public void initImages(){
        sorryImage = new Image(sorryTexture);
        sorryImage.setSize(app.vpWidth/2.5f, app.vpHeight/3);

        sorryImage.setOrigin(sorryImage.getWidth() / 2, sorryImage.getHeight() / 2);
        sorryImage.setPosition((app.vpWidth-sorryImage.getWidth())/2, app.vpHeight/2 + sorryImage.getHeight()/2 + 40);
        sorryImage.addAction(sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f, 2f, 2.5f, Interpolation.pow5),
                        moveTo(stage.getWidth() / 2 - sorryImage.getWidth()/2, stage.getHeight() / 2 - sorryImage.getHeight()/2 + 40, 2f, Interpolation.swing))));

        stage.addActor(sorryImage);
    }

    public void initLabels() {
        String string = "¿Deseas salir o volver a jugar?";
        sorryLabel = new Label(string, app.skin);
        sorryLabel.setWrap(true);
        sorryLabel.setSize(app.vpWidth - 100, app.vpHeight - 250);
        sorryLabel.setFontScale(2);
        sorryLabel.setColor(Color.BLACK);
        sorryLabel.setPosition((app.vpWidth - sorryLabel.getWidth()) / 2, 200);
        stage.addActor(sorryLabel);
    }

    public void initButtons(){
        Vector2 buttonSize = new Vector2(150,70);
        exitButton = new TextButton("SALIR", app.skin);
        exitButton.setSize(buttonSize.x, buttonSize.y);
        exitButton.setPosition(app.vpWidth/5, 50);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(exitButton);

        playAgainButton = new TextButton("JUGAR", app.skin);
        playAgainButton.setSize(buttonSize.x, buttonSize.y);
        playAgainButton.setPosition(3*app.vpWidth/5, 50);
        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(screen);
            }
        });
        stage.addActor(playAgainButton);

    }

}
