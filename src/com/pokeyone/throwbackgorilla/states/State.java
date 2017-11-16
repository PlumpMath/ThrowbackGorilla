package com.pokeyone.throwbackgorilla.states;

import java.awt.*;

/**
 * A basic state of the game that handles user input and output
 */
public abstract class State {

    public int stateWidth, stateHeight;

    public State(int width, int height){
        this.stateWidth = width;
        this.stateHeight = height;
    }

    /**
     * Renders visuals for the state to the graphics object given
     * @param g The graphics object to render to
     */
    public abstract void paint(Graphics g);

    /**
     * Does all the processing before each frame
     */
    public abstract void tick();


    /**
     * Processes a key press
     * @param keyCode The key code of the key pressed gotten through KeyEvent.getKeyCode()
     */
    public abstract void keyPressed(int keyCode);

    /**
     * Processes a key release
     * @param keyCode The key code of the key pressed gotten through KeyEvent.getKeyCode()
     */
    public abstract void keyReleased(int keyCode);
}
