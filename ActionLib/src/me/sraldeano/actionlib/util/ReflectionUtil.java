package me.sraldeano.actionlib.util;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import me.sraldeano.actionlib.ActionLib;

/**
 *
 * @author SrAldeano
 */
public class ReflectionUtil {
    
    public static Set<Class<?>> getClasses(File jar, String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            JarFile file = new JarFile(jar);
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
                JarEntry jarEntry = entry.nextElement();
                String jarName = jarEntry.getName().replace('/', '.');
                if (jarName.startsWith(packageName) && jarName.endsWith(".class")) {
                    classes.add(Class.forName(jarName.substring(0, jarName.length() - 6)));
                }
            }
            file.close();
        }
        
        catch (IOException | ClassNotFoundException ex) {
            ActionLib.plugin.getLogger().severe("Error ocurred at getting classes, log: " + ex);
        }
        return classes;
    }
    
}
