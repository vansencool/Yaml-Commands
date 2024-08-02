package dev.vansen.yamlcommands;

import com.maximde.pluginsimplifier.PluginHolder;
import com.maximde.pluginsimplifier.command.CommandRegistrar;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CommandLoader {

    public static void registerCommand(String configPath, CommandExecutor executor) {
        registerCommand(configPath, executor, null);
    }

    public static void registerCommand(String configPath, CommandExecutor executor, TabCompleter completer) {
        CompletableFuture.runAsync(() -> {
            File configFile = new File(PluginHolder.getPluginInstance().getDataFolder(), "commands.yml");
            if (!configFile.exists()) {
                PluginHolder.getPluginInstance().getLogger().warning("commands.yml not found in the data folder");
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            if (!config.contains(configPath)) {
                PluginHolder.getPluginInstance().getLogger().warning("Command configuration for " + configPath + " not found in commands.yml");
                return;
            }

            String command = config.getString(configPath + ".name", "null");
            String namespace = config.getString(configPath + ".namespace", "null");
            String permission = config.getString(configPath + ".permission", "null");
            List<String> aliases = config.getStringList(configPath + ".aliases");
            String description = config.getString(configPath + ".description", "null");
            String usage = config.getString(configPath + ".usage", "null");

            CommandRegistrar.registerCommand(
                    command,
                    executor,
                    namespace,
                    usage,
                    description,
                    aliases,
                    permission,
                    completer
            );
        });
    }
}