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

public class WelcomeScreen implements Screen {

    private final Application app;
    private Stage stage;
    private SpriteBatch batch;
    private Label welcomeLabel;
    private String welcomeText;
    private TextButton backButton, nextButton;
    private Screen screen;

    public WelcomeScreen(Application app, String welcomeText, Screen screen){
        this.app = app;
        this.welcomeText = welcomeText;
        this.screen = screen;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        initLabels();
        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.7f, 0.9f, 1);
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
        welcomeLabel = new Label(welcomeText, app.skin);
        welcomeLabel.setWrap(true);
        welcomeLabel.setSize(app.vpWidth - 100, app.vpHeight - 250);
        welcomeLabel.setFontScale(2);
        welcomeLabel.setColor(Color.BLACK);
        welcomeLabel.setPosition((app.vpWidth - welcomeLabel.getWidth()) / 2, 200);
        stage.addActor(welcomeLabel);
    }

    public void initButtons(){
        Vector2 buttonSize = new Vector2(150,100);
        backButton = new TextButton("SALIR", app.skin);
        backButton.setSize(buttonSize.x, buttonSize.y);
        backButton.setPosition((app.vpWidth-buttonSize.x*2)/3, 50);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(backButton);

        nextButton = new TextButton("SIGUIENTE", app.skin);
        nextButton.setSize(buttonSize.x, buttonSize.y);
        nextButton.setPosition(backButton.getX() + buttonSize.x +((app.vpWidth-buttonSize.x*2)/3), 50);
        nextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(screen);
            }
        });
        stage.addActor(nextButton);

    }

}
