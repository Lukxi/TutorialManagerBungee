package de.lukas.TutorialManager;

import de.lukas.TutorialManager.Database.DatabaseManager;
import de.lukas.TutorialManager.Listener.PlayerJoin;
import de.lukas.TutorialManager.Listener.ServerSwitch;
import de.lukas.TutorialManager.Util.ConfigManager;
import de.lukas.TutorialManager.Util.TutorialManagerCommand;
import me.oxolotel.utils.wrapped.event.player.PlayerLoginEvent;
import me.oxolotel.utils.wrapped.module.ModuleManager;
import me.oxolotel.utils.wrapped.player.Player;
import me.oxolotel.utils.wrapped.plugin.BungeePlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class TutorialManager extends BungeePlugin{

    public static LuckPerms luckPermsAPI;
    public static DatabaseManager db;
    public static String PREFIX;

    @Override
    public void onEnable() {
        PREFIX = "§8§l[§9§lTutorialManager§8§l]§r ";
        db = ModuleManager.registerModule(DatabaseManager.class, this);
        luckPermsAPI = LuckPermsProvider.get();
        new PlayerJoin().register(PlayerLoginEvent.class,this);
        ConfigManager.createConfigConfiguration(this, getDataFolder());
        getProxy().getPluginManager().registerCommand(this, new TutorialManagerCommand("tutorialmanager"));
        getProxy().getPluginManager().registerListener(this, new ServerSwitch());
        ModuleManager.loadModule(db);
    }

    public static void addPermission(String name, String permission) {
        User user = luckPermsAPI.getUserManager().getUser(name);
        // Add the permission
        user.data().add(Node.builder(permission).build());

        // Now we need to save changes.
        luckPermsAPI.getUserManager().saveUser(user);
    }
    public static boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }
}