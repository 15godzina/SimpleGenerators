package pl.karol.skygen.config;

import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Exclude;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.karol.skygen.builder.ItemBuilder;
import pl.karol.skygen.generator.Generator;
import pl.karol.skygen.util.TextUtil;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public final class SkyGeneratorsConfig extends OkaeriConfig {

    @Exclude
    private final Generator exampleGenerator1 = new Generator(
            TextUtil.colorString("Przykladowa nazwa generatora"),
            ItemBuilder.newBuilder(Material.DIRT)
                    .setName("&cPotężna ziemia")
                    .setLore(Arrays.asList("&aTo jest przykładowe", "&2lore przedmiotu."))
                    .addEnchantment(Enchantment.DAMAGE_ALL, 9).build(),
            new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5),
            Duration.ofSeconds(5)
    );


    public Map<String, Generator> generatorMap = new TreeMap<String, Generator>() {
        {

        }
    };

    public String permission = "karol.generators.admin";

}