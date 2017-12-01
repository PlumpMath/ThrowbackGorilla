package com.pokeyone.throwbackgorilla.states;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * A basic state of the game that handles user input and output
 */
public abstract class State {

    /**
     * Width and height used for rendering
     */
    public int stateWidth, stateHeight;
    /**
     * The name of the state used for identification only
     */
    public String name;
    /**
     * When anything other than "none", the state's manager should change to the state with the name equal to this string
     */
    public String changeState = "none";

    /**
     * Initializes a new State with the given width, height, and name
     * @param width The width that the state will have when displayed
     * @param height The height that the state will have when displayed
     * @param name The name of the state, to be used for identification purposes
     */
    public State(int width, int height, String name){
        this.stateWidth = width;
        this.stateHeight = height;
        this.name = name;
    }

    /**
     * Called when state is brought into view
     */
    public void onSwitch(){changeState = "none";}

    /**
     * Renders visuals for the state to the graphics object given
     * @param g The graphics object to render to
     */
    public abstract void paint(Graphics g);

    /**
     * Does all the processing before each frame
     */
    public abstract void tick();

    /*
    public abstract InputMap getInputMap();
    public abstract ActionMap getActionMap();
    */

    /**
     * Process the key press event. Should be called by the manager of the states
     * @param e The KeyEvent of the key press
     */
    public abstract void keyPressed(KeyEvent e);

    /**
     * Process the key release event. Should be called by the manager of the states
     * @param e The KeyEvent of the key release
     */
    public abstract void keyReleased(KeyEvent e);
}
