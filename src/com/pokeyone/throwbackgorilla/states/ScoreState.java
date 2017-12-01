package com.pokeyone.throwbackgorilla.states;

import com.pokeyone.throwbackgorilla.Frame;
import com.pokeyone.throwbackgorilla.resources.ResourceImage;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by pokeyone on 2017-12-01.
 */
public class ScoreState extends State {

    private int score = 0;

    public ScoreState(){
        super(Frame.FRAME_WIDTH, Frame.FRAME_HEIGHT, "score");
    }

    public void onSwitch(){
        super.onSwitch();
        this.score = GameState.score;
    }

    @Override
    public void paint(Graphics g) {
        // Background
        g.drawImage(((ResourceImage) (GameState.resourceHandler.getResource("background"))).getImage(), 0, 0, stateWidth, stateHeight, null);

        g.setColor(Color.BLACK);

        g.setFont(MenuState.titleFont);
        g.drawString("Final score: " + score, 50, 100);

        g.setFont(MenuState.optionFont);
        g.drawString("Press any key to go back to menu", 50, 300);
    }

    @Override
    public void tick() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        changeState = "start";
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
