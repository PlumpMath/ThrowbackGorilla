package com.pokeyone.throwbackgorilla.entities;

import com.pokeyone.throwbackgorilla.states.GameState;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by pokeyone on 2017-11-14.
 */
public class BoxEntity extends Entity {

    private Random random = new Random();
    private int imageID = 0;
    public boolean thrown = false;

    public int letter;
    public static final int[] keyCodes = {
            KeyEvent.VK_A,
            KeyEvent.VK_B,
            KeyEvent.VK_C,
            KeyEvent.VK_D,
            KeyEvent.VK_E,
            KeyEvent.VK_F,
            KeyEvent.VK_G,
            KeyEvent.VK_H,
            KeyEvent.VK_I,
            KeyEvent.VK_J,
            KeyEvent.VK_K,
            KeyEvent.VK_L,
            KeyEvent.VK_M,
            KeyEvent.VK_N,
            KeyEvent.VK_O,
            KeyEvent.VK_P,
            KeyEvent.VK_Q,
            KeyEvent.VK_R,
            KeyEvent.VK_S,
            KeyEvent.VK_T,
            KeyEvent.VK_U,
            KeyEvent.VK_V,
            KeyEvent.VK_W,
            KeyEvent.VK_X,
            KeyEvent.VK_Y,
            KeyEvent.VK_Z
    };

    public double x;
    public double y;

    public BoxEntity(double x, double y){
        super(64, 64, GameState.RES_BOX_TILESET_NAME);
        imageID = random.nextInt(GameState.resourceHandler.getTileSet(this.imageName).count);
        this.x = x;
        this.y = y;
        letter = random.nextInt(keyCodes.length);
    }

    public BufferedImage getImage() {
        return GameState.resourceHandler.getTileSet(imageName).images[imageID];
    }
}
