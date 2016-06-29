package com.teo_finds_games.miniJuegos.dosmilcuarentayocho;


import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class DosMilCuarentaYOchoButton extends TextButton {

    private int num;
    TextButton.TextButtonStyle tbStyle;


    public DosMilCuarentaYOchoButton(String text, int num, Skin skin){
        super(text, skin);
        this.num = num;
    }

    public int getNum(){ return num;}

    public void setNum(int num){
        this.num = num;
        if(num == 0){
            setText(" ");
        }else{
            setText(num+""); //The text show the number
        }
    }

}
