/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sraldeano.actionlib.util;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author markelm
 */
public class AddonDescription {
    private Yaml yaml;
    
    private String main, name, description, author, version;

    public AddonDescription(InputStream file) {
        Reader reader = new InputStreamReader(file);
        YamlConfiguration desc = YamlConfiguration.loadConfiguration(reader);
        main = desc.getString("main");
        name = desc.getString("name");
        description = desc.getString("description");
        author = desc.getString("author");
        version = desc.getString("version");
    }
    
    public String getAuthor() {
        return  author;
    }

    public String getDescription() {
        return description;
    }

    public String getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
    
}
