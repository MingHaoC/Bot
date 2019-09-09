package Event;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FilterUser extends ListenerAdapter {

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(event.getAuthor().getName().equals("Weegee") && !event.getChannel().toString().equals("TC:weegee(563538387827687444)") && !event.getAuthor().isBot()){
            event.getChannel().sendMessage("This bot is so fast that you won't even see this").queue();
            List<Message> messages = event.getChannel().getHistory().retrievePast(2).complete();
            event.getChannel().deleteMessages(messages).queue();
            event.getChannel().sendMessage("Weegee, you re only allow to speak in weegee channel").queue();
        }
    }

}
