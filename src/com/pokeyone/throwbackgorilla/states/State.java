package com.pokeyone.throwbackgorilla.states;

import java.awt.*;

/**
 * A basic state of the game that handles user input and output
 */
public abstract class State {

    /**
     * Renders visuals for the state to the graphics object given, with the width and height given
     * @param g The graphics object to render to
     * @param width The width of the window
     * @param height The height of the window
     */
    public abstract void paint(Graphics g, int width, int height);

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
