package com.pokeyone.throwbackgorilla.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by pokeyone on 2017-11-06.
 */
public class ResourceTileSet extends Resource {

    public int tileWidth, tileHeight;
    public int count;

    public BufferedImage[] images;

    public ResourceTileSet(String name, String path, int tileWidth, int tileHeight, int count){
        super(name, path, ResourceType.TILE_SET);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.count = count;
    }

    public void load() throws IOException{
        BufferedImage image = ImageIO.read(new File(path));
        images = new BufferedImage[count];
        for(int i = 0; i < count; i++){
            images[i] = image.getSubimage(tileWidth * i, 0, tileWidth, tileHeight);
        }
    }
}
