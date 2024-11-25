# Warning ⚠️

> **This repository is outdated!**  
> Use with caution. While it still works, it is not actively maintained and may not be the best solution for your project.  
> Consider alternatives or ensure you understand the potential risks before using it.

![Outdated Badge](https://img.shields.io/badge/Status-Outdated-red)

# Yaml-Commands

Library which allows users to customize commands using a yaml file

# How to install it?

**Gradle**

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

```groovy
dependencies {
    implementation 'com.github.max1mde:PluginSimplifier:1.0.9'
    implementation 'com.github.vansencool:Yaml-Commands:1.0.8'
}
```

**Maven**

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
    <groupId>com.github.max1mde</groupId>
    <artifactId>PluginSimplifier</artifactId>
    <version>1.0.9</version>
</dependency>
<dependency>
    <groupId>com.github.vansencool</groupId>
    <artifactId>Yaml-Commands</artifactId>
    <version>1.0.8</version>
</dependency>
```

# How to register a command?

```java
CommandLoader.register(String configPath, CommandExecutor executor, TabCompleter completer)
```

or alternatively, If you do not want to use a tab completer

```java
CommandLoader.register(String configPath, CommandExecutor executor)
```

# Examples

Here's a full example on how this works:

```java
package com.example.plugin;

import dev.vansen.yamlcommands.CommandLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ExampleCommand implements CommandExecutor {

    public ExampleCommand() {
        CommandLoader.register("commands.example", this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 0) {
            int num;
            try {
                num = Integer.parseInt(args[0]);
            } catch (final NumberFormatException e) {
                sender.sendMessage("Invalid number! Please enter a valid number for the bound");
                return true;
            }
            sender.sendMessage("Your random number is: " + num);
        } else {
            sender.sendMessage("Please enter a number for the bound!");
        }
        return true;
    }
}
```

Now, here's how the commands.yml should look like:

```yaml
commands:
  example:
    enabled: true
    name: "example"
    namespace: "myplugin"
    description: "example command"
    permission: "myplugin.example"
    usage: "/<command>"
    aliases:
      - "ex"
      - "ex2"
```

# Notes

There is no need to register the command in plugin.yml or paper-plugin.yml.

Be sure to shade the library.
