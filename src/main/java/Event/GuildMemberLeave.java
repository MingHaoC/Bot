package Event;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class GuildMemberLeave extends ListenerAdapter {

    String messages[] = {"Welcome [member], our newest McDonald employee", "WHY ARE YOU RUNNING? [member]", "[member] has left the server.  :("};
    public void onGuildMemberLeave(GuildMemberLeaveEvent event){
        Random rand = new Random();
        int number = rand.nextInt(messages.length);
        EmbedBuilder newMemeber = new EmbedBuilder();
        newMemeber.setColor(0x66d8ff);
        newMemeber.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
        event.getGuild().getDefaultChannel().sendTyping().queue();
        event.getGuild().getDefaultChannel().sendMessage(newMemeber.build()).queue();
        newMemeber.clear();
    }
}
