package com.Commands;

import com.Main.Main;
import com.Main.Main.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Commands extends ListenerAdapter {

    /**
     * Basic information on how to use the discord bot
     *
     * @param event Message receiving event
     */
public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event){
        // Checking if user needs help
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(Main.prefix)){
            // Outputting all information about the bot
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("Bot com.Commands");
            info.setDescription("You're too dumb to use this bot?  Don't worry I got you!");
            info.addField("Command: clear", "~clear #ofmessage(s) (# have to be between 1-100 and the message have to be less then 2 week old).  Also requires ADMINISTRATE authority", false);
            info.addField("Command: better calculator", "~calculator operator value1 value2.  Operator(+ | - | / | *)", false);
            info.addField("Command: get the latest Anime", "~anime", false);
            info.setColor(0x33FFFF);
            info.setFooter("Created by Ming", Objects.requireNonNull(Main.jda.getUserById("178616639049170945")).getAvatarUrl());
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }

    }

}
