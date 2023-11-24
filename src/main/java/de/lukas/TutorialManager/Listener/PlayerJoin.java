package de.lukas.TutorialManager.Listener;

import de.lukas.TutorialManager.Database.QuizTable;
import de.lukas.TutorialManager.TutorialManager;
import de.lukas.TutorialManager.Util.ConfigManager;
import me.oxolotel.utils.wrapped.event.Listener;
import me.oxolotel.utils.wrapped.event.player.PlayerLoginEvent;

import org.jetbrains.annotations.NotNull;


public class PlayerJoin implements Listener<PlayerLoginEvent> {


    @Override
    public void onEvent(@NotNull PlayerLoginEvent e) {
        while (e.getPlayer().getServer(true) == null && e.getPlayer().isOnline(true)){
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (!TutorialManager.isPlayerInGroup(e.getPlayer(), "mitglied")){
            TutorialManager.db.write(new QuizTable(e.getPlayer().getPlayerID(true), false));
        }

        if (ConfigManager.servers.contains(e.getPlayer().getServer(true)) &&
                !TutorialManager.db.readPlayerByID(e.getPlayer().getPlayerID(true)).isSolvedQuiz()
                && TutorialManager.isPlayerInGroup(e.getPlayer(), "mitglied")){
            e.getPlayer().teleport(ConfigManager.location);
            TutorialManager.db.updateQuizTable(true, e.getPlayer().getPlayerID(true));
            e.getPlayer().sendMessage(ConfigManager.message);
        }
    }
}
