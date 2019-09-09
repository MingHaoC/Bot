package Event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class GuildMemberJoin extends ListenerAdapter {
    String messages[] = {"[member] joined. Do you like dicks?", "It's dangerous to go alone [member], take this dildos"};

    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Random rand = new Random();
        int number = rand.nextInt(messages.length);
        EmbedBuilder newMemeber = new EmbedBuilder();
        newMemeber.setColor(0x66d8ff);
        newMemeber.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
        event.getGuild().getDefaultChannel().sendTyping().queue();
        event.getGuild().getDefaultChannel().sendMessage(newMemeber.build()).queue();
        newMemeber.clear();

        event.getGuild().getController().addRolesToMember(event.getMember(), event.getGuild().getRolesByName("Pleb", true)).complete();
    }
}
