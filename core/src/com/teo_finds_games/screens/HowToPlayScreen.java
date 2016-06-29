package com.teo_finds_games.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

public class HowToPlayScreen implements Screen {

    private final Application app;
    private Stage stage;
    private SpriteBatch batch;
    private Label explanationLabel;
    private String explanationText;
    private TextButton jugarButton;
    private Screen screen;

    public HowToPlayScreen(Application app, String explanationText, Screen screen){
        this.app = app;
        this.explanationText = explanationText;
        this.screen = screen;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        initButtons();
        initLabels();
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
    }

    public void initLabels() {
        explanationLabel = new Label(explanationText, app.skin);
        explanationLabel.setWrap(true);
        explanationLabel.setSize(app.vpWidth - 150, app.vpHeight / 2);
        explanationLabel.setFontScale(2);
        explanationLabel.setColor(Color.BLACK);
        explanationLabel.setPosition((app.vpWidth - explanationLabel.getWidth()) / 2, 200+jugarButton.getHeight());
        stage.addActor(explanationLabel);
    }

    public void initButtons(){
        Vector2 buttonSize = new Vector2(150,70);
        jugarButton = new TextButton("JUGAR", app.skin);
        jugarButton.setSize(buttonSize.x, buttonSize.y);
        jugarButton.setPosition((app.vpWidth-buttonSize.x)/2, 50);
        jugarButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(screen);
            }
        });
        stage.addActor(jugarButton);


    }

}
