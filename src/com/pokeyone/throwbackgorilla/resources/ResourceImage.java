package com.pokeyone.throwbackgorilla.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by pokeyone on 2017-11-06.
 */
public class ResourceImage extends Resource {

    private BufferedImage image;

    public ResourceImage(String name, String path) {
        super(name, path, ResourceType.IMAGE);
    }

    public BufferedImage getImage(){
        return image;
    }

    public void load() throws IOException {
        System.out.println(path);
        image = ImageIO.read(new File(path));
    }
}
