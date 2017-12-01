package com.pokeyone.throwbackgorilla.states;

import com.pokeyone.throwbackgorilla.*;
import com.pokeyone.throwbackgorilla.Frame;
import com.pokeyone.throwbackgorilla.entities.BoxEntity;
import com.pokeyone.throwbackgorilla.entities.Entity;
import com.pokeyone.throwbackgorilla.entities.MainCharacter;
import com.pokeyone.throwbackgorilla.resources.ResourceHandler;
import com.pokeyone.throwbackgorilla.resources.ResourceImage;
import com.pokeyone.throwbackgorilla.resources.ResourceTileSet;
import com.pokeyone.throwbackgorilla.resources.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by pokeyone on 2017-11-03.
 */
public class GameState extends State {

    Random random = new Random();

    private HashMap<Integer, Boolean> keys;
    //private InputMap inputMap;
    //private ActionMap actionMap;

    public static ResourceHandler resourceHandler;
    public static final String RES_GORILLA_NAME = "gorilla";
    public static final String RES_GRASS_NAME = "grass";
    public static final String RES_BACKGROUND_NAME = "background";
    public static final String RES_BOX_TILESET_NAME = "box tileset";

    private MainCharacter mainCharacter;
    private ArrayList<BoxEntity> boxes;

    private Font boxLetterFont = new Font("Arial", Font.PLAIN, 36);

    // Main character last animation frame change
    private Date mainCharacterLastFrame = new Date();
    // Ground movement last animation frame change
    private Date groundLastFrame = new Date();
    private double groundOffset = 0;
    // box spawn last spawn
    private Date lastBoxSpawn = new Date();
    // box frequency in milliseconds
    private int boxFrequency = 4000;
    // number of boxes spawned
    private int boxCount = 0;
    // Minimum box frequency
    private int boxFreqStopThreshold = 750;
    // Whether or not game is paused
    private boolean paused = false;

    public static int score = 0;

    public GameState(){
        super(Frame.FRAME_WIDTH, Frame.FRAME_HEIGHT, "game");

        // Initialize keys map
        keys = new HashMap<>();
        for (int keycode : BoxEntity.keyCodes) {
            keys.put(keycode, false);
        }

        // Init entities
        mainCharacter = new MainCharacter("George", 96, 96, RES_GORILLA_NAME);
        boxes = new ArrayList<>();
    }

    public void onSwitch(){
        super.onSwitch();

        lastBoxSpawn = new Date();
        boxFrequency = 4000;
        boxCount = 0;
        score = 0;
        paused = false;

        // Initialize keys map
        keys = new HashMap<>();
        for (int keycode : BoxEntity.keyCodes) {
            keys.put(keycode, false);
        }

        // Init entities
        mainCharacter = new MainCharacter("George", 96, 96, RES_GORILLA_NAME);
        boxes = new ArrayList<>();
    }

