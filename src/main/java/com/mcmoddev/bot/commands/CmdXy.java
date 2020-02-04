package com.mcmoddev.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.mcmoddev.bot.misc.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.Instant;

/**
 *
 */
public final class CmdXy extends Command {

	/**
	 *
	 */
	private static final String BODY =
		"The XY problem is asking about your attempted solution rather than your actual problem."
			+ "This leads to enormous amounts of wasted time and energy, both on the part of people asking for help,"
			+ "and on the part of those providing help." + System.lineSeparator()
			+ Utils.makeHyperlink("More Info", "http://xyproblem.info/");

	/**
	 *
	 */
	public CmdXy() {
		super();
		name = "xy";
		aliases = new String[]{"xy-problem", "xyproblem"};
		help = "Gives info on \"The XY Problem\"";
	}

	/**
	 *
	 */
	@Override
	protected void execute(final CommandEvent event) {
		final EmbedBuilder embed = new EmbedBuilder();
		final TextChannel channel = event.getTextChannel();

		embed.setTitle("The XY Problem, what is it?");
		embed.setDescription(BODY);
		embed.setColor(Color.ORANGE);
		embed.setTimestamp(Instant.now());

		channel.sendMessage(embed.build()).queue();
	}
}
