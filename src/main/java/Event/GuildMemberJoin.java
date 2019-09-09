package Event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class GuildMemberJoin extends ListenerAdapter {

    // Hardcoded message(s)
    private String messages[] = {"[member] joined. Do you like adventures?", "It's dangerous to go alone [member], take this sword"};

    /**
     * Welcomes new user that joins the discord
     *
     * @param event new Guild member joining event
     */
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event){
        // Randomize the welcome messages when new user joins the discord
        Random rand = new Random();
        int number = rand.nextInt(messages.length);
        // Output the message to announcement text channel
        EmbedBuilder newMember = new EmbedBuilder();
        newMember.setColor(0x66d8ff);
        newMember.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendTyping().queue();
        event.getGuild().getDefaultChannel().sendMessage(newMember.build()).queue();
        newMember.clear();
        event.getGuild().getController().addRolesToMember(event.getMember(), event.getGuild().getRolesByName("Pleb", true)).complete();
    }
}
