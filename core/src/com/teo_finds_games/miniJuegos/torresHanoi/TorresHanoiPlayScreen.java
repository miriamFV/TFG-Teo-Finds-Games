package com.teo_finds_games.miniJuegos.torresHanoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.teo_finds_games.Application;

public class TorresHanoiPlayScreen implements Screen{

    private final Application app;
    private Stage stage;

    private TextButton backButton;

    private Texture textureFloor, texturePost, texturePiece;
    private TorresHanoiActorFloor floor;
    private TorresHanoiActorPost leftPost, middlePost, rightPost;

    public static TorresHanoiActorPiece[] pieceArray;

    public TorresHanoiPlayScreen(Application app) {
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(app.vpWidth, app.vpHeight)){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    System.exit(0);
                }
                return super.keyDown(keyCode);
            }
        };
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        initButtons();
        initTextures();
        initActors();
    }

    public void update(float delta){
        if ((pieceTouched() != null) && pieceTouched().isOnTop()){
            if (pieceTouched().isOnTop()){
                //The piece have to move with your finger
                pieceTouched().getRectanglePiece().setPosition(Gdx.input.getX() - pieceTouched().getWidth()/2, app.vpHeight - (Gdx.input.getY() + pieceTouched().getHeight()/2 ));
            }else{
                //We can not move this piece
            }
        }

        if (haveWon()){
            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.torresHanoiCS.setNumCoins(TorresHanoiMenuScreen.numPieces+1); //If there are x pieces, the player will win x+1 coins, etc
                    app.setScreen(app.torresHanoiCS);
                }
            })));
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.act();
        stage.draw();
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
        textureFloor.dispose();
        texturePost.dispose();
        texturePiece.dispose();
    }

    public void initButtons(){
        backButton = new TextButton("ATRAS", app.skin);
        backButton.setSize(125, 75);
        backButton.setPosition(40, app.vpHeight - (backButton.getHeight() + 40));
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.torresHanoiMenuScreen);
            }
        });
        stage.addActor(backButton);

    }


    public void initTextures(){
        //Initialize textures
        textureFloor = new Texture(Gdx.files.internal("images/torreshanoi/floor.png"));
        texturePost = new Texture(Gdx.files.internal("images/torreshanoi/post.png"));
        texturePiece = new Texture(Gdx.files.internal("images/torreshanoi/piece2.png"));
    }

    public void initActors(){
        //Initialize actors
        floor = new TorresHanoiActorFloor(textureFloor);
        stage.addActor(floor);

        leftPost = new TorresHanoiActorPost(texturePost,1);
        stage.addActor(leftPost);

        middlePost = new TorresHanoiActorPost(texturePost,2);
        stage.addActor(middlePost);

        rightPost = new TorresHanoiActorPost(texturePost,3);
        stage.addActor(rightPost);

        pieceArray = new TorresHanoiActorPiece[TorresHanoiMenuScreen.numPieces];

        for(int i = 0; i < pieceArray.length; i++){
            pieceArray[i] = new TorresHanoiActorPiece(texturePiece, i + 1, leftPost, middlePost, rightPost);
            int num = pieceArray.length-i;
            leftPost.stack.push(pieceArray.length-i); //The stack is 87654321
            stage.addActor(pieceArray[i]);
        }
    }


    public TorresHanoiActorPiece pieceTouched(){
        TorresHanoiActorPiece sol = null;
        for (int i = pieceArray.length-1; i>=0;i--){
            if (pieceArray[i].getIsTouched()){
                sol = pieceArray[i];
            }
        }
        return sol;
    }


    public boolean haveWon(){
        return (middlePost.stack.size() == pieceArray.length || rightPost.stack.size() == pieceArray.length);
    }

}
