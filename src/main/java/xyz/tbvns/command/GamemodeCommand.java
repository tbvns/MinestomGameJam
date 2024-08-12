package xyz.tbvns.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;

import java.util.List;
import java.util.Locale;

public class GamemodeCommand extends Command {
    public GamemodeCommand() {
        super("gamemode", "gm");

        ArgumentEnum<GameMode> arg = ArgumentType.Enum("gamemode", GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);
        ArgumentEntity playerArg = ArgumentType.Entity("player").onlyPlayers(true);

        setDefaultExecutor(((commandSender, commandContext) -> {
            commandSender.sendMessage("/gamemode <gamemode> </player>");
        }));

        addSyntax((sender, ctx) -> {
            GameMode gamemode = ctx.get(arg);
            if (!(sender instanceof Player p)) {
                sender.sendMessage("You can't use that");
                return;
            }
            setGameModeSelf(p, gamemode);

        }, arg);

        addSyntax((sender, ctx) -> {
            GameMode gameMode = ctx.get(arg);
            EntityFinder finder = ctx.get(playerArg);
            List<Entity> entities = finder.find(sender);

            setGameModeFor(sender, entities, gameMode);

            }, arg, playerArg);
    }

    private void setGameModeFor(CommandSender sender, List<Entity> target, GameMode mode) {
        if (target.isEmpty()) {
            sender.sendMessage(Component.translatable("argument.entity.notfound.player").color(NamedTextColor.RED));
            return;
        }

        for (Entity entity : target) {
            if (entity instanceof Player p) {
                if (p == sender) {
                    setGameModeSelf((Player) sender, mode);
                    return;
                }

                p.setGameMode(mode);
                sender.sendMessage(Component.translatable("commands.gamemode.success.other", NamedTextColor.GRAY));
            }
        }
    }

    private void setGameModeSelf(Player p, GameMode gamemode) {
        p.setGameMode(gamemode);

        String gameModeStr = "gameMode." + gamemode.name().toLowerCase(Locale.ROOT);
        Component gameModeComponent = Component.translatable(gameModeStr).color(NamedTextColor.GREEN);
        p.sendMessage(Component.translatable("gameMode.changed", gameModeComponent).color(NamedTextColor.GRAY));
    }
}
