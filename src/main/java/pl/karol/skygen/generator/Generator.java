package pl.karol.skygen.generator;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;

public final class Generator {
    private final String name;
    private final ItemStack icon;
    private final Location location;
    private final Duration generationRate;

    public Generator(String name, ItemStack icon, Location location, Duration generationRate) {
        this.name = name;
        this.icon = icon;
        this.location = location;
        this.generationRate = generationRate;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public Duration getGenerationRate() {
        return generationRate;
    }

    public Location getLocation() {
        return location;
    }
}
