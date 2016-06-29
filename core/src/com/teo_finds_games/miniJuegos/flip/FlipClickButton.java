package com.teo_finds_games.miniJuegos.flip;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class FlipClickButton extends TextButton {

    private int id;
    private int num;
    TextButton.TextButtonStyle tbStyle;


    public FlipClickButton(String text,int num, Skin skin, int id) {
        super(text, skin);
        this.id = id;
        this.num = num;
    }

    public int getId(){
        return id;
    }

    public int getNum(){ return num;}

    public void setNum(int num){
        this.num = num;
        setText(num+""); //We want the text to change along with the number
    }

}
