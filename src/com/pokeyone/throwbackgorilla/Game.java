package com.pokeyone.throwbackgorilla;

import com.pokeyone.throwbackgorilla.resources.ResourceHandler;
import com.pokeyone.throwbackgorilla.resources.ResourceType;
import com.pokeyone.throwbackgorilla.states.GameState;
import com.pokeyone.throwbackgorilla.states.MenuState;
import com.pokeyone.throwbackgorilla.states.State;
import com.pokeyone.throwbackgorilla.ui.MenuOption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by pokeyone on 2017-11-02.
 */
public class Game extends JPanel implements Runnable, KeyListener {

    private boolean running = true;
    private Thread thread;

    //Array holding all initialized states
    private State[] states;
    //The index of the state currently presented
    private int currentState = 0;

    public Game(){
        setFocusable(true);

        // Initialize resource handler and resources
        GameState.resourceHandler = new ResourceHandler("res/");
        try {
            GameState.resourceHandler.addResource("Gorilla.png", GameState.RES_GORILLA_NAME, ResourceType.TILE_SET, 64, 64, 3);
            GameState.resourceHandler.addResource("grass.png", GameState.RES_GRASS_NAME, ResourceType.IMAGE);
            GameState.resourceHandler.addResource("background.png", GameState.RES_BACKGROUND_NAME, ResourceType.IMAGE);
            GameState.resourceHandler.addResource("boxes.png", GameState.RES_BOX_TILESET_NAME, ResourceType.TILE_SET, 64, 64, 4);
            GameState.resourceHandler.loadAll();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        states = new State[]{
                new MenuState("Throwback Gorilla", "start", new MenuOption("PLAY", "game"), new MenuOption("QUIT", "exit")),
                new GameState()
        };

        addKeyListener(this);

        //setInputMap(JPanel.WHEN_FOCUSED, states[0].getInputMap());
        //setInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, states[0].getInputMap());
        //setActionMap(states[0].getActionMap());

        thread = new Thread(this);
        thread.start();
    }

    /**
     * Does all processing of game not related to rendering, but is called before every repaint
     */
    private void tick(){
        if(!states[currentState].changeState.equals("none")){
            currentState = getStateWithName(states[currentState].changeState);
            states[currentState].onSwitch();
        }

        //tick the state
        states[currentState].tick();
    }

    private int getStateWithName(String stateName){
        for(int i = 0; i < states.length; i++){
            if (states[i].name.equals(stateName)) {
                return i;
            }
        }
        return -1;
    }

    public void paint(Graphics g){
        //Clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        //Render current state
        states[currentState].paint(g);
    }

    public void keyPressed(KeyEvent e){
        states[currentState].keyPressed(e);
    }

    public void keyReleased(KeyEvent e){
        states[currentState].keyReleased(e);
    }

    public void keyTyped(KeyEvent e){};

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
