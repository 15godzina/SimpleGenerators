package pl.karol.skygen;

import eu.okaeri.commands.Commands;
import eu.okaeri.commands.bukkit.CommandsBukkit;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.karol.skygen.command.AdminGeneratorCommand;
import pl.karol.skygen.config.SkyGeneratorsConfig;
import pl.karol.skygen.config.SkyGeneratorsSerdes;
import pl.karol.skygen.generator.Generator;
import pl.karol.skygen.generator.GeneratorTask;
import pl.karol.skygen.generator.TaskService;

import java.io.File;

public final class GeneratorPlugin extends JavaPlugin {

    private SkyGeneratorsConfig skyGeneratorsConfig;
    @Override
    public void onEnable() {

        skyGeneratorsConfig = ConfigManager.create(SkyGeneratorsConfig.class, (it) ->{
            it.withConfigurer(
                    new YamlSnakeYamlConfigurer(),
                    new SerdesBukkit(),
                    new SerdesCommons(),
                    new SkyGeneratorsSerdes()
            );
            it.withBindFile(new File(this.getDataFolder(), "generatorConfiguration.yml"));
            it.saveDefaults();
            it.load(true);
        });
        final TaskService taskService = new TaskService();

        final Commands commands = CommandsBukkit.of(this);

        commands.registerCommand(new AdminGeneratorCommand(skyGeneratorsConfig, this, taskService));


        skyGeneratorsConfig.generatorMap.forEach((s, generator) -> {
            GeneratorTask generatorTask = new GeneratorTask(generator);

            generatorTask.runTaskTimer(this, 20L, generator.getGenerationRate().getSeconds() * 20);

            taskService.addTask(generatorTask, s);

        });

    }

    @Override
    public void onDisable() {

        skyGeneratorsConfig.save();
    }
}
