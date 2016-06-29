package com.teo_finds_games.miniJuegos.colCabraLobo;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.teo_finds_games.Application;

public class ColCabraLoboBoatActor extends Image {
    public static int side; // left side = -1  and  right side = 1
    public static Boolean isFull = false;

    public static Vector2 leftPosition, rightPosition;
    public static float amount;
    public static Vector2 size;
    private MoveToAction moveToRight = new MoveToAction(), moveToLeft = new MoveToAction();


    public ColCabraLoboBoatActor(TextureRegion textureRegion){
        super(textureRegion);
        side = -1;
        leftPosition = new Vector2(Application.vpWidth/6, (Application.vpHeight - textureRegion.getRegionHeight())/2 );
        rightPosition = new Vector2((Application.vpWidth/6)*5 - textureRegion.getRegionWidth(), (Application.vpHeight - textureRegion.getRegionHeight())/2);
        amount = rightPosition.x - leftPosition.x;
        size = new Vector2(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        setSize(size.x, size.y);

        switch (side){
            case -1: setPosition(leftPosition.x, leftPosition.y);
                break;
            case 1: setPosition(rightPosition.x, rightPosition.y);
                break;
        }
        moveToLeft.setPosition(leftPosition.x, leftPosition.y);
        moveToLeft.setDuration(1f);
        moveToRight.setPosition(rightPosition.x, rightPosition.y);
        moveToRight.setDuration(1f);
    }

    public void moveToTheOtherSide(){ //This method is called when go button is clicked
        switch(side){
            case -1: addAction(moveToRight);
                break;
            case 1: addAction(moveToLeft);
                break;
        }
        side = side * (-1);
        moveToRight.restart();//The action can be executed more than once
        moveToLeft.restart();//The action can be executed more than once
    }

    public Vector2 getPosition(){
        return new Vector2(this.getX(), this.getY());
    }
}
