package com.teo_finds_games.miniJuegos.llave;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LlaveStaticPiece extends Image {

    private TextureRegion textureRegion;
    private int index;
    private Vector2 position;
    private float rotation;

    public LlaveStaticPiece(TextureRegion textureRegion, int index, Vector2 position, float rotation){
        super(textureRegion);
        this.textureRegion = textureRegion;
        this.index = index;
        this.position = position;
        this.rotation = rotation;

        setSize(textureRegion.getRegionWidth()* LlavePlayScreen.SCALE, textureRegion.getRegionHeight()*LlavePlayScreen.SCALE);
        setPosition(position.x, position.y);
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    public Vector2 getPosition() {
        return position;
    }
}
