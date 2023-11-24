package de.lukas.TutorialManager.Listener;

import de.lukas.TutorialManager.TutorialManager;
import de.lukas.TutorialManager.Util.ConfigManager;
import me.oxolotel.utils.wrapped.player.Player;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitch implements Listener {

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent e) {
        if (!TutorialManager.db.readPlayerByID(Player.of(e.getPlayer().getName()).getPlayerID(true)).isSolvedQuiz()) {
            Player p = Player.of(e.getPlayer().getName());
            while (p.getServer(true) == null && p.isOnline(true)){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (ConfigManager.servers.contains(p.getServer(true)) && TutorialManager.isPlayerInGroup(p, "mitglied")) {
                p.teleport(ConfigManager.location);
                p.sendMessage(ConfigManager.message);
                TutorialManager.db.updateQuizTable(true, p.getPlayerID(true));
            }
        }
    }

}
