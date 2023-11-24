package de.lukas.TutorialManager.Util;


import de.lukas.TutorialManager.TutorialManager;
import me.oxolotel.utils.wrapped.config.Configuration;
import me.oxolotel.utils.wrapped.world.Location;
import java.io.*;
import java.util.ArrayList;
import java.util.List;




public class ConfigManager {

    public static List<String> servers = new ArrayList<>() {
    };
    public static me.oxolotel.utils.wrapped.world.Location location;
    public static Configuration configuration;

    public static String message;


    public static void createConfigConfiguration(TutorialManager main, File f) {
        configuration =  me.oxolotel.utils.wrapped.config.Configuration.of(main);
        readLocation();
        readMessage();
        readServers();
    }

    public static void writeLocationConfig(Location location) {
        configuration.set("location" , location);
        configuration.save();
    }

    public static void readServers() {
        servers = configuration.getStringList("servers");
    }

    public static void readLocation() {
         try{
            location = configuration.getLocation("location");
        }catch (Exception e){

        }
    }

    public static void readMessage(){
        message = configuration.getString("message");
    }
}

