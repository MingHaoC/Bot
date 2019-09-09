package Event;

import Main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LanuageFilter extends ListenerAdapter {

    private List<String> words = new ArrayList<>();

    /**
     * Filtered out any swear word(s)
     *
     * @param event message received event
     */
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {


        try {
            // Check if user bot owner have list of swear words set up if not prompts the user to set it up
            File swearWords = new File("words.txt");
            if (!swearWords.exists()) {
                swearWords.createNewFile();
                event.getChannel().sendMessage("Oh you guy didn't set up filter words. Tell the bot owner to go to this directory and set up the filter words: " + swearWords.getAbsolutePath()).queue();
            }
            // If they do have iut set up, add all the swears words into the ArrayList
            FileInputStream filestream = new FileInputStream("words.txt");
            DataInputStream input = new DataInputStream(filestream);
            BufferedReader re = new BufferedReader(new InputStreamReader(input));
            String newLine;

            while ((newLine = re.readLine()) != null) {
                newLine = newLine.trim();
                if ((newLine.length() != 0)) {
                    words.add(newLine);
                }
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }


        boolean swear = false, add = true;
        StringBuilder temp = new StringBuilder();

        // Get the messages user have inputted
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (!args[0].equalsIgnoreCase(Main.prefix) && !event.getAuthor().isBot() && !Objects.requireNonNull(event.getMessage().getMember()).hasPermission(Permission.ADMINISTRATOR)) {

            // Loop through all the words user have send
            for (String arg : args) {
                // Loop through the swear words list
                for (String word : words) {
                    add = true;
                    // If swear word(s) was found, they are replaced with an ✱
                    if (arg.contains(word)) {
                        swear = true;
                        for (int i = 0; i < arg.length(); i++) {
                            temp.append("✱");
                        }
                        temp.append(" ");
                        add = false;
                        break;
                    }
                }
                if (add)
                    temp.append(arg).append(" ");
            }
        }
        // If user message contains one or more swear word(s), it'll delete that messages and put out brand family message
        if (swear) {
            event.getChannel().sendMessage("This bot is so fast that you won't even see this").queue();
            List<Message> messages = event.getChannel().getHistory().retrievePast(2).complete();
            event.getChannel().deleteMessages(messages).queue();
            event.getChannel().sendMessage("No swearing in my Christan Minecraft server, btw " + Objects.requireNonNull(event.getMember()).getAsMention() + " said the following").queue();
            event.getChannel().sendMessage(event.getMember().getNickname() + "\n" + temp).queue();
        }
    }
}
