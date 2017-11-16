package com.pokeyone.throwbackgorilla;

import javax.swing.*;

/**
 * Created by pokeyone on 2017-11-02.
 */
public class Frame extends JFrame {

    JPanel game = new Game();

    public static final int FRAME_WIDTH = 640;
    public static final int FRAME_HEIGHT = 480;

    public Frame(){
        setSize(640, 480);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Throwback Gorilla");
        setLocationRelativeTo(null);

        add(game);

        setVisible(true);
    }

    public static void main(String[] args){
        new Frame();
    }
}
