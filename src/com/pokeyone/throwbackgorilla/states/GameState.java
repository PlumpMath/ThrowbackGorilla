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
import java.beans.PropertyChangeListener;
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
    private InputMap inputMap;
    private ActionMap actionMap;

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

    public GameState(){
        super(Frame.FRAME_WIDTH, Frame.FRAME_HEIGHT);

        // Initialize keys map
        keys = new HashMap<>();
        for (int keycode : BoxEntity.keyCodes) {
            keys.put(keycode, false);
        }

        // Initialize resource handler and resources
        resourceHandler = new ResourceHandler("res/");
        try {
            resourceHandler.addResource("Gorilla.png", RES_GORILLA_NAME, ResourceType.TILE_SET, 64, 64, 3);
            resourceHandler.addResource("grass.png", RES_GRASS_NAME, ResourceType.IMAGE);
            resourceHandler.addResource("background.png", RES_BACKGROUND_NAME, ResourceType.IMAGE);
            resourceHandler.addResource("boxes.png", RES_BOX_TILESET_NAME, ResourceType.TILE_SET, 64, 64, 3);
            resourceHandler.loadAll();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        // Init entities
        mainCharacter = new MainCharacter("George", 96, 96, RES_GORILLA_NAME);
        boxes = new ArrayList<>();

        // Init input and action maps
        inputMap = new InputMap();
        actionMap = new ActionMap();
        for (int keyCode : BoxEntity.keyCodes) {
            inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), "keyPress" + keyCode);
            inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, true), "keyReleased" + keyCode);

            actionMap.put("keyPress" + keyCode, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keys.put(keyCode, true);
                    System.out.println("Key Press " + keyCode);
                }
            });

            actionMap.put("keyReleased" + keyCode, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keys.put(keyCode, false);
                    System.out.println("Key Release " + keyCode);
                }
            });
        }

    }

    public void tick(){
        // Main Character Animation
        if(new Date().getTime() - mainCharacterLastFrame.getTime() >= 250) {
            if (mainCharacter.currentImage == 2){
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
        double newOff = (new Date().getTime() - groundLastFrame.getTime())/10.0;
        groundOffset += newOff;

        if(groundOffset >= 64){
            groundOffset = 0.0;
        }
        groundLastFrame = new Date();

        // Box spawning
        if(new Date().getTime() - lastBoxSpawn.getTime() >= boxFrequency){
            System.out.println("BOX SPAWN!");
            boxes.add(new BoxEntity(640, stateHeight-128));
            boxCount++;
            lastBoxSpawn = new Date();

            if(boxFrequency > boxFreqStopThreshold) {
                int bound = 106;
                if (boxCount < 25) {
                    bound = 6 + boxCount * 4;
                }
                int rand = random.nextInt(bound);
                int additioner = (rand - (3 + boxCount * 3));
                boxFrequency += additioner;
                System.out.println(boxFrequency + " boop " + additioner);
                if(boxFrequency < boxFreqStopThreshold) boxFrequency = boxFreqStopThreshold;
            }
        }

        // Box Movement & Collision
        if(boxes != null && !boxes.isEmpty()) {
            for (BoxEntity box : boxes) {
                box.x -= newOff;
                if (box.thrown) {
                    box.y -= newOff*3;
                    box.x -= newOff;
                }

                if (box.x < 64 && !box.thrown) {
                    if (keys.get(BoxEntity.keyCodes[box.letter]) != null && keys.get(BoxEntity.keyCodes[box.letter])) {
                        box.thrown = true;
                        mainCharacter.currentImage = 2;
                        mainCharacterLastFrame = new Date();
                    }else{
                        endgame();
                    }
                }

                if (box.x < 0) {
                    boxes.remove(box);
                }
            }
        }
    }

    public void paint(Graphics g){
        // Background
        g.drawImage(((ResourceImage)(resourceHandler.getResource("background"))).getImage(), 0, 0, 640, 480, null);

        // Floor
        for(int i = 0; i <= Math.ceil(stateWidth/64.0); i++){
            g.drawImage(((ResourceImage)(resourceHandler.getResource("grass"))).getImage(), i * 64 - (int)groundOffset, stateHeight-64, null);
        }

        // Main Character
        g.drawImage(mainCharacter.getImage(), 0, stateHeight-mainCharacter.height-64, mainCharacter.width, mainCharacter.height, null);

        // Boxes
        g.setColor(Color.BLACK);
        g.setFont(boxLetterFont);
        g.getFontMetrics(boxLetterFont);


        if(boxes != null && !boxes.isEmpty())
            for (BoxEntity box : boxes){
                g.drawImage(box.getImage(), (int)box.x, (int)box.y, box.width, box.height, null);
                g.drawString(KeyEvent.getKeyText(box.keyCodes[box.letter]), (int)box.x, (int)box.y);
            }

    }

    public InputMap getInputMap(){
        return inputMap;
    }

    public ActionMap getActionMap(){
        return actionMap;
    }

    public void endgame(){
        System.out.println("FAILURE");
        //TODO: transfer to score screen
    }
}
