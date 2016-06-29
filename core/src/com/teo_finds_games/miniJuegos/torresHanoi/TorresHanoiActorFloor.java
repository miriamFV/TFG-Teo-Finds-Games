package com.teo_finds_games.miniJuegos.torresHanoi;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.teo_finds_games.Application;

public class TorresHanoiActorFloor extends Actor {
    private Texture textureFloor;
    public static int floorWidth, floorHeight;

    public TorresHanoiActorFloor(Texture textureFloor){
        this.textureFloor = textureFloor;

        floorWidth = Application.vpWidth;
        floorHeight = Application.vpHeight/6;
        setPosition(0,0);
        setSize(floorWidth, floorHeight);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureFloor, getX(), getY(), floorWidth, floorHeight);
    }
}
