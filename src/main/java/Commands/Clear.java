package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import Main.main;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.dv8tion.jda.api.Permission.ADMINISTRATOR;

public class Clear extends ListenerAdapter {

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(main.prefix + "clear") && event.getMessage().getMember().hasPermission(ADMINISTRATOR)) {
            if (args.length < 2) {
                // Error
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Need more than one argument").queue();
            } else {
                try {
                    List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                    event.getChannel().deleteMessages(messages).queue();
                    event.getChannel().sendMessage("Successfully deleted " + Integer.parseInt(args[1]) + " Message").queue();
                } catch (IllegalArgumentException e) {
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(0xff3923);
                    System.out.println(e.toString());
                    if (e.toString().startsWith("java.lang.IllegalArgumentException: Must provide at least 2 or at most 100 messages to be deleted."))
                        error.setTitle("Too many / too little message selected");
                    else
                        error.setTitle("Trying to delete message older than 2 week");
                    error.setDescription("2-100 messages");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(error.build()).queue();
                    error.clear();
                }
            }
        }
    }
}
