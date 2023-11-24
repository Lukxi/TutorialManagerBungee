package de.lukas.TutorialManager.Util;

import de.lukas.TutorialManager.TutorialManager;
import me.oxolotel.utils.wrapped.player.Player;
import me.oxolotel.utils.wrapped.world.Location;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TutorialManagerCommand extends Command {
    public TutorialManagerCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            if (!commandSender.hasPermission("tutorialmanager.command")) {
                commandSender.sendMessage(TutorialManager.PREFIX + "§cDu hast keine Rechte für diesen Befehl!");
                return;
            }
            if (strings.length == 0) {
                Player p = me.oxolotel.utils.wrapped.player.Player.of(commandSender);
                Location l = p.getLocation(true);
                ConfigManager.writeLocationConfig(l);
                commandSender.sendMessage(TutorialManager.PREFIX + "§aLocation erfolgreich geändert!");
                ConfigManager.location = l;
                }
            } else commandSender.sendMessage("Diesen Befehel kannst du nur als Spieler verwenden!");
        }
    }

