package robot7769.advancedwhitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Objects;

public class JoinListener implements Listener {
    private final AdvancedWhiteList plugin;

    public JoinListener(AdvancedWhiteList plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }
        String playerName = event.getPlayer().getName();
        if (Objects.equals(plugin.getConfig().getString("author"), playerName)) {
            return;
        }
        if (plugin.getWhiteListMode() == WhiteListMode.NONE) {
            event.getPlayer().kickPlayer(plugin.getConfig().getString("kick-msg"));
            return;
        }
        List<String> playerNames = plugin.getConfig().getStringList(plugin.getWhiteListMode().getName());
        if (!playerNames.contains(playerName)) {
            event.getPlayer().kickPlayer(plugin.getConfig().getString("kick-msg"));
        }
    }
}
