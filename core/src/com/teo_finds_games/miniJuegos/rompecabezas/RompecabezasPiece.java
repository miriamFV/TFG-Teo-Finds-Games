package com.teo_finds_games.miniJuegos.rompecabezas;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RompecabezasPiece extends Image {

    private int num;

    public RompecabezasPiece(TextureRegion textureRegion, int num){
        super(textureRegion);
        this.num = num;
    }

    public int getNum() {
        return num;
    }

}
