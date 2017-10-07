/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.sraldeano.actionlib.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *
 * @author markel2
 */
public class TitleUtil {
    private static HashMap<Class<? extends Entity>, Method> handles = new HashMap<>();
    private static Field playerConnection = null;
    private static Method playerSendPacket = null;

    public static void sendActionBar(Player p, String msg) {
        try {
            msg = TextUtil.colored(msg);
            Class<?> packetClass = getServerClass("PacketPlayOutChat");
            Class<?> componentClass = getServerClass("IChatBaseComponent");
            Class<?> serializerClass = getServerClass("IChatBaseComponent$ChatSerializer");
            Constructor<?> packetConstructor = packetClass.getConstructor(componentClass, byte.class);
            Object baseComponent;
            baseComponent = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + msg + "\"}");
            Object packet = packetConstructor.newInstance(baseComponent, (byte)2);
            sendPacket(p, packet);
        } catch(Exception e){}
    }
    public static void sendTitle(Player p, String title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
        title = TextUtil.colored(title);
        subtitle = TextUtil.colored(subtitle);
        Class<?> packetClass = getServerClass("PacketPlayOutTitle");
        Class<?> componentClass = getServerClass("IChatBaseComponent");
        Class<?> serializerClass = getServerClass("IChatBaseComponent$ChatSerializer");
        Class<Enum> enumTitleAction = (Class<Enum>)getServerClass("PacketPlayOutTitle$EnumTitleAction");
        Constructor <?> packetConstructor = null;
        try {
            packetConstructor = packetClass.getConstructor(enumTitleAction, componentClass, int.class, int.class, int.class);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        if (subtitle != null) {
            Object subTitleSer;
            Object subTitlePacket;
            try {
                subTitleSer = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + subtitle + "\"}");
                subTitlePacket = packetConstructor.newInstance(
                        enumTitleAction.getEnumConstants()[1], subTitleSer, fadeIn.intValue(),
                        stay.intValue(), fadeOut.intValue()
                );
                sendPacket(p, subTitlePacket);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if (title != null) {
            Object ser;
            Object packet;
            try {
                ser = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + title + "\"}");
                packet = packetConstructor.newInstance(enumTitleAction.getEnumConstants()[0], ser, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
                sendPacket(p, packet);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static Class<?> getServerClass(String name){
        String className = "net.minecraft.server." + ReflectionUtil.getVersion() + name;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return clazz;
    }

    public static Object getHandle(Entity entity){
        try {
            if (handles.get(entity.getClass()) != null)
                return handles.get(entity.getClass()).invoke(entity);
            else {
                Method entity_getHandle = entity.getClass().getMethod("getHandle");
                handles.put(entity.getClass(), entity_getHandle);
                return entity_getHandle.invoke(entity);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static void sendPacket(Player p, Object packet) {
        try {
            if (playerConnection == null){
                playerConnection = getHandle(p).getClass().getField("playerConnection");
                for (Method m : playerConnection.get(getHandle(p)).getClass().getMethods()){
                    if (m.getName().equalsIgnoreCase("sendPacket")){
                        playerSendPacket = m;
                    }
                }
            }
            playerSendPacket.invoke(playerConnection.get(getHandle(p)), packet);
        } catch (Exception e) {e.printStackTrace();}
    }
}
