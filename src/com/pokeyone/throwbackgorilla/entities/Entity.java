package com.pokeyone.throwbackgorilla.entities;

import com.pokeyone.throwbackgorilla.resources.Resource;

import java.awt.image.BufferedImage;

/**
 * Created by pokeyone on 2017-11-08.
 */
public abstract class Entity {

    public int width, height;
    protected String imageName;

    public Entity(int width, int height, String imageName){
        this.width = width;
        this.height = height;
        this.imageName = imageName;
    }

    public abstract BufferedImage getImage();
}
