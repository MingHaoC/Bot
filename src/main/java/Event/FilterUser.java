package Event;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FilterUser extends ListenerAdapter {

    /**
     * Determine what user can talk in what channel(S)
     *
     * @param event Message receiving event
     */
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        // Get the person who send the message(s), and check if they are allow to message in that certain channel
        if(event.getAuthor().getName().equals("Weegee") && !event.getChannel().toString().equals("TC:weegee(563538387827687444)") && !event.getAuthor().isBot()){
            // Deletes the user message(s) if they weren't allow to
            event.getChannel().sendMessage("This bot is so fast that you won't even see this").queue();
            List<Message> messages = event.getChannel().getHistory().retrievePast(2).complete();
            event.getChannel().deleteMessages(messages).queue();
            event.getChannel().sendMessage("Weegee, you re only allow to speak in weegee channel").queue();
        }
    }

}
