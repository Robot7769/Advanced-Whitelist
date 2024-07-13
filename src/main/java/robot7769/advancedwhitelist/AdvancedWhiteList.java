package robot7769.advancedwhitelist;

import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedWhiteList extends JavaPlugin {

    private WhiteListMode whiteListMode;

    public WhiteListMode getWhiteListMode() {
        whiteListMode = WhiteListMode.valueOf(getConfig().getString("wl-mode"));
        return whiteListMode;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getCommand("whitelist").setExecutor(new WhiteListCommand(this));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
