package com.pokeyone.throwbackgorilla.entities;

import com.pokeyone.throwbackgorilla.resources.Resource;
import com.pokeyone.throwbackgorilla.resources.ResourceTileSet;
import com.pokeyone.throwbackgorilla.states.GameState;

import java.awt.image.BufferedImage;

/**
 * Created by pokeyone on 2017-11-08.
 */
public class MainCharacter extends Entity {

    public String name;
    public int currentImage = 0;

    public MainCharacter(String name, int width, int height, String imageName){
        super(width, height, imageName);
        this.name = name;
    }

    public BufferedImage getImage(){
        return ((ResourceTileSet)GameState.resourceHandler.getResource(imageName)).images[currentImage];
    }
}
