package com.pokeyone.throwbackgorilla.states;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by pokeyone on 2017-12-01.
 */
public class ExitState extends State {

    public ExitState() {
        super(0, 0, "exit");
    }

    public void onSwitch(){
        System.exit(0);
    }

    @Override
    public void paint(Graphics g) {

    }

    @Override
    public void tick() {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
