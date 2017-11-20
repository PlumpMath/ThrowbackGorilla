package com.pokeyone.throwbackgorilla;

import com.pokeyone.throwbackgorilla.states.GameState;
import com.pokeyone.throwbackgorilla.states.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by pokeyone on 2017-11-02.
 */
public class Game extends JPanel implements Runnable {

    private boolean running = true;
    private Thread thread;

    //Array holding all initialized states
    private State[] states;
    //The index of the state currently presented
    private int currentState = 0;

    public Game(){
        setFocusable(true);

        states = new State[]{
                new GameState()
        };

        setInputMap(JPanel.WHEN_FOCUSED, states[0].getInputMap());
        setActionMap(states[0].getActionMap());

        thread = new Thread(this);
        thread.start();
    }

    /**
     * Does all processing of game not related to rendering, but is called before every repaint
     */
    private void tick(){
        //tick the state
        states[currentState].tick();
    }

    public void paint(Graphics g){
        //Clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        //Render current state
        states[currentState].paint(g);
    }

    public void run(){
        while(running){
            tick();
            repaint();

            try{
                Thread.sleep(17);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
