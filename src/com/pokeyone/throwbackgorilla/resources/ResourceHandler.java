package com.pokeyone.throwbackgorilla.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pokeyone on 2017-11-06.
 */
public class ResourceHandler {

    private HashMap<String, Resource> resources;
    private String path;

    public ResourceHandler(String path){
        resources = new HashMap<>();
        this.path = path;
    }

    public void addResource(String filename, String name, ResourceType type, int ... args) throws IOException, Exception {
        switch(type){
            case TILE_SET:
                if (args.length < 3) {
                    throw new Exception("Not enough arguments given for tileset resource!");
                }else{
                    resources.put(name, new ResourceTileSet(name, path + filename, args[0], args[1], args[2]));
                }
                break;
            case IMAGE:
                resources.put(name, new ResourceImage(name, path + filename));
                break;
        }
    }

    public Resource getResource(String name){
        return resources.get(name);
    }

    public ResourceTileSet getTileSet(String name){
        if(resources.get(name) instanceof ResourceTileSet){
            return (ResourceTileSet)resources.get(name);
        }
        return null;
    }

    public void loadAll(){
        for(Resource resource : resources.values()){
            try {
                resource.load();
            }catch(IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
