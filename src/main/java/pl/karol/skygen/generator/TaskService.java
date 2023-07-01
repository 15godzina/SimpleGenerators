package pl.karol.skygen.generator;

import jdk.internal.org.jline.utils.ShutdownHooks;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TaskService {
    private final Map<String, BukkitRunnable> tasks = new ConcurrentHashMap<>();

    public void addTask(BukkitRunnable runnable, String string) {
        tasks.put(string, runnable);
    }
    public void removeTask(String string) {
        tasks.remove(string);
    }

    public BukkitRunnable findTaskByName(String name) {
        return tasks.get(name);
    }
}
