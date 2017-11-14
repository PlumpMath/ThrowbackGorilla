package com.pokeyone.throwbackgorilla.resources;

import java.io.IOException;

/**
 * Created by pokeyone on 2017-11-06.
 */
public abstract class Resource {

    public String name;
    public String path;
    public ResourceType type;

    public Resource(String name, String path, ResourceType type){
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public abstract void load() throws IOException;
}
