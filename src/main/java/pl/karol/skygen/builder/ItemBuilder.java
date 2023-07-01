package pl.karol.skygen.builder;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public final class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    private ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public static ItemBuilder newBuilder(Material material) {
        return new ItemBuilder(material);
    }

    public ItemBuilder setName(String name) {
        this.itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.applyItemMeta();
        return this;
    }

    public ItemBuilder setLore(List<String> lines) {
        final List<String> coloredLines = lines.stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toUnmodifiableList());
        this.itemMeta.setLore(coloredLines);
        this.applyItemMeta();
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        this.applyItemMeta();
        return this;
    }

    public ItemStack build() {
        this.applyItemMeta();
        return this.itemStack;
    }

    private void applyItemMeta() {
        this.itemStack.setItemMeta(this.itemMeta);
    }

}