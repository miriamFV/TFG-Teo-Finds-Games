package com.teo_finds_games.actors;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teo_finds_games.Application;

public class Inhabitants extends Image {

    private final Application app;
    private Screen screen;
    private Player player;


    public Inhabitants(Texture texture, Application app, Player player, Vector2 position, Screen screen){
        super(texture);
        this.app = app;
        this.screen = screen;
        this.player = player;
        setPosition(position.x*16, position.y*16); //16 is the size of each tile of the Tile Map

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                changeScreen();
                return false;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private void changeScreen(){
        app.setPlayerPosition(new Vector2(player.getX(), player.getY()));
        app.setScreen(screen);
    }

}
