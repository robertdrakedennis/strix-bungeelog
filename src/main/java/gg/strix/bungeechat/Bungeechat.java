package gg.strix.bungeechat;

import net.md_5.bungee.api.plugin.Plugin;

public final class Bungeechat extends Plugin {

    @Override
    public void onEnable() {

        getProxy().getPluginManager().registerListener(this, new ChatEvent());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
