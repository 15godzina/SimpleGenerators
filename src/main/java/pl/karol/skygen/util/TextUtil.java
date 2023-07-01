package pl.karol.skygen.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public final class TextUtil {

    public static String colorString(String message) {
        if (message.isEmpty()) return "";
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static List<String> colorStringList(List<String> messages) {
        return messages.stream()
                .map(TextUtil::colorString)
                .collect(Collectors.toList());
    }
}
