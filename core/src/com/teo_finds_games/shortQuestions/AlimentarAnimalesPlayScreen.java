package com.teo_finds_games.shortQuestions;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

public class AlimentarAnimalesPlayScreen implements Screen {

    protected final Application app;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Label questionLabel, answerCowLabel, answerPorkLabel, answerRabbitLabel;
    private String questionText;
    private TextField answerCowField, answerPorkField, answerRabbitField;
    private TextButton checkButton;

    public AlimentarAnimalesPlayScreen(Application app, String questionText){
        this.app = app;
        this.questionText = questionText;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        initTextures();
        setBackground();
        initLabels();
        initTextFields();
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


    public void initTextures(){
        backgroundTexture = new Texture(Gdx.files.internal("images/alimentaranimales/background.png"));
    }

    public void setBackground(){
        Image imageBackground = new Image(backgroundTexture);
        imageBackground.setSize(app.vpWidth, app.vpHeight);
        stage.addActor(imageBackground);
    }

    public void initLabels(){
        questionLabel = new Label(questionText, app.skin);
        questionLabel.setWrap(true);
        questionLabel.setSize(app.vpWidth - 150, app.vpHeight/2);
        questionLabel.setFontScale(2);
        questionLabel.setColor(Color.BLACK);
        questionLabel.setPosition((app.vpWidth - questionLabel.getWidth())/2 , app.vpHeight/2 + 50);
        stage.addActor(questionLabel);

        answerRabbitLabel = new Label("Conejos: ", app.skin);
        answerRabbitLabel.setSize(150,75);
        answerRabbitLabel.setFontScale(2);
        answerRabbitLabel.setColor(Color.BLACK);
        answerRabbitLabel.setPosition( (Application.vpWidth-3*answerRabbitLabel.getWidth())/4, Application.vpHeight/2 + 50);
        stage.addActor(answerRabbitLabel);

        answerCowLabel = new Label("Vacas: ", app.skin);
        answerCowLabel.setSize(150,75);
        answerCowLabel.setFontScale(2);
        answerCowLabel.setColor(Color.BLACK);
        answerCowLabel.setPosition(2*((Application.vpWidth-3*answerRabbitLabel.getWidth())/4) + answerCowLabel.getWidth(), Application.vpHeight/2 + 50);
        stage.addActor(answerCowLabel);

        answerPorkLabel = new Label("Cerdos: ", app.skin);
        answerPorkLabel.setSize(150,75);
        answerPorkLabel.setFontScale(2);
        answerPorkLabel.setColor(Color.BLACK);
        answerPorkLabel.setPosition(3*((Application.vpWidth-3*answerRabbitLabel.getWidth())/4) + 2*answerCowLabel.getWidth(), Application.vpHeight/2 + 50);
        stage.addActor(answerPorkLabel);
    }

    public void initTextFields(){
        answerRabbitField = new TextField("", app.skin);
        answerRabbitField.setSize(150,75);
        answerRabbitField.setPosition( answerRabbitLabel.getX(), answerRabbitLabel.getY()- answerRabbitField.getHeight());
        stage.addActor(answerRabbitField);

        answerCowField = new TextField("", app.skin);
        answerCowField.setSize(150,75);
        answerCowField.setPosition( answerCowLabel.getX(), answerCowLabel.getY()- answerCowField.getHeight());
        stage.addActor(answerCowField);

        answerPorkField = new TextField("", app.skin);
        answerPorkField.setSize(150,75);
        answerPorkField.setPosition( answerPorkLabel.getX(), answerPorkLabel.getY()- answerPorkField.getHeight());
        stage.addActor(answerPorkField);
    }

    public void initButtons(){
        checkButton = new TextButton("COMPROBAR", app.skin);
        checkButton.setSize(150,65);
        checkButton.setPosition(10,10);
        checkButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(rightSolution()){
                    stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.alimentarAnimalesCS.setNumCoins(3);
                            app.setScreen(app.alimentarAnimalesCS);
                        }
                    })));
                }else{
                    stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.setScreen(app.alimentarAnimalesSS);
                        }
                    })));
                }
            }
        });
        stage.addActor(checkButton);
    }

    public boolean rightSolution(){
        return ((answerRabbitField.getText().equals("14") || answerRabbitField.getText().equals("catorce")) &&
                (answerCowField.getText().equals("1") || answerCowField.getText().equals("una")) &&
                (answerPorkField.getText().equals("5") || answerPorkField.getText().equals("cinco")));
    }

}