package com.Event;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class GuildMemberLeave extends ListenerAdapter {
    // Hard coded farewell message(s)
    private String messages[] = {"Welcome [member], our newest McDonald employee", "WHY ARE YOU RUNNING? [member]", "[member] has left the server.  :("};

    /**
     * Says goodbye to user leaving the discord
     *
     * @param event Guild member leaving event
     */
    public void onGuildMemberLeave(@NotNull GuildMemberLeaveEvent event){
        // Randomize the leaving messages
        Random rand = new Random();
        int number = rand.nextInt(messages.length);
        // Output the message to announcement channel
        EmbedBuilder newMemeber = new EmbedBuilder();
        newMemeber.setColor(0x66d8ff);
        newMemeber.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendTyping().queue();
        event.getGuild().getDefaultChannel().sendMessage(newMemeber.build()).queue();
        newMemeber.clear();
    }
}
