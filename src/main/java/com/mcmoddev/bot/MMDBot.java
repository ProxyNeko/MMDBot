package com.mcmoddev.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcmoddev.bot.command.CommandCurse;
import com.mcmoddev.bot.command.CommandGreatMoves;
import com.mcmoddev.bot.command.CommandGuild;
import com.mcmoddev.bot.command.CommandHelp;
import com.mcmoddev.bot.command.CommandMe;
import com.mcmoddev.bot.command.CommandXY;
import com.mcmoddev.bot.command.moderative.CommandKill;
import com.mcmoddev.bot.command.moderative.CommandOldChannels;
import com.mcmoddev.bot.command.moderative.CommandReload;
import com.mcmoddev.bot.command.moderative.CommandUser;
import com.mcmoddev.bot.cursemeta.CurseMetaTracker;
import com.mcmoddev.bot.lib.ScheduledTimer;

import ch.qos.logback.classic.Level;
import net.darkhax.botbase.BotBase;
import net.darkhax.botbase.commands.ManagerCommands;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

public class MMDBot extends BotBase {

    public static final Logger LOG = LoggerFactory.getLogger("MMDBot");
    public static MMDBot instance;

    private IRole admin;
    private IRole moderator;
    private IRole botAdmin;

    public final ScheduledTimer timer;
    public CurseMetaTracker curseMeta;

    public static void main (String... args) {

        try {

            final Configuration config = Configuration.getConfig();

            if (Discord4J.LOGGER instanceof ch.qos.logback.classic.Logger) {

                LOG.info("Restricting Discord4J's logger to errors!");
                ((ch.qos.logback.classic.Logger) Discord4J.LOGGER).setLevel(Level.ERROR);
            }

            instance = new MMDBot("MMDBot", config.getDiscordToken(), config.getCommandKey());
            instance.login();
        }

        catch (final Exception e) {

            LOG.trace("Unable to launch bot!", e);
        }
    }

    public MMDBot (String botName, String auth, String commandKey) {

        super(botName, auth, commandKey, LOG);
        instance = this;
        this.timer = new ScheduledTimer();
    }

    @Override
    public boolean isModerator (IGuild guild, IUser user) {

        return user.hasRole(this.moderator);
    }

    @Override
    public boolean isAdminUser (IGuild guild, IUser user) {

        return user.hasRole(this.admin) || user.hasRole(this.botAdmin);
    }

    @Override
    public void registerCommands (ManagerCommands handler) {

        // Moderative
        handler.registerCommand("reload", new CommandReload());
        handler.registerCommand("kill", new CommandKill());
        handler.registerCommand("oldchans", new CommandOldChannels());
        handler.registerCommand("user", new CommandUser());

        // Misc
        handler.registerCommand("guild", new CommandGuild());
        handler.registerCommand("xy", new CommandXY());
        handler.registerCommand("help", new CommandHelp());
        handler.registerCommand("me", new CommandMe());
        handler.registerCommand("curse", new CommandCurse());
        handler.registerCommand("greatmoves", new CommandGreatMoves());
    }

    @Override
    public void onFailedLogin (IDiscordClient instance) {

        // No use
    }

    @Override
    public void onSucessfulLogin (IDiscordClient instance) {

        this.admin = instance.getRoleByID(176781877682634752L);
        this.moderator = instance.getRoleByID(178772974990655489L);
        this.botAdmin = instance.getRoleByID(226067502977777664L);

        this.curseMeta = new CurseMetaTracker(this);
    }

    @Override
    public void reload () {

        super.reload();
        this.curseMeta.updateCurseData();
    }
}