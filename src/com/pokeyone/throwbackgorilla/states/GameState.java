package com.pokeyone.throwbackgorilla.states;

import com.pokeyone.throwbackgorilla.*;
import com.pokeyone.throwbackgorilla.Frame;
import com.pokeyone.throwbackgorilla.entities.BoxEntity;
import com.pokeyone.throwbackgorilla.entities.MainCharacter;
import com.pokeyone.throwbackgorilla.resources.ResourceHandler;
import com.pokeyone.throwbackgorilla.resources.ResourceImage;
import com.pokeyone.throwbackgorilla.resources.ResourceTileSet;
import com.pokeyone.throwbackgorilla.resources.ResourceType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by pokeyone on 2017-11-03.
 */
public class GameState extends State {

    Random random = new Random();

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

        mainCharacter = new MainCharacter("George", 96, 96, RES_GORILLA_NAME);
        boxes = new ArrayList<>();
    }

    public void tick(){
        // Main Character Animation
        if(new Date().getTime() - mainCharacterLastFrame.getTime() >= 250) {
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

        // Box Movement
        if(boxes != null && !boxes.isEmpty()) {
            for (BoxEntity box : boxes) {
                box.x -= newOff;
                if (box.thrown)
                    box.y -= newOff;
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

    public void keyPressed(int keyCode){

    }

    public void keyReleased(int keyCode){

    }
}
