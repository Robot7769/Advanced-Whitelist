package robot7769.advancedwhitelist;

import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedWhiteList extends JavaPlugin {

    private String whiteListMode;

    public String getWhiteListMode() {
        whiteListMode = getConfig().getString("wl-mode");
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
        getConfig().set("wl-mode", getWhiteListMode());
        saveConfig();
        // Plugin shutdown logic
    }
}