    public void tick(){
        if (!paused) {
            // Main Character Animation
            if (new Date().getTime() - mainCharacterLastFrame.getTime() >= 250) {
                if (mainCharacter.currentImage == 2) {
                    mainCharacter.currentImage = 0;
                }
                if (mainCharacter.currentImage == 0) {
                    mainCharacter.currentImage = 1;
                } else {
                    mainCharacter.currentImage = 0;
                }

                mainCharacterLastFrame = new Date();
            }

            // Ground Offset Movement
            double newOff = (new Date().getTime() - groundLastFrame.getTime()) / 10.0;
            groundOffset += newOff;

            if (groundOffset >= 64) {
                groundOffset = 0.0;
            }
            groundLastFrame = new Date();

            // Box spawning
            if (new Date().getTime() - lastBoxSpawn.getTime() >= boxFrequency) {
                System.out.println("BOX SPAWN!");
                boxes.add(new BoxEntity(640, stateHeight - 128));
                boxCount++;
                lastBoxSpawn = new Date();

                if (boxFrequency > boxFreqStopThreshold) {
                    int bound = 106;
                    if (boxCount < 25) {
                        bound = 6 + boxCount * 4;
                    }
                    int rand = random.nextInt(bound);
                    int additioner = (rand - (3 + boxCount * 3));
                    boxFrequency += additioner;
                    System.out.println(boxFrequency + " boop " + additioner);
                    if (boxFrequency < boxFreqStopThreshold) boxFrequency = boxFreqStopThreshold;
                }
            }

            // Box Movement & Collision
            if (boxes != null && !boxes.isEmpty()) {
                for (int i = 0; i < boxes.size(); i++) {
                    boxes.get(i).x -= newOff;
                    if (boxes.get(i).thrown) {
                        boxes.get(i).y -= newOff * 3;
                        boxes.get(i).x -= newOff;
                    }

                    if(boxes.get(i).isDunTyped && !boxes.get(i).thrown && boxes.get(i).x < 64){
                        boxes.get(i).thrown = true;
                        mainCharacter.currentImage = 2;
                        mainCharacterLastFrame = new Date();
                    }

                    if (boxes.get(i).x < 64 && !boxes.get(i).thrown) {
                        endgame();
                    }

                    if (boxes.get(i).x < 0) {
                        boxes.remove(i);
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {
        // Background
        g.drawImage(((ResourceImage) (resourceHandler.getResource("background"))).getImage(), 0, 0, 640, 480, null);

        // Floor
        for (int i = 0; i <= Math.ceil(stateWidth / 64.0); i++) {
            g.drawImage(((ResourceImage) (resourceHandler.getResource("grass"))).getImage(), i * 64 - (int) groundOffset, stateHeight - 64, null);
        }

        // Main Character
        g.drawImage(mainCharacter.getImage(), 0, stateHeight - mainCharacter.height - 64, mainCharacter.width, mainCharacter.height, null);

        // Boxes
        g.setColor(Color.BLACK);
        g.setFont(boxLetterFont);
        g.getFontMetrics(boxLetterFont);


        if (boxes != null && !boxes.isEmpty()){
            for (int i = 0; i < boxes.size(); i++) {
                g.drawImage(boxes.get(i).getImage(), (int) boxes.get(i).x, (int) boxes.get(i).y, boxes.get(i).width, boxes.get(i).height, null);
                if(boxes.get(i).isDunTyped){
                    g.setColor(new Color(252, 227, 35));
                    g.drawString(boxes.get(i).getRenderText(), (int) boxes.get(i).x, (int) boxes.get(i).y);
                    g.setColor(Color.BLACK);
                }else if(mainCharacter.currentImage == 0) {
                    g.drawString(boxes.get(i).getRenderText(), (int) boxes.get(i).x, (int) boxes.get(i).y);
                }else{
                    g.drawString(boxes.get(i).getRenderText().replace('|', ':'), (int) boxes.get(i).x, (int) boxes.get(i).y);
                }
            }
        }

        g.drawString("" + score, 10, 50);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 128));
            g.fillRect(0, 0, stateWidth, stateHeight);
            g.setColor(Color.WHITE);
            g.drawString("Paused", stateWidth/2, stateHeight/2);
        }
    }

    /*
    public InputMap getInputMap(){
        return inputMap;
    }

    public ActionMap getActionMap(){
        return actionMap;
    }
    */

    public void keyPressed(KeyEvent e){
        for (int i = 0; i < boxes.size(); i++) {
            if(!boxes.get(i).isDunTyped)
            if(boxes.get(i).getText().charAt(boxes.get(i).currentProgress) == e.getKeyCode()){
                boxes.get(i).currentProgress++;
                if(boxes.get(i).currentProgress >= boxes.get(i).letters.length){
                    boxes.get(i).isDunTyped = true;
                    score += boxCount * (int)(boxes.get(i).letters.length * 1.5);
                }
            }
        }
    }

    public void keyReleased(KeyEvent e){
    }

    public void endgame(){
        System.out.println("FAILURE");
        System.out.println("Final score: " + score);
        changeState = "score";
    }
}
