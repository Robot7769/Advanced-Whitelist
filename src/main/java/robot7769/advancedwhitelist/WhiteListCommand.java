package robot7769.advancedwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;

public class WhiteListCommand implements CommandExecutor {
    private final AdvancedWhiteList plugin;

    public WhiteListCommand(AdvancedWhiteList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0 || strings[0].equalsIgnoreCase("mode")) {
            commandSender.sendMessage(ChatColor.YELLOW + "Current whitelist mode: " + ChatColor.BOLD + plugin.getWhiteListMode() + "\n Use /whitelist help for help");
            return true;
        }
        if (strings[0].equalsIgnoreCase("help")) {
            commandSender.sendMessage(ChatColor.YELLOW + "Whitelist help:\n" +
                    ChatColor.AQUA + "/whitelist help" + ChatColor.RESET + " - Shows this message\n" +
                    ChatColor.AQUA + "/whitelist mode" + ChatColor.RESET + " - Show actual WL mode\n" +
                    ChatColor.AQUA + "/whitelist none" + ChatColor.RESET + " - Nobody allow\n" +
                    ChatColor.AQUA + "/whitelist all" + ChatColor.RESET + " - Allow all player\n" +
                    ChatColor.AQUA + "/whitelist dev" + ChatColor.RESET + " - Allow only dev\n" +
                    ChatColor.AQUA + "/whitelist deti" + ChatColor.RESET + " - Allow deti on camp\n" +
                    ChatColor.AQUA + "/whitelist reload" + ChatColor.RESET + " - Reload config\n" +
                    ChatColor.AQUA + "/whitelist add <dev/deti> <player>" + ChatColor.RESET + " - Add player to whitelist\n" +
                    ChatColor.AQUA + "/whitelist remove <dev/deti> <player>" + ChatColor.RESET + " - Remove player from whitelist\n" +
                    ChatColor.AQUA + "/whitelist list" + ChatColor.RESET + " - List all players in whitelist\n" +
                    ChatColor.AQUA + "/whitelist list <dev/deti>" + ChatColor.RESET + " - List all players in whitelist\n");
            return true;
        }
        if (strings[0].equalsIgnoreCase("none")) {
            plugin.getConfig().set("wl-mode", WhiteListMode.NONE.getName());
            plugin.saveConfig();
            commandSender.sendMessage(ChatColor.YELLOW + "Whitelist mode set to " + ChatColor.RED + "NONE");
            checkPlayers();
            return true;
        }
        if (strings[0].equalsIgnoreCase("all")) {
            plugin.getConfig().set("wl-mode", WhiteListMode.ALL.getName());
            plugin.saveConfig();
            commandSender.sendMessage(ChatColor.YELLOW + "Whitelist mode set to " + ChatColor.GREEN + "ALL");
            checkPlayers();
            return true;
        }
        if (strings[0].equalsIgnoreCase("dev")) {
            plugin.getConfig().set("wl-mode", WhiteListMode.DEV.getName());
            plugin.saveConfig();
            commandSender.sendMessage(ChatColor.YELLOW + "Whitelist mode set to " + ChatColor.GOLD + "DEV");
            checkPlayers();
            return true;
        }
        if (strings[0].equalsIgnoreCase("deti")) {
            plugin.getConfig().set("wl-mode", WhiteListMode.DETI.getName());
            plugin.saveConfig();
            commandSender.sendMessage(ChatColor.YELLOW + "Whitelist mode set to " + ChatColor.LIGHT_PURPLE + "DETI");
            checkPlayers();
            return true;
        }
        if (strings[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            commandSender.sendMessage(ChatColor.YELLOW + "Config reloaded");
            checkPlayers();
            return true;
        }
        if (strings[0].equalsIgnoreCase("add")){
            if (strings.length == 3) {
                if (strings[1].equalsIgnoreCase("dev")) {
                    plugin.getConfig().set(WhiteListMode.DEV.getName(), plugin.getConfig().getStringList(WhiteListMode.DEV.getName()).add(strings[2]));
                    plugin.saveConfig();
                    commandSender.sendMessage(ChatColor.YELLOW + "Player " + ChatColor.BLUE + strings[2] + ChatColor.RESET + " added to whitelist");
                    checkPlayers();
                    return true;
                }
                if (strings[1].equalsIgnoreCase("deti")) {
                    plugin.getConfig().set(WhiteListMode.DETI.getName(), plugin.getConfig().getStringList(WhiteListMode.DETI.getName()).add(strings[2]));
                    plugin.saveConfig();
                    commandSender.sendMessage(ChatColor.YELLOW + "Player " + ChatColor.BLUE + strings[2] + ChatColor.RESET + " added to whitelist");
                    checkPlayers();
                    return true;
                }
            } else {
                commandSender.sendMessage(ChatColor.YELLOW + "Usage: /whitelist add <dev/deti> <player>");
                return true;
            }
        }
        if (strings[0].equalsIgnoreCase("remove")) {
            if (strings.length == 3) {
                if (strings[1].equalsIgnoreCase("dev")) {
                    plugin.getConfig().set(WhiteListMode.DEV.getName(), plugin.getConfig().getStringList(WhiteListMode.DEV.getName()).remove(strings[2]));
                    plugin.saveConfig();
                    commandSender.sendMessage(ChatColor.YELLOW + "Player " + ChatColor.BLUE + strings[2] + ChatColor.RESET + " removed from whitelist");
                    checkPlayers();
                    return true;
                }
                if (strings[1].equalsIgnoreCase("deti")) {
                    plugin.getConfig().set(WhiteListMode.DETI.getName(), plugin.getConfig().getStringList(WhiteListMode.DETI.getName()).remove(strings[2]));
                    plugin.saveConfig();
                    commandSender.sendMessage(ChatColor.YELLOW + "Player " + ChatColor.BLUE + strings[2] + ChatColor.RESET + " removed from whitelist");
                    checkPlayers();
                    return true;
                }
            } else {
                commandSender.sendMessage(ChatColor.YELLOW + "Usage: /whitelist remove <dev/deti> <player>");
                return true;
            }
        }
        if (strings[0].equalsIgnoreCase("list")) {
            if (strings.length == 2) {
                if (strings[1].equalsIgnoreCase("dev")) {
                    commandSender.sendMessage(ChatColor.YELLOW + "Dev whitelist:");
                    for (String playerName : plugin.getConfig().getStringList(WhiteListMode.DEV.getName())) {
                        commandSender.sendMessage(ChatColor.WHITE + playerName);
                    }
                    return true;
                }
                if (strings[1].equalsIgnoreCase("deti")) {
                    commandSender.sendMessage(ChatColor.YELLOW + "Deti whitelist:");
                    for (String playerName : plugin.getConfig().getStringList(WhiteListMode.DETI.getName())) {
                        commandSender.sendMessage(ChatColor.WHITE + playerName);
                    }
                    return true;
                }
            } else if (strings.length == 1) {
                commandSender.sendMessage(ChatColor.YELLOW + "Dev whitelist:");
                for (String playerName : plugin.getConfig().getStringList(WhiteListMode.DEV.getName())) {
                    commandSender.sendMessage(ChatColor.WHITE + playerName);
                }
                commandSender.sendMessage(ChatColor.YELLOW + "Deti whitelist:");
                for (String playerName : plugin.getConfig().getStringList(WhiteListMode.DETI.getName())) {
                    commandSender.sendMessage(ChatColor.WHITE + playerName);
                }
                return true;
            }
        }


        return false;
    }

    private void checkPlayers() {
        if (Objects.equals(plugin.getWhiteListMode(),"all")) {
            return;
        }
        List<String> playerNames = plugin.getConfig().getStringList(plugin.getWhiteListMode());
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (player.isOp()) {
                return;
            }
            String playerName = player.getName();
            if (Objects.equals(plugin.getConfig().getString("author"), playerName)) {
                return;
            }
            if (Objects.equals(plugin.getWhiteListMode(), "none")) {
                player.kickPlayer(plugin.getConfig().getString("kick-msg"));
                return;
            }
            if (!playerNames.contains(playerName)) {
                player.kickPlayer(plugin.getConfig().getString("kick-msg"));
            }
        });
    }
}
