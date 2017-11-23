package me.sraldeano.actionlib.util;

import me.sraldeano.actionlib.ActionLib;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemUtil {

    /**
     * Converts a ConfigurationSection into an ItemStack
     * Included options:
     * - (String) type
     * - (int) amount
     * - (byte) data
     * - (String) name
     * - (StringList) lore
     * - (boolean) enchanted
     * - (Section) enchants
     * - (boolean) hide-enchants
     * - (boolean) hide-atributes
     * - (boolean) hide-potion-effects
     * - (boolean) hide-destroys
     * - (boolean) hide-placed-on
     * Exclusive in books:
     * - (StringList) pages
     * - (String) title
     * - (String) author
     *
     * @param section
     * @return the converted ItemStack
     * @since 1.0
     */
    public static ItemStack itemConstructor(ConfigurationSection section) {
        ItemStack item = new ItemStack(Material.getMaterial(section.getString("type", section.getString("item")).toUpperCase()),
                section.getInt("amount", 1), (byte) section.getInt("data", 0));
        ItemMeta meta = item.getItemMeta();
        if (section.isString("name")) {
            meta.setDisplayName(TextUtil.colored(section.getString("name")));
        }
        if (section.isList("lore")) {
            meta.setLore(TextUtil.colored(section.getStringList("lore")));
        }
        if (section.getBoolean("enchanted", false)) {
            if (section.isConfigurationSection("enchants")) {
                Set<String> enchants = section.getConfigurationSection("enchants").getKeys(false);
                for (String e : enchants) {
                    item.addUnsafeEnchantment(Enchantment.getByName(e), section.getInt("enchants." + e));
                    meta.addEnchant(Enchantment.getByName(e), section.getInt("enchants." + e, 1), true);
                }
            }
            else {
                meta.addEnchant(Enchantment.LUCK, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        if (section.getBoolean("hide-enchants", false)) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (section.getBoolean("hide-attributes", true)) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        if (section.getBoolean("hide-potion-effects", false)) {
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        }
        if (section.getBoolean("hide-destroys", false)) {
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        }
        if (section.getBoolean("hide-placed-on", false)) {
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        }


        item.setItemMeta(meta);

        if (item.getType() == Material.WRITTEN_BOOK) {
            try {
                BookMeta bM = (BookMeta) item.getItemMeta();
                bM.setAuthor(section.getString("author", ActionLib.plugin.getServer().getName()));
                bM.setTitle(section.getString("title", "Just a title"));

                if (section.isList("pages")) {
                    List<String> pages = section.getStringList("pages");
                    ArrayList<String> colorPages = new ArrayList<>();
                    for (String p : pages) {
                        colorPages.add(ChatColor.translateAlternateColorCodes('&', p));
                    }
                    bM.setPages(colorPages);
                }
                item.setItemMeta(bM);
            }catch (ClassCastException e) {}
        }
        if (item.getType().name().startsWith("LEATHER_")) {
            LeatherArmorMeta aM = (LeatherArmorMeta) item.getItemMeta();
            if (section.isSet("color")) {
                aM.setColor(TextUtil.getRGBColor(section.getString("color")));
            }
            item.setItemMeta(aM);
        }
        if (item.getType().equals(Material.POTION)) {
            PotionMeta pM = (PotionMeta) item.getItemMeta();
            if (ActionLib.plugin.getServer().getVersion().contains("1.8")) {
                if (section.isConfigurationSection("potion-effects")) {
                    Set<String> effects = section.getConfigurationSection("potion-effects").getKeys(false);
                    for (String e : effects) {
                        ConfigurationSection effectSection = section.getConfigurationSection("potion-effects." + e);
                        pM.addCustomEffect(new PotionEffect(PotionEffectType.getByName(e),
                                effectSection.getInt("duration", 1000),
                                effectSection.getInt("amplifier", 1), effectSection.getBoolean("ambient", false),
                                effectSection.getBoolean("particles", true)), true);
                    }
                }
            }
            else {
                try {
                    pM.setColor(TextUtil.getRGBColor(section.getString("color")));
                    pM.setBasePotionData(new PotionData(PotionType.valueOf(section.getString("potion", "JUMP"))));
                }catch (Exception e) {}
            }
            item.setItemMeta(pM);
        }
        if (item.getType().equals(Material.BANNER)) {
            BannerMeta bM = (BannerMeta) item.getItemMeta();
            if (section.isList("banner-patterns")) {
                List<String> patterns = section.getStringList("banner-patterns");
                for (String p : patterns) {
                    String[] settings = p.split(",");
                    String type = settings[0].toUpperCase();
                    String color = settings[1].toUpperCase();
                    bM.addPattern(new Pattern(DyeColor.valueOf(color), PatternType.valueOf(type)));
                }
            }
            item.setItemMeta(bM);
        }
        if (item.getType().equals(Material.SKULL_ITEM)) {
            SkullMeta sM = (SkullMeta) item.getItemMeta();
            sM.setOwner(section.getString("skull-owner", "Sr_Aldeano"));
            item.setItemMeta(sM);
        }
        if (item.getType().equals(Material.MAP)) {
            MapMeta mM = (MapMeta) item.getItemMeta();
            mM.setScaling(section.getBoolean("scale", true));
            if (section.isConfigurationSection("color")) {
                mM.setColor(TextUtil.getRGBColor(section.getString("color")));
            }
            mM.setLocationName(section.getString("location-name"));
            item.setItemMeta(mM);
        }
        if (item.getType().equals(Material.FIREWORK)) {
            item.setItemMeta(fireworkMetaConstructor(item, section));
        }
        if (item.getType().equals(Material.MONSTER_EGG)) {
            SpawnEggMeta eM = (SpawnEggMeta) item.getItemMeta();
            eM.setSpawnedType(EntityType.valueOf(section.getString("entity", "PIG").toUpperCase()));
        }

        //item.setItemMeta(meta);
        return item;
    }

    public static ItemStack itemConstructor(Map<String, Object> map) {
        MemorySection sect = Util.mapToMemorySection(map);
        return itemConstructor(sect);
    }

    /**
     * Creates various ItemStacks
     * @param section a section wich includes one or more item-sections
     * @return ArrayList of ItemStacks created
     * @since 1.0.5
     */

    public static List<ItemStack> itemsConstructor(ConfigurationSection section) {
        ArrayList<ItemStack> items = new ArrayList<>();
        Set<String> keys = section.getKeys(false);
        for (String key : keys) {
            ItemStack item = itemConstructor(section.getConfigurationSection(key));
            items.add(item);
        }
        return items;
    }

    public static FireworkMeta fireworkMetaConstructor(ItemStack item, ConfigurationSection section) {
        FireworkMeta fM = (FireworkMeta) item.getItemMeta();
        fM.setPower(section.getInt("power"));
        for (String k : section.getConfigurationSection("firework-effects").getKeys(false)) {
            ArrayList<Color> colors = new ArrayList<>();
            ArrayList<Color> fadeColors = new ArrayList<>();
            ConfigurationSection s = section.getConfigurationSection("firework-effects." + k);

            if (!s.isSet("colors")) {
                colors.add(TextUtil.getRGBColor(section.getRoot().getString(s.getCurrentPath() + ".color")));
            }

            if (!s.isSet("fade-colors")) {
                fadeColors.add(TextUtil.getRGBColor(section.getRoot().getString(s.getCurrentPath() + ".color")));
            }

            if (s.isSet("colors")){
                colors.addAll(TextUtil.getRGBColors(s.getStringList("colors")));

            }
            if (s.isSet("fade-colors")) {
                fadeColors.addAll(TextUtil.getRGBColors(s.getStringList("colors")));

            }

            FireworkEffect.Builder effectBuilder = FireworkEffect.builder();

            effectBuilder.with(FireworkEffect.Type.valueOf(s.getString("type")))
                    .trail(s.getBoolean("trail", true)).flicker(s.getBoolean("flicker")).withColor(colors).
                    withFade(fadeColors);

            fM.addEffect(effectBuilder.build());
        }
        return fM;
    }
}
