package com.teo_finds_games.miniJuegos.ochoNumeros;


import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OchoNumerosClickButton extends TextButton {

    private int row, column;
    private int num;

    public OchoNumerosClickButton(int num, Skin skin, int row, int column) {
        super(""+num, skin);
        this.row = row;
        this.column = column;
        this.num = num;
    }

    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }

    public int getNum(){ return num;}

    public void setNum(int num){
        this.num = num;
        setText(num+""); //We want that the button's text changes when we change the number of this button
    }

}
