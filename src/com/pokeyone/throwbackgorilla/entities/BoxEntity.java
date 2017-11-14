package com.pokeyone.throwbackgorilla.entities;

import com.pokeyone.throwbackgorilla.states.GameState;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by pokeyone on 2017-11-14.
 */
public class BoxEntity extends Entity {

    private Random random = new Random();
    private int imageID = 0;

    public double x;

    public BoxEntity(double x){
        super(64, 64, GameState.RES_BOX_TILESET_NAME);
        imageID = random.nextInt(GameState.resourceHandler.getTileSet(this.imageName).count);
        this.x = x;
    }

    public BufferedImage getImage() {
        return GameState.resourceHandler.getTileSet(imageName).images[imageID];
    }
}
