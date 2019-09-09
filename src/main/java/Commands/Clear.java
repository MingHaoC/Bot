package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import Main.Main;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static net.dv8tion.jda.api.Permission.ADMINISTRATOR;

public class Clear extends ListenerAdapter {

    /**
     * This method clears message(s) depending on user input (min = 2, max = 200)
     *
     * @param event Message receiving event
     */
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        // Get user input
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        // Check if user wants to use the clear function
        if (args[0].equalsIgnoreCase(Main.prefix + "clear") && Objects.requireNonNull(event.getMessage().getMember()).hasPermission(ADMINISTRATOR)) {
            if (args.length < 2) {
                // User inputted not enough parameter(s)
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Need more than one argument").queue();
            } else {
                try {
                    // Delete the # of message (depending on user input)
                    List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                    event.getChannel().deleteMessages(messages).queue();
                    event.getChannel().sendMessage("Successfully deleted " + Integer.parseInt(args[1]) + " Message").queue();
                } catch (IllegalArgumentException e) {
                    EmbedBuilder error = new EmbedBuilder();
                    error.setColor(0xff3923);
                    System.out.println(e.toString());
                    // Checking cause of the error and outputting it to the user
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
