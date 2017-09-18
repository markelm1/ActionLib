package me.sraldeano.actionlib.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.util.NumberConversions;

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
        } catch (IOException | ClassNotFoundException ex) {
            ActionLib.plugin.getLogger().severe("Error ocurred at getting classes, log: " + ex);
        }
        return classes;
    }

    public static void setField(Field field, Object object, Action action) {
        Class<?> fieldType = field.getType();
        String value = object.toString();
        try {
            if (fieldType.equals(Integer.TYPE) || fieldType.equals(Integer.class)) {
                field.set(action, NumberConversions.toInt(value));
            } else if (fieldType.equals(Float.TYPE) || fieldType.equals(Float.class)) {
                field.set(action, NumberConversions.toFloat(value));
            } else if (fieldType.equals(Double.TYPE) || fieldType.equals(Double.class)) {
                field.set(action, NumberConversions.toDouble(value));
            } else if (fieldType.equals(Boolean.TYPE) || fieldType.equals(Boolean.class)) {
                field.set(action, value.equalsIgnoreCase("true"));
            } else if (fieldType.equals(Long.TYPE) || fieldType.equals(Long.class)) {
                field.set(action, NumberConversions.toLong(value));
            } else if (fieldType.equals(Short.TYPE) || fieldType.equals(Short.class)) {
                field.set(action, NumberConversions.toShort(value));
            } else if (fieldType.equals(Byte.TYPE) || fieldType.equals(Byte.class)) {
                field.set(action, NumberConversions.toByte(value));
            } else if (fieldType.isAssignableFrom(String.class)) {
                field.set(action, value);
            } else if (fieldType.equals(Material.class)) {
                try {
                    Material material = Material.valueOf(value.toUpperCase());
                    field.set(action, material);
                } catch (Exception ex) {
                }
            } else if (fieldType.equals(Color.class)) {
                try {
                    Integer rgb = Integer.parseInt(value, 16);
                    Color color = Color.fromRGB(rgb);
                    field.set(action, color);
                } catch (Exception ex) {

                }
            } else if (fieldType.isAssignableFrom(Map.class)) {
                System.out.println("Is a map!  :D");
                field.set(action, object);
            }
        
        } catch (Exception e) {
            
        }

    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
    }
}