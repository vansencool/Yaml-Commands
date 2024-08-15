package dev.vansen.yamlcommands;

import com.maximde.pluginsimplifier.PluginHolder;
import com.maximde.pluginsimplifier.command.CommandRegistrar;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CommandLoader {

    public static void register(String configPath, CommandExecutor executor) {
        register(configPath, executor, null);
    }

    public static void register(String configPath, CommandExecutor executor, TabCompleter completer) {
        CompletableFuture.runAsync(() -> {
            File configFile = new File(PluginHolder.getPluginInstance().getDataFolder(), "commands.yml");
            if (!configFile.exists()) {
                PluginHolder.getPluginInstance().saveResource("commands.yml", false);
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            if (!config.contains(configPath)) {
                PluginHolder.getPluginInstance().getLogger().warning("Command configuration for " + configPath + " not found in commands.yml (it will still try to register the command)");
            }

            if (config.getBoolean(configPath + ".enabled", true)) {
                CommandRegistrar.command(config.getString(configPath + ".name", configPath.split("\\.")[configPath.split("\\.").length - 1]), executor)
                        .namespace(config.getString(configPath + ".namespace", PluginHolder.getPluginInstance().getPluginMeta().getName()))
                        .usage(config.getString(configPath + ".usage", "null"))
                        .description(config.getString(configPath + ".description", "null"))
                        .aliases(config.getStringList(configPath + ".aliases"))
                        .permission(config.getString(configPath + ".permission", "null"))
                        .permissionMessage(config.getString(configPath + ".permission_message", "null"))
                        .completer(completer)
                        .register();
            }
        });
    }
}