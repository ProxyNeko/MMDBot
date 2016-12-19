package com.mcmoddev.bot.command;

import com.mcmoddev.bot.util.DiscordUtilities;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.EmbedBuilder;

public class CommandMemberCount implements Command {
    
    @Override
    public void processCommand (IMessage message, String[] params) {
        
        final IGuild guild = message.getGuild();
        
        if (guild != null) {

            final String base = String.format("There are %d people in the server :)", guild.getTotalMemberCount());

            if (params.length == 2 && params[1].equalsIgnoreCase("roles")) {

                final StringBuilder builder = new StringBuilder();

                for (final IRole role : guild.getRoles())
                    builder.append(String.format("%s: %d", role.getName().replaceAll("@", ""), guild.getUsersByRole(role).size()) + DiscordUtilities.SEPERATOR);

                final EmbedBuilder embed = new EmbedBuilder();
                embed.ignoreNullEmptyFields();
                embed.withDesc(builder.toString());
                embed.withColor((int) (Math.random() * 0x1000000));

                DiscordUtilities.sendMessage(message.getChannel(), base, embed.build());
            }

            else
                DiscordUtilities.sendMessage(message.getChannel(), base);
        }
    }
    
    @Override
    public String getDescription () {
        
        return "Counts the number of members on the discord server. If roles is added as a parameter, a break down of members per role will also be given. ";
    }
}
