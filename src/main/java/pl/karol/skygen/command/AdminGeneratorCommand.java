package pl.karol.skygen.command;

import eu.okaeri.commands.annotation.Arg;
import eu.okaeri.commands.annotation.Command;
import eu.okaeri.commands.annotation.Executor;
import eu.okaeri.commands.bukkit.annotation.Permission;
import eu.okaeri.commands.bukkit.annotation.Sender;
import eu.okaeri.commands.service.CommandService;
import it.unimi.dsi.fastutil.shorts.ShortRBTreeSet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.karol.skygen.config.SkyGeneratorsConfig;
import pl.karol.skygen.generator.Generator;
import pl.karol.skygen.generator.GeneratorTask;
import pl.karol.skygen.generator.TaskService;
import pl.karol.skygen.util.TextUtil;

import java.time.Duration;
import java.util.Arrays;


@Command(
        label = "agen"
)
public final class AdminGeneratorCommand implements CommandService {

    private final SkyGeneratorsConfig skyGeneratorsConfig;
    private final Plugin plugin;

    private final TaskService taskService;

    private Generator generator;

    public AdminGeneratorCommand(SkyGeneratorsConfig skyGeneratorsConfig, Plugin plugin, TaskService taskService) {
        this.skyGeneratorsConfig = skyGeneratorsConfig;
        this.plugin = plugin;
        this.taskService = taskService;
    }

    @Executor
    @Permission("generators.admin")
    public void _def(@Sender Player player) {
        if (!player.hasPermission(skyGeneratorsConfig.permission)) {
            player.sendMessage(TextUtil.colorString("&cNie posiadasz permisji do tej komendy!"));
            return;
        }
        TextUtil.colorStringList(Arrays.asList(
                        "&8* &c/agen create <nazwa> <material> <czas>",
                        "&8* &c/agen delete <nazwa>",
                        "&8* &c/agen list"))
                .forEach(player::sendMessage);
    }

    @Executor(
            pattern = "create <name> <material> <duration>"
    )
    @Permission("generators.admin")
    public void create(@Sender Player player, @Arg("name") String name, @Arg("material") String material, @Arg("duration") Duration duration) {
        Material parsedMaterial = Material.getMaterial(material.toUpperCase());
        if (!player.hasPermission(skyGeneratorsConfig.permission)) {
            player.sendMessage(TextUtil.colorString("&cNie posiadasz permisji do tej komendy!"));
            return;
        }

        if (skyGeneratorsConfig.generatorMap
                .containsKey(name)) {
            player.sendMessage(TextUtil.colorString("&cTaki generator juz istnieje!"));
            return;
        }
        generator = new Generator(name, new ItemStack(parsedMaterial), player.getLocation(), duration);

        skyGeneratorsConfig.generatorMap.put(generator.getName(), generator);
        BukkitRunnable runnable = new GeneratorTask(generator);
        taskService.addTask(runnable, generator.getName());

        player.sendMessage(TextUtil.colorString("&cUtworzyles generator!"));

        runnable.runTaskTimer(plugin, 20L, generator.getGenerationRate().getSeconds() * 20L);
        skyGeneratorsConfig.save();
        skyGeneratorsConfig.update();
    }
    @Executor(
            pattern = "delete <name>"
    )    @Permission("generators.admin")
    public void delete(@Sender Player player, @Arg("name") String name) {
        if (!player.hasPermission(skyGeneratorsConfig.permission)) {
            player.sendMessage(TextUtil.colorString("&cNie posiadasz permisji do tej komendy!"));
            return;
        }
        if (!skyGeneratorsConfig.generatorMap
                        .containsKey(name)) {
            player.sendMessage(TextUtil.colorString("&cTaki generator nie istnieje!"));
            return;
        }
        skyGeneratorsConfig.generatorMap.remove(name);

        BukkitRunnable runnable = taskService.findTaskByName(name);
        if (runnable == null) {
            return;
        }
        taskService.removeTask(name);

        runnable.cancel();


        skyGeneratorsConfig.save();
        skyGeneratorsConfig.update();
        player.sendMessage(TextUtil.colorString("&cUsunales generator!"));
    }
    @Executor(
            pattern = "list"
    )    @Permission("generators.admin")
    public void list(@Sender Player player) {
        if (!player.hasPermission(skyGeneratorsConfig.permission)) {
            player.sendMessage(TextUtil.colorString("&cNie posiadasz permisji do tej komendy!"));
            return;
        }
        player.sendMessage(TextUtil.colorString("&8* &7Lista generatorow: "));
        for (Generator generator : skyGeneratorsConfig.generatorMap.values()) {
            TextUtil.colorStringList(Arrays.asList(
                            "&8* &c" + generator.getName() + " X:" + generator.getLocation().getBlockX() + " Y:" + generator.getLocation().getBlockY() + " Z:" + generator.getLocation().getBlockZ()))
                    .forEach(player::sendMessage);
        }
    }
}
