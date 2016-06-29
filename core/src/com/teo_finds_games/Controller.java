package com.teo_finds_games;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.teo_finds_games.actors.Player;
import com.teo_finds_games.screens.PlayScreen;


public class Controller extends Touchpad {

    //Touchpad
    public static Skin touchpadSkin;
    public static Touchpad.TouchpadStyle touchpadStyle;
    private int deadzoneRadius = 10, width = 100, height = 100, blockSpeed = 100;

    private Player player;
    private Camera camera;

    public Controller(Player player, Camera camera){
        super(10, Controller.getTouchPadStyle()); //deadzoneRadius = 10;
        this.player = player;
        this.camera = camera;
        setBounds(player.getX()+player.getWidth()/2 - camera.viewportWidth / 2, player.getY()+player.getHeight()/2 - camera.viewportHeight / 2, width, height);
    }

    private static Touchpad.TouchpadStyle getTouchPadStyle(){
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("images/pantallaprincipal/touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("images/pantallaprincipal/touchKnob.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
        return touchpadStyle;
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        if(isTouched()) {
            //Player moves
            player.setPlayerFrame(updateCurrentFrame());
            player.setVelocity(new Vector2(this.getKnobPercentX() * blockSpeed, this.getKnobPercentY() * blockSpeed ));


        }else if(!isTouched()){
            //Player stands
            player.setPlayerFrame(player.getPlayerStandAnimation().getKeyFrame(PlayScreen.stateTime/3, true));
            player.velocity.x = 0;
            player.velocity.y = 0;
        }
    }


    private TextureRegion updateCurrentFrame(){
        if(getKnobPercentX() <= getKnobPercentY()){
            if(getKnobPercentX() >= -getKnobPercentY()){
                //SpriteAnimation which represent the movement to the top
                return player.getPlayerUpAnimation().getKeyFrame(PlayScreen.stateTime, true);
            } else{
                //SpriteAnimation which represent the movement to the left
                return player.getPlayerLeftAnimation().getKeyFrame(PlayScreen.stateTime, true);
            }
        }else{
            if(getKnobPercentX() >= -getKnobPercentY()){
                //SpriteAnimation which represent the movement to the right
                return player.getPlayerRightAnimation().getKeyFrame(PlayScreen.stateTime, true);
            } else{
                //SpriteAnimation which represent the movement to the bottom
                return player.getPlayerDownAnimation().getKeyFrame(PlayScreen.stateTime, true);
            }
        }
    }

}
