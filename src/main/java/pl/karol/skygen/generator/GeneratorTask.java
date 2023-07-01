package pl.karol.skygen.generator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public final class GeneratorTask extends BukkitRunnable {
    private final Generator generator;
    private final AtomicReference<Item> itemReference = new AtomicReference<>();

    public GeneratorTask(Generator generator) {
        this.generator = generator;
    }

    @Override
    public void run() {
        final World world = this.generator.getLocation().getWorld();
        Objects.requireNonNull(world);

        final Item item = this.itemReference.get();
        if (item == null || item.isEmpty()) {
            Item item_ref = world.dropItem(this.generator.getLocation(), this.generator.getIcon());
            this.itemReference.set(item_ref);
            item_ref.setVelocity(item_ref.getLocation().getDirection().multiply(0f));
            return;
        }

        final int itemAmount = item.getItemStack().getAmount();
        if (itemAmount == 63) return;

        item.getItemStack().setAmount(itemAmount + 1);
    }
}
